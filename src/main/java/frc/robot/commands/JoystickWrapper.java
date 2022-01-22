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

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickWrapper {
    final Joystick joystick;
    final double driftOffset;
    public JoystickWrapper(Joystick joystick, double driftOffset) {
        this.joystick = joystick;
        this.driftOffset = driftOffset;
    }

    public double getLeftStickY() {
        return getRawAxis(RobotMap.lStickY) * -1;
    }
    public double getLeftStickX() {
        return getRawAxis(RobotMap.lStickX);
    }
    public double getLeftTrigger() {
        return getRawAxis(RobotMap.Ltrigger);
    }
    public double getRightTrigger() {
        return getRawAxis(RobotMap.Rtrigger);
    }

    public double getTriggers() {
        double tr = getRightTrigger();
        double tl = getLeftTrigger();
        double trigs;
		if(tr > 0) {
			trigs = tr;
		} else {
			trigs = tl * -1;
        }
        return trigs;
    }
    public double getRightStickY() {
        return getRawAxis(RobotMap.rStickY) * -1;
    }
    public double getRightStickX() {
        return getRawAxis(RobotMap.rStickX);
    }

    private double getRawAxis(int axis) {
        double value = joystick.getRawAxis(axis);
        if(Math.abs(value) < driftOffset) { // Prevent control drifting (driver controller)
		    value = 0;
        }
        return value;
    }

    public boolean isAButton() {
        return getRawButton(RobotMap.xboxA);
    }
    public boolean isBButton() {
        return getRawButton(RobotMap.xboxB);
    }
    public boolean isXButton() {
        return getRawButton(RobotMap.xboxX);
    }
    public boolean isYButton() {
        return getRawButton(RobotMap.xboxY);
    }
    public boolean isLShoulderButton() {
        return getRawButton(RobotMap.xboxLTrig);
    }
    public boolean isRShoulderButton() {
        return getRawButton(RobotMap.xboxRTrig);
    }
    public boolean isLStickPressButton() {
        return getRawButton(RobotMap.xboxLStick);
    }
    public boolean isRStickPressButton() {
        return getRawButton(RobotMap.xboxRStick);
    }

    private boolean getRawButton(int button) {
        return joystick.getRawButton(button);
    }

    public int getPOV() {
        return joystick.getPOV();
    }   
}
