package org.wildstang.yearly.auto.testprograms;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.yearly.auto.steps.TurnByNDegreesStep;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnTesting extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      // TODO Auto-generated method stub
      //addStep(new PathFollowerStep(PathNameConstants.GEAR_AUTO_FORWARD));
      //addStep(new AutoStepDelay(200));
      //SmartDashboard.putNumber("Test Turn Angle", 0);
      addStep(new TurnByNDegreesStep((int) SmartDashboard.getNumber("Test Turn Angle", 0)));
      //addStep(new AutoStepDelay(200));
      //addStep(new TrackVisionToGearStep());
   }

   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return "Turn Tester";
   }

   

}
