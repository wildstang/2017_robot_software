package org.wildstang.yearly.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.Drive;

public class PIDdrive extends AutoStep
{
   Drive m_drive;
   double m_throttle;
   double m_heading;
   int m_distance;

   public PIDdrive(double throttle, double heading, int distance)
   {
      m_throttle = throttle;
      m_heading = heading;
      m_distance = distance;
   }

   @Override
   public void initialize()
   {
      m_drive = (Drive) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName());
      m_drive.setPIDHeading(m_heading);
      m_drive.setPIDThrottle(m_throttle);
      m_drive.resetEncoders();
      m_drive.setPIDMode();
   }

   @Override
   public void update()
   {
      // TODO Auto-generated method stub

      if (m_drive.getEncoderDistanceInches() >= m_distance)
      {
         m_drive.setPIDThrottle(0);
         m_drive.setPIDHeading(0);
         setFinished(true);
      }

   }

   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return "PID Drive straight";
   }

}
