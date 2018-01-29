package org.usfirst.frc.team4131.robot.command;

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

    @Override
    protected void execute() {
        this.subsystem.doClamp();
    }
}