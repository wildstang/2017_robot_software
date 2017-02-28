package org.wildstang.yearly.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.crio.outputs.WsSolenoid;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.yearly.robot.WSOutputs;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Gear implements Subsystem
{
   // add variables here
   
   private boolean m_holdGear;
   private boolean m_tiltGear;

   private boolean m_gearPrev;
   private boolean m_gearCurrent;
   private boolean m_gearTiltToggle;
   
   private boolean m_doorPrev;
   private boolean m_doorCurrent;
   private boolean m_doorToggle;
   
   private DigitalInput m_tiltButton;
   private DigitalInput m_doorButton;
   
   private WsSolenoid m_tiltSolenoid;
   private WsSolenoid m_doorSolenoid;
   
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
      m_tiltButton = (DigitalInput)Core.getInputManager().getInput(WSInputs.GEAR_TILT_BUTTON.getName());
      m_tiltButton.addInputListener(this);
      
      m_doorButton = (DigitalInput)Core.getInputManager().getInput(WSInputs.GEAR_HOLD_BUTTON.getName());
      m_doorButton.addInputListener(this);
      
      m_tiltSolenoid =  (WsSolenoid)Core.getOutputManager().getOutput(WSOutputs.GEAR_TILT.getName());
      m_doorSolenoid = (WsSolenoid)Core.getOutputManager().getOutput(WSOutputs.GEAR_HOLD.getName());
   }

   @Override
   public void inputUpdate(Input source)
   { 
      // This section reads the input sensors and places them into local variables
      if (source == m_tiltButton)
      {
    	  m_gearCurrent = m_tiltButton.getValue();
    	  
        if (m_gearCurrent && !m_gearPrev)
        {
           m_gearTiltToggle = !m_gearTiltToggle;
        }
        m_gearPrev = m_gearCurrent;
    	  setTiltGear(m_gearTiltToggle);
      }
      
      if (source == m_doorButton)
      {
        m_doorCurrent = m_doorButton.getValue();
        
        if (m_doorCurrent && !m_doorPrev)
        {
           m_doorToggle = !m_doorToggle;
        }
        m_doorPrev = m_doorCurrent;
    	  setHoldGear(m_doorToggle);
      }
   }

   @Override
   public void update()
   {
      m_tiltSolenoid.setValue(m_tiltGear);
      m_doorSolenoid.setValue(m_holdGear);
	   
	   SmartDashboard.putBoolean("HoldGear", m_holdGear);
	   SmartDashboard.putBoolean("TiltGear", m_tiltGear);
   }
   
   public void setHoldGear(boolean p_holdGear)
   {
      
	  m_holdGear = p_holdGear;
      
   }
   
   public void setTiltGear(boolean p_tiltGear)
   {
     
	   m_tiltGear = p_tiltGear;

   }
}
