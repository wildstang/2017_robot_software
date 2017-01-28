package org.wildstang.yearly.subsystems.shooter;

import org.wildstang.hardware.crio.outputs.WsSolenoid;
import org.wildstang.yearly.subsystems.Shooter;
import org.wildstang.hardware.crio.outputs.WsVictor;

public class Feed extends Shooter
{

   private WsVictor m_victor;
   private double m_forwardSpeed;
   private double m_backwardSpeed;
   
   public Feed(WsVictor p_victor)
   {
      m_victor = p_victor;
   }
   
   boolean isJammed()
   {
      boolean state = false;

      return state;

   }

   void runForward()
   {
      m_victor.setValue(m_forwardSpeed);
   }

   void runBackwards()
   {
      m_victor.setValue(m_backwardSpeed);
   }

   void stop()
   {
      m_victor.setValue(0);
   }
}
