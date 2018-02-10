package org.usfirst.frc.team4131.robot.auto.action;

import org.usfirst.frc.team4131.robot.Oi;
import org.usfirst.frc.team4131.robot.Robot;
import org.usfirst.frc.team4131.robot.auto.Action;
import org.usfirst.frc.team4131.robot.ctl.DriveCtl;
import org.usfirst.frc.team4131.robot.ctl.TurnCtl;
import org.usfirst.frc.team4131.robot.subsystem.DriveBaseSubsystem;

import java.util.function.DoubleConsumer;

public class FallbackDistanceMove implements Action {
    private final DriveBaseSubsystem driveBase;
    private final int ticks;

    public FallbackDistanceMove(DriveBaseSubsystem driveBase, int ticks) {
        this.driveBase = driveBase;
        this.ticks = ticks;
    }

    @Override
    public void doAction() {
        FallbackDistanceMove.Consumer consumer = new FallbackDistanceMove.Consumer();

        DriveCtl ctl = this.driveBase.getCtl();
        TurnCtl turnCtl = TurnCtl.getInstance();

        turnCtl.begin(0);
        ctl.begin(this.ticks);
        while (true) {
        	double moveDelta = ctl.getDelta();
        	double turn = turnCtl.getDelta();
        	Robot.debug(() -> String.valueOf(moveDelta));
            this.driveBase.setVelocityLeft(moveDelta + (Oi.sigl() * turn));
            this.driveBase.setVelocityRight(moveDelta + (Oi.sigr() * turn));

            consumer.reset();
        }
    }

    private static class Consumer implements DoubleConsumer {
        private double value;

        @Override
        public void accept(double value) {
            this.value += value;
        }

        public void reset() {
            this.value = 0;
        }

        public double getValue() {
            return this.value;
        }
    }
}