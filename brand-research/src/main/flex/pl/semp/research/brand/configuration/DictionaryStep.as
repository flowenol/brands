package pl.semp.research.brand.configuration
{
	[XmlClass(alias="step")]
	[Bindable]
	public class DictionaryStep
	{
		[XmlElement(alias="text")]
		public var text:String;
		
		[XmlElement(alias="includeFakeDictionary")]
		public var includeFakeDictionary:Boolean;
		
		[XmlElement(alias="measureTime")]
		public var measureTime:Boolean;
		
		[XmlElement(alias="type")]
		public var type:String;
		
		public function DictionaryStep()
		{
			super();
		}
	}
}