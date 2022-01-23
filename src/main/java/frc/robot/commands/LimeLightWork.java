package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LimeLightWork extends CommandBase {
    public static int iter=0;
    int centeredCount=0;
    boolean shootNow=false;

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
        SmartDashboard.putBoolean("shootnow:", shootNow);

        //if (Robot.trackTarget != Robot.targetTypes.throwingTarget &&
        //    Robot.trackTarget != Robot.targetTypes.turretOnly &&
        //    Robot.trackTarget != Robot.targetTypes.ballLLTarget ) {
        //    // We are not tracking the ball, just return
        //    shootNow=false;
		//	return;
        //}
        
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

            if (area < .2) {
                threshold = 1.5;
            } else if (area < 1) {
                threshold = 2.5;
            } else if (area < 2) {
                threshold = 3.5;
            } else {
                threshold = 4.5;
            }

            if ( Robot.limeLight.getllTargetX() < ( -1 * threshold ) ) {
                // Target is to the left of the Robot, need to move left
                Robot.robotTurn=-.25;
                if ( Robot.limeLight.getllTargetX() + threshold < ( -1 * threshold ) ) {
                    Robot.robotTurn=-.3;
                }
                centeredCount=0;
                shootNow=false;
            } else if ( Robot.limeLight.getllTargetX() > threshold ) {
                // Target is to the left of the Robot, need to move right
                Robot.robotTurn=.25;
                if ( Robot.limeLight.getllTargetX() - threshold > threshold ) {
                    Robot.robotTurn=.3;
                }
                centeredCount=0;
                shootNow=false;
            } else {
                centeredCount++;
                //if (Robot.trackTarget != Robot.targetTypes.ballLLTarget) {
                    if (centeredCount > 20) {
                        shootNow=true;
                    } else {
                        shootNow=false;
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
            shootNow=false;

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

	/************************************************************************
	 ************************************************************************/

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        return false;
    }
}
