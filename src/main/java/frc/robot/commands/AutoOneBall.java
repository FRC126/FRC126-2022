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

public class AutoOneBall extends SequentialCommandGroup {
    public AutoOneBall() {
        // TODO Target RPM for throw after picking up second ball
        int throwRPM=14000;

    /**********************************************************************************
     **********************************************************************************/

     addCommands(
            // Shift the Transmission to Low
            new InstantCommand(Robot.driveBase::shiftDown, Robot.driveBase),

             // Spin up the thrower
            new ThrowerWork(throwRPM, 0),

            new ParallelCommandGroup(
                // TODO ?? How long to throw both balls.
                new ThrowerWork(throwRPM, 250),
                // Run Feeder Motor
                new InstantCommand(Robot.ballThrower::ThrowerIntakeRun, Robot.ballThrower)
            ),

            new InstantCommand(Robot.ballThrower::ThrowerIntakeStop, Robot.ballThrower),
            new ThrowerWork(0, 0),

            // Backup past the line
            new DriveDistance(-0.3, 0, 36, 250),
            // Backup past the line
            // new DriveWork(-0.3, 0, 150),

            // put the transmission in high gear
            new InstantCommand(Robot.driveBase::shiftUp, Robot.driveBase),

            // Turn 180 degrees, TODO how long to turn and what speed
            //new DriveWork(0,.3,200),
            // Turn by degrees
            new TurnDegrees(0.3, 175, 250)
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
