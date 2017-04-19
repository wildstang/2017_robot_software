package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.control.AutoStepDelay;
import org.wildstang.yearly.auto.steps.MotionMagicStraightLine;
import org.wildstang.yearly.auto.steps.PathFollowerStep;
import org.wildstang.yearly.auto.steps.ShootStep;
import org.wildstang.yearly.auto.steps.ShooterOnAndReady;
import org.wildstang.yearly.auto.steps.SideGearStepGroup;
import org.wildstang.yearly.auto.steps.StopShooting;
import org.wildstang.yearly.auto.steps.TurnByNDegreesStepMagic;

public class RightGearToShoot extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      addStep(new SideGearStepGroup(-60));

      // Go backwards 2ft
//      addStep(new DriveDistanceStraightStep(-0.5, 24));
      addStep(new MotionMagicStraightLine(-24));

      addStep(new TurnByNDegreesStepMagic(-175)); //Not sure if negative or positive 175 degrees

      addStep(new MotionMagicStraightLine(60));
      addStep(new ShooterOnAndReady());
      addStep(new ShootStep());
      addStep(new AutoStepDelay(10000));
      addStep(new StopShooting());
   }

   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return "Right Gear to Shoot";
   }

}
