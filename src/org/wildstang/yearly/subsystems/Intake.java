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
   private boolean 	intakeOn = false;
   private double  	m_motorSpeed = 0.0;
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
	   
      Core.getInputManager().getInput(WSInputs.INTAKE_ON.getName()).addInputListener(this);
      m_motorSpeed = Core.getConfigManager().getConfig().getDouble(this.getClass().getName() + ".IntakeMotor", 0.5);
      
      m_intakeMotor = (WsVictor) Core.getOutputManager().getOutput(WSOutputs.INTAKE.getName());
      
   }

   @Override
   public void inputUpdate(Input source)
   {
	   if (source.getName().equals(WSInputs.INTAKE_ON.getName()))
	   {
		    if (((DigitalInput) source).getValue() == true)
			{
		    	intakeOn= !intakeOn;
			}
		}
   }

   @Override
   public void update()
   { 
      //*********************************************************************************************
      // This method is called after all of the registered updates have gone through the inputUpdate()
      // method. The software in this method should do the following:
      //
      // 1. Tell the framework what the updated output values should be set to.
	   
	   if (intakeOn)
	   {
		   m_intakeMotor.setValue(m_motorSpeed);
	   }
		   
       SmartDashboard.putBoolean("intakeOn", intakeOn);
       SmartDashboard.putNumber("motorSpeed", m_motorSpeed);
	   
   }
}
