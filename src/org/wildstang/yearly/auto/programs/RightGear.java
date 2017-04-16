package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.control.AutoStepDelay;
import org.wildstang.framework.config.Config;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.auto.steps.DeliverGearStep;
import org.wildstang.yearly.auto.steps.MotionMagicStraightLine;
import org.wildstang.yearly.auto.steps.SetBrakeModeStep;
import org.wildstang.yearly.auto.steps.SetHighGearStep;
import org.wildstang.yearly.auto.steps.TrackVisionToGearStep;
import org.wildstang.yearly.auto.steps.TurnByNDegreesStep;

public class RightGear extends AutoProgram
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

      // Drive forward and turn 60 degrees towards peg
      addStep(new MotionMagicStraightLine(87));
      addStep(new AutoStepDelay(200));
      addStep(new TurnByNDegreesStep(-60));
      addStep(new AutoStepDelay(200));

      // Track the vision target
      addStep(new TrackVisionToGearStep());
      
      addStep(new DeliverGearStep());
      // Wait to let it settle
      addStep(new AutoStepDelay(waitTime));

      // Go backwards 2ft
      addStep(new MotionMagicStraightLine(-24));

   }

   @Override
   public String toString()
   {
      return "Right Gear";
   }

}
