package pl.semp.research.brand.configuration
{
	import mx.collections.ArrayCollection;

	[XmlClass(ordered="true")]
	[Bindable]
	public class InputStep
	{
		
		[XmlElement(alias="text", order=1)]
		public var text:String;
		
		[XmlElement(alias="type", order=2)]
		public var type:String;
		
		[XmlArray(alias="selection", type="String", order=3)]
		public var selection:ArrayCollection;
		
		[XmlElement(alias="valueName", order=4)]
		public var valueName:String
		
		[XmlElement(alias="required", order=5)]
		public var required:Boolean;
		
		[XmlElement(alias="numeric", order=6)]
		public var numeric:Boolean;
		
		[XmlElement(alias="rangeHigh", order=7)]
		public var rangeHigh:int;
		
		[XmlElement(alias="rangeLow", order=8)]
		public var rangeLow:int;
		
		public function InputStep()
		{
			super();
		}
	}
}