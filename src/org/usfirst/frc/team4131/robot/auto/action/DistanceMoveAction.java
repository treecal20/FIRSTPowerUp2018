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
    private static final int V_GRANULARITY = 200;
    /**
     * The amount of acceptable error for the target tick
     * distance
     */
    private static final int ERR_BOUND = 30;

    /** The drive base used to move the robot */
    private final DriveBaseSubsystem driveBase;
    /** The number of ticks used to move the left wheel */
    private final int leftDist;
    /** The number of ticks used to move the right wheel */
    private final int rightDist;

    /**
     * Creates a new action that will move the robot the
     * given distance, with a velocity of 0.3
     *
     * @param driveBase the robot drive base
     * @param distance the distance, in inches
     */
    public DistanceMoveAction(DriveBaseSubsystem driveBase, double distance) {
        this.driveBase = driveBase;
        this.leftDist = (int) (74.9151910531 * distance);
        this.rightDist = (int) (75.5747883349 * distance);
    }

    @Override
    public void doAction() {
        this.driveBase.reset();
        this.driveBase.gotoPosition(this.leftDist, this.rightDist);

        int lastLeftValue = this.driveBase.getLeftDist();
        int lastRightValue = this.driveBase.getRightDist();
        int roundsSinceLastChange = 0;
        while (true) {
            int newLeftValue = this.driveBase.getLeftDist();
            int newRightValue = this.driveBase.getRightDist();
            boolean hasChanged = false;

            int lDiff = Math.abs(newLeftValue - lastLeftValue);
            if (lDiff > ERR_BOUND) {
                lastLeftValue = newLeftValue;
                roundsSinceLastChange = 0;
                hasChanged = true;
            }

            int rDiff = Math.abs(newRightValue - lastRightValue);
            if (rDiff > ERR_BOUND) {
                lastRightValue = newRightValue;
                roundsSinceLastChange = 0;
            } else if (!hasChanged) {
                roundsSinceLastChange++;
            }

            // Victory
            if (roundsSinceLastChange >= V_GRANULARITY) {
                break;
            }
        }
    }
}