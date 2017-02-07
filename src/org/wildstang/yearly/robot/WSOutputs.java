package org.wildstang.yearly.robot;

// expand this and edit if trouble with Ws
import org.wildstang.framework.core.Outputs;
import org.wildstang.framework.hardware.OutputConfig;
import org.wildstang.framework.io.outputs.OutputType;
import org.wildstang.hardware.crio.outputs.WSOutputType;
import org.wildstang.hardware.crio.outputs.WsDoubleSolenoidState;
import org.wildstang.hardware.crio.outputs.config.WsDoubleSolenoidConfig;
import org.wildstang.hardware.crio.outputs.config.WsI2COutputConfig;
import org.wildstang.hardware.crio.outputs.config.WsSolenoidConfig;
import org.wildstang.hardware.crio.outputs.config.WsVictorConfig;

import edu.wpi.first.wpilibj.I2C;

public enum WSOutputs implements Outputs
{
   // ********************************
   // Talon Motors (PLACE HOLDERS)
   // ********************************
   //DRIVE_1_LEFT("Drive 1 Left",              WSOutputType.TALON,    new WsTalonConfig(1, 0.0), getLogging()),     // CAN ID 1 Driver Subsystem
   //DRIVE_2_LEFT("Drive 2 Left",              WSOutputType.TALON,    new WsTalonConfig(2, 0.0), getLogging()),     // CAN ID 2 Driver Subsystem
   //DRIVE_3_RIGHT("Drive 3 Right",            WSOutputType.TALON,    new WsTalonConfig(3, 0.0), getLogging()),     // CAN ID 3 Driver Subsystem
   //DRIVE_4_RIGHT("Drive 4 Right",            WSOutputType.TALON,    new WsTalonConfig(4, 0.0), getLogging()),     // CAN ID 4 Driver Subsystem
   //FLYWHEEL_LEFT("Flywheel Motor Left",      WSOutputType.TALON,    new WsTalonConfig(5, 0.0), getLogging()),     // CAN ID 5 Driver Subsystem
   //FLYWHEEL_RIGHT("Flywheel Motor Left",     WSOutputType.TALON,    new WsTalonConfig(6, 0.0), getLogging()),     // CAN ID 6 Driver Subsystem
   
   // ********************************
   // PWM Outputs
   // ********************************
   //---------------------------------             
   // Motors
   //---------------------------------             
   FEEDER_LEFT("Left Feeder Motor",          WSOutputType.VICTOR,    new WsVictorConfig(0, 0.0), getLogging()),   // Shooter Subsystem
   FEEDER_RIGHT("Right Feeder Motor",        WSOutputType.VICTOR,    new WsVictorConfig(1, 0.0), getLogging()),   // Shooter Subsystem
   INTAKE("Intake Motor",                    WSOutputType.VICTOR,    new WsVictorConfig(2, 0.0), getLogging()),   // Intake Subsystem
   WINCH("Winch motor",                      WSOutputType.VICTOR,    new WsVictorConfig(3, 0.0), getLogging()),   // Winch Subsystem
                                             
   //---------------------------------             
   // Servos
   //---------------------------------             
   //SERVO_0("Test Servo 0",                   WSOutputType.SERVO,     new WsServoConfig(0, 0.0), getLogging()),    // PWM 0, Initial Rotation Angle 0.0

   // ********************************
   // DIO Outputs                             
   // ********************************
   //DIO_O_0("Test Digital Output 0",          WSOutputType.DIGITAL_OUTPUT, new WsDigitalOutputConfig(0, false), getLogging()), // Channel 0, Initially Low
   //DIO_O_1("Test Digital Output 1",          WSOutputType.DIGITAL_OUTPUT, new WsDigitalOutputConfig(1, false), getLogging()), // Channel 1, Initially Low 
   //DIO_O_2("Test Digital Output 2",          WSOutputType.DIGITAL_OUTPUT, new WsDigitalOutputConfig(2, false), getLogging()), // Channel 2, Initially Low 
   //DIO_O_3("Test Digital Output 3",          WSOutputType.DIGITAL_OUTPUT, new WsDigitalOutputConfig(3, false), getLogging()), // Channel 3, Initially Low 
   //DIO_O_4("Test Digital Output 4",          WSOutputType.DIGITAL_OUTPUT, new WsDigitalOutputConfig(4, false), getLogging()), // Channel 4, Initially Low 
   //DIO_O_5("Test Digital Output 5",          WSOutputType.DIGITAL_OUTPUT, new WsDigitalOutputConfig(5, false), getLogging()), // Channel 5, Initially Low 
   //DIO_O_6("Test Digital Output 6",          WSOutputType.DIGITAL_OUTPUT, new WsDigitalOutputConfig(6, false), getLogging()), // Channel 6, Initially Low 
   //DIO_O_7("Test Digital Output 7",          WSOutputType.DIGITAL_OUTPUT, new WsDigitalOutputConfig(7, false), getLogging()), // Channel 7, Initially Low 
   //DIO_O_8("Test Digital Output 8",          WSOutputType.DIGITAL_OUTPUT, new WsDigitalOutputConfig(8, false), getLogging()), // Channel 8, Initially Low 
   //DIO_O_9("Test Digital Output 9",          WSOutputType.DIGITAL_OUTPUT, new WsDigitalOutputConfig(9, false), getLogging()), // Channel 9, Initially Low 
   
   // ********************************
   // Solenoids
   // ********************************
   SHIFTER("Shifter double solenoid",        WSOutputType.SOLENOID_DOUBLE, new WsDoubleSolenoidConfig(1, 0, 1, WsDoubleSolenoidState.FORWARD), getLogging()), // Ctrl 1, Pins 0 & 1 (2 DIO pins are used): Driver Subsystem
   GATE_LEFT("Gate Left",                    WSOutputType.SOLENOID_SINGLE, new WsSolenoidConfig(1, 2, false), getLogging()),                                  // Ctrl 1, Pin  2                            Shooter Subsystem
   GATE_RIGHT("Gate Right",                  WSOutputType.SOLENOID_SINGLE, new WsSolenoidConfig(1, 3, false), getLogging()),                                  // Ctrl 1, Pin  3                            Shooter Subsystem
   GEAR_SHIFTER("Gear Shifter",              WSOutputType.SOLENOID_SINGLE, new WsSolenoidConfig(1, 4, false), getLogging()),                                  // Ctrl 1, Pin  4                            Shooter Subsystem
   WINCH_BRKE("Winch Brake",                 WSOutputType.SOLENOID_SINGLE, new WsSolenoidConfig(1, 5, false), getLogging()),                                  // Ctrl 1, Pin  5                            Climber Subsystem

   // ********************************
   // Relays
   // ********************************
   //RELAY_0("Relay 0",                        WSOutputType.RELAY, new WsRelayConfig(0, WsRelayState.RELAY_OFF), getLogging()), // Relay 0, Both Off
   //RELAY_1("Relay 1",                        WSOutputType.RELAY, new WsRelayConfig(1, WsRelayState.RELAY_OFF), getLogging()), // Relay 1, Both Off
   //RELAY_2("Relay 2",                        WSOutputType.RELAY, new WsRelayConfig(2, WsRelayState.RELAY_OFF), getLogging()), // Relay 2, Both Off
   //RELAY_3("Relay 3",                        WSOutputType.RELAY, new WsRelayConfig(3, WsRelayState.RELAY_OFF), getLogging()), // Relay 3, Both Off

   // ********************************
   // Others ...
   // ********************************
   LED("LEDs",                               WSOutputType.I2C, new WsI2COutputConfig(I2C.Port.kMXP, 0x10), true);

   
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
