package org.usfirst.frc.team4131.robot.command;

import org.usfirst.frc.team4131.robot.Oi;
import org.usfirst.frc.team4131.robot.subsystem.ClawSubsystem;

/**
 * A command which will perform a clamp action in order to
 * pick up a power cube.
 */
// TODO: Complete
public class ClawCommand extends SingleSubsystemCmd<ClawSubsystem> {
    public ClawCommand(ClawSubsystem subsystem) {
        super(subsystem);
    }

    /**
     * Checks the claw button in order to determine whether
     * the claw should clamp.
     *
     * @return {@code true} to begin clamping
     */
    private static boolean buttonClaw() {
        return Oi.CLAW.get();
    }

    /**
     * Checks the eject button in order to determine whether
     * the robot should unclamp.
     *
     * @return {@code true} to eject the held crate
     */
    private static boolean buttonArm() {
        return Oi.ARM.get();
    }
    
    @Override
    protected void execute() {
       if (buttonClaw() && buttonArm()) {
    	   this.subsystem.release();
    	   this.subsystem.raise();
       } else if (buttonClaw()) {
    	   this.subsystem.release();
       } else if (buttonArm()) {
    	   this.subsystem.raise();
       } else {
    	   this.subsystem.clamp();
    	   this.subsystem.lower();
       }
    }
    
    @Override
    protected void interrupted() {
        this.subsystem.clamp();
        this.subsystem.lower();
    }
}