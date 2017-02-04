package org.wildstang.yearly.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.Shooter;

public class ShootStep extends AutoStep
{

   private Shooter shooter;

   @Override
   public void initialize()
   {
      shooter = (Shooter) Core.getSubsystemManager().getSubsystem(WSSubsystems.SHOOTER.getName());
   }

   @Override
   public void update()
   {
      shooter.turnFlywheelOn();
      // wait 
      shooter.openBothGate();
      // wait
      shooter.turnFeedOn();
      // wait
      
      shooter.turnFeedOff();
      // wait
      shooter.closeBothGate();
      // wait
      shooter.turnFlywheelOff();
      
      setFinished(true);
   }

   @Override
   public String toString()
   {
      return "Shoot Step";
   }
}
