/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;


import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Intake;
/**
 * An example command.  You can replace me with your own command.
 */

public class IntakeMoveMotor extends Command {
  public IntakeMoveMotor() {
    // Use requires() here to declare subsystem dependencies
    
    requires(Robot.m_subsystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double forward = -1.0 * Intake.xBoxJoy.getY(Hand.kLeft);	// Sign this so forward is positive
    double turn = +1.0 * Intake.xBoxJoy.getX(Hand.kLeft);       // Sign this so right is positive
    
        
        /* Deadband - within 10% joystick, make it zero */
		if (Math.abs(forward) < 0.10) {
			forward = 0;
		}
		if (Math.abs(turn) < 0.10) {
			turn = 0;
    }

    if(Intake.xBoxJoy.getRawButton(1)){
      Intake.DubSolenoid.set(Value.kForward);
    }
  
    if(Intake.xBoxJoy.getRawButton(2)){
      Intake.DubSolenoid.set(Value.kReverse);
    }
    Intake.intakeMotor.set(-Intake.xBoxJoy.getRawAxis(3)/2);            
  }



  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
