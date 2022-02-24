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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class BallThrower extends SubsystemBase {
    static double targetRPM;
    static double throwerSpeed;
    static int delay;

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
     * Run Main Thower Wheels by target RPM
	 ************************************************************************/

    public boolean throwerRPM(int targetRPM) {
        boolean targetReached=false;

        int rpm = (int)Math.abs(Robot.throwerMotor2.getSelectedSensorVelocity());

        if (rpm < targetRPM-50) {
            // If we are below the rpm target
            if (delay <= 0 ) {
                if (rpm < targetRPM-500) {
                    // If we are more than 500 RPM away, change speed faster
                    if (rpm < targetRPM-1500) {
                        // If we are more than 1500 RPM away, change speed even faster
                        delay=1;
                    } else {
                        delay=2;
                    }
                    throwerSpeed = throwerSpeed + 0.01;   
                } else {
                    // if we less than 500 RPM awawy, change speed slower
                    delay=5;
                    throwerSpeed = throwerSpeed + 0.0025;
                }
                if (throwerSpeed > 1) { throwerSpeed = 1; }
            }
        } else if (rpm > targetRPM+50) {
            // If we are above the rpm target
            if (delay <= 0 ) {
                if (rpm > targetRPM+500) {
                    // If we are more than 500 RPM away, change speed faster
                    if (rpm > targetRPM+1500) {
                        // If we are more than 1500 RPM away, change speed even faster
                        delay=1;
                    } else {
                        delay=2;
                    }
                    throwerSpeed = throwerSpeed - 0.01;   
                } else {
                    // if we less than 500 RPM awawy, change speed slower
                    delay=5;
                    throwerSpeed = throwerSpeed - 0.0025;
                }
                if (throwerSpeed < 0) { throwerSpeed = 0; }
            }
        } else {
            targetReached=true;
        }

        if (targetRPM == 0) {
            // Short cut to stop the thrower motors
            throwerSpeed=0;
        }

        delay--;

        // Set the speed on the Thrower Motors
        Robot.throwerMotor1.set(ControlMode.PercentOutput,throwerSpeed * -1);
        Robot.throwerMotor2.set(ControlMode.PercentOutput,throwerSpeed);

        // Log info to the smart dashboard
		SmartDashboard.putNumber("Throw RPM Current",rpm);
        SmartDashboard.putNumber("Throw RPM Target",targetRPM);
        SmartDashboard.putBoolean("Throw RPM Reached",targetReached);

        return(targetReached);
    }
    
  	/************************************************************************
    * Run Thrower Intake Wheels
	 ************************************************************************/

    public void ThrowerIntake(double speed) {
        Robot.feederMotor.set(speed);
    }

   	/************************************************************************
	 ************************************************************************/

    public void ThrowerIntakeRun() {
        SmartDashboard.putBoolean("Thrower Intake Run",true);
        ThrowerIntake(-0.4);
        if (!Robot.intakeRunning) {
            Robot.intakeMotor2.set(0.4);
        }

    }

  	/************************************************************************
	 ************************************************************************/

     public void ThrowerIntakeStop() {
        SmartDashboard.putBoolean("Thrower Intake Run",false);
        ThrowerIntake(0.0);
        if (!Robot.intakeRunning) {
            Robot.intakeMotor2.set(0.0);
        }
    }
}