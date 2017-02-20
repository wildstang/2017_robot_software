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
   //  |      			                                           |   
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
   DRV_THROTTLE("Driver throttle",                 WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(0, JoystickConstants.LEFT_JOYSTICK_Y), getLogging()),     // Driver Subsystem
   DRV_HEADING("Driver heading",                   WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(1, JoystickConstants.RIGHT_JOYSTICK_X), getLogging()),    // Driver Subsystem
                                                
   //---------------------------------             
   // Driver Buttons                               
   //---------------------------------             
   INTAKE_ON("Intake Turn on/off",                 WSInputType.JS_BUTTON, new WsJSButtonInputConfig(0, 4), getLogging()),     // Intake Subsystem
   SHIFT("Driver Shift",                           WSInputType.JS_BUTTON, new WsJSButtonInputConfig(0, 5), getLogging()),     // Driver Subsystem
   QUICK_TURN("Quick Turn",                           WSInputType.JS_BUTTON, new WsJSButtonInputConfig(0, 7), getLogging()),     // Driver Subsystem

   //---------------------------------             
   // Manipulator Joysticks                        
   //---------------------------------             
   FEEDER_LEFT("Feeder Left Up/Down",              WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(1, JoystickConstants.LEFT_JOYSTICK_Y), getLogging()),     // Shooter Subsystem
   FEEDER_RIGHT("Feeder Right Up/Down",            WSInputType.JS_JOYSTICK, new WsJSJoystickInputConfig(1, JoystickConstants.RIGHT_JOYSTICK_Y), getLogging()),    // Shooter Subsystem

   //---------------------------------             
   // Manipulator DPAD Buttons                     
   //---------------------------------             
   CLIMBER_UP("Climber Up",                        WSInputType.JS_DPAD_BUTTON, new WsJSButtonInputConfig(1, JoystickConstants.DPAD_Y_UP), getLogging()),          // Climber Subsystem
   //CLIMBER_DOWN("Climber Down",                    WSInputType.JS_DPAD_BUTTON, new WsJSButtonInputConfig(1, JoystickConstants.DPAD_Y_DOWN), getLogging()),        // Climber Subsystem

   //---------------------------------             
   // Manipulator Buttons                          
   //---------------------------------             
   GEAR_TILT_BUTTON("Gear Wall",                  WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 0), getLogging()),      // Gear Subsystem
   GEAR_HOLD_BUTTON("Gear Hold",                  WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 1), getLogging()),      // Gear Subsystem
   GATE("Gate Button",                            WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 4), getLogging()),        // Shooter Subsystem
   FLYWHEEL("Flywheel On/Off",                    WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 5), getLogging()),
   //MAN_BUTTON_6("Manipulator Button 6",                   WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 6), getLogging()),        // Shooter Subsystem
   //GEAR("Gear Positioner Control",                 WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 7), getLogging()),
   OVERRIDE("Override",                           WSInputType.JS_BUTTON, new WsJSButtonInputConfig(1, 9), getLogging()),


   // ********************************             
   // Digital IOs                                  
   // ********************************             
//   GEAR_IN_POSITION("Gear In Position Sensor",     WSInputType.SWITCH, new WsDigitalInputConfig(0, true), getLogging()),	// Gear Subsystem
//   BALLS_WAITING_LEFT("Balls Waiting Left",        WSInputType.SWITCH, new WsDigitalInputConfig(1, true), getLogging()),	// Shooter Subsystem
//   BALLS_WAITING_RIGHT("Digital Waiting Right",    WSInputType.SWITCH, new WsDigitalInputConfig(2, true), getLogging()),	// Shooter Subsystem

   // ********************************             
   // Others ...                                   
   // ********************************             
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
