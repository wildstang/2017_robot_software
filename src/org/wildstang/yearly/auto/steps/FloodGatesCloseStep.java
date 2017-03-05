package org.wildstang.yearly.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.Shooter;

public class FloodGatesCloseStep extends AutoStep
{
   Shooter m_shooter;
   
   @Override
   public void initialize()
   {
      m_shooter = (Shooter) Core.getSubsystemManager().getSubsystem(WSSubsystems.SHOOTER.getName());
      m_shooter.closeBothGate();
   }

   @Override
   public void update()
   {
      // TODO Auto-generated method stub

   }

   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return null;
   }

}
