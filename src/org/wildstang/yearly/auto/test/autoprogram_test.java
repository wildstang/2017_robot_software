package org.wildstang.yearly.auto.test;

import org.wildstang.framework.auto.AutoProgram;

public class autoprogram_test extends AutoProgram {

	@Override
	protected void defineSteps() {
		// TODO Auto-generated method stub
		addStep(new autostep_test());
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "autoprogram_test";
	}

}
