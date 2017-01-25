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

public class Gate implements Subsystem
{
   // add variables here
   //private boolean 	TestSwitchSensor;
   //private double  	DrvJoystickRightY = 0.0;
   //private WsServo	Servo_0;
   //private WsServo	Servo_1;
   //private boolean 	DpadXLeft	= false;
   //private boolean 	DpadXRight	= false;
   //private double  	ServoPos_0	= 0.0;
   //private double  	ServoPos_1	= 0.0;

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
      return "Gate";
   }

   @Override
   public void init()
   {
      // 
      // TODO Auto-generated method stub
      // 
      //*********************************************************************************************
      // This method must exist even if it does nothing. It is called once and only once when the 
      // framework is started. It is used to setup local variables to initial values and to register
      // with the framework which inputs the framework should call the inputUpdate() method for.
      //*********************************************************************************************

      // Setup any local variables with intial values
      
      //TestSwitchSensor 	= false;
      //DrvJoystickRightY	= 0.0;
      //
      //// Register the sensors that this subsystem wants to be notified about
      //Core.getInputManager().getInput(WSInputs.TEST_SWITCH_SENSOR.getName()).addInputListener(this);
      //
      //Core.getInputManager().getInput(WSInputs.DRV_RIGHT_Y.getName()).addInputListener(this);
      //Core.getInputManager().getInput(WSInputs.DRV_DPAD_X_LEFT.getName()).addInputListener(this);
      //Core.getInputManager().getInput(WSInputs.DRV_DPAD_X_RIGHT.getName()).addInputListener(this);
      //
      //Servo_0 = (WsServo) Core.getOutputManager().getOutput(WSOutputs.TEST_SERVO_0.getName());
      //Servo_1 = (WsServo) Core.getOutputManager().getOutput(WSOutputs.TEST_SERVO_1.getName());

      //SmartDashboard.putNumber("ServoPos_1", ServoPos_1);
   }

   @Override
   public void inputUpdate(Input source)
   {
      // 
      // TODO Auto-generated method stub
      // 
      //*********************************************************************************************
      // This method is called any time one of the registered inputs has changed. The software in 
      // this method should do the following:
      // 
      // 1. Determine which registered input this method is being called with
      // 2. Read the updated value and store so it can be used in the update() method
      // 3. Update any variables based on the input variables
      //*********************************************************************************************

      // This section reads the input sensors and places them into local variables
      //if (source.getName().equals(WSInputs.TEST_SWITCH_SENSOR.getName()))
      //{
      //   TestSwitchSensor = ((DigitalInput) source).getValue();
      //}
      //
      //if (source.getName().equals(WSInputs.DRV_RIGHT_Y.getName()))
      //{
      //    // -1.0 <= TestJoystickLeft<= 1.0
      //    DrvJoystickRightY= ((AnalogInput) source).getValue();
      //}
      //
      //if (source.getName().equals(WSInputs.DRV_DPAD_X_LEFT.getName()))
      //{
      //    DpadXLeft= ((DigitalInput) source).getValue();
      //}
      //
      //if (source.getName().equals(WSInputs.DRV_DPAD_X_RIGHT.getName()))
      //{
      //    DpadXRight= ((DigitalInput) source).getValue();
      //}
   }

   @Override
   public void update()
   {
      // 
      // TODO Auto-generated method stub
      // 
      //*********************************************************************************************
      // This method is called after all of the registered updates have gone through the inputUpdate()
      // method. The software in this method should do the following:
      //
      // 1. Tell the framework what the updated output values should be set to.
      // 
//       ((DigitalOutput)Core.getOutputManager().getOutput(WSOutputs.TEST_LED.getName())).setValue(TestSwitchSensor);
       //if (DpadXLeft == true)
       //{
       //    ServoPos_0 = 90.0;
       //}
       //else if (DpadXRight == true)
       //{
       //    ServoPos_0 = 45.0;
       //}
       //else
       //    ServoPos_0 = 0.0;

       //SmartDashboard.putBoolean("DpadXLeft", DpadXLeft);
       //SmartDashboard.putBoolean("DpadXRight", DpadXRight);
       //SmartDashboard.putNumber("ServoPos_0", ServoPos_0);
       //
       //ServoPos_1 = SmartDashboard.getNumber("ServoPos_1", 90);
       //
       //Servo_0.setValue(ServoPos_0);
       //Servo_1.setValue(ServoPos_1);
   }
}
