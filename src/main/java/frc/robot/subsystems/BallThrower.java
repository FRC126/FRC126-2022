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

package frc.robot.subsystems;

import frc.robot.Robot;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class BallThrower extends SubsystemBase {

	/************************************************************************
	 ************************************************************************/

     public BallThrower() {
        // Register this subsystem with command scheduler and set the default command
        CommandScheduler.getInstance().registerSubsystem(this);
        setDefaultCommand(new ThrowerControl(this));
    }

	/************************************************************************
	 ************************************************************************/
    
    public void periodic() {}

	/************************************************************************
     * Run Main Thower Wheels
	 ************************************************************************/

    public void ThrowerSpeed(double speed) {
        // Robot.throw1.set(ControlMode.PercentOutput,speed);
        // Robot.throw2.set(ControlMode.PercentOutput,speed * -1);
    }   

  	/************************************************************************
    * Run Thrower Intake Wheels
	 ************************************************************************/

    public void ThowerIntake(double speed) {
        // TODO Define Thrower Motors  
    }
}
