package frc.robot.commands;

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
		Log.print(0, "OI", "Operator control initialized.");
	}    

	// Called every tick (20ms)
	@SuppressWarnings("static-access")
	@Override
	public void execute() {
		// Get stick inputs
		JoystickWrapper driveJoystick = new JoystickWrapper(Robot.oi.driveController, 0.05);

		count++;

		if (driveJoystick.isLShoulderButton() ) {
			// TODO Shift Up Drive Train
		}

        if (driveJoystick.isRShoulderButton() ) {
			// TODO Shift Down Drive Train
		}

        double Y = driveJoystick.getLeftStickY();
        double X = driveJoystick.getRightStickX();

        Robot.driveBase.Drive(X,Y);

  		// Log the Joystick X,Y Axis to the SmartDashboard.
		SmartDashboard.putNumber("JoyStick Y Axis",Y);
		SmartDashboard.putNumber("JoyStick X Axis",X);
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
