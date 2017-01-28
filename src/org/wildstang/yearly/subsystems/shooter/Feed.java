package org.wildstang.yearly.subsystems.shooter;

import org.wildstang.hardware.crio.outputs.WsSolenoid;
import org.wildstang.yearly.subsystems.Shooter;
import org.wildstang.hardware.crio.outputs.WsVictor;

public class Feed extends Shooter
{

   private WsVictor m_victor;
   
   private double limit;
   private boolean jammed;
   
   private double m_forwardSpeed;
   private double m_backwardSpeed;

   public Feed(WsVictor p_victor)
   {
      m_victor = p_victor;
   }

   public boolean isJammed(double p_current)
   {
      if (p_current > limit)
      {
         jammed = true;
      }
      else
      {
         jammed = false;
      }

      return jammed;
   }

   public void runForward()
   {
      m_victor.setValue(m_forwardSpeed);
   }

   public void runBackwards()
   {
      m_victor.setValue(m_backwardSpeed);
   }

   public void stop()
   {
      m_victor.setValue(0);
   }
}
