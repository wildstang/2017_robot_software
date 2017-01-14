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
   
   private long elapsed = 0;
   private long m_last = 0;
   private long m_current = 0;
   
   private boolean m_toggle;
   private WsDoubleSolenoid m_solenoid;
   
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
      
      m_solenoid = (WsDoubleSolenoid)Core.getOutputManager().getOutput(WSOutputs.SHIFTER.getName());
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

         elapsed += (m_current - m_last);
         
         if (elapsed > 100)
         {
            m_toggle = !m_toggle;
            elapsed = 0;
         }
         m_last = m_current;
         
         if (m_toggle)
         {
            m_solenoid.setValue(WsDoubleSolenoidState.FORWARD.ordinal());
         }
         else
         {
            m_solenoid.setValue(WsDoubleSolenoidState.REVERSE.ordinal());
         }
         
      }
   }

   @Override
   public String getName()
   {
      return "PneumaticsTest";
   }

}
