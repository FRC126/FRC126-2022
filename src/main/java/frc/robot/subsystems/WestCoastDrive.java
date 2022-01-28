package frc.robot.subsystems;

import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import com.ctre.phoenix.motorcontrol.ControlMode;

import java.util.Arrays;

public class WestCoastDrive extends SubsystemBase {

	double leftMultiplier, rightMultiplier, leftSpeed, rightSpeed, fbSlowDown, rotSlowDown, limiter, left1RPM, left2RPM, right1RPM, right2RPM;
	double previousLimiter = 1;

	public WestCoastDrive() {
		CommandScheduler.getInstance().registerSubsystem(this);
		setDefaultCommand(new DriverControl(this));
		leftSpeed = 0;
		rightSpeed = 0;
	}

	public void periodic() {
    }

	public double getMeanRPM() {
		left1RPM = Math.abs(Robot.leftDriveMotor1.getSelectedSensorVelocity() / 3.41);
		left2RPM = Math.abs(Robot.leftDriveMotor2.getSelectedSensorVelocity() / 3.41);
		right1RPM = Math.abs(Robot.rightDriveMotor1.getSelectedSensorVelocity() / 3.41);
		right2RPM = Math.abs(Robot.rightDriveMotor2.getSelectedSensorVelocity() / 3.41);
		return((left1RPM + left2RPM + right1RPM + right2RPM) / 4);
	}
	public double getStallRPM() {
		double[] stallrpms = {0,0,0,0};
		left1RPM = Math.abs(Robot.leftDriveMotor1.getSelectedSensorVelocity() / 3.41);
		left2RPM = Math.abs(Robot.leftDriveMotor2.getSelectedSensorVelocity() / 3.41);
		right1RPM = Math.abs(Robot.rightDriveMotor1.getSelectedSensorVelocity() / 3.41);
		right2RPM = Math.abs(Robot.rightDriveMotor2.getSelectedSensorVelocity() / 3.41);
		stallrpms[0] = left1RPM;
		stallrpms[1] = left2RPM;
		stallrpms[2] = right1RPM;
		stallrpms[3] = right2RPM;
		Arrays.sort(stallrpms);
		return(stallrpms[0]);
	}
	public double getPeakRPM() {
		double[] peakrpms = {0,0,0,0};
		left1RPM = Math.abs(Robot.leftDriveMotor1.getSelectedSensorVelocity() / 3.41);
		left2RPM = Math.abs(Robot.leftDriveMotor2.getSelectedSensorVelocity() / 3.41);
		right1RPM = Math.abs(Robot.rightDriveMotor1.getSelectedSensorVelocity() / 3.41);
		right2RPM = Math.abs(Robot.rightDriveMotor2.getSelectedSensorVelocity() / 3.41);
		peakrpms[0] = left1RPM;
		peakrpms[1] = left2RPM;
		peakrpms[2] = right1RPM;
		peakrpms[3] = right2RPM;
		Arrays.sort(peakrpms);
		return(peakrpms[peakrpms.length - 1]);
	}

	public void Drive(double fb, double rot) { // Send power to the drive motors
		leftMultiplier = fb + (rot);
		rightMultiplier = fb - (rot);
		leftSpeed = leftMultiplier;
		rightSpeed = rightMultiplier;

		limiter = 1 + (1 * (Robot.internalData.getVoltage() - Robot.voltageThreshold));
		if(limiter < 0) {
			limiter = 0;
		} else if(limiter > 1) {
			limiter = 1;
		}
		previousLimiter = (4 * previousLimiter + limiter) / 5;
		if(Robot.internalData.getVoltage() < Robot.voltageThreshold) {
			leftSpeed *= previousLimiter;
			rightSpeed *= previousLimiter;
		}
		Robot.leftDriveMotor1.set(ControlMode.PercentOutput, leftSpeed * RobotMap.left1Inversion);
		Robot.leftDriveMotor2.set(ControlMode.PercentOutput, leftSpeed * RobotMap.left2Inversion);

        Robot.rightDriveMotor1.set(ControlMode.PercentOutput, rightSpeed * RobotMap.right1Inversion);
		Robot.rightDriveMotor2.set(ControlMode.PercentOutput, rightSpeed * RobotMap.right2Inversion);
	}
}
