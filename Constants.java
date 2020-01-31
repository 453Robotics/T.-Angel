
package frc.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;

public final class Constants {
    public static final class motorIDConst{
       public static final int leftDeviceID = 4;
       public static final int rightDeviceID = 2;
       public static final int leftFDeviceID = 3;
       public static final int rightFDeviceID = 8;
       public static final int intakeID = 10;
       public static final int shooterOne = 15;
       public static final int shooterTwo = 30;
       public static final int pigeon_Imu = 6;
       public static final int doubleSolen = 12;
       public static final int conveyorID = 11;
       public static final int colorWheelChooserID = 45;
       public static final int colorSensor = 60;
       public static final int canifier = 20;
	   
    }
	public static final class encoderConstants{
	    public final static Encoder m_shooterEncoder = new Encoder(0, 1,true);
		public final static SimpleMotorFeedforward m_shooterFeedforward = new SimpleMotorFeedforward(0.05, 0.24);
		
	}
}