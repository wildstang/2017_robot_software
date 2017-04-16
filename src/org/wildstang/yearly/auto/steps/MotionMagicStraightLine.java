package org.wildstang.yearly.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.Drive;
import org.wildstang.yearly.subsystems.drive.DriveConstants;

public class MotionMagicStraightLine extends AutoStep
{

   private double m_rotations;
   private Drive m_drive;
   private boolean m_started = false;
   
   private static final double ONE_ROTATION_INCHES = 4 * Math.PI;
   
   // Tolerance - in rotations. The numerator is in inches
   private static final double TOLERANCE = 1 / ONE_ROTATION_INCHES;
   
   public MotionMagicStraightLine(double p_inches)
   {
      m_rotations = (p_inches / 12) * ONE_ROTATION_INCHES;
   }
   
   @Override
   public void initialize()
   {
      m_drive = (Drive)Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName());

      m_drive.setMotionMagicMode(true, DriveConstants.MM_DRIVE_F_GAIN);
      m_drive.resetEncoders();
   }

   @Override
   public void update()
   {
      if (!m_started)
      {
         m_drive.setMotionMagicTargetAbsolute(m_rotations, m_rotations);
         m_started = true;
      }
      else
      {
         // Check if we've gone far enough
         if (Math.abs(m_drive.getLeftSensorValue() - m_rotations) <= TOLERANCE)
         {
            m_drive.setOpenLoopDrive();
            m_drive.setBrakeMode(true);
            setFinished(true);
         }
      }
   }

   @Override
   public String toString()
   {
      return "Motion Magic Straight Drive";
   }

}
