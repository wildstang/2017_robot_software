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
import org.wildstang.hardware.crio.outputs.WsSolenoid;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoid;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoidState;
import org.wildstang.hardware.crio.outputs.WsDigitalOutput;
import org.wildstang.hardware.crio.outputs.WsVictor;
import org.wildstang.hardware.crio.outputs.WsServo;
import org.wildstang.hardware.crio.outputs.WsSolenoid;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSOutputs;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber implements Subsystem
{
   // add variables here
   private boolean DPadUp;
   
   //private boolean DPadDown;
   
   private double motorspeed = .8;
   
   private boolean running;
   
  // private boolean pressedyet;
   
  // private boolean brakestate;
   
   private DigitalInput upbutton;
   
 //  private DigitalInput downbutton;
   
   // private boolean canrun;
   
   //private boolean winch_running;
   
   //Outputs
  // private WsSolenoid w_brake;
   
   private WsVictor w_motor;
   
   
   
   
   

   @Override
   public void selfTest()
   {
      // 
      // TODO Auto-generated method stub
      // 
      //*********************************************************************************************
      // This method must exist but for the time being, leave it empty. 
      //*********************************************************************************************
   }

   @Override
   public String getName()
   {
      // 
      // TODO Auto-generated method stub
      // 
      //*********************************************************************************************
      // This method should return the name of the subsystem.
      //*********************************************************************************************
      return "Climber";
   }

   @Override
   public void init()
   {
      
      DPadUp = false; 
     
      motorspeed = .8; //Speed for motor
 
      running = false;

      //Output
      
      w_motor = (WsVictor) Core.getOutputManager().getOutput(WSOutputs.WINCH.getName());
      
      //Inputs
      
      upbutton = (DigitalInput) Core.getInputManager().getInput(WSInputs.CLIMBER_UP.getName());
      upbutton.addInputListener(this);
      
   }

   @Override
   public void inputUpdate(Input source)
   {
      if(source == upbutton)
      {
         DPadUp = upbutton.getValue();
      }
      
   }

   @Override
   public void update()
   {
      
      if (DPadUp == true) //Checks if the dpad button is being pushed
      {
         running = true; // for Smart Dashboard
         
         w_motor.setValue(motorspeed); //Sets the motor speed to .8
      }
      else if (DPadUp == false) // Checks to see if the button is not being pushed
      {
         running = false; // For smart dashboard
         
         w_motor.setValue(0); // When not pushed the motor speed is 0.
      }
      
      SmartDashboard.putBoolean("Winch Running", running);
      
      
      
      
      
      
      
      
      
      
  
   }
}
