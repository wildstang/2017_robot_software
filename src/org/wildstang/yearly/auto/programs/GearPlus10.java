package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.control.AutoStepDelay;
import org.wildstang.yearly.auto.steps.PathFollowerStep;
import org.wildstang.yearly.auto.steps.ShootStep;
import org.wildstang.yearly.auto.steps.ShooterOnAndReady;
import org.wildstang.yearly.auto.steps.SideGearStepGroup;
import org.wildstang.yearly.auto.steps.StopShooting;
import org.wildstang.yearly.auto.steps.TrackVisionToGearStep;
import org.wildstang.yearly.auto.steps.TurnByNDegreesStep;
import org.wildstang.yearly.auto.steps.TurnByNDegreesStepMagic;

public class GearPlus10 extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      //Drop off Gear
      addStep(new SideGearStepGroup(60, 76));
      
      addStep(new PathFollowerStep(PathNameConstants.GEAR_BACKUP_FAR));
      addStep(new AutoStepDelay(200));
      addStep(new TurnByNDegreesStep(-175, 0.6));
      addStep(new AutoStepDelay(200));
      addStep(new PathFollowerStep(PathNameConstants.GEAR_TO_BOILER));
      
      addStep(new ShooterOnAndReady());
      addStep(new ShootStep());
      addStep(new AutoStepDelay(15000));
      addStep(new StopShooting());
   }

   @Override
   public String toString()
   {
      return "Gear plus 10";
   }

}
