package org.usfirst.frc.team4131.robot.ctl;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import org.usfirst.frc.team4131.robot.subsystem.DriveBaseSubsystem;

/**
 * Drivebase control used for distance movement.
 */
public class DriveCtl implements PIDOutput {
    /** The controller */
    private final PIDController controller;

    /** If the controller is being used */
    private boolean isDriving;
    /** The delta value to pass to consumers */
    private double delta;

    /**
     * Creates a new drive controller for the given
     * subsystem.
     *
     * @param driveBase the subsystem to read off PID input
     */
    public DriveCtl(DriveBaseSubsystem driveBase) {
        PIDController controller = new PIDController(.0005, 0, 0, driveBase, this);
        controller.setInputRange(Integer.MIN_VALUE, Integer.MAX_VALUE);
        controller.setOutputRange(-0.7, 0.7);
        controller.setAbsoluteTolerance(30);
        controller.setContinuous(false);
        controller.disable();
        this.controller = controller;
    }

    /**
     * Begins the PID loop to be set to the given target
     * number of encoder ticks.
     *
     * @param ticks the number of encoder ticks to move
     */
    public void begin(int ticks) {
        if (!this.isDriving) {
            this.isDriving = true;

            this.delta = 0;
            this.controller.setSetpoint(ticks);
            this.controller.enable();
        }
    }

    /**
     * The result of running the PID function.
     *
     * @return the throttle which to move the robot
     */
    public double getDelta() {
        return this.delta;
    }

    /**
     * Checks to determine whether the robot has reached its
     * target.
     *
     * @return {@code true} if the robot is on target
     */
    public boolean onTarget() {
        return this.controller.onTarget();
    }

    /**
     * Completes the loop control.
     */
    public void finish() {
        if (this.isDriving) {
            this.isDriving = false;
            this.controller.disable();
        }
    }
    
    @Override
    public void pidWrite(double output) {
        this.delta = output;
    }
}