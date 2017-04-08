package org.wildstang.yearly.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.crio.outputs.WsSolenoid;
import org.wildstang.yearly.robot.CANConstants;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSOutputs;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GearV2 implements Subsystem
{
   // add variables here
   

   private boolean m_mechUpPrev;
   private boolean m_mechUpCurrent;
   private boolean m_mechUp;
   
   private boolean m_rollerIn;
   private boolean m_rollerOut;
   
   private DigitalInput m_mechUpButton;
   private DigitalInput m_rollerInButton;
   private DigitalInput m_rollerOutButton;
   private DigitalInput m_deliverGearButton;
   
   private WsSolenoid m_mechUpSolenoid;
   
   private CANTalon m_rollerTalon;
   
   private static final boolean MECH_UP = true;

   private static final boolean MECH_DOWN = !MECH_UP;

   @Override
   public void resetState()
   {
      m_mechUp = MECH_DOWN;
      
      m_mechUpPrev = false;
      m_mechUpCurrent = false;
      
      m_rollerIn = false;
      m_rollerOut = false;
   }

   @Override
   public void selfTest()
   {
   }

   @Override
   public String getName()
   { 
      return "Gear";
   }

   @Override
   public void init()
   {
	   // Register the sensors that this subsystem wants to be notified about
      m_mechUpButton = (DigitalInput)Core.getInputManager().getInput(WSInputs.GEAR_PICKUP_BUTTON.getName());
      m_mechUpButton.addInputListener(this);
      
      m_rollerInButton = (DigitalInput)Core.getInputManager().getInput(WSInputs.ROLLER_IN_BUTTON.getName());
      m_rollerInButton.addInputListener(this);
      
      m_rollerOutButton = (DigitalInput)Core.getInputManager().getInput(WSInputs.ROLLER_OUT_BUTTON.getName());
      m_rollerOutButton.addInputListener(this);

      m_deliverGearButton = (DigitalInput)Core.getInputManager().getInput(WSInputs.DELIVER_GEAR_BUTTON.getName());
      m_deliverGearButton.addInputListener(this);
      
      m_mechUpSolenoid =  (WsSolenoid)Core.getOutputManager().getOutput(WSOutputs.GEAR_TILT.getName());
      m_rollerTalon = new CANTalon(CANConstants.GEAR_ROLLER_ID);
      
      m_rollerTalon.enableBrakeMode(true);
      
      resetState();
   }

   
   @Override
   public void inputUpdate(Input source)
   { 
      // This section reads the input sensors and places them into local variables
      if (source == m_mechUpButton)
      {
    	  m_mechUpCurrent = m_mechUpButton.getValue();
    	  
        if (m_mechUpCurrent && !m_mechUpPrev)
        {
           m_mechUp = !m_mechUp;
        }
        m_mechUpPrev = m_mechUpCurrent;
      }
      
      if (source == m_rollerInButton)
      {
        m_rollerIn = m_rollerInButton.getValue();
      }
      if (source == m_rollerOutButton)
      {
         m_rollerOut = m_rollerOutButton.getValue();
      }
      if (source == m_deliverGearButton)
      {
         if (m_deliverGearButton.getValue())
         {
            deliverGear();
         }
         else
         {
            // Stop the roller running
            m_rollerOut = false;
         }
      }
   }

   @Override
   public void update()
   {
      m_mechUpSolenoid.setValue(m_mechUp);
      
      if (m_rollerIn)
      {
         m_rollerTalon.set(1);
      }
      else if (m_rollerOut)
      {
         m_rollerTalon.set(-0.2);
      }
      else
      {
         m_rollerTalon.set(0);
      }
	   
	   SmartDashboard.putBoolean("Gear back", m_mechUp);
   }
   
   public void rollerIn()
   {
      m_rollerIn = true;
      m_rollerOut = false;
   }
   
   public void rollerOut()
   {
      m_rollerIn = false;
      m_rollerOut = true;
   }
   
   public void rollerOff()
   {
      m_rollerIn = false;
      m_rollerOut = false;
   }
   
   public void deliverGear()
   {
      m_rollerOut = true;
      m_rollerIn = false;
      m_mechUp = MECH_DOWN;
   }
   
   public void mechUp()
   {
      m_mechUp = MECH_UP;
   }
   
   public void mechDown()
   {
      m_mechUp = MECH_DOWN;
   }
}
