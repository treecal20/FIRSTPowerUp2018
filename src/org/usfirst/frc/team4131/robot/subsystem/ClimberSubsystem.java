package org.usfirst.frc.team4131.robot.subsystem;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4131.robot.command.ClimbCommand;

/**
 * Links control of the climbing mechanism.
 */
public class ClimberSubsystem extends Subsystem {
    /**
     * Initializes and caches the climbing mechanism motors.
     */
    public ClimberSubsystem() {
    }

    @Override
    protected void initDefaultCommand() {
        this.setDefaultCommand(new ClimbCommand(this));
    }

    /**
     * Performs the climbing action and hoists the robot
     * up the pull-up bar.
     */
    public void doClimb() {
    }
}