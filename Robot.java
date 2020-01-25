/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.       
                 */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  WPI_TalonFX _frontLeftMotor = new WPI_TalonFX(3);
  WPI_TalonFX _frontRightMotor = new WPI_TalonFX(1);
  WPI_TalonFX _backLeftMotor = new WPI_TalonFX(4);
  WPI_TalonFX _backRightMotor = new WPI_TalonFX(2);
  //WPI_VictorSPX intake = new WPI_VictorSPX(10);

  /* Construct drivetrain by providing master motor controllers */
  DifferentialDrive _drive = new DifferentialDrive(_frontLeftMotor, _frontRightMotor);
  

  /* Joystick for control */
Joystick _joy = new Joystick(0);
CameraServer server;
//Compressor c = new Compressor(0);


    
    //DoubleSolenoid exampleDouble = new DoubleSolenoid(0, 1);

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code. 
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    _frontLeftMotor.configFactoryDefault();
    _frontRightMotor.configFactoryDefault();
    _backLeftMotor.configFactoryDefault();
    _backRightMotor.configFactoryDefault();
    //intake.configFactoryDefault();
    _frontLeftMotor.setInverted(false); // <<<<<< Adjust this until robot drives forward when stick is forward
    _frontRightMotor.setInverted(false);
    _backRightMotor.setInverted(false);
    //_backLeftMotor.setInverted(true);
    //_backRightMotor.setInverted(true);
    //intake.setInverted(false);
    server = CameraServer.getInstance();
    server.startAutomaticCapture();

    _backLeftMotor.follow(_frontLeftMotor);
    _backRightMotor.follow(_frontRightMotor);

    
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {

    /* Gamepad processing */
		double forward = -0.75 * _joy.getY(Hand.kLeft);	// Sign this so forward is positive
    double turn = +0.5 * _joy.getX(Hand.kLeft);       // Sign this so right is positive
    
        
        /* Deadband - within 10% joystick, make it zero */
		if (Math.abs(forward) < 0.10) {
			forward = 0;
		}
		if (Math.abs(turn) < 0.10) {
			turn = 0;
    }

   /*  if(_joy.getRawButton(1)){
      exampleDouble.set(Value.kForward);
    }
  
    if(_joy.getRawButton(2)){
      exampleDouble.set(Value.kReverse);
    } */
    //intake.set(-_joy.getRawAxis(3)/2);        
		/**
		 * Print the joystick values to sign them, comment
		 * out this line after checking the joystick directions. 
		 */
        System.out.println("JoyY:" + forward + "  turn:" + turn );
        
		/**
		 * Drive the robot, 
		 */
    _drive.arcadeDrive(forward, (turn));
    //SmartDashboard.putNumber("Intake Speed", -_joy.getRawAxis(3)/2);
    SmartDashboard.putNumber("FWD Speed", forward);
    SmartDashboard.putNumber("TURN Speed", turn);
    SmartDashboard.updateValues();

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}