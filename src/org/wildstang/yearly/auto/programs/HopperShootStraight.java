package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.control.AutoStepDelay;
import org.wildstang.yearly.auto.steps.PathFollowerStep;
import org.wildstang.yearly.auto.steps.SetBrakeModeStep;
import org.wildstang.yearly.auto.steps.SetHighGearStep;
import org.wildstang.yearly.auto.steps.TurnByNDegreesStepMagic;

public class HopperShootStraight extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      // TODO Auto-generated method stub
      addStep(new SetHighGearStep(true));
      addStep(new SetBrakeModeStep(true));
      
      addStep(new PathFollowerStep(PathNameConstants.HOPPER_SHOOT_1));
      addStep(new AutoStepDelay(2000));
      addStep(new TurnByNDegreesStepMagic(90));
      addStep(new AutoStepDelay(2000));
      addStep(new PathFollowerStep(PathNameConstants.HOPPER_SHOOT_2));
      addStep(new AutoStepDelay(5000));
      addStep(new PathFollowerStep(PathNameConstants.HOPPER_SHOOT_3));
      addStep(new AutoStepDelay(2000));
      addStep(new TurnByNDegreesStepMagic(90));
      addStep(new AutoStepDelay(2000));
      addStep(new PathFollowerStep(PathNameConstants.HOPPER_SHOOT_4));
      addStep(new AutoStepDelay(2000));
      addStep(new TurnByNDegreesStepMagic(-45));
      addStep(new AutoStepDelay(2000));
      addStep(new PathFollowerStep(PathNameConstants.HOPPER_SHOOT_5));
      addStep(new AutoStepDelay(2000));
      addStep(new TurnByNDegreesStepMagic(90));
   }

   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return "Hopper Shoot Straight Paths";
   }

}
