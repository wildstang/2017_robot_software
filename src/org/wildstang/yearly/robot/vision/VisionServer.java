package org.wildstang.yearly.robot.vision;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionServer implements Runnable
{
   private int m_port;
   private boolean m_running;
   private ServerSocket m_serverSocket;
   
   private int m_currentValue;
   int xCorrectionLevel;
   double distance;
      
   private ArrayList<VisionHandler> m_handlers = new ArrayList<VisionHandler>();
   
   
   public VisionServer(int p_port)
   {
      m_port = p_port;
   }
   
   
   public void startVisionServer()
   {
	   SmartDashboard.putBoolean("server file entered", isRunning());
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
   
   public int getCurrentValue()
   {
      return m_currentValue;
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
            VisionHandler handler = new VisionHandler(this, s);
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
   
   
   public void updateValue(int p_value)
   {
      m_currentValue = p_value;
   }

   public void shutdown()
   {
      for (VisionHandler handler : m_handlers)
      {
         handler.stop();
      }
   }
   
   public void setXCorrectionLevel(int newVal){
      xCorrectionLevel = newVal;
   }
   
   public int getXCorrectionLevel(){
      return xCorrectionLevel;
   }
   
   public void setDistance(double newVal){
      distance = newVal;
   }
   
   public double getDistance(){
      return distance;
   }
   
}
