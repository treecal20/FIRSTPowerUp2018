package org.usfirst.frc.team4131.robot.auto.action;

import org.usfirst.frc.team4131.robot.Robot;
import org.usfirst.frc.team4131.robot.auto.Action;
import org.usfirst.frc.team4131.robot.ctl.TurnCtl;
import org.usfirst.frc.team4131.robot.subsystem.DriveBaseSubsystem;

import java.util.function.DoubleConsumer;

/**
 * A turn action causes the robot to rotate until the given
 * arguments have been reached.
 */
public class DriveStraight implements Action {
    /**
     * The amount of rounds in which the target turn angle
     * must be within bounds before the turn can exit.
     */
    private static final int V_GRANULARITY = 10;

    /** The drive base */
    private final DriveBaseSubsystem driveBase;
    /** The value to turn */
    private final double targetThrottle;
    private final double targetAngularDelta = 0;
    private final double targetL;
    private final double targetR;

    /**
     * Creates a new turning action that moves a specified
     * relative delta value.
     *
     * @param driveBase the drive base
     * @param delta the delta angle to turn
     */
    public DriveStraight(DriveBaseSubsystem driveBase, double targetThrottle) {
        this.driveBase = driveBase;
        this.targetThrottle = targetThrottle;
        this.targetL = targetThrottle;
        this.targetR = targetThrottle;
    }

    @Override
    public void doAction() {
        DoubleConsumer consumer = value -> {
            value *= 10; //tuning
            this.driveBase.doThrottle(this.targetThrottle - value, this.targetThrottle + value);
            double temp = value;
            Robot.debug(() -> String.valueOf(temp));
        };

        TurnCtl controller = TurnCtl.getInstance();
        controller.begin(this.targetAngularDelta);
        int roundsSinceChange = 0;
        this.driveBase.doThrottle(this.targetThrottle, this.targetThrottle);
        while (true) {
            Robot.debug(controller::getYaw);
            if (controller.targetReached()) {
                roundsSinceChange++;
            }
            /*
            if (roundsSinceChange >= V_GRANULARITY) {
                break;
            }*/

            controller.pollData(consumer);
        }
        //controller.finish();
        //this.driveBase.doThrottle(0, 0);
    }
}
