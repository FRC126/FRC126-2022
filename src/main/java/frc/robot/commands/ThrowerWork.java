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
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**********************************************************************************
 **********************************************************************************/

 public class ThrowerWork extends CommandBase {
    int targetRPM, 
        iters=0;
    boolean reachedRPM=false;
    static int targetReachedCount=0;
    boolean autoThrow=false;
    boolean stopSpinning=true;
    int throwCount=0;

	/**********************************************************************************
	 **********************************************************************************/
	
    public ThrowerWork(int targetRPM_in, int iters_in, boolean autoThrow_in, boolean stopSpinning_in) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        targetRPM = targetRPM_in;
        iters = iters_in;
        reachedRPM=false;
        targetReachedCount=0;
        autoThrow=autoThrow_in;
        stopSpinning=stopSpinning_in;
        throwCount=0;
    }

	/**********************************************************************************
     * Called just before this Command runs the first time
	 **********************************************************************************/
	
    public void initialize() {
        reachedRPM=false;
        targetReachedCount=0;
        throwCount=0;
    }

	/**********************************************************************************
     * Called repeatedly when this Command is scheduled to run
	 **********************************************************************************/
	
    public void execute() {
        //  Call Thrower RPM to spin up the thrower wheels.
        iters--;
        reachedRPM = Robot.ballThrower.throwerRPM(targetRPM);

        if (targetRPM == 0) {
            // Short Circuit the stop
            reachedRPM=true;
            targetReachedCount=50;
        }

        if(reachedRPM) {
            targetReachedCount++;
        }

        if (targetReachedCount>5 && autoThrow) {
            Robot.ballThrower.ThrowerIntakeRun();
            Robot.ballIntake.IntakeRun();
            throwCount++;
        }

    }

	/**********************************************************************************
     * Make this return true when this Command no longer needs to run execute()
	 **********************************************************************************/
	
    public boolean isFinished() {
        if (reachedRPM && targetReachedCount > 10 && (!autoThrow || throwCount > 60) && iters <= 0) {
            // If we reached the target RPM and the number of iterations has expired
            // Finish this command.
            Robot.ballThrower.ThrowerIntakeStop();
            Robot.ballIntake.IntakeStop();
            return true;
        }
        return false;
    }

	/**********************************************************************************
     * Called once after isFinished returns true
	 **********************************************************************************/
	
    public void end(boolean isInteruppted) {
        if (stopSpinning == true) {
            Robot.ballThrower.throwerRPM(RobotMap.idleThrow);
        }    
        Robot.ballThrower.ThrowerIntakeStop();
    }

}

