package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.config.Config;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.auto.steps.*;

public class DeliverGearRedRight extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      Config config = Core.getConfigManager().getConfig();
      
      addStep(new PathFollowerStep(config.getString("AUTO_PATH_GEAR_RED_RIGHT", "")));
      addStep(new TrackVisionToGearStep());
      addStep(new DeliverGearStep());
      addStep(new WaitStep(config.getInt("DELIVER_GEAR_WAIT_TIME", 1000)));
      addStep(new CloseGearHolderStep());
      
      // TODO: Drive away?  Shoot?  Need to reuse the above steps
   }

   @Override
   public String toString()
   {
      return "Deliver Gear - Red Right";
   }

}
