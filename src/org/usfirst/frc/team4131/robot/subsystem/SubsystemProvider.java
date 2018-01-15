package org.usfirst.frc.team4131.robot.subsystem;

/**
 * Provides subsystem wrapper singletons
 */
// TODO Just pass around rather than accumulating (?)
public class SubsystemProvider {
    private final DriveBaseSubsystem driveBase;

    public SubsystemProvider(DriveBaseSubsystem driveBase) {
        this.driveBase = driveBase;
    }

    public DriveBaseSubsystem getDriveBase() {
        return this.driveBase;
    }
}