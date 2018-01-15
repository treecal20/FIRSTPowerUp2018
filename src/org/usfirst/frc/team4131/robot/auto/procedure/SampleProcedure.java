package org.usfirst.frc.team4131.robot.auto.procedure;

import org.usfirst.frc.team4131.robot.auto.Action;
import org.usfirst.frc.team4131.robot.auto.DistanceMoveAction;
import org.usfirst.frc.team4131.robot.auto.Procedure;
import org.usfirst.frc.team4131.robot.subsystem.SubsystemProvider;

import java.util.List;

/**
 * A sample procedure which drives the robot 10000 inches.
 */
public class SampleProcedure extends Procedure {
    @Override
    public int estimateLen() {
        return 1;
    }

    @Override
    public void populate(SubsystemProvider provider, List<Action> procedure) {
        procedure.add(new DistanceMoveAction(provider.getDriveBase(), 20000));
    }
}