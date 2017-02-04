package org.wildstang.yearly.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.Shooter;

public class OpenGateStep extends AutoStep
{
   private Shooter shooter;
   private boolean gatesOpen = false;
   
   @Override
   public void initialize()
   {
      shooter = (Shooter)Core.getSubsystemManager().getSubsystem(WSSubsystems.SHOOTER.getName());
   }

   @Override
   public void update()
   {
      

   }

   @Override
   public String toString()
   {
     
      return "Open gate step";
   }

}
