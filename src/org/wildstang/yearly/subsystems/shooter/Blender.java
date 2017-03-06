package org.wildstang.yearly.subsystems.shooter;

import org.wildstang.framework.core.Core;
import org.wildstang.hardware.crio.outputs.WsVictor;

public class Blender
{
   private WsVictor m_victor;
   private double m_speed;
   
   public Blender(WsVictor p_victor)
   {
      m_victor = p_victor;
      m_speed = Core.getConfigManager().getConfig().getDouble(this.getClass().getName() + ".blenderSpeed", 1.0);
   }
   
   
   public void turnOn()
   {
      m_victor.setValue(m_speed);
   }
   
   public void turnOff()
   {
      m_victor.setValue(0);
   }
   
}
