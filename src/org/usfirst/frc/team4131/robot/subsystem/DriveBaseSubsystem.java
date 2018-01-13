package org.usfirst.frc.team4131.robot.subsystem;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4131.robot.RobotMap;
import org.usfirst.frc.team4131.robot.commands.MoveCommand;

public class DriveBaseSubsystem extends Subsystem {
    // Control mode used to determine the drive speed
    private static final ControlMode CTL = ControlMode.PercentOutput;

    // Sensor constants
    private static final int PID_IDX = 0;
    private static final int SENSOR_TIMEOUT = 0;

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
    	ErrorCode code = ErrorCode.worstOne(talon.config_kP(0, PID_IDX, SENSOR_TIMEOUT),
    			talon.config_kI(0, PID_IDX, SENSOR_TIMEOUT),
    			talon.config_kD(0, PID_IDX, SENSOR_TIMEOUT),
    			talon.config_kF(0, PID_IDX, SENSOR_TIMEOUT));
    	if (code.value != 0) {
    		DriverStation.reportError("Error occurred configuring PIDF", false);
    	}
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

    public void doMove(double l, double r) {
        this.l1.set(CTL, l);
        this.l2.set(CTL, l);
        this.r1.set(CTL, -r);
        this.r2.set(CTL, -r);
    }

    public int getLeftDist() {
        return this.l1.getSelectedSensorPosition(PID_IDX);
    }

    public int getRightDist() {
        return this.r1.getSelectedSensorPosition(PID_IDX);
    }

    public void debug() {
        int l1ss = this.l1.getSelectedSensorPosition(PID_IDX);
        int l1cs = this.r1.getSelectedSensorPosition(PID_IDX);

        System.out.printf("L: %s === R: %s%n", l1ss, l1cs);
    }
}