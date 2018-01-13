package org.usfirst.frc.team4131.robot.commands;

import org.usfirst.frc.team4131.robot.subsystem.DriveBaseSubsystem;

public class AutoDefaultCommand extends SingleSubsystemCmd<DriveBaseSubsystem> {
    private long start;

    public AutoDefaultCommand(DriveBaseSubsystem subsystem) {
        super(subsystem);
    }

    @Override
    protected void initialize() {
        this.start = System.currentTimeMillis();
    }

    @Override
    protected void execute() {
        this.subsystem.doMove(.3, .3);
    }

    @Override
    protected boolean isFinished() {
        boolean b = this.subsystem.getLeftDist() >= 10000 ||
                this.subsystem.getRightDist() >= 1000;
        if (b) {
            this.subsystem.debug();
            this.subsystem.doMove(0, 0);
            this.subsystem.reset();
        }

        return false;
    }

    @Override
    protected void end() {
        this.subsystem.doMove(0, 0);
    }
}