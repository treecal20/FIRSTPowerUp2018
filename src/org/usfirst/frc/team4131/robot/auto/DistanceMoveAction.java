package org.usfirst.frc.team4131.robot.auto;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4131.robot.subsystem.DriveBaseSubsystem;

/**
 * An action that will cause the robot to be moved a
 * certain given distance, in inches.
 */
public class DistanceMoveAction extends Action {
    /** The drive base used to move the robot */
    private final DriveBaseSubsystem driveBase;
    /** The distance that should be moved, in inches */
    private final int distance;

    /**
     * Creates a new action that will move the robot the
     * given distance, with a velocity of 0.3
     *
     * @param driveBase the robot drive base
     * @param distance the distance, in inches
     */
    public DistanceMoveAction(DriveBaseSubsystem driveBase, int distance) {
        this.driveBase = driveBase;
        this.distance = distance;
    }

    @Override
    public void doAction() {
        this.driveBase.reset();
        this.driveBase.gotoPosition(this.distance);
        long start = System.currentTimeMillis();
        long i = 0;
        while (true) {
            if (i % 10000 == 0) {
                long now = System.currentTimeMillis();
                SmartDashboard.putString("Debug", String.valueOf(now - start));
                this.driveBase.debug();
            }

            i++;
        }
    }
}