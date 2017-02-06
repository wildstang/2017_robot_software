package org.wildstang.yearly.robot.vision;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class VisionHandler implements Runnable
{
   private Socket m_socket;
   private boolean m_running;
   private InputStream m_inputStream;
   private OutputStream m_outputStream;
   
   private long m_lastMsgReceived;
   
   public VisionHandler(Socket p_socket)
   {
      m_socket = p_socket;
      
   }

   public boolean isRunning()
   {
      return m_running;
   }
   
   @Override
   public void run()
   {
      BufferedReader in = null;
      PrintWriter out = null;
      
      try
      {
         m_inputStream = m_socket.getInputStream();
         in = new BufferedReader(new InputStreamReader(m_inputStream));

         m_outputStream = m_socket.getOutputStream();
         out = new PrintWriter(m_outputStream);
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      
      if (in != null && out != null)
      {
         SmartDashboard.putBoolean("Camera connection", true);

         sendPreferences(out);
         
         while (m_running)
         {
            
         }
      }
      else
      {
         SmartDashboard.putBoolean("Camera connection", false);
      }
      
   }
   
   
   private void sendPreferences(PrintWriter p_out)
   {
      StringBuffer buf = new StringBuffer();
      Preferences prefs = Preferences.getInstance();
      
      buf.append(prefs.getInt("H_min", VisionConstants.H_MIN));
      buf.append("|");
      buf.append(prefs.getInt("S_min", VisionConstants.H_MIN));
      buf.append("|");
      buf.append(prefs.getInt("V_min", VisionConstants.H_MIN));
      buf.append("|");
      buf.append(prefs.getInt("H_max", VisionConstants.H_MIN));
      buf.append("|");
      buf.append(prefs.getInt("S_max", VisionConstants.H_MIN));
      buf.append("|");
      buf.append(prefs.getInt("V_max", VisionConstants.V_MAX));
      buf.append("\n");
      
      p_out.write(buf.toString());
   }
   
   
   public void start()
   {
      m_running = true;
   }
   
   public void stop()
   {
      m_running = false;

      try
      {
         if (m_inputStream != null)
         {
            m_inputStream.close();
         }
         
         if (m_outputStream != null)
         {
            m_outputStream.close();
         }
         
         if (m_socket != null)
         {
            m_socket.close();
         }
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
   }

}
