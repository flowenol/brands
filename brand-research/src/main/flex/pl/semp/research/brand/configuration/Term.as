package pl.semp.research.brand.configuration
{
	[XmlClass(alias="term")]
	[Bindable]
	public class Term
	{
		[XmlElement(alias="id")]
		public var id:String;
		
		[XmlElement(alias="term")]
		public var term:String;
		
		public function Term()
		{
			super();
		}
	}
}