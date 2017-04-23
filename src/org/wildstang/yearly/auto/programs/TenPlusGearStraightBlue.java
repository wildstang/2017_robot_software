package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.control.AutoStepDelay;
import org.wildstang.yearly.auto.steps.MotionMagicStraightLine;
import org.wildstang.yearly.auto.steps.PathFollowerStep;
import org.wildstang.yearly.auto.steps.ShootStep;
import org.wildstang.yearly.auto.steps.ShooterOnAndReady;
import org.wildstang.yearly.auto.steps.SideGearStepGroup;
import org.wildstang.yearly.auto.steps.StopShooting;
import org.wildstang.yearly.auto.steps.TrackVisionToGearStep;
import org.wildstang.yearly.auto.steps.TurnByNDegreesStepMagic;

public class TenPlusGearStraightBlue extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      //Drop off Gear
      addStep(new SideGearStepGroup(60, 76));
      
      addStep(new MotionMagicStraightLine(-48));
      addStep(new AutoStepDelay(200));
      addStep(new TurnByNDegreesStepMagic(120));
      addStep(new AutoStepDelay(200));
      addStep(new MotionMagicStraightLine(50));
      
      addStep(new TurnByNDegreesStepMagic(45));
      addStep(new AutoStepDelay(200));
      addStep(new MotionMagicStraightLine(36));

      addStep(new ShooterOnAndReady());
      addStep(new ShootStep());
      addStep(new AutoStepDelay(15000));
      addStep(new StopShooting());
   }

   @Override
   public String toString()
   {
      return "Gear plus 10 Left";
   }

}
