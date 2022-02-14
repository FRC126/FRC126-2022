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

import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Robot;
 
public class AutoTest extends SequentialCommandGroup {
    public AutoTest() {
        int throwRPM=12000;

        addCommands(
            // Shift the Transmission to Low
            new InstantCommand(Robot.driveBase::shiftDown,Robot.ballIntake),

            // Extend the Intake
            new InstantCommand(Robot.ballIntake::ExtendIntake,Robot.ballIntake),

            // Start Running the Intake
            new InstantCommand(Robot.ballIntake::IntakeRun,Robot.ballIntake),

            // Backup to the Ball
            new DriveWork(-0.3,0,100),

            // TODO Do we need to keep running intake for a little bit??

            // Stop the Intake
            new InstantCommand(Robot.ballIntake::IntakeStop,Robot.ballIntake),

            // Retract the Intake
            new InstantCommand(Robot.ballIntake::RetractIntake,Robot.ballIntake),

            // Turn 180 degrees
            new DriveWork(0,.3,100),

            // TODO Spin up the thrower
            // ? Target RPM
            new ThrowerWork(throwRPM,0),

            new ParallelCommandGroup(
                // ?? How long to throw both balls.
                new ThrowerWork(throwRPM,500),
                // TODO Run Feeder Motor
                new InstantCommand(Robot.ballThrower::ThowerIntakeRun,Robot.ballThrower)
            ),

            new InstantCommand(Robot.ballThrower::ThowerIntakeStop,Robot.ballThrower),
            new ThrowerWork(0,0)
        );
    }       
}



