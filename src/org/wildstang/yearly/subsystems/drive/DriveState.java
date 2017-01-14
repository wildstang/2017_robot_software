package org.wildstang.yearly.subsystems.drive;

public class DriveState {
	
	private double deltaLeftEncoderTicks;
	private double deltaRightEncoderTicks;
	private double deltaTimeMS;
	private double headingAngle;
	private double turnRadiusInches;
	private double deltaTheta;
	
	public DriveState() {
		
	}
	
	public DriveState(double deltaTime, double deltaRightTicks, 
			double deltaLeftTicks, double heading, double turningRadius, double deltaTheta) {
		deltaTimeMS = deltaTime;
		deltaRightEncoderTicks = deltaRightTicks;
		deltaLeftEncoderTicks = deltaLeftTicks;
		headingAngle = heading;
		turnRadiusInches = turningRadius;
		this.deltaTheta = deltaTheta;
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
	
	public void setDeltaTheta(double dTheta) {
		deltaTheta = dTheta;
	}
	
	public double getDeltaTheta() {
		return deltaTheta;
	}
	
	public void setTurnRadius(double turnRadius) {
		turnRadiusInches = turnRadius;
	}
	
	public double getTurnRadius() {
		return turnRadiusInches;
	}
	
	public String toString() {
		return deltaTimeMS + ", " + deltaRightEncoderTicks + ", " 
				+ deltaLeftEncoderTicks + ", " + headingAngle + ", "
				+ turnRadiusInches + ", " + deltaTheta + "\n"; 
	}

}
