package org.wildstang.yearly.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.hardware.crio.inputs.WsAnalogGyro;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.Drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnByNDegreesStepMagic extends AutoStep
{
   private WsAnalogGyro m_gyro;
   private Drive m_drive;
   private int m_deltaHeading;
   private int m_target;
   double m_rightTarget;
   double m_leftTarget;
   
   private int m_currentHeading;
   
   private long m_cycleCount = 0;
   
   private static final int TOLERANCE = 1;
   private static final int TICKS_PER_DEGREE = 101;
   
   public TurnByNDegreesStepMagic(int p_deltaHeading)
   {
      m_deltaHeading = p_deltaHeading;
   }
   
   @Override
   public void initialize()
   {
      m_gyro = (WsAnalogGyro)Core.getInputManager().getInput(WSInputs.GYRO.getName());
      m_drive = (Drive)Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName());

      m_drive.setMotionMagicMode(true);
      
      // The gyro drift compensation means we should be able to set the target in initialize() rather than on
      // first time through update()
      m_currentHeading = (int)m_gyro.getValue();
      m_target = getCompassHeading((m_currentHeading + m_deltaHeading));
      double rotations = getRotationsForDeltaAngle((int)modAngle(m_deltaHeading));
      
      // Turning left means right is a positive count
      m_rightTarget = -rotations;
      m_leftTarget = rotations;

      SmartDashboard.putNumber("Initial heading", m_currentHeading);
      SmartDashboard.putNumber("Target heading", m_target);
      SmartDashboard.putNumber("Target Encoder Ticks", rotations * 4096);
   }

   @Override
   public void update()
   {      
      m_drive.setHighGear(false);
//      m_drive.setQuickTurn(true);
      
      m_currentHeading = getCompassHeading((int)m_gyro.getValue());

      // Every 5 cycles (about 100ms) recalculate to adjust for slipping
      // This will also execute on the first run, setting the initial targets
//      if (m_cycleCount++ % 5 == 0)
//      {
         // These values are rotations, for Motion Magic
//         double rotations = getRotationsForDeltaAngle(m_currentHeading - m_target);
//         
//         // Turning left means right is a positive count
//         double rightTarget = rotations;
//         double leftTarget = -rotations;
         
         m_drive.setMotionMagicTarget(m_leftTarget, m_rightTarget);
//      }
            
      if (Math.abs(m_target - m_currentHeading) <= TOLERANCE)
      {
         SmartDashboard.putBoolean("Gyro turn on target", true);
         m_drive.setOpenLoopDrive();
         m_drive.setBrakeMode(true);
         m_drive.setThrottle(0);
         setFinished(true);
      }
      else
      {
         SmartDashboard.putBoolean("Gyro turn on target", false);
      }
      
      SmartDashboard.putNumber("Current heading", m_currentHeading);
   }

   private double getRotationsForDeltaAngle(int p_delta)
   {
      double ticks = p_delta * TICKS_PER_DEGREE;
      SmartDashboard.putNumber("p_delta", p_delta);
      return ticks / 4096;
   }
   
   private int getCompassHeading(int p_relative)
   {
      return (p_relative + 360) % 360;
   }
   
   public double modAngle(double initAngle) {
      double modAngle = initAngle; 
      while (modAngle > 180) { //should account for all angles
         modAngle -= 360;
      }
      while (modAngle < -180) {
         modAngle += 360;
      }

      
      return modAngle;
   }
   
   @Override
   public String toString()
   {
      return "Turn to heading step";
   }

}
