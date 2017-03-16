package org.wildstang.yearly.auto.testprograms;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.control.AutoStepDelay;
import org.wildstang.yearly.auto.steps.FloodGatesCloseStep;
import org.wildstang.yearly.auto.steps.FloodGatesOpenStep;
import org.wildstang.yearly.auto.steps.WaitStep;

public class TestWait extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      addStep(new FloodGatesOpenStep());
      addStep(new AutoStepDelay(2000));
      addStep(new FloodGatesCloseStep());
   }

   @Override
   public String toString()
   {
      return "Test Wait";
   }

}
