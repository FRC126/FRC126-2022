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
import frc.robot.Log;
import frc.robot.Robot;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeControl extends CommandBase {
    static int count=0;
	static int delay=0;
    static boolean intakeExtended=false;
	static int intakeRPM=0;

    public IntakeControl(BallIntake subsystem) {
		addRequirements(subsystem);
    }

	@Override
	public void initialize() {
		Log.print(0, "OI", "Operator control initialized.");
	}    

	// Called every tick (20ms)
	@Override
	public void execute() {
		// Get stick inputs
		//JoystickWrapper driveJoystick = new JoystickWrapper(Robot.oi.driveController, 0.05);
		JoystickWrapper operatorJoystick = new JoystickWrapper(Robot.oi.operatorController, 0.10);

		count++;

		//////////////////////////////////////////////////////////////
		// Intake Controls

		if(operatorJoystick.isLShoulderButton()) {
            if (delay <= 0 && !intakeExtended) {
                // Extend Ball Intake
                Robot.ballIntake.ExtendIntake();
                intakeExtended=true;
                delay=100;
            }    
        }    
					
		if (operatorJoystick.isRShoulderButton()) {
            if (delay <= 0 && intakeExtended) {
                // Retract Ball Intake
                Robot.ballIntake.RetractIntake();
                intakeExtended=false;
                delay=100;
            }    
        }
        
		double foo = operatorJoystick.getRightStickY();
		if (foo > .2) {
			Robot.ballIntake.IntakeRun();
		} else if ( foo < -.2) {
			Robot.ballIntake.IntakeReverse();
		} else {
			Robot.ballIntake.IntakeStop();
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
