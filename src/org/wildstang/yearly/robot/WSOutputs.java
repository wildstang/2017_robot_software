package org.wildstang.yearly.robot;

// expand this and edit if trouble with Ws
import org.wildstang.framework.core.Outputs;
import org.wildstang.framework.hardware.OutputConfig;
import org.wildstang.framework.io.outputs.OutputType;
import org.wildstang.hardware.crio.outputs.WSOutputType;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoidState;
import org.wildstang.hardware.crio.outputs.WsRelayState;
import org.wildstang.hardware.crio.outputs.config.WsDoubleSolenoidConfig;
import org.wildstang.hardware.crio.outputs.config.WsI2COutputConfig;
import org.wildstang.hardware.crio.outputs.config.WsRelayConfig;
import org.wildstang.hardware.crio.outputs.config.WsSolenoidConfig;
import org.wildstang.hardware.crio.outputs.config.WsVictorConfig;

import edu.wpi.first.wpilibj.I2C;

public enum WSOutputs implements Outputs
{
   //PWM Outputs
   // Motors
   LEFT_1("Left motor 1",                    WSOutputType.VICTOR,    new WsVictorConfig(0, 0.0), getLogging()),
   LEFT_2("Left motor 2",                    WSOutputType.VICTOR,    new WsVictorConfig(1, 0.0), getLogging()),
   RIGHT_1("Right motor 1",                  WSOutputType.VICTOR,    new WsVictorConfig(2, 0.0), getLogging()),
   RIGHT_2("Right motor 2",                  WSOutputType.VICTOR,    new WsVictorConfig(3, 0.0), getLogging()),
                                             
   //TEST_SERVO_0("Test Servo 0",              WSOutputType.SERVO,     new WsServoConfig(8, 0.0), getLogging()),

   //DIO Outputs
   // Solenoids
   SHIFTER("Shifter double solenoid",        WSOutputType.SOLENOID_DOUBLE, new WsDoubleSolenoidConfig(0, 0, 1, WsDoubleSolenoidState.FORWARD), getLogging()), // 2 DIO pins are used
   //INTAKE_FRONT_LOWER("Intake front lower",  WSOutputType.SOLENOID_SINGLE, new WsSolenoidConfig(1, 3, false), getLogging()),                                  // 1 DIO pin is used


   LED("LEDs",                         WSOutputType.I2C, new WsI2COutputConfig(I2C.Port.kMXP, 0x10), true);

   
   private String m_name;
   private OutputType m_type;
   private OutputConfig m_config;
   private boolean m_trackingState;
   
   private static boolean isLogging = true;

   WSOutputs(String p_name, OutputType p_type, OutputConfig p_config, boolean p_trackingState)
   {
      m_name = p_name;
      m_type = p_type;
      m_config = p_config;
      m_trackingState = p_trackingState;
   }
   
  
   @Override
   public String getName()
   {
      return m_name;
   }
   
   @Override
   public OutputType getType()
   {
      return m_type;
   }
   
   public OutputConfig getConfig()
   {
      return m_config;
   }

   public boolean isTrackingState()
   {
      return m_trackingState;
   }
   
   public static boolean getLogging()
   {
      return isLogging;
   }

}
