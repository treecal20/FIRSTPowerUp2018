package org.usfirst.frc.team4131.robot.subsystem;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team4131.robot.Robot;
import org.usfirst.frc.team4131.robot.RobotMap;
import org.usfirst.frc.team4131.robot.command.ElevatorCommand;

/**
 * Links control of the elevator, used to raise the claw.
 */
public class ElevatorSubsystem extends Subsystem {
    private final Spark motor;

    /**
     * Initializes and caches the climbing mechanism motor.
     */
    public ElevatorSubsystem() {
        this.motor = new Spark(RobotMap.E);
    }

    @Override
    protected void initDefaultCommand() {
        this.setDefaultCommand(new ElevatorCommand(this));
    }

    /**
     * Raises the claw.
     */
    public void raise() {
        this.motor.set(-0.5);
    }

    /**
     * Lowers the claw.
     */
    public void lower() {
        this.motor.set(0.5);
    }

    /**
     * Halts claw movement, but the claw may or may not move
     * depending on its position.
     */
    public void stop() {
        this.motor.set(0);
    }
    
    public void goToBottom() {
    	this.stop();
    	while (Robot.isElevatorBottom) {
    		this.lower();
    	}
    	this.stop();
    }
    
    public void goToTop() {
    	this.stop();
    	while (Robot.isElevatorTop) {
    		this.raise();
    	}
    	this.stop();
    }
}