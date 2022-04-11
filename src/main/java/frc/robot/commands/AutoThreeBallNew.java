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
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Robot;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj2.command.WaitCommand;

/**********************************************************************************
 **********************************************************************************/

public class AutoThreeBallNew extends SequentialCommandGroup {
    public AutoThreeBallNew() {

        addCommands(          
            new InstantCommand(Robot.ballIntake::ExtendIntake, Robot.ballIntake),

            new WaitCommand(.25),

            new InstantCommand(Robot.ballIntake::IntakeRunOne, Robot.ballIntake),

            new ParallelCommandGroup(
                new ThrowerWork(RobotMap.tarmacThrow, 0, false, false),
                new DriveDistance(41,250)
            ),

            //new InstantCommand(Robot.ballIntake::IntakeStop, Robot.ballIntake),

            // Start the ball intake
            new ParallelCommandGroup(
                new ThrowerWork(RobotMap.tarmacThrow, 0, false, false),
                new DriveDistance(-15,250)
            ),

            new ParallelCommandGroup(
                new TurnDegreesBetter(167, 250)
            ),
            // Throw the ball
            new ThrowerWork(RobotMap.tarmacThrow, 0, true, false),
        
            // Turn to pickup the Third ball
            new TurnDegreesBetter(-88, 200),

            new InstantCommand(Robot.ballIntake::IntakeRun, Robot.ballIntake),

            // Drive to pickup the thrid ball 
            new DriveDistance(80, 250),

            // Turn to aim
            new TurnDegreesBetter(120, 100),

            new ParallelCommandGroup(

                //Stop the ball intake
                new InstantCommand(Robot.ballIntake::IntakeStop, Robot.ballIntake),

                new DriveDistance(14
                , 150),

                new ThrowerWork(RobotMap.tarmacThrow, 0, false, false)
            ),

            // Stop the trower Intake
            new InstantCommand(Robot.ballThrower::ThrowerIntakeStop, Robot.ballThrower),           

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

