package org.usfirst.frc.team4131.robot.auto;

import org.usfirst.frc.team4131.robot.subsystem.DriveBaseSubsystem;

/**
 * An action that will cause the robot to be moved a
 * certain given distance, in inches.
 */
public class DistanceMoveAction implements Action {
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
        this.distance = inToTicks(distance);
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
        while (!this.hasSucceeded()) {
        }
    }

    private boolean hasSucceeded() {
        int diff = Math.max(Math.abs(this.distance - this.driveBase.getLeftDist()),
                Math.abs(this.distance - this.driveBase.getRightDist()));
        return diff < 25;
    }
}