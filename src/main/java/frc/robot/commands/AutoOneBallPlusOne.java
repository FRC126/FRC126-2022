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
 
/**********************************************************************************
 **********************************************************************************/

public class AutoOneBallPlusOne extends SequentialCommandGroup {
    public AutoOneBallPlusOne() {
        // TODO Target RPM for throw after picking up second ball
        int throwRPM=14000;

        /**********************************************************************************
         **********************************************************************************/

        addCommands(
            new ParallelCommandGroup(
                // Shift the Transmission to Low
                new InstantCommand(Robot.driveBase::shiftDown, Robot.driveBase),

                // Extend the Intake
                new InstantCommand(Robot.ballIntake::ExtendIntake, Robot.ballIntake),

                //Backup to throw the ball
                new DriveWork(-0.3, 0, 75)
            ),    

            // Throw the Ball
            new ThrowerWork(throwRPM, 0, true),
        
            // Stop the trower
            new InstantCommand(Robot.ballThrower::ThrowerIntakeStop, Robot.ballThrower),
            new ThrowerWork(0, 0, false),

            // Turn by degrees
            new TurnDegrees(-0.25, 155, 150),

            // Start Running the Intake
            new InstantCommand(Robot.ballIntake::IntakeRun, Robot.ballIntake),

            // Drive to the Ball
            new DriveWork(3, 0, 100),

            // Keep running intake for a little bit, will stop when done
            new IntakeWork(true, 50),

            // Turn by degrees
            new TurnDegrees(-0.25, 155, 150),

            // Drive forward to the target
            new DriveWork(3, 0, 100),

            // Throw the ball
            new ThrowerWork(throwRPM, 0, true),

            // Stop the trower
            new InstantCommand(Robot.ballThrower::ThrowerIntakeStop, Robot.ballThrower),
            new ThrowerWork(0, 0, false),

            // put the transmission in high gear
            new InstantCommand(Robot.driveBase::shiftUp, Robot.driveBase)
        );
    }       

    /******************************************************************************************
     * Called once after isFinished returns true
     ******************************************************************************************/

     @Override
    public void end(boolean isInterrupted) {
        Robot.ballIntake.IntakeStop();
        Robot.ballIntake.RetractIntake();
        Robot.driveBase.Drive(0,0);
        Robot.ballThrower.ThrowerIntakeStop();
        Robot.ballThrower.throwerRPM(0);
    }  
    
}
