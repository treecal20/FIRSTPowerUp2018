package org.usfirst.frc.team4131.robot.subsystem;

/**
 * Provides subsystem wrapper singletons
 */
public class SubsystemProvider {
    private final DriveBaseSubsystem driveBase;
    private final ClawSubsystem claw;
    private final ClimberSubsystem climber;

    public SubsystemProvider(DriveBaseSubsystem driveBase,
                             ClawSubsystem claw,
                             ClimberSubsystem climber) {
        this.driveBase = driveBase;
        this.claw = claw;
        this.climber = climber;
    }

    public DriveBaseSubsystem getDriveBase() {
        return this.driveBase;
    }

    public ClawSubsystem getClaw() {
        return this.claw;
    }

    public ClimberSubsystem getClimber() {
        return this.climber;
    }
}