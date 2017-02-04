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

   @Override
   public void initialize()
   {
      shooter = (Shooter) Core.getSubsystemManager().getSubsystem(WSSubsystems.SHOOTER.getName());
   }

   @Override
   public void update()
   {
      shooter.turnFlywheelOn();
      timeDelay(5000);
      shooter.openBothGate();
      timeDelay(5000);
      shooter.turnFeedOn();
      
      timeDelay(10000);

      shooter.turnFeedOff();
      timeDelay(5000);
      shooter.closeBothGate();
      timeDelay(5000);
      shooter.turnFlywheelOff();

      setFinished(true);
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
