package org.wildstang.yearly.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.yearly.robot.CANConstants;
import org.wildstang.yearly.robot.WSInputs;

import com.ctre.CANTalon;
import com.ctre.CANTalon.StatusFrameRate;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterTest implements Subsystem
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

   private float m_currentSpeed = .3f;
   
   private boolean m_shooterOn;
   private boolean m_shooterOnCurr;
   private boolean m_shooterPrev;
   
   private boolean m_holdPos = false;
   
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
      else if (p_source == m_shooterPosInput)
      {
         m_holdPos = m_shooterPosInput.getValue();
      }
   }

   @Override
   public void init()
   {
      m_shooter = new CANTalon(2);

      m_shooter.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
      m_shooter.setStatusFrameRateMs(StatusFrameRate.QuadEncoder, 10);
      m_shooter.configEncoderCodesPerRev(256);
      m_shooter.reverseOutput(false);
      m_shooter.reverseSensor(false);

      m_shooter.configNominalOutputVoltage(0.0,  0.0);
      m_shooter.configPeakOutputVoltage(+12.0,  -12.0);
//      m_shooter.setVoltageRampRate(24.0);  // Max spinup of 6V/s - start here
      
      
      m_shooter.changeControlMode(TalonControlMode.Speed);
      m_shooter.setAllowableClosedLoopErr(0);
      // Set up closed loop PID control gains in slot 0
      m_shooter.setProfile(0);
      m_shooter.setF(0);      // 0.1998
      m_shooter.setP(0);      //(10% X 1023) / (error) 
      m_shooter.setI(0);
      m_shooter.setD(0);

      
      switch (m_shooter.isSensorPresent(CANTalon.FeedbackDevice.QuadEncoder))
      {
         case FeedbackStatusPresent:
            SmartDashboard.putString("Shooter encoder status", "Present");
            break;
         case FeedbackStatusNotPresent:
            SmartDashboard.putString("Shooter encoder status", "Not present");
            break;
         case FeedbackStatusUnknown:
            SmartDashboard.putString("Shooter encoder status", "Unknown");
            break;
      }

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

      m_shooterPosInput = (DigitalInput)Core.getInputManager().getInput(WSInputs.DRV_BUTTON_4.getName());
      m_shooterPosInput.addInputListener(this);
      
   }

   @Override
   public void selfTest()
   {
   }

   @Override
   public void update()
   {
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
      
      if (m_shooterOnCurr && !m_shooterPrev)
      {
         m_shooterOn = !m_shooterOn;
      }
      m_inc50prev = m_inc50curr;
      m_inc10prev = m_inc10curr;
      m_dec50prev = m_dec50curr;
      m_dec10prev = m_dec10curr;
      
      m_shooterPrev = m_shooterOnCurr;
      
      if (m_shooterOn)
      {
         // Set the speed
         m_shooter.set(m_currentSpeed);
      }
      else
      {
         m_shooter.set(0.0);
      }
      
//      // Test holding a position - but only if the shooter is not on, and if the rotation is slow enough
//      if (m_holdPos && !m_shooterOn && m_shooter.getSpeed() < 120)
//      {
//         m_shooter.changeControlMode(TalonControlMode.Position);
//         m_shooter.set(500);
//      }
      
      // Print the target and current to the dashboard
      SmartDashboard.putBoolean("Shooter on", m_shooterOn);
      SmartDashboard.putNumber("Target RPM", m_currentSpeed);
      SmartDashboard.putNumber("Current RPM", m_shooter.getSpeed());
      SmartDashboard.putNumber("Error", m_shooter.getClosedLoopError());
      SmartDashboard.putNumber("Output voltage", m_shooter.getOutputVoltage());
      SmartDashboard.putNumber("Bus voltage", m_shooter.getBusVoltage());
   }

   @Override
   public String getName()
   {
      return "Shooter Test";
   }

}
