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
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**********************************************************************************
 **********************************************************************************/

public class AutoTwoBallLeftNew extends SequentialCommandGroup {
    public AutoTwoBallLeftNew() {

        /**********************************************************************************
         **********************************************************************************/

        addCommands(
            new ParallelCommandGroup(
                // Spin up the thrower
                new ThrowerWork(RobotMap.tarmacThrow, 100, false, false),

                // Shift the Transmission to Low
                new InstantCommand(Robot.driveBase::shiftDown, Robot.driveBase),

                // Extend the Intake
                new InstantCommand(Robot.ballIntake::ExtendIntake, Robot.ballIntake),

                //Backup to throw the ball
                new DriveDistance(-24,100)
            ),    

            new ParallelCommandGroup(
                // Turn by degrees
                new TurnDegreesBetter(15, 50),

                // Spin up the thrower
                new ThrowerWork(RobotMap.tarmacThrow, 50, false, false)
            ),

            new ThrowerWork(RobotMap.tarmacThrow, 0, true, false),

            // Stop the thrower intake
            new InstantCommand(Robot.ballThrower::ThrowerIntakeStop, Robot.ballThrower),

            // Start the ball intake
            new InstantCommand(Robot.ballIntake::IntakeRun, Robot.ballIntake),
                
            // Turn by degrees
            new TurnDegreesBetter(115, 200),

            // Drive to the Ball
            new DriveDistance(36, 150),

            // Turn by degrees
            new TurnDegreesBetter(-155, 200),

            // Stop the intake
            new InstantCommand(Robot.ballIntake::IntakeStop, Robot.ballIntake),

            // Drive forward to the target
            new DriveDistance(24, 130),

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

