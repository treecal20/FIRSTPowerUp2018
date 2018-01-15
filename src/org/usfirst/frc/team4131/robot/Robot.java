/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4131.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4131.robot.auto.Action;
import org.usfirst.frc.team4131.robot.auto.Procedure;
import org.usfirst.frc.team4131.robot.auto.Side;
import org.usfirst.frc.team4131.robot.auto.procedure.SampleProcedure;
import org.usfirst.frc.team4131.robot.subsystem.DriveBaseSubsystem;
import org.usfirst.frc.team4131.robot.subsystem.SubsystemProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Robot lifecycle handler.
 */
public class Robot extends IterativeRobot {
    private SubsystemProvider provider;
    private SendableChooser<Procedure> chooser = new SendableChooser<>();

    @Override
    public void robotInit() {
        // Run init block for OI
        // TODO: If no init needed, remove this
        Oi.init();

        // Init subsystems
        this.provider = new SubsystemProvider(new DriveBaseSubsystem());

        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setVideoMode(new VideoMode(VideoMode.PixelFormat.kMJPEG, 600, 600, 10));

        // TODO: Register auton Commands to chooser
        this.chooser.addDefault("Test Auto", new SampleProcedure());
        SmartDashboard.putData("Auto mode", this.chooser);
    }

    // DISABLED MODE ---------------------------------------

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    // AUTONOMOUS ------------------------------------------

    @Override
    public void autonomousInit() {
        String str = DriverStation.getInstance().getGameSpecificMessage();
        Side[] sides = new Side[str.length()];
        for (int i = 0, s = str.length(); i < s; i++) {
            sides[i] = Side.decode(str.charAt(i));
        }

        Procedure procedure = this.chooser.getSelected();
        this.chooser = null; // Free memory

        procedure.init(sides);

        List<Action> actions = new ArrayList<>(procedure.estimateLen());
        procedure.populate(this.provider, actions);
        // this.provider = null; // Free unneeded references

        for (int i = 0, s = actions.size(); i < s; i++) {
            Action action = actions.get(i);
            action.doAction();
        }
    }

    // HUMAN-OPERATED --------------------------------------

    @Override
    public void teleopInit() {
        this.provider.getDriveBase().debug();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
}