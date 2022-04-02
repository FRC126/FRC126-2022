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

/**********************************************************************************
 **********************************************************************************/

public class AutoThreeBall extends SequentialCommandGroup {
    public AutoThreeBall() {

        addCommands(
           
            /////////////////////////////////////////////////////////////////////////
            // Throw the first Ball
            /////////////////////////////////////////////////////////////////////////

            new AutoOneBallThrow(1),

            /////////////////////////////////////////////////////////////////////////
            // Pickup the Second Ball
            /////////////////////////////////////////////////////////////////////////
            
            // Start the ball intake
            new InstantCommand(Robot.ballIntake::IntakeRun, Robot.ballIntake),
                
            // Turn by degrees to pickup the second ball
            new TurnDegreesBetter(-125, 200),

            // Drive to pickup the second ball
            new DriveDistance(26,250),

            /////////////////////////////////////////////////////////////////////////
            // Pickup the Third Ball
            /////////////////////////////////////////////////////////////////////////

            // backup after picking up the second ball
            new DriveDistance(-14,200),

            // Turn to pickup the Third ball
            new TurnDegreesBetter(56, 200),

            // Drive to pickup the thrid ball 
            new DriveDistance(84, 200),

            /////////////////////////////////////////////////////////////////////////
            // Throw the second and third balls
            /////////////////////////////////////////////////////////////////////////

            // Turn to aim
            new TurnDegreesBetter(120, 100),

            new ParallelCommandGroup(

                //Stop the ball intake
                new InstantCommand(Robot.ballIntake::IntakeStop, Robot.ballIntake),

                // Drive to throw spot for the 2 balls 
                new DriveDistance(15, 150),

                // Throw the ball
                new ThrowerWork(RobotMap.tarmacThrow, 0, true, false)
            ),

            // Stop the trower Intake
            new InstantCommand(Robot.ballThrower::ThrowerIntakeStop, Robot.ballThrower),           

            // Throw the ball
            new ThrowerWork(RobotMap.idleThrow, 0, true, false)

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

