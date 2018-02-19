package org.usfirst.frc.team4131.robot.command;

import org.usfirst.frc.team4131.robot.Oi;
import org.usfirst.frc.team4131.robot.Robot;
import org.usfirst.frc.team4131.robot.subsystem.ClimberSubsystem;

/**
 * A command which will activate the climber and raise the
 * robot using the pull-up bar.
 */
public class ClimbCommand extends SingleSubsystemCmd<ClimberSubsystem> {
    public ClimbCommand(ClimberSubsystem subsystem) {
        super(subsystem);
    }

    private static boolean up() {
        return Oi.CLIMB.get();
    }

    private static boolean down() {
        return Oi.DESCEND.get();
    }

    @Override
    protected void execute() {
    	if (up() && down()) {
            this.subsystem.doStop();
        } else if (up()) {

            if (!Robot.isClimberTop) {
                this.subsystem.doStop();
            }
            else {
                this.subsystem.doClimb();
            }
        } else if (down()) {
            if (!Robot.isClimberBottom) {
                this.subsystem.doStop();
            }
            else {
                this.subsystem.doLower();
            }
        } else {
            this.subsystem.doStop();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void interrupted() {
        this.subsystem.doStop();
    }
}