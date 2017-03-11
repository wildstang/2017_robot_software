package org.wildstang.yearly.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.robot.RobotTemplate;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.Drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TrackVisionToGearStep extends AutoStep
{
   double distance;
   double xCorrection;
   private Drive m_drive;
   private final double CORRECTION_HEADING_LEVEL = 1.5;

   @Override
   public void initialize()
   {
      m_drive = (Drive) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName());
      m_drive.setAutoGearMode();
//      m_drive.setOpenLoopDrive();
      m_drive.setHighGear(true);
   }

   @Override
   public void update()
   {
//      xCorrection = RobotTemplate.getVisionServer().getXCorrectionLevel();
//      distance = RobotTemplate.getVisionServer().getDistance();
//
//      SmartDashboard.putNumber("Distance", distance);
//      SmartDashboard.putNumber("xCorrection", xCorrection);
//
//      m_drive.setHeading(xCorrection * CORRECTION_HEADING_LEVEL);
//
//      if (distance < 36)
//      {
//         m_drive.setThrottle(.15);
//      }
//      else
//      {
//         m_drive.setThrottle(.3);
//      }
//
//      if (distance < 10)
//      {
//         m_drive.setThrottle(0);
//         setFinished(true);
//      }
      if (m_drive.isGearDropFinished())
      {
         setFinished(true);
      }
   }

   @Override
   public String toString()
   {
      return "Track Vision Target To Gear Step";
   }

}
