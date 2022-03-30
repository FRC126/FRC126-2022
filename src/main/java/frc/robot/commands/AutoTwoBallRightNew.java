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

public class AutoTwoBallRightNew extends SequentialCommandGroup {
    public AutoTwoBallRightNew() {

        addCommands(
            /////////////////////////////////////////////////////////////////////////
            // Throw the first ball
            /////////////////////////////////////////////////////////////////////////

            new AutoOneBallThrow(),

            /////////////////////////////////////////////////////////////////////////
            // Pickup the second ball
            /////////////////////////////////////////////////////////////////////////

            // Turn by degrees
            new TurnDegreesBetter(-150, 150),

            // Start Running the Intake
            new InstantCommand(Robot.ballIntake::IntakeRun, Robot.ballIntake),

            // Drive to the Ball
            new DriveDistance(36,150),

            /////////////////////////////////////////////////////////////////////////
            // Throw the Second Ball
            /////////////////////////////////////////////////////////////////////////

            // Turn by degrees
            new TurnDegreesBetter(90, 150),

            new InstantCommand(Robot.ballIntake::IntakeStop, Robot.ballIntake),

            // Drive forward to the target
            new DriveDistance(24, 100),

            // Throw the ball
            new ThrowerWork(RobotMap.tarmacThrow, 0, true, false),

            // Stop the trower
            new InstantCommand(Robot.ballThrower::ThrowerIntakeStop, Robot.ballThrower),
            new ThrowerWork(RobotMap.idleThrow, 50, false, false)
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
