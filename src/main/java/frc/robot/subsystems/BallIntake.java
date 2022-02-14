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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class BallIntake extends SubsystemBase {
    int delay=0;
	private Solenoid intakeSolenoid1;
	private Solenoid intakeSolenoid2;

	/************************************************************************
	 ************************************************************************/

     public BallIntake() {
        // Register this subsystem with command scheduler and set the default command
        CommandScheduler.getInstance().registerSubsystem(this);
        setDefaultCommand(new IntakeControl(this));
    
        intakeSolenoid1 = new Solenoid(PneumaticsModuleType.CTREPCM,1);
        intakeSolenoid2 = new Solenoid(PneumaticsModuleType.CTREPCM,2);
    }

	/************************************************************************
	 ************************************************************************/
    
    public void periodic() {}

	/************************************************************************
     * Run Intake Wheels
	 ************************************************************************/

    public void IntakeSpeed(double speed) {
        // TODO Define Thrower Motors  
        Robot.intakeMotor1.set(speed);
        Robot.intakeMotor2.set(speed);
    }   

    public void IntakeRun() {
        IntakeSpeed(0.3);
    }

    public void IntakeReverse() {
        IntakeSpeed(-0.3);
    }

    public void IntakeStop() {
        IntakeSpeed(0);
    }

  	/************************************************************************
    * Extend/Retract Intake
	 ************************************************************************/

    public void MoveIntake(boolean extend) {      
        if (extend) {
            intakeSolenoid1.set(true);
            intakeSolenoid2.set(true);
        } else {
            intakeSolenoid1.set(false);
            intakeSolenoid2.set(false);
        }
    }

  	/************************************************************************
	 ************************************************************************/


     public void ExtendIntake() {      
        intakeSolenoid1.set(true);
        intakeSolenoid2.set(true);
    }

  	/************************************************************************
	 ************************************************************************/

    public void RetractIntake() {      
        intakeSolenoid1.set(true);
        intakeSolenoid2.set(true);
    }
}

