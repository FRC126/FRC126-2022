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

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.RobotMap;
 
/**********************************************************************************
 **********************************************************************************/

public class AutoOneBall extends SequentialCommandGroup {
    public AutoOneBall() {

        addCommands(

            /////////////////////////////////////////////////////////////////////////
            // Throw the first Ball
            /////////////////////////////////////////////////////////////////////////

            new AutoOneBallThrow(),

            // Idle the thrower
            new ThrowerWork(RobotMap.idleThrow, 0, false, false),

            // Backup past the line
            new DriveDistance(-24,100)
        );
    }       

    /******************************************************************************************
     * Called once after isFinished returns true
     ******************************************************************************************/

     @Override
    public void end(boolean isInterrupted) {
        Robot.ballIntake.IntakeStop();
        Robot.driveBase.Drive(0,0);
        Robot.ballThrower.ThrowerIntakeStop();
    }     
}

