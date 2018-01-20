package org.usfirst.frc.team4131.robot.auto.procedure;

import org.usfirst.frc.team4131.robot.auto.*;
import org.usfirst.frc.team4131.robot.auto.action.DistanceMoveAction;
import org.usfirst.frc.team4131.robot.auto.action.TurnAction;
import org.usfirst.frc.team4131.robot.subsystem.DriveBaseSubsystem;
import org.usfirst.frc.team4131.robot.subsystem.SubsystemProvider;

import java.util.List;

/**
 * A sample procedure that moves the robot forward 12 inches
 * and then turns 90 degrees to the right.
 */
public class Move12ThenTurn90 implements Procedure {
    @Override
    public int estimateLen() {
        return 2;
    }

    @Override
    public void populate(SubsystemProvider provider, List<Side> data, List<Action> procedure) {
        DriveBaseSubsystem drive = new DriveBaseSubsystem();
        procedure.add(new DistanceMoveAction(drive, 12));
        procedure.add(new TurnAction(drive, 90));
    }
}