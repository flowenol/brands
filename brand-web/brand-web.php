<?php
// brand-web.phps
require_once 'bootstrap.php';
require_once 'models/FormData.php';

if (!isset($_POST["formData"])) {
	http_response_code(400); // Bad Request.
	return;
}

$data = new FormData();

// disable schema errors
libxml_use_internal_errors(true);

$document = new DOMDocument('1.0', 'utf-8');

if ($document->loadXML($_POST["formData"]) && $document->schemaValidate('formData.xsd')) {
	$data->setData($_POST["formData"]);
	
	$data->setSubmissionDate(new DateTime("now", new DateTimeZone('Europe/Warsaw')));
	
	$entityManager->persist($data);
	$entityManager->flush();
	
	http_response_code(200); // OK
} else {
	http_response_code(400); // Bad request
}
?>