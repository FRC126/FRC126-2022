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

    public void checkCurrent() {
        double currentLeft=Robot.climberMotorLeft.getStatorCurrent();
        double currentRight=Robot.climberMotorRight.getStatorCurrent();

        double currentLimit = 5;

        SmartDashboard.putNumber("Climber Currentn R", currentRight);
        SmartDashboard.putNumber("Climber Currentn L", currentLeft);

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

     private double getRightPos() {
        double posRight = Robot.climberMotorRight.getSelectedSensorPosition();
        SmartDashboard.putNumber("Arm Position Right", posRight);
        return(posRight);
     }

   	/************************************************************************
     ************************************************************************/

    private double getLeftPos() {
        double posLeft = Robot.climberMotorLeft.getSelectedSensorPosition();
        SmartDashboard.putNumber("Arm Position Left", posLeft);
        return(posLeft);
     }

  	/************************************************************************
     ************************************************************************/

     public void RaiseClimber() {
        //  Raise Climber
        limitCountLeft--;
        limitCountRight--;

        // TODO, need to know what the height limit is on the arm encoders.
        // TODO have position for first bar and second bar
        double heightLimit = 25000;

        // Check the current draw before we move the motors
        checkCurrent();   

        double posRight = getRightPos();
        double posLeft = getLeftPos();

        // Need to use encoder to track max extension
        if (posLeft < heightLimit && limitCountLeft <= 0 ) {
            if (posLeft < heightLimit - 5000) {
                // Slow down as we get close to the limit
                Robot.climberMotorLeft.set(ControlMode.PercentOutput,0.2);
            } else {
                Robot.climberMotorLeft.set(ControlMode.PercentOutput,0.4);
            }    
        } else {
            Robot.climberMotorLeft.set(ControlMode.PercentOutput,-0);
        }

        if (posRight < heightLimit && limitCountRight <= 0 ) {
            if (posRight < heightLimit - 5000) {
                // Slow down as we get close to the limit
                Robot.climberMotorRight.set(ControlMode.PercentOutput,0.2);
            } else {
                Robot.climberMotorRight.set(ControlMode.PercentOutput,0.4);
            }    
        } else {
            Robot.climberMotorRight.set(ControlMode.PercentOutput,-0);
        }

        // Check the current draw after we move the motors
        checkCurrent();
    }   

  	/************************************************************************
     ************************************************************************/

    public void LowerClimber() {
        limitCountLeft--;
        limitCountRight--;

        // Check the current draw before we move the motors
        checkCurrent();

        // Need to use encoder to track retraction.
        double posRight = getRightPos();
        double posLeft = getLeftPos();

       if (Robot.leftClimbLimit.get() == true) {
            // Stop lowering left arm
            // zero encoder
            Robot.climberMotorLeft.set(ControlMode.PercentOutput,0);
            Robot.climberMotorLeft.setSelectedSensorPosition(0);
        } else {
            if ( limitCountLeft <= 0 ) {
                if (posLeft<5000) {
                    // Slow down as we get close to the bottom
                    Robot.climberMotorLeft.set(ControlMode.PercentOutput,-0.2);
                } else {    
                    Robot.climberMotorLeft.set(ControlMode.PercentOutput,-0.4);
                }    
            } else {
                Robot.climberMotorLeft.set(ControlMode.PercentOutput,-0);
            }
        }

        if (Robot.rightClimbLimit.get() == true) {
            // Stop lowering rightlc -l arm
            // zero encoder
            Robot.climberMotorRight.set(ControlMode.PercentOutput,0);
            Robot.climberMotorRight.setSelectedSensorPosition(0);
        } else {
            if ( limitCountRight <= 0 ) {
                if (posRight<5000) {
                    // Slow down as we get close to the bottom
                    Robot.climberMotorRight.set(ControlMode.PercentOutput,-0.2);
                } else {    
                    Robot.climberMotorRight.set(ControlMode.PercentOutput,-0.4);
                }    
            } else {
                Robot.climberMotorRight.set(ControlMode.PercentOutput,0);
            }
        }

        // Check the current draw after we move the motors
        checkCurrent();
    }
}

