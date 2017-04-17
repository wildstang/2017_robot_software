package org.wildstang.yearly.auto.testprograms;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.yearly.auto.programs.PathNameConstants;
import org.wildstang.yearly.auto.steps.MotionMagicStraightLine;
import org.wildstang.yearly.auto.steps.PathFollowerStep;

public class TEST10FtStraightLineSlowPath extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      addStep(new MotionMagicStraightLine(7));
   }
   
   @Override
   public String toString()
   {
      return "10ft straight line Slow";
   }


}
