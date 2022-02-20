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
     * Run Main Thower Wheels
	 ************************************************************************/

    public boolean throwerRPM(int targetRPM) {
        boolean targetReached=false;

        int rpm = (int)Math.abs(Robot.testTalon.getSelectedSensorVelocity());

        if (rpm < targetRPM-25) {
            if (delay <= 0 ) {
                if (rpm < targetRPM-500) {
                    if (rpm < targetRPM-1500) {
                        delay=1;
                    } else {
                        delay=2;
                    }
                    throwerSpeed = throwerSpeed + 0.01;   
                    throwerSpeed = throwerSpeed + 0.01;   
                } else {
                    delay=5;
                    throwerSpeed = throwerSpeed + 0.0025;
                }
                if (throwerSpeed > 1) { throwerSpeed = 1; }
            }
        } else if (rpm > targetRPM+25) {
            if (delay <= 0 ) {
                if (rpm > targetRPM+500) {
                    if (rpm > targetRPM+1500) {
                        delay=1;
                    } else {
                        delay=2;
                    }
                    throwerSpeed = throwerSpeed - 0.01;   
                } else {
                    delay=5;
                    throwerSpeed = throwerSpeed - 0.0025;
                }
                if (throwerSpeed < 0) { throwerSpeed = 0; }
            }
        } else {
            targetReached=true;
        }

        if (targetRPM == 0) {
            throwerSpeed=0;
        }

        delay--;

        // Test Motor
        //Robot.testTalon.set(ControlMode.PercentOutput, throwerSpeed);

        // Real Thrower Motors
        Robot.throwerMotor1.set(ControlMode.PercentOutput,throwerSpeed);
        Robot.throwerMotor2.set(ControlMode.PercentOutput,throwerSpeed * -1);

		SmartDashboard.putNumber("Intake RPM Current",rpm);
        SmartDashboard.putNumber("Intake RPM Target",targetRPM);

        return(targetReached);
    }
    
  	/************************************************************************
    * Run Thrower Intake Wheels
	 ************************************************************************/

    public void ThrowerIntake(double speed) {
        // TODO Define Thrower Motors  
        Robot.feederMotor.set(speed);
    }

   	/************************************************************************
	 ************************************************************************/

    public void ThrowerIntakeRun() {
        ThrowerIntake(0.4);
    }

  	/************************************************************************
	 ************************************************************************/

     public void ThrowerIntakeStop() {
        ThrowerIntake(0.0);
    }
}
