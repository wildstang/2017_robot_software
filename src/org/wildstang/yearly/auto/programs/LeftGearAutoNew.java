package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.control.AutoStepDelay;
import org.wildstang.yearly.auto.steps.PathFollowerStep;
import org.wildstang.yearly.auto.steps.TrackVisionToGearStep;
import org.wildstang.yearly.auto.steps.TurnByNDegreesStepMagic;

public class LeftGearAutoNew extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      // TODO Auto-generated method stub
      addStep(new PathFollowerStep(PathNameConstants.GEAR_AUTO_FORWARD));
      addStep(new AutoStepDelay(200));
      addStep(new TurnByNDegreesStepMagic(60));
      addStep(new AutoStepDelay(200));
      addStep(new TrackVisionToGearStep());
   }

   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return "Left Gear vChamps";
   }

   

}
