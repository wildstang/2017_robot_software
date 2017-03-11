package org.wildstang.yearly.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.Drive;

public class SetHighGearStep extends AutoStep
{
   private Drive m_drive;
   private boolean m_high;
   
   public SetHighGearStep(boolean p_high)
   {
      m_high = p_high;
   }
   
   @Override
   public void initialize()
   {
      m_drive = (Drive) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName());
   }

   @Override
   public void update()
   {
      m_drive.setHighGear(m_high);
      setFinished(true);
   }

   @Override
   public String toString()
   {
      return "Set high gear step";
   }

}
