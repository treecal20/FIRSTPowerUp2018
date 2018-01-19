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
import org.usfirst.frc.team4131.robot.subsystem.ClawSubsystem;
import org.usfirst.frc.team4131.robot.subsystem.ClimberSubsystem;
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
    private final SendableChooser<Procedure> chooser = new SendableChooser<>();

    @Override
    public void robotInit() {
        // Init subsystems
        this.provider = new SubsystemProvider(new DriveBaseSubsystem(),
                new ClawSubsystem(), new ClimberSubsystem());

        // Init camera
        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setVideoMode(new VideoMode(VideoMode.PixelFormat.kMJPEG, 600, 600, 10));

        this.chooser.addDefault("Move 12 then Turn 90", new Move12ThenTurn90());
        this.chooser.addObject("Turn 90 Degrees", new Turn90());
        this.chooser.addObject("Move 12 Inches", new Move12());
        SmartDashboard.putData("Auto mode", this.chooser);
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

    @Override
    public void autonomousPeriodic() {
    }

    // HUMAN-OPERATED --------------------------------------

    @Override
    public void teleopInit() {
        this.step(1);
        this.step(-1);
        this.step(-1);
        this.step(1);
    }

    public void step(int sigint) {
        DriveBaseSubsystem base = this.provider.getDriveBase();
        int rounds = 10000000;
        double cur = 0;
        for (int i = 0; i < rounds * 10; i++) {
            base.doThrottle(cur, cur);
            base.pavv(cur, i);

            if (i % rounds == 0) {
                cur += Math.copySign(.1, sigint);
            }
        }
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
}