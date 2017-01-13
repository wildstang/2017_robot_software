package org.wildstang.yearly.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.yearly.robot.CANConstants;
import org.wildstang.yearly.robot.WSInputs;

import com.ctre.CANTalon;
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

   private int m_currentSpeed = 1000;
   
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
      m_shooter = new CANTalon(CANConstants.FLYWHEEL_TEST_TALON_ID);

      m_shooter.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
      m_shooter.configEncoderCodesPerRev(256);
      m_shooter.reverseOutput(false);
      m_shooter.reverseSensor(false);
      
      m_shooter.configPeakOutputVoltage(+12.0,  -12.0);
      m_shooter.setVoltageRampRate(6.0);  // Max spinup of 6V/s - start here
      
      // Set up closed loop PID control gains in slot 0
      m_shooter.setProfile(0);
      m_shooter.setF(0);      // 0.1998
      m_shooter.setP(0);      //(10% X 1023) / (error) 
      m_shooter.setI(0);
      m_shooter.setD(0);
      
      m_shooter.changeControlMode(TalonControlMode.Speed);
      if (m_shooter.isSensorPresent(CANTalon.FeedbackDevice.QuadEncoder) != CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent)
      {
         SmartDashboard.putBoolean("ShooterEncoder", false);
      }
      else
      {
         SmartDashboard.putBoolean("ShooterEncoder", true);
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
         m_inc50prev = m_inc50curr;
      }
      else if (m_inc10curr && !m_inc10prev)
      {
         m_currentSpeed += 10;
         m_inc10prev = m_inc10curr;
      }
      else if (m_dec50curr && !m_dec50prev)
      {
         m_currentSpeed -= 50;
         m_dec50prev = m_dec50curr;
      }
      else if (m_dec10curr && !m_dec10prev)
      {
         m_currentSpeed -= 10;
         m_dec10prev = m_dec10curr;
      }
      
      if (m_shooterOnCurr && !m_shooterPrev)
      {
         m_shooterOn = !m_shooterOn;
      }
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
      SmartDashboard.putNumber("Target RPM", m_currentSpeed);
      SmartDashboard.putNumber("Current RPM", m_shooter.getSpeed());
      SmartDashboard.putNumber("Error", m_shooter.getClosedLoopError());
   }

   @Override
   public String getName()
   {
      return "Shooter Test";
   }

}
