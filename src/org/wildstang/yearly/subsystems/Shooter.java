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
import org.wildstang.yearly.robot.CANConstants;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSOutputs;
import org.wildstang.yearly.subsystems.shooter.Flywheel;
import org.wildstang.yearly.subsystems.shooter.Feed;
import org.wildstang.yearly.subsystems.shooter.Gate;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter implements Subsystem
{
   // add variables here
   // private boolean TestSwitchSensor;
   // private double DrvJoystickRightY = 0.0;
   // private WsServo Servo_0;
   // private WsServo Servo_1;
   // private boolean DpadXLeft = false;
   // private boolean DpadXRight = false;
   // private double ServoPos_0 = 0.0;
   // private double ServoPos_1 = 0.0;

   private CANTalon m_CANFlywheelLeft;
   private CANTalon m_CANFlywheelRight;

   private Flywheel m_leftFlywheel;
   private Flywheel m_rightFlywheel;

   @Override
   public void selfTest()
   {
      // DO NOT IMPLELMENT
   }

   @Override
   public String getName()
   {
      return "Shooter";
   }

   @Override
   public void init()
   {
      m_CANFlywheelLeft = new CANTalon(CANConstants.FLYWHEEL_LEFT_TALON_ID);
      m_CANFlywheelRight = new CANTalon(CANConstants.FLYWHEEL_RIGHT_TALON_ID);

      m_leftFlywheel = new Flywheel(m_CANFlywheelLeft);
      m_rightFlywheel = new Flywheel(m_CANFlywheelRight);

   }

   @Override
   public void inputUpdate(Input source)
   {
   }

   @Override
   public void update()
   {
      // if the shooter should turn on
      m_leftFlywheel.turnOn();
   }
}
