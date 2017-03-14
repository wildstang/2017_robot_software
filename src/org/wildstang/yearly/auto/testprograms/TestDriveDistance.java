package org.wildstang.yearly.auto.testprograms;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.yearly.auto.steps.DriveDistanceStraightStep;
import org.wildstang.yearly.auto.steps.WaitStep;

public class TestDriveDistance extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      addStep(new DriveDistanceStraightStep(0.5, 24));
      addStep(new WaitStep(2000));
      addStep(new DriveDistanceStraightStep(0.5, -24));
   }

   @Override
   public String toString()
   {
      return "Test Wait";
   }

}
