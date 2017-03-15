package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.control.AutoStepDelay;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.auto.steps.FeedOffStep;
import org.wildstang.yearly.auto.steps.FeedOnStep;
import org.wildstang.yearly.auto.steps.FloodGatesOpenStep;
import org.wildstang.yearly.auto.steps.PathFollowerStep;
import org.wildstang.yearly.auto.steps.SetBrakeModeStep;
import org.wildstang.yearly.auto.steps.SetHighGearStep;
import org.wildstang.yearly.auto.steps.ShootStep;
import org.wildstang.yearly.auto.steps.StopShooting;
import org.wildstang.yearly.auto.steps.ShooterOnAndReady;
import org.wildstang.yearly.auto.steps.WaitStep;

public class BoilerShoot extends AutoProgram
{

   private long delayWhileShooting;

   @Override
   public void initialize()
   {
      super.initialize();
      
      // Read config values
      delayWhileShooting = Core.getConfigManager().getConfig().getInt(this.getClass().getName() + ".delayWhileShooting", 5000);
   }

   @Override
   protected void defineSteps()
   {
      // Set high gear state
      addStep(new SetHighGearStep(true));
      addStep(new SetBrakeModeStep(true));
      
//      // Drive from the wall to the hopper
//      addStep(new PathFollowerStep(PathNameConstants.WALL_TO_BOILER));
//
      // Turn on shooter and shoot
      addStep(new FloodGatesOpenStep());
      addStep(new ShooterOnAndReady());
      addStep(new ShootStep());
      //addStep(new FeedOnStep());
      addStep(new AutoStepDelay(15000));
      addStep(new StopShooting());
      
   }


   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return "Boiler direct shoot";
   }

}
