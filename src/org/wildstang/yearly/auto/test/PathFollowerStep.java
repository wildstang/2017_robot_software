package org.wildstang.yearly.auto.test;

import java.io.File;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.Drive;
import org.wildstang.yearly.subsystems.drive.Path;
import org.wildstang.yearly.subsystems.drive.PathFollower;
import org.wildstang.yearly.subsystems.drive.PathReader;
import org.wildstang.yearly.subsystems.drive.Trajectory;

public class PathFollowerStep extends AutoStep
{

   private String m_filePath;
   private Path m_path;
   private Drive m_drive;
   private PathFollower m_pathFollower;
   
   private boolean m_started = false;
   
   public PathFollowerStep(String p_path)
   {
      m_filePath = p_path;
   }
   
   @Override
   public void initialize()
   {
      m_path = new Path();
      File leftFile = new File(m_filePath + ".left");
      File rightFile = new File(m_filePath + ".right");
      
      Trajectory leftTrajectory = PathReader.readTrajectory(leftFile);
      Trajectory rightTrajectory = PathReader.readTrajectory(rightFile);

      m_path.setLeft(leftTrajectory);
      m_path.setRight(rightTrajectory);
      
      m_drive = (Drive)Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName());
   }

   @Override
   public void update()
   {
      if (!m_started)
      {
         m_drive.setPathFollowingMode();
         m_drive.setPath(m_path);
         m_pathFollower = m_drive.getPathFollower();
         m_drive.startFollowingPath();
      
         m_started = true;
         
      }
      else
      {
         if (m_pathFollower.isActive())
         {
            // Do nothing - path is running
         }
         else
         {
            m_drive.pathCleanup();
            setFinished(true);
         }
      }
      
   }

   @Override
   public String toString()
   {
      return "Path Follower";
   }

}
