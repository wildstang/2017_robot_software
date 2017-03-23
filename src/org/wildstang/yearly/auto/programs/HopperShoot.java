package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.control.AutoStepDelay;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.auto.steps.PathFollowerStep;
import org.wildstang.yearly.auto.steps.SetBrakeModeStep;
import org.wildstang.yearly.auto.steps.SetHighGearStep;
import org.wildstang.yearly.auto.steps.ShootStep;
import org.wildstang.yearly.auto.steps.StopShooting;
import org.wildstang.yearly.auto.steps.ShooterOnAndReady;

public class HopperShoot extends AutoProgram
{

   private int hopperWaitTime;
   private int delayWhileShooting;

   @Override
   public void initialize()
   {
      super.initialize();
 
      
      // Read config values
      // 10000 = ten seconds
      hopperWaitTime = Core.getConfigManager().getConfig().getInt(this.getClass().getName() + ".hopperWaitTime", 3000);
      delayWhileShooting = Core.getConfigManager().getConfig().getInt(this.getClass().getName() + ".delayWhileShooting", 5000);
   }

   @Override
   protected void defineSteps()
   {
      // Set high gear state
      addStep(new SetHighGearStep(true));
      addStep(new SetBrakeModeStep(true));
      
      // Drive from the wall to the hopper
      addStep(new PathFollowerStep(PathNameConstants.WALL_TO_HOPPER));

//      addStep(new FeedOnStep());
      addStep(new AutoStepDelay(hopperWaitTime));
//      addStep(new FeedOffStep());
      
      
//      addStep(new SetBrakeModeStep(false));
      // Backup from the hopper
      addStep(new PathFollowerStep(PathNameConstants.BACKUP_FROM_HOPPER));

      // Turn on feed and wait for balls
      

//      addStep(new SetBrakeModeStep(true));
      addStep(new PathFollowerStep(PathNameConstants.HOPPER_TO_BOILER));

      // Turn on shooter and shoot
      addStep(new ShooterOnAndReady());
      addStep(new ShootStep());
      //addStep(new FeedOnStep());
      addStep(new AutoStepDelay(delayWhileShooting));
      addStep(new StopShooting());
      
      // TODO: Back up over auto line?
   }


   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return "Hopper-Shoot";
   }

}
