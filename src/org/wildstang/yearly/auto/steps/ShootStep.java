package org.wildstang.yearly.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.Shooter;

public class ShootStep extends AutoStep
{

   private Shooter shooter;

   private long startTime;
   private double timePassed;
   private boolean waiting;

   private double delayWhileShooting;

   @Override
   public void initialize()
   {
      shooter = (Shooter) Core.getSubsystemManager().getSubsystem(WSSubsystems.SHOOTER.getName());
      // Default is 2 seconds for testing
      // WS Config sets to 4 seconds
      delayWhileShooting = Core.getConfigManager().getConfig().getDouble("delayWhileShooting", 2000);
   }

   @Override
   public void update()
   {
      shooter.turnFlywheelOn();
      
      if (shooter.checkRangeForAuto())
      {
         shooter.openBothGate();
         shooter.turnFeedOn();

         timeDelay(delayWhileShooting);

         shooter.closeBothGate();
         shooter.turnFeedOff();
         shooter.turnFlywheelOff();

         setFinished(true);
      }

   }

   @Override
   public String toString()
   {
      return "Shoot Step";
   }

   private void timeDelay(double time)
   {
      waiting = true;
      startTime = System.currentTimeMillis();

      while (waiting)
      {
         timePassed = (double) (System.currentTimeMillis() - startTime);

         if (timePassed == time)
         {
            waiting = false;
         }
      }
   }
}