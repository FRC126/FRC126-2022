// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/**********************************
	   _      ___      ____
	 /' \   /'___`\   /'___\
	/\_, \ /\_\ /\ \ /\ \__/
	\/_/\ \\/_/// /__\ \  _``\
	   \ \ \  // /_\ \\ \ \L\ \
	    \ \_\/\______/ \ \____/
		 \/_/\/_____/   \/___/

    Team 126 2022 Code       
	Go get em gaels!

***********************************/


package frc.robot;


import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoSink;
import edu.wpi.first.cscore.VideoSource;

import edu.wpi.first.wpilibj.TimedRobot;
//import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.DigitalInput;
//import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.cameraserver.CameraServer;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
//import com.revrobotics.ColorSensorV3;

import com.ctre.phoenix.motorcontrol.can.*;
import frc.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  public static CANSparkMax sparkMax1 = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);
  public static CANSparkMax sparkMax2 = new CANSparkMax(RobotMap.SparkMax2, CANSparkMaxLowLevel.MotorType.kBrushless);
  //public static CANSparkMax sparkMax3 = new CANSparkMax(RobotMap.SparkMax3, CANSparkMaxLowLevel.MotorType.kBrushless);
  public static CANSparkMax sparkMax4 = new CANSparkMax(RobotMap.SparkMax4, CANSparkMaxLowLevel.MotorType.kBrushless);

  public static TalonFX throw1 = new TalonFX(RobotMap.ThrowerMotorCanID1);
  public static TalonFX throw2 = new TalonFX(RobotMap.ThrowerMotorCanID2);

  public static TalonFX LeftClimber = new TalonFX(RobotMap.LeftClimberMotor);
  public static TalonFX RightClimber = new TalonFX(RobotMap.RightClimberMotor);

  public static TalonFX leftDriveMotor1 = new TalonFX(RobotMap.LeftDriveMotorCanID1);
  public static TalonFX leftDriveMotor2 = new TalonFX(RobotMap.LeftDriveMotorCanID2);
  public static TalonFX rightDriveMotor1 = new TalonFX(RobotMap.RightDriveMotorCanID1);
  public static TalonFX rightDriveMotor2 = new TalonFX(RobotMap.RightDriveMotorCanID2);
  
  public int RobotID;
  public static int objectId=1;

	public static double voltageThreshold = 10.0;


  // Automation Variables
  public static double robotTurn = 0;
	public static double robotDrive = 0;
  public static boolean shootNow = false;
  public static boolean pickupNow = false;

  public static Controllers oi;
  public static Log log;
  public static InternalData internalData;
  public static BallThrower ballThrower;
  public static BallIntake ballIntake;
  public static WestCoastDrive driveBase;
  public static PixyVision pixyVision;
  public static LimeLight limeLight;
	public static UsbCamera driveCam;
	public static VideoSink server;

  public static enum targetHeights{LowTarget,HighTarget};
  public static enum targetTypes{NoTarget,BallSeek,TargetSeek, PixyTargetSeek};
  public static enum allianceColor{Red,Blue};
  public static targetTypes targetType = Robot.targetTypes.NoTarget;

  
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    
    // TODO Read from SmartDashboard to switch between platforms
    RobotID=0;
    RobotMap.setRobot(RobotID);

    oi = new Controllers();
    log = new Log();
    internalData = new InternalData();

    CommandScheduler.getInstance().enable();

    // Create and register the motorController Subsystem
    ballThrower = new BallThrower();
    ballIntake = new BallIntake();
    driveBase = new WestCoastDrive();
    pixyVision = new PixyVision();
    limeLight = new LimeLight();

    internalData.initGyro();
    internalData.resetGyro();

    driveCam = CameraServer.startAutomaticCapture();
		server = CameraServer.getServer();

    driveCam.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
		server.setSource(driveCam);
    

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
    //Log.print(0, "Robot", "Robot Teleop periodic");
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    CommandScheduler.getInstance().run();
  }
}
