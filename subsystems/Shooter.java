
package frc.robot.subsystems;

/*----------------------------------------------------------------------------*/

/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants.encoderConstants;



public class Shooter extends PIDSubsystem {
public WPI_TalonSRX m_shooterMotor = new WPI_TalonSRX(0);//placeholder device number
public WPI_TalonSRX m_shooterMotor2 = new WPI_TalonSRX(1);//placeholder device number
 // private final Encoder m_shooterEncoder =
  //    new Encoder(0, 1,true);
 // private final SimpleMotorFeedforward m_shooterFeedforward =
 //     new SimpleMotorFeedforward(0.05, 0.24);

  /**
   * The shooter subsystem for the robot.
   */
  public Shooter() {
    super(new PIDController(1,0,0));
    getController().setTolerance(50);
    encoderConstants.m_shooterEncoder.setDistancePerPulse(0.0009765625);
    setSetpoint(4000);
  }

  @Override
  public void useOutput(double output, double setpoint) {
    m_shooterMotor.setVoltage(output + encoderConstants.m_shooterFeedforward.calculate(setpoint));
  }

  @Override
  public double getMeasurement() {
    return encoderConstants.m_shooterEncoder.getRate();
  }

  public boolean atSetpoint() {
    return m_controller.atSetpoint();
  
  }


  
/*
  public void runFeeder() {
    m_feederMotor.set(ShooterConstants.kFeederSpeed);
  }
  public void stopFeeder() {
    m_feederMotor.set(0);
  }
  */
}