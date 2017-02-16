package org.wildstang.yearly.subsystems.shooter;

import org.wildstang.yearly.subsystems.Shooter;
import org.wildstang.framework.core.Core;
import org.wildstang.hardware.crio.outputs.WsVictor;

public class Feed extends Shooter
{

   private WsVictor m_victor;
   private double feedSpeed;

   private double limit = 30;
   private boolean jammed = false;

   private boolean ballReady;

   // Creating a feeder object so that both feeder belts can be declared in the
   // Shooter subclass
   // as well as mutated accordingly with the functions below

   public Feed(WsVictor p_victor, double p_speed)
   {
      m_victor = p_victor;
   }

   // This function is setup in the shooter class to determine whether or not
   // the belt is jammed testing if the voltage out to that port is higher than
   // is usual voltage pull

   public boolean isJammed(double p_current)
   {
      if (p_current > limit)
      {
         jammed = true;
      }
      else
      {
         jammed = false;
      }

      // TODO
      return false;
   }

   // This function makes the motors on the belts run in a positive rotation

   public void runForward()
   {
      m_victor.setValue(feedSpeed);
   }

   // Basically does the same as the function above, but in reverse

   public void runBackwards()
   {
      m_victor.setValue(-feedSpeed);
   }

   // This function turns the motors off

   public void stop()
   {
      m_victor.setValue(0);
   }

   // This function may or may not be used. Either way, the purpose of this
   // function is
   // to determine whether or not a ball is ready based on possible sensors
   // (emphasis on possible)

   public boolean isBallReady(boolean p_digitalInput)
   {
      if (p_digitalInput)
      {
         ballReady = true;
      }
      else
      {
         ballReady = false;
      }
      return ballReady;
   }

   // gets speed for testing.
   public double getSpeed()
   {
      return m_victor.getValue();
   }

}
