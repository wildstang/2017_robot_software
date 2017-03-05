package org.wildstang.yearly.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.Shooter;

public class FloodGatesOpenStep extends AutoStep
{
   Shooter m_shooter;

   @Override
   public void initialize()
   {
      m_shooter = (Shooter) Core.getSubsystemManager().getSubsystem(WSSubsystems.SHOOTER.getName());
   }

   @Override
   public void update()
   {
      m_shooter.openBothGate();
      setFinished(true);
   }

   @Override
   public String toString()
   {
      return "Open feed gates";
   }

}
