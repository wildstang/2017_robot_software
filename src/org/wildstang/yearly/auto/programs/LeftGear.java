package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.config.Config;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.auto.steps.CloseGearHolderStep;
import org.wildstang.yearly.auto.steps.DeliverGearStep;
import org.wildstang.yearly.auto.steps.DriveDistanceStraightStep;
import org.wildstang.yearly.auto.steps.OpenGearHolderStep;
import org.wildstang.yearly.auto.steps.PathFollowerStep;
import org.wildstang.yearly.auto.steps.SetBrakeModeStep;
import org.wildstang.yearly.auto.steps.SetDriveGearStep;
import org.wildstang.yearly.auto.steps.TrackVisionToGearStep;
import org.wildstang.yearly.auto.steps.WaitStep;

public class LeftGear extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      Config config = Core.getConfigManager().getConfig();

      // Use high gear
      addStep(new SetDriveGearStep(true));

      // For this step, turn off brake mode so we can transition smoothly to vision
      addStep(new SetBrakeModeStep(false));

      addStep(new CloseGearHolderStep());

      addStep(new PathFollowerStep(PathNameConstants.WALL_TO_LEFT_GEAR));

//      addStep(new TrackVisionToGearStep());
      addStep(new DeliverGearStep());
      addStep(new OpenGearHolderStep());

      // TODO: Drive away
      // Go backwards 2ft
      addStep(new WaitStep(500));
      //addStep(new DriveDistanceStraightStep(0.5, -24));
      addStep(new PathFollowerStep(PathNameConstants.GEAR_CENTER_TO_WALL));

      // TODO: Drive over auto line?
   }

   @Override
   public String toString()
   {
      return "Left Gear";
   }

}
