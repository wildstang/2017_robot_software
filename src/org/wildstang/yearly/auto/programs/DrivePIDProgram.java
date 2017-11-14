package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.yearly.auto.steps.PIDdrive;

public class DrivePIDProgram extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      addStep(new PIDdrive(10, 0, 10));
   }

   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return "PID Drive";
   }

}
