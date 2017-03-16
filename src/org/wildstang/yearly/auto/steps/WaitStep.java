package org.wildstang.yearly.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;


public class WaitStep extends AutoStep
{
   private long endTime;
   private long duration;
   private boolean firstRun = true;

   public WaitStep(long waitTime)
   {
      duration = waitTime;
   }

   public void initialize()
   {
   }

   @Override
   public void update()
   {
      if (firstRun)
      {
         endTime = System.currentTimeMillis() + duration;
         firstRun = false;
      }

      if (System.currentTimeMillis() >= endTime)
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
