package org.usfirst.frc.team4131.robot.command;

import org.usfirst.frc.team4131.robot.Oi;
import org.usfirst.frc.team4131.robot.Robot;
import org.usfirst.frc.team4131.robot.subsystem.DriveBaseSubsystem;

/**
 * A command that allows joystick control of the drive base
 */
public class MoveCommand extends SingleSubsystemCmd<DriveBaseSubsystem> {
    public MoveCommand(DriveBaseSubsystem subsystem) {
        super(subsystem);
    }

    private static double getLeft() {
        if (Robot.isInverted) {
            return Oi.R_JOYSTICK.getRawAxis(1);
        } else {
            return -Oi.L_JOYSTICK.getRawAxis(1);
        }
    }

    private static double getRight() {
        if (Robot.isInverted) {
            return -Oi.L_JOYSTICK.getRawAxis(1);
        } else {
            return Oi.R_JOYSTICK.getRawAxis(1);
        }
    }

    // CLEAN UP --------------------------------------------

    @Override
    protected void execute() {
        this.subsystem.doThrottle(getLeft(), getRight());
    }

    // JOYSTICK --------------------------------------------

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void interrupted() {
        this.subsystem.doThrottle(0, 0);
    }
}