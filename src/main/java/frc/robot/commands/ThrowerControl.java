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
<<<<<<< Updated upstream
=======
	static int throwCount=0;
    static int throwerRPM=0;
>>>>>>> Stashed changes

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
		//JoystickWrapper driveJoystick = new JoystickWrapper(Robot.oi.driveController, 0.05);
		JoystickWrapper operatorJoystick = new JoystickWrapper(Robot.oi.operatorController, 0.05);

		count++;
 
        if (operatorJoystick.isAButton()) {
            // Run Ball Intake
		    if (delay <= 0) {
				throwerRPM+=500;
				delay=5;
			}	
        } 

		if (operatorJoystick.isBButton()) {
            // Run Ball Intake
		    if (delay <= 0) {
				throwerRPM-=500;
				delay=5;
			}	
        } 
<<<<<<< Updated upstream
		
		if (driveJoystick.isBButton()) {
			if ( delay <= 0 ) {
				speed -= 0.05;
				if (speed < 0) { speed = 0; }
				delay=15;
				Robot.ballThrower.ThrowerSpeed(speed);
			}
		}

        if (driveJoystick.isRShoulderButton() ) {
		}
 
		// Turn on the NEO based on the position of the joystick
        //Robot.sparkMax1.set(Y);

		// Log the thrower motor percentage to the Smart Dashboard 
		SmartDashboard.putNumber("Motor Percentage",speed*100);
		int rpm = (int)Math.abs(Robot.throw1.getSelectedSensorVelocity());
		SmartDashboard.putNumber("Motor RPM",rpm/100);
		SmartDashboard.putNumber("Motor RPM 2",rpm);
=======

		if (operatorJoystick.isYButton()) {
            // Run Ball Intake
		    throwerRPM=0;
        } 

		if (throwerRPM > 20000) { throwerRPM = 20000; }
		if (throwerRPM < 0) { throwerRPM = 0; }

		Robot.ballThrower.throwerRPM(throwerRPM);

		SmartDashboard.putNumber("Thrower RPM",throwerRPM);
>>>>>>> Stashed changes

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
