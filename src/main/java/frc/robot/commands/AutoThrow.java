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
 
public class AutoThrow extends SequentialCommandGroup {
    public AutoThrow(int throwRPM) {
        addCommands(
            // Stop Running the Intake
            new InstantCommand(Robot.ballIntake::IntakeStop,Robot.ballIntake),

            // Retract the Intake
            new InstantCommand(Robot.ballIntake::RetractIntake,Robot.ballIntake),

            // Spin up the thrower
            new ThrowerWork(throwRPM,0),

            new ParallelCommandGroup(
                // TODO ?? How long to throw both balls.
                new ThrowerWork(throwRPM,250),
                // Run Feeder Motor
                new InstantCommand(Robot.ballThrower::ThrowerIntakeRun,Robot.ballThrower)
            ),

            new InstantCommand(Robot.ballThrower::ThrowerIntakeStop,Robot.ballThrower),
            new ThrowerWork(0,0)
        );
    }       

    /******************************************************************************************
     * Called once after isFinished returns true
     ******************************************************************************************/
    @Override
	public void end(boolean isInterrupted) {
        Robot.ballThrower.ThrowerIntakeStop();
        Robot.ballThrower.throwerRPM(0);
    }  
}