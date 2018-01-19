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
    /** The distance that should be moved, in ticks */
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

        // TODO: Change back
        this.distance = 10000; // inToTicks(distance);
    }

    /**
     * Converts the given distance in inches to encoder
     * ticks that may be passed to the talons.
     *
     * @param inches the distance to convert
     * @return the whole number of ticks
     */
    public static int inToTicks(double inches) {
        return (int) Math.round(inches / 0.01308125);
    }

    @Override
    public void doAction() {
        this.driveBase.reset();
        this.driveBase.gotoPosition(this.distance);

        int tLeft = this.driveBase.getLeftDist();
        int tRight = this.driveBase.getRightDist();
        int roundsSinceLastChange = 0;
        int i = 0;
        while (true) {
            if (i++ == 100) {
                System.out.println("L=" + tLeft + " R=" + tRight);
                i = 0;
            }

            int nLeft = this.driveBase.getLeftDist();
            int nRight = this.driveBase.getRightDist();
            boolean hasChanged = false;

            int lDiff = Math.abs(nLeft - tLeft);
            if (lDiff > ERR_BOUND) {
                tLeft = nLeft;
                roundsSinceLastChange = 0;
                hasChanged = true;
            }

            int rDiff = Math.abs(nRight - tRight);
            if (rDiff > ERR_BOUND) {
                tRight = nRight;
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