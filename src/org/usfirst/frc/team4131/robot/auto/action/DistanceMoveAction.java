package org.usfirst.frc.team4131.robot.auto.action;

import org.usfirst.frc.team4131.robot.auto.Action;
import org.usfirst.frc.team4131.robot.subsystem.DriveBaseSubsystem;

/**
 * An action that will cause the robot to be moved a
 * certain given distance, in inches.
 */
public class DistanceMoveAction implements Action {
    /**
     * The number of polls in loop to reasonably declare
     * PID victory
     */
    private static final int V_GRANULARITY = 500;
    /**
     * The amount of acceptable error for the target tick
     * distance
     */
    private static final int ERR_BOUND = 1;

    /** The drive base used to move the robot */
    private final DriveBaseSubsystem driveBase;
    /** The number of ticks to move the robot */
    private final int distance;
    
    /**
     * Creates a new action that will move the robot the
     * given distance, with a velocity of 0.3
     *
     * @param driveBase the robot drive base
     * @param distance the distance, in inches
     */
    public DistanceMoveAction(DriveBaseSubsystem driveBase, double distance) {
        this.driveBase = driveBase;
        this.distance = (int) (74.9151910531 * distance);
    }

    @Override
    public void doAction() {
        this.driveBase.reset();
        this.driveBase.gotoPosition(this.distance);

        int lastLeftValue = this.driveBase.getLeftDist();
        int roundsSinceLastChange = 0;
        while (true) {
            int newLeftValue = this.driveBase.getLeftDist();

            int lDiff = Math.abs(newLeftValue - lastLeftValue);
            if (lDiff >= ERR_BOUND) {
                lastLeftValue = newLeftValue;
                roundsSinceLastChange = 0;
            } else {
                roundsSinceLastChange++;
            }

            // Victory
            if (roundsSinceLastChange >= V_GRANULARITY) {
                break;
            }
        }
    }
}