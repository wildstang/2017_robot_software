package org.wildstang.yearly.subsystems.drive;

public class Track {
   
   // this array is indexed as [time][velocity%]
   private double[][] m_velocities;
   private double[][] m_coords;
   
   public Track()
   {
   }

   public double[][] getVelocities()
   {
      return m_velocities;
   }

   public double[][] getCoords()
   {
      return m_coords;
   }

   public void setCoords(double[][] p_coords)
   {
      m_coords = p_coords;
   }

   public void setVelocities(double[][] p_velocities)
   {
      m_velocities = p_velocities;
   }
   
   
   
   

}
