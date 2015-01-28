package pl.semp.research.brand.data
{
	[XmlClass(alias="inputData", ordered="true")]
	[Bindable]
	public class InputData
	{
		[XmlElement(alias="name", order=1)]
		public var name:String;
		
		[XmlElement(alias="value", order=2)]
		public var value:String;
		
		public function InputData()
		{
		}
	}
}