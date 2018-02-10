package org.usfirst.frc.team4131.robot.ctl;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import org.usfirst.frc.team4131.robot.subsystem.DriveBaseSubsystem;

import java.util.function.DoubleConsumer;

public class DriveCtl implements PIDOutput {
    private final PIDController controller;

    private DoubleConsumer cached;
    private boolean isDriving;
    private double delta;

    public DriveCtl(DriveBaseSubsystem driveBase) {
        PIDController controller = new PIDController(.001, 0, 0, driveBase, this);
        controller.setInputRange(Integer.MIN_VALUE, Integer.MAX_VALUE);
        controller.setOutputRange(-0.7, 0.7);
        controller.setAbsoluteTolerance(30);
        controller.setContinuous(false);
        controller.disable();
        this.controller = controller;
    }

    public void begin(int ticks) {
        if (!this.isDriving) {
            this.isDriving = true;
            this.cached = null;

            this.delta = 0;
            this.controller.setSetpoint(ticks);
            this.controller.enable();
        }
    }

    public void finish() {
        if (this.isDriving) {
            this.isDriving = false;
            this.controller.disable();
        }
    }
    
    public double getDelta() {
    	return this.delta;
    }

    @Override
    public void pidWrite(double output) {
        this.delta = output;
    }
}