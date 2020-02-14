package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Controller;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.Joystick;


import com.revrobotics.ColorSensorV3;
//import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
//import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class ColorSensor
{
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
    private final ColorMatch m_colorMatcher = new ColorMatch();
    public boolean colorButtonOn = false;


    WPI_TalonSRX spinner = new WPI_TalonSRX(10);

    Joystick _joy = new Joystick(0);

    //colorSpinner.configFactoryDefault();
    
    //set colors
    private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

    public void periodic() {
        // This method will be called once per scheduler run
        if(_joy.getRawButton(1) && colorButtonOn == false){
          //turn on the motor
           
          //change bool to say that color sensore is on
          colorButtonOn = true;
          //call class to find color
          Color detectedColor = m_colorSensor.getColor();
          ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
          String colorString;
    
    

          if (match.color == kBlueTarget) {
            colorString = "Blue";
          } else if (match.color == kRedTarget) {
            colorString = "Red";
          } else if (match.color == kGreenTarget) {
            colorString = "Green";
          } else if (match.color == kYellowTarget) {
            colorString = "Yellow";
          } else {
            colorString = "Unknown";
          }
          SmartDashboard.putNumber("Red", detectedColor.red);

          SmartDashboard.putNumber("Green", detectedColor.green);

          SmartDashboard.putNumber("Blue", detectedColor.blue);

          SmartDashboard.putNumber("Confidence", match.confidence);

          SmartDashboard.putString("Detected Color", colorString);
          
        }
        //if pressed again turn it off
        else if(_joy.getRawButton(1) && colorButtonOn == true)
        {
           colorButtonOn = false;
        }
    }

    public void init()
    {
      m_colorMatcher.addColorMatch(kBlueTarget);

      m_colorMatcher.addColorMatch(kGreenTarget);
  
      m_colorMatcher.addColorMatch(kRedTarget);
  
      m_colorMatcher.addColorMatch(kYellowTarget); 
    }
 /**
     * The method GetColor() returns a normalized color value from the sensor and can be
     * useful if outputting the color to an RGB LED or similar. To
     * read the raw color, use GetRawColor().
     * 
     * The color sensor works best when within a few inches from an object in
     * well lit conditions (the built in LED is a big help here!). The farther
     * an object is the more light from the surroundings will bleed into the 
     * measurements and make it difficult to accurately determine its color.
     */
   

    /**
     * Run the color match algorithm on our detected color
     */
   

    

    
}