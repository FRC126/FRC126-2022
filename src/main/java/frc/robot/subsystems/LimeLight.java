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
        // Register this subsystem with command scheduler and set the default command
        CommandScheduler.getInstance().registerSubsystem(this);
        setDefaultCommand(new LimeLightWork(this));

        llTargetValid=false;
        llTargetArea = 0.0;
        llTargetX = 0.0;
        llTargetY = 0.0;
        turretTarget = 0;
    }

	/************************************************************************
	 ************************************************************************/
    @Override
    public void periodic() {
        //if ( Robot.trackTarget == Robot.targetTypes.throwingTarget ||
        //Robot.trackTarget == Robot.targetTypes.turretOnly ) {
            getEntry("pipeline").setNumber(0);
        //} else {
        //    getEntry("pipeline").setNumber(1);
        //}
        double tv = getEntry("tv").getDouble(0);
        double tx = getEntry("tx").getDouble(0);
        double ty = getEntry("ty").getDouble(0);
        double ta = getEntry("ta").getDouble(0);
        
        if (tv < 1.0) {
            setllTargetData(false, 0, 0, 0);
        } else {
            setllTargetData(true, ta, tx, ty);
        }        
    }

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

    public void setLED(boolean onOff) {
        getEntry("ledMode").setNumber(onOff ? 1 : 0);
    }

	/************************************************************************
	 ************************************************************************/

    public void setCameraMode(boolean vision) {
        getEntry("camMode").setNumber(vision ? 0 : 1);
    }

	/************************************************************************
	 ************************************************************************/

    public void setPipeline(int pipeline) {
        getEntry("pipeline").setNumber(pipeline);
    }

	/************************************************************************
	 ************************************************************************/

    public void setStreamMode(int mode) {
        getEntry("stream").setNumber(mode);
    }

    /************************************************************************
	 ************************************************************************/

    public NetworkTableEntry getEntry(String entry) {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry(entry);   
    }
}

