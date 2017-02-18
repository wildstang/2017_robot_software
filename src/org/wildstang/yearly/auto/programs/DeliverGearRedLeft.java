package org.wildstang.yearly.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.yearly.auto.steps.DeliverGearStep;

public class DeliverGearRedLeft extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      // TODO Add path steps
      addStep(new DeliverGearStep());
   }

   @Override
   public String toString()
   {
      return "Deliver Gear - Red Left";
   }

}
