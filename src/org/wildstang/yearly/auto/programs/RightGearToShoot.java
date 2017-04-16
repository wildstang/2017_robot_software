package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.control.AutoStepDelay;
import org.wildstang.framework.config.Config;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.auto.steps.*;

public class RightGearToShoot extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      // TODO Auto-generated method stub
      Config config = Core.getConfigManager().getConfig();

      int waitTime = config.getInt(this.getClass().getName() + ".deliverWaitTime", 500);

      // Use high gear
      addStep(new SetHighGearStep(true));

      // For this step, turn off brake mode so we can transition smoothly to vision
      addStep(new SetBrakeModeStep(false));

      addStep(new PathFollowerStep(PathNameConstants.WALL_TO_RIGHT_GEAR));
      addStep(new AutoStepDelay(500));

      addStep(new TrackVisionToGearStep());
      
      addStep(new DeliverGearStep());
      // Wait to let it settle
      addStep(new AutoStepDelay(waitTime));

      // Go backwards 2ft
      addStep(new PathFollowerStep(PathNameConstants.BACKWARDS_2FT));

      addStep(new TurnByNDegreesStepMagic(-175)); //Not sure if negative or positive 175 degrees

      addStep(new PathFollowerStep(PathNameConstants.RIGHT_GEAR_TO_BOILER));
      addStep(new ShooterOnAndReady());
      addStep(new ShootStep());
      addStep(new AutoStepDelay(10000));
      addStep(new StopShooting());
   }

   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return "Right Gear to Shoot";
   }

}
