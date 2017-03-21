package org.wildstang.yearly.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.hardware.crio.inputs.WsCompassInput;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.Drive;

import edu.wpi.first.wpilibj.PIDController;

public class TurnToHeadingStep extends AutoStep
{
   private PIDController m_PidController = null;
   private WsCompassInput m_compass;
   private Drive m_drive;
   private boolean m_started = false;
   private double m_heading;
   
   public TurnToHeadingStep(double p_heading)
   {
      m_heading = p_heading;
   }
   
   @Override
   public void initialize()
   {
      m_compass = (WsCompassInput)Core.getInputManager().getInput(WSInputs.IMU.getName());
      m_drive = (Drive)Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName());
      m_PidController = new PIDController(0.03, 0, 0, m_compass, m_drive);
      
      m_PidController.setAbsoluteTolerance(2.0);
      m_PidController.setInputRange(0, 360);
      m_PidController.setContinuous();
      m_PidController.setSetpoint(m_heading);
      m_PidController.setOutputRange(-1.0, 1.0);
   }

   @Override
   public void update()
   {
      if (!m_started)
      {
         m_PidController.enable();
         m_started = true;
      }
      
      if (m_PidController.onTarget())
      {
         setFinished(true);
      }
   }

   @Override
   public String toString()
   {
      return "Turn to heading step";
   }

}
