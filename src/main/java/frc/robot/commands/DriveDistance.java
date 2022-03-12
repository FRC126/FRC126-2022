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
        //Robot.driveBase.driveBrakeMode();
    }

	/**********************************************************************************
     * Called repeatedly when this Command is scheduled to run
	 **********************************************************************************/
	
    public void execute() {
        double inversion=1;

        double currentDistance = Robot.driveBase.getDistanceInches();
        double diff =  Math.abs(distance) - currentDistance;
        double tmp = Math.abs(diff) / 20;
        if ( tmp > .45) { tmp=.45; }
        if ( tmp < .15) { tmp=.15; }


        if (distance < 0) {
            if ( diff > 1 ) {
                driveFb = tmp * inversion * -1;
            } else {
                driveFb=0;
            }
        } else {
            if ( diff > 1 ) {
                driveFb = tmp * inversion;
            } else {
                driveFb=0;
            }
        }

        SmartDashboard.putNumber("Drv Dist Spd",driveFb);

        // if(Robot.internalData.getGyroAngle() - targetAngle > 1) {
        //     // We are drifiting to the left, correct
        //     Robot.driveBase.Drive(driveFb, 0.05);
        // }
        // else if(Robot.internalData.getGyroAngle() - targetAngle < -1) {
        //     // We are drifiting to the right, correct
        //     Robot.driveBase.Drive(driveFb, -0.05);
        // } else {
        //     // Drive straight
             Robot.driveBase.Drive(driveFb, 0);
        //}
     }

	/**********************************************************************************
     * Make this return true when this Command no longer needs to run execute()
	 **********************************************************************************/
	
    public boolean isFinished() {
        iters--;
        double currentDistance = Robot.driveBase.getDistanceInches();
        if ((currentDistance >= distance - 1 && currentDistance <= distance + 1)|| iters <= 0) {
            // if we have reached the target distance, or run out of time to do so, 
            // stop driving and end the command.

            Robot.driveBase.Drive(0, 0);

            if (reachedCount > 10 || iters <= 0) {
                Robot.driveBase.driveCoastMode();
                return true;
            }
            reachedCount++;    
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

