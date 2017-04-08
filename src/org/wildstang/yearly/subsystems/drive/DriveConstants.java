package org.wildstang.yearly.subsystems.drive;

public class DriveConstants
{

   public static final int BRAKE_MODE_ALLOWABLE_ERROR = 20;
   
   public static final int PATH_PROFILE_SLOT = 0;
   public static final int BASE_LOCK_PROFILE_SLOT = 1;
   
   
   // Path following PID constants
   public static final double PATH_F_GAIN = 0.23;
   public static final double PATH_P_GAIN = 0;//1.5;
   public static final double PATH_I_GAIN = 0.0;
   public static final double PATH_D_GAIN = 0;//2.0;
   
   // Base lock PID constants
   public static final double BASE_F_GAIN = 1;
   public static final double BASE_P_GAIN = 0.0;
   public static final double BASE_I_GAIN = 0.0;
   public static final double BASE_D_GAIN = 0.0;

   // Motion Magic quickturn PID constants
   public static final double MM_QUICK_F_GAIN = .85;
   public static final double MM_QUICK_P_GAIN = 0.0;
   public static final double MM_QUICK_I_GAIN = 0.0;
   public static final double MM_QUICK_D_GAIN = 0.0;

   // Motion Magic regular PID constants
   public static final double MM_DRIVE_F_GAIN = 0.33;
   public static final double MM_DRIVE_P_GAIN = 0.0;
   public static final double MM_DRIVE_I_GAIN = 0.0;
   public static final double MM_DRIVE_D_GAIN = 0.0;

   
}
