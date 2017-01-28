package org.wildstang.yearly.subsystems.shooter;

import org.wildstang.yearly.subsystems.Shooter;

import com.ctre.CANTalon;

public class Flywheel extends Shooter {
	
	private CANTalon m_talon;
	
	public Flywheel (CANTalon p_talon){
		m_talon = p_talon;
		
	}
	
	void turnOn() {
		
	}

	
    void turnOff() {
		
	}
    
    
    boolean getState() {
    	
    	boolean state = false;
    	
    	return state;
    }
    
    
    public double getSpeed() {
    	return m_talon.getSpeed();
    }
    
    
    void setSpeed( double p_wheel  ) {
    	
    	
    }


}
