package org.usfirst.frc.team4131.robot.command;

import org.usfirst.frc.team4131.robot.subsystem.ClimberSubsystem;

/**
 * A command which will activate the climber and raise the
 * robot using the pull-up bar.
 */
// TODO: complete
public class ClimbCommand extends SingleSubsystemCmd<ClimberSubsystem> {
    public ClimbCommand(ClimberSubsystem subsystem) {
        super(subsystem);
    }

    @Override
    protected void execute() {
        this.subsystem.doClimb();
    }
}