package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.yearly.auto.steps.DeliverGearStep;

public class DeliverGearCenter extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      // TODO Add Path
      addStep(new DeliverGearStep());
   }

   @Override
   public String toString()
   {
      return "Deliver Gear Center";
   }

}