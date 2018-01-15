package org.usfirst.frc.team4131.robot.command;

import org.usfirst.frc.team4131.robot.Oi;
import org.usfirst.frc.team4131.robot.subsystem.DriveBaseSubsystem;

public class MoveCommand extends SingleSubsystemCmd<DriveBaseSubsystem> {
    public MoveCommand(DriveBaseSubsystem subsystem) {
        super(subsystem);
    }

    @Override
    protected void execute() {
        this.subsystem.doThrottle(getLeft(), getRight());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    // CLEAN UP --------------------------------------------

    @Override
    protected void end() {
        this.subsystem.doThrottle(0, 0);
    }

    @Override
    protected void interrupted() {
        this.subsystem.doThrottle(0, 0);
    }

    // JOYSTICK --------------------------------------------

    private static double getLeft() {
        return constrain(Oi.L_JOYSTICK.getRawAxis(1));
    }

    private static double getRight() {
        return constrain(Oi.R_JOYSTICK.getRawAxis(1));
    }

    private static double constrain(double d) {
        return Math.max(-1, Math.min(1, d));
    }
}