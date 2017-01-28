package org.wildstang.yearly.subsystems.shooter;

import org.wildstang.hardware.crio.outputs.WsSolenoid;
import org.wildstang.yearly.subsystems.Shooter;
import org.wildstang.hardware.crio.outputs.WsVictor;

public class Feed extends Shooter
{

   private WsVictor m_victor;
   
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

   }

   void runBackwards()
   {

   }

   void stop()
   {

   }
}
