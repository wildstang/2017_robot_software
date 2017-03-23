package org.wildstang.yearly.auto.steps;

import java.io.File;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.Drive;
import org.wildstang.yearly.subsystems.drive.Path;
import org.wildstang.yearly.subsystems.drive.PathFollower;
import org.wildstang.yearly.subsystems.drive.PathReader;
import org.wildstang.yearly.subsystems.drive.Trajectory;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class PathFollowerStep extends AutoStep
{

   private String m_filePath;
   private Path m_path;
   private Drive m_drive;
   private PathFollower m_pathFollower;
   
   private boolean m_started = false;
   
//   private SendableChooser chooser;
   
   public PathFollowerStep(String p_path)
   {
      m_filePath = p_path;
   }
   
   @Override
   public void initialize()
   {
//      chooser = new SendableChooser();
//      chooser.addDefault("Red", true);
//      chooser.addObject("Blue", false);

      m_path = new Path();
      File leftFile = new File(m_filePath + ".left");
      File rightFile = new File(m_filePath + ".right");
      Trajectory leftTrajectory;
      Trajectory rightTrajectory;
      
//       (chooser.getSelected().equals("Blue"))
      if (DriverStation.getInstance().getAlliance().equals(DriverStation.Alliance.Blue)) {
         leftTrajectory = PathReader.readTrajectory(rightFile);
         rightTrajectory = PathReader.readTrajectory(leftFile);
      } else {
         leftTrajectory = PathReader.readTrajectory(leftFile);
         rightTrajectory = PathReader.readTrajectory(rightFile);
      }
      System.out.println("Left has " + leftTrajectory.getTalonPoints().size() + " points");
      System.out.println("right has " + rightTrajectory.getTalonPoints().size() + " points");
      
      m_path.setLeft(leftTrajectory);
      m_path.setRight(rightTrajectory);
      
      m_drive = (Drive)Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName());
      m_drive.setHighGear(true);
   }

   @Override
   public void update()
   {
      if (!isFinished())
      {
         if (!m_started)
         {
            // TODO: Can next 3 lines be moved to init() ??
            m_drive.setPathFollowingMode();
            m_drive.setPath(m_path);
            m_pathFollower = m_drive.getPathFollower();

            m_drive.startFollowingPath();
            m_drive.resetEncoders();
            m_started = true;
         }
         else
         {
            if (m_pathFollower.isActive())
            {
               m_pathFollower.update();
            }
            else
            {
               m_drive.pathCleanup();
               setFinished(true);
            }
         }
      }      
   }

   @Override
   public String toString()
   {
      return "Path Follower";
   }

}
