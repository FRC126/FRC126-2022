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

public class AutoTwoBallLeftNew extends SequentialCommandGroup {
    public AutoTwoBallLeftNew() {

        addCommands(

            /////////////////////////////////////////////////////////////////////////
            // Throw the first ball
            /////////////////////////////////////////////////////////////////////////

            new AutoOneBallThrow(1),

            /////////////////////////////////////////////////////////////////////////
            // Pickup the Second Ball
            /////////////////////////////////////////////////////////////////////////
            
            // Start the ball intake
            new InstantCommand(Robot.ballIntake::IntakeRun, Robot.ballIntake),
                
            // Turn by degrees
            new TurnDegreesBetter(115, 200),

            // Drive to the Ball
            new DriveDistance(24, 150),

            // Turn by degrees
            new TurnDegreesBetter(-133, 200),

            // Stop the intake
            new InstantCommand(Robot.ballIntake::IntakeStop, Robot.ballIntake),

            /////////////////////////////////////////////////////////////////////////
            // Throw the second ball
            /////////////////////////////////////////////////////////////////////////

            // Drive forward to the target
            new DriveDistance(18, 130),

            // Throw the ball
            new ThrowerWork(RobotMap.tarmacThrow, 0, true, false),

            // Idle the thrower
            new InstantCommand(Robot.ballThrower::ThrowerIntakeStop, Robot.ballThrower),
            new ThrowerWork(RobotMap.idleThrow, 0, false, false)
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

