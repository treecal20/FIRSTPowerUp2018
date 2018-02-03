package org.usfirst.frc.team4131.robot.command;

import org.usfirst.frc.team4131.robot.Oi;
import org.usfirst.frc.team4131.robot.Robot;
import org.usfirst.frc.team4131.robot.subsystem.ElevatorSubsystem;

/**
 * A command which will activate the climber and raise the
 * robot using the pull-up bar.
 */
//TODO test when bot is built
public class ElevatorCommand extends SingleSubsystemCmd<ElevatorSubsystem> {
    public ElevatorCommand(ElevatorSubsystem subsystem) {
        super(subsystem);
    }

    @Override
    protected void execute() {
        if (up() && down()) {
        	this.subsystem.doStop();
        } else if (up()) {
        	this.subsystem.doClimb(true);
        	if (Robot.isTop) {
        		this.subsystem.doStop();
        	}
        } else if (down()) {
        	this.subsystem.doClimb(false);
        	if (!Robot.isBottom) {
        		this.subsystem.doStop();
        	}
        } else {
        	this.subsystem.doStop();
        }
    }
    
    protected boolean isFinished() {
        return false;
    }
    
    @Override
    protected void interrupted() {
        this.subsystem.doStop();
    }
    
    private static boolean up() {
    	return Oi.CLAWUP.get();
    }
    
    private static boolean down() {
    	return Oi.CLAWDOWN.get();
    }
}