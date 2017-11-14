package org.wildstang.yearly.subsystems.gyro;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.pid.input.IPidInput;
import org.wildstang.framework.pid.output.IPidOutput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.crio.inputs.WsAnalogGyro;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.Drive;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GyroController implements IPidInput, IPidOutput
{
   WsAnalogGyro m_gyro = (WsAnalogGyro) Core.getInputManager().getInput(WSInputs.GYRO.getName());
   Drive m_drive = (Drive) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName());

   @Override
   public void pidWrite(double output)
   {
      // TODO Auto-generated method stub
      SmartDashboard.putNumber("PID Gyro Value", output);
      m_drive.setPIDHeading(output);
   }

   @Override
   public double pidRead()
   {
      // TODO Auto-generated method stub
      SmartDashboard.putNumber("Gyro", m_gyro.readRawValue());

      return m_gyro.readRawValue();
   }

}
