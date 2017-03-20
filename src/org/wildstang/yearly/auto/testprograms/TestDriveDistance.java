package org.wildstang.yearly.auto.testprograms;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.control.AutoStepDelay;
import org.wildstang.yearly.auto.steps.DriveDistanceStraightStep;
import org.wildstang.yearly.auto.steps.FloodGatesCloseStep;
import org.wildstang.yearly.auto.steps.FloodGatesOpenStep;

public class TestDriveDistance extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      addStep(new DriveDistanceStraightStep(0.5, 24));
      addStep(new FloodGatesCloseStep());
      addStep(new AutoStepDelay(2000));
      addStep(new FloodGatesOpenStep());
      addStep(new DriveDistanceStraightStep(0.5, 36));
      addStep(new FloodGatesCloseStep());
   }

   @Override
   public String toString()
   {
      return "Test Drive Distance";
   }

}
