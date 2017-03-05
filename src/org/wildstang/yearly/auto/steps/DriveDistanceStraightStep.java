package org.wildstang.yearly.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.Drive;

public class DriveDistanceStraightStep extends AutoStep
{
   Drive m_drive;
   double m_speed;
   int m_distance2Go;

   public DriveDistanceStraightStep(double speed, int distance)
   {
      m_speed = speed;
      m_distance2Go = distance;
      if (distance < 0)
      {
         m_speed *= -1;
      }
   }

   public void initialize()
   {
      m_drive = (Drive) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName());
      m_drive.setOpenLoopDrive();
      m_drive.resetEncoders();
   }

   @Override
   public void update()
   {
      if (m_drive.getEncoderDistance() < m_distance2Go)
      {
         m_drive.setThrottle(m_speed);
      }
      else
      {
         m_drive.setThrottle(0);
         setFinished(true);
      }
   }

   @Override
   public String toString()
   {
      return "Drive Straight Distance";
   }

}
