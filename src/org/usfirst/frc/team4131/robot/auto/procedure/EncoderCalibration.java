package org.usfirst.frc.team4131.robot.auto.procedure;

import org.usfirst.frc.team4131.robot.Robot;
import org.usfirst.frc.team4131.robot.auto.Action;
import org.usfirst.frc.team4131.robot.auto.Procedure;
import org.usfirst.frc.team4131.robot.auto.Side;
import org.usfirst.frc.team4131.robot.subsystem.DriveBaseSubsystem;
import org.usfirst.frc.team4131.robot.subsystem.SubsystemProvider;

import java.util.List;

/**
 * Procedure used to calibrate the encoder units.
 */
public class EncoderCalibration implements Procedure {
    @Override
    public int estimateLen() {
        return 1;
    }

    @Override
    public void populate(SubsystemProvider provider, List<Side> data, List<Action> procedure) {
        procedure.add(() -> {
            int dist = 10000;
            double speed = 0.1;
            DriveBaseSubsystem base = provider.getDriveBase();
            base.reset();

            while (base.getLeftDist() < dist) {
                base.doThrottle(speed, 0);
                Robot.debug(() -> String.valueOf(base.getLeftDist()));
            }

            base.doThrottle(0, 0);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("FINAL LEFT: " + base.getLeftDist());

            base.reset();
            while (base.getRightDist() < dist) {
                base.doThrottle(0, speed);
                Robot.debug(() -> String.valueOf(base.getRightDist()));
            }

            base.doThrottle(0, 0);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("FINAL RIGHT: " + base.getRightDist());
        });
    }
}