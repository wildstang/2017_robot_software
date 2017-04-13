package org.wildstang.yearly.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.Drive;
import org.wildstang.yearly.subsystems.WsGyro;

public class TurnToHeading extends AutoStep
{

   private Drive m_drive;
   private WsGyro m_gyro;

   @Override
   public void initialize()
   {
      m_drive = (Drive) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName());

   }

   @Override
   public void update()
   {
      // TODO Auto-generated method stub

   }

   @Override
   public String toString()
   {
      return "Turn n degrees";
   }

}
