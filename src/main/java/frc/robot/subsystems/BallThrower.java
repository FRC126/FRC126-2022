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
import frc.robot.RobotMap;

/**********************************************************************************
 **********************************************************************************/

public class BallThrower extends SubsystemBase {
    static double targetRPM;
    static double throwerSpeed;
    static int delay;
//    static double P = 0.000008;
//    static double I = -0.0003;
    static double P = 0.000008;
    static double I = -0.0003;
    static double ix, error = 0.0;
    static int targetReachedCount=0;
    static double intakeSpeed=1.0;

	/************************************************************************
	 ************************************************************************/

     public BallThrower() {
        // Register this subsystem with command scheduler and set the default command
        CommandScheduler.getInstance().registerSubsystem(this);
        setDefaultCommand(new ThrowerControl(this));
        targetReachedCount=0;
    }

	/************************************************************************
	 ************************************************************************/
    
    public void periodic() {}

	/************************************************************************
     * Run Main Thower Wheels by target RPM
	 ************************************************************************/

    public boolean throwerRPM(int targetRPM) {
        boolean targetReached=false;
        boolean usePidLoop=true;

        // Range check the RPM
		if (targetRPM > 20800) { targetRPM = 20800; }
		if (targetRPM < 0) { targetRPM = 0; }

        int rpm = (int)Math.abs(Robot.throwerMotor2.getSelectedSensorVelocity());

        if (usePidLoop == true) {
            /**********************************************************************
             * PID Loop for controlling motor RPM
             **********************************************************************/

            if (targetRPM == 0) { /** Spindown **/
                throwerSpeed=0;
            } else { /** Normal operation **/                SmartDashboard.putNumber("ix",ix);

                error = targetRPM - rpm;
                ix = error * 0.02; /** Loop frequency **/
                throwerSpeed += P * error + I * ix;

                SmartDashboard.putNumber("err0r",error);
                SmartDashboard.putNumber("ix",ix);
                SmartDashboard.putNumber("throwerSpeed",throwerSpeed);
            }

            if(throwerSpeed < 0) {
                throwerSpeed = 0;
            } else if(throwerSpeed > 1) {
                throwerSpeed = 1;
            }

            if (targetRPM < rpm + 100 && targetRPM > rpm - 100) {
                targetReached=true;
            }
        } else {
            /**********************************************************************
             * Manual Motor Control
             **********************************************************************/
            if (rpm < targetRPM-75) {
                // If we are below the rpm target
                if (delay <= 0 ) {
                    if (rpm < targetRPM-500) {
                        // If we are more than 500 RPM away, change speed faster
                        if (rpm < targetRPM-1500) {
                            // If we are more than 1500 RPM away, change speed even faster
                            delay=1;
                        } else {
                            delay=2;
                        }
                        throwerSpeed = throwerSpeed + 0.01;   
                    } else {
                        // if we less than 500 RPM awawy, change speed slower
                        delay=2;
                        throwerSpeed = throwerSpeed + 0.002;
                    }
                    if (throwerSpeed > 1) { throwerSpeed = 1; }
                }
            } else if (rpm > targetRPM+75) {
                // If we are above the rpm target
                if (delay <= 0 ) {
                    if (rpm > targetRPM+500) {
                        // If we are more than 500 RPM away, change speed faster
                        if (rpm > targetRPM+1500) {
                            // If we are more than 1500 RPM away, change speed even faster
                            delay=1;
                        } else {
                            delay=2;
                        }
                        throwerSpeed = throwerSpeed - 0.01;   
                    } else {
                        // if we less than 500 RPM awawy, change speed slower
                        delay=2;
                        throwerSpeed = throwerSpeed - 0.002;
                    }
                    if (throwerSpeed < 0) { throwerSpeed = 0; }
                }
            } else {
                targetReached=true;
            }
    
            if (targetRPM == 0) {
                // Short cut to stop the thrower motors
                throwerSpeed=0;
            }
            
            delay--;
        }

        // Set the speed on the Thrower Motors
        Robot.throwerMotor1.set(ControlMode.PercentOutput,throwerSpeed * RobotMap.throwerMotor1Inversion);
        Robot.throwerMotor2.set(ControlMode.PercentOutput,throwerSpeed * RobotMap.throwerMotor2Inversion);

        // Log info to the smart dashboard
		SmartDashboard.putNumber("Throw RPM Current",rpm);
        SmartDashboard.putNumber("Throw RPM Target",targetRPM);
        SmartDashboard.putBoolean("Throw RPM Reached",targetReached);

        return(targetReached);
    }

   	/************************************************************************
	 ************************************************************************/

     public void resetReachedCount() {
        targetReachedCount=0;
     }

   	/************************************************************************
	 ************************************************************************/

     public void autoThrowerRPM(int targetRPM) {
        boolean rpmReached = throwerRPM(targetRPM);

		if (rpmReached) {
            if ( targetReachedCount++ > 10) {
    			// If we reached the target RPM, and autoThrow is set, run the thrower intake motor
			    Robot.ballThrower.ThrowerIntakeRun();
			    Robot.throwerRunning=true;
            }    
		} else {
			if (targetReachedCount > 10 ) {
				// If we hit the RPM target 10 times, just keep throwing till they let go of the
				// button.
				Robot.throwerRunning=true;
				Robot.ballThrower.ThrowerIntakeRun();
		    } else {
				// Stop trying to throw a ball.
				Robot.throwerRunning=false;
				Robot.ballThrower.ThrowerIntakeStop();
			}		
		}
     }

  	/************************************************************************
    * Run Thrower Intake Wheels
	 ************************************************************************/

    public void ThrowerIntake(double speed) {
        Robot.feederMotor.set(speed);
    }

   	/************************************************************************
	 ************************************************************************/

    public void ThrowerIntakeRun() {
        //SmartDashboard.putBoolean("Thrower Intake Run",true);
        ThrowerIntake(0.7 * RobotMap.feederMotorInversion);
        if (!Robot.intakeRunning) {
            Robot.intakeMotor2.set(intakeSpeed);
        }

    }

  	/************************************************************************
	 ************************************************************************/

     public void ThrowerIntakeStop() {
        //SmartDashboard.putBoolean("Thrower Intake Run",false);
        ThrowerIntake(0.0);
        if (!Robot.intakeRunning) {
            Robot.intakeMotor2.set(0.0);
        }
    }

      	/************************************************************************
	 ************************************************************************/

    public void ThrowerAllStop() {
        //SmartDashboard.putBoolean("Thrower Intake Run",false);
        throwerRPM(0);
        ThrowerIntake(0.0);
        if (!Robot.intakeRunning) {
            Robot.intakeMotor2.set(0.0);
        }
    }

}