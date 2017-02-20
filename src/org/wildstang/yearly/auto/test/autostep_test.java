package org.wildstang.yearly.auto.test;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.yearly.robot.WSSubsystems;
//import org.wildstang.yearly.subsystems.Drive;
import org.wildstang.yearly.subsystems.Subsystem_Test;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class autostep_test extends AutoStep {

	// private Drive m_drive;
	private Subsystem_Test m_subsystemTest;

	@Override
	public void initialize() {
		// // TODO Auto-generated method stub
		// //m_drive =
		// (Drive)Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE_BASE.getName());
		m_subsystemTest = ((Subsystem_Test) Core.getSubsystemManager().getSubsystem(WSSubsystems.SUBSYSTEM_TEST_PP.getName()));

		SmartDashboard.putBoolean("Step Test", true);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		boolean limitSwitchPressed;
		boolean buttonPressed;

		limitSwitchPressed = m_subsystemTest.autoLimitSwitchPressed();
		buttonPressed = m_subsystemTest.autoButtonPressed();

		SmartDashboard.putBoolean("buttonPressed", buttonPressed);
		SmartDashboard.putBoolean("limitPressed", limitSwitchPressed);

		SmartDashboard.putBoolean("Step Test", buttonPressed);

		if (buttonPressed || limitSwitchPressed) {
			m_subsystemTest.autoLedSet(true);
			//setFinished(true);
		} else {
			m_subsystemTest.autoLedSet(false);
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "autostep_test";
	}

}
