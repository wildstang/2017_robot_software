package org.wildstang.yearly.subsystems.drive;

public class Track {
   
   // this array is indexed as [time][velocity%]
   private double[][] m_trajectoryPoints;
   
   public Track()
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

   
   

}
