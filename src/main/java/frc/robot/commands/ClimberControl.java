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

import frc.robot.JoystickWrapper;
import frc.robot.Robot;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**********************************************************************************
 **********************************************************************************/

public class ClimberControl extends CommandBase {
    static boolean intakeExtended=false;
	JoystickWrapper driveJoystick;
	SequentialCommandGroup autoClimb;

	/**********************************************************************************
	 **********************************************************************************/
	
    public ClimberControl(VerticalClimber subsystem) {
		addRequirements(subsystem);
		driveJoystick = new JoystickWrapper(Robot.oi.driveController, 0.05);
    }

	/**********************************************************************************
	 **********************************************************************************/
	
	@Override
	public void initialize() {
	}    

	/**********************************************************************************
	 * Called every tick (20ms)
	 **********************************************************************************/

	 @Override
	public void execute() {
		if (Robot.internalData.isAuto()) {
			// Ignore user controls during Autonomous
			return;
		}

		if (Robot.autoClimbRunning) {
			return;
		}

		//////////////////////////////////////////////////////////////
		// Climber Controls

		Robot.verticalClimber.getRightPos();
		Robot.verticalClimber.getLeftPos();
		
        if (driveJoystick.isAButton()) {
            // Extend the Climber while the A Button is pressed
		    Robot.verticalClimber.RaiseClimber();
        }  else if (driveJoystick.isYButton()) {
            // Retact the Climber while the Y Button is pressed
		    Robot.verticalClimber.LowerClimberNoLimit();
        }  else if (driveJoystick.isBButton()) {
            // Retact the Climber while the B Button is pressed
		    Robot.verticalClimber.LowerClimber();
        } else if (driveJoystick.getPovLeft()) {
			// Lower just the left climber, don't stop at 0
		    Robot.verticalClimber.LowerLeftClimber(false);
        } else if (driveJoystick.getPovRight()) {
			// Lower just the right climber, don't stop at 0
		    Robot.verticalClimber.LowerRightClimber(false);
        } else if (driveJoystick.isLShoulderButton()) {
		    Robot.verticalClimber.StopClimber();
		    Robot.verticalClimber.ExtendArms();
        } else if (driveJoystick.isRShoulderButton()) {
		    Robot.verticalClimber.StopClimber();
		    Robot.verticalClimber.RetractArms();
		} else {
			// If none of the climber buttons are pressed, stop moving the climber
		    Robot.verticalClimber.StopClimber();
		}

		//if(driveJoystick.isBackButton() && !Robot.autoClimbRunning) {

		// 	autoClimb = new AutoClimb();
		// 	autoClimb.schedule();
        //     Robot.autoClimbRunning=true;
		// }

		if(driveJoystick.isStartButton()) {
			// Reset the climber encoders
			Robot.verticalClimber.ResetClimberEncoder();
		}
	}

	/**********************************************************************************
     * Returns true if command finished
	 **********************************************************************************/
	
	@Override
	public boolean isFinished() {
		return false;
	}

	/**********************************************************************************
	 * Called once after isFinished returns true
	 **********************************************************************************/
	
    @Override
	public void end(boolean isInterrupted) {
	}  
}
