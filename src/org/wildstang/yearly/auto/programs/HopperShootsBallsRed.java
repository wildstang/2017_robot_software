package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.auto.steps.FeedOffStep;
import org.wildstang.yearly.auto.steps.FeedOnStep;
import org.wildstang.yearly.auto.steps.WaitStep;
import org.wildstang.yearly.auto.steps.ShootStep;

public class HopperShootsBallsRed extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      // Default is 10 seconds for testing
      double hopperWaitTime = Core.getConfigManager().getConfig().getDouble(this.getClass().getName()
            + ".waitStep", 10000);

      // TODO Add path to hopper here
      addStep(new FeedOnStep());
      addStep(new WaitStep(hopperWaitTime));
      addStep(new FeedOffStep());
      // TODO Add path to boiler here
      addStep(new ShootStep());
   }

   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return "Hopper-Shoot Red";
   }

}
