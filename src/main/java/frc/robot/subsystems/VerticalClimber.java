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

public class VerticalClimber extends SubsystemBase {

    double currentLimit = 5;
    int limitCountLeft=0;
    int limitCountRight=0;

	/************************************************************************
	 ************************************************************************/

     public VerticalClimber() {
        // Register this subsystem with command scheduler and set the default command
        CommandScheduler.getInstance().registerSubsystem(this);
        setDefaultCommand(new ClimberControl(this));
    }

	/************************************************************************
	 ************************************************************************/
    
    public void periodic() {}

	/************************************************************************
     ************************************************************************/

     public void StopClimber() {
        Robot.climberMotorLeft.set(ControlMode.PercentOutput,-0);
        Robot.climberMotorRight.set(ControlMode.PercentOutput,-0);
    }

	/************************************************************************
     ************************************************************************/

     public void RaiseClimber() {
        //  Raise Climber
        limitCountLeft--;
        limitCountRight--;

        // TODO, need to know what the height limit is on the arm encoders.
        double heightLimit = 5000;

        double currentLeft=Robot.climberMotorLeft.getStatorCurrent();
        double currentRight=Robot.climberMotorRight.getStatorCurrent();

        SmartDashboard.putNumber("Climber Currentn R", currentRight);
        SmartDashboard.putNumber("Climber Currentn L", currentLeft);

        // TODO, set current limit, so we don't break things.

        double posRight = Robot.climberMotorRight.getSelectedSensorPosition();
        double posLeft = Robot.climberMotorLeft.getSelectedSensorPosition();

        // Need to use encoder to track max extension
        if (posRight < heightLimit && limitCountRight <= 0 ) {
            //Robot.climberMotorL.set(ControlMode.PercentOutput,-0.3);
        } else if ( currentRight > currentLimit ) {
            Robot.climberMotorRight.set(ControlMode.PercentOutput,0);
            limitCountRight=50;
        } else {
            Robot.climberMotorRight.set(ControlMode.PercentOutput,-0);
        }

        if (posLeft < heightLimit && limitCountLeft <= 0 ) {
            //Robot.climberMotorR.set(ControlMode.PercentOutput,-0.3);
        } else if ( currentLeft > currentLimit ) {
            Robot.climberMotorLeft.set(ControlMode.PercentOutput,0);
            limitCountLeft=50;
        } else {
            Robot.climberMotorLeft.set(ControlMode.PercentOutput,-0);
        }

        currentLeft=Robot.climberMotorLeft.getStatorCurrent();
        currentRight=Robot.climberMotorRight.getStatorCurrent();
        if ( currentLeft > currentLimit ) {
            Robot.climberMotorLeft.set(ControlMode.PercentOutput,0);
            limitCountLeft=50;
        }    
        if ( currentRight > currentLimit ) {
            Robot.climberMotorRight.set(ControlMode.PercentOutput,0);
            limitCountRight=50;
        }    
    }   

  	/************************************************************************
     ************************************************************************/

    public void LowerClimber() {
        limitCountLeft--;
        limitCountRight--;

        // Lower Climber
        double currentLeft=Robot.climberMotorLeft.getStatorCurrent();
        double currentRight=Robot.climberMotorRight.getStatorCurrent();

        SmartDashboard.putNumber("Climber Currentn R", currentRight);
        SmartDashboard.putNumber("Climber Currentn L", currentLeft);

        // Need to use encoder to track retraction.

        if (Robot.leftClimbLimit.get() == true) {
            // Stop lowering left arm
            // zero encoder
            Robot.climberMotorLeft.set(ControlMode.PercentOutput,0);
            Robot.climberMotorLeft.setSelectedSensorPosition(0);
        } else if ( currentLeft > currentLimit ) {
            Robot.climberMotorLeft.set(ControlMode.PercentOutput,0);
            limitCountLeft=50;
        } else {
            if ( limitCountLeft <= 0 ) {
                Robot.climberMotorLeft.set(ControlMode.PercentOutput,-0.3);
            } else {
                Robot.climberMotorLeft.set(ControlMode.PercentOutput,-0);
            }
        }

        if (Robot.rightClimbLimit.get() == true) {
            // Stop lowering rightlc -l arm
            // zero encoder
            Robot.climberMotorRight.set(ControlMode.PercentOutput,0);
            Robot.climberMotorRight.setSelectedSensorPosition(0);
        } else if ( currentRight > currentLimit ) {
            Robot.climberMotorRight.set(ControlMode.PercentOutput,0);
            limitCountRight=50;
        } else {
            if ( limitCountRight <= 0 ) {
                Robot.climberMotorRight.set(ControlMode.PercentOutput,-0.3);
            } else {
                Robot.climberMotorRight.set(ControlMode.PercentOutput,0);
            }
        }

        currentLeft=Robot.climberMotorLeft.getStatorCurrent();
        currentRight=Robot.climberMotorRight.getStatorCurrent();
        if ( currentLeft > currentLimit ) {
            Robot.climberMotorLeft.set(ControlMode.PercentOutput,0);
            limitCountLeft=50;
        }    
        if ( currentRight > currentLimit ) {
            Robot.climberMotorRight.set(ControlMode.PercentOutput,0);
            limitCountRight=50;
        }    
    }
}

