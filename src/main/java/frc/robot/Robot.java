// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ColorSensorV3;

import com.ctre.phoenix.motorcontrol.can.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.RobotMap;
import frc.robot.Controllers;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  public static CANSparkMax sparkMax1 = new CANSparkMax(RobotMap.SparkMax1, CANSparkMaxLowLevel.MotorType.kBrushless);
  public static CANSparkMax sparkMax2 = new CANSparkMax(RobotMap.SparkMax2, CANSparkMaxLowLevel.MotorType.kBrushless);
  //public static CANSparkMax sparkMax3 = new CANSparkMax(RobotMap.SparkMax3, CANSparkMaxLowLevel.MotorType.kBrushless);
  public static CANSparkMax sparkMax4 = new CANSparkMax(RobotMap.SparkMax4, CANSparkMaxLowLevel.MotorType.kBrushless);
  public static TalonFX throw1 = new TalonFX(9);
  public static TalonFX throw2 = new TalonFX(10);
  public int RobotID;

  public static double speed = 0;
  public static int delay = 0;

  public static Controllers oi;
  public static Log log;
  public static InternalData internalData;
  public static MotorControl motorControl;
  
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    
    RobotMap.setRobot(0);

    oi = new Controllers();
    log = new Log();
    internalData = new InternalData();
    motorControl = new MotorControl();

    internalData.initGyro();
    internalData.resetGyro();

    Log.print(0, "Robot", "Robot Init Complete");
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    Log.print(0, "Robot", "Robot Autonomous Init");
    
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    Log.print(0, "Robot", "Robot Autonomous Run");

    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {
    Log.print(0, "Robot", "Robot Teleop Init");
  }

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    // Log.print(0, "Robot", "Robot Teleop Run");

    OperatorControl foo = new OperatorControl();
    foo.execute();

    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
