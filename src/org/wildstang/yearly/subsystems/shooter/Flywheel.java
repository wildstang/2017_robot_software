package org.wildstang.yearly.subsystems.shooter;

import org.wildstang.yearly.subsystems.Shooter;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;

public class Flywheel extends Shooter
{

   private CANTalon m_talon;

   private double m_speed;

   private double p; // Proportional
   private double i; // Integral
   private double d; // Derivative

   public Flywheel(CANTalon p_talon)
   {
      m_talon = p_talon;
      m_talon.setPID(p, i, d); // Set the PID constants (p, i, d)

   }

   public void turnOn()
   {
      m_talon.set(m_speed);
   }

   public void turnOff()
   {
      m_talon.set(0);
   }

   public boolean isRunning()
   {
      return m_talon.isEnabled();
   }

   public double getSpeed()
   {
      return m_talon.getSpeed();
   }

   public void setSpeed(double p_wheelSpeed)
   {
      m_talon.set(p_wheelSpeed);
   }

   public void setTalonPID()
   {

      m_talon.setPID(p, i, d); // Set the PID constants (p, i, d)

   }

}
