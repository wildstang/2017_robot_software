package org.wildstang.yearly.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WaitStep extends AutoStep
{
   private double startTime;
   private double timePassed;
   private double targetTime;
   private boolean firstRun = true;

   public WaitStep(double waitTime)
   {
      targetTime = waitTime;
   }

   public void initialize()
   {
      startTime = System.currentTimeMillis();
   }

   @Override
   public void update()
   {
      if (firstRun)
      {
         startTime = (double) System.currentTimeMillis();
         firstRun = false;
      }

      timePassed = ((double) System.currentTimeMillis() - startTime);

      if (timePassed >= targetTime)
      {
         setFinished(true);
      }
   }

   @Override
   public String toString()
   {
      return "Wait Step";
   }

}
