package org.usfirst.frc.team4131.robot.subsystem;

/**
 * Provides subsystem wrapper singletons
 */
public class SubsystemProvider {
    private final DriveBaseSubsystem driveBase;
    private final ClawSubsystem claw;
    private final ClimberSubsystem climber;
    private final ElevatorSubsystem elevator;

    public SubsystemProvider(DriveBaseSubsystem driveBase,
                             ClawSubsystem claw,
                             ClimberSubsystem climber,
                             ElevatorSubsystem elevator) {
        this.driveBase = driveBase;
        this.claw = claw;
        this.climber = climber;
        this.elevator = elevator;
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

    public ElevatorSubsystem getElevator() {
        return this.elevator;
    }
}