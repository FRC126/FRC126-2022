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

public class AutoOneBallThrow extends SequentialCommandGroup {
    public AutoOneBallThrow() {

        /**********************************************************************************
         **********************************************************************************/

        addCommands(
            // Shift the Transmission to Low
            new InstantCommand(Robot.driveBase::shiftDown, Robot.driveBase),

            // Extend the Intake
            new InstantCommand(Robot.ballIntake::ExtendIntake, Robot.ballIntake),

            new ParallelCommandGroup(
                // Spin up the thrower
                new ThrowerWork(RobotMap.tarmacThrow, 0, false, false),

                new SequentialCommandGroup(
                    //Backup to throw the ball
                    new DriveDistance(RobotMap.FirstBallBackup,100),

                    // Turn towards the target
                    new TurnDegreesBetter(RobotMap.FirstBallTurn, 50)
                )    
            ),

            // Throw the ball
            new ThrowerWork(RobotMap.tarmacThrow, 0, true, false),
            
            // Stop the Thrower Intake
            new InstantCommand(Robot.ballThrower::ThrowerIntakeStop, Robot.ballThrower)
        );
    }       

    /******************************************************************************************
     * Called once after isFinished returns true
     ******************************************************************************************/

     @Override
    public void end(boolean isInterrupted) {
        Robot.driveBase.Drive(0,0);
    }     
}
