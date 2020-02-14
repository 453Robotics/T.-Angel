/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.GenericHID.Hand;
//import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants.motorIDConst;
import frc.robot.commands.IntakeReverse;
import frc.robot.commands.IntakeForward;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class Intake extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public static WPI_VictorSPX intakeMotor = new WPI_VictorSPX(motorIDConst.intakeID); // creating intake motor. motorID.Const is imported from the constant file to get the motors ID
  public static Joystick xBoxJoy = new Joystick(0); // creating an xboxJoystick.
  public static DoubleSolenoid DubSolenoid = new DoubleSolenoid(0, 1); // pneumatics 
  IntakeReverse intakeCommand = new IntakeReverse();
  IntakeForward intakeCommandTwo = new IntakeForward();
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(intakeCommand);

    
 

  }
  /**
 * @return the intakeMotor
 */
public WPI_VictorSPX getIntakeMotor() {
	return intakeMotor;
}


public void teleopInit(){
  double forward = -1.0 * Intake.xBoxJoy.getY(Hand.kLeft);	// Sign this so forward is positive
  double turn = +1.0 * Intake.xBoxJoy.getX(Hand.kLeft);       // Sign this so right is positive
  
      
      /* Deadband - within 10% joystick, make it zero */
  if (Math.abs(forward) < 0.10) {
    forward = 0;  // dead zones
  }
  if (Math.abs(turn) < 0.10) {
    turn = 0; // deadzones
  }

  if(Intake.xBoxJoy.getRawButton(1)){           //Puts intake out of frame
    intakeCommandTwo.start();
    
  }

  if(Intake.xBoxJoy.getRawButton(2)){           //Puts intake in frame
    intakeCommand.start();
    
  }

  Intake.intakeMotor.set(-Intake.xBoxJoy.getRawAxis(3)/2);  //joystick foward moves intake in  

  }
}
