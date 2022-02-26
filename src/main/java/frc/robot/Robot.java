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
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.ctre.phoenix.motorcontrol.can.*;
import frc.robot.subsystems.*;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {

    // Ball Intake Motors
    public static CANSparkMax intakeMotor1 = new CANSparkMax(RobotMap.intakeMotor1CanID, CANSparkMaxLowLevel.MotorType.kBrushless);
    public static CANSparkMax intakeMotor2 = new CANSparkMax(RobotMap.intakeMotor2CanID, CANSparkMaxLowLevel.MotorType.kBrushless);

    // Ball Feeder Motor
    public static CANSparkMax feederMotor = new CANSparkMax(RobotMap.feederMotorCanID, CANSparkMaxLowLevel.MotorType.kBrushless);

    // Climber Motors
    public static TalonFX climberMotorLeft = new TalonFX(RobotMap.climberMotorLCanID);
    public static TalonFX climberMotorRight = new TalonFX(RobotMap.climberMotorRCanID);

    // Thrower Motors
    public static TalonFX throwerMotor1 = new TalonFX(RobotMap.throwerMotorCanID1);
    public static TalonFX throwerMotor2 = new TalonFX(RobotMap.throwerMotorCanID2);

    // Driver Base Motors
    public static TalonFX leftDriveMotor1 = new TalonFX(RobotMap.leftDriveMotorCanID1);
    public static TalonFX leftDriveMotor2 = new TalonFX(RobotMap.leftDriveMotorCanID2);
    public static TalonFX rightDriveMotor1 = new TalonFX(RobotMap.rightDriveMotorCanID1);
    public static TalonFX rightDriveMotor2 = new TalonFX(RobotMap.rightDriveMotorCanID2); 

    // Lidar Light Distance Measure
    public static LidarLite distance;

    // Automation Variables
    public static double robotTurn = 0;
	public static double robotDrive = 0;
    public static boolean shootNow = false;
    public static boolean pickupNow = false;
    public static boolean isThrowCommand=false;
    public static targetTypes targetType = Robot.targetTypes.NoTarget;
    public static int objectId=1;

    // Subsystems
    public static Controllers oi;
    public static Log log;
    public static InternalData internalData;
    public static BallThrower ballThrower;
    public static BallIntake ballIntake;
    public static WestCoastDrive driveBase;
    public static PixyVision pixyVision;
    public static VerticalClimber verticalClimber;
    public static LimeLight limeLight;
	public static UsbCamera driveCam;
	public static VideoSink server;
    public static SequentialCommandGroup autonomous;
    public static boolean intakeRunning=false;
    public static boolean throwerRunning=false;

    // Global Robot Variables
    public int RobotID = 0;

    public static DigitalInput rightClimbLimit;
	public static DigitalInput leftClimbLimit;
    public static enum targetHeights{LowTarget,HighTarget};
    public static enum targetTypes{NoTarget,BallSeek,TargetSeek, PixyTargetSeek};
    public static enum allianceColor{Red,Blue};
	public static double voltageThreshold = 10.0;

    public static Compressor compressor;

    // For use with limelight class
    public static double ThrowerRPM=0;

    int selectedAutoPosition;
	int selectedAutoFunction;
	
	@SuppressWarnings("rawtypes")
	SendableChooser autoPosition = new SendableChooser(); // Position chooser
	@SuppressWarnings("rawtypes")
	SendableChooser autoFunction = new SendableChooser(); // Priority chooser

 	  /************************************************************************
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
	   ************************************************************************/
    @Override
    public void robotInit() {
        // Set the robot id for use by RobotMap
        RobotMap.setRobot(RobotID);

        // Enable the command scheduler
        CommandScheduler.getInstance().enable();

        // Create and register the robot Subsystems
        oi = new Controllers();
        log = new Log();
        internalData = new InternalData();
        ballThrower = new BallThrower();
        ballIntake = new BallIntake();
        driveBase = new WestCoastDrive();
        limeLight = new LimeLight();
        verticalClimber = new VerticalClimber();

        // create the lidarlite class on DIO 5
        // distance = new LidarLite(new DigitalInput(5));

        // Not using the PIXY right now
        //pixyVision = new PixyVision();

        // Limit switches on the climbers
        rightClimbLimit = new DigitalInput(0);
        leftClimbLimit = new DigitalInput(1);

        compressor = new Compressor(2, PneumaticsModuleType.REVPH);
        compressor.enableDigital();

        // Initialize the built in gyro
        internalData.initGyro();
        internalData.resetGyro();

        // Start the camera server for the drive camera
        driveCam = CameraServer.startAutomaticCapture();
		server = CameraServer.getServer();
        driveCam.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
		server.setSource(driveCam);

        autoFunction.setDefaultOption("One_Ball_Auto (default)",0);
        autoFunction.addOption("Two_Ball_Auto",1);
        autoFunction.addOption("One_Ball_Plus_OneAuto",2);

        autoPosition.setDefaultOption("Right Side (default)",0);
        autoPosition.addOption("Middle",1);
        autoPosition.addOption("Left Side",2);

        Log.print(0, "Robot", "Robot Init Complete");
    }

 	  /************************************************************************
	   * This function is run once each time the robot enters autonomous mode. 
     ************************************************************************/
    @Override
    public void autonomousInit() {
        Log.print(0, "Robot", "Robot Autonomous Init");

        try {
			selectedAutoPosition = (int) autoPosition.getSelected();
		} catch(NullPointerException e) {
			selectedAutoPosition = 0;
		}
		try {
			selectedAutoFunction = (int) autoFunction.getSelected();
		} catch(NullPointerException e) {
			selectedAutoFunction = 0;
		}

        // Position doesn't matter right now.
        if (selectedAutoFunction == 0) {
            autonomous = new AutoOneBall();
        } else if (selectedAutoFunction == 1) {
            autonomous = new AutoTwoBall();
        } else if (selectedAutoFunction == 2) {
            autonomous = new AutoOneBallPlusOne();
        }
    }

 	  /************************************************************************
     * This function is called periodically during autonomous.
	   ************************************************************************/
    @Override
    public void autonomousPeriodic() {
        CommandScheduler.getInstance().run();
    }

 	  /************************************************************************
     * This function is called once each time the robot enters teleoperated mode.
	   ************************************************************************/
    @Override
    public void teleopInit() { 
        Log.print(0, "Robot", "Robot Teleop Init");
  
        if(autonomous != null){
            // Cancel the auto command if it was created
	          autonomous.cancel();
        }
    }

 	  /************************************************************************
     * This function is called periodically during teleoperated mode.
	   ************************************************************************/
    @Override
    public void teleopPeriodic() {
        CommandScheduler.getInstance().run();
    }

 	  /************************************************************************
     * This function is called once each time the robot enters test mode.  
	   ************************************************************************/
    @Override
    public void testInit() {
        Log.print(0, "Robot", "Robot Test Init");
    }  

 	  /************************************************************************
     * This function is called periodically during test mode.
	   ************************************************************************/
   @Override
    public void testPeriodic() {
        CommandScheduler.getInstance().run();
    }
}
