package org.wildstang.yearly.auto.testprograms;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.control.AutoStepDelay;
import org.wildstang.yearly.auto.steps.DriveDistanceStraightStep;
import org.wildstang.yearly.auto.steps.TurnByNDegreesStepMagic;

public class TestTurnMotionMagic extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      addStep(new TurnByNDegreesStepMagic(400));
      addStep(new AutoStepDelay(5000));
      addStep(new TurnByNDegreesStepMagic(-5));
   }

   @Override
   public String toString()
   {
      return "Test Turn Motion Magic";
   }

}
