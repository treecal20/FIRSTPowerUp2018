package org.usfirst.frc.team4131.robot.command;

import org.usfirst.frc.team4131.robot.Oi;
import org.usfirst.frc.team4131.robot.RobotMap;
import org.usfirst.frc.team4131.robot.subsystem.ClawSubsystem;

/**
 * A command which will perform a clamp action in order to
 * pick up a power cube.
 */
// TODO: Complete
public class ClawCommand extends SingleSubsystemCmd<ClawSubsystem> {
    public ClawCommand(ClawSubsystem subsystem) {
        super(subsystem);
    }

    private boolean buttonClaw() {
    	return Oi.CLAW.get();
    }
    
    private boolean buttonEject() {
    	return Oi.EJECT.get();
    }
    
    @Override
    protected void execute() {
    	if (buttonClaw()) {
    		this.subsystem.doRelease();
    		if (buttonEject()) {
    			this.subsystem.doEject();
    		} else {
    			this.subsystem.doRetract();
    		}
    	} else {
    		this.subsystem.doRetract();
    		this.subsystem.doClamp();
    	}
    }
    
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void interrupted() {
        this.subsystem.doClamp();
        this.subsystem.doRetract();
    }
}