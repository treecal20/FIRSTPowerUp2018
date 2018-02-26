package org.usfirst.frc.team4131.robot.subsystem;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team4131.robot.RobotMap;
import org.usfirst.frc.team4131.robot.command.ClawCommand;

/**
 * Links control for the claw-related motors.
 */
public class ClawSubsystem extends Subsystem {
    // Pneumatic devices
    private final Solenoid clawOne;
    private final Solenoid clawTwo;
    private final Solenoid armOne;
    private final Solenoid armTwo;

    /**
     * Initializes and caches the motor wrapper for the claw
     * subsystem
     */
    public ClawSubsystem() {
        this.clawOne = new Solenoid(RobotMap.PCM, RobotMap.CLAWONE);
        this.clawTwo = new Solenoid(RobotMap.PCM, RobotMap.CLAWTWO);
        this.armOne = new Solenoid(RobotMap.PCM, RobotMap.ARMONE);
        this.armTwo = new Solenoid(RobotMap.PCM, RobotMap.ARMTWO);
    }

    @Override
    protected void initDefaultCommand() {
        this.setDefaultCommand(new ClawCommand(this));
    }

    /**
     * Performs the clamping action in order to pick up a
     * power cube
     */
    public void clamp() {
        this.clawOne.set(false);
        this.clawTwo.set(true);
    }

    /**
     * Releases the crate held by the claw
     */
    public void release() {
        this.clawOne.set(true);
        this.clawTwo.set(false);
    }

    /**
     * Ejects the crate
     */
    public void lower() {
        this.armOne.set(true);
        this.armTwo.set(false);
    }

    /**
     * Causes the claw to retract
     */
    public void raise() {
    	this.armOne.set(false);
        this.armTwo.set(true);
    }
}