package org.wildstang.yearly.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.Shooter;

// This is an autonomous step which turns off the belt/feed 

public class FeedOffStep extends AutoStep
{
   private Shooter shooter;

   public void initialize()
   {
      shooter = (Shooter) Core.getSubsystemManager().getSubsystem(WSSubsystems.SHOOTER.getName());
   }

   @Override
   public void update()
   {
      shooter.turnFeedOff();
   }

   @Override
   public String toString()
   {
      return "Feed Off Step";
   }

}
