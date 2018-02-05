package org.usfirst.frc.team4131.robot.auto.action;

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
        Consumer consumer = new Consumer();

        DriveCtl ctl = this.driveBase.getCtl();
        TurnCtl turnCtl = TurnCtl.getInstance();

        turnCtl.begin(0);
        ctl.begin(this.ticks);
        while (true) {
            ctl.poll(consumer);
            turnCtl.pollData(consumer);

            double value = consumer.getValue();
            this.driveBase.setVelocity((int) value);

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