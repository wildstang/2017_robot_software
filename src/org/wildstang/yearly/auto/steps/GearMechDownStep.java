package org.wildstang.yearly.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.GearV2;

public class GearMechDownStep extends AutoStep
{
   private GearV2 m_gearSubsystem;
   
   @Override
   public void initialize()
   {
      m_gearSubsystem = (GearV2)Core.getSubsystemManager().getSubsystem(WSSubsystems.GEAR.getName());
   }

   @Override
   public void update()
   {
      m_gearSubsystem.mechDown();
      setFinished(true);
   }

   @Override
   public String toString()
   {
      return "Gear mech up step";
   }

}
