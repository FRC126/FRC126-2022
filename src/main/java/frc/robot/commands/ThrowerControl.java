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
//import edu.wpi.first.wpilibj.Solenoid;
//import com.ctre.phoenix.motorcontrol.ControlMode;
//import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.util.Color;

public class ThrowerControl extends CommandBase {
    static int count;
	static double speed;
	static int delay=0;
	static int throwCount=0;

    public ThrowerControl(BallThrower subsystem) {
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
		//JoystickWrapper operatorJoystick = new JoystickWrapper(Robot.oi.operatorController, 0.05);

		//Log.print(0, "Robot", "operator control");

		count++;

		//////////////////////////////////////////////////////////////
		// Thrower Controls

		if(driveJoystick.isXButton()) {
			Robot.ballThrower.ThrowerSpeed(0.90);
        }    
					
		if (driveJoystick.isYButton()) {
			speed=0;
			Robot.ballThrower.ThrowerSpeed(speed);
        }

        if (driveJoystick.isAButton()) {
			if ( delay <= 0 ) {
				speed += 0.05;
				if (speed > 1.0) { speed = 1; }
				delay=15;
				Robot.ballThrower.ThrowerSpeed(speed);
			} 
        } 
		
		if (driveJoystick.isBButton()) {
			if ( delay <= 0 ) {
				speed -= 0.05;
				if (speed < 0) { speed = 0; }
				delay=15;
				Robot.ballThrower.ThrowerSpeed(speed);
			}
		}

		int rpm = (int)Math.abs(Robot.throw1.getSelectedSensorVelocity());

		if (Robot.shootNow) {
             if (Robot.ThrowerRPM == 9000) { speed = 0.65;}
             if (Robot.ThrowerRPM == 6000) { speed = 0.45;}
             if (Robot.ThrowerRPM == 3000) { speed = 0.25;}
             if (Robot.ThrowerRPM == 0) { speed = 0;}

			 if ( rpm > Robot.ThrowerRPM) { 
				 throwCount++;
			 }

			 if (throwCount > 50) {
				 Robot.sparkMax1.set(.25);
			 }
            
			 Robot.ballThrower.ThrowerSpeed(speed);
		} else {
			if (Robot.targetType == Robot.targetTypes.TargetSeek) {
				speed=0;
				Robot.ballThrower.ThrowerSpeed(speed);
			}
			throwCount=0;
			Robot.sparkMax1.set(0);
		}

        if (driveJoystick.isRShoulderButton() ) {
		}
 
		// Turn on the NEO based on the position of the joystick
        //Robot.sparkMax1.set(Y);

		// Log the thrower motor percentage to the Smart Dashboard 
		SmartDashboard.putNumber("Motor Percentage",speed*100);
		SmartDashboard.putNumber("Motor RPM",rpm/100);
		SmartDashboard.putNumber("Motor RPM 2",rpm);
		SmartDashboard.putNumber("Throw Count",throwCount);

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
