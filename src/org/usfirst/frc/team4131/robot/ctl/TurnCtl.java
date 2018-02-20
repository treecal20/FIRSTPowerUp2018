package org.usfirst.frc.team4131.robot.ctl;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SPI;

/**
 * A turn controller used to ensure accurate rotation using
 * the navX onboard controller.
 * <p>See the usage of this class in {@link org.usfirst.frc.team4131.robot.auto.action.TurnAction}</p>.
 *
 * @see #getInstance()
 */
public class TurnCtl implements PIDOutput {
    /** The singleton instance of the turn controller */
    private static final TurnCtl INSTANCE = new TurnCtl();

    // Internal navX and PID control device
    private final AHRS dev;
    private final PIDController controller;

    // Exposed methods used for supplying turn actions
    private boolean isTurning;
    private double throttleDelta;

    private TurnCtl() {
        this.dev = new AHRS(SPI.Port.kMXP);

        PIDController controller = new PIDController(.01, 0, 0, 0, this.dev, this);
        controller.setInputRange(-180, 180);
        controller.setOutputRange(-1, 1);
        controller.setAbsoluteTolerance(1);
        controller.setContinuous(true);
        controller.disable();
        this.controller = controller;
    }

    /**
     * Obtains the singleton instance of the navX turn
     * controller.
     *
     * @return the turn controller wrapper
     */
    public static TurnCtl getInstance() {
        return INSTANCE;
    }

    /**
     * Obtains the yaw read off of the navX.
     *
     * @return the yaw
     */
    public double getYaw() {
        return this.dev.getYaw();
    }

    /**
     * Begins the PID procedure. Must be placed before a
     * polling loop in order to cause the controller to
     * supply the correct throttle deltas.
     *
     * @param delta the angle which to turn towards between
     * {@code -180 <= delta <= 180}
     */
    public void begin(double delta) {
        if (!this.isTurning) {
            this.dev.zeroYaw();
            while (Math.abs(this.dev.getYaw()) < 0.2);

            this.isTurning = true;
            this.controller.setSetpoint(delta);
            this.throttleDelta = 0;
            this.controller.enable();
        }
    }

    /**
     * Obtains the throttle delta value that is produced by
     * running the PID function.
     *
     * @return the throttle
     */
    public double getDelta() {
        return this.throttleDelta;
    }


    /**
     * Determines whether the target has been reached.
     * <p>This method does not detect whether or not the
     * PID operation has completed or not, so be cautious
     * to continue polling until this method returns
     * consistently.</p>
     *
     * @return {@code true} if the turn control comes within
     * bound of the target angle, {@code false} if it is
     * still outside
     */
    public boolean targetReached() {
        return this.controller.onTarget();
    }

    /**
     * Completes PID control, should be called at the end
     * of a polling loop in order to release the controller
     * for other turning processes.
     */
    public void finish() {
        if (this.isTurning) {
            this.isTurning = false;
            this.controller.disable();
        }
    }

    @Override
    public void pidWrite(double output) {
        this.throttleDelta = output;
    }
}