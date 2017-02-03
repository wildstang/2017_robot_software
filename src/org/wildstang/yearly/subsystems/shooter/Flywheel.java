package org.wildstang.yearly.subsystems.shooter;

import org.wildstang.framework.core.Core;
import org.wildstang.yearly.subsystems.Shooter;

import com.ctre.CANTalon;

public class Flywheel extends Shooter
{

   private CANTalon m_talon;
   private double m_speed;

   private boolean m_running = true;

   // Creating a flywheel object so that both wheels can be declared in the
   // Shooter subclass
   // as well as mutated accordingly with the functions below

   public Flywheel(CANTalon p_talon)
   {
      m_talon = p_talon;
      m_speed = Core.getConfigManager().getConfig().getDouble("Flywheel Speed", 0);
   }

   // This function starts the flywheel up when called by a flywheel object in
   // the
   // shooter class to a mutable speed we can change later in our config page.
   // It
   // also toggles the m_running variable to true for the isRunning function

   // BENO: Will likely remove altogether

   // public void turnOn()
   // {
   // m_talon.set(m_speed);
   // m_running = true;
   // }
   //
   // // Similar to above, this function turns off the flywheel and toggles the
   // // m_running variable to false for the isRunning function.
   //
   public void turnOff()
   {
      m_talon.set(0);
      m_running = false;
   }

   // This function is used to determine the current state of the flywheel
   // and, more specifically, whether or not its running by returning the
   // toggled
   // m_running variable from turnOn and turnOff.

   public boolean isRunning()
   {
      return m_running;
   }

   // This returns the current speed of the flywheel object

   public double getSpeed()
   {
      return m_talon.getSpeed();
   }

   // This sets the current speed of the flywheel object

   public void setSpeed(double p_wheelSpeed)
   {
      m_talon.set(p_wheelSpeed);
   }

}
