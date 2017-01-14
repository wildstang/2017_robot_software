package org.wildstang.yearly.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoid;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoidState;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSOutputs;


public class PneumaticsTest implements Subsystem
{

   private boolean m_buttonPressed;
   private DigitalInput m_buttonInput;
   
   private long m_elapsed = 0;
   private long m_last = 0;
   private long m_current = 0;
   
   private boolean m_toggle;
   private WsDoubleSolenoid m_solenoid1;
   private WsDoubleSolenoid m_solenoid2;
   
   private boolean m_first = true;
   
   @Override
   public void inputUpdate(Input p_source)
   {
      if (p_source == m_buttonInput)
      {
         m_buttonPressed = m_buttonInput.getValue();
      }
   }

   @Override
   public void init()
   {
      m_buttonInput = (DigitalInput)Core.getInputManager().getInput(WSInputs.DRV_BUTTON_2.getName());
      m_buttonInput.addInputListener(this);
      
      m_solenoid1 = (WsDoubleSolenoid)Core.getOutputManager().getOutput(WSOutputs.SHIFTER.getName());
      m_solenoid2 = (WsDoubleSolenoid)Core.getOutputManager().getOutput(WSOutputs.SOLENOID2.getName());
   }

   @Override
   public void selfTest()
   {
   }

   @Override
   public void update()
   {
      if (m_buttonPressed)
      {
         m_current = System.currentTimeMillis();
         if (m_first)
         {
            m_first = false;
            m_last = m_current;
         }
         m_elapsed += (m_current - m_last);
         
         if (m_elapsed > 100)
         {
            m_toggle = !m_toggle;
            m_elapsed = 0;
         }
         m_last = m_current;
         
         if (m_toggle)
         {
            m_solenoid1.setValue(WsDoubleSolenoidState.FORWARD.ordinal());
            m_solenoid2.setValue(WsDoubleSolenoidState.REVERSE.ordinal());
         }
         else
         {
            m_solenoid1.setValue(WsDoubleSolenoidState.REVERSE.ordinal());
            m_solenoid2.setValue(WsDoubleSolenoidState.FORWARD.ordinal());
         }
         
      }
   }

   @Override
   public String getName()
   {
      return "PneumaticsTest";
   }

}
