package org.wildstang.yearly.auto.testprograms;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.yearly.auto.programs.PathNameConstants;
import org.wildstang.yearly.auto.steps.PathFollowerStep;

public class TEST10FtStraightLineFastPath extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      addStep(new PathFollowerStep(PathNameConstants.STRAIGHT_LINE_10_FT_FAST_TEST));
   }
   
   @Override
   public String toString()
   {
      return "10ft straight line Fast";
   }


}
