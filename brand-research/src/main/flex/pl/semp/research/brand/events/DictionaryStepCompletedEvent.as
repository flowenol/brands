package pl.semp.research.brand.events
{
	import flash.events.Event;
	
	public class DictionaryStepCompletedEvent extends Event
	{
		public static const STEP_COMPLETED:String = "dictionaryStepCompleted";
		
		public function DictionaryStepCompletedEvent()
		{
			super(STEP_COMPLETED, true);
		}
	}
}