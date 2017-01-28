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

   public void openGate()
   {
      m_solenoid.setValue(true);
   }

   public void closeGate()
   {
      m_solenoid.setValue(false);
   }

   public boolean isOpen()
   {

      return m_solenoid.getValue();

   }

   public boolean isClosed()
   {

      return !(m_solenoid.getValue());

   }
}
