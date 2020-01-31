/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.Constants.motorIDConst;
import frc.robot.commands.ShooterCommandBack;
import frc.robot.commands.ShooterCommandForw;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Shooter extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
 public static WPI_TalonSRX shooterMone = new WPI_TalonSRX(motorIDConst.shooterOne);
 public static WPI_TalonSRX shooterMtwo = new WPI_TalonSRX(motorIDConst.shooterTwo);
 public static Joystick xBoxJoy = new Joystick(0); // creating an xboxJoystick.
 ShooterCommandBack backwardsCommand;
 ShooterCommandForw forwardsCommand;
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
  public void teleOpinit(){
    if(xBoxJoy.getRawButton(3)){
      forwardsCommand.start();
    }
    if (xBoxJoy.getRawButtonReleased(3)){
      forwardsCommand.close();
    }
    if(xBoxJoy.getRawButton(4)){
      backwardsCommand.start();
    }
    if(xBoxJoy.getRawButtonReleased(4)){
      backwardsCommand.cancel();
    }
  }
}
