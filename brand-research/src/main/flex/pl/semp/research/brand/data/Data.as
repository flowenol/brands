package pl.semp.research.brand.data
{
	import mx.collections.ArrayCollection;

	[XmlClass(alias="data", prefix="tns", uri="http://semp.pl/research/brand/data", ordered="true")]
	[Bindable]
	public class Data
	{
		
		[XmlElement(alias="name", namespace="")]
		public var name:String;
		
		[XmlArray(alias="steps", type="pl.semp.research.brand.data.StepData", namespace="")]
		[ArrayElementType("pl.semp.research.brand.data.StepData")]
		public var steps:ArrayCollection = new ArrayCollection();
		
		public function Data()
		{
		}
	}
}