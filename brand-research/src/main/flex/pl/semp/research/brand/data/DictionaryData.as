package pl.semp.research.brand.data
{
	import mx.collections.ArrayCollection;

	[XmlClass]
	[Bindable]
	public class DictionaryData
	{
		
		[XmlArray(alias="terms", type="pl.semp.research.brand.data.TermData")]
		[ArrayElementType("pl.semp.research.brand.data.TermData")]
		public var terms:ArrayCollection;
		
		public function DictionaryData()
		{
		}
	}
}