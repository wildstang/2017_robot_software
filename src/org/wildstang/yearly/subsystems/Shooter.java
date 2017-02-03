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

   private boolean flywheelToggle = false;
   private boolean ShooterNow;
   private boolean ShooterPrev;

   private boolean readyToShootLeft = false;
   private boolean readyToShootRight = false;

   private double targetSpeed;
   private double lowLimitSpeed;
   private double highLimitSpeed;

   // Gates
   private WsSolenoid m_leftGateSolenoid;
   private WsSolenoid m_rightGateSolenoid;

   private Gate m_leftGate;
   private Gate m_rightGate;

   private boolean leftGateToggleOpen = false;
   private boolean rightGateToggleOpen = false;

   private boolean leftGateOpen;
   private boolean leftGateNow;
   private boolean leftGatePrev;

   private boolean rightGateOpen;
   private boolean rightGateNow;
   private boolean rightGatePrev;

   // Feeds
   private WsVictor m_leftFeedVictor;
   private WsVictor m_rightFeedVictor;

   private Feed m_leftFeed;
   private Feed m_rightFeed;

   private double leftJoyAxis;
   private double rightJoyAxis;

   // PDP
   private PowerDistributionPanel pdp;
   double rightFeedCurrent;
   double leftFeedCurrent;

   // Inputs
   private DigitalInput flywheelButton;

   private DigitalInput leftGateButton;
   private DigitalInput rightGateButton;

   private AnalogInput leftBeltJoystick;
   private AnalogInput rightBeltJoystick;

   // Variables

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
      m_leftGate = new Gate(m_leftGateSolenoid);
      m_rightGate = new Gate(m_rightGateSolenoid);

      m_leftGateSolenoid = (WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.GATE_LEFT.getName());
      m_rightGateSolenoid = (WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.GATE_RIGHT.getName());

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

      flywheelButton = (DigitalInput) Core.getInputManager().getInput(WSInputs.FLYWHEEL.getName());
      flywheelButton.addInputListener(this);

      leftGateButton = (DigitalInput) Core.getInputManager().getInput(WSInputs.GATE_LEFT.getName());
      leftGateButton.addInputListener(this);
      rightGateButton = (DigitalInput) Core.getInputManager().getInput(WSInputs.GATE_RIGHT.getName());
      rightGateButton.addInputListener(this);

      leftBeltJoystick = (AnalogInput) Core.getInputManager().getInput(WSInputs.FEEDER_LEFT.getName());
      leftBeltJoystick.addInputListener(this);
      rightBeltJoystick = (AnalogInput) Core.getInputManager().getInput(WSInputs.FEEDER_RIGHT.getName());
      rightBeltJoystick.addInputListener(this);

      // leftBallReadySwitch = (DigitalInput)
      // Core.getInputManager().getInput(WSInputs.BALLS_WAITING_LEFT.getName());
      // leftBallReadySwitch.addInputListener(this);
      // rightBallReadySwitch = (DigitalInput)
      // Core.getInputManager().getInput(WSInputs.BALLS_WAITING_RIGHT.getName());
      // rightBallReadySwitch.addInputListener(this);

   }

   @Override
   public void inputUpdate(Input source)
   {
      ShooterNow = flywheelButton.getValue();

      leftGateNow = leftGateButton.getValue();
      rightGateNow = rightGateButton.getValue();

      leftJoyAxis = leftBeltJoystick.getValue();
      rightJoyAxis = rightBeltJoystick.getValue();

      // Toggle for flywheels
      if (ShooterNow && !ShooterPrev)
      {
         flywheelToggle = !flywheelToggle;
         ShooterPrev = ShooterNow;
      }

      // Toggle for gates
      else if (leftGateNow && !leftGatePrev)
      {
         leftGateOpen = !leftGateOpen;
         leftGatePrev = leftGateNow;
      }

      else if (rightGateNow && !rightGatePrev)
      {
         rightGateOpen = !rightGateOpen;
         rightGatePrev = rightGateNow;
      }

   }

   @Override
   public void update()
   {
      updateFlywheels();
      updateGates();
      updateFeed();
   }

   // Turns on the flywheels w/out buttons for auto
   public void turnFlywheelOn()
   {
      // Replaced this with whats below
      // flywheelToggleOn = true;

      m_leftFlywheel.setSpeed(targetSpeed);
      m_rightFlywheel.setSpeed(-targetSpeed);
   }

   // Turns off the flywheels w/out buttons for auto
   public void turnFlywheelOff()
   {
      // flywheelToggleOn = false;

      m_leftFlywheel.setSpeed(0);
      m_rightFlywheel.setSpeed(0);
   }

   // Updates the state of the flywheels based off of the toggle switch and
   // button
   public void updateFlywheels()
   {
      // BENO: We currently have a variable for the speed we want in both
      // the flywheel class, m_speed, and in shooter class, targetSpeed.
      // Should one of these be removed or should the turn on function be
      // scrapped altogether?
      // turnOn and turnOff are now in two places

      if (flywheelToggle)
      {
         // note: right must run in reverse with left due to motor positioning

         m_leftFlywheel.setSpeed(targetSpeed);
         m_rightFlywheel.setSpeed(-targetSpeed);

      }
      else if (!flywheelToggle)
      {
         // Should this be a setSpeed 0 instead?
         m_leftFlywheel.turnOff();
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

      // BENO: Should these all be else if's as well? I say no because if left
      // side
      // is ready to shoot, it will short circuit right side. But does that
      // matter?

      // LEFT SIDE
      if (m_leftFlywheel.getSpeed() <= highLimitSpeed
            && m_leftFlywheel.getSpeed() >= lowLimitSpeed)
      {
         readyToShootLeft = true;
      }
      else
      {
         readyToShootLeft = false;
      }

      // RIGHT SIDE
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

      // LEFT SIDE
      if (leftGateToggleOpen && readyToShootLeft)
      {
         m_leftGate.openGate();
      }
      else
      {
         m_leftGate.closeGate();
      }

      // RIGHT SIDE
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

      // LEFT SIDE
      if (m_leftFeed.isJammed(leftFeedCurrent))
      {
         SmartDashboard.putBoolean("Left is Jammed", true);
      }

      // RIGHT SIDE
      if (m_rightFeed.isJammed(rightFeedCurrent))
      {
         SmartDashboard.putBoolean("Right is Jammed", true);
      }

      // Setting left and right talon speed based off of analog joystick input

      // LEFT SIDE
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

      // RIGHT SIDE
      // note: right must run in reverse with left due to motor positioning
      if (rightJoyAxis > 0)
      {
         m_rightFeed.runBackwards();
      }
      else if (rightJoyAxis < 0)
      {
         m_rightFeed.runForward();
      }
      else
      {
         m_rightFeed.stop();
      }

   }

}