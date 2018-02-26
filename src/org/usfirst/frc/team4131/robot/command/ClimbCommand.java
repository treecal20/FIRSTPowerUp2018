package org.usfirst.frc.team4131.robot.command;

import org.usfirst.frc.team4131.robot.Oi;
import org.usfirst.frc.team4131.robot.Robot;
import org.usfirst.frc.team4131.robot.subsystem.ClimberSubsystem;

/**
 * A command which will activate the climber and raise the
 * robot using the pull-up bar.
 */
public class ClimbCommand extends SingleSubsystemCmd<ClimberSubsystem> {
    public ClimbCommand(ClimberSubsystem subsystem) {
        super(subsystem);
    }

    /**
     * Checks the climb button in order to determine whether
     * the robot should climb.
     *
     * @return {@code true} to signal that climbing should
     * commence
     */
    private static boolean shouldClimb() {
        return Oi.CLIMB.get();
    }

    /**
     * Checks the lower button in order to determine whether
     * the robot should lower itself.
     *
     * @return {@code true} to signal that the robot should
     * lower itself
     */
    private static boolean shouldDescend() {
        return Oi.DESCEND.get();
    }

    @Override
    protected void execute() {
        if (shouldClimb() && shouldDescend()) {
            this.subsystem.stop();
        } else if (shouldClimb()) {
            if (!Robot.isClimberTop) {
                this.subsystem.stop();
            } else {
                this.subsystem.climb();
            }
        } else if (shouldDescend()) {
            if (!Robot.isClimberBottom) {
                this.subsystem.stop();
            } else {
                this.subsystem.descend();
            }
        } else {
            this.subsystem.stop();
        }
    }

    @Override
    protected void interrupted() {
        this.subsystem.stop();
    }
}