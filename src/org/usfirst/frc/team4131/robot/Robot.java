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
import org.usfirst.frc.team4131.robot.auto.procedure.Move12;
import org.usfirst.frc.team4131.robot.auto.procedure.Move12ThenTurn90;
import org.usfirst.frc.team4131.robot.auto.procedure.Turn90;
import org.usfirst.frc.team4131.robot.subsystem.DriveBaseSubsystem;
import org.usfirst.frc.team4131.robot.subsystem.SubsystemProvider;

import java.util.ArrayList;
import java.util.Arrays;
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
        this.chooser.addDefault("Move 12 then Turn 90", new Move12ThenTurn90());
        this.chooser.addObject("Turn 90 Degrees", new Turn90());
        this.chooser.addObject("Move 12 Inches", new Move12());
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
        List<Action> actions = new ArrayList<>(procedure.estimateLen());
        procedure.populate(this.provider, Arrays.asList(sides), actions);

        for (int i = 0, s = actions.size(); i < s; i++) {
            Action action = actions.get(i);
            action.doAction();
        }
    }

    // HUMAN-OPERATED --------------------------------------

    @Override
    public void teleopInit() {
        DriveBaseSubsystem driveBase = this.provider.getDriveBase();
        while (driveBase.getLeftDist() < 10000) {
            driveBase.doThrottle(.1, .1);
        }
        driveBase.doThrottle(0, 0);
        try {
            Thread.sleep(2000);
            System.out.println("CALIBRATE DONE");
            driveBase.debug();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
}