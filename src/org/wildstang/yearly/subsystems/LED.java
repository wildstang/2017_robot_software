package org.wildstang.yearly.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.crio.outputs.WsI2COutput;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSOutputs;
import org.wildstang.yearly.robot.WSSubsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class LED implements Subsystem
{

   private static final int DISABLED_ID = 1;
   private static final int AUTO_ID = 2;
   private static final int ALLIANCE_ID = 3;
   private static final int FLYWHEEL_SPINNING_UP_ID = 4;
   private static final int FLYWHEEL_AT_SPEED_ID = 5;
   private static final int SHOOTING_ID = 6;
   private static final int SHOOTER_JAMMED_ID = 7;
   
   // Sent states
   boolean autoDataSent = false;
   boolean m_newDataAvailable = false;
   boolean disableDataSent = false;
   
   private String m_name;

   WsI2COutput m_ledOutput;

   boolean m_turbo;
   boolean m_normal;
   boolean m_intake;

   // Reused commands from year to year
   LedCmd disabledCmd = new LedCmd(DISABLED_ID, 0, 0, 0);
   LedCmd autoCmd = new LedCmd(AUTO_ID, 0, 0, 0);
   LedCmd redAllianceCmd = new LedCmd(ALLIANCE_ID, 255, 0, 0);
   LedCmd blueAllianceCmd = new LedCmd(ALLIANCE_ID, 0, 0, 255);

   public LED()
   {
      m_name = "LED";
   }

   @Override
   public void init()
   {
      autoDataSent = false;
      disableDataSent = false;
      m_ledOutput = (WsI2COutput) Core.getOutputManager().getOutput(WSOutputs.LED.getName());

      // Core.getInputManager().getInput(WSInputs.DRV_BUTTON_1.getName()).addInputListener(this);
      // Core.getInputManager().getInput(WSInputs.DRV_BUTTON_8.getName()).addInputListener(this);
   }

   @Override
   public void update()
   {
      // Get all inputs relevant to the LEDs
      boolean isRobotEnabled = DriverStation.getInstance().isEnabled();
      boolean isRobotTeleop = DriverStation.getInstance().isOperatorControl();
      boolean isRobotAuton = DriverStation.getInstance().isAutonomous();

      DriverStation.Alliance alliance = DriverStation.getInstance().getAlliance();

      m_normal = !m_turbo;

      if (isRobotEnabled)
      {
         if (isRobotTeleop)
         {
            if (m_newDataAvailable)
            {
               if (m_turbo)
               {
                  // m_ledOutput.setValue(turboCmd.getBytes());
               }
               else if (m_normal)
               {
                  switch (alliance)
                  {
                     case Red:
                     {
                        if (!disableDataSent)
                        {
                           m_ledOutput.setValue(redAllianceCmd.getBytes());
                           disableDataSent = true;
                        }
                     }
                        break;

                     case Blue:
                     {
                        if (!disableDataSent)
                        {
                           m_ledOutput.setValue(blueAllianceCmd.getBytes());
                           disableDataSent = true;
                        }
                     }
                        break;

                     default:
                     {
                        disableDataSent = false;
                     }
                        break;
                  }
               }

               else if (m_intake)
               {
                  // m_ledOutput.setValue(intake.getBytes());
               }

            }
            m_newDataAvailable = false;
         }
         SmartDashboard.putBoolean("Turbo", m_turbo);
         SmartDashboard.putBoolean("Intake", m_intake);
      }
      else if (isRobotAuton)
      {
         if (!autoDataSent)
         {
            m_ledOutput.setValue(autoCmd.getBytes());
            autoDataSent = true;
         }
      }
      else
      {
         if (!disableDataSent)
         {
            m_ledOutput.setValue(disabledCmd.getBytes());
            disableDataSent = true;
         }

//         switch (alliance)
//         {
//            case Red:
//            {
//               if (!disableDataSent)
//               {
//                  m_ledOutput.setValue(redAllianceCmd.getBytes());
//                  disableDataSent = true;
//               }
//            }
//               break;
//
//            case Blue:
//            {
//               if (!disableDataSent)
//               {
//                  m_ledOutput.setValue(blueAllianceCmd.getBytes());
//                  disableDataSent = true;
//               }
//            }
//               break;
//
//            default:
//            {
//               disableDataSent = false;
//            }
//               break;
//         }
      }
   }

   @Override
   public void inputUpdate(Input source)
   {
      // if (source.getName().equals(WSInputs.DRV_BUTTON_1.getName()))
      // {
      // m_shooter = ((DigitalInput) source).getValue();
      // }

      m_newDataAvailable = true;
   }

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

      byte[] getBytes()
      {
         return dataBytes;
      }
   }

@Override
public void resetState() {
	// TODO Auto-generated method stub
	
}
}
