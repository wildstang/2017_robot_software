package org.wildstang.yearly.subsystems;


import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.crio.outputs.WsSolenoid;
import org.wildstang.hardware.crio.outputs.WsVictor;
import org.wildstang.yearly.robot.CANConstants;
import org.wildstang.yearly.robot.RobotTemplate;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSOutputs;
import org.wildstang.yearly.subsystems.shooter.Flywheel;
import org.wildstang.yearly.subsystems.shooter.Blender;
import org.wildstang.yearly.subsystems.shooter.Feed;
import org.wildstang.yearly.subsystems.shooter.Gate;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.StatusFrameRate;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter implements Subsystem
{
   // Flywheels
   private CANTalon m_CANFlywheelLeft;
   private CANTalon m_CANFlywheelRight;

   private Flywheel m_leftFlywheel;
   private Flywheel m_rightFlywheel;

   
   private double m_targetSpeedLeft;
   private double m_targetSpeedRight;
   private double m_lowLimitSpeed;
   private double m_highLimitSpeed;

   // Gate
   private WsSolenoid m_gateSolenoid;

   private Gate m_gate;

   // Feeds
   private WsVictor m_leftFeedVictor;
   private WsVictor m_rightFeedVictor;

   private Feed m_leftFeed;
   private Feed m_rightFeed;
   
   private WsVictor m_blenderVictor;
   private Blender m_blender;

   // Deadband so nothing happens if joystick is bumped on accident
   private double m_feedDeadBand;
   private double m_feedSpeed;

   // PDP for checking if Feeds are jammed
   private PowerDistributionPanel pdp;

   // Inputs
   private DigitalInput m_flywheelButton;
   private DigitalInput m_gateButton;
   private DigitalInput m_overrideButton;
   private AnalogInput m_leftBeltJoystick;
   private AnalogInput m_rightBeltJoystick;

   // ALL variables below here are state that need to be reset in resetState to revert to an initial state
   // For the toggle
   private boolean m_flywheelOn = false;
   private boolean m_shooterCurrent;
   private boolean m_shooterPrev;

   // For toggling
   private boolean m_gateOpen = false;
   private boolean m_gateCurrent;
   private boolean m_gatePrev;

   private double m_leftJoyAxis;
   private double m_rightJoyAxis;

   // For checking if the gates should open when flywheels are up to speed
   private boolean readyToShootLeft = false;
   private boolean readyToShootRight = false;

   // Can override the flywheel speed checker and open gates anyway
   private boolean m_shootOverride = false;
   // Limits for range flywheels should be at before opening gates

   // Enumeration variable for SHOOT, REVERSE, and STOP
   private FeedDirection m_leftFeedDirection;
   private FeedDirection m_rightFeedDirection;


   @Override
   public void resetState()
   {
      m_shootOverride = false;

      m_leftFeedDirection = FeedDirection.STOP;
      m_rightFeedDirection = FeedDirection.STOP;
      
      readyToShootLeft = false;
      readyToShootRight = false;
      m_leftJoyAxis = 0;
      m_rightJoyAxis = 0;
      
      m_gateOpen = false;
      m_flywheelOn = false;

      // Toggle state variables
      m_shooterCurrent = false;
      m_shooterPrev = false;
      m_gateCurrent = false;
      m_gatePrev = false;
   }

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
      if (RobotTemplate.LOG_STATE)
      {
         Core.getStateTracker().addIOInfo("Left shooter (RPM)", "Shooter", "Input", null);
         Core.getStateTracker().addIOInfo("Right shooter (RPM)", "Shooter", "Input", null);
         Core.getStateTracker().addIOInfo("Left shooter voltage", "Shooter", "Input", null);
         Core.getStateTracker().addIOInfo("Right shooter voltage", "Shooter", "Input", null);
         Core.getStateTracker().addIOInfo("Left shooter current", "Shooter", "Input", null);
         Core.getStateTracker().addIOInfo("Right shooter current", "Shooter", "Input", null);
      }
      
      // Read config values
      readConfigValues();
      
      // Flywheels
      // CAN talons
      m_CANFlywheelLeft = new CANTalon(CANConstants.FLYWHEEL_LEFT_TALON_ID);
      m_CANFlywheelRight = new CANTalon(CANConstants.FLYWHEEL_RIGHT_TALON_ID);

      configureFlywheelTalons();

      m_leftFlywheel = new Flywheel(m_CANFlywheelLeft, m_targetSpeedLeft);
      m_rightFlywheel = new Flywheel(m_CANFlywheelRight, m_targetSpeedRight);

      // Gates
      m_gateSolenoid = (WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.GATE.getName());
      m_gate = new Gate(m_gateSolenoid);

      // Feeds
      m_leftFeedVictor = (WsVictor) Core.getOutputManager().getOutput(WSOutputs.FEEDER_LEFT.getName());
      m_rightFeedVictor = (WsVictor) Core.getOutputManager().getOutput(WSOutputs.FEEDER_RIGHT.getName());
      m_leftFeed = new Feed(m_leftFeedVictor, m_feedSpeed);
      m_rightFeed = new Feed(m_rightFeedVictor, m_feedSpeed);
      
      m_blenderVictor = (WsVictor) Core.getOutputManager().getOutput(WSOutputs.BLENDER.getName());
      m_blender = new Blender(m_blenderVictor);

      // PDP
      pdp = new PowerDistributionPanel();

      // Input Listeners
      m_flywheelButton = (DigitalInput) Core.getInputManager().getInput(WSInputs.FLYWHEEL.getName());
      m_flywheelButton.addInputListener(this);

      m_gateButton = (DigitalInput) Core.getInputManager().getInput(WSInputs.GATE.getName());
      m_gateButton.addInputListener(this);

      m_leftBeltJoystick = (AnalogInput) Core.getInputManager().getInput(WSInputs.FEEDER_LEFT.getName());
      m_leftBeltJoystick.addInputListener(this);
      m_rightBeltJoystick = (AnalogInput) Core.getInputManager().getInput(WSInputs.FEEDER_RIGHT.getName());
      m_rightBeltJoystick.addInputListener(this);

      m_overrideButton = (DigitalInput) Core.getInputManager().getInput(WSInputs.OVERRIDE.getName());
      m_overrideButton.addInputListener(this);
      
      resetState();
   }

   private void configureFlywheelTalons()
   {
      // Configure left talon
      configureFlywheelTalon(m_CANFlywheelLeft, 
            Preferences.getInstance().getDouble("L_F", 0.02366), 
            Preferences.getInstance().getDouble("L_P", 0.013), 
            Preferences.getInstance().getDouble("L_I", 0), 
            Preferences.getInstance().getDouble("L_D", 0.15));

      // Configure right talon
      configureFlywheelTalon(m_CANFlywheelRight, 
            Preferences.getInstance().getDouble("R_F", 0.02366), 
            Preferences.getInstance().getDouble("R_P", 0.013), 
            Preferences.getInstance().getDouble("R_I", 0), 
            Preferences.getInstance().getDouble("R_D", 0.15));
   }

   private void readConfigValues()
   {
      // Reads values from Ws Config
      m_targetSpeedLeft = Core.getConfigManager().getConfig().getDouble(this.getClass().getName() + ".flywheelSpeedLeft", 5450.0);
      m_targetSpeedRight = Core.getConfigManager().getConfig().getDouble(this.getClass().getName() + ".flywheelSpeedRight", 5450.0);
      m_lowLimitSpeed = Core.getConfigManager().getConfig().getDouble(this.getClass().getName() + ".lowLimitSpeed", 5400.0);
      m_highLimitSpeed = Core.getConfigManager().getConfig().getDouble(this.getClass().getName() + ".highLimitSpeed", 5550.0);
      m_feedSpeed = Core.getConfigManager().getConfig().getDouble(this.getClass().getName() + ".feedSpeed", 1.0);
      m_feedDeadBand = Core.getConfigManager().getConfig().getDouble(this.getClass().getName() + ".feedDeadBand", 0.05);
   }

   private void configureFlywheelTalon(CANTalon p_talon, double p_fGain, double p_pGain, double p_iGain, double p_dGain)
   {
      p_talon.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
      p_talon.setStatusFrameRateMs(StatusFrameRate.Feedback, 10);
      p_talon.enableBrakeMode(false);

      p_talon.reverseSensor(true);
      p_talon.setEncPosition(0);
      p_talon.changeControlMode(TalonControlMode.Speed);

      p_talon.configNominalOutputVoltage(+0.0f, -0.0f);
      p_talon.configPeakOutputVoltage(+12.0f, 0.0f);
      // p_talon.setVoltageRampRate(24.0); // Max spinup of 24V/s - start here

      // Set up closed loop PID control gains in slot 0
      p_talon.setProfile(0);
      p_talon.setF(p_fGain);
      p_talon.setP(p_pGain);
      p_talon.setI(p_iGain);
      p_talon.setD(p_pGain);
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

      if (RobotTemplate.LOG_STATE)
      {
         Core.getStateTracker().addState("Left shooter (RPM)", "Shooter", m_CANFlywheelLeft.getSpeed());
         Core.getStateTracker().addState("Right shooter (RPM)", "Shooter", m_CANFlywheelRight.getSpeed());
         Core.getStateTracker().addState("Left shooter voltage", "Shooter", m_CANFlywheelLeft.getOutputVoltage());
         Core.getStateTracker().addState("Right shooter voltage", "Shooter", m_CANFlywheelRight.getOutputVoltage());
         Core.getStateTracker().addState("Left shooter current", "Shooter", m_CANFlywheelLeft.getOutputCurrent());
         Core.getStateTracker().addState("Right shooter current", "Shooter", m_CANFlywheelRight.getOutputCurrent());
      }
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
      // Tests to see if the left and right flywheel is up to speed and ready to shoot a ball.
      // Sets a conditional toggle to true if that flywheel is ready.
      // Can be overriden so gates can open even if flywheel isn't up to speed

      // LEFT SIDE
      readyToShootLeft = isLeftReadyToShoot(); // || m_shootOverride;
      // RIGHT SIDE
      readyToShootRight = isRightReadyToShoot(); // || m_shootOverride;

      // Opens the gate if the flywheel is up to speed and the button is pressed
      // LEFT SIDE
      if (m_gateOpen) // && readyToShootLeft)
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
      return m_leftFeed.isJammed(pdp.getCurrent(11));
   }

   public boolean checkRightFeedJammed()
   {
      return m_rightFeed.isJammed(pdp.getCurrent(4));
   }

   public void updateFeed()
   {
      // LEFT SIDE
      if (checkLeftFeedJammed())
      {
         m_leftFeedDirection = FeedDirection.STOP;
      }

      // RIGHT SIDE
      if (checkRightFeedJammed())
      {
         m_rightFeedDirection = FeedDirection.STOP;
      }

      runFeedBelt(m_leftFeed, m_leftFeedDirection);
      runFeedBelt(m_rightFeed, m_rightFeedDirection);
   }

   private void runFeedBelt(Feed p_feed, FeedDirection p_direction)
   {
      switch (p_direction)
      {
         case SHOOT:
            p_feed.runForward();
            m_blender.turnOn();
            break;
         case REVERSE:
            p_feed.runBackwards();
            m_blender.turnOff();
            break;
         case STOP:
            p_feed.stop();
            m_blender.turnOff();
            break;
         default:
            p_feed.stop();
            m_blender.turnOff();
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
      SmartDashboard.putBoolean("Left flywheel on", m_leftFlywheel.isRunning());
      SmartDashboard.putBoolean("Right flywheel on", m_rightFlywheel.isRunning());

      SmartDashboard.putNumber("Left flywheel speed", m_leftFlywheel.getSpeed());
      SmartDashboard.putNumber("Right flywheel speed", m_rightFlywheel.getSpeed());

      SmartDashboard.putNumber("Left flywheel error", m_CANFlywheelLeft.getClosedLoopError());
      SmartDashboard.putNumber("Right flywheel error", m_CANFlywheelRight.getClosedLoopError());

      SmartDashboard.putBoolean("Gates open", m_gate.isOpen());

      SmartDashboard.putBoolean("Left feed jammed", m_leftFeed.isJammed(pdp.getCurrent(11)));
      SmartDashboard.putBoolean("Right feed jammed", m_rightFeed.isJammed(pdp.getCurrent(4)));

      // WS config
      SmartDashboard.putNumber("Left flywheel target", m_targetSpeedLeft);
      SmartDashboard.putNumber("Right flywheel target", m_targetSpeedRight);
      SmartDashboard.putNumber("Flywheel low limit", m_lowLimitSpeed);
      SmartDashboard.putNumber("Flywheel high limit", m_highLimitSpeed);
   }

}
