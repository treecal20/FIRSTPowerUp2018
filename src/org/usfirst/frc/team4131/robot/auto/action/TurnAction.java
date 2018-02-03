package org.usfirst.frc.team4131.robot.auto.action;

import org.usfirst.frc.team4131.robot.Robot;
import org.usfirst.frc.team4131.robot.auto.Action;
import org.usfirst.frc.team4131.robot.nav.TurnController;
import org.usfirst.frc.team4131.robot.subsystem.DriveBaseSubsystem;

import java.util.function.DoubleConsumer;

/**
 * A turn action causes the robot to rotate until the given
 * arguments have been reached.
 */
public class TurnAction implements Action {
    /**
     * The amount of rounds in which the target turn angle
     * must be within bounds before the turn can exit.
     */
    private static final int V_GRANULARITY = 10;

    /** The drive base */
    private final DriveBaseSubsystem driveBase;
    /** The value to turn */
    private final float delta;

    /**
     * Creates a new turning action that moves a specified
     * relative delta value.
     *
     * @param driveBase the drive base
     * @param delta the delta angle to turn
     */
    public TurnAction(DriveBaseSubsystem driveBase, float delta) {
        this.driveBase = driveBase;
        this.delta = delta;
    }

    @Override
    public void doAction() {
        DoubleConsumer consumer = value -> {
            value = value < 0.1 ? 0.1 : value;
            this.driveBase.doThrottle(value, -value);
        };

        TurnController controller = TurnController.getInstance();
        controller.begin(this.delta);
        int roundsSinceChange = 0;
        while (true) {
            Robot.debug(controller::getYaw);
            if (controller.targetReached()) {
                roundsSinceChange++;
            }

            if (roundsSinceChange >= V_GRANULARITY) {
                break;
            }

            controller.pollData(consumer);
        }
        controller.finish();
        this.driveBase.doThrottle(0, 0);
    }
}