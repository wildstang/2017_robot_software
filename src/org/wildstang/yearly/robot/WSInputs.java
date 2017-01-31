package org.wildstang.yearly.robot;

import org.wildstang.framework.core.Inputs;
import org.wildstang.framework.hardware.InputConfig;
import org.wildstang.framework.hardware.WsRemoteAnalogInputConfig;
import org.wildstang.framework.io.inputs.InputType;
import org.wildstang.hardware.JoystickConstants;
import org.wildstang.hardware.crio.inputs.WSInputType;
import org.wildstang.hardware.crio.inputs.config.WsDigitalInputConfig;
import org.wildstang.hardware.crio.inputs.config.WsI2CInputConfig;
import org.wildstang.hardware.crio.inputs.config.WsJSButtonInputConfig;
import org.wildstang.hardware.crio.inputs.config.WsJSJoystickInputConfig;
import org.wildstang.hardware.crio.inputs.config.WsMotionProfileConfig;

import edu.wpi.first.wpilibj.I2C;

public enum WSInputs implements Inputs
{
   // im.addSensorInput(LIDAR, new WsLIDAR());
   //
   //***************************************************************
   //      Driver and Manipulator Controller Button Locations
   //***************************************************************
   //
   //    +-------------------------------------------------------+
   //  +  +---------+              [TOP]              +---------+  +       
   //  |  |    6    |                                 |    7    |  |       
   //  |  +---------+                                 +---------+  |       
   //  |      			                             			   |   
   //  |  +---------+                                 +---------+  |       
   //  |  |    4    |                                 |    5    |  |
   //  +  +---------+                                 +---------+  +
   //    +-------------------------------------------------------+
   //  
   //    +-------------------------------------------------------+
   //   /    +--+                 [FRONT]                         \
   //  +     |YU|                                         (3)      +       
   //  |  +--+  +--+        +----+       +----+                    | 
   //  |  |XL    XR|        |  8 |  (X)  |  9 |       (0)     (2)  |       
   //  |  +--+  +--+        +----+       +----+                    | 
   //  |     |YD|                                         (1)      |       
   //  |     +--+     +--+          (X)          +--+              |
   //  |             /    \                     /    \             |
   //  |            |  10  |                   |  11  |            |
   //  |             \    /                     \    /             |
   //  +              +--+                       +--+              +
   //   \                                                         /
   //    \            +-----------------------------+            /
   //     \          /                               \          /
   //      \        /                                 \        /
   //       \      /                                   \      /
   //        +----+                                     +----+
   //
   //  
   // ********************************
   // Driver Enums
   // ********************************
   //  
   //---------------------------------
   // Driver Joysticks
   //---------------------------------
   //DRV_LEFT_X("Driver left X",                   WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(0, JoystickConstants.LEFT_JOYSTICK_X), getLogging()),
   DRV_THROTTLE("Driver throttle",                 WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(0, JoystickConstants.LEFT_JOYSTICK_Y), getLogging()),     // Driver Subsystem
   DRV_HEADING("Driver heading",                   WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(1, JoystickConstants.RIGHT_JOYSTICK_X), getLogging()),    // Driver Subsystem
   //DRV_RIGHT_Y("Driver right Y",                 WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(1, JoystickConstants.RIGHT_JOYSTICK_Y), getLogging()),    
                                                
   //---------------------------------             
   // Driver DPAD Buttons                          
   //---------------------------------             
   //DRV_DPAD_X_LEFT("Driver DPAD X Left",         WSInputType.JS_DPAD_BUTTON, new WsJSButtonInputConfig(0, JoystickConstants.DPAD_X_LEFT), getLogging()),
   //DRV_DPAD_X_RIGHT("Driver DPAD X Right",       WSInputType.JS_DPAD_BUTTON, new WsJSButtonInputConfig(0, JoystickConstants.DPAD_X_RIGHT), getLogging()),
   //DRV_DPAD_Y_UP("Driver DPAD Y Up",             WSInputType.JS_DPAD_BUTTON, new WsJSButtonInputConfig(0, JoystickConstants.DPAD_Y_UP), getLogging()),
   //DRV_DPAD_Y_DOWN("Driver DPAD Y Down",         WSInputType.JS_DPAD_BUTTON, new WsJSButtonInputConfig(0, JoystickConstants.DPAD_Y_DOWN), getLogging()),

   //---------------------------------             
   // Driver Buttons                               
   //---------------------------------             
   //DRV_BUTTON_0("Driver button 0",               WSInputType.JS_BUTTON, new WsJSButtonInputConfig(0, 0), getLogging()),
   //DRV_BUTTON_1("Shooter on",                    WSInputType.JS_BUTTON, new WsJSButtonInputConfig(0, 1), getLogging()),
   //DRV_BUTTON_2("Driver button 2",               WSInputType.JS_BUTTON, new WsJSButtonInputConfig(0, 2), getLogging()),
   //DRV_BUTTON_3("Driver button 3",               WSInputType.JS_BUTTON, new WsJSButtonInputConfig(0, 3), getLogging()),
   QUICK_TURN("Driver Quick Turn",                 WSInputType.JS_BUTTON, new WsJSButtonInputConfig(0, 4), getLogging()),     // Driver Subsystem
   SHIFT("Driver Shift",                           WSInputType.JS_BUTTON, new WsJSButtonInputConfig(0, 5), getLogging()),     // Driver Subsystem
   //DRV_BUTTON_6("Driver Intake Nose Control",    WSInputType.JS_BUTTON, new WsJSButtonInputConfig(0, 6), getLogging()),
   INTAKE_ON("Intake Turn on/off",                 WSInputType.JS_BUTTON, new WsJSButtonInputConfig(0, 7), getLogging()),     // Intake Subsystem
   //DRV_BUTTON_8("Driver button 8",               WSInputType.JS_BUTTON, new WsJSButtonInputConfig(0, 8), getLogging()),
   //DRV_BUTTON_9("Driver button 9",               WSInputType.JS_BUTTON, new WsJSButtonInputConfig(0, 9), getLogging()),
   //DRV_BUTTON_10("Driver button 10",             WSInputType.JS_BUTTON, new WsJSButtonInputConfig(0, 10), getLogging()),
   //DRV_BUTTON_11("Driver button 11",             WSInputType.JS_BUTTON, new WsJSButtonInputConfig(0, 11), getLogging()),


   // ********************************             
   // Manipulator Enums                            
   // ********************************             
   //---------------------------------             
   // Manipulator Joysticks                        
   //---------------------------------             
   //MAN_LEFT_JOYSTICK_X("Manip Left Joystick X",  WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(1, JoystickConstants.LEFT_JOYSTICK_X), getLogging()),
   FEEDER_LEFT("Feeder Left Up/Down",              WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(1, JoystickConstants.LEFT_JOYSTICK_Y), getLogging()),     // Shooter Subsystem
   //MAN_RIGHT_JOYSTICK_X("Manip Right Joystick X",WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(1, JoystickConstants.RIGHT_JOYSTICK_X), getLogging()),
   FEEDER_RIGHT("Feeder Right Up/Down",            WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(1, JoystickConstants.RIGHT_JOYSTICK_Y), getLogging()),    // Shooter Subsystem

   //---------------------------------             
   // Manipulator DPAD Buttons                     
   //---------------------------------             
   //MAN_DPAD_X_LEFT("Manipulator DPAD X Left",    WSInputType.JS_DPAD_BUTTON, new WsJSButtonInputConfig(1, JoystickConstants.DPAD_X_LEFT), getLogging()),
   //MAN_DPAD_X_RIGHT("Manipulator DPAD X Right",  WSInputType.JS_DPAD_BUTTON, new WsJSButtonInputConfig(1, JoystickConstants.DPAD_X_RIGHT), getLogging()),
   CLIMBER_UP("Climber Up",                        WSInputType.JS_DPAD_BUTTON, new WsJSButtonInputConfig(1, JoystickConstants.DPAD_Y_UP), getLogging()),          // Climber Subsystem
   CLIMBER_DOWN("Climber Down",                    WSInputType.JS_DPAD_BUTTON, new WsJSButtonInputConfig(1, JoystickConstants.DPAD_Y_DOWN), getLogging()),        // Climber Subsystem

   //---------------------------------             
   // Manipulator Buttons                          
   //---------------------------------             
   //MAN_BUTTON_0("Manipulator button 0",          WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 0), getLogging()),
   //MAN_BUTTON_1("Manipulator button 1",          WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 1), getLogging()),
   //MAN_BUTTON_2("Manipulator button 2",          WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 2), getLogging()),
   //MAN_BUTTON_3("Manipulator button 3",          WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 3), getLogging()),
   FLYWHEEL_LEFT("Flywheel Left On/Off",           WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 4), getLogging()),        // Shooter Subsystem
   FLYWHEEL_RIGHT("Flywheel Right On/Off",         WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 5), getLogging()),        // Shooter Subsystem
   GATE_LEFT("Gate Left On/Off",                   WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 6), getLogging()),        // Shooter Subsystem
   GATE_RIGHT("Gate Left On/Off",                  WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 7), getLogging()),        // Shooter Subsystem
   GEAR("Gear Positioner Control",                 WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 8), getLogging()),
   //MAN_BUTTON_9("Manipulator button 9",          WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 9), getLogging()),
   //MAN_BUTTON_10("Manipulator button 10",        WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 10), getLogging()),
   //MAN_BUTTON_11("Manipulator button 11",        WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 11), getLogging()),

   // ********************************             
   // Digital IOs                                  
   // ********************************             
   GEAR_IN_POSITION("Gear In Position Sensor",     WSInputType.SWITCH, new WsDigitalInputConfig(0, true), getLogging()),	// Gear Subsystem
   BALLS_WAITING_LEFT("Balls Waiting Left",        WSInputType.SWITCH, new WsDigitalInputConfig(1, true), getLogging()),	// Shooter Subsystem
   BALLS_WAITING_RIGHT("Digital Waiting Right",    WSInputType.SWITCH, new WsDigitalInputConfig(2, true), getLogging()),	// Shooter Subsystem
   //DIO_I_3("Digital Input 3",                    WSInputType.SWITCH, new WsDigitalInputConfig(3, true), getLogging()),	// Digital IO Channel 3
   //DIO_I_4("Digital Input 4",                    WSInputType.SWITCH, new WsDigitalInputConfig(4, true), getLogging()),	// Digital IO Channel 4
   //DIO_I_5("Digital Input 5",                    WSInputType.SWITCH, new WsDigitalInputConfig(5, true), getLogging()),	// Digital IO Channel 5
   //DIO_I_6("Digital Input 6",                    WSInputType.SWITCH, new WsDigitalInputConfig(6, true), getLogging()),	// Digital IO Channel 6
   //DIO_I_7("Digital Input 7",                    WSInputType.SWITCH, new WsDigitalInputConfig(7, true), getLogging()),	// Digital IO Channel 7
   //DIO_I_8("Digital Input 8",                    WSInputType.SWITCH, new WsDigitalInputConfig(8, true), getLogging()),	// Digital IO Channel 8
   //DIO_I_9("Digital Input 9",                    WSInputType.SWITCH, new WsDigitalInputConfig(9, true), getLogging()),	// Digital IO Channel 9

   // ********************************             
   // Others ...                                   
   // ********************************             
   //MOTION_PROFILE_CONTROL("MotionProfileConfig", WSInputType.MOTION_PROFILE_CONTROL, new WsMotionProfileConfig(), getLogging()),

   IMU("IMU", WSInputType.I2C,                     new WsI2CInputConfig(I2C.Port.kMXP, 0x20), getLogging());


   private final String m_name;
   private final InputType m_type;

   private InputConfig m_config = null;

   private boolean m_trackingState;

   private static boolean isLogging = true;

   WSInputs(String p_name, InputType p_type, InputConfig p_config,
         boolean p_trackingState)
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
   public InputType getType()
   {
      return m_type;
   }

   public InputConfig getConfig()
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
