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

package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LimeLightWork extends CommandBase {
    public static int iter=0;
    int centeredCount=0;

	/************************************************************************
	 ************************************************************************/

    public LimeLightWork(LimeLight subsystem) {
		addRequirements(subsystem);
    }

	/************************************************************************
	 ************************************************************************/

    // Called just before this Command runs the first time
    public void initialize() {
    }

	/************************************************************************
	 ************************************************************************/

    // Called repeatedly when this Command is scheduled to run
    public void execute() {
        SmartDashboard.putBoolean("shootnow:", Robot.shootNow);

        NetworkTable networkTable = getLimeLightNetworkTable();
        SmartDashboard.putNumber("LL tv", networkTable.getEntry("tv").getDouble(0));
        SmartDashboard.putNumber("LL tx", networkTable.getEntry("tx").getDouble(0));
        SmartDashboard.putNumber("LL ty", networkTable.getEntry("ty").getDouble(0));
        SmartDashboard.putNumber("LL ta", networkTable.getEntry("ta").getDouble(0));
        SmartDashboard.putNumber("LL ts", networkTable.getEntry("ts").getDouble(0));
        SmartDashboard.putNumber("LL tl", networkTable.getEntry("tl").getDouble(0));
        SmartDashboard.putNumber("LL tshort", networkTable.getEntry("tshort").getDouble(0));
        SmartDashboard.putNumber("LL tlong", networkTable.getEntry("tlong").getDouble(0));
        SmartDashboard.putNumber("LL thor", networkTable.getEntry("thor").getDouble(0));
        SmartDashboard.putNumber("LL tvert", networkTable.getEntry("tvert").getDouble(0));
        SmartDashboard.putNumber("LL getpipe", networkTable.getEntry("getpipe").getDouble(0));
        SmartDashboard.putNumber("LL camtran", networkTable.getEntry("camtran").getDouble(0));

        if (Robot.targetType != Robot.targetTypes.TargetSeek) {
            Robot.shootNow=false;
            Robot.ThrowerRPM=0;
            //.limeLight.setLED(false);
		 	return;
        }

        Robot.limeLight.setLED(true);
        
        Robot.limeLight.getCameraData();

        SmartDashboard.putBoolean("LL Valid:", Robot.limeLight.getllTargetValid());

        if (Robot.limeLight.getllTargetValid()){
            // We found a valid vision target.
            iter=0;

            Robot.robotDrive=0;

            double area = Robot.limeLight.getllTargetArea();
            double threshold;

            //System.out.println("LimeLightWork: X: " + Robot.limeLight.getllTargetX() + " Area: " + area );

            SmartDashboard.putNumber("LL Area:", area);

            if ( area < .02 ) { Robot.ThrowerRPM = 9000; }
            if ( area > .02 && area < 0.05 ) { Robot.ThrowerRPM = 6000; }
            if ( area > .05 && area < 0.1 ) { Robot.ThrowerRPM = 3000; }

            SmartDashboard.putNumber("Thrower RPM:", Robot.ThrowerRPM);

            if (area < .1) {
                threshold = 25;
            } else if (area < 1) {
                threshold = 25;
            } else if (area < 2) {
                threshold = 35;
            } else {
                threshold = 45;
            }

            SmartDashboard.putNumber("getllTargetX:", Robot.limeLight.getllTargetX());

            if ( Robot.limeLight.getllTargetX() < ( -1 * threshold ) ) {
                // Target is to the left of the Robot, need to move left
                Robot.robotTurn=-.25;
                if ( Robot.limeLight.getllTargetX() + threshold < ( -1 * threshold ) ) {
                    Robot.robotTurn=-.3;
                }
                centeredCount=0;
                Robot.shootNow=false;
            } else if ( Robot.limeLight.getllTargetX() > threshold ) {
                // Target is to the left of the Robot, need to move right
                Robot.robotTurn=.25;
                if ( Robot.limeLight.getllTargetX() - threshold > threshold ) {
                    Robot.robotTurn=.3;
                }
                centeredCount=0;
                Robot.shootNow=false;
            } else {
                centeredCount++;
                //if (Robot.trackTarget != Robot.targetTypes.ballLLTarget) {
                    if (centeredCount > 20) {
                        Robot.shootNow=true;
                    } else {
                        Robot.shootNow=false;
                    }
                //}    
                Robot.robotTurn=0;
            }

            //if (Robot.trackTarget != Robot.targetTypes.ballLLTarget) {
            //  if ( Robot.limeLight.getllTargetX() < -.10 ) {
            //        Robot.limeLight.setllTurretTarget((int)(Robot.limeLight.getllTargetX() * 15));
            //  } else if ( Robot.limeLight.getllTargetX() > .10 ) {
            //      Robot.limeLight.setllTurretTarget((int)(Robot.limeLight.getllTargetX() * 15));
            //  } else {
            //      Robot.limeLight.setllTurretTarget(0);
            //  }
            //  Robot.robotDrive=0;
            //} else {
                Robot.limeLight.setllTurretTarget(0);
                if ( area < 6 ) {
                    Robot.robotDrive=.25;
                } else {
                    Robot.robotDrive=0;
                }
            //}
        } else {
            iter++;
            centeredCount=0;
            Robot.shootNow=false;
            Robot.ThrowerRPM=0;

            if (iter > 10 && iter < 350) {
                // Try turning until we pick up a target
                Robot.robotTurn= -0.3;
            } else {
                Robot.robotTurn=0;
            }    

            if ( iter > 500 ) { 
                iter=0; 
            }

            // Don't move forward or back
            Robot.robotDrive=0;
        }
    }        
    
    private NetworkTable getLimeLightNetworkTable(){
        return NetworkTableInstance.getDefault().getTable("limelight");
    }    

	/************************************************************************
	 ************************************************************************/

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        return false;
    }
}
