package org.wildstang.yearly.robot;
/*----------------------------------------------------------------------------*/

/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.wildstang.framework.auto.AutoManager;
import org.wildstang.framework.core.Core;
import org.wildstang.framework.logger.StateLogger;
import org.wildstang.framework.timer.ProfilingTimer;
import org.wildstang.hardware.crio.RoboRIOInputFactory;
import org.wildstang.hardware.crio.RoboRIOOutputFactory;
import org.wildstang.hardware.crio.outputs.WsI2COutput;
import org.wildstang.yearly.auto.test.TESTTalonMotionProfileAuto;
import org.wildstang.yearly.auto.test.autoprogram_test;
import org.wildstang.yearly.robot.vision.VisionServer;

import org.wildstang.yearly.subsystems.Drive;
import org.wildstang.yearly.subsystems.LED;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//import edu.wpi.first.wpilibj.Watchdog;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot
{

   private static long lastCycleTime = 0;
   private StateLogger m_stateLogger = null;
   private Core m_core = null;
   private static Logger s_log = Logger.getLogger(RobotTemplate.class.getName());

   private VisionServer m_visionServer;
   
   private boolean exceptionThrown = false;

   private boolean m_firstDisabled = true;

   private boolean firstRun = true;
   private boolean AutoFirstRun = true;
   private double oldTime = System.currentTimeMillis();

   static boolean teleopPerodicCalled = false;

   private static final String DRIVER_STATES_FILENAME = "/home/lvuser/driver_states.txt";

   private void startloggingState()
   {
      Writer outputWriter = null;

      outputWriter = getFileWriter();
      // outputWriter = getNetworkWriter("10.1.11.12", 17654);

      m_stateLogger.setWriter(outputWriter);

      // Set the interval between writes to the file. Try 100ms
      // m_stateLogger.setWriteInterval(100);
      m_stateLogger.start();

      Thread t = new Thread(m_stateLogger);
      t.start();
   }

   private Writer getNetworkWriter(String ipAddress, int port)
   {
      BufferedWriter output = null;

      try
      {
         Socket socket = new Socket(ipAddress, port);
         output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      }
      catch (UnknownHostException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      return output;
   }

   private FileWriter getFileWriter()
   {
      FileWriter output = null;

      try
      {
         File outputFile;
         String osname = System.getProperty("os.name");
         if (osname.startsWith("Windows"))
         {
            outputFile = new File("./../../log.txt");
         }
         else if (osname.startsWith("Mac"))
         {
            outputFile = new File("./../../log.txt");
         }
         else
         {
            outputFile = new File("/home/lvuser/log.txt");
         }
         if (outputFile.exists())
         {
            outputFile.delete();
         }
         outputFile.createNewFile();
         output = new FileWriter(outputFile);
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      return output;
   }

   public void robotInit()
   {
      startupTimer.startTimingSection();

      m_core = new Core(RoboRIOInputFactory.class, RoboRIOOutputFactory.class);
      m_stateLogger = new StateLogger(Core.getStateTracker());

      // Load the config
      loadConfig();

      // Create application systems
      m_core.createInputs(WSInputs.values());
      m_core.createOutputs(WSOutputs.values());

      // 1. Add subsystems
      m_core.createSubsystems(WSSubsystems.values());

      startloggingState();

      // 2. Add Auto programs
      AutoManager.getInstance().addProgram(new TESTTalonMotionProfileAuto());
      AutoManager.getInstance().addProgram(new autoprogram_test());
      
      // 3. Start Vision server
      m_visionServer = new VisionServer(5080);
      m_visionServer.startVisionServer();
      
      s_log.logp(Level.ALL, this.getClass().getName(), "robotInit", "Startup Completed");

      startupTimer.endTimingSection();

   }

   private void loadConfig()
   {
      File configFile;
      String osname = System.getProperty("os.name");
      if (osname.startsWith("Windows"))
      {
         configFile = new File("./Config/ws_config.txt");
      }
      else if (osname.startsWith("Mac"))
      {
         configFile = new File("./Config/ws_config.txt");
      }
      else
      {
         configFile = new File("/ws_config.txt");
      }

      BufferedReader reader = null;

      try
      {
         reader = new BufferedReader(new FileReader(configFile));
         Core.getConfigManager().loadConfig(reader);

      }
      catch (FileNotFoundException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      if (reader != null)
      {
         try
         {
            reader.close();
         }
         catch (IOException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
   }

   ProfilingTimer durationTimer = new ProfilingTimer("Periodic method duration", 50);
   ProfilingTimer periodTimer = new ProfilingTimer("Periodic method period", 50);
   ProfilingTimer startupTimer = new ProfilingTimer("Startup duration", 1);
   ProfilingTimer initTimer = new ProfilingTimer("Init duration", 1);

   public void disabledInit()
   {
      initTimer.startTimingSection();
      AutoManager.getInstance().clear();

      loadConfig();

      initTimer.endTimingSection();
      s_log.logp(Level.ALL, this.getClass().getName(), "disabledInit", "Disabled Init Complete");

   }

   public void disabledPeriodic()
   {

      if (((Drive) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName())).getPathFollower() != null)
      {
         if (((Drive) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName())).getPathFollower().isActive())
         {
            ((Drive) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName())).pathCleanup();
         }
      }

      if (m_firstDisabled)
      {
         // Send alliance colour to LEDs
         if (DriverStation.getInstance().getAlliance().equals(Alliance.Red))
         {
            ((WsI2COutput) Core.getOutputManager().getOutput(WSOutputs.LED.getName())).setValue(LED.redAllianceCmd.getBytes());
         }
         else if (DriverStation.getInstance().getAlliance().equals(Alliance.Blue))
         {
            ((WsI2COutput) Core.getOutputManager().getOutput(WSOutputs.LED.getName())).setValue(LED.blueAllianceCmd.getBytes());
         }
         else if (DriverStation.getInstance().getAlliance().equals(Alliance.Invalid))
         {
            ((WsI2COutput) Core.getOutputManager().getOutput(WSOutputs.LED.getName())).setValue(LED.purpleAllianceCmd.getBytes());
         }
         m_firstDisabled = false;
      }
      else
      {
         // Send rainbow colour to LEDs
         ((WsI2COutput) Core.getOutputManager().getOutput(WSOutputs.LED.getName())).setValue(LED.disabledCmd.getBytes());
      }

      // If we are finished with teleop, finish and close the log file
      if (((Drive) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName())).getPathFollower() != null)
      {
         if (((Drive) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName())).getPathFollower().isActive())
         {
            ((Drive) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName())).pathCleanup();
         }
      }

      if (teleopPerodicCalled)
      {
         m_stateLogger.stop();
      }

      resetRobotState();
   }

   /**
    * This should be called to reset any robot state between runs, without
    * having to restart robot code.
    * 
    */
   private void resetRobotState()
   {
      AutoFirstRun = true;
      firstRun = true;
   }

   public void autonomousInit()
   {
      m_core.setAutoManager(AutoManager.getInstance());
      AutoManager.getInstance().startCurrentProgram();
   }

   public void autonomousPeriodic()
   {
      // Update all inputs, outputs and subsystems

      m_core.executeUpdate();
//      double time = System.currentTimeMillis();
//      SmartDashboard.putNumber("Cycle Time", time - oldTime);
//      oldTime = time;
      if (AutoFirstRun)
      {
         AutoFirstRun = false;
      }
   }

   /**
    * This function is called periodically during operator control
    */
   public void teleopInit()
   {
      // Write all DriveState objects to a file from auto
      ((Drive) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName())).writeDriveStatesToFile(DRIVER_STATES_FILENAME);
      // Remove the AutoManager from the Core
      m_core.setAutoManager(null);

      Drive driveBase = ((Drive) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName()));
      driveBase.setOpenLoopDrive();

      periodTimer.startTimingSection();
   }

   public void teleopPeriodic()
   {

      if (firstRun)
      {
         teleopPerodicCalled = true;
         firstRun = false;
      }

      try
      {

         // Update all inputs, outputs and subsystems
         m_core.executeUpdate();
      }
      catch (Throwable e)
      {
         SmartDashboard.putString("Exception thrown", e.toString());
         exceptionThrown = true;
         throw e;
      }
      finally
      {
         SmartDashboard.putBoolean("ExceptionThrown", exceptionThrown);
      }
   }

   public void testInit()
   {

   }

   /**
    * This function is called periodically during test mode
    */
   public void testPeriodic()
   {
      // Watchdog.getInstance().feed();
   }
}
