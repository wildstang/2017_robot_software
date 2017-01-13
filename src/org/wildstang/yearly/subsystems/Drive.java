package org.wildstang.yearly.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoid;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoidState;
import org.wildstang.yearly.robot.CANConstants;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSOutputs;
import org.wildstang.yearly.subsystems.drive.CheesyDriveHelper;
import org.wildstang.yearly.subsystems.drive.DriveSignal;
import org.wildstang.yearly.subsystems.drive.DriveType;
import org.wildstang.yearly.subsystems.drive.Path;
import org.wildstang.yearly.subsystems.drive.PathFollower;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive implements Subsystem
{
   // Hold a reference to the input test for fast equality test during
   // inputUpdate()
   private AnalogInput m_headingInput;
   private AnalogInput m_throttleInput;
   private WsDoubleSolenoid m_shifter;

   // Values from inputs
   private double m_throttleValue;
   private double m_headingValue;
   private boolean m_quickTurn;

   // Talons for output
   private CANTalon m_leftMaster;
   private CANTalon m_rightMaster;
   private CANTalon m_leftFollower;
   private CANTalon m_rightFollower;

   // State information
   private DriveType m_driveMode = DriveType.CHEESY;
   private PathFollower m_pathFollower;
   private CheesyDriveHelper m_cheesyHelper = new CheesyDriveHelper();

   private boolean m_brakeMode = true;

   // While this is really a temporary variable, declared here to prevent
   // constant stack allocation
   private DriveSignal m_driveSignal;

   @Override
   public void init()
   {
      // Drive
      m_headingInput = (AnalogInput) Core.getInputManager().getInput(WSInputs.DRV_HEADING.getName());
      m_headingInput.addInputListener(this);

      m_throttleInput = (AnalogInput) Core.getInputManager().getInput(WSInputs.DRV_THROTTLE.getName());
      m_throttleInput.addInputListener(this);

      m_shifter = (WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.SHIFTER.getName());

      initDriveTalons();
   }

   public void initDriveTalons()
   {
      m_leftMaster = new CANTalon(CANConstants.LEFT_MASTER_TALON_ID);
      m_leftFollower = new CANTalon(CANConstants.LEFT_FOLLOWER_TALON_ID);
      m_rightMaster = new CANTalon(CANConstants.RIGHT_MASTER_TALON_ID);
      m_rightFollower = new CANTalon(CANConstants.RIGHT_FOLLOWER_TALON_ID);

      // Start in open loop mode
      m_leftMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
      m_leftMaster.set(0);
      m_leftFollower.changeControlMode(CANTalon.TalonControlMode.Follower);
      m_leftFollower.set(CANConstants.LEFT_MASTER_TALON_ID);

      m_rightMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
      m_rightMaster.set(0);
      m_rightFollower.changeControlMode(CANTalon.TalonControlMode.Follower);
      m_rightFollower.set(CANConstants.RIGHT_MASTER_TALON_ID);

      setBrakeMode(true);

      // TODO: Enable when encoders are mounted

      // Set up the encoders
      m_leftMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
      if (m_leftMaster.isSensorPresent(CANTalon.FeedbackDevice.QuadEncoder) != CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent)
      {
         SmartDashboard.putBoolean("LeftEncPresent", false);
      }
      else
      {
         SmartDashboard.putBoolean("LeftEncPresent", true);
      }
      m_leftMaster.reverseSensor(true);

      m_rightMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
      if (m_rightMaster.isSensorPresent(CANTalon.FeedbackDevice.QuadEncoder) != CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent)
      {
         SmartDashboard.putBoolean("RightEndPresent", false);
      }
      else
      {
         SmartDashboard.putBoolean("RightEndPresent", true);
      }

      m_rightMaster.reverseSensor(false);

      // TODO: When gearboxes are constructed and motor direction is determined,
      // update to suit
      // m_leftMaster.reverseOutput(false);
      // m_leftFollower.reverseOutput(false);
      // m_rightMaster.reverseOutput(true);
      // m_rightFollower.reverseOutput(false);

      // TODO:Load PID profiles

   }

   @Override
   public void inputUpdate(Input p_source)
   {

      if (p_source == m_throttleInput)
      {
         m_throttleValue = m_throttleInput.getValue();
         SmartDashboard.putNumber("throttleValue", m_throttleValue);
      }
      else if (p_source == m_headingInput)
      {
         m_headingValue = m_headingInput.getValue();
         // headingValue *= -1;
         SmartDashboard.putNumber("heading value", m_headingValue);
      }
      // TODO: Add quickturn - either from a button or some state
   }

   @Override
   public void selfTest()
   {
      // DO NOT IMPLEMENT
   }

   @Override
   public void update()
   {
      switch (m_driveMode)
      {
         case PATH:
            break;

         case CHEESY:
            m_driveSignal = m_cheesyHelper.cheesyDrive(m_throttleValue, m_headingValue, m_quickTurn);
            setMotorSpeeds(m_driveSignal);
            break;
         case FULL_BRAKE:
            break;
         case RAW:
         default:
            // Raw is default
            break;
      }
   }

   public void setHighGear(boolean p_high)
   {
      if (p_high)
      {
         m_shifter.setValue(WsDoubleSolenoidState.FORWARD.ordinal());
      }
      else
      {
         m_shifter.setValue(WsDoubleSolenoidState.REVERSE.ordinal());
      }
   }

   public void setBrakeMode(boolean p_brakeOn)
   {
      if (m_brakeMode != p_brakeOn)
      {
         m_leftMaster.enableBrakeMode(p_brakeOn);
         m_leftFollower.enableBrakeMode(p_brakeOn);
         m_rightMaster.enableBrakeMode(p_brakeOn);
         m_rightFollower.enableBrakeMode(p_brakeOn);
         m_brakeMode = p_brakeOn;
      }

   }

   public void setMotorSpeeds(DriveSignal p_signal)
   {
      // Set left and right speeds
      m_leftMaster.set(p_signal.leftMotor);
      m_rightMaster.set(p_signal.rightMotor);
   }

   public void setPathFollowingMode()
   {
      m_driveMode = DriveType.PATH;

      // Configure motor controller modes for path following
      // TODO
   }

   public void setOpenLoopDrive()
   {
      // Stop following any current path
      if (m_driveMode == DriveType.PATH)
      {
         abortFollowingPath();
         pathCleanup();
      }

      m_driveMode = DriveType.CHEESY;

      // TODO: Reconfigure motor controllers
   }

   public void setRawDrive()
   {
      // Stop following any current path
      if (m_driveMode == DriveType.PATH)
      {
         abortFollowingPath();
         pathCleanup();
      }

      m_driveMode = DriveType.RAW;
   }

   public void setFullBrakeMode()
   {
      // Stop following any current path
      if (m_driveMode == DriveType.PATH)
      {
         abortFollowingPath();
         pathCleanup();
      }

      // Set talons to hold their current position
      if (m_driveMode != DriveType.FULL_BRAKE)
      {
         // TODO: Need to test and tune PID
         // At least set constants and set a profile before uncommenting the
         // code

         // m_leftMaster.setProfile(kBaseLockControlSlot);
         // m_leftMaster.changeControlMode(CANTalon.TalonControlMode.Position);
         // m_leftMaster.setAllowableClosedLoopErr(DriveConstants.BRAKE_MODE_ALLOWABLE_ERROR);
         // m_leftMaster.set(m_leftMaster.getPosition());
         //
         // m_rightMaster.setProfile(kBaseLockControlSlot);
         // m_rightMaster.changeControlMode(CANTalon.TalonControlMode.Position);
         // m_rightMaster.setAllowableClosedLoopErr(DriveConstants.BRAKE_MODE_ALLOWABLE_ERROR);
         // m_rightMaster.set(m_rightMaster.getPosition());

         m_driveMode = DriveType.FULL_BRAKE;

         setBrakeMode(true);
      }
      setHighGear(false);
   }

   public void setPath(Path p_path)
   {
      if (m_pathFollower != null)
      {
         if (m_pathFollower.isActive())
         {
            throw new IllegalStateException("One path is already active!");
         }
      }

      m_pathFollower = new PathFollower(p_path, m_leftMaster, m_rightMaster);
   }

   public void startFollowingPath()
   {
      if (m_pathFollower == null)
      {
         throw new IllegalStateException("No path set");
      }

      if (m_pathFollower.isActive())
      {
         throw new IllegalStateException("Path is already active");
      }

      m_pathFollower.start();
   }

   public void abortFollowingPath()
   {
      if (m_pathFollower != null)
      {
         m_pathFollower.stop();
      }
   }

   public void pathCleanup()
   {
      m_pathFollower = null;
   }

   @Override
   public String getName()
   {
      return "Drive";
   }

}
