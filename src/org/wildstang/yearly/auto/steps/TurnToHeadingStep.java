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
   
   
   @Override
   public void initialize()
   {
      m_compass = (WsCompassInput)Core.getInputManager().getInput(WSInputs.IMU.getName());
      m_drive = (Drive)Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName());
      m_PidController = new PIDController(0.01, 0, 0, m_compass, m_drive);
   }

   @Override
   public void update()
   {
   }

   @Override
   public String toString()
   {
      return "Turn to heading step";
   }

}
