package org.usfirst.frc.team4131.robot.nav;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SPI;

import java.util.function.DoubleConsumer;

public class TurnController implements PIDOutput {
    private final AHRS dev;
    private final PIDController controller;

    private DoubleConsumer cached;
    private boolean isTurning;
    private double throttleDelta;

    public TurnController() {
        this.dev = new AHRS(SPI.Port.kMXP);

        PIDController controller = new PIDController(.010, 0, 0, 0, this.dev, this);
        controller.setInputRange(-180, 180);
        controller.setOutputRange(-1, 1);
        // TODO: Value range
        // TODO: Configure tolerance
        controller.setAbsoluteTolerance(.2);
        controller.setContinuous(true);
        controller.disable();
        this.controller = controller;
    }

    public double getYaw() {
        return this.dev.getYaw();
    }

    public void begin(double delta) {
        if (!this.isTurning) {
            this.dev.zeroYaw();
            this.isTurning = true;
            this.cached = null;

            this.controller.setSetpoint(delta);
            this.throttleDelta = 0;
            this.controller.enable();
        }
    }

    public void pollData(DoubleConsumer dataConsumer) {
        if (this.cached != null) {
            if (this.cached != dataConsumer) {
                throw new RuntimeException("Incorrect usage of data polling");
            }
        } else {
            this.cached = dataConsumer;
        }

        dataConsumer.accept(this.throttleDelta);
    }

    public boolean hasFinished() {
        if (this.controller.onTarget()) {
            this.isTurning = false;
            this.controller.disable();
            return true;
        }

        return false;
    }

    @Override
    public void pidWrite(double output) {
        this.throttleDelta = output;
    }
}