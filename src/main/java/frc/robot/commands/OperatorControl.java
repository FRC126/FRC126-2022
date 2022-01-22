package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.Log;
import edu.wpi.first.wpilibj.Solenoid;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

public class OperatorControl extends CommandBase {
    static int count;
	static int speed;

    public OperatorControl() {
    }

	@Override
	public void initialize() {
		Log.print(0, "OI", "Operator control initialized.");
	}    

	// Called every tick (20ms)
	@SuppressWarnings("static-access")
	@Override
	public void execute() {
		// START CONTROL SETUP
		// Get stick inputs
		JoystickWrapper driveJoystick = new JoystickWrapper(Robot.oi.driveController, 0.05);
		JoystickWrapper operatorJoystick = new JoystickWrapper(Robot.oi.operatorController, 0.05);
		// END CONTROLS SETUP

		count++;

		//Log.print(0, "Robot", "Motor Control");
		
		if(driveJoystick.isXButton()) {
			Robot.throw1.set(ControlMode.PercentOutput,0.90);
			Robot.throw2.set(ControlMode.PercentOutput,-0.90);
        }    
					
		if (driveJoystick.isYButton()) {
			Robot.throw1.set(ControlMode.PercentOutput,0);
			Robot.throw2.set(ControlMode.PercentOutput,0);
			speed=0;
        }

        if (driveJoystick.isAButton()) {
			Robot.throw1.set(ControlMode.PercentOutput,0.60);
			Robot.throw2.set(ControlMode.PercentOutput,-0.60);
			//speed += 0.05;
			//if (speed > 1.0) {
			//	speed = 1;
			//}
			//Robot.throw1.set(ControlMode.PercentOutput,speed);
			//Robot.throw2.set(ControlMode.PercentOutput,speed * -1);
        } 
		
		if (driveJoystick.isBButton()) {
			Robot.throw1.set(ControlMode.PercentOutput,0.65);
			Robot.throw2.set(ControlMode.PercentOutput,-0.65);
//			speed -= 0.05;
//			if (speed < 0) {
//				speed = 0;
//			}
//			Robot.throw1.set(ControlMode.PercentOutput,speed);
//			Robot.throw2.set(ControlMode.PercentOutput,speed * -1);
		}

		if (driveJoystick.isLShoulderButton() ) {
		}
		Log.print(0, "Robot", "Speed " + speed);


        if (driveJoystick.isRShoulderButton() ) {
		}

        double Y = driveJoystick.getLeftStickY();
        double X = driveJoystick.getRightStickX();

        if ( Y > -0.1 && Y < 0.1 ) {
            Y=0;     
        } else {
			Log.print(0, "Robot", "Motor Control " + Y);
		}

        if ( X > -0.2 && X < 0.2 ) {
            X=0;     
        }

		if(driveJoystick.isXButton()) {
			//Robot.sparkMax1.set(.1);
        } else {    
            //Robot.sparkMax1.set(Y);
		}	
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
