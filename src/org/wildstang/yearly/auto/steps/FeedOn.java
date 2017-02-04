package org.wildstang.yearly.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.Shooter;

public class FeedOn extends AutoStep
{

   private Shooter shooter;

   public void initialize()
   {
      shooter = (Shooter) Core.getSubsystemManager().getSubsystem(WSSubsystems.SHOOTER.getName());
   }

   @Override
   public void update()
   {
      // TODO Auto-generated method stub

   }

   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return null;
   }

}
