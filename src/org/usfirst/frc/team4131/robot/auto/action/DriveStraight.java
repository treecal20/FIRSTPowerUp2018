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
    private double targetL;
    private double targetR;
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
        targetL = targetThrottle;
        targetR = targetThrottle;
        		
    }

    @Override
    public void doAction() {
        DoubleConsumer consumer = value -> {
        	value *= 10; //tuning
            this.driveBase.doThrottle(targetThrottle - value, targetThrottle+value);
            double temp = value;
            Robot.debug(()->(String.valueOf(temp)));
        };

        TurnController controller = TurnController.getInstance();
        controller.begin(targetAngularDelta);
        int roundsSinceChange = 0;
        this.driveBase.doThrottle(targetThrottle, targetThrottle);
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
