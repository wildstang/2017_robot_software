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

public class GearPlus10StraightLeft extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      addStep(new ShooterOnAndReady());
      addStep(new ShootStep());
      addStep(new AutoStepDelay(10000));

      addStep(new StopShooting());
      
      addStep(new TurnByNDegreesStepMagic(80));
      addStep(new AutoStepDelay(200));
      addStep(new MotionMagicStraightLine(90));

   }

   @Override
   public String toString()
   {
      return "Shoot 10 baseline blue";
   }

}
