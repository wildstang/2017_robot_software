package org.wildstang.yearly.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.Gear;

public class CloseGearHolderStep extends AutoStep
{
   Gear m_gearSubsystem;
   
   @Override
   public void initialize()
   {
      m_gearSubsystem = (Gear)Core.getSubsystemManager().getSubsystem(WSSubsystems.GEAR.getName());
   }

   @Override
   public void update()
   {
      m_gearSubsystem.closeGearDoor();
   }

   @Override
   public String toString()
   {
      return "Deliver Gear Step";
   }

}
