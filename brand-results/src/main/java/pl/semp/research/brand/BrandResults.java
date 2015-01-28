package pl.semp.research.brand;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import pl.semp.research.brand.configuration.Configuration;
import pl.semp.research.brand.configuration.ConfigurationStep;
import pl.semp.research.brand.configuration.Term;
import pl.semp.research.brand.data.Data;
import pl.semp.research.brand.data.StepData;
import pl.semp.research.brand.data.TermData;

public class BrandResults {

    private static Logger logger = Logger.getLogger(BrandResults.class);

    static {
        BasicConfigurator.configure();
    }

    private static final String SCHEMA_NAME = "brand";
    private static final String FORM_DATA_SQL = "select data, submissionDate from FormData";

    private static final String DATE_COLUMN = "submissionDate";
    private static final String DATA_COLUMN = "data";

    private static final String[] CONFIGURATIONS = { "/configuration1.xml", "/configuration2.xml" };

    private static final String YES = "tak";
    private static final String NO = "nie";

    private static final String TIME_RESULT_SUFFIX = "_czas";

    private String url;
    private String user;
    private String password;

    private Connection connection;
    private PreparedStatement statement;
    private Unmarshaller configurationUnmarshaller;
    private Unmarshaller dataUnmarshaller;

    private Map<String, Configuration> configurationMap = new HashMap<>();
    // private Map<String, TreeMap<String, String>> researchDictionaryMap = new HashMap<>();
    private Map<String, WritableWorkbook> workbooks = new HashMap<>();

    private static final Comparator<Term> termComparator =
            new Comparator<Term>() {
                @Override
                public int compare(Term o1, Term o2) {
                    return o1.getId().compareTo(o2.getId());
                }
            };

    private static final Comparator<TermData> termDataComparator =
            new Comparator<TermData>() {
                @Override
                public int compare(TermData o1, TermData o2) {
                    return o1.getId().compareTo(o2.getId());
                }
            };

    private static WritableCellFormat getHeaderFormat() {
        try {
            WritableCellFormat writableCellFormat = new WritableCellFormat();

            writableCellFormat.setBackground(Colour.LIGHT_GREEN);
            writableCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

            return writableCellFormat;
        } catch (WriteException e) {
            // Goodbye!
            throw new RuntimeException(e);
        }
    }

    public BrandResults(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public void init() throws SQLException, JAXBException {
        connection = DriverManager.getConnection(url, user, password);
        connection.setReadOnly(true);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
        connection.setSchema(SCHEMA_NAME);

        statement = connection.prepareStatement(FORM_DATA_SQL);
        statement.setFetchSize(1000);

        configurationUnmarshaller = JAXBContext.newInstance("pl.semp.research.brand.configuration")
                .createUnmarshaller();
        dataUnmarshaller = JAXBContext.newInstance("pl.semp.research.brand.data").createUnmarshaller();
    }

    public void process() throws Exception {
        if (connection.isClosed() || statement.isClosed()) {
            throw new IllegalStateException();
        }

        processConfiguration();

        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                logger.info(resultSet.getString(DATE_COLUMN));
                logger.info(new String(resultSet.getBytes(DATA_COLUMN)));

                JAXBElement<Data> dataElement = (JAXBElement<Data>) dataUnmarshaller
                        .unmarshal(new ByteArrayInputStream(resultSet.getBytes(DATA_COLUMN)));
                Data data = dataElement.getValue();

                processData(data);

            }
        }

        for (WritableWorkbook workbook : workbooks.values()) {
            workbook.write();
            workbook.close();
        }

        statement.close();
        connection.close();
    }

    private void processConfiguration() {
        Arrays.asList(CONFIGURATIONS).forEach(
            configurationPath -> {
                try {
                    JAXBElement<Configuration> configurationElement = (JAXBElement<Configuration>) 
                        configurationUnmarshaller.unmarshal(BrandResults.class.getResourceAsStream(configurationPath));
                    Configuration configuration = configurationElement.getValue();
                    Configuration.Dictionary dictionary = configuration.getDictionary();

                    configurationMap.put(configuration.getName(), configuration);
                    /*researchDictionaryMap.put(configuration.getName(), dictionary.getTerm().stream().collect(
                        Collectors.toMap(Term::getId, Term::getTerm, ((t1, t2) -> t1), TreeMap::new)));*/

                    WritableWorkbook workbook = Workbook.createWorkbook(new File(configuration.getName() + ".xls"));
                    workbooks.put(configuration.getName(), workbook);

                    WritableSheet sheet = workbook.createSheet("results", 0);

                    int cell = 0;
                    for (ConfigurationStep stepData: configuration.getSteps().getStep()) {

                        if (stepData.getMessage() != null) {
                            continue;
                        }

                        if (stepData.getInput() != null) {
                            sheet.addCell(new Label(cell, 0, stepData.getName(), getHeaderFormat()));
                            cell++;
                        }

                        if (stepData.getDictionary() != null) {
                            Collections.sort(dictionary.getTerm(), termComparator);

                            for (Term term : dictionary.getTerm()) {
                                sheet.addCell(new Label(cell, 0, stepData.getName() + "_" + term.getTerm(), getHeaderFormat()));

                                cell++;
                                if (stepData.getDictionary().isMeasureTime()) {
                                    sheet.addCell(new Label(cell, 0, stepData.getName() + "_" + term.getTerm() + TIME_RESULT_SUFFIX,
                                        getHeaderFormat()));
                                    cell++;
                                }
                            }
                        }
                    }
                } catch (WriteException | IOException | JAXBException e) {
                    throw new RuntimeException(e);
                }
            }
        );
    }

    private void processData(Data data) throws Exception {
        int cell = 0;

        WritableWorkbook workbook = workbooks.get(data.getName());
        WritableSheet sheet = workbook.getSheet(0);

        int row = sheet.getRows();
        for (StepData stepData : data.getSteps().getStep()) {

            if (stepData.getInputData() != null) {
                sheet.addCell(new Label(cell, row, stepData.getInputData().getValue()));
                cell++;
            }

            if (stepData.getDictionaryData() != null) {
                Collections.sort(stepData.getDictionaryData().getTerms().getTerm(), termDataComparator);

                for (TermData term : stepData.getDictionaryData().getTerms().getTerm()) {

                    if (term.getId().matches("^fake.*$")) {
                        continue;
                    }

                    if (term.getValue().matches(Boolean.TRUE.toString())
                            || term.getValue().matches(Boolean.FALSE.toString())) {
                        sheet.addCell(new Label(cell, row, Boolean.parseBoolean(term.getValue()) ? YES : NO));
                    } else {
                        sheet.addCell(new Label(cell, row, term.getValue()));
                    }

                    cell++;
                    if (term.getTime() != null && term.getTime() > 0) {
                        sheet.addCell(new Label(cell, row, Double.toString(term.getTime())));
                        cell++;
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {

        BrandResults instance = new BrandResults("jdbc:mysql://localhost:8889/brand", "root", "root");
        instance.init();

        instance.process();
    }
}
