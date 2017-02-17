package org.wildstang.yearly.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.crio.outputs.WsVictor;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSOutputs;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake implements Subsystem
{
   // add variables here
   private boolean m_intakeOn = false;
   private boolean m_intakeCurrent = false;
   private boolean m_intakePrev = false;

   private DigitalInput m_intakeButton;

   private double m_motorSpeed;
   private WsVictor m_intakeMotor;

   @Override
   public void selfTest()
   {
   }

   @Override
   public String getName()
   {
      return "Intake";
   }

   @Override
   public void init()
   {
      // Setup any local variables with intial values
      m_motorSpeed = Core.getConfigManager().getConfig().getDouble(this.getClass().getName()
            + ".IntakeMotor", 0.5);

      m_intakeMotor = (WsVictor) Core.getOutputManager().getOutput(WSOutputs.INTAKE.getName());

      m_intakeButton = (DigitalInput) Core.getInputManager().getInput(WSInputs.INTAKE_ON.getName());
      m_intakeButton.addInputListener(this);

   }

   @Override
   public void inputUpdate(Input p_source)
   {
      // Toggle for intake
      if (p_source == m_intakeButton)
      {
         m_intakeCurrent = m_intakeButton.getValue();

         if (m_intakeCurrent && !m_intakePrev)
         {
            m_intakeOn = !m_intakeOn;
         }
         m_intakePrev = m_intakeCurrent;
      }
   }

   @Override
   public void update()
   {

      if (m_intakeOn)
      {
         m_intakeMotor.setValue(m_motorSpeed);
      }
      else
      {
         m_intakeMotor.setValue(0);
      }

      SmartDashboard.putBoolean("intakeOn", m_intakeOn);
      SmartDashboard.putNumber("intake motorSpeed from WS", m_motorSpeed);
      SmartDashboard.putNumber("intake motorSpeed now", m_intakeMotor.getValue());
   }
}
