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
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**********************************************************************************
 **********************************************************************************/

public class AutoFiveBall extends SequentialCommandGroup {
    public AutoFiveBall() {

        addCommands(
           
            new AutoThreeBall(),          

            /////////////////////////////////////////////////////////////////////////
            // Go towards the human player station
            /////////////////////////////////////////////////////////////////////////

            // Stop the trower Intake Motor
            new InstantCommand(Robot.ballThrower::ThrowerIntakeStop, Robot.ballThrower),

            // Turn towards human player station
            new TurnDegreesBetter(-150, 150),

            // Drive to throw spot for the 2 balls 
            new DriveDistance(96, 250),

            // Stop the ball intake
            new InstantCommand(Robot.ballIntake::IntakeRun, Robot.ballIntake),

            // Turn towards human player station
            new TurnDegreesBetter(180, 150),

            // Stop the ball intake
            new InstantCommand(Robot.ballIntake::IntakeStop, Robot.ballIntake),

            // Drive to throw spot for the 2 balls 
            new DriveDistance(96, 250),

            // Throw the ball
            new ThrowerWork(RobotMap.tarmacThrow, 0, true, false)
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
        Robot.ballThrower.throwerRPM(RobotMap.idleThrow);
    }  
    
}
