package org.wildstang.yearly.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.yearly.robot.CANConstants;
import org.wildstang.yearly.robot.WSInputs;
import org.wildstang.hardware.crio.outputs.WsVictor;
import org.wildstang.yearly.robot.WSOutputs;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.StatusFrameRate;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TalonTest implements Subsystem
{

   private CANTalon m_shooter;
   
   
   private boolean m_inc50prev;
   private boolean m_dec50prev;
   private boolean m_inc10prev;
   private boolean m_dec10prev;
   
   private boolean m_inc50curr;
   private boolean m_dec50curr;
   private boolean m_inc10curr;
   private boolean m_dec10curr;

   private DigitalInput m_up50Input;
   private DigitalInput m_down50Input;
   private DigitalInput m_up10Input;
   private DigitalInput m_down10Input;
   private DigitalInput m_shooterOnInput;
   private DigitalInput m_shooterPosInput;
   
   private AnalogInput m_throttleInput;

   private double m_throttle;
   
   private double m_currentSpeed = 50;
   
   
   private boolean m_speedModeOn;
   private boolean m_shooterOnCurr;
   private boolean m_shooterPrev;
   
   
   
   StringBuilder m_sb = new StringBuilder();
   int m_loops = 0;

   @Override
   public void inputUpdate(Input p_source)
   {
      if (p_source == m_up50Input)
      {
         m_inc50curr = m_up50Input.getValue();
      }
      else if (p_source == m_up10Input)
      {
         m_inc10curr = m_up10Input.getValue();
      }
      else if (p_source == m_down50Input)
      {
         m_dec50curr = m_down50Input.getValue();
      }
      else if (p_source == m_down10Input)
      {
         m_dec10curr = m_down10Input.getValue();
      }
      else if (p_source == m_shooterOnInput)
      {
         m_shooterOnCurr = m_shooterOnInput.getValue();
      }
//      else if (p_source == m_shooterPosInput)
//      {
//         m_holdPos = m_shooterPosInput.getValue();
//      }
      else if (p_source == m_throttleInput)
      {
         m_throttle = m_throttleInput.getValue();
      }
   }

   @Override
   public void init()
   {
      m_shooter = new CANTalon(5);

      m_shooter.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
      m_shooter.setStatusFrameRateMs(StatusFrameRate.Feedback, 10);

//      m_shooter.reverseSensor(true);
//      m_shooter.reverseOutput(true);
      
      m_shooter.setEncPosition(0);
      
      m_shooter.configNominalOutputVoltage(+0.0f,  -0.0f);
      m_shooter.configPeakOutputVoltage(+12.0f,  0.0f);
      m_shooter.setVoltageRampRate(24.0);  // Max spinup of 24V/s - start here

      // Set up closed loop PID control gains in slot 0
      m_shooter.setProfile(0);
      m_shooter.setF(1);      // 0.1998
      m_shooter.setP(0);      //(10% X 1023) / (error) 
      m_shooter.setI(0);
      m_shooter.setD(0);
      
      m_up50Input = (DigitalInput)Core.getInputManager().getInput(WSInputs.SPEED_UP_50.getName());
      m_up50Input.addInputListener(this);
      m_up10Input = (DigitalInput)Core.getInputManager().getInput(WSInputs.SPEED_UP_10.getName());
      m_up10Input.addInputListener(this);
      m_down50Input = (DigitalInput)Core.getInputManager().getInput(WSInputs.SPEED_DOWN_50.getName());
      m_down50Input.addInputListener(this);
      m_down10Input = (DigitalInput)Core.getInputManager().getInput(WSInputs.SPEED_DOWN_10.getName());
      m_down10Input.addInputListener(this);
      
      m_shooterOnInput = (DigitalInput)Core.getInputManager().getInput(WSInputs.DRV_BUTTON_2.getName());
      m_shooterOnInput.addInputListener(this);

//      m_shooterPosInput = (DigitalInput)Core.getInputManager().getInput(WSInputs.DRV_BUTTON_4.getName());
//      m_shooterPosInput.addInputListener(this);
      
      m_throttleInput = (AnalogInput)Core.getInputManager().getInput(WSInputs.DRV_THROTTLE.getName());
      m_throttleInput.addInputListener(this);
      
      
      
      SmartDashboard.putNumber("MAX_RPM", 0);
      
   }

   @Override
   public void selfTest()
   {
   }

   @Override
   public void update()
   {
      long start = System.currentTimeMillis();
      // Increment/decrement target speed
      if (m_inc50curr && !m_inc50prev)
      {
         m_currentSpeed += 50;
      }
      else if (m_inc10curr && !m_inc10prev)
      {
         m_currentSpeed += 10;
      }
      else if (m_dec50curr && !m_dec50prev)
      {
         m_currentSpeed -= 50;
      }
      else if (m_dec10curr && !m_dec10prev)
      {
         m_currentSpeed -= 10;
      }
      // Toggle speed mode
      if (m_shooterOnCurr && !m_shooterPrev)
      {
         m_speedModeOn = !m_speedModeOn;
      }
      m_shooterPrev = m_shooterOnCurr;
      m_inc50prev = m_inc50curr;
      m_inc10prev = m_inc10curr;
      m_dec50prev = m_dec50curr;
      m_dec10prev = m_dec10curr;
      
      
      double motorOutput = m_shooter.getOutputVoltage() / m_shooter.getBusVoltage();
      m_sb.append("\t%Vout: ");
      m_sb.append(motorOutput);
      m_sb.append("\tspeed: ");
      m_sb.append(m_shooter.getSpeed());

      
      if (m_speedModeOn)
      {
         m_shooter.changeControlMode(TalonControlMode.Speed);
         // Set the speed
//         m_currentSpeed = m_throttle * MAX_RPM;
         m_shooter.set(m_currentSpeed);

         ((WsVictor)Core.getOutputManager().getOutput(WSOutputs.LEFT_1.getName())).setValue(-1.0);

         /* append more signals to print when in speed mode. */
         m_sb.append("\terror: ");
         m_sb.append(m_shooter.getClosedLoopError());
         m_sb.append("\ttarget: ");
         m_sb.append(m_currentSpeed);
      }
      else
      {
         m_shooter.changeControlMode(TalonControlMode.PercentVbus);
         m_shooter.set(m_throttle);
         if (m_throttle < 0.05)
         {
            ((WsVictor)Core.getOutputManager().getOutput(WSOutputs.LEFT_1.getName())).setValue(-1.0);
         }
         else
         {
            ((WsVictor)Core.getOutputManager().getOutput(WSOutputs.LEFT_1.getName())).setValue(0);
         }
      }
      SmartDashboard.putNumber("Throttle", m_throttle);
      SmartDashboard.putBoolean("Speed mode", m_speedModeOn);
      
      // Test holding a position - but only if the shooter is not on, and if the rotation is slow enough
//      if (m_holdPos)// && !m_shooterOn && m_shooter.getSpeed() < 120)
//      {
//         m_shooter.changeControlMode(TalonControlMode.Position);
//         m_shooter.set(.5);
//      }
      
      // Print the target and current to the dashboard
      SmartDashboard.putNumber("Vout", motorOutput);
      SmartDashboard.putNumber("Speed", m_shooter.getSpeed());
      SmartDashboard.putNumber("Error", m_shooter.getClosedLoopError());
      SmartDashboard.putNumber("Target", m_currentSpeed);

      SmartDashboard.putNumber("Shooter encoder pos", m_shooter.getEncPosition());
      
      if (m_loops++ >= 10)
      {
         m_loops = 0;
         System.out.println(m_sb.toString());
      }
      m_sb.setLength(0);
//      System.out.println("Talon Test loop period: " + (System.currentTimeMillis() - start));


   }

   @Override
   public String getName()
   {
      return "Talon test";
   }

}
