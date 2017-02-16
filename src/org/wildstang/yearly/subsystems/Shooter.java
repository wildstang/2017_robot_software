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

   // For the toggle
   private boolean m_flywheelOn = false;
   private boolean m_shooterCurrent;
   private boolean m_shooterPrev;

   // For checking if the gates should open when flywheels are up to speed
   private boolean readyToShootLeft = false;
   private boolean readyToShootRight = false;
   // Can override the flywheel speed checker and open gates anyway
   private boolean m_shootOverride = false;
   // Limits for range flywheels should be at before opening gates
   private double m_targetSpeed;
   private double m_lowLimitSpeed;
   private double m_highLimitSpeed;

   // Gate
   private WsSolenoid m_gateSolenoid;

   private Gate m_gate;

   // For toggling
   private boolean m_gateOpen = false;
   private boolean m_gateCurrent;
   private boolean m_gatePrev;

   // Feeds
   private WsVictor m_leftFeedVictor;
   private WsVictor m_rightFeedVictor;

   private Feed m_leftFeed;
   private Feed m_rightFeed;

   private double m_leftJoyAxis;
   private double m_rightJoyAxis;
   // Deadband so nothing happens if joystick is bumped on accident
   private double m_feedDeadBand;
   private double m_feedSpeed;

   // Enumeration variable for SHOOT, REVERSE, and STOP
   private FeedDirection m_leftFeedDirection;
   private FeedDirection m_rightFeedDirection;

   // PDP for checking if Feeds are jammed
   private PowerDistributionPanel pdp;
   double m_rightFeedCurrent;
   double m_leftFeedCurrent;

   // Inputs
   private DigitalInput m_flywheelButton;

   private DigitalInput m_gateButton;

   private DigitalInput m_overrideButton;

   private AnalogInput m_leftBeltJoystick;
   private AnalogInput m_rightBeltJoystick;

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
      // CAN talons
      m_CANFlywheelLeft = new CANTalon(CANConstants.FLYWHEEL_LEFT_TALON_ID);
      m_CANFlywheelRight = new CANTalon(CANConstants.FLYWHEEL_RIGHT_TALON_ID);

      // Reads values from Ws Config, defaults are nonsensical for testing
      m_targetSpeed = Core.getConfigManager().getConfig().getDouble(this.getClass().getName()
            + ".flywheelSpeed", 10.0);
      m_lowLimitSpeed = Core.getConfigManager().getConfig().getDouble(this.getClass().getName()
            + ".lowLimitSpeed", 5.0);
      m_highLimitSpeed = Core.getConfigManager().getConfig().getDouble(this.getClass().getName()
            + ".highLimitSpeed", 15.0);
      m_feedSpeed = Core.getConfigManager().getConfig().getDouble(this.getClass().getName()
            + ".feedSpeed", 0.4);
      m_feedDeadBand = Core.getConfigManager().getConfig().getDouble(this.getClass().getName()
            + ".feedDeadBand", 0.1);

      m_leftFlywheel = new Flywheel(m_CANFlywheelLeft, m_targetSpeed);
      m_rightFlywheel = new Flywheel(m_CANFlywheelRight, m_targetSpeed);

      // Gates
      m_gateSolenoid = (WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.GATE.getName());

      m_gate = new Gate(m_gateSolenoid);

      // Feeds
      m_leftFeedVictor = (WsVictor) Core.getOutputManager().getOutput(WSOutputs.FEEDER_LEFT.getName());
      m_rightFeedVictor = (WsVictor) Core.getOutputManager().getOutput(WSOutputs.FEEDER_RIGHT.getName());

      // inverts the left, may change
      m_leftFeed = new Feed(m_leftFeedVictor, m_feedSpeed);
      m_rightFeed = new Feed(m_rightFeedVictor, m_feedSpeed);

      m_leftFeedDirection = FeedDirection.STOP;
      m_rightFeedDirection = FeedDirection.STOP;

      // PDP
      pdp = new PowerDistributionPanel();
      m_leftFeedCurrent = pdp.getCurrent(11);
      m_rightFeedCurrent = pdp.getCurrent(4);

      // Input Listeners
      m_flywheelButton = (DigitalInput) Core.getInputManager().getInput(WSInputs.FLYWHEEL.getName());
      m_flywheelButton.addInputListener(this);

      m_gateButton = (DigitalInput) Core.getInputManager().getInput(WSInputs.GATE_LEFT.getName());
      m_gateButton.addInputListener(this);

      m_leftBeltJoystick = (AnalogInput) Core.getInputManager().getInput(WSInputs.FEEDER_LEFT.getName());
      m_leftBeltJoystick.addInputListener(this);
      m_rightBeltJoystick = (AnalogInput) Core.getInputManager().getInput(WSInputs.FEEDER_RIGHT.getName());
      m_rightBeltJoystick.addInputListener(this);

      m_overrideButton = (DigitalInput) Core.getInputManager().getInput(WSInputs.OVERRIDE.getName());
      m_overrideButton.addInputListener(this);

      // If we get a swtich for balls waiting
      // leftBallReadySwitch = (DigitalInput)
      // Core.getInputManager().getInput(WSInputs.BALLS_WAITING_LEFT.getName());
      // leftBallReadySwitch.addInputListener(this);
      // rightBallReadySwitch = (DigitalInput)
      // Core.getInputManager().getInput(WSInputs.BALLS_WAITING_RIGHT.getName());
      // rightBallReadySwitch.addInputListener(this);

   }

   @Override
   public void inputUpdate(Input p_source)
   {
      if (p_source == m_flywheelButton)
      {
         m_shooterCurrent = m_flywheelButton.getValue();
         // Toggle for flywheels
         if (m_shooterCurrent && !m_shooterPrev)
         {
            m_flywheelOn = !m_flywheelOn;
         }
         m_shooterPrev = m_shooterCurrent;
      }
      else if (p_source == m_gateButton)
      {
         m_gateCurrent = m_gateButton.getValue();
         // Toggle for gate left
         if (m_gateCurrent && !m_gatePrev)
         {
            m_gateOpen = !m_gateOpen;
         }
         m_gatePrev = m_gateCurrent;
      }
      // Sets feed enumaration based on joystick
      else if (p_source == m_leftBeltJoystick)
      {
         m_leftJoyAxis = m_leftBeltJoystick.getValue();
         if (m_leftJoyAxis > m_feedDeadBand)
         {
            m_leftFeedDirection = FeedDirection.SHOOT;
         }
         else if (m_leftJoyAxis < -m_feedDeadBand)
         {
            m_leftFeedDirection = FeedDirection.REVERSE;
         }
         else
         {
            m_leftFeedDirection = FeedDirection.STOP;
         }
      }
      else if (p_source == m_rightBeltJoystick)
      {
         m_rightJoyAxis = m_rightBeltJoystick.getValue();
         if (m_rightJoyAxis > m_feedDeadBand)
         {
            m_rightFeedDirection = FeedDirection.SHOOT;
         }
         else if (m_rightJoyAxis < -m_feedDeadBand)
         {
            m_rightFeedDirection = FeedDirection.REVERSE;
         }
         else
         {
            m_rightFeedDirection = FeedDirection.STOP;
         }
      }
      else if (p_source == m_overrideButton)
      {
         m_shootOverride = !m_shootOverride;
      }

   }

   @Override
   public void update()
   {
      updateFlywheels();
      updateGates();
      updateFeed();

      updateDashboardData();
   }

   // Flywheel stuff
   // Turns on the flywheels w/out buttons for auto
   public void turnFlywheelOn()
   {
      m_flywheelOn = true;
   }

   // Turns off the flywheels w/out buttons for auto
   public void turnFlywheelOff()
   {
      m_flywheelOn = false;
   }

   // Updates the state of the flywheels based off of the toggle switch and
   // button
   public void updateFlywheels()
   {

      if (m_flywheelOn)
      {
         m_leftFlywheel.turnOn();
         m_rightFlywheel.turnOn();
      }
      else if (!m_flywheelOn)
      {
         m_leftFlywheel.turnOff();
         m_rightFlywheel.turnOff();
      }

   }

   // Gate Opens
   public void openBothGate()
   {
      m_gateOpen = true;
   }

   public void closeBothGate()
   {
      m_gateOpen = false;
   }

   public boolean isLeftReadyToShoot()
   {
      return isReadyToShoot(m_CANFlywheelLeft);
   }

   public boolean isRightReadyToShoot()
   {
      return isReadyToShoot(m_CANFlywheelRight);
   }

   public boolean isReadyToShoot(CANTalon p_talon)
   {
      double speed = p_talon.getSpeed();

      return (speed >= m_lowLimitSpeed && speed <= m_highLimitSpeed);
   }

   public void updateGates()
   {
      // Tests to see if the left and right flywheel is up to speed and ready to
      // shoot a ball.
      // Sets a conditional toggle to true if that flywheel is ready.
      // Can be overriden so gates can open even if flywheel isn't up to speed

      // LEFT SIDE
      readyToShootLeft = isLeftReadyToShoot(); // || m_shootOverride;
      // RIGHT SIDE
      readyToShootRight = isRightReadyToShoot(); // || m_shootOverride;

      // Opens the gate if the flywheel is up to speed and the button is pressed
      // LEFT SIDE
      if (m_gateOpen && readyToShootLeft)
      {
         m_gate.openGate();
      }
      else
      {
         m_gate.closeGate();
      }

   }

   // Feed Stuff
   // Turns on the belts w/out buttons for auto
   public void turnFeedOn()
   {
      m_leftFeedDirection = FeedDirection.SHOOT;
      m_rightFeedDirection = FeedDirection.SHOOT;
   }

   // Turns off the belts w/out buttons for auto
   public void turnFeedOff()
   {
      m_leftFeedDirection = FeedDirection.STOP;
      m_rightFeedDirection = FeedDirection.STOP;
   }

   public boolean checkLeftFeedJammed()
   {
      return m_leftFeed.isJammed(m_leftFeedCurrent);
   }

   public boolean checkRightFeedJammed()
   {
      return m_rightFeed.isJammed(m_rightFeedCurrent);
   }

   public void updateFeed()
   {
      // Determines whether or not the feeder is jammed and, if so,
      // displays "Is Jammed" on the dash

      // LEFT SIDE
      if (!checkLeftFeedJammed())
      {
         SmartDashboard.putBoolean("Left is Jammed", false);
         runFeedBelt(m_leftFeed, m_leftFeedDirection);
      }
      else
      {
         
         SmartDashboard.putBoolean("Left is Jammed", true);
         m_leftFeed.stop();
      }

      // RIGHT SIDE
      if (!checkRightFeedJammed())
      {
         SmartDashboard.putBoolean("Right is Jammed", false);
         runFeedBelt(m_rightFeed, m_rightFeedDirection);
      }
      else
      {
         SmartDashboard.putBoolean("Right is Jammed", true);
         m_rightFeed.stop();
      }

   }

   private void runFeedBelt(Feed p_feed, FeedDirection p_direction)
   {
      switch (p_direction)
      {
         case SHOOT:
            p_feed.runForward();
            break;
         case REVERSE:
            p_feed.runBackwards();
            break;
         case STOP:
            p_feed.stop();
            break;
      }
   }

   enum FeedDirection
   {
      SHOOT, REVERSE, STOP;
   }

   // Shows speeds and states for testing
   public void updateDashboardData()
   {
      SmartDashboard.putBoolean("left flywheel is running", m_leftFlywheel.isRunning());
      SmartDashboard.putBoolean("right flywheel is running", m_rightFlywheel.isRunning());

      SmartDashboard.putNumber("left flywheel speed", m_leftFlywheel.getSpeed());
      SmartDashboard.putNumber("right flywheel speed", m_rightFlywheel.getSpeed());

      SmartDashboard.putBoolean("Gates is open", m_gate.isOpen());

      SmartDashboard.putNumber("left feed speed", m_leftFeed.getSpeed());
      SmartDashboard.putNumber("right feed speed", m_rightFeed.getSpeed());

      // WS config
      SmartDashboard.putNumber("flywheel target speed", m_targetSpeed);
      SmartDashboard.putNumber("flywheel low limit speed", m_lowLimitSpeed);
      SmartDashboard.putNumber("flywheel high limit speed", m_highLimitSpeed);
      SmartDashboard.putNumber("feed speed constant", m_feedSpeed);
      SmartDashboard.putNumber("feed dead band", m_feedDeadBand);
   }

}
