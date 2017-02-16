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
      Core.getInputManager().getInput(WSInputs.GEAR_TILT_BUTTON.getName()).addInputListener(this);
      Core.getInputManager().getInput(WSInputs.GEAR_HOLD_BUTTON.getName()).addInputListener(this);
   }

   @Override
   public void inputUpdate(Input source)
   { 
	   boolean l_tiltButton;
	   boolean l_holdButton;

      // This section reads the input sensors and places them into local variables
      
      if (source.getName().equals(WSInputs.GEAR_TILT_BUTTON.getName()))
      {
    	  l_tiltButton = ((DigitalInput) source).getValue();
    	  
    	  setTiltGear(l_tiltButton);
      }
      
      if (source.getName().equals(WSInputs.GEAR_HOLD_BUTTON.getName()))
      {
    	  l_holdButton = ((DigitalInput) source).getValue();
    	  
    	  setHoldGear(l_holdButton);
      }
   }

   @Override
   public void update()
   {
      ((WsSolenoid)Core.getOutputManager().getOutput(WSOutputs.GEAR_HOLD.getName())).setValue(m_holdGear);
	   ((WsSolenoid)Core.getOutputManager().getOutput(WSOutputs.GEAR_TILT.getName())).setValue(m_tiltGear);
	   
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
