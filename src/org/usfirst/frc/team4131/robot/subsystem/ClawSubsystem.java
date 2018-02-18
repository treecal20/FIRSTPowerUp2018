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
	
	private final Solenoid clawOne, clawTwo, ejectOne, ejectTwo;
	
    public ClawSubsystem() {
    	clawOne = new Solenoid(RobotMap.PCM, RobotMap.CLAWONE);
    	clawTwo = new Solenoid(RobotMap.PCM, RobotMap.CLAWTWO);
    	ejectOne = new Solenoid(RobotMap.PCM, RobotMap.EJECTONE);
    	ejectTwo = new Solenoid(RobotMap.PCM, RobotMap.EJECTTWO);
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
    	clawOne.set(false);
    	clawTwo.set(true);
    }
    
    public void doRelease() {
    	clawOne.set(true);
    	clawTwo.set(false);
    }
    
    public void doEject() {
    	ejectOne.set(true);
    	ejectOne.set(false);
    }
    
    public void doRetract() {
    	ejectOne.set(false);
    	ejectTwo.set(true);
    }
}