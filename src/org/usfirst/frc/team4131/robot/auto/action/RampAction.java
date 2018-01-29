package org.usfirst.frc.team4131.robot.auto.action;

import org.usfirst.frc.team4131.robot.auto.Action;
import org.usfirst.frc.team4131.robot.subsystem.DriveBaseSubsystem;

/**
 * A ramping action used to test the drive base and output
 * metrics about amperage, velocity, and voltage used by
 * the talons.
 */

public class RampAction implements Action {

	/** The drive base */
    private final DriveBaseSubsystem driveBase;
    
    /** The direction */
    private final int sigint;
    
    /** The current input */
    private double cur;

    /**
     * Creates a new ramping action that will output
     * metrics used by the talons.
     *
     * @param driveBase the drive base to test
     */
    public RampAction(DriveBaseSubsystem driveBase, int sigint) {
        this.driveBase = driveBase;
        this.sigint = sigint;
    }

    @Override
    public void doAction() {
        DriveBaseSubsystem base = this.driveBase;
        int rounds = 1000000;
        for (int i = 0; i < rounds * 10; i++) {
            base.doThrottle(this.cur, this.cur);
            base.pavv(this.cur, i);

            if (i % rounds == 0) {
                this.cur += Math.copySign(.1, this.sigint);
            }
        }
    }
}