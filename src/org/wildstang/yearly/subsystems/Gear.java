package org.wildstang.yearly.subsystems;

import org.wildstang.framework.config.Config;
/* Please edit!!!
 *
 * This class is an example of a subsystem. For the moment, it reads a digital sensor 
 * and drives a digital output which turns on or off an LED.
 *
 * Continue...
 */
//expand this and edit if trouble with Ws
import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.io.outputs.AnalogOutput;
import org.wildstang.framework.io.outputs.DigitalOutput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoid;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoidState;
import org.wildstang.hardware.crio.outputs.WsDigitalOutput;
import org.wildstang.hardware.crio.outputs.WsServo;
import org.wildstang.hardware.crio.outputs.WsSolenoid;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSOutputs;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Gear implements Subsystem
{
   // add variables here
   private boolean 	buttonPressed;
   private boolean 	gearPistonOn;
   private boolean sensorPressed;
   private boolean tiltButton;
   private boolean holdButton;
   private boolean m_holdGear;
   private boolean m_tiltGear;

   @Override
   public void selfTest()
   {
      //*********************************************************************************************
      // This method must exist but for the time being, leave it empty. 
      //*********************************************************************************************
   }

   @Override
   public String getName()
   { 
      //*********************************************************************************************
      // This method should return the name of the subsystem.
      //*********************************************************************************************
      return "Gear";
   }

   @Override
   public void init()
   {
      //*********************************************************************************************
      // This method must exist even if it does nothing. It is called once and only once when the 
      // framework is started. It is used to setup local variables to initial values and to register
      // with the framework which inputs the framework should call the inputUpdate() method for.
      //*********************************************************************************************

      // Setup any local variables with intial values
      
	   // Register the sensors that this subsystem wants to be notified about
      Core.getInputManager().getInput(WSInputs.GEAR.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.GEAR_IN_POSITION.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.GEAR_WALL_BUTTON.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.GEAR_HOLD_BUTTON.getName()).addInputListener(this);
   }

   @Override
   public void inputUpdate(Input source)
   { 
      //*********************************************************************************************
      // This method is called any time one of the registered inputs has changed. The software in 
      // this method should do the following:
      // 
      // 1. Determine which registered input this method is being called with
      // 2. Read the updated value and store so it can be used in the update() method
      // 3. Update any variables based on the input variables
      //*********************************************************************************************

      // This section reads the input sensors and places them into local variables
      
      if (source.getName().equals(WSInputs.GEAR_WALL_BUTTON.getName()))
      {
    	  tiltButton = ((DigitalInput) source).getValue();
    	  
    	  TiltGearSet(tiltButton);
      }
      
      if (source.getName().equals(WSInputs.GEAR_HOLD_BUTTON.getName()))
      {
    	  holdButton = ((DigitalInput) source).getValue();
    	  
    	  HoldGearSet(holdButton);
      }
   }

   @Override
   public void update()
   {
       
      //*********************************************************************************************
      // This method is called after all of the registered updates have gone through the inputUpdate()
      // method. The software in this method should do the following:
      //
      // Tell the framework what the updated output values should be set to.
      // 
       ((WsSolenoid)Core.getOutputManager().getOutput(WSOutputs.GEAR_HOLD_SOL.getName())).setValue(m_holdGear);
	   ((WsSolenoid)Core.getOutputManager().getOutput(WSOutputs.GEAR_WALL_SOL.getName())).setValue(m_tiltGear);
   }
   
   public void HoldGearSet(boolean p_holdGear)
   {
      
	  m_holdGear = p_holdGear;
      
   }
   
   public void TiltGearSet(boolean p_tiltGear)
   {
     
	   m_tiltGear = p_tiltGear;

   }
}
