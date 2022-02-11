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
// import frc.robot.commands.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

//import com.ctre.phoenix.motorcontrol.ControlMode;

public class ReachClimber extends SubsystemBase {

	/************************************************************************
	 ************************************************************************/

     public ReachClimber() {
        // Register this subsystem with command scheduler and set the default command
        CommandScheduler.getInstance().registerSubsystem(this);
        //setDefaultCommand(new IntakeControl(this));
    }

	/************************************************************************
	 ************************************************************************/
    
    public void periodic() {}

	/************************************************************************
     ************************************************************************/

    public void RaiseClimber() {
        // TODO Raise Climber
    }   

  	/************************************************************************
     ************************************************************************/

    public void LowerClimber() {
        // TODO Lower Climber
    }
}


