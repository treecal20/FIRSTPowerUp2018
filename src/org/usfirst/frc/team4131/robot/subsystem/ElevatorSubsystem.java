package org.usfirst.frc.team4131.robot.subsystem;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4131.robot.RobotMap;
import org.usfirst.frc.team4131.robot.command.ElevatorCommand;

/**
 * Links control of the climbing mechanism.
 */
public class ElevatorSubsystem extends Subsystem {
    /**
     * Initializes and caches the climbing mechanism motors.
     */

    private final Spark motor;

    public ElevatorSubsystem() {
        this.motor = new Spark(RobotMap.E);
    }

    @Override
    protected void initDefaultCommand() {
        this.setDefaultCommand(new ElevatorCommand(this));
    }

    /**
     * Performs the climbing action and hoists the robot
     * up the pull-up bar.
     */
    public void doClimb() {
    	this.motor.set(-0.5);
    }

<<<<<<< HEAD
        this.motor.set(0.5);
    }

    public void doLower() {

        this.motor.set(-0.5);
=======
    public void doLower() {
    	this.motor.set(0.5);
>>>>>>> e0e7d78e4d2901a955ef0f33dab6576bf2c8eee9
    }

    public void doStop() {
        this.motor.set(0);
    }
}