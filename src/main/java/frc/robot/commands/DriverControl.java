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

import frc.robot.Robot;
import frc.robot.subsystems.*;	
import frc.robot.JoystickWrapper;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**********************************************************************************
 **********************************************************************************/

public class DriverControl extends CommandBase {
	static int delay=0;
	static boolean brakeMode=false;
	JoystickWrapper driveJoystick;
	
	/**********************************************************************************
	 **********************************************************************************/
	
    public DriverControl(WestCoastDrive subsystem) {
		addRequirements(subsystem);
		driveJoystick = new JoystickWrapper(Robot.oi.driveController, 0.1);
    }

	/**********************************************************************************
	 **********************************************************************************/
	
	@Override
	public void initialize() {
	}    

	/**********************************************************************************
	 * Called every tick (20ms)
	 **********************************************************************************/
	
	@SuppressWarnings("static-access")
	@Override
	public void execute() {
		if (Robot.internalData.isAuto()) {
			// Ignore user controls during Autonomous
			return;
		}

		// Get stick inputs
        double FB = driveJoystick.getLeftStickY();
        double LR = driveJoystick.getRightStickX() * -1 ;

	    // Left trigger enables slow mode
		//if (driveJoystick.getLeftTrigger() > .25) {
		//	LR=LR*.7;
		//	FB=FB*.3;
		//}
		

		// Right trigger enable brake mode.
		//if (driveJoystick.getRightTrigger() > .25) {
		//	LR=0;
		//	FB=0;
		//	if ( !brakeMode ) {
		//	    Robot.driveBase.driveBrakeMode();
		//		brakeMode=true;
		//	}	
		//} else {
		//	if (brakeMode) {
		//		Robot.driveBase.driveCoastMode();
		//		brakeMode=false;
		//	}		
		//}

		if (driveJoystick.isLShoulderButton()) {
			// Shift Down Drive Train
		    if (delay <= 0 ) {
				Robot.driveBase.shiftDown();
				delay=1;
			}
			SmartDashboard.putBoolean("Shift Down",true);
		} else {
			SmartDashboard.putBoolean("Shift Down",false);
		}

		if (driveJoystick.isRShoulderButton()) {
			// Shift Up Drive Train
		    if (delay <= 0 ) {
				Robot.driveBase.shiftUp();
				delay=1;
			}	
			SmartDashboard.putBoolean("Shift Up",true);
		} else {
			SmartDashboard.putBoolean("Shift Up",false);
		}

		delay--;

		// Log the Joystick X,Y Axis to the SmartDashboard.
		//SmartDashboard.putNumber("JoyStick Y Axis",FB);
		//SmartDashboard.putNumber("JoyStick X Axis",LR);
		SmartDashboard.putNumber("robotTurn",Robot.robotTurn);
		SmartDashboard.putNumber("robotDrive",Robot.robotDrive);

		if (Robot.targetType == Robot.targetTypes.TargetSeek) {
			// If we are seeking the throwing target, ignore the driver input
			Robot.driveBase.Drive(Robot.robotDrive,Robot.robotTurn);
		} else {
			// Set drivebase speed based on user input
			Robot.driveBase.Drive(FB,LR);
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
