package org.wildstang.yearly.subsystems.drive;

public class PathFollower implements Runnable
{
   
   private boolean m_running = false;

   private Path m_path;
   
   public PathFollower(Path p_path)
   {
      m_path = p_path;
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
      while (m_running)
      {
         // Throw commands at the talon
      }
   }
   
   public boolean isActive()
   {
      return m_running;
   }
   
   

}
