package org.wildstang.yearly.subsystems.shooter;

import org.wildstang.yearly.subsystems.Shooter;

import com.ctre.CANTalon;

public class Flywheel extends Shooter {
	
	private CANTalon m_talon;
	private double m_speed;
	
	public Flywheel (CANTalon p_talon){
		m_talon = p_talon;
		
	}
	
	public void turnOn() {
		m_talon.set(m_speed);
	}

	
    public void turnOff() {
		
	}
    
    
    public boolean getState() {
    	
    	boolean state = false;
    	
    	return state;
    }
    
    
    public double getSpeed() {
    	return m_talon.getSpeed();
    }
    
    
    public void setSpeed( double p_wheel  ) {
    	
    	
    }


}
