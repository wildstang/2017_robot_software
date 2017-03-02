package org.wildstang.yearly.auto.testprograms;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.yearly.auto.programs.PathNameConstants;
import org.wildstang.yearly.auto.steps.PathFollowerStep;

public class TESTWallToGearCenterPath extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      addStep(new PathFollowerStep(PathNameConstants.WALL_TO_GEAR_CENTER));
   }

   @Override
   public String toString()
   {
      return "TEST - Wall to gear center path";
   }


}
