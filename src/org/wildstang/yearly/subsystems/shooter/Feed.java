package org.wildstang.yearly.subsystems.shooter;

import org.wildstang.yearly.subsystems.Shooter;
import org.wildstang.hardware.crio.outputs.WsVictor;

public class Feed extends Shooter
{

   private WsVictor m_victor;

   private double limit;
   private boolean jammed;

   private double m_forwardSpeed;
   private double m_backwardSpeed; // this should be negative

   public Feed(WsVictor p_victor)
   {
      m_victor = p_victor;
   }

   public boolean isJammed(double p_current)
   {
      jammed = (p_current > limit);
      return jammed;
   }

   public boolean isBallReady(boolean p_digitalInput)
   {
      return p_digitalInput;
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
