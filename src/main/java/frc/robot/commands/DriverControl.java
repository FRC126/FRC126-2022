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

public class DriverControl extends CommandBase {
    static int count;
	static int delay=0;
	static boolean turnAround;
	static double startAngle;
	
	/**********************************************************************************
	 **********************************************************************************/
	
    public DriverControl(WestCoastDrive subsystem) {
		addRequirements(subsystem);
    }

	/**********************************************************************************
	 **********************************************************************************/
	
	@Override
	public void initialize() {
	}    

	/**********************************************************************************
	 **********************************************************************************/
	
	// Called every tick (20ms)
	@SuppressWarnings("static-access")
	@Override
	public void execute() {
		if (Robot.internalData.isAuto()) {
			// Ignore user controls during Autonomous
			return;
		}

		// Get stick inputs
		JoystickWrapper driveJoystick = new JoystickWrapper(Robot.oi.driveController, 0.10);
		
		count++;

        double FB = driveJoystick.getLeftStickY();
        double LR = driveJoystick.getRightStickX();

		if (driveJoystick.isLShoulderButton()) {
			// Shift Down Drive Train
		    if (delay <= 0 ) {
				Robot.driveBase.shiftDown();
				delay=20;
			}	
		}

		if (driveJoystick.isRShoulderButton()) {
			// Shift Up Drive Train
		    if (delay <= 0 ) {
				Robot.driveBase.shiftUp();
				delay=20;
			}	
		}

		if (driveJoystick.isXButton()) {
			if (turnAround == false) {
				startAngle = Robot.internalData.getGyroAngle();
			}
		}

		if (turnAround == true) {
			double currAngle = Robot.internalData.getGyroAngle();
			if ( currAngle < startAngle + 175) {
				LR=0.3;
			} else {
				LR=0;
				turnAround=false;
			}
		}
		delay--;

		// Log the Joystick X,Y Axis to the SmartDashboard.
		SmartDashboard.putNumber("JoyStick Y Axis",FB);
		SmartDashboard.putNumber("JoyStick X Axis",LR);
		SmartDashboard.putNumber("robotTurn",Robot.robotTurn);
		SmartDashboard.putNumber("robotDrive",Robot.robotDrive);

		if (Robot.targetType == Robot.targetTypes.TargetSeek) {
			Robot.driveBase.Drive(Robot.robotDrive,Robot.robotTurn);
		} else {
			Robot.driveBase.Drive(FB,LR);
		}
	}

	/**********************************************************************************
	 **********************************************************************************/

	// Returns true if command finished
	@Override
	public boolean isFinished() {
		return false;
	}

	/**********************************************************************************
	 **********************************************************************************/
	
	// Called once after isFinished returns true
    @Override
	public void end(boolean isInterrupted) {
	}  
}
