package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.yearly.auto.steps.MotionMagicStraightLine;

public class Baseline extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      addStep(new MotionMagicStraightLine(96));
   }

   @Override
   public String toString()
   {
      return "Baseline";
   }

}
