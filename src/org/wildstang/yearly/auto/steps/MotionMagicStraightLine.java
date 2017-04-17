package org.wildstang.yearly.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.Drive;
import org.wildstang.yearly.subsystems.drive.DriveConstants;

public class MotionMagicStraightLine extends AutoStep
{

   
   double rotations;
   Drive m_drive;
   
   public MotionMagicStraightLine(double distance) {
      rotations = distance; //* (12 / (4 * Math.PI));
   }
   
   @Override
   public void initialize()
   {
      // TODO Auto-generated method stub
      m_drive = (Drive)Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName());

      m_drive.setMotionMagicMode(true, DriveConstants.MM_DRIVE_F_GAIN);
      m_drive.resetEncoders();
   }

   @Override
   public void update()
   {
      // TODO Auto-generated method stub
      m_drive.setMotionMagicTargetAbsolute(rotations, rotations);
      
      
   }

   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return "Motion Magic Straight Drive";
   }

}
