package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.config.Config;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.auto.steps.CloseGearHolderStep;
import org.wildstang.yearly.auto.steps.DeliverGearStep;
import org.wildstang.yearly.auto.steps.DriveDistanceStraightStep;
import org.wildstang.yearly.auto.steps.OpenGearHolderStep;
import org.wildstang.yearly.auto.steps.PathFollowerStep;
import org.wildstang.yearly.auto.steps.TrackVisionToGearStep;
import org.wildstang.yearly.auto.steps.WaitStep;

public class RightGear extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      Config config = Core.getConfigManager().getConfig();

      // TODO:  WRONG PATH!!!
      addStep(new PathFollowerStep(PathNameConstants.WALL_TO_GEAR_CENTER));
      addStep(new TrackVisionToGearStep());
      addStep(new DeliverGearStep());
    
      addStep(new OpenGearHolderStep());
      
      // Wait a little for it to settle
      addStep(new WaitStep(200));

      // Drive away
      addStep(new DriveDistanceStraightStep(0.5, -24));

      addStep(new CloseGearHolderStep());

      // TODO: Drive over auto line?
   }

   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return "Right Gear";
   }

}
