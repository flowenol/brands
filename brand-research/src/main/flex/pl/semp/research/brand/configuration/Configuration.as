package pl.semp.research.brand.configuration
{
	import mx.collections.ArrayCollection;

	[XmlClass(alias="configuration", prefix="tns", uri="http://semp.pl/research/brand/configuration", ordered="true")]
	[Bindable]
	public class Configuration
	{
		[XmlElement(alias="name")]
		public var name:String;
		
		[XmlElement(alias="url")]
		public var url:String;
		
		[XmlArray(alias="dictionary", type="pl.semp.research.brand.configuration.Term", namespace="")]
		[ArrayElementType("pl.semp.research.brand.configuration.Term")]
		public var dictionary:ArrayCollection;
		
		[XmlArray(alias="fakeDictionary", type="pl.semp.research.brand.configuration.Term", namespace="")]
		[ArrayElementType("pl.semp.research.brand.configuration.Term")]
		public var fakeDictionary:ArrayCollection;
		
		[XmlArray(alias="steps", type="pl.semp.research.brand.configuration.ConfigurationStep", namespace="")]
		[ArrayElementType("pl.semp.research.brand.configuration.ConfigurationStep")]
		public var steps:ArrayCollection;
		
		public function Configuration()
		{
			super();
		}
	}
}