package org.wildstang.yearly.robot.vision;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Properties;
import org.wildstang.framework.core.Core;

public class VisionHandler implements Runnable
{

   final int CORRECTION_LEVEL_INDEX = 0;
   final int DISTANCE_INDEX = 1;
   private Socket m_socket;
   private boolean m_running;
   private InputStream m_inputStream;
   private OutputStream m_outputStream;
   private VisionServer m_visionServer;

   private int h_min;
   private int s_min;
   private int v_min;
   private int h_max;
   private int s_max;
   private int v_max;
   private int center;
   private int threshold;
   private double blurRadius;
   final private int H_MIN_DEFAULT = 81;
   final private int S_MIN_DEFAULT = 0;
   final private int V_MIN_DEFAULT = 238;
   final private int H_MAX_DEFAULT = 125;
   final private int S_MAX_DEFAULT = 255;
   final private int V_MAX_DEFAULT = 255;
   final private int CENTER_DEFAULT = 400;
   final private int THRESHOLD_DEFAULT = 50;
   final private double BLUR_RADIUS_DEFAULT = 5.0;
   final private String H_MIN_KEY = "";
   final private String S_MIN_KEY = "";
   final private String V_MIN_KEY = "";
   final private String H_MAX_KEY = "";
   final private String S_MAX_KEY = "";
   final private String V_MAX_KEY = "";
   final private String CENTER_KEY = "";
   final private String THRESHOLD_KEY = "";
   final private String BLUR_RADIUS_KEY = "";

   private long m_lastMsgReceived;

   public VisionHandler(VisionServer p_server, Socket p_socket)
   {
      m_socket = p_socket;
      m_visionServer = p_server;

      h_min = Core.getConfigManager().getConfig().getInt(this.getClass().getName()
            + H_MIN_KEY, H_MIN_DEFAULT);
      s_min = Core.getConfigManager().getConfig().getInt(this.getClass().getName()
            + S_MIN_KEY, S_MIN_DEFAULT);
      v_min = Core.getConfigManager().getConfig().getInt(this.getClass().getName()
            + V_MIN_KEY, V_MIN_DEFAULT);
      h_max = Core.getConfigManager().getConfig().getInt(this.getClass().getName()
            + H_MAX_KEY, H_MAX_DEFAULT);
      s_max = Core.getConfigManager().getConfig().getInt(this.getClass().getName()
            + S_MAX_KEY, S_MAX_DEFAULT);
      v_max = Core.getConfigManager().getConfig().getInt(this.getClass().getName()
            + V_MAX_KEY, V_MAX_DEFAULT);

      center = 0;//Core.getConfigManager().getConfig().getInt(this.getClass().getName()
//            + CENTER_KEY, CENTER_DEFAULT);
      threshold = 10;//Core.getConfigManager().getConfig().getInt(this.getClass().getName()
//            + THRESHOLD_KEY, THRESHOLD_DEFAULT);
      blurRadius = Core.getConfigManager().getConfig().getDouble(this.getClass().getName()
            + BLUR_RADIUS_KEY, BLUR_RADIUS_DEFAULT);
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
      String line = null;
      int readValue;
      String delims = "[,|]";

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
         System.out.println("Camera connection: " + true);

         // We've just connected - send the required HSV values to the client
         sendPreferences(out);

         m_running = true;

         while (m_running)
         {
            // read the value sent from the client and update the current
            // value to be used
            try
            {
               line = in.readLine();

               if (line != null)
               {
                  // System.out.println("line: " + line);

                  String[] tokens = line.split(delims);
                  double[] parms = new double[tokens.length];

                  for (int i = 0; i < tokens.length; i++)
                  {
                     parms[i] = Double.parseDouble(tokens[i].trim());
                     System.out.println(parms[i]);
                  }
                  if (parms.length > 0)
                  {
                     m_visionServer.setXCorrectionLevel(parms[CORRECTION_LEVEL_INDEX]);
                     if (parms.length > 1)
                     {
                        m_visionServer.setDistance(parms[DISTANCE_INDEX]);
                     }
                  }                  

                  // System.out.println("Parms: " + parm0 + "," + parm1 +
                  // "," + parm2+ "," + parm3);
                  // System.out.println("tokens.length():" +
                  // tokens.length);
                  // System.out.println("token[0]: " + tokens[0]);
                  // System.out.println("token[1]: " + tokens[1]);
                  // System.out.println("token[2]: " + tokens[2]);
                  // System.out.println("token[3]: " + tokens[3]);

                  // readValue = Integer.parseInt(line.trim());
                  // System.out.println("Read: " + readValue);
                  // m_lastMsgReceived = System.currentTimeMillis();
                  // m_visionServer.updateValue(readValue);
               }
            }
            catch (NumberFormatException e)
            {
               e.printStackTrace();
            }
            catch (IOException e)
            {
               e.printStackTrace();
            }
         }
      }
      else
      {
         System.out.println("Camera connection: " + false);
      }

   }

   private void sendPreferences(PrintWriter p_out)
   {
      StringBuffer buf = new StringBuffer();
      Properties prefs = new Properties();

      buf.append(h_min);
      buf.append("|");
      buf.append(s_min);
      buf.append("|");
      buf.append(v_min);
      buf.append("|");
      buf.append(h_max);
      buf.append("|");
      buf.append(s_max);
      buf.append("|");
      buf.append(v_max);
      buf.append("|");
      buf.append(center);
      buf.append("|");
      buf.append(threshold);
      buf.append("|");
      buf.append(blurRadius);
      buf.append("\n");

      p_out.println(buf.toString());
      p_out.flush();
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
