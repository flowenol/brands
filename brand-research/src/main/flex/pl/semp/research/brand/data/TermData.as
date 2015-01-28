package pl.semp.research.brand.data
{
	[XmlClass(alias="term", ordered=true)]
	[Bindable]
	public class TermData
	{
		[XmlElement(alias="id", order = 1)]
		public var id:String;
		
		[XmlElement(alias="value", order = 2)]
		public var value:String;
		
		[XmlElement(alias="time", order = 3)]
		public var time:Number;
		
		public function TermData()
		{
		}
	}
}