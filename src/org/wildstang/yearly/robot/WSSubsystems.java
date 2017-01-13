package org.wildstang.yearly.robot;

import org.wildstang.framework.core.Subsystems;
import org.wildstang.yearly.subsystems.Drive;
import org.wildstang.yearly.subsystems.ShooterTest;
import org.wildstang.yearly.subsystems.LED;

public enum WSSubsystems implements Subsystems
{

   //DO NOT REMOVE THIS COMMENT.  DO NOT PLACE ANY ENUMERATION DEFINITIONS IN FRONT OF IT.
   //This keeps the formatter from completely making the enumeration unreadable.
   // @formatter::off
//   MONITOR("Monitor", Monitor.class),
   DRIVE_BASE("Drive Base", Drive.class),
   LED("LEDs", LED.class),
   SHOOTER_TEST("Shooter Test", ShooterTest.class);
   
   //DO NOT REMOVE THIS COMMENT.  DO NOT PLACE ANY ENUMERATION DEFINITIONS AFTER IT.
   //This keeps the formatter from completely making the enumeration unreadable.
   // @formatter::on
   
   private String m_name;
   private Class m_class;

   WSSubsystems(String p_name, Class p_class)
   {
      m_name = p_name;
      m_class = p_class;
   }

   @Override
   public String getName()
   {
      return m_name;
   }

   @Override
   public Class getSubsystemClass()
   {
      return m_class;
   }

}
