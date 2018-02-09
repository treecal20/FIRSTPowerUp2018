package org.usfirst.frc.team4131.robot.auto.action;

import org.usfirst.frc.team4131.robot.Oi;
import org.usfirst.frc.team4131.robot.auto.Action;
import org.usfirst.frc.team4131.robot.ctl.DriveCtl;
import org.usfirst.frc.team4131.robot.ctl.TurnCtl;
import org.usfirst.frc.team4131.robot.subsystem.DriveBaseSubsystem;

/**
 * An action that will cause the robot to be moved a
 * certain given distance, in inches.
 */
public class DistanceMoveAction implements Action {
    /**
     * The number of polls in loop to reasonably declare
     * PID victory
     */
    private static final int V_GRANULARITY = 500;

    /** The drive base used to move the robot */
    private final DriveBaseSubsystem driveBase;
    /** The number of ticks to move the robot */
    private final int distance;

    /**
     * Creates a new action that will move the robot the
     * given distance, with a velocity of 0.3
     *
     * @param driveBase the robot drive base
     * @param distance the distance, in inches
     */
    public DistanceMoveAction(DriveBaseSubsystem driveBase, double distance) {
        this.driveBase = driveBase;
        this.distance = (int) (74.9151910531 * distance);
    }

    @Override
    public void doAction() {
    	this.driveBase.reset();
    	
    	DriveCtl ctl = this.driveBase.getCtl();
        TurnCtl turnCtl = TurnCtl.getInstance();

        int turns = 0;
        
        turnCtl.begin(0);
        ctl.begin(this.distance);
        while (true) {
            double baseDelta = -ctl.getDelta();
            double turnDelta = turnCtl.getDelta();

            double leftDelta = this.constrain(baseDelta + (-Oi.sigl() * turnDelta));
            this.driveBase.setVelocityLeft(leftDelta);
            
            double rightDelta = constrain(baseDelta - (-Oi.sigr() * turnDelta));
            this.driveBase.setVelocityRight(rightDelta);
            
            if (ctl.onTarget()) {
            	turns++;
            } else {
            	turns = 0;
            }
            
            if (turns > V_GRANULARITY) {
            	break;
            }
        }
        ctl.finish();
        turnCtl.finish();
        
        this.driveBase.doThrottle(0, 0);
    }
    
    private double constrain(double d) {
    	return Math.abs(d) < 0.1 ? Math.signum(d) * 0.1 : d;
    }
}