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

/**********************************************************************************
 **********************************************************************************/

public class ThrowerControl extends CommandBase {
	static double speed;
	static int delay=0;
	static int throwCount=0;
    static int throwerRPM=0;
	static boolean autoThrow=false;
	static boolean idleMotor=true;
	JoystickWrapper operatorJoystick;

	static int shortThrow=7500;
	static int tarmacThrow=14000;
	static int longThrow=18000;
	static int safteyThrow=20750;
	static int idleThrow=4000;

	/**********************************************************************************
	 **********************************************************************************/
	
    public ThrowerControl(BallThrower subsystem) {
		addRequirements(subsystem);
		operatorJoystick = new JoystickWrapper(Robot.oi.operatorController, 0.05);
	}

	/**********************************************************************************
	 **********************************************************************************/
	
	public void initialize() {
	}    

	/**********************************************************************************
	 * Called every tick (20ms)
	 **********************************************************************************/
	
	@SuppressWarnings("static-access")
	@Override
	public void execute() {
		if (Robot.internalData.isAuto() || Robot.isThrowCommand ) {
			// Ignore user controls during Autonomous
			return;
		}

		double originalRPM = throwerRPM;

		if (operatorJoystick.isXButton()) {
			if ( throwerRPM > 0 ) {
				// Set autoThrow if the A button is pressed and the target RPM > 0
				autoThrow=true;
			}
		} else {
			if ( autoThrow == true ) {
				// IF autoThrow was true, cancel it.
				autoThrow=false;
				Robot.throwerRunning=false;
				Robot.ballThrower.ThrowerIntakeStop();
			}
			autoThrow=false;
		}

		// Use the POV pad to set 4 different throwing distances.  Button needs to be
		// held down for length of throwing action
		if (operatorJoystick.getPovDown()) {
            throwerRPM=longThrow;
		} else if (operatorJoystick.getPovUp()) {
            throwerRPM=safteyThrow;
		} else if (operatorJoystick.getPovLeft()) {
            throwerRPM=tarmacThrow-1000;
		} else if (operatorJoystick.getPovRight()) {
            throwerRPM=tarmacThrow+1000;
	    } else if (operatorJoystick.getLeftStickY() < -.3) {
			throwerRPM=tarmacThrow;
	    } else if (operatorJoystick.getLeftStickY() > .3) {
			throwerRPM=shortThrow;
		} else {
			if ( autoThrow == true ) {
				// IF autoThrow was true, cancel it.
				autoThrow=false;
				Robot.throwerRunning=false;
				Robot.ballThrower.ThrowerIntakeStop();
			}

			if (idleMotor) {
				// idle the throwing wheels, so spin up time is less.
			    throwerRPM=idleThrow;
			} else {
				// operator has cancelled the idling
				throwerRPM=0;
			}
		}	

		if (throwerRPM != originalRPM) {
			// Reset the target reached count if the target rpm changed.
			Robot.ballThrower.resetReachedCount();
		}

		if (operatorJoystick.isAButton()) {
   		    // Stop idling the throwing wheels
		    throwerRPM=0;
			idleMotor=false;
        } 

		if (operatorJoystick.isBButton()) {
            // Idle the throwing wheels
		    throwerRPM=idleThrow;
			idleMotor=true;
        } 

		// Call throwerRPM to set the target RPM, and auto throw if called for.
		if (autoThrow) {
		    Robot.ballThrower.throwerRPM(throwerRPM);
		} else {
		    Robot.ballThrower.autoThrowerRPM(throwerRPM);
		}			

		delay--;	
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
