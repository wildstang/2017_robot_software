package org.wildstang.yearly.auto.testprograms;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.yearly.auto.programs.PathNameConstants;
import org.wildstang.yearly.auto.steps.PathFollowerStep;

public class TESTHopperBackupPath extends AutoProgram
{

   @Override
   protected void defineSteps()
   {
      addStep(new PathFollowerStep(PathNameConstants.BACKUP_FROM_HOPPER));
   }
   
   @Override
   public String toString()
   {
      return "TEST - 10ft straight line";
   }


}
