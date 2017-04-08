package org.wildstang.yearly.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.GearV2;

public class DeliverGearStep extends AutoStep
{
   GearV2 m_gearSubsystem;
   
   @Override
   public void initialize()
   {
      m_gearSubsystem = (GearV2)Core.getSubsystemManager().getSubsystem(WSSubsystems.GEAR.getName());
   }

   @Override
   public void update()
   {
      m_gearSubsystem.deliverGear();
      setFinished(true);
   }

   @Override
   public String toString()
   {
      return "Deliver Gear Step";
   }

}
