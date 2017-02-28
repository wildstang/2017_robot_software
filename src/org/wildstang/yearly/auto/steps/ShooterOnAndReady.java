package org.wildstang.yearly.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.Shooter;

public class ShooterOnAndReady extends AutoStep
{
   private Shooter m_shooter;

   @Override
   public void initialize()
   {
      m_shooter = (Shooter) Core.getSubsystemManager().getSubsystem(WSSubsystems.SHOOTER.getName());
   }

   @Override
   public void update()
   {
      m_shooter.turnFlywheelOn();

      if (m_shooter.isLeftReadyToShoot() && m_shooter.isRightReadyToShoot())
      {
         setFinished(true);
      }

   }

   @Override
   public String toString()
   {
      return "Shooter on and ready";
   }

}
