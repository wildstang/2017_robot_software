package org.wildstang.yearly.subsystems.drive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class PathReader
{

   public static Track[] readTracks(File p_path)
   {
      Track[] tracks = new Track[2];
      tracks[0] = new Track();
      tracks[1] = new Track();
      boolean left = true;

      ArrayList<String> leftRaw = new ArrayList<String>();
      ArrayList<String> rightRaw = new ArrayList<String>();
      
      // Open the file
      BufferedReader reader = null;

      try
      {
         reader = new BufferedReader(new FileReader(p_path));
      }
      catch (FileNotFoundException e)
      {
         e.printStackTrace();
      }
      
      if (reader != null)
      {
         String line;
         try
         {
            // Read all the lines.  Sort into left and right paths
            while ((line = reader.readLine()) != null)
            {
               if (line.equals("-"))
               {
                  left = false;
               }
               else
               {
                  if (left)
                  {
                     leftRaw.add(line);
                  }
                  else
                  {
                     rightRaw.add(line);
                  }
               }
            }
         }
         catch (IOException e)
         {
            e.printStackTrace();
         }
      }
      
      double[][] leftPoints = new double[leftRaw.size()][];
      double[][] rightPoints = new double[rightRaw.size()][];
      
      // Parse into numbers
      for (int i = 0; i < leftRaw.size(); i++)
      {
         String tempLine = leftRaw.get(i);
         StringTokenizer st = new StringTokenizer(tempLine, ",\n");

         leftPoints[i] = new double[3];
         double rotations = Double.parseDouble(st.nextToken());
         double velocity = Double.parseDouble(st.nextToken());
         double interval = Double.parseDouble(st.nextToken());
         
         leftPoints[i][0] = rotations;
         leftPoints[i][1] = velocity;
         leftPoints[i][2] = interval;
      }
      
      for (int i = 0; i < rightRaw.size(); i++)
      {
         String tempLine = rightRaw.get(i);
         StringTokenizer st = new StringTokenizer(tempLine, ",\n");

         rightPoints[i] = new double[3];
         double rotations = Double.parseDouble(st.nextToken());
         double velocity = Double.parseDouble(st.nextToken());
         double interval = Double.parseDouble(st.nextToken());
         
         rightPoints[i][0] = rotations;
         rightPoints[i][1] = velocity;
         rightPoints[i][2] = interval;
      }
   
      tracks[0].setTrajectoryPoints(leftPoints);
      tracks[1].setTrajectoryPoints(rightPoints);
      
      return tracks;
   }
}
