package org.usfirst.frc.team4131.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4131.robot.RobotMap;
import org.usfirst.frc.team4131.robot.command.ClimbCommand;

/**
 * Links control of the climbing mechanism.
 */
public class ClimberSubsystem extends Subsystem {
    /**
     * Initializes and caches the climbing mechanism motors.
     */

    private final TalonSRX one;
    private final TalonSRX two;

    public ClimberSubsystem() {
        this.one = new TalonSRX(RobotMap.C1);
        this.two = new TalonSRX(RobotMap.C2);
    }

    @Override
    protected void initDefaultCommand() {
        this.setDefaultCommand(new ClimbCommand(this));
    }

    /**
     * Performs the climbing action and hoists the robot
     * up the pull-up bar.
     */
    public void doClimb(boolean upDown) {
        if (upDown) {
            this.one.set(ControlMode.PercentOutput, 0.5);
            this.two.set(ControlMode.PercentOutput, 0.5);
        } else {
            this.one.set(ControlMode.PercentOutput, -0.5);
            this.two.set(ControlMode.PercentOutput, -0.5);
        }
    }

    public void doStop() {
        this.one.set(ControlMode.PercentOutput, 0);
        this.two.set(ControlMode.PercentOutput, 0);
    }
}