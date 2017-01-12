package org.wildstang.yearly.subsystems.drive;

public class Path {

	private Track m_left;
	private Track m_right;

	private Track m_smoothPath;

	private double[][] m_waypoints;
	
	public Path(double[][] waypoints)
	{
		m_waypoints = waypoints;
	}
	

	public double[][] getWaypoints()
	{
		return m_waypoints;
	}


   public Track getLeft()
   {
      return m_left;
   }


   public void setLeft(Track p_left)
   {
      m_left = p_left;
   }


   public Track getRight()
   {
      return m_right;
   }


   public void setRight(Track p_right)
   {
      m_right = p_right;
   }


   public Track getSmoothPath()
   {
      return m_smoothPath;
   }


   public void setSmoothPath(Track p_smoothPath)
   {
      m_smoothPath = p_smoothPath;
   }
	
   
   
	
}
