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
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class LimeLight extends SubsystemBase {

    private boolean llTargetValid;
    private double llTargetArea;
    private double llTargetX;
    private double llTargetY;
    private int validCount;
    private int missedCount;
    private int centeredCount;
    private int iter;
    private boolean throwNow;
    public static SequentialCommandGroup throwCommand;

	/************************************************************************
	 ************************************************************************/

    public LimeLight() {
        // Register this subsystem with command scheduler and set the default command
        CommandScheduler.getInstance().registerSubsystem(this);
        setDefaultCommand(new LimeLightWork(this));

        llTargetValid=false;
        llTargetArea = 0.0;
        llTargetX = 0.0;
        llTargetY = 0.0;

        validCount=0;
        missedCount=0;

        centeredCount=0;

        iter=0;

        throwNow=false;
    }

	/************************************************************************
	 ************************************************************************/

    public void periodic() {}

	/************************************************************************
	 ************************************************************************/

	public boolean getllTargetValid() {
       return llTargetValid;
    }   

	/************************************************************************
	 ************************************************************************/

	public double getllTargetArea() {
        return llTargetArea;
    }   
 
	/************************************************************************
	 ************************************************************************/

    public double getllTargetX() {
        return llTargetX;
    }   

	/************************************************************************
	 ************************************************************************/

    public double getllTargetY() {
        return llTargetY;
    }   

	/************************************************************************
	 ************************************************************************/

    public void setllTargetData(boolean isValid,
                                double targetArea,
                                double targetX,
                                double targetY) {
        llTargetValid = isValid;
        llTargetArea = targetArea;
        llTargetX = targetX;
        llTargetY = targetY;
    }    

	/************************************************************************
	 ************************************************************************/

    public void getCameraData() {

        //if ( Robot.trackTarget == Robot.targetTypes.throwingTarget ||
        //Robot.trackTarget == Robot.targetTypes.turretOnly ) {
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);
        //} else {
        //    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1);
        //}
        double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
        double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        double ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
        
        if (tv < 1.0) {
            setllTargetData(false, 0, 0, 0);
        } else {
            setllTargetData(true, ta, tx, ty);
        }        
    }

	/************************************************************************
	 ************************************************************************/

    public void setLED(boolean onOff) {
        if (onOff) {
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(0);
        } else {
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
        }    
    }

	/************************************************************************
	 ************************************************************************/

    public void setCameraMode(boolean vision) {
        if (vision) {
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
        } else {
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
        }    
    }

	/************************************************************************
	 ************************************************************************/

    public void setPipeline(int pipeline) {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(pipeline);
    }

	/************************************************************************
	 ************************************************************************/

    public void setStreamMode(int mode) {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("stream").setNumber(mode);
    }

   	/************************************************************************
	 ************************************************************************/
    
     private void dashboardData() {
        SmartDashboard.putBoolean("LL Valid:", Robot.limeLight.getllTargetValid());
        SmartDashboard.putNumber("LL Area:", getllTargetArea());
        SmartDashboard.putBoolean("shootnow:", Robot.shootNow);
     }

   	/************************************************************************
	 ************************************************************************/

    public void trackTarget() {

        if (Robot.targetType != Robot.targetTypes.TargetSeek) {
            Robot.robotDrive=0;
            Robot.robotTurn=0;
            Robot.shootNow=false;

            setllTargetData(false, 0, 0, 0);
            
            validCount=0;
            missedCount=0;
            centeredCount=0;
            iter=0;

            if (throwNow == true) {
                throwCommand.cancel();
                throwNow=false;
            }
            
            dashboardData();
		 	return;
        }
        
        Robot.limeLight.getCameraData();

        if (Robot.limeLight.getllTargetValid()){
            // We found a valid vision target.
            iter=0;

            // Keep track of the number of time we seen a valid target
            validCount++;
            missedCount=0;

            Robot.robotDrive=0;

            double area = Robot.limeLight.getllTargetArea();
            double threshold;

            if (area < .2) {
                threshold = 1.5;
            } else if (area < 1) {
                threshold = 2.5;
            } else if (area < 2) {
                threshold = 3.5;
            } else {
                threshold = 4.5;
            }

            if ( Robot.limeLight.getllTargetX() < ( -1 * threshold ) ) {
                // Target is to the left of the Robot, need to move left
                Robot.robotTurn=-.25;
                if ( Robot.limeLight.getllTargetX() + threshold < ( -1 * threshold ) ) {
                    Robot.robotTurn=-.35;
                }
                centeredCount=0;
                Robot.shootNow=false;
            } else if ( Robot.limeLight.getllTargetX() > threshold ) {
                // Target is to the left of the Robot, need to move right
                Robot.robotTurn=.25;
                if ( Robot.limeLight.getllTargetX() - threshold > threshold ) {
                    Robot.robotTurn=.35;
                }
                centeredCount=0;
                Robot.shootNow=false;
            } else {
                centeredCount++;
                if (centeredCount > 10) {
                    Robot.shootNow=true;
                } else {
                   Robot.shootNow=false;
                }
                Robot.robotTurn=0;
            }
        } else {
            if (validCount > 10 && missedCount <= 3) {
                // Don't change the old data, so we won't stop on dropping a frame or 3
                missedCount++;
                Robot.robotTurn = 0;
            } else {
                iter++;
                centeredCount=0;
                Robot.shootNow=false;
                validCount=0;

                if (iter > 10 && iter < 350) {
                    // Try turning until we pick up a target
                    Robot.robotTurn= -0.3;
                } else {
                    Robot.robotTurn=0;
                }    

                if ( iter > 500 ) { 
                    iter=0; 
                }

                // Don't move forward or back
                Robot.robotDrive=0;
                
            }    
        }

        if (Robot.shootNow && throwNow == false) {
            throwCommand = new AutoThrow();
            throwNow=true;
        }

        dashboardData();
    }          
}

