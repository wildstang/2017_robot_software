package org.wildstang.yearly.auto.steps;

import org.wildstang.framework.auto.steps.AutoSerialStepGroup;
import org.wildstang.framework.auto.steps.control.AutoStepDelay;
import org.wildstang.framework.config.Config;
import org.wildstang.framework.core.Core;

public class SideGearStepGroup extends AutoSerialStepGroup
{
   
   public SideGearStepGroup(int p_turnAngle)
   {
      Config config = Core.getConfigManager().getConfig();
      
      int waitTime = config.getInt(this.getClass().getName() + ".deliverWaitTime", 500);

      // Use high gear
      addStep(new SetHighGearStep(true));

      // For this step, turn off brake mode so we can transition smoothly to vision
      addStep(new SetBrakeModeStep(false));
      addStep(new CloseGearHolderStep());
      addStep(new GearBackStep());

      // Drive forward and turn 60 degrees towards peg
      addStep(new MotionMagicStraightLine(76));
      addStep(new AutoStepDelay(200));
      if (p_turnAngle < 0)
      {
         addStep(new TurnByNDegreesStep(p_turnAngle, 0.5));
      }
      else
      {
         addStep(new TurnByNDegreesStep(p_turnAngle, 0.45));
      }
      addStep(new AutoStepDelay(200));
      addStep(new MotionMagicStraightLine(36));
      // Track the vision target
      addStep(new TrackVisionToGearStep());
      
      addStep(new DeliverGearStep());
      // Wait to let it settle
      addStep(new AutoStepDelay(waitTime));

   }
}
