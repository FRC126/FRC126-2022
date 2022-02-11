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
//import frc.robot.Log;
import frc.robot.Robot;
import frc.robot.subsystems.*;	

//import edu.wpi.first.wpilibj.Solenoid;
//import com.ctre.phoenix.motorcontrol.ControlMode;
//import edu.wpi.first.wpilibj.DoubleSolenoid;
//import edu.wpi.first.wpilibj.util.Color;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriverControl extends CommandBase {
    static int count;

    public DriverControl(WestCoastDrive subsystem) {
		addRequirements(subsystem);
    }

	@Override
	public void initialize() {
	}    

	// Called every tick (20ms)
	@SuppressWarnings("static-access")
	@Override
	public void execute() {
		// Get stick inputs
		JoystickWrapper driveJoystick = new JoystickWrapper(Robot.oi.driveController, 0.05);

		count++;
        double Y = driveJoystick.getLeftStickY();
        double X = driveJoystick.getRightStickX();

        Robot.driveBase.Drive(X,Y);

		//if(Robot.robotDrive > 0) {
		//	Robot.sparkMax1.set(.1);
		//	double turns = Robot.sparkMax1.getEncoder().getPosition();
		//	double rpms = Robot.sparkMax1.getEncoder().getVelocity();
		//	SmartDashboard.putNumber("Turns",turns);
		//	SmartDashboard.putNumber("RPMS",rpms);
		//} else {
		//	Robot.sparkMax1.set(0);
		//}

	    double sparkSpeed=0;
		SmartDashboard.putBoolean("isLShoulderButton",driveJoystick.isLShoulderButton());
		SmartDashboard.putBoolean("isRShoulderButton",driveJoystick.isRShoulderButton());
		if (driveJoystick.isLShoulderButton() ) {
			sparkSpeed=.1;
		} else if (driveJoystick.isRShoulderButton() ) {
			sparkSpeed=.2;
		} else {
			sparkSpeed=0;
		}

		//Robot.sparkMax1.set(sparkSpeed);

  		// Log the Joystick X,Y Axis to the SmartDashboard.
		SmartDashboard.putNumber("JoyStick Y Axis",Y);
		SmartDashboard.putNumber("JoyStick X Axis",X);

		SmartDashboard.putNumber("robotTurn",Robot.robotTurn);
		SmartDashboard.putNumber("robotDrive",Robot.robotDrive);
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
