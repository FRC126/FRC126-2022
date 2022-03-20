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
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;
 
/**********************************************************************************
 **********************************************************************************/

public class AutoDriveDistance extends SequentialCommandGroup {
    public AutoDriveDistance() {

    /**********************************************************************************
     **********************************************************************************/

     addCommands(

            new DriveDistance(24,250),
            new WaitCommand(1), // do nothing for 1 second
            new TurnDegreesBetter(180,900),
            new WaitCommand(1), // do nothing for 1 second
            new DriveDistance(24,250),
            new WaitCommand(1), // do nothing for 1 second
            new TurnDegreesBetter(180,900),
            new WaitCommand(1), // do nothing for 1 second
            new DriveDistance(24,250),
            new WaitCommand(1), // do nothing for 1 second
            new DriveDistance(-24,250)
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

