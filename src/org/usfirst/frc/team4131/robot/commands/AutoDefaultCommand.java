package org.usfirst.frc.team4131.robot.commands;

import org.usfirst.frc.team4131.robot.subsystems.DriveBaseSubsystem;

public class AutoDefaultCommand extends SingleSubsystemCmd<DriveBaseSubsystem> {
    private long start = -1;
    private long last;

    public AutoDefaultCommand(DriveBaseSubsystem subsystem) {
        super(subsystem);
    }

    @Override
    protected void initialize() {
        this.start = System.currentTimeMillis();
        this.last = System.currentTimeMillis();
    }

    @Override
    protected void execute() {
        long time = System.currentTimeMillis();
        System.out.println("ELAPSED INT: " + (time - this.last));
        this.last = time;

        this.subsystem.doMove(1, 1);
    }

    @Override
    protected boolean isFinished() {
        return System.currentTimeMillis() - this.start >= 5000L;
    }

    @Override
    protected void end() {
        this.subsystem.doMove(0, 0);
    }
}