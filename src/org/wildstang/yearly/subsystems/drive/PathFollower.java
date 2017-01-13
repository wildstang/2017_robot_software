package org.wildstang.yearly.subsystems.drive;

import com.ctre.CANTalon;
import com.ctre.CANTalon.MotionProfileStatus;

public class PathFollower implements Runnable
{
   
   private boolean m_running = false;

   private Path m_path;
   private CANTalon m_left;
   private CANTalon m_right;
   
   public PathFollower(Path p_path, CANTalon p_left, CANTalon p_right)
   {
      m_path = p_path;
      m_left = p_left;
      m_right = p_right;
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
   }
   
   @Override
   public void run()
   {
      MotionProfileStatus status = new MotionProfileStatus();
      while (m_running)
      {
         // Throw commands at the talon
         m_left.getMotionProfileStatus(status);
      }
   }
   
   public boolean isActive()
   {
      return m_running;
   }
   
   

}
