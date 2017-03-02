package org.wildstang.yearly.auto.testprograms;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.yearly.auto.programs.PathNameConstants;
import org.wildstang.yearly.auto.steps.PathFollowerStep;

public class TESTWallToHopperPath extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      addStep(new PathFollowerStep(PathNameConstants.WALL_TO_HOPPER));
   }

   @Override
   public void update()
   {
      
   }
   
   @Override
   public String toString()
   {
      return "TEST - Wall to hopper path";
   }


}
