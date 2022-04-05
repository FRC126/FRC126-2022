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
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;

/**********************************************************************************
 **********************************************************************************/

public class AutoClimb extends SequentialCommandGroup {
    public AutoClimb() {

        addCommands(

            /////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////

            new InstantCommand(Robot.verticalClimber::RetractArms, Robot.verticalClimber),

            new RaiseClimberArms(250),

            // Drive to the bar
            new DriveDistance(16, 250),
            
            new LowerClimberArms(250),
            
            //new WaitCommand(.2),

            new RaiseClimberArms(50),

            new WaitCommand(.5),

            new InstantCommand(Robot.verticalClimber::ExtendArms, Robot.verticalClimber),

            new RaiseClimberArms(250),

            new InstantCommand(Robot.verticalClimber::RetractArms, Robot.verticalClimber),

            new WaitCommand(.5),

            new LowerClimberArms(250)

            // new WaitCommand(2),

            // new RaiseClimberArms(40),

            // new InstantCommand(Robot.verticalClimber::ExtendArms, Robot.verticalClimber)

            );
    }       

    /******************************************************************************************
     * Called once after isFinished returns true
     ******************************************************************************************/

     @Override
    public void end(boolean isInterrupted) {
        Robot.autoClimbRunning=false;
        Robot.verticalClimber.StopClimber();
    }  
    
}

