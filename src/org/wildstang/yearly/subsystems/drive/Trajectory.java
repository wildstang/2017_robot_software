package org.wildstang.yearly.subsystems.drive;

import java.util.ArrayList;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Trajectory {
   
   // this array is indexed as [time][3]
   // each row is [rotation, velocity, time]
   private double[][] m_trajectoryPoints;
   private ArrayList<TalonSRX.TrajectoryPoint> m_points; 
   
   public Trajectory()
   {
   }

   public double[][] getTrajectoryPoints()
   {
      return m_trajectoryPoints;
   }

   public void setTrajectoryPoints(double[][] p_trajectoryPoints)
   {
      m_trajectoryPoints = p_trajectoryPoints;
   }

   public void setTalonPoints(ArrayList<TalonSRX.TrajectoryPoint> p_points)
   {
      m_points = p_points;
   }

   public ArrayList<TalonSRX.TrajectoryPoint> getTalonPoints()
   {
      return m_points;
   }
   

}
