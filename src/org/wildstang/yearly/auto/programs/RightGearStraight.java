package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.yearly.auto.steps.MotionMagicStraightLine;
import org.wildstang.yearly.auto.steps.SideGearStepGroup;

public class RightGearStraight extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      addStep(new SideGearStepGroup(-60,78));

      // Go backwards 2ft
      addStep(new MotionMagicStraightLine(-12));

   }

   @Override
   public String toString()
   {
      return "Right Gear straight";
   }

}
