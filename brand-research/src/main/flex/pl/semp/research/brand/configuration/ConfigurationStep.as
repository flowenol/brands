package pl.semp.research.brand.configuration
{
	[XmlClass]
	public class ConfigurationStep
	{
		
		[XmlElement(alias="name")]
		public var name:String;
		
		[XmlElement(alias="message")]
		public var message:MessageStep;
		
		[XmlElement(alias="input")]
		public var input:InputStep;
		
		[XmlElement(alias="dictionary")]
		public var dictionary:DictionaryStep;
		
		public function ConfigurationStep()
		{
			super();
		}
	}
}