package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.control.AutoStepDelay;
import org.wildstang.yearly.auto.steps.MotionMagicStraightLine;
import org.wildstang.yearly.auto.steps.PathFollowerStep;
import org.wildstang.yearly.auto.steps.SetBrakeModeStep;
import org.wildstang.yearly.auto.steps.SetHighGearStep;
import org.wildstang.yearly.auto.steps.ShootStep;
import org.wildstang.yearly.auto.steps.ShooterOnAndReady;
import org.wildstang.yearly.auto.steps.StopShooting;
import org.wildstang.yearly.auto.steps.TurnByNDegreesStepMagic;
import org.wildstang.yearly.subsystems.drive.DriveConstants;

public class HopperShootStraight extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      // TODO Auto-generated method stub
      addStep(new SetHighGearStep(true));
      addStep(new SetBrakeModeStep(true));
      //addStep(new MotionMagicStraightLine());
      addStep(new MotionMagicStraightLine(96)); //Need to tune distances
      addStep(new AutoStepDelay(200));
      addStep(new TurnByNDegreesStepMagic(-86));
      addStep(new AutoStepDelay(200));
      addStep(new MotionMagicStraightLine(24));
      addStep(new AutoStepDelay(3000));
      addStep(new MotionMagicStraightLine(-12));
      addStep(new AutoStepDelay(200));
      addStep(new TurnByNDegreesStepMagic(-86));
      addStep(new AutoStepDelay(200));
      addStep(new MotionMagicStraightLine(78));
      addStep(new AutoStepDelay(200));
      //addStep(new ShooterOnAndReady());
      addStep(new TurnByNDegreesStepMagic(45));
      addStep(new AutoStepDelay(500));
      addStep(new MotionMagicStraightLine(12));
      
      //Shoot
      
      addStep(new ShootStep());
      addStep(new AutoStepDelay(15000));
      addStep(new StopShooting());
   }

   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return "Hopper Shoot Straight Paths";
   }

}
