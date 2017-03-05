package org.wildstang.yearly.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.robot.RobotTemplate;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.Drive;

public class TrackVisionToGearStep extends AutoStep
{
   double distance;
   int xCorrection;
   private Drive m_drive;
   private final double CORRECTION_HEADING_LEVEL = .05;

   @Override
   public void initialize()
   {
      m_drive = (Drive) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName());
      m_drive.setOpenLoopDrive();
   }

   @Override
   public void update()
   {
      xCorrection = RobotTemplate.getVisionServer().getXCorrectionLevel();
      distance = RobotTemplate.getVisionServer().getDistance();

      m_drive.setHeading(xCorrection * CORRECTION_HEADING_LEVEL);

      if (distance < 36)
      {
         m_drive.setThrottle(.1);
      }
      else
      {
         m_drive.setThrottle(.25);
      }

      if (distance < 3)
      {
         m_drive.setThrottle(.05);
         setFinished(true);
      }
   }

   @Override
   public String toString()
   {
      return "Track Vision Target To Gear Step";
   }

}
