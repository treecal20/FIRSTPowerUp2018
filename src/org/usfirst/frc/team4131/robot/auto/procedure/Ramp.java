package org.usfirst.frc.team4131.robot.auto.procedure;

import org.usfirst.frc.team4131.robot.auto.Action;
import org.usfirst.frc.team4131.robot.auto.Procedure;
import org.usfirst.frc.team4131.robot.auto.Side;
import org.usfirst.frc.team4131.robot.auto.action.RampAction;
import org.usfirst.frc.team4131.robot.subsystem.SubsystemProvider;

import java.util.List;

/**
 * Ramping procedure
 */
public class Ramp implements Procedure {
    @Override
    public void populate(SubsystemProvider provider, List<Side> data, List<Action> procedure) {
        procedure.add(new RampAction(provider.getDriveBase(), 1));
        procedure.add(new RampAction(provider.getDriveBase(), -1));
        procedure.add(new RampAction(provider.getDriveBase(), -1));
        procedure.add(new RampAction(provider.getDriveBase(), 1));
    }
}