package org.wildstang.yearly.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.WaitCommand;

public class WaitStep extends AutoStep
{
   private Shooter shooter;
   private long startTime;
   private double timePassed;
   private double targetTime;

   public void initialize()
   {
      shooter = (Shooter) Core.getSubsystemManager().getSubsystem(WSSubsystems.SHOOTER.getName());
      startTime = System.currentTimeMillis();
      // Default is 10 seconds for testing
      targetTime = Core.getConfigManager().getConfig().getDouble("waitStep", 10000);
   }

   @Override
   public void update()
   {
      timePassed = (double) (System.currentTimeMillis() - startTime);

      if (timePassed == targetTime)
      {
         setFinished(true);
      }

   }

   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return "Wait Step";
   }

}
