package org.wildstang.yearly.auto.testprograms;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.yearly.auto.programs.PathNameConstants;
import org.wildstang.yearly.auto.steps.PathFollowerStep;

public class TESTHopperToBoilerPath extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      addStep(new PathFollowerStep(PathNameConstants.HOPPER_TO_BOILER));
   }
   
   @Override
   public String toString()
   {
      return "TEST - Hopper to boiler path";
   }


}
