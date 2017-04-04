package org.wildstang.yearly.auto.testprograms;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.control.AutoStepDelay;
import org.wildstang.yearly.auto.steps.CloseGearHolderStep;
import org.wildstang.yearly.auto.steps.DriveDistanceStraightStep;
import org.wildstang.yearly.auto.steps.OpenGearHolderStep;
import org.wildstang.yearly.auto.steps.TurnByNDegreesStep;

public class TestTurn extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      addStep(new OpenGearHolderStep());
      addStep(new TurnByNDegreesStep(-50));
      addStep(new CloseGearHolderStep());
   }

   @Override
   public String toString()
   {
      return "Test Turn";
   }

}
