package pl.semp.research.brand.utils
{
	import flash.utils.Dictionary;
	
	public class TextUtils
	{
		public function TextUtils()
		{
		}
		
		public static function assignRegisteredValues(text:String, registeredValues:Dictionary):String {
			if (!text || !registeredValues) {
				return text;
			}
			
			var result:String = text;
			for (var registeredValue:String in registeredValues) {
				result = result.replace(new RegExp("{" + registeredValue + "}", "g"), registeredValues[registeredValue]);
			}
			
			return result;
		}
	}
}