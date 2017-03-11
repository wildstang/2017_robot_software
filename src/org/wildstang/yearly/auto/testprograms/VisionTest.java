package org.wildstang.yearly.auto.testprograms;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.yearly.auto.steps.SetBrakeModeStep;
import org.wildstang.yearly.auto.steps.TrackVisionToGearStep;

public class VisionTest extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      // TODO Auto-generated method stub
      addStep(new SetBrakeModeStep(true));
      addStep(new TrackVisionToGearStep());
   }

   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return "Vision Test";
   }

}
