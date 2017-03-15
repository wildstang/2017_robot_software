package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.control.AutoStepDelay;
import org.wildstang.framework.config.Config;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.auto.steps.CloseGearHolderStep;
import org.wildstang.yearly.auto.steps.DeliverGearStep;
import org.wildstang.yearly.auto.steps.OpenGearHolderStep;
import org.wildstang.yearly.auto.steps.PathFollowerStep;
import org.wildstang.yearly.auto.steps.SetBrakeModeStep;
import org.wildstang.yearly.auto.steps.SetHighGearStep;
import org.wildstang.yearly.auto.steps.TrackVisionToGearStep;

public class LeftGear extends AutoProgram
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

      addStep(new PathFollowerStep(PathNameConstants.WALL_TO_LEFT_GEAR));

      addStep(new TrackVisionToGearStep());
      
      addStep(new DeliverGearStep());
      addStep(new OpenGearHolderStep());
      // Wait to let it settle
      addStep(new AutoStepDelay(500));

      // Go backwards 2ft
//      addStep(new DriveDistanceStraightStep(0.5, -24));
      addStep(new PathFollowerStep(PathNameConstants.BACKWARDS_2FT));

   }

   @Override
   public String toString()
   {
      return "Left Gear";
   }

}
