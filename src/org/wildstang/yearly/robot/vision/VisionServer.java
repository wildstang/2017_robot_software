package org.wildstang.yearly.robot.vision;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionServer implements Runnable
{
   private int m_port;
   private boolean m_running;
   private ServerSocket m_serverSocket;
   
   private VisionMessage m_message;
   private VisionParams m_params;
   
   private ArrayList<VisionHandler> m_handlers = new ArrayList<VisionHandler>();
   
   public VisionServer(int p_port)
   {
      m_port = p_port;
      
      startVisionServer();
   }
   
   
   public void startVisionServer()
   {
      // Create Server Socket
      try
      {
         m_serverSocket = new ServerSocket(m_port);
      }
      catch (IOException e)
      {
         
         e.printStackTrace();
      }
      
      // Start thread
      if (m_serverSocket != null)
      {
         Thread t = new Thread(this);
         t.start();
         m_running = true;
      }
   }

   public boolean isRunning()
   {
      return m_running;
   }
   
   
   
   public void run()
   {
      // Listen for clients
      while (m_running)
      {
         // Accept new connection
         Socket s;
         try
         {
            s = m_serverSocket.accept();

            // Create new VisionHandler for requests
            VisionHandler handler = new VisionHandler(s);
            SmartDashboard.putString("Vision server connected to", s.getInetAddress().getHostAddress());

            if (s.getInetAddress().getHostAddress().equals("10.1.11.10"))
            {
               SmartDashboard.putBoolean("Camera connected", true);
            }
            else
            {
               SmartDashboard.putBoolean("Camera connected", false);
            }
            
            Thread t = new Thread(handler);
            t.start();
            
            m_handlers.add(handler);
         }
         catch (IOException e)
         {
            e.printStackTrace();
         }
         
      }
   }
   

   public void shutdown()
   {
      for (VisionHandler handler : m_handlers)
      {
         handler.stop();
      }
   }
   
}
