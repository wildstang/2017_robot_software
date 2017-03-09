package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.config.Config;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.auto.steps.*;

public class MiddleGear extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      Config config = Core.getConfigManager().getConfig();

      addStep(new SetDriveGearStep(true));
      
      // For this step, turn off brake mode so we can transition smoothly to vision
      addStep(new SetBrakeModeStep(false));
      
      addStep(new CloseGearHolderStep());

      addStep(new PathFollowerStep(PathNameConstants.WALL_TO_GEAR_CENTER));
//      addStep(new TrackVisionToGearStep());
      addStep(new DeliverGearStep());
      addStep(new OpenGearHolderStep());

      // Go backwards 2ft
      addStep(new WaitStep(500));
      //addStep(new DriveDistanceStraightStep(0.5, -24));
      addStep(new PathFollowerStep(PathNameConstants.GEAR_CENTER_TO_WALL));
      //addStep(new CloseGearHolderStep());
      
   }

   @Override
   public String toString()
   {
      return "Middle Gear";
   }

}
