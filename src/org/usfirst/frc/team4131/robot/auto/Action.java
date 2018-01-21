package org.usfirst.frc.team4131.robot.auto;

/**
 * An action that that will be run as a part of a procedure
 */
@FunctionalInterface
public interface Action {
    /**
     * Performs the action and blocks until it has finished
     */
    void doAction();
}