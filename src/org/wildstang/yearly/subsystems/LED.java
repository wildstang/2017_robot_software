package org.wildstang.yearly.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.crio.outputs.WsI2COutput;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSOutputs;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.Shooter;
import org.wildstang.yearly.subsystems.Intake;
import edu.wpi.first.wpilibj.DriverStation;

/**
 *
 */
public class LED implements Subsystem
{

   private static final int DISABLED_ID = 1;
   private static final int AUTO_ID = 2;
   private static final int ALLIANCE_ID = 3;
   private static final int TURBO_ID = 4;
   private static final int SHOOTER_ON_ID = 5;
   private static final int SHOOTING_ID = 6;
   private static final int CLIMBING_ID = 7;
   private static final int CLIMB_COMPLETE_ID = 8;
   private static final int LEFTJAM_ID = 9;
   private static final int RIGHTJAM_ID = 10;
   private static final int INTAKE_ID = 11;

   // Sent states
   boolean autoDataSent = false;
   boolean m_newDataAvailable = false;
   boolean disableDataSent = false;

   private String m_name;

   WsI2COutput m_ledOutput;

   boolean m_turbo;
   boolean m_normal = true;
   boolean m_shooter;
   boolean m_intake;

   boolean m_leftJammedLast = false;
   boolean m_rightJammedLast = false;
   boolean m_isFlywheelReady = false;
   
   private Shooter shooter;

   // Reused commands from year to year
   public static LedCmd disabledCmd = new LedCmd(DISABLED_ID, 0, 0, 0);
   public static LedCmd autoCmd = new LedCmd(AUTO_ID, 0, 0, 0);
   public static LedCmd redAllianceCmd = new LedCmd(ALLIANCE_ID, 255, 0, 0);
   public static LedCmd blueAllianceCmd = new LedCmd(ALLIANCE_ID, 0, 0, 255);
   public static LedCmd purpleAllianceCmd = new LedCmd(ALLIANCE_ID, 255, 0, 255);
   public static LedCmd turboCmd = new LedCmd(TURBO_ID, 0, 0, 0);
   
   public static LedCmd shooterOnCmd = new LedCmd(SHOOTER_ON_ID, 255, 255, 0);
      // When flywheels are simply running
   public static LedCmd shootingCmd = new LedCmd(SHOOTING_ID, 0, 0, 0);
      // When flywheels are running and gates are open and feed is going
   
   public static LedCmd intakeCmd = new LedCmd(INTAKE_ID, 255, 255, 0);
   public static LedCmd climbingCmd = new LedCmd(CLIMBING_ID, 0, 0, 0);
   public static LedCmd leftFeedCmd = new LedCmd(LEFTJAM_ID, 0, 0, 255);
   public static LedCmd rightFeedCmd = new LedCmd(RIGHTJAM_ID, 255, 0, 0);

   public LED()
   {
      m_name = "LED";
   }

   @Override
   public void init()
   {
      resetState();
      
      m_ledOutput = (WsI2COutput) Core.getOutputManager().getOutput(WSOutputs.LED.getName());

      shooter = (Shooter) Core.getSubsystemManager().getSubsystem(WSSubsystems.SHOOTER.getName());

      // Core.getInputManager().getInput(WSInputs.DRV_BUTTON_1.getName()).addInputListener(this);
      // Core.getInputManager().getInput(WSInputs.DRV_BUTTON_8.getName()).addInputListener(this);
   }

   @Override
   public void resetState()
   {
      autoDataSent = false;
      disableDataSent = false;
      m_newDataAvailable = false;
   }

   @Override
   public void update()
   {
      // Get all inputs relevant to the LEDs
      boolean isRobotEnabled = DriverStation.getInstance().isEnabled();
      boolean isRobotTeleop = DriverStation.getInstance().isOperatorControl();
      boolean isRobotAuton = DriverStation.getInstance().isAutonomous();

      boolean isLeftFeedJammed = shooter.checkLeftFeedJammed();
      m_newDataAvailable = isLeftFeedJammed && !m_leftJammedLast;
      m_leftJammedLast = isLeftFeedJammed;
      
      boolean isRightFeedJammed = shooter.checkRightFeedJammed();
      m_newDataAvailable |= isRightFeedJammed && !m_rightJammedLast;
      m_rightJammedLast = isRightFeedJammed;
      
      boolean isFlywheelsReady = (shooter.isLeftReadyToShoot() && shooter.isRightReadyToShoot());
      m_newDataAvailable |= isFlywheelsReady && !m_isFlywheelReady;
      m_isFlywheelReady = isFlywheelsReady;

      m_normal = !m_turbo;

      if (isRobotEnabled)
      {
         // Robot is enabled - teleop or auto
         if (isRobotTeleop)
         {
            if (m_newDataAvailable)
            {
               if (isLeftFeedJammed)
               {
                  m_ledOutput.setValue(leftFeedCmd.getBytes());
               }
               else if (isRightFeedJammed)
               {
                  m_ledOutput.setValue(rightFeedCmd.getBytes());
               }
               else if (isFlywheelsReady)
               {
                  m_ledOutput.setValue(shooterOnCmd.getBytes());
               }
            }
            m_newDataAvailable = false;

         }
         else if (isRobotAuton)
         {
            if (!autoDataSent)
            {
               m_ledOutput.setValue(autoCmd.getBytes());
               autoDataSent = true;
            }
         }
      }
   }

   @Override
   public void inputUpdate(Input source)
   {
      // if (source.getName().equals(WSInputs.DRV_BUTTON_1.getName()))
      // {
      // m_shooter = ((DigitalInput) source).getValue();
      // }

      // m_newDataAvailable = true;
   }

//   public void sendCommand(LedCmd p_command)
//   {
//      m_currentCmd = p_command;
//      m_newDataAvailable = true;
//   }

   @Override
   public void selfTest()
   {
   }

   @Override
   public String getName()
   {
      return m_name;
   }

   public static class LedCmd
   {

      byte[] dataBytes = new byte[4];

      public LedCmd(int command, int red, int green, int blue)
      {

         dataBytes[0] = (byte) command;
         dataBytes[1] = (byte) red;
         dataBytes[2] = (byte) green;
         dataBytes[3] = (byte) blue;
      }

      public byte[] getBytes()
      {
         return dataBytes;
      }
   }
}
