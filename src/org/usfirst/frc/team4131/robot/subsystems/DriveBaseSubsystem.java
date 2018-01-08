package org.usfirst.frc.team4131.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4131.robot.RobotMap;
import org.usfirst.frc.team4131.robot.commands.MoveCommand;

public class DriveBaseSubsystem extends Subsystem {
    private final CANTalon l1;
    private final CANTalon l2;
    private final CANTalon r1;
    private final CANTalon r2;

    public DriveBaseSubsystem() {
        this.l1 = new CANTalon(RobotMap.L1);
        this.l2 = new CANTalon(RobotMap.L2);
        this.r1 = new CANTalon(RobotMap.R1);
        this.r2 = new CANTalon(RobotMap.R2);
    }

    @Override
    protected void initDefaultCommand() {
        this.setDefaultCommand(new MoveCommand(this));
    }

    public void doMove(double l, double r) {
        this.l1.set(ControlMode.Velocity, l);
        this.l2.set(ControlMode.Velocity, l);
        this.r1.set(ControlMode.Velocity, -r);
        this.r2.set(ControlMode.Velocity, -r);
    }
}