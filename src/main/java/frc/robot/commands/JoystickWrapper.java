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

import edu.wpi.first.wpilibj.XboxController;

public class JoystickWrapper {
    final XboxController joystick;
    final double driftOffset;
    public JoystickWrapper(XboxController joystick, double driftOffset) {
        this.joystick = joystick;
        this.driftOffset = driftOffset;
    }

    public double getLeftStickY() {
        return joystick.getLeftY();
    }
    public double getLeftStickX() {
        return joystick.getLeftX();
    }
    public double getLeftTrigger() {
        return joystick.getLeftTriggerAxis();
    }
    public double getRightTrigger() {
        return joystick.getRightTriggerAxis();
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
        return joystick.getRightY() * -1;
    }
    public double getRightStickX() {
        return joystick.getRightX();
    }

    public boolean isAButton() {
        return joystick.getAButtonPressed();
    }
    public boolean isBButton() {
        return joystick.getBButtonPressed();
    }
    public boolean isXButton() {
        return joystick.getXButtonPressed();
    }
    public boolean isYButton() {
        return joystick.getYButtonPressed();
    }
    public boolean isLShoulderButton() {
        return joystick.getLeftBumperPressed();
    }
    public boolean isRShoulderButton() {
        return joystick.getRightBumperPressed();
    }
    public boolean isLStickPressButton() {
        return joystick.getLeftStickButtonPressed();
    }
    public boolean isRStickPressButton() {
        return joystick.getRightStickButtonPressed();
    }

    public int getPOV() {
        return joystick.getPOV();
    }   
}
