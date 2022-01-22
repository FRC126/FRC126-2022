package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;

public class InternalData extends SubsystemBase {
	static ADXRS450_Gyro gyro;
	public void initDefaultCommand() {
	}

	// Match stats
	public boolean isAuto() { // Is the robot in auto mode?
		return  RobotState.isAutonomous();
	}
	public boolean isTeleop() { // Or is it in teleop mode?
		return  RobotState.isTeleop();
	}
	public boolean isEnabled() { // Or is it just enabled at all?
		return  RobotState.isEnabled();
	}
	public double getMatchTime() { // Get the time left in the match
		return  Timer.getMatchTime();
	}
	public double getVoltage() { // Get battery voltage
		return RobotController.getBatteryVoltage();
	}
	public void initGyro() {
		if(gyro == null) {
			try {
				gyro = new ADXRS450_Gyro();
			} catch(Exception e) {
				gyro = null;
			}
		}
	}
	public void resetGyro() {
		if(gyro != null) {
			gyro.reset();
		}
	}
	public double getGyroAngle() {
		if(gyro != null) {
			return gyro.getAngle();
		} else {
			return 0;
		}
	}

}
