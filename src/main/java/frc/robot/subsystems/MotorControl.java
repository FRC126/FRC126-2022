package frc.robot.subsystems;

import frc.robot.commands.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class MotorControl extends SubsystemBase {

    public void initDefaultCommand() {
            setDefaultCommand(new OperatorControl());
	}
}
