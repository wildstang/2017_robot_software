package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.auto.steps.FeedOffStep;
import org.wildstang.yearly.auto.steps.FeedOnStep;
import org.wildstang.yearly.auto.steps.WaitStep;
import org.wildstang.yearly.robot.WSSubsystems;
import org.wildstang.yearly.auto.steps.ShootStep;
import org.wildstang.yearly.subsystems.Shooter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.robot.WSSubsystems;

public class HopperShootsBallsRed extends AutoProgram
{
   private String someString = this.getClass().getName() + ".hopperWaitTime";

   private double hopperWaitTime = Core.getConfigManager().getConfig().getDouble(this.getClass().getName()
            + ".hopperWaitTime", 10000);

   @Override
   protected void defineSteps()
   {

      // TODO Add path to hopper here

      addStep(new FeedOnStep());

      addStep(new WaitStep(hopperWaitTime));

      addStep(new FeedOffStep());

      // TODO Add path to boiler here

      addStep(new ShootStep());
   }

   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return "Hopper-Shoot Red";
   }

}
