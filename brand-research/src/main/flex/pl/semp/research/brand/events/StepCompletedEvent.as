package pl.semp.research.brand.events
{
	import flash.events.Event;
	
	import pl.semp.research.brand.data.StepData;
	
	public class StepCompletedEvent extends Event
	{
		public static const STEP_COMPLETED:String = "stepCompleted";
		
		public var stepData:StepData;
		
		public function StepCompletedEvent(stepData:StepData = null)
		{
			super(STEP_COMPLETED, true);
			this.stepData = stepData;
		}
		
	}
}