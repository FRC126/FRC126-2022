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
import frc.robot.RobotMap;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
            throwerRPM=RobotMap.longThrow;
		} else if (operatorJoystick.getPovUp()) {
            throwerRPM=RobotMap.safteyThrow;
		} else if (operatorJoystick.getPovLeft()) {
            throwerRPM=RobotMap.tarmacThrow-1000;
		} else if (operatorJoystick.getPovRight()) {
            throwerRPM=RobotMap.tarmacThrow+1000;
	    } else if (operatorJoystick.getLeftStickY() < -.3) {
			throwerRPM=RobotMap.shortThrow;
	    } else if (operatorJoystick.getLeftStickY() > .3) {
			throwerRPM=RobotMap.tarmacThrow;
		} else {
			if ( autoThrow == true ) {
				// IF autoThrow was true, cancel it.
				autoThrow=false;
				Robot.throwerRunning=false;
				Robot.ballThrower.ThrowerIntakeStop();
			}

			if (idleMotor) {
				// idle the throwing wheels, so spin up time is less.
			    throwerRPM=RobotMap.idleThrow;
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
		    throwerRPM=RobotMap.idleThrow;
			idleMotor=true;
        } 

        SmartDashboard.putBoolean("AutoThrow", autoThrow);

		// Call throwerRPM to set the target RPM, and auto throw if called for.
		if (autoThrow) {
		    Robot.ballThrower.autoThrowerRPM(throwerRPM);
		} else {
		    Robot.ballThrower.throwerRPM(throwerRPM);
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
