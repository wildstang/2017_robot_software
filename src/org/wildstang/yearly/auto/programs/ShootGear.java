package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.control.AutoStepDelay;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.auto.steps.ShootStep;
import org.wildstang.yearly.auto.steps.StopShooting;
import org.wildstang.yearly.auto.steps.ShooterOnAndReady;

public class ShootGear extends AutoProgram
{

   private long delayWhileShooting;

   @Override
   public void initialize()
   {
      super.initialize();
      
      // Read config values
      delayWhileShooting = Core.getConfigManager().getConfig().getInt(this.getClass().getName() + ".delayWhileShooting", 2000);
   }
   protected void defineSteps()
   {
      // TODO Auto-generated method stub
      addStep(new ShooterOnAndReady());
      addStep(new ShootStep());
      
      addStep(new AutoStepDelay(2000));
      
      addStep(new StopShooting());
      
//       TODO Correct Paths and Tracker, if need be
//      addStep(new PathFollowerStep(config.getString("AUTO_PATH", "")));
//      addStep(new TrackVisionToGearStep());
//      addStep(new DeliverGearStep());
//      addStep(new OpenGearHolderStep());
      
   }

   @Override
   public String toString()
   {
      return "Shoot Gear";
   }

}
