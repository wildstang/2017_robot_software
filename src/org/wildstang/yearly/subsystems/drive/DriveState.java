package org.wildstang.yearly.subsystems.drive;

public class DriveState {
	
	private double deltaLeftEncoderTicks;
	private double deltaRightEncoderTicks;
	private double deltaTimeMS;
	private double headingAngle;
	
	public DriveState() {
		
	}
	
	public DriveState(double deltaTime, double deltaRightTicks, 
			double deltaLeftTicks, double heading) {
		deltaTimeMS = deltaTime;
		deltaRightEncoderTicks = deltaRightTicks;
		deltaLeftEncoderTicks = deltaLeftTicks;
		headingAngle = heading;
	}
	
	public void setDeltaTime(double time) {
		deltaTimeMS = time;
	}
	
	public double getDeltaTime() {
		return deltaTimeMS;
	}
	
	public void setDeltaRightEncoderTicks(double ticks) {
		deltaRightEncoderTicks = ticks;
	}
	
	public double getDeltaRightEncoderTicks() {
		return deltaRightEncoderTicks;
	}
	
	public void setDeltaLeftEncoderTicks(double ticks) {
		deltaLeftEncoderTicks = ticks;
	}
	
	public double getDeltaLeftEncoderTicks() {
		return deltaLeftEncoderTicks;
	}
	
	public void setHeading(double heading) {
		headingAngle = heading;
	}
	
	public double getHeadingAngle() {
		return headingAngle;
	}

}
