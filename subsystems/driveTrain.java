/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.Robot;
import frc.robot.Constants.motorIDConst;
import java.util.Set;
import frc.robot.commands.DriveTrain;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.commands.autonDriveStraight;
//import sun.tools.javac.ErrorConsumer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
//CANError com.revrobotics.CANSparkMaxLowLevel.restoreFactoryDefualts()

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class driveTrain extends Subsystem{
  public Command autonDriveStraight;
  private final I2C.Port i2cPort = I2C.Port.kOnboard;

  /////////////////////////////////////////////////////////////////////////////////

  // motors
  public static CANSparkMax m_leftMotor = new CANSparkMax(motorIDConst.leftDeviceID, MotorType.kBrushless);
  public static CANSparkMax m_rightMotor = new CANSparkMax(motorIDConst.rightDeviceID, MotorType.kBrushless);
  public static CANSparkMax m_leftMotorF = new CANSparkMax(motorIDConst.leftFDeviceID, MotorType.kBrushless);///////////// Follower
  public static CANSparkMax m_rightMotorF = new CANSparkMax(motorIDConst.rightFDeviceID, MotorType.kBrushless);/////////// Follower
  private DifferentialDrive m_myRobot;
  private Joystick m_leftStick;// move to commabndw
  private Joystick m_rightStick;// never used
  Joystick flightStick = new Joystick(1);
  private static Encoder encoder = new Encoder(0 , 1 , true);


  /*
   * WPI_TalonFX _frontLeftMotor = new WPI_TalonFX(3);
   * 
   * WPI_TalonFX _frontRightMotor = new WPI_TalonFX(4);
   * 
   * WPI_VictorSPX intake = new WPI_VictorSPX(10);
   *//////////////////////////////////////////////////////////


  ///////////
  private final static double kDriveTick2Feet = 1.0 / 128 * 6 * Math.PI / 2;
  final static double kP = 0.5;
  final static double kI = 0.5;
  DriveTrain drivetrainC = new DriveTrain();
  static double lastTimeStamp = 0;
  static double sensorPos = encoder.get() * kDriveTick2Feet;
  static double setpoint = 0;
  static double errorSum = 0;
  static double error = setpoint - sensorPos;
  public static double outputSpeed = kP * error + kI * errorSum;
  static double DeltaTime = Timer.getFPGATimestamp() - lastTimeStamp;
  
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */

  public void robotInit() {
    // Instantiate our RobotContainer. This will perform all our button bindings,
    // and put our
    // autonomous chooser on the dashboard.

    encoder.reset();

    // factory reset
    m_leftMotor.restoreFactoryDefaults();
    m_rightMotor.restoreFactoryDefaults();
    m_leftMotorF.restoreFactoryDefaults();
    m_rightMotorF.restoreFactoryDefaults();

    m_leftMotorF.follow(m_leftMotor);
    m_rightMotorF.follow(m_rightMotor);
    m_rightMotor.setInverted(true);
    errorSum = 0;
    lastTimeStamp = Timer.getFPGATimestamp();
    m_myRobot = new DifferentialDrive(m_leftMotor, m_rightMotor);

    m_leftStick = new Joystick(0);// move to command

  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */

  public void robotPeriodic() {
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.
    //CommandScheduler.getInstance().run();///
    if(m_leftStick.getRawButton(1)){
      drivetrainC.start();
      
    }
   // SmartDashboard.putNumber("Encoder Data" , encoder.get() * kDriveTick2Feet); // encoder data displayed

    //Shuffleboard.putNumber("Encoder Data" , encoder.get() * kDriveTick2Feet);
   // if (m_leftStick.getRawButton(1)){ 
   //   autonDriveStraight.start(); // "start" 'auton' 
   // }
    


  }

  public void testInit() {
    // Cancels all running commands at the start of test mode.
    //CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */

  public void testPeriodic() {// move to command
/*
    if(flightStick.getRawAxis(1) <= -.05 || flightStick.getRawAxis(1) >= .05){
      //forward or back
      SmartDashboard.putNumber("JoyY", flightStick.getRawAxis(1));//
      m_myRobot.arcadeDrive(flightStick.getRawAxis(1) * -1, 0);
    }
    if(flightStick.getRawAxis(0) >= .1 || flightStick.getRawAxis(0) <= -.1){
      //turning
      SmartDashboard.putNumber("JoyX", flightStick.getRawAxis(0));
      m_myRobot.arcadeDrive(0, flightStick.getRawAxis(0));
    } */
  }



  public Set<Subsystem> getRequirements() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected void initDefaultCommand() {
    // TODO Auto-generated method stub

  }
}