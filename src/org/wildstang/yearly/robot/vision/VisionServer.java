package org.wildstang.yearly.robot.vision;

public class VisionServer implements Runnable
{
   private int m_port;
   private boolean m_running;
   
   public VisionServer(int p_port)
   {
      m_port = p_port;
      
      init();
   }
   
   
   private void init()
   {
      // Create Server Socket
      
      // Start thread
   }
   
   public void run()
   {
      // Listen for clients
      
      // Create new VisionHandler for requests
      
      
   }
   
}
