package org.usfirst.frc.team4131.robot.subsystem;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team4131.robot.RobotMap;
import org.usfirst.frc.team4131.robot.command.ClawCommand;

/**
 * Links control for the claw-related motors.
 */
public class ClawSubsystem extends Subsystem {
    /**
     * Initializes and caches the motor wrapper for the claw
     * subsystem
     */
	
	private final Solenoid claw, eject;
	
    public ClawSubsystem() {
    	claw = new Solenoid(RobotMap.PCM, RobotMap.CLAW);
    	eject = new Solenoid(RobotMap.PCM, RobotMap.EJECT);
    }

    @Override
    protected void initDefaultCommand() {
        this.setDefaultCommand(new ClawCommand(this));
    }

    /**
     * Performs the clamping action in order to pick up a
     * power cube
     */
    public void doClamp() {
    	claw.set(true);
    }
    
    public void doRelease() {
    	claw.set(false);
    }
    
    public void doEject() {
    	eject.set(true);
    }
    
    public void doRetract() {
    	eject.set(false);
    }
}