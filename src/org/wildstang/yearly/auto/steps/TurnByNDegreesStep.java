package org.wildstang.yearly.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.hardware.crio.inputs.WsCompassInput;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.Drive;

import edu.wpi.first.wpilibj.PIDController;

public class TurnByNDegreesStep extends AutoStep
{
   private PIDController m_PidController = null;
   private WsCompassInput m_compass;
   private Drive m_drive;
   private boolean m_started = false;
   private double m_heading;
   private double m_target;
   private boolean m_firstrun = true;
   
   public TurnByNDegreesStep(double p_heading)
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
      m_PidController.setOutputRange(-1.0, 1.0);
      
      
   }

   @Override
   public void update()
   {
      m_drive.setHighGear(false);
      m_drive.setQuickTurn(true);
      
      if (m_firstrun)
      {
         double currentHeading = m_compass.getValue()[0] * 2;
         m_target = (currentHeading + m_heading) % 360;
         m_PidController.setSetpoint(m_target);
         m_firstrun = false;
      }
      
      if (!m_started)
      {
         m_PidController.enable();
         m_started = true;
      }
      
      if (m_PidController.onTarget())
      {
         m_PidController.disable();
         setFinished(true);
      }
   }

   @Override
   public String toString()
   {
      return "Turn to heading step";
   }

}