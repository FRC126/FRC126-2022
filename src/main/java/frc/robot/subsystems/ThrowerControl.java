package frc.robot.subsystems;

import frc.robot.Robot;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Log;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class ThrowerControl extends SubsystemBase {

    //public void initDefaultCommand() {
    //        setDefaultCommand(new OperatorControl(this));
	//}

    public ThrowerControl() {
        CommandScheduler.getInstance().registerSubsystem(this);
        setDefaultCommand(new OperatorControl(this));
    }

    public void periodic() {
        //OperatorControl foo = new OperatorControl(this);
        //foo.execute();
        //Log.print(0, "Robot", "thrower control");
    }

    public void ThrowerSpeed(double speed) {
        Robot.throw1.set(ControlMode.PercentOutput,speed);
        Robot.throw2.set(ControlMode.PercentOutput,speed * -1);
    }   
}
