package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.control.AutoStepDelay;
import org.wildstang.framework.config.Config;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.auto.steps.*;

public class MiddleGear extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      Config config = Core.getConfigManager().getConfig();

      int waitTime = config.getInt(this.getClass().getName() + ".deliverWaitTime", 500);

      // Use high gear
      addStep(new SetHighGearStep(true));
      
      // For this step, turn off brake mode so we can transition smoothly to vision
      addStep(new SetBrakeModeStep(false));
      addStep(new CloseGearHolderStep());

      addStep(new PathFollowerStep(PathNameConstants.WALL_TO_GEAR_CENTER));
      addStep(new AutoStepDelay(500));
//      addStep(new DriveDistanceStraightStep(0.5, 48));
      addStep(new TrackVisionToGearStep());

      addStep(new DeliverGearStep());
      // Wait to let it settle
      addStep(new AutoStepDelay(waitTime));

      // Go backwards 2ft
//      addStep(new DriveDistanceStraightStep(0.5, -24));
      addStep(new PathFollowerStep(PathNameConstants.BACKWARDS_2FT));
      
   }

   @Override
   public String toString()
   {
      return "Middle Gear";
   }

}
