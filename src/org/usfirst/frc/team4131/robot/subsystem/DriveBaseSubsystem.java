package org.usfirst.frc.team4131.robot.subsystem;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4131.robot.RobotMap;
import org.usfirst.frc.team4131.robot.command.MoveCommand;

public class DriveBaseSubsystem extends Subsystem {
    // Sensor constants
    private static final int PID_IDX = 0;
    private static final int SENSOR_TIMEOUT = 0;

    private static final int ERROR_DELTA = 0;

    // Physical drive mappings
    private final TalonSRX l1;
    private final TalonSRX l2;
    private final TalonSRX r1;
    private final TalonSRX r2;

    public DriveBaseSubsystem() {
        this.l1 = new TalonSRX(RobotMap.L1);
        this.l2 = new TalonSRX(RobotMap.L2);
        this.r1 = new TalonSRX(RobotMap.R1);
        this.r2 = new TalonSRX(RobotMap.R2);

        ErrorCode code = ErrorCode.worstOne(this.l1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0),
                this.l2.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_IDX, SENSOR_TIMEOUT),
                this.r1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_IDX, SENSOR_TIMEOUT),
                this.r2.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_IDX, SENSOR_TIMEOUT));
        if (code.value != 0) {
            DriverStation.reportError("Error occurred configuring quad encoders", false);
        }

        ErrorCode code0 = ErrorCode.worstOne(this.l1.setSelectedSensorPosition(0, PID_IDX, SENSOR_TIMEOUT),
                this.l2.setSelectedSensorPosition(0, PID_IDX, SENSOR_TIMEOUT),
                this.r1.setSelectedSensorPosition(0, PID_IDX, SENSOR_TIMEOUT),
                this.r2.setSelectedSensorPosition(0, PID_IDX, SENSOR_TIMEOUT));
        if (code0.value != 0) {
            DriverStation.reportError("Error occurred resetting quad encoders", false);
        }

        this.configPid(this.l1);
        this.configPid(this.l2);
        this.configPid(this.r1);
        this.configPid(this.r2);
    }

    @Override
    protected void initDefaultCommand() {
        this.setDefaultCommand(new MoveCommand(this));
    }

    private void configPid(TalonSRX talon) {
        ErrorCode code = ErrorCode.worstOne(talon.config_kP(PID_IDX, 4, SENSOR_TIMEOUT),
                talon.config_kI(PID_IDX, 0, SENSOR_TIMEOUT),
                talon.config_kD(PID_IDX, 0, SENSOR_TIMEOUT),
                talon.config_kF(PID_IDX, 0, SENSOR_TIMEOUT));
        ErrorCode code0 = talon.configAllowableClosedloopError(PID_IDX, ERROR_DELTA, SENSOR_TIMEOUT);
        // talon.configVoltageCompSaturation(5, SENSOR_TIMEOUT);
        // talon.enableVoltageCompensation(true);
        if (code.value != 0 || code0.value != 0) {
            DriverStation.reportError("Error occurred configuring PIDF", false);
        }
    }

    public void debug() {
        int l1ss = this.l1.getSelectedSensorPosition(PID_IDX);
        int l1cs = this.r1.getSelectedSensorPosition(PID_IDX);

        SmartDashboard.putString("Debug", String.format("L: %s === R: %s%n", l1ss, l1cs));
    }

    public void doThrottle(double l, double r) {
        this.l1.set(ControlMode.PercentOutput, l);
        this.l2.set(ControlMode.PercentOutput, l);
        this.r1.set(ControlMode.PercentOutput, -r);
        this.r2.set(ControlMode.PercentOutput, -r);
    }

    public void gotoPosition(int pos) {
        this.l1.set(ControlMode.Position, pos);
        this.l2.set(ControlMode.Position, pos);
        // TODO: Re-enable
        // this.r1.set(ControlMode.Position, pos);
        // this.r2.set(ControlMode.Position, pos);
    }

    public void reset() {
        ErrorCode code0 = ErrorCode.worstOne(this.l1.setSelectedSensorPosition(0, PID_IDX, SENSOR_TIMEOUT),
                this.l2.setSelectedSensorPosition(0, PID_IDX, SENSOR_TIMEOUT),
                this.r1.setSelectedSensorPosition(0, PID_IDX, SENSOR_TIMEOUT),
                this.r2.setSelectedSensorPosition(0, PID_IDX, SENSOR_TIMEOUT));
        if (code0.value != 0) {
            DriverStation.reportError("Error occurred resetting quad encoders", false);
        }
    }

    // DO NOT use SensorCollection here

    public int getLeftDist() {
        return this.l1.getSelectedSensorPosition(PID_IDX);
    }

    public int getRightDist() {
        return this.r1.getSelectedSensorPosition(PID_IDX);
    }
}