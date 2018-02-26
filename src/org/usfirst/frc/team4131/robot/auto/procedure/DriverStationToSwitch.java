package org.usfirst.frc.team4131.robot.auto.procedure;

import edu.wpi.first.wpilibj.DriverStation;
import org.usfirst.frc.team4131.robot.auto.Action;
import org.usfirst.frc.team4131.robot.auto.Procedure;
import org.usfirst.frc.team4131.robot.auto.Side;
import org.usfirst.frc.team4131.robot.auto.action.DistanceMoveAction;
import org.usfirst.frc.team4131.robot.auto.action.TurnAction;
import org.usfirst.frc.team4131.robot.subsystem.SubsystemProvider;

import java.util.List;

/**
 * Autonomous procedure to run when the robot is placed
 * in front of the driver station.
 */
public class DriverStationToSwitch implements Procedure {
    @Override
    public int estimateLen() {
        return 5;
    }

    @Override
    public void populate(SubsystemProvider provider, List<Side> data, List<Action> procedure) {
        // check fms data
        if (data.get(0) == Side.RIGHT) {
            //forward 18", right 90, forward 27", left 90, forward 122"

            procedure.add(new DistanceMoveAction(provider.getDriveBase(), 18));
            procedure.add(new TurnAction(provider.getDriveBase(), 90));
            procedure.add(new DistanceMoveAction(provider.getDriveBase(), 27));
            procedure.add(new TurnAction(provider.getDriveBase(), -90));
            procedure.add(new DistanceMoveAction(provider.getDriveBase(), 122));
        } else if (data.get(0) == Side.LEFT) {
            //forward 18", left 90, forward 151", right 90, forward 122"

            procedure.add(new DistanceMoveAction(provider.getDriveBase(), 18));
            procedure.add(new TurnAction(provider.getDriveBase(), -90));
            procedure.add(new DistanceMoveAction(provider.getDriveBase(), 27));
            procedure.add(new TurnAction(provider.getDriveBase(), 90));
            procedure.add(new DistanceMoveAction(provider.getDriveBase(), 122));
        } else {
            DriverStation.reportError("Bad FMS data", true);
        }
    }
}
