package org.wildstang.yearly.subsystems.shooter;

import org.wildstang.yearly.subsystems.Shooter;
import edu.wpi.first.wpilibj.Solenoid;
import org.wildstang.hardware.crio.outputs.WsSolenoid;

public class Gate extends Shooter
{

   private WsSolenoid m_solenoid;

   public Gate(WsSolenoid p_solenoid)
   {
      m_solenoid = p_solenoid;
   }

   void openGate()
   {
      m_solenoid.setValue(true);
   }

   void closeGate()
   {
      m_solenoid.setValue(false);
   }

   boolean isOpen()
   {
      // boolean state = false;
      //
      // return state;
      
      return m_solenoid.getValue();
      
   }

   boolean isClosed()
   {
      // return false;

      return !(m_solenoid.getValue());

   }
}
