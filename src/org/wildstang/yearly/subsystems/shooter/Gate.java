package org.wildstang.yearly.subsystems.shooter;

import org.wildstang.yearly.subsystems.Shooter;
import edu.wpi.first.wpilibj.Solenoid;

public class Gate extends Shooter
{

   private Solenoid m_solenoid;

   public Gate(Solenoid p_solenoid)
   {
      m_solenoid = p_solenoid;
   }

   void openGate()
   {
      m_solenoid.set(true);
   }

   void closeGate()
   {
      m_solenoid.set(false);
   }

   boolean isOpen()
   {
      boolean state = false;

      return state;
   }

   boolean isClosed()
   {
      return false;

   }
}
