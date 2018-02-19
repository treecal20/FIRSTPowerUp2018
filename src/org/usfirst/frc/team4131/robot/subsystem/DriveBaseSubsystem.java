package org.usfirst.frc.team4131.robot.subsystem;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4131.robot.RobotMap;
import org.usfirst.frc.team4131.robot.command.MoveCommand;
import org.usfirst.frc.team4131.robot.ctl.DriveCtl;

import static org.usfirst.frc.team4131.robot.Oi.sigl;
import static org.usfirst.frc.team4131.robot.Oi.sigr;

/**
 * The drive base subsystem, linking the 4 Talon SRX
 * controllers and their respective motors.
 */
public class DriveBaseSubsystem extends Subsystem implements PIDSource {
    // Sensor constants
    private static final int PID_IDX = 0;
    private static final int SENSOR_TIMEOUT = 0;

    // Drive fallback PID controller
    private final DriveCtl ctl;

    // Physical drive mappings
    private final TalonSRX left;
    private final TalonSRX right;

    /**
     * Creates and caches the motors used for the drive base
     */
    public DriveBaseSubsystem() {
        this.ctl = new DriveCtl(this);

        this.left = new TalonSRX(RobotMap.L1);
        new TalonSRX(RobotMap.L2).follow(this.left);

        this.right = new TalonSRX(RobotMap.R1);
        new TalonSRX(RobotMap.R2).follow(this.right);

        this.setupEncoder();
        this.reset();
    }

    @Override
    protected void initDefaultCommand() {
        this.setDefaultCommand(new MoveCommand(this));
    }

    /**
     * Obtains the controller used for moving a certain
     * distance.
     *
     * @return the distance drive controller
     */
    public DriveCtl getCtl() {
        return this.ctl;
    }

    // Talon setup methods ---------------------------------

    /**
     * Configures the selected sensor to the quadrature
     * encoder on the left and right motors.
     */
    private void setupEncoder() {
        ErrorCode code = this.left.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_IDX, SENSOR_TIMEOUT);
        if (code.value != 0) {
            DriverStation.reportError("Error occurred configuring quad encoders", false);
        }
    }

    /**
     * Resets the position indicated by the quadencoders to
     * {@code 0}.
     */
    public void reset() {
        ErrorCode code = this.left.setSelectedSensorPosition(0, PID_IDX, SENSOR_TIMEOUT);
        if (code.value != 0) {
            DriverStation.reportError("Error occurred resetting quad encoders", false);
        }
    }

    /**
     * Reduces ramping in order to allow teleop control to
     * be more responsive.
     */
    public void prepareTeleop() {
        this.left.configOpenloopRamp(0, SENSOR_TIMEOUT);
        this.right.configOpenloopRamp(0, SENSOR_TIMEOUT);
    }

    // Talon control ---------------------------------------

    /**
     * Changes the speed of the motors to the given
     * constant output mode (-1 to 1).
     *
     * @param l the left motor speed
     * @param r the right motor speed
     */
    public void doThrottle(double l, double r) {
        this.left.set(ControlMode.PercentOutput, Math.pow((sigl() * l), 3));
        this.right.set(ControlMode.PercentOutput, Math.pow((sigr() * r), 3));
    }

    /**
     * Sets the throttle for the left motor.
     *
     * @param vel the throttle (-1 to 1)
     */
    public void setVelocityLeft(double vel) {
        this.left.set(ControlMode.PercentOutput, sigl() * vel);
    }

    /**
     * Sets the throttle for the right motor.
     *
     * @param vel the throttle (-1 to 1)
     */
    public void setVelocityRight(double vel) {
        this.right.set(ControlMode.PercentOutput, sigr() * vel);
    }

    // Sensor polling methods ------------------------------
    // DO NOT use SensorCollection here

    /**
     * Obtains the tick count for the left encoder.
     *
     * @return the ticks since the last reset travelled by
     * the left encoder
     */
    public int getLeftDist() {
        return this.left.getSelectedSensorPosition(PID_IDX);
    }

    // PID Fallback source methods -------------------------

    @Override
    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double pidGet() {
        return this.getLeftDist();
    }
}