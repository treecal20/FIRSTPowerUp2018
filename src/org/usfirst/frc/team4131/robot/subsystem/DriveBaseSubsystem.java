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

import org.usfirst.frc.team4131.robot.Oi;

/**
 * The drive base subsystem, linking the 4 Talon SRX
 * controllers and their respective motors.
 */
public class DriveBaseSubsystem extends Subsystem implements PIDSource {
    // Sensor constants
    private static final int PID_IDX = 0;
    private static final int SENSOR_TIMEOUT = 0;

    private static final int ERROR_DELTA = 0;

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
        TalonSRX right2 = new TalonSRX(RobotMap.R2);
        right2.follow(this.right);

        this.setupEncoder();
        this.reset();
        this.setupPid(this.left);
    }

    @Override
    protected void initDefaultCommand() {
        this.setDefaultCommand(new MoveCommand(this));
    }

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
     * Configures the PID constants on the given talon
     * device.
     *
     * @param talon the talon on which the PID constants
     * will be configured
     */
    private void setupPid(TalonSRX talon) {
        ErrorCode code = ErrorCode.worstOne(talon.config_kP(PID_IDX, 0.4, SENSOR_TIMEOUT),
                talon.config_kI(PID_IDX, 0, SENSOR_TIMEOUT),
                talon.config_kD(PID_IDX, 0, SENSOR_TIMEOUT),
                talon.config_kF(PID_IDX, 0, SENSOR_TIMEOUT));
        ErrorCode code0 = ErrorCode.worstOne(talon.configAllowableClosedloopError(PID_IDX, ERROR_DELTA, SENSOR_TIMEOUT),
                talon.configOpenloopRamp(0.3, SENSOR_TIMEOUT));
        if (code.value != 0 || code0.value != 0) {
            DriverStation.reportError("Error occurred configuring PIDF", false);
        }
    }

    /**
     * Print Amperage Voltage Velocity
     *
     * @param speed the speed currently being input
     * @param round the loop round
     */
    public void pavv(double speed, int round) {
        if (round % 100000 == 0) {
            System.out.println("INPUT=" + speed
                    + " ENCODER VEL=" + this.left.getSelectedSensorVelocity(PID_IDX)
                    + " AMPS=" + this.left.getOutputCurrent()
                    + " VOLTS=" + this.left.getBusVoltage());
        }
    }
    
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
        l = l * l * l;
        r = r * r * r;
        this.left.set(ControlMode.PercentOutput, sigl() * l);
        this.right.set(ControlMode.PercentOutput, sigr() * r);
    }
    
    public void setVelocityLeft(double vel) {
        this.left.set(ControlMode.PercentOutput, sigl() * vel);
    }
    
    public void setVelocityRight(double vel) {
        this.right.set(ControlMode.PercentOutput, sigr() * vel);
    }

    /**
     * Performs a PID-controlled movement using the
     * encoder target tick.
     *
     * @param ticks the number of ticks to move the
     * robot
     */
    public void gotoPosition(int ticks) {
        this.left.set(ControlMode.Position, ticks);
    }

    // Sensor polling methods ------------------------------
    // DO NOT use SensorCollection here

    public int getLeftVelocity() {
        return sigl() * this.left.getSelectedSensorVelocity(PID_IDX);
    }

    public int getRightVelocity() {
        return sigr() * this.right.getSelectedSensorVelocity(PID_IDX);
    }

    /**
     * Obtains the tick count for the left encoder.
     *
     * @return the ticks since the last reset travelled by
     * the left encoder
     */
    public int getLeftDist() {
        return this.left.getSelectedSensorPosition(PID_IDX);
    }

    public int getRightDist() {
        return this.right.getSelectedSensorPosition(PID_IDX);
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