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

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickWrapper {
    final Joystick joystick;
    final double driftOffset;
    public JoystickWrapper(Joystick joystick, double driftOffset) {
        this.joystick = joystick;
        this.driftOffset = driftOffset;
    }

    ////////////////////////////////////////////////////
    // JoyStick Interfaces
    ////////////////////////////////////////////////////

    public double getLeftStickY() {
        return getRawAxis(RobotMap.lStickY) * -1;
    }
    public double getLeftStickX() {
        return getRawAxis(RobotMap.lStickX);
    }
    public double getRightStickY() {
        return getRawAxis(RobotMap.rStickY) * -1;
    }
    public double getRightStickX() {
        return getRawAxis(RobotMap.rStickX);
    }
    public boolean isLStickPressButton() {
        return getRawButton(RobotMap.xboxLStick);
    }
    public boolean isRStickPressButton() {
        return getRawButton(RobotMap.xboxRStick);
    }
    private double getRawAxis(int axis) {
        double value = joystick.getRawAxis(axis);
        if(Math.abs(value) < driftOffset) { // Prevent control drifting (driver controller)
		    value = 0;
        }
        return value;
    }

    ////////////////////////////////////////////////////
    // Trigger Interfaces
    ////////////////////////////////////////////////////

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
    
    ////////////////////////////////////////////////////
    // Button Interfaces
    ////////////////////////////////////////////////////

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
    public boolean isBackButton() {
        return getRawButton(RobotMap.xboxBack);
    }
    public boolean isStartButton() {
        return getRawButton(RobotMap.xboxStart);
    }
    public boolean isLShoulderButton() {
        return getRawButton(RobotMap.xboxLTrig);
    }
    public boolean isRShoulderButton() {
        return getRawButton(RobotMap.xboxRTrig);
    }

    private boolean getRawButton(int button) {
        return joystick.getRawButton(button);
    }

    ////////////////////////////////////////////////////
    // POV Interfaces
    ////////////////////////////////////////////////////

    public int getPov() {
        return joystick.getPOV();
    }   
    public boolean getPovUp() {
        int tmp=joystick.getPOV();
        if (tmp == 0) {
            return true;
        }
        return false;
    }   
    public boolean getPovDown() {
        int tmp=joystick.getPOV();
        if (tmp == 180) {
            return true;
        }
        return false;
    }   
    public boolean getPovRight() {
        int tmp=joystick.getPOV();
        if (tmp == 90) {
            return true;
        }
        return false;
    }   
    public boolean getPovLeft() {
        int tmp=joystick.getPOV();
        if (tmp == 270) {
            return true;
        }
        return false;
    }   
}
