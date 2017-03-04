package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.AutoParallelStepGroup;
import org.wildstang.yearly.auto.steps.CloseGearHolderStep;
import org.wildstang.yearly.auto.steps.DeliverGearStep;
import org.wildstang.yearly.auto.steps.DriveDistanceStraightStep;
import org.wildstang.yearly.auto.steps.OpenGearHolderStep;
import org.wildstang.yearly.auto.steps.TrackVisionToGearStep;
import org.wildstang.yearly.robot.RobotTemplate;

public class GearDriveWithVision extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      AutoParallelStepGroup goingIN = new AutoParallelStepGroup();
      goingIN.addStep(new TrackVisionToGearStep());
      goingIN.addStep(new DeliverGearStep());
      
      AutoParallelStepGroup goingOUT = new AutoParallelStepGroup();
      goingOUT.addStep(new CloseGearHolderStep());
      goingOUT.addStep(new DriveDistanceStraightStep(.05, 12));

      addStep(goingIN);
      addStep(new OpenGearHolderStep());
      addStep(goingOUT);
   }

   @Override
   public String toString()
   {
      return "Gear w/ Vision";
   }

}
