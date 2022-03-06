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

/**********************************************************************************
 **********************************************************************************/

 public class TurnDegreesBetter extends CommandBase {
    double startAngle;
    double degrees;
    int iters;
    static private double driftAllowance=4;
    int targetReached=0;

	/**********************************************************************************
	 **********************************************************************************/
	
    public TurnDegreesBetter(double degrees_in, int iters_in ) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        degrees = degrees_in;
        iters = iters_in;
        targetReached=0;
    }

	/**********************************************************************************
     * Called just before this Command runs the first time
	 **********************************************************************************/
	
    public void initialize() {
        // Save the starting angle for the turn
        Robot.internalData.resetGyro();
        startAngle = Robot.internalData.getGyroAngle();
        Robot.driveBase.driveBrakeMode();
    }

	/**********************************************************************************
     * Called repeatedly when this Command is scheduled to run
	 **********************************************************************************/
	
    public void execute() {
        // get the current angle from the gyro
        double currentDegrees = Robot.internalData.getGyroAngle();
        double target = startAngle+degrees;
        double diff = currentDegrees - target;
        double lrInvert=1;
        double driveLr=0;

        double tmp = Math.abs(diff) / 100;
        if ( tmp > .6) { tmp=.6; }
        if ( tmp < .1) { tmp=.1; }

        if ( (currentDegrees >= target - driftAllowance) &&
             (currentDegrees <= target + driftAllowance) ) {
             // We are at the right angle
        } else if (currentDegrees < target) {
            if (diff > 3) {
                driveLr=tmp * lrInvert;;
            }
        } else {
            if (diff < -3) {
                driveLr=-tmp * lrInvert * -1;
            }    
        }

        Robot.driveBase.Drive(0, driveLr);
    }

	/**********************************************************************************
     * Make this return true when this Command no longer needs to run execute()
	 **********************************************************************************/
	
    public boolean isFinished() {
        iters--;
        double currentDegrees = Robot.internalData.getGyroAngle();

        if (((currentDegrees >= startAngle + degrees - driftAllowance) &&
             (currentDegrees <= startAngle + degrees + driftAllowance)) ||
             iters <= 0 ) {
            if (targetReached>=10 || iters <= 0) {
                // We have reached our target angle or run out of time to do so.
                Robot.driveBase.driveCoastMode();
                Robot.driveBase.Drive(0, 0);
                return true;
            }
            targetReached++;
        } else {
            targetReached=0;
        }

        return false;
    }

	/**********************************************************************************
     * Called once after isFinished returns true
	 **********************************************************************************/
	
    public void end(boolean isInteruppted) {
        Robot.driveBase.driveCoastMode();
        Robot.driveBase.Drive(0, 0);
    }
}
