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

 public class RaiseClimberArms extends CommandBase {
    int iters;
    boolean targetReached;

	/**********************************************************************************
	 **********************************************************************************/
	
    public RaiseClimberArms(int itersIn) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        targetReached=false;
        iters=itersIn;
    }

	/**********************************************************************************
     * Called just before this Command runs the first time
	 **********************************************************************************/
	
    public void initialize() {
        targetReached=false;
    }

	/**********************************************************************************
     * Called repeatedly when this Command is scheduled to run
	 **********************************************************************************/
	
    public void execute() {
        boolean ret=Robot.verticalClimber.RaiseClimber();

        if (ret) {
            Robot.verticalClimber.StopClimber();
            targetReached=true;
        }
    }

	/**********************************************************************************
     * Make this return true when this Command no longer needs to run execute()
	 **********************************************************************************/
	
    public boolean isFinished() {
        iters--;

        if (targetReached || iters < 0) {
            Robot.verticalClimber.StopClimber();
            return true;
        }

        return false;
    }

	/**********************************************************************************
     * Called once after isFinished returns true
	 **********************************************************************************/
	
    public void end(boolean isInteruppted) {
        Robot.verticalClimber.StopClimber();
    }
}
