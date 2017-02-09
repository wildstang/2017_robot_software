package org.wildstang.yearly.auto.test;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.yearly.auto.steps.PathFollowerStep;

public class TESTTalonMotionProfileAuto extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      addStep(new PathFollowerStep("/home/lvuser/path.test.txt"));
   }

   @Override
   public String toString()
   {
      return "PathFollower";
   }

}
