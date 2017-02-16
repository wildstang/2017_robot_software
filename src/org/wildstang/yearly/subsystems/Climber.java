package org.wildstang.yearly.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.crio.outputs.WsVictor;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSOutputs;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber implements Subsystem
{
   // add variables here
   private boolean DPadUp;
   private double motorspeed = .8;
   private boolean running;
   private DigitalInput upbutton;
   private WsVictor w_motor;
   

   @Override
   public void selfTest()
   {
   }

   @Override
   public String getName()
   {
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
      
      if (DPadUp) // Checks if the dpad button is being pushed
      {
         running = true; // for Smart Dashboard
         w_motor.setValue(motorspeed); //Sets the motor speed to .8
      }
      else // Checks to see if the button is not being pushed
      {
         running = false; // For smart dashboard
         w_motor.setValue(0); // When not pushed the motor speed is 0.
      }
      
      SmartDashboard.putBoolean("Winch Running", running);
      
   }
}
