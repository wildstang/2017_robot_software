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

      addStep(new PathFollowerStep(PathNameConstants.WALL_TO_GEAR_CENTER));
//      addStep(new TrackVisionToGearStep());
      addStep(new DeliverGearStep());
      addStep(new OpenGearHolderStep());

      // TODO: Drive away
      // Go backwards 2ft
      addStep(new DriveDistanceStraightStep(0.5, -24));

      addStep(new CloseGearHolderStep());
      
      // TODO: Drive away?  Shoot?  Need to reuse the above steps
   }

   @Override
   public String toString()
   {
      return "Middle Gear";
   }

}
