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

public class ClimberControl extends CommandBase {
    static int count=0;
	static int delay=0;
    static boolean intakeExtended=false;
	static int intakeRPM=0;

    public ClimberControl(VerticalClimber subsystem) {
		addRequirements(subsystem);
    }

	@Override
	public void initialize() {
	}    

	// Called every tick (20ms)
	@Override
	public void execute() {
		if (Robot.isAutonomous) {
			// Ignore user controls during Autonomous
			return;
		}

		// Get stick inputs
		JoystickWrapper driveJoystick = new JoystickWrapper(Robot.oi.driveController, 0.05);
		//JoystickWrapper operatorJoystick = new JoystickWrapper(Robot.oi.operatorController, 0.05);

		count++;

		//////////////////////////////////////////////////////////////
		// Climber Controls

        if (driveJoystick.isAButton()) {
            // Extend the Climber
		    Robot.verticalClimber.RaiseClimber();
        }  else if (driveJoystick.isBButton()) {
            // Retact the Climber
		    Robot.verticalClimber.LowerClimber();
        } else {
		    Robot.verticalClimber.StopClimber();
		}

		delay--;	
	}

	// Returns true if command finished
	@Override
	public boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
    @Override
	public void end(boolean isInterrupted) {
	}  
}
