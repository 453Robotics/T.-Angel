/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

//import com.ctre.phoenix.motorcontrol.can.TalonSRX;
//import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.IntakeMoveMotor;
import frc.robot.subsystems.Intake;
import frc.robot.Constants.motorIDConst;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static Intake m_subsystem = new Intake();
  public static OI m_oi;
  
  Joystick flightStick = new Joystick(1);
  public static Joystick flightStickTwo = new Joystick(2);
  Joystick arcadeStick = new Joystick(3);
  
 // private static WPI_TalonSRX m_leftMotor = new WPI_TalonSRX(leftDeviceID);
 // private static WPI_TalonSRX m_rightMotor = new WPI_TalonSRX(rightDeviceID);
  private static CANSparkMax m_leftMotor = new CANSparkMax(motorIDConst.leftDeviceID, MotorType.kBrushless);
  private static CANSparkMax m_rightMotor = new CANSparkMax(motorIDConst.rightDeviceID, MotorType.kBrushless);
 // TalonSRX m_leftMotorf = new TalonSRX(leftFDeviceID);
  //TalonSRX m_rightMotorf = new TalonSRX(rightFDeviceID);
  CANSparkMax m_leftMotorf = new CANSparkMax(motorIDConst.leftFDeviceID, MotorType.kBrushless);
  CANSparkMax m_rightMotorf = new CANSparkMax(motorIDConst.rightFDeviceID, MotorType.kBrushless);
  DifferentialDrive m_myRobot = new DifferentialDrive(m_leftMotor, m_rightMotor);
  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();
  SendableChooser<Integer> controlChooser = new SendableChooser<>();
 

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_oi = new OI();
    m_chooser.setDefaultOption("Default Auto", new IntakeMoveMotor());
    // chooser.addOption("My Auto", new MyAutoCommand());
    SmartDashboard.putData("Auto mode", m_chooser);

    controlChooser.setDefaultOption("xBox Joystick-Arcade Drive", 0);
    controlChooser.addOption("Flight Joystick", 1);
    controlChooser.addOption("Flight Joystick Two", 2);
    controlChooser.addOption("Arcade Stick", 3);

    m_rightMotorf.follow(m_rightMotor);
    m_leftMotorf.follow(m_leftMotor);
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
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    


  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
	if(controlChooser.getSelected() == 0){
      m_myRobot.arcadeDrive(-xBoxJoy.getY(Hand.kLeft), xBoxJoy.getX(Hand.kLeft));//arcade drive, leftJoystick=foward/backwards and turn
    }

    else if(controlChooser.getSelected() == 1){
      m_myRobot.tankDrive(flightStick.getY(), flightStickTwo.getY()); // flight stick tankdrive
    }
    else if(controlChooser.getSelected() == 2){
      m_myRobot.tankDrive(-xBoxJoy.getY(Hand.kLeft), xBoxJoy.getX(Hand.kRight)); // xBox Tankdrive
    }
    else if(controlChooser.getSelected() == 3){// Flight stick arcade drive
      if(flightStick.getRawAxis(1) <= -.05 || flightStick.getRawAxis(1) >= .05){
        //forward or back
        SmartDashboard.putNumber("JoyY", flightStick.getRawAxis(1));//
        m_myRobot.arcadeDrive(flightStick.getRawAxis(1) * -1, 0);
      }
      if(flightStick.getRawAxis(0) >= .1 || flightStick.getRawAxis(0) <= -.1){
        //turning
        SmartDashboard.putNumber("JoyX", flightStick.getRawAxis(0));
        m_myRobot.arcadeDrive(0, flightStick.getRawAxis(0));
      }
    }
      
      else{
        SmartDashboard.putString("nothing?", "yes");
        m_leftMotor.set(0);
        m_rightMotor.set(0);
      }
    }
      //if forward both motors go
     // m_leadMotor.set(m_joystick.getY());
  }
