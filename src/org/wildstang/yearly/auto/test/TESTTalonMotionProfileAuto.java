package org.wildstang.yearly.auto.test;

import java.io.File;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.auto.steps.PathFollowerStep;

public class TESTTalonMotionProfileAuto extends AutoProgram
{

	boolean isRed;
	
	
   @Override
   protected void defineSteps()
   { 
//	  isRed = false; // Need to change this to a Driver Station input 
//	  String auto;
//	  if (isRed) {
//		  auto = Core.getConfigManager().getConfig().getString("boiler.to.hopper.auto.red", "defaultRed");
//	  } else {
//	      auto = Core.getConfigManager().getConfig().getString("boiler.to.hopper.auto.blue", "defaultBlue");
//	  }
//	  
//	  String autoRight = auto + ".right";
//	  String autoLeft = auto + ".left";
//	  
//	  addStep(new PathFollowerStep(autoRight));
//      addStep(new PathFollowerStep(autoLeft));

	   addStep(new PathFollowerStep("/home/lvuser/path.test.s.curve"));
   }

   @Override
   public String toString()
   {
      return "PathFollower";
   }

}
