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
import org.wildstang.hardware.crio.outputs.WsVictor;
import org.wildstang.yearly.robot.CANConstants;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSOutputs;
import org.wildstang.yearly.subsystems.shooter.Flywheel;
import org.wildstang.yearly.subsystems.shooter.Feed;
import org.wildstang.yearly.subsystems.shooter.Gate;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter implements Subsystem
{
   // Flywheels
   private CANTalon m_CANFlywheelLeft;
   private CANTalon m_CANFlywheelRight;

   private Flywheel m_leftFlywheel;
   private Flywheel m_rightFlywheel;

   // Gates
   private WsSolenoid m_leftGateSolenoid;
   private WsSolenoid m_rightGateSolenoid;

   private Gate m_leftGate;
   private Gate m_rightGate;

   // Feeds
   private WsVictor m_leftFeedVictor;
   private WsVictor m_rightFeedVictor;

   private Feed m_leftFeed;
   private Feed m_rightFeed;

   // PDP
   private PowerDistributionPanel pdp;
   double rightFeedCurrent;
   double leftFeedCurrent;

   // Inputs
   private DigitalInput leftBallReadySwitch;
   private DigitalInput rightBallReadySwitch;

   private DigitalInput leftFlywheelButton;
   private DigitalInput rightFlywheelButton;

   private DigitalInput leftGateButton;
   private DigitalInput rightGateButton;
   
   private AnalogInput leftBeltJoystick;
   private AnalogInput rightBeltJoystick;

   // Variables

   private boolean leftFlywheelToggleOn = false;
   private boolean rightFlywheelToggleOn = false;  
   
   private boolean leftBallReady = false;
   private boolean rightBallReady = false;

   private boolean readyToShootLeft = false;
   private boolean readyToShootRight = false;

   private double targetSpeed;
   private double lowLimitSpeed;
   private double highLimitSpeed;
   
   private boolean leftGateToggleOpen = false;
   private boolean rightGateToggleOpen = false;
   
   private double leftJoyAxis;
   private double rightJoyAxis;


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
      // Flywheels
      m_CANFlywheelLeft = new CANTalon(CANConstants.FLYWHEEL_LEFT_TALON_ID);
      m_CANFlywheelRight = new CANTalon(CANConstants.FLYWHEEL_RIGHT_TALON_ID);

      m_leftFlywheel = new Flywheel(m_CANFlywheelLeft);
      m_rightFlywheel = new Flywheel(m_CANFlywheelRight);

      // Gates
      m_leftGateSolenoid = (WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.GATE_LEFT.getName());
      m_rightGateSolenoid = (WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.GATE_RIGHT.getName());

      m_leftGate = new Gate(m_leftGateSolenoid);
      m_rightGate = new Gate(m_rightGateSolenoid);

      // Feeds
      m_leftFeedVictor = (WsVictor) Core.getOutputManager().getOutput(WSOutputs.FEEDER_LEFT.getName());
      m_rightFeedVictor = (WsVictor) Core.getOutputManager().getOutput(WSOutputs.FEEDER_RIGHT.getName());

      m_leftFeed = new Feed(m_leftFeedVictor);
      m_rightFeed = new Feed(m_rightFeedVictor);

      // PDP
      pdp = new PowerDistributionPanel();
      leftFeedCurrent = pdp.getCurrent(8);
      rightFeedCurrent = pdp.getCurrent(9);

      // Input Listeners
      leftBallReadySwitch = (DigitalInput) Core.getInputManager().getInput(WSInputs.BALLS_WAITING_LEFT.getName());
      leftBallReadySwitch.addInputListener(this);
      rightBallReadySwitch = (DigitalInput) Core.getInputManager().getInput(WSInputs.BALLS_WAITING_RIGHT.getName());
      rightBallReadySwitch.addInputListener(this);

      leftFlywheelButton = (DigitalInput) Core.getInputManager().getInput(WSInputs.FLYWHEEL_LEFT.getName());
      leftFlywheelButton.addInputListener(this);
      rightFlywheelButton = (DigitalInput) Core.getInputManager().getInput(WSInputs.FLYWHEEL_RIGHT.getName());
      rightFlywheelButton.addInputListener(this);

      leftGateButton = (DigitalInput) Core.getInputManager().getInput(WSInputs.GATE_LEFT.getName());
      leftGateButton.addInputListener(this);
      rightGateButton = (DigitalInput) Core.getInputManager().getInput(WSInputs.GATE_RIGHT.getName());
      rightGateButton.addInputListener(this);
      
      leftBeltJoystick = (AnalogInput) Core.getInputManager().getInput(WSInputs.FLYWHEEL_LEFT.getName());
      leftBeltJoystick.addInputListener(this);
      rightBeltJoystick = (AnalogInput) Core.getInputManager().getInput(WSInputs.FLYWHEEL_RIGHT.getName());
      rightBeltJoystick.addInputListener(this);
      
      
   }

   @Override
   public void inputUpdate(Input source)
   {
      leftJoyAxis = leftBeltJoystick.getValue();
      rightJoyAxis = rightBeltJoystick.getValue();
      
      // Ball in waiting switches
      if (source == leftBallReadySwitch)
      {
         leftBallReady = m_leftFeed.isBallReady(leftBallReadySwitch.getValue());
      }
      if (source == rightBallReadySwitch)
      {
         rightBallReady = m_rightFeed.isBallReady(rightBallReadySwitch.getValue());
      }

      // Toggle for flywheels
      if (source == leftFlywheelButton)
      {
         leftFlywheelToggleOn = !leftFlywheelToggleOn;
      }
      if (source == rightFlywheelButton)
      {
         rightFlywheelToggleOn = !rightFlywheelToggleOn;
      }

      // Toggle for gates
      if (source == leftGateButton)
      {
         leftGateToggleOpen = !leftGateToggleOpen;
      }
      if (source == rightGateButton)
      {
         rightGateToggleOpen = !rightGateToggleOpen;
      }
   }

   @Override
   public void update()
   {
      updateFlywheels();
      updateGates();
      updateFeed();
   }

   public void updateFlywheels()
   {
      // Left
      if (leftFlywheelToggleOn)
      {
         m_leftFlywheel.setSpeed(targetSpeed);
         
      }
      else if (!leftFlywheelToggleOn)
      {
         m_leftFlywheel.turnOff();
      }

      // Right
      if (rightFlywheelToggleOn)
      {
         m_rightFlywheel.setSpeed(targetSpeed);
      }
      else if (!rightFlywheelToggleOn)
      {
         m_rightFlywheel.turnOff();
      }
   }

   // TODO:
   // make sure flywheels are on when gates open
   public void updateGates()
   {
      // Tests to see if the left and right flywheel is up to speed and ready to
      // shoot a ball.
      // Sets a conditional toggle to true if that flywheel is ready.

      // Left Side
      if (m_leftFlywheel.getSpeed() <= highLimitSpeed
            && m_leftFlywheel.getSpeed() >= lowLimitSpeed)
      {
         readyToShootLeft = true;
      }
      else
      {
         readyToShootLeft = false;
      }

      // Right Side
      if (m_rightFlywheel.getSpeed() <= highLimitSpeed
            && m_rightFlywheel.getSpeed() >= lowLimitSpeed)
      {
         readyToShootRight = true;
      }
      else
      {
         readyToShootRight = false;
      }

      // Opens the gate if the flywheel is up to speed and the button is pressed

      // Left Side
      if (leftGateToggleOpen && readyToShootLeft)
      {
         m_leftGate.openGate();
      }
      else
      {
         m_leftGate.closeGate();
      }

      // Right Side
      if (rightGateToggleOpen && readyToShootRight)
      {
         m_rightGate.openGate();
      }
      else
      {
         m_rightGate.closeGate();
      }
   }

   public void updateFeed()
   {
      // Determines whether or not the feeder is jammed and, if so,
      // displays "Is Jammed" on the dash
      
      // Left
      if (m_leftFeed.isJammed(leftFeedCurrent))
      {
         SmartDashboard.putBoolean("Left is Jammed", true);
      }
      
      // Right
      if (m_rightFeed.isJammed(rightFeedCurrent))
      {
         SmartDashboard.putBoolean("Right is Jammed", true);
      }
      
      // Setting left and right talon speed based off of analog joystick input
      
      //Left
      if (leftJoyAxis > 0)
      {
         m_leftFeed.runForward();
      }
      else if (leftJoyAxis < 0)
      {
         m_leftFeed.runBackwards();
      }
      else 
      {
         m_leftFeed.stop();
      }
      
    //Right
      if (rightJoyAxis > 0)
      {
         m_rightFeed.runForward();
      }
      else if (rightJoyAxis < 0)
      {
         m_rightFeed.runBackwards();
      }
      else 
      {
         m_rightFeed.stop();
      }

   }

}
