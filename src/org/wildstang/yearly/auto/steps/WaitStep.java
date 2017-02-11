package org.wildstang.yearly.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;


public class WaitStep extends AutoStep
{
   private long startTime;
   private double timePassed;
   private double targetTime;
   private boolean firstRun = true;
   
   public WaitStep(long waitTime)
   {
      targetTime = waitTime;
   }
   
   public void initialize()
   {

      startTime = System.currentTimeMillis();
      // Default is 2 seconds for testing
      // WS config sets to 5 seconds
      targetTime = Core.getConfigManager().getConfig().getDouble(this.getClass().getName()
            + ".waitStep", 2000);

   }

   @Override
   public void update()
   {
      if (firstRun)
      {
         startTime = System.currentTimeMillis();
         firstRun = false;
      }

      timePassed = (double) (System.currentTimeMillis() - startTime);

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
