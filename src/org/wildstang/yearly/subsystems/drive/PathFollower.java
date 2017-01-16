package org.wildstang.yearly.subsystems.drive;

import com.ctre.CANTalon;
import com.ctre.CANTalon.MotionProfileStatus;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PathFollower implements Runnable
{
   
   private boolean m_running = false;

   private Path m_path;
   private CANTalon m_left;
   private CANTalon m_right;
   
   private CANTalon.SetValueMotionProfile m_mpEnable = CANTalon.SetValueMotionProfile.Disable;
   private MotionProfileStatus m_leftStatus = new MotionProfileStatus();
   private MotionProfileStatus m_rightStatus = new MotionProfileStatus();

   
   public PathFollower(Path p_path, CANTalon p_left, CANTalon p_right)
   {
      m_path = p_path;
      m_left = p_left;
      m_right = p_right;

      m_left.changeMotionControlFramePeriod(5);
      m_right.changeMotionControlFramePeriod(5);

      m_notifer.startPeriodic(0.005);
   }
   
   public void start()
   {
      Thread t = new Thread(this);
      m_running = true;
      t.start();
      
   }
   
   public void stop()
   {
      m_running = false;

      /*
       * Let's clear the buffer just in case user decided to disable in the
       * middle of an MP, and now we have the second half of a profile just
       * sitting in memory.
       */
      m_left.clearMotionProfileTrajectories();
      m_right.clearMotionProfileTrajectories();
      /* When we do re-enter motionProfile control mode, stay disabled. */
      m_mpEnable = CANTalon.SetValueMotionProfile.Disable;
   }
   
   @Override
   public void run()
   {
      m_mpEnable = CANTalon.SetValueMotionProfile.Enable;

      while (m_running)
      {
         // Throw commands at the talon
         m_left.getMotionProfileStatus(m_leftStatus);
         m_right.getMotionProfileStatus(m_rightStatus);
         
         
      }
      
   }
   
   public boolean isActive()
   {
      return m_running;
   }
   
   class PeriodicRunnable implements java.lang.Runnable
   {
      public void run()
      {
         m_left.processMotionProfileBuffer();
         m_right.processMotionProfileBuffer();
      }
   }

   Notifier m_notifer = new Notifier(new PeriodicRunnable());

   
   private void fillPathBuffers()
   {
      fillPathBuffers(m_path.getLeft().getTrajectoryPoints(), m_path.getRight().getTrajectoryPoints(), m_path.getLeft().getTrajectoryPoints().length);
   }

   private void fillPathBuffers(double[][] leftProfile, double[][] rightPoints, int totalCnt)
   {

      /* create an empty point */
      CANTalon.TrajectoryPoint leftPoint = new CANTalon.TrajectoryPoint();
      CANTalon.TrajectoryPoint rightPoint = new CANTalon.TrajectoryPoint();

      /* did we get an underrun condition since last time we checked ? */
      if (m_leftStatus.hasUnderrun)
      {
         DriverStation.reportError("Left drive has underrun", false);
         m_left.clearMotionProfileHasUnderrun();
      }
      if (m_rightStatus.hasUnderrun)
      {
         DriverStation.reportError("Right drive has underrun", false);
         m_right.clearMotionProfileHasUnderrun();
      }
      
      /*
       * just in case we are interrupting another MP and there is still buffer
       * points in memory, clear it.
       */
      m_left.clearMotionProfileTrajectories();
      m_right.clearMotionProfileTrajectories();

      /* This is fast since it's just into our TOP buffer */
      for (int i = 0; i < totalCnt; ++i)
      {
         /* for each point, fill our structure and pass it to API */
         leftPoint.position = leftProfile[i][0];
         leftPoint.velocity = leftProfile[i][1];
         leftPoint.timeDurMs = (int) leftProfile[i][2];
         leftPoint.profileSlotSelect = 0; // which set of gains would you like to use?
         leftPoint.velocityOnly = false; // set true to not do any position servo, just velocity feedforward
         leftPoint.zeroPos = false;
         
         rightPoint.position = leftProfile[i][0];
         rightPoint.velocity = leftProfile[i][1];
         rightPoint.timeDurMs = (int) leftProfile[i][2];
         rightPoint.profileSlotSelect = 0; // which set of gains would you like to use?
         rightPoint.velocityOnly = false; // set true to not do any position servo, just velocity feedforward
         rightPoint.zeroPos = false;

         if (i == 0)
         {
            leftPoint.zeroPos = true; // set this to true on the first point
            rightPoint.zeroPos = true; // set this to true on the first point
         }
         
         leftPoint.isLastPoint = false;
         rightPoint.isLastPoint = false;

         if ((i + 1) == totalCnt)
         {
            leftPoint.isLastPoint = true; // set this to true on the last point
            rightPoint.isLastPoint = true; // set this to true on the last point
         }
         
         m_left.pushMotionProfileTrajectory(leftPoint);
         m_right.pushMotionProfileTrajectory(rightPoint);
      }
   }

}
