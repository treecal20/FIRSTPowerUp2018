package org.usfirst.frc.team4131.robot.subsystem;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4131.robot.command.ClimbCommand;

public class ClimberSubsystem extends Subsystem {
    public ClimberSubsystem() {
    }

    @Override
    protected void initDefaultCommand() {
        this.setDefaultCommand(new ClimbCommand(this));
    }

    public void doClimb() {
    }
}