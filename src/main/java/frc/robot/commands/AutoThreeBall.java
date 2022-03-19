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

public class AutoThreeBall extends SequentialCommandGroup {
    public AutoThreeBall() {

        /**********************************************************************************
         **********************************************************************************/

        addCommands(
            new ParallelCommandGroup(
                // Shift the Transmission to Low
                new InstantCommand(Robot.driveBase::shiftDown, Robot.driveBase),

                // Extend the Intake
                new InstantCommand(Robot.ballIntake::ExtendIntake, Robot.ballIntake),

                //Backup to throw the ball
                new DriveDistance(-24,100),

                new ThrowerWork(RobotMap.tarmacThrow, 100, false, false)
            ),

            // Turn to aim 
            new TurnDegreesBetter(15,50),
            
            // Throw the Ball
            new ThrowerWork(RobotMap.tarmacThrow, 0, true, false),
   
            // Stop the trower
            new InstantCommand(Robot.ballThrower::ThrowerIntakeStop, Robot.ballThrower),

            // Start the ball intake
            new InstantCommand(Robot.ballIntake::IntakeRun, Robot.ballIntake),
                
            // Turn by degrees to pickup the second ball
            new TurnDegreesBetter(165, 200),

            // Drive to pickup the second ball
            new DriveDistance(18,150),

            // backup after picking up the second ball
            new DriveDistance(-18,150),

            // Turn to pickup the Third ball
            new TurnDegreesBetter(120, 200),

            // Drive to pickup the thrid ball 
            new DriveDistance(72, 200),

            // Turn to aim
            new TurnDegreesBetter(70, 100),

            // Stop the ball intake
            new InstantCommand(Robot.ballIntake::IntakeStop, Robot.ballIntake),

            // Drive to pickup the thrid ball 
            new DriveDistance(24, 150),

            // Throw the ball
            new ThrowerWorkStop(RobotMap.tarmacThrow, 0, true),

            // Stop the trower
            new InstantCommand(Robot.ballThrower::ThrowerIntakeStop, Robot.ballThrower),
            new ThrowerWork(RobotMap.idleThrow, 0, false, false),

            // Turn towards human player station
            new TurnDegreesBetter(-150, 150)

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

