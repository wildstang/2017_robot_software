package org.wildstang.yearly.subsystems;

import java.io.*;
import java.util.LinkedList;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.logger.StateTracker;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoid;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoidState;
import org.wildstang.yearly.robot.CANConstants;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSOutputs;
import org.wildstang.yearly.subsystems.drive.CheesyDriveHelper;
import org.wildstang.yearly.subsystems.drive.DriveConstants;
import org.wildstang.yearly.subsystems.drive.DriveSignal;
import org.wildstang.yearly.subsystems.drive.DriveState;
import org.wildstang.yearly.subsystems.drive.DriveType;
import org.wildstang.yearly.subsystems.drive.Path;
import org.wildstang.yearly.subsystems.drive.PathFollower;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive implements Subsystem
{
   // Hold a reference to the input test for fast equality test during
   // inputUpdate()
   private AnalogInput m_headingInput;
   private AnalogInput m_throttleInput;
   private WsDoubleSolenoid m_shifterSolenoid;
   private DigitalInput m_rawModeInput;
   private DigitalInput m_shifterInput;
   private DigitalInput m_quickTurnInput;

   // Values from inputs
   private double m_throttleValue;
   private double m_headingValue;
   private boolean m_quickTurn;
   
   private boolean m_shifterCurrent = false;
   private boolean m_shifterPrev = false;
   private boolean m_highGear = false;

   private boolean m_rawModeCurrent = false;
   private boolean m_rawModePrev = false;
   private boolean m_rawMode = false;

   // Talons for output
   private CANTalon m_leftMaster;
   private CANTalon m_rightMaster;
   private CANTalon m_leftFollower;
   private CANTalon m_rightFollower;

   // State information
   private DriveType m_driveMode = DriveType.CHEESY;
   private PathFollower m_pathFollower;
   private CheesyDriveHelper m_cheesyHelper = new CheesyDriveHelper();

   private static final double ROBOT_WIDTH_INCHES = 38;
   private static final double WHEEL_DIAMETER_INCHES = 4;
   private static final double TICKS_TO_INCHES = WHEEL_DIAMETER_INCHES * Math.PI / 1024;
   private DriveState absoluteDriveState = new DriveState(0, 0, 0, 0, 0, 0);
   private LinkedList<DriveState> driveStates = new LinkedList<DriveState>();

   private boolean m_brakeMode = true;

   // TODO Remove this
   private PowerDistributionPanel pdp;
   
   // While this is really a temporary variable, declared here to prevent
   // constant stack allocation
   private DriveSignal m_driveSignal;

   @Override
   public void init()
   {
      pdp = new PowerDistributionPanel();
      Core.getStateTracker().addIOInfo("Left speed (RPM)", "Drive", "Input", null);
      Core.getStateTracker().addIOInfo("Right speed (RPM)", "Drive", "Input", null);
      Core.getStateTracker().addIOInfo("Left 1 current", "Drive", "Input", null);
      Core.getStateTracker().addIOInfo("Left 2 current", "Drive", "Input", null);
      Core.getStateTracker().addIOInfo("Right 1 current", "Drive", "Input", null);
      Core.getStateTracker().addIOInfo("Right 2 current", "Drive", "Input", null);
      
      // Drive
      m_headingInput = (AnalogInput) Core.getInputManager().getInput(WSInputs.DRV_HEADING.getName());
      m_headingInput.addInputListener(this);

      m_throttleInput = (AnalogInput) Core.getInputManager().getInput(WSInputs.DRV_THROTTLE.getName());
      m_throttleInput.addInputListener(this);
      
      m_shifterInput = (DigitalInput)Core.getInputManager().getInput(WSInputs.SHIFT.getName());
      m_shifterInput.addInputListener(this);

      m_quickTurnInput = (DigitalInput)Core.getInputManager().getInput(WSInputs.QUICK_TURN.getName());
      m_quickTurnInput.addInputListener(this);

      m_shifterSolenoid = (WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.SHIFTER.getName());

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

      m_leftMaster.configNominalOutputVoltage(0.0, 0.0);
      m_leftMaster.configPeakOutputVoltage(+12.0f, -12.0f);
      
      m_rightMaster.configNominalOutputVoltage(0.0, 0.0);
      m_rightMaster.configPeakOutputVoltage(+12.0f, -12.0f);

      setBrakeMode(true);

      // TODO: Enable when encoders are mounted

      // Set up the encoders
      m_leftMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
      m_leftMaster.configEncoderCodesPerRev(256);
      if (m_leftMaster.isSensorPresent(CANTalon.FeedbackDevice.QuadEncoder) != CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent)
      {
         SmartDashboard.putBoolean("LeftEncPresent", false);
      }
      else
      {
         SmartDashboard.putBoolean("LeftEncPresent", true);
      }
      //m_leftMaster.reverseSensor(false);

      m_rightMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
      m_rightMaster.configEncoderCodesPerRev(256);
      if (m_rightMaster.isSensorPresent(CANTalon.FeedbackDevice.QuadEncoder) != CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent)
      {
         SmartDashboard.putBoolean("RightEncPresent", false);
      }
      else
      {
         SmartDashboard.putBoolean("RightEncPresent", true);
      }

      //m_rightMaster.reverseSensor(true);

      // TODO: When gearboxes are constructed and motor direction is determined,
      // update to suit
      // m_leftMaster.reverseOutput(false);
      // m_leftFollower.reverseOutput(false);
      // m_rightMaster.reverseOutput(true);
      // m_rightFollower.reverseOutput(false);

      // Load PID profiles
      // Path following profile
      m_leftMaster.setProfile(DriveConstants.PATH_PROFILE_SLOT);
      m_leftMaster.setF(DriveConstants.PATH_F_GAIN);
      m_leftMaster.setP(DriveConstants.PATH_P_GAIN);
      m_leftMaster.setI(DriveConstants.PATH_I_GAIN);
      m_leftMaster.setD(DriveConstants.PATH_D_GAIN);

      m_rightMaster.setProfile(DriveConstants.PATH_PROFILE_SLOT);
      m_rightMaster.setF(DriveConstants.PATH_F_GAIN);
      m_rightMaster.setP(DriveConstants.PATH_P_GAIN);
      m_rightMaster.setI(DriveConstants.PATH_I_GAIN);
      m_rightMaster.setD(DriveConstants.PATH_D_GAIN);

      // Base lock profile
      m_leftMaster.setProfile(DriveConstants.BASE_LOCK_PROFILE_SLOT);
      m_leftMaster.setF(DriveConstants.BASE_F_GAIN);
      m_leftMaster.setP(DriveConstants.BASE_P_GAIN);
      m_leftMaster.setI(DriveConstants.BASE_I_GAIN);
      m_leftMaster.setD(DriveConstants.BASE_D_GAIN);

      m_rightMaster.setProfile(DriveConstants.BASE_LOCK_PROFILE_SLOT);
      m_rightMaster.setF(DriveConstants.BASE_F_GAIN);
      m_rightMaster.setP(DriveConstants.BASE_P_GAIN);
      m_rightMaster.setI(DriveConstants.BASE_I_GAIN);
      m_rightMaster.setD(DriveConstants.BASE_D_GAIN);

   }

   @Override
   public void inputUpdate(Input p_source)
   {

      if (p_source == m_throttleInput)
      {
         m_throttleValue = -m_throttleInput.getValue();
         SmartDashboard.putNumber("throttleValue", m_throttleValue);
      }
      else if (p_source == m_headingInput)
      {
         m_headingValue = -m_headingInput.getValue();
         // headingValue *= -1;
         SmartDashboard.putNumber("heading value", m_headingValue);
      }
      else if (p_source == m_shifterInput)
      {
         m_shifterCurrent = m_shifterInput.getValue();
         // Check and toggle shifter state
         toggleShifter();
      }
      else if (p_source == m_quickTurnInput)
      {
         m_quickTurn = m_quickTurnInput.getValue();
      }
      // If SELECT is pressed, use Raw mode
      else if (p_source == m_rawModeInput)
      {
         m_rawModeCurrent = m_rawModeInput.getValue();

         // Determine drive state override
         calculateRawMode();
      }
   }

   @Override
   public void selfTest()
   {
      // DO NOT IMPLEMENT
   }

   @Override
   public void update()
   {

      // Set shifter output before driving
      // NOTE: The state of m_highGear needs to be set prior to update being called.  This is either in inputUpdate() (for teleop)
      // or by an auto program by calling setHighGear()
      if (m_highGear)
      {
         m_shifterSolenoid.setValue(WsDoubleSolenoidState.FORWARD.ordinal());
      }
      else
      {
         m_shifterSolenoid.setValue(WsDoubleSolenoidState.REVERSE.ordinal());
      }
      
      switch (m_driveMode)
      {
         case PATH:
            collectDriveState();
            break;

         case CHEESY:
            m_driveSignal = m_cheesyHelper.cheesyDrive(m_throttleValue, m_headingValue, m_quickTurn);
            setMotorSpeeds(m_driveSignal);
            collectDriveState();
            break;
         case FULL_BRAKE:
            break;
         case RAW:
         default:
            // Raw is default
            m_driveSignal = new DriveSignal(m_throttleValue, m_throttleValue);
            break;
      }

      Core.getStateTracker().addState("Left speed (RPM)", "Drive", m_leftMaster.getSpeed());
      Core.getStateTracker().addState("Right speed (RPM)", "Drive", m_rightMaster.getSpeed());
      
      Core.getStateTracker().addState("Left 1 current", "Drive", pdp.getCurrent(0));
      Core.getStateTracker().addState("Left 2 current", "Drive", pdp.getCurrent(1));
      Core.getStateTracker().addState("Right 1 current", "Drive", pdp.getCurrent(14));
      Core.getStateTracker().addState("Right 2 current", "Drive", pdp.getCurrent(15));
   }

   private void toggleShifter()
   {
      if (m_shifterCurrent && !m_shifterPrev)
      {
         m_highGear = !m_highGear;
      }
      m_shifterPrev = m_shifterCurrent;
   }

   private void collectDriveState()
   {
      // Calculate all changes in DriveState
      double deltaLeftTicks = m_leftMaster.getEncPosition() - absoluteDriveState.getDeltaLeftEncoderTicks();
      double deltaRightTicks = m_rightMaster.getEncPosition() - absoluteDriveState.getDeltaRightEncoderTicks();
      double deltaHeading = 0 - absoluteDriveState.getHeadingAngle(); // CHANGE
      double deltaTime = System.currentTimeMillis() - absoluteDriveState.getDeltaTime();

      SmartDashboard.putNumber("Left Encoder", m_leftMaster.getEncPosition());
      SmartDashboard.putNumber("Right Encoder", m_rightMaster.getEncPosition());
      
      /****** CONVERT TICKS TO TURN RADIUS AND CIRCLE ******/
      long startTime = System.nanoTime();

      double deltaLeftInches = deltaLeftTicks * TICKS_TO_INCHES;
      double deltaRightInches = deltaRightTicks * TICKS_TO_INCHES;

      double deltaTheta;
      if (deltaLeftTicks != deltaRightTicks)
      {
         deltaTheta = Math.atan2(ROBOT_WIDTH_INCHES, (deltaLeftTicks - deltaRightTicks));
      }
      else
      {
         deltaTheta = 0;
      }

      double c;
      double rLong;
      // double deltaXRight; // delta X and Y relative to the robots position
      // double deltaXLeft;
      // double deltaYRight;
      // double deltaYLeft;
      if (deltaTheta < 0)
      {
         c = Math.abs((deltaRightTicks * ROBOT_WIDTH_INCHES) / (deltaLeftTicks - deltaRightTicks));

      }
      else if (deltaTheta > 0)
      {
         c = Math.abs((deltaLeftTicks * ROBOT_WIDTH_INCHES) / (deltaRightTicks - deltaLeftTicks));

      }
      else
      {
         c = (double) Integer.MAX_VALUE;

      }

      rLong = c + ROBOT_WIDTH_INCHES; // Will probably use later, this is the
                                      // larger turn radius.

      System.out.println("Time Elapsed: " + (System.nanoTime() - startTime));
      /*********************************/

      // Add the DriveState to the list
      driveStates.add(new DriveState(deltaTime, deltaRightTicks, deltaLeftTicks, deltaHeading, c, deltaTheta));

      // reset the absolute DriveState for the next cycle
      absoluteDriveState.setDeltaTime(absoluteDriveState.getDeltaTime() + deltaTime);
      absoluteDriveState.setDeltaRightEncoderTicks(absoluteDriveState.getDeltaRightEncoderTicks() + deltaRightTicks);
      absoluteDriveState.setDeltaLeftEncoderTicks(absoluteDriveState.getDeltaLeftEncoderTicks() + deltaLeftTicks);
      absoluteDriveState.setHeading(absoluteDriveState.getHeadingAngle() + deltaHeading);

      

   }

   private void calculateRawMode()
   {
      if (m_rawModeCurrent && !m_rawModePrev)
      {
         m_rawMode = !m_rawMode;
      }
      m_rawModePrev = m_rawModeCurrent;
      if (m_rawMode)
      {
         setRawDrive();
      }
   }

   public void setHighGear(boolean p_high)
   {
      m_highGear = p_high;
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
      System.out.println("Drive.setPathFollowingMode() called");

      m_driveMode = DriveType.PATH;

      // Configure motor controller modes for path following
      m_leftMaster.changeControlMode(TalonControlMode.MotionProfile);
      m_leftMaster.setProfile(0);

      m_rightMaster.changeControlMode(TalonControlMode.MotionProfile);
      m_rightMaster.setProfile(0);

      // Go as fast as possible
      setHighGear(true);
      
      // Use brake mode to stop quickly at end of path, since Talons will put output to neutral
      setBrakeMode(true);
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

      // Reconfigure motor controllers
      m_leftMaster.changeControlMode(TalonControlMode.PercentVbus);
      m_rightMaster.changeControlMode(TalonControlMode.PercentVbus);
   }

   public void setRawDrive()
   {
      setOpenLoopDrive();

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
         // Set up Talons to hold their current position as close as possible

         m_leftMaster.setProfile(DriveConstants.BASE_LOCK_PROFILE_SLOT);
         m_leftMaster.changeControlMode(CANTalon.TalonControlMode.Position);
         m_leftMaster.setAllowableClosedLoopErr(DriveConstants.BRAKE_MODE_ALLOWABLE_ERROR);
         m_leftMaster.set(m_leftMaster.getPosition());

         m_rightMaster.setProfile(DriveConstants.BASE_LOCK_PROFILE_SLOT);
         m_rightMaster.changeControlMode(CANTalon.TalonControlMode.Position);
         m_rightMaster.setAllowableClosedLoopErr(DriveConstants.BRAKE_MODE_ALLOWABLE_ERROR);
         m_rightMaster.set(m_rightMaster.getPosition());

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

      System.out.println("Drive.setPath(): Creating new PathFollower");
      m_pathFollower = new PathFollower(p_path, m_leftMaster, m_rightMaster);
   }

   public PathFollower getPathFollower()
   {
      return m_pathFollower;
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
      m_pathFollower.stop();
      m_pathFollower = null;
   }

   @Override
   public String getName()
   {
      return "Drive Base";
   }

   public void writeDriveStatesToFile(String fileName)
   {
      BufferedWriter bw = null;
      FileWriter fw = null;

      try
      {
         fw = new FileWriter(fileName);
         bw = new BufferedWriter(fw);
         for (DriveState ds : driveStates)
         {
            bw.write(ds.toString());
         }
         System.out.println("Done");
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }

      try
      {
         if (bw != null)
         {
            bw.close();
         }
         if (fw != null)
         {
            fw.close();
         }
      }
      catch (IOException ex)
      {
         ex.printStackTrace();
      }
   }
}
