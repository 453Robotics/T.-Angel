/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.awt.Button;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;



/**
 * An example command that uses an example subsystem.
 */
public class ShootingButton extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  Shooter m_subsystem;
  //Adjustable speed on right motor
  static double adjustablespeed = 0.45;
  Joystick shootJoystick = new Joystick(0);

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ShootingButton(final Shooter subsystem) {
    m_subsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // displays value for speed
  public static double adjustablespeeddisplay() {
    return adjustablespeed;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putNumber("Right  Motor Speed", ShootingButton.adjustablespeeddisplay());
    
    if(shootJoystick.getRawButton(4) && adjustablespeed > 0.00)
    {
      adjustablespeed -= .05;
    }
    
    if(shootJoystick.getRawButton(5))
    {
      adjustablespeed += .05;
    }
     
    if(shootJoystick.getRawButton(0))
    {
      m_subsystem.m_shooterMotor.set(adjustablespeed);
      m_subsystem.m_shooterMotor2.set(-.45); 
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(final boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}