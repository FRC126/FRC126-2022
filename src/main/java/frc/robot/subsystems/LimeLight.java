package frc.robot.subsystems;

//import frc.robot.Robot;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class LimeLight extends SubsystemBase {

    private boolean llTargetValid;
    private double llTargetArea;
    private double llTargetX;
    private double llTargetY;
    private double turretTarget;

	/************************************************************************
	 ************************************************************************/

    public LimeLight() {
        llTargetValid=false;
        llTargetArea = 0.0;
        llTargetX = 0.0;
        llTargetY = 0.0;
        turretTarget = 0;
        CommandScheduler.getInstance().registerSubsystem(this);
            setDefaultCommand(new LimeLightWork(this));
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

    public double getllTurretTarget() {
        return turretTarget;
     }   

	/************************************************************************
	 ************************************************************************/

    public void setllTurretTarget(double target) {
        turretTarget = target;
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

}

