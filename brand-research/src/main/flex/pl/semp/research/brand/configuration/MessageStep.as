package pl.semp.research.brand.configuration
{
	import mx.collections.ArrayCollection;

	[XmlClass]
	public class MessageStep
	{
	
		[XmlArray(alias="messages", type="String")]
		public var messages:ArrayCollection;
		
		public function MessageStep()
		{
		}
	}
}