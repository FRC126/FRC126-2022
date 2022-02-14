package frc.robot.commands;


import frc.robot.Robot;
import frc.robot.InternalData;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 *
 */
public class ThrowerWork extends CommandBase {
    int targetRPM, 
        iters,
        count=0,
        minLoops=0;
    boolean reachedRPM=false;

    public ThrowerWork(int targetRPM_in, int minLoops_in) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        targetRPM = targetRPM_in;
        minLoops = minLoops_in;
    }

    // Called just before this Command runs the first time
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    public void execexecute() {
        reachedRPM = Robot.ballThrower.throwerRPM(targetRPM);
    }

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        if (reachedRPM && minLoops <= 0) {
             return true;
        }
        return false;
    }

    // Called once after isFinished returns true
    public void end(boolean isInteruppted) {
        Robot.driveBase.Drive(0, 0);
    }

}

