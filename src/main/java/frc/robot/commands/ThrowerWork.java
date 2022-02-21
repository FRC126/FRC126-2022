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

/**
 *
 */
public class ThrowerWork extends CommandBase {
    int targetRPM, 
        iters=0;
    boolean reachedRPM=false;

	/**********************************************************************************
	 **********************************************************************************/
	
    public ThrowerWork(int targetRPM_in, int iters_in) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        targetRPM = targetRPM_in;
        iters = iters_in;
    }

	/**********************************************************************************
	 **********************************************************************************/
	
    // Called just before this Command runs the first time
    public void initialize() {
    }

	/**********************************************************************************
	 **********************************************************************************/
	
    // Called repeatedly when this Command is scheduled to run
    public void execute() {
        iters--;
        reachedRPM = Robot.ballThrower.throwerRPM(targetRPM);
    }

	/**********************************************************************************
	 **********************************************************************************/
	
    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        if (reachedRPM && iters <= 0) {
            return true;
        }
        return false;
    }

	/**********************************************************************************
	 **********************************************************************************/
	
    // Called once after isFinished returns true
    public void end(boolean isInteruppted) {
        Robot.ballThrower.throwerRPM(0);
    }

}

