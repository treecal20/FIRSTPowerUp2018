package org.usfirst.frc.team4131.robot.command;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * A command that implements control for a single subsystem.
 *
 * @param <T> the type of subsystem that will be commanded
 */
public abstract class SingleSubsystemCmd<T extends Subsystem> extends Command {
    /** The subsystem being commanded */
    protected final T subsystem;

    /**
     * Constructor used to pass and cache the subsystem for
     * use during execution.
     *
     * @param subsystem the subsystem required by the
     * command
     */
    public SingleSubsystemCmd(T subsystem) {
        this.subsystem = subsystem;
        this.requires(subsystem);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}