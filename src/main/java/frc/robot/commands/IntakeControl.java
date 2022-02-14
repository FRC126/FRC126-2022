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
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeControl extends CommandBase {
    static int count=0;
	static int delay=0;
    static boolean intakeExtended=false;
	static int intakeRPM=0;

    public IntakeControl(BallIntake subsystem) {
		addRequirements(subsystem);
    }

	@Override
	public void initialize() {
		Log.print(0, "OI", "Operator control initialized.");
	}    

	// Called every tick (20ms)
	@Override
	public void execute() {
		// Get stick inputs
		//JoystickWrapper driveJoystick = new JoystickWrapper(Robot.oi.driveController, 0.05);
		JoystickWrapper operatorJoystick = new JoystickWrapper(Robot.oi.operatorController, 0.05);

		count++;

		//////////////////////////////////////////////////////////////
		// Intake Controls

		if(operatorJoystick.isLShoulderButton()) {
            if (delay <= 0 && !intakeExtended) {
                // Extend Ball Intake
                Robot.ballIntake.MoveIntake(true);
                intakeExtended=true;
                delay=100;
            }    
        }    
					
		if (operatorJoystick.isRShoulderButton()) {
            if (delay <= 0 && intakeExtended) {
                // Retract Ball Intake
                Robot.ballIntake.MoveIntake(false);
                intakeExtended=false;
                delay=100;
            }    
        }
        
		double foo = operatorJoystick.getRightStickY();
		if (foo > .1 || foo < -.1) {
            intakeRPM = (int)(foo * 20000);
		}
		
        if (operatorJoystick.isAButton()) {
            // Run Ball Intake
		    intakeRPM=18000;
        } 

		if (operatorJoystick.isBButton()) {
            // Run Ball Intake
		    intakeRPM=5000;
        } 

		if (operatorJoystick.isXButton()) {
            // Run Ball Intake
		    intakeRPM=10000;
        } 

		if (operatorJoystick.isYButton()) {
            // Run Ball Intake
		    intakeRPM=0;
        } 

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
