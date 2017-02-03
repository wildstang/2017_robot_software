package org.wildstang.yearly.subsystems.shooter;

import org.wildstang.yearly.subsystems.Shooter;
import org.wildstang.hardware.crio.outputs.WsSolenoid;

public class Gate extends Shooter
{
   // Needs comments
   private WsSolenoid m_solenoid;

   private boolean OPEN_STATE = true;

   // Creating a gate object so that both gates can be declared in the Shooter
   // subclass
   // as well as mutated accordingly with the functions below

   public Gate(WsSolenoid p_solenoid)
   {
      m_solenoid = p_solenoid;
   }

   // Opens the gate by setting the solenoid state to true

   public void openGate()
   {
      m_solenoid.setValue(OPEN_STATE);
   }

   // Closes the gate by setting the solenoid state to false

   public void closeGate()
   {
      m_solenoid.setValue(!OPEN_STATE);
   }

   // Returns the current state/position of the gate

   public boolean isOpen()
   {

      return m_solenoid.getValue();

   }

   // BeNo: This funcion is not really needed, should consider removing!!

   // public boolean isClosed()
   // {
   //
   // return !isOpen();
   //
   // }
}
