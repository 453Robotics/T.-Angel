/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Relay.Value;
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
  private final Joystick flightStickOne = new Joystick(0);
  private final Joystick flightStickTwo = new Joystick(1);
  private final WPI_TalonSRX intake = new WPI_TalonSRX(4);
  WPI_TalonFX _frontLeftMotor = new WPI_TalonFX(10);
  WPI_TalonFX _frontRightMotor = new WPI_TalonFX(11);
  WPI_TalonFX _backLeftMotor = new WPI_TalonFX(12);
  WPI_TalonFX _backRightMotor = new WPI_TalonFX(13);
  WPI_TalonSRX intakeUpDown = new WPI_TalonSRX(5);
  DifferentialDrive _drive = new DifferentialDrive(_frontLeftMotor, _frontRightMotor);
  public static final Relay m_relay = new Relay(0);
  public static final int kRelayForwardButton = 3;
  public static final int kRelayBackwardButton= 4;
  public WPI_TalonSRX m_shooterMotor = new WPI_TalonSRX(1);//placeholder device number
  public WPI_TalonSRX m_shooterMotor2 = new WPI_TalonSRX(2);//placeholder device number

  CameraServer server;
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
    _frontRightMotor.setInverted(true);
    server = CameraServer.getInstance();
    server.startAutomaticCapture();

    _backLeftMotor.follow(_frontLeftMotor);
    _backRightMotor.follow(_frontRightMotor);
    m_shooterMotor2.follow(m_shooterMotor);
    m_shooterMotor2.setInverted(true);
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
   * This function is called once when teleop is enabled.
   */
  @Override
  public void teleopInit() {
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {

    double forward = -0.75 * flightStickOne.getY(Hand.kLeft);	// Sign this so forward is positive
    double turn = +0.5 * flightStickOne.getX(Hand.kLeft);       // Sign this so right is positive
    
        
        /* Deadband - within 10% joystick, make it zero */
		if (Math.abs(forward) < 0.10) {
			forward = 0;
		}
		if (Math.abs(turn) < 0.10) {
			turn = 0;
    }
    _drive.arcadeDrive(forward, (turn));


    if(flightStickTwo.getRawButton(1)){
      intake.set(0.5);
    }
    if(flightStickTwo.getRawButton(2)){
      intake.set(-0.25);
    }
    else{
      intake.set(0);
    }

    boolean forwardRelay = flightStickTwo.getRawButton(kRelayForwardButton);
    boolean backward = flightStickTwo.getRawButton(kRelayBackwardButton);

    if(forwardRelay && backward){
      m_relay.set(Value.kOn);
  }
  else if(forwardRelay){
      m_relay.set(Value.kForward);
  }
  else if(backward){
      m_relay.set(Value.kReverse);
  }
  else{
      m_relay.set(Value.kOff);
  }
  double shootSpeed = flightStickTwo.getRawAxis(3);
  
  if(flightStickTwo.getRawButton(10)){
    intakeUpDown.set(0.40);
  }
  if(flightStickTwo.getRawButton(9)){
    intakeUpDown.set(-0.40);
  }
  else{
    intakeUpDown.set(0);
  }


  if(flightStickTwo.getRawButton(8)){
    m_shooterMotor.set(shootSpeed);
  }
  else{
    m_shooterMotor.set(0);
  }
  }
  

  /**
   * This function is called once when the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  /**
   * This function is called periodically when disabled.
   */
  @Override
  public void disabledPeriodic() {
  }

  /**
   * This function is called once when test mode is enabled.
   */
  @Override
  public void testInit() {
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
