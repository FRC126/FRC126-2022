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
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**********************************************************************************
 **********************************************************************************/

 public class DriveDistance extends CommandBase {
    double driveFb;
    double driveLr;
    double targetAngle;
    double distance;
    int iters;
    int reachedCount=0;

	/**********************************************************************************
	 **********************************************************************************/
	
    public DriveDistance(double distance_in, int iters_in ) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        driveFb = 0;
        driveLr = 0;
        distance = distance_in;
        iters = iters_in;
        reachedCount=0;
    }

	/**********************************************************************************
     * Called just before this Command runs the first time
	 **********************************************************************************/
	
    public void initialize() {
        Robot.internalData.resetGyro();
        targetAngle = Robot.internalData.getGyroAngle();
        Robot.driveBase.resetEncoders();
        Robot.driveBase.driveCoastMode();
    }

	/**********************************************************************************
     * Called repeatedly when this Command is scheduled to run
	 **********************************************************************************/
	
    public void execute() {
        double distanceInversion=1;

        double currentDistance = Robot.driveBase.getDistanceInches();
        double diff =  Math.abs(distance) - currentDistance;
        double tmp = Math.abs(diff) / 20;
        if ( tmp > .95) { tmp=.95; }
        if ( tmp < .15) { tmp=.15; }

        if (distance < 0) {
            distanceInversion=-1;
        }    

        if ( diff > 1 ) {
            driveFb = tmp * distanceInversion;
            reachedCount=0;
            Robot.driveBase.driveCoastMode();
        } else {
            driveFb=0;
            reachedCount++;
            Robot.driveBase.driveBrakeMode();
        }

        Robot.driveBase.Drive(driveFb, 0);

        SmartDashboard.putNumber("Drv Dist Spd",driveFb);

        // Try to keep the robot straight using the gyro
        if (driveFb != 0) {       
            if(Robot.internalData.getGyroAngle() - targetAngle > 0.5) {
                // We are drifiting to the left, correct
                Robot.driveBase.Drive(driveFb, 0.05);
            } else if (Robot.internalData.getGyroAngle() - targetAngle < -0.5) {
                // We are drifiting to the right, correct
                Robot.driveBase.Drive(driveFb, -0.05);
            } else {
                // Drive straight
                Robot.driveBase.Drive(driveFb, 0);
            }
        } else {
            Robot.driveBase.Drive(driveFb, 0);
        }    
     }

	/**********************************************************************************
     * Make this return true when this Command no longer needs to run execute()
	 **********************************************************************************/
	
    public boolean isFinished() {
        iters--;

        if (reachedCount > 5 || iters <= 0) {
            Robot.driveBase.Drive(0, 0);
            Robot.driveBase.driveCoastMode();
            return true;
        }
        return false;
    }

	/**********************************************************************************
     * Called once after isFinished returns true
	 **********************************************************************************/
	
    public void end(boolean isInteruppted) {
        Robot.driveBase.Drive(0, 0);
        Robot.driveBase.driveCoastMode();
    }
}

