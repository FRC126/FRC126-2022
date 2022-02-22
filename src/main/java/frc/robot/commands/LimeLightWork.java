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

import frc.robot.Robot;
import frc.robot.subsystems.*;
import frc.robot.JoystickWrapper;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class LimeLightWork extends CommandBase {
    public static int iter=0;
    JoystickWrapper driveJoystick;

	/************************************************************************
	 ************************************************************************/

    public LimeLightWork(LimeLight subsystem) {
		addRequirements(subsystem);
        driveJoystick = new JoystickWrapper(Robot.oi.driveController, 0.05);
    }

	/************************************************************************
     * Called just before this Command runs the first time
	 ************************************************************************/

    public void initialize() {
    }

	/************************************************************************
     * Called repeatedly when this Command is scheduled to run
	 ************************************************************************/

    public void execute() {

        if (driveJoystick.getPovUp()) {
            Robot.targetType = Robot.targetTypes.TargetSeek;
        } else {
            Robot.targetType = Robot.targetTypes.NoTarget;
        }     

        Robot.limeLight.trackTarget();
    }

	/************************************************************************
     * Make this return true when this Command no longer needs to run execute()
	 ************************************************************************/

    public boolean isFinished() {
        return false;
    }
}
