package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.auto.steps.DeliverGearStep;
import org.wildstang.yearly.auto.steps.FeedOnStep;
import org.wildstang.yearly.auto.steps.OpenGearHolderStep;
import org.wildstang.yearly.auto.steps.PathFollowerStep;
import org.wildstang.yearly.auto.steps.ShootStep;
import org.wildstang.yearly.auto.steps.ShooterOff;
import org.wildstang.yearly.auto.steps.ShooterOnAndReady;
import org.wildstang.yearly.auto.steps.TrackVisionToGearStep;
import org.wildstang.yearly.auto.steps.WaitStep;

public class ShootGear extends AutoProgram
{

   private double delayWhileShooting;

   @Override
   public void initialize()
   {
      super.initialize();
      
      // Read config values
      delayWhileShooting = Core.getConfigManager().getConfig().getDouble(this.getClass().getName() + ".delayWhileShooting", 2000);
   }
   protected void defineSteps()
   {
      // TODO Auto-generated method stub
      addStep(new ShooterOnAndReady());
      addStep(new ShootStep());
      
      addStep(new WaitStep(delayWhileShooting));
      
      addStep(new ShooterOff());
      
//       TODO Correct Paths and Tracker, if need be
//      addStep(new PathFollowerStep(config.getString("AUTO_PATH", "")));
//      addStep(new TrackVisionToGearStep());
//      addStep(new DeliverGearStep());
//      addStep(new OpenGearHolderStep());
      
   }

   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return "Shoot Gear";
   }

}
