package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.auto.steps.FeedOffStep;
import org.wildstang.yearly.auto.steps.FeedOnStep;
import org.wildstang.yearly.auto.steps.PathFollowerStep;
import org.wildstang.yearly.auto.steps.ShootStep;
import org.wildstang.yearly.auto.steps.ShooterOff;
import org.wildstang.yearly.auto.steps.ShooterOnAndReady;
import org.wildstang.yearly.auto.steps.WaitStep;

public class HopperShoot extends AutoProgram
{

   private double hopperWaitTime;
   private double delayWhileShooting;

   @Override
   public void initialize()
   {
      super.initialize();
      
      // Read config values
      hopperWaitTime = Core.getConfigManager().getConfig().getDouble(this.getClass().getName() + ".hopperWaitTime", 10000);
      delayWhileShooting = Core.getConfigManager().getConfig().getDouble(this.getClass().getName() + ".delayWhileShooting", 2000);
   }

   @Override
   protected void defineSteps()
   {
      // Drive from the wall to the hopper
      addStep(new PathFollowerStep(PathNameConstants.WALL_TO_HOPPER));

      // Turn on feed and wait for balls
      addStep(new FeedOnStep());
      addStep(new WaitStep(hopperWaitTime));
      addStep(new FeedOffStep());

      // TODO Add path to boiler here

      // Turn on shooter and shoot
      addStep(new ShooterOnAndReady());
      addStep(new ShootStep());
      addStep(new WaitStep(delayWhileShooting));
      addStep(new ShooterOff());
   }


   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return "Hopper-Shoot";
   }

}
