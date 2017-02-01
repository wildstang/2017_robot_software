package org.wildstang.yearly.subsystems.drive;

public class DriveConstants
{

   public static final int BRAKE_MODE_ALLOWABLE_ERROR = 20;
   
   public static final int PATH_PROFILE_SLOT = 0;
   public static final int BASE_LOCK_PROFILE_SLOT = 1;
   
   
   // Path following PID constants
   public static final double PATH_F_GAIN = 1.6650390625;
   public static final double PATH_P_GAIN = 0.0;
   public static final double PATH_I_GAIN = 0.0;
   public static final double PATH_D_GAIN = 0.0;
   
   // Base lock PID constants
   public static final double BASE_F_GAIN = 1;
   public static final double BASE_P_GAIN = 0.0;
   public static final double BASE_I_GAIN = 0.0;
   public static final double BASE_D_GAIN = 0.0;
}
