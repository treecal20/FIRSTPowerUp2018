package org.usfirst.frc.team4131.robot.subsystem;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team4131.robot.RobotMap;
import org.usfirst.frc.team4131.robot.command.ElevatorCommand;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * Links control of the climbing mechanism.
 */
public class ElevatorSubsystem extends Subsystem {
    /**
     * Initializes and caches the climbing mechanism motors.
     */
    
	private final TalonSRX motor;
	
	public ElevatorSubsystem() {
		 this.motor = new TalonSRX(RobotMap.E);
	}

    @Override
    protected void initDefaultCommand() {
        this.setDefaultCommand(new ElevatorCommand(this));
    }

    /**
     * Performs the climbing action and hoists the robot
     * up the pull-up bar.
     */
    public void doMove(boolean upDown) {
    	if (upDown) {
    		this.motor.set(ControlMode.PercentOutput, 0.5);
    	} else {
    		this.motor.set(ControlMode.PercentOutput, -0.5);
    	}
    }
    
    public void doStop() {
    	this.motor.set(ControlMode.PercentOutput, 0);
    }
}