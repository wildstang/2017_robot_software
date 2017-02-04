package org.wildstang.yearly.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.Shooter;

//This is an autonomous step which turns on the belt/feed 

public class FeedOnStep extends AutoStep
{
   private Shooter shooter;

   public void initialize()
   {
      shooter = (Shooter) Core.getSubsystemManager().getSubsystem(WSSubsystems.SHOOTER.getName());
   }

   @Override
   public void update()
   {
      shooter.turnFeedOn();
   }

   @Override
   public String toString()
   {
      return "Feed On Step";
   }

}
