package pl.semp.research.brand.data
{
	[XmlClass(alias="step", ordered=true)]
	[Bindable]
	public class StepData
	{
		[XmlElement(alias="name", order=1)]
		public var name:String;
		
		[XmlElement(alias="inputData", order=2)]
		public var inputData:InputData;
		
		[XmlElement(alias="dictionaryData", order=3)]
		public var dictionaryData:DictionaryData;
		
		public function StepData()
		{
		}
	}
}