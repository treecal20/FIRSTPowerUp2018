package org.usfirst.frc.team4131.robot.auto.procedure;

import org.usfirst.frc.team4131.robot.Oi;
import org.usfirst.frc.team4131.robot.auto.Action;
import org.usfirst.frc.team4131.robot.auto.Procedure;
import org.usfirst.frc.team4131.robot.auto.Side;
import org.usfirst.frc.team4131.robot.auto.action.DistanceMoveAction;
import org.usfirst.frc.team4131.robot.auto.action.FallbackDistanceMove;
import org.usfirst.frc.team4131.robot.auto.action.TurnAction;
import org.usfirst.frc.team4131.robot.subsystem.SubsystemProvider;

import java.util.List;

/**
 * A sample procedure which drives the robot 12 feet.
 */
public class LeftRightBaseline implements Procedure {
    @Override
    public int estimateLen() {
        return 2;
    }

    @Override
    public void populate(SubsystemProvider provider, List<Side> data, List<Action> procedure) {
<<<<<<< HEAD
        procedure.add(new DistanceMoveAction(provider.getDriveBase(), 100));
=======
        procedure.add(new DistanceMoveAction(provider.getDriveBase(), 60));
>>>>>>> dee077e3ce5544c139199c2bc4a658cc192e2a22
    }
}