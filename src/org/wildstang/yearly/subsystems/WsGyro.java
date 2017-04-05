package org.wildstang.yearly.subsystems;

import javax.swing.Timer;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.crio.inputs.WsI2CInput;
import org.wildstang.yearly.robot.WSInputs;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WsGyro implements Subsystem{
   
   Gyro m_gyro = new AnalogGyro(0); // Still need input type for gyro, this works for now
   double angle = 0;
   double startTime;

   private final double DRIFT_PER_NANO_FIXED = .903; //GOOD DEFAULT VALUE TO USE
   
   private double DRIFT_PER_NANO = DRIFT_PER_NANO_FIXED;
   
   boolean firstRun = true;
   
   private String m_name;
   
   public WsGyro()
   {
      m_name = "WsGyro";
   }

   @Override
   public void inputUpdate(Input p_source) {
    
   }

   @Override
   public void init() {
       m_gyro.calibrate();
       startTime = System.nanoTime();
   }
   
   

   @Override
   public void resetState()
   {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void selfTest() {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void update() {
      if (firstRun) {
         DRIFT_PER_NANO = m_gyro.getAngle() / (System.nanoTime() - startTime);
         firstRun = false;
      }
      
      angle = m_gyro.getAngle() - getAdjustment();;
      SmartDashboard.putNumber("Gyro Heading", m_gyro.getAngle());
      SmartDashboard.putNumber("Robot Angle", angle);
   }
   
   private double getAdjustment() {
      return  (System.nanoTime() - startTime) * DRIFT_PER_NANO;
   }

   
   public double getAngle() {
      return angle;
   }
   
   @Override
   public String getName() {
      // TODO Auto-generated method stub
      return "WsGyro";
   }

}