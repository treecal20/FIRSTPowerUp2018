package org.usfirst.frc.team4131.robot.auto.action;

import org.usfirst.frc.team4131.robot.Robot;
import org.usfirst.frc.team4131.robot.auto.Action;
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
	private static final int V_GRANULARITY = 200;
	/**
	 * The amount of acceptable error for the target tick
	 * distance
	 */
	private static final int ERR_BOUND = 30;

	/** The drive base used to move the robot */
	private final DriveBaseSubsystem driveBase;
	/** The number of ticks used to move the left wheel */
	private final int leftDist;
	/** The number of ticks used to move the right wheel */
	private final int rightDist;

	/**
	 * Creates a new action that will move the robot the
	 * given distance, with a velocity of 0.3
	 *
	 * @param driveBase the robot drive base
	 * @param distance the distance, in inches
	 */
	public DistanceMoveAction(DriveBaseSubsystem driveBase, double distance) {
		this.driveBase = driveBase;
		this.leftDist = (int) (74.9151910531 * distance);
		this.rightDist = (int) (75.5747883349 * distance);
	}

	@Override
	public void doAction() {
		this.driveBase.reset();

		int currentVelocity = 100;
		this.driveBase.setVelocity(currentVelocity);

		int tLeft = this.driveBase.getLeftVelocity();
		int tRight = this.driveBase.getRightVelocity();
		int roundsSinceLastChange = 0;
		while (true) {
			int nLeft = this.driveBase.getLeftVelocity();
			int nRight = this.driveBase.getRightVelocity();
			boolean hasChanged = false;
			
			Robot.debug(() -> String.valueOf(nLeft + " " + nRight));

			int lDiff = Math.abs(nLeft - tLeft);
			if (lDiff > ERR_BOUND) {
				tLeft = nLeft;
				roundsSinceLastChange = 0;
				hasChanged = true;
			}

			int rDiff = Math.abs(nRight - tRight);
			if (rDiff > ERR_BOUND) {
				tRight = nRight;
				roundsSinceLastChange = 0;
			} else if (!hasChanged) {
				roundsSinceLastChange++;
			}

			// Victory
			if (roundsSinceLastChange >= V_GRANULARITY) {
				int velocity = currentVelocity;

				if (this.driveBase.getLeftDist() >= this.leftDist / 2 ||
						this.driveBase.getRightDist() >= this.rightDist / 2) {
					if (velocity == 1000) {
						velocity = 500;
					} else if (velocity == 500) {
						velocity = 100;
					} else if (velocity == 100) {
						break;
					}
				} else {
					if (velocity == 100) {
						velocity = 500;
					} else if (velocity == 500) {
						velocity = 1000;
					}
				}
				currentVelocity = velocity;
			}
			this.driveBase.setVelocity(currentVelocity);
			boolean leftSlow = Math.abs(this.driveBase.getLeftVelocity()) < Math.abs(this.driveBase.getRightVelocity());
			if(leftSlow) {
				this.driveBase.setVelocityRight(this.driveBase.getLeftVelocity());
				this.driveBase.setVelocityLeft(currentVelocity);
			}else {
				this.driveBase.setVelocityLeft(this.driveBase.getRightVelocity());
				this.driveBase.setVelocityRight(currentVelocity);
			}
		}
	}
}