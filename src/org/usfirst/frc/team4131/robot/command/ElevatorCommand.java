package org.usfirst.frc.team4131.robot.command;

import org.usfirst.frc.team4131.robot.Oi;
import org.usfirst.frc.team4131.robot.Robot;
import org.usfirst.frc.team4131.robot.subsystem.ElevatorSubsystem;

/**
 * A command used to raise or lower the crate held in the
 * claw using the elevator subsystem.
 */
//TODO test when elevator is built
public class ElevatorCommand extends SingleSubsystemCmd<ElevatorSubsystem> {
    public ElevatorCommand(ElevatorSubsystem subsystem) {
        super(subsystem);
    }

    /**
     * Checks the claw elevator button in order to determine
     * if the claw should be raised.
     *
     * @return {@code true} to signal that the claw should
     * be raised
     */
    private static boolean shouldRaise() {
        return Oi.CLAWUP.get();
    }

    /**
     * Checks the claw elevator button in order to determine
     * if the claw should be lowered.
     *
     * @return {@code true} to signal that the claw should
     * be lowered
     */
    private static boolean shouldLower() {
        return Oi.CLAWDOWN.get();
    }

    @Override
    protected void execute() {
        if (shouldRaise() && shouldLower()) {
            this.subsystem.stop();
        } else if (shouldRaise()) {
            if (!Robot.isElevatorTop) {
                this.subsystem.stop();
            } else {
                this.subsystem.raise();
            }
        } else if (shouldLower()) {
            if (!Robot.isElevatorBottom) {
                this.subsystem.stop();
            } else {
                this.subsystem.lower();
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