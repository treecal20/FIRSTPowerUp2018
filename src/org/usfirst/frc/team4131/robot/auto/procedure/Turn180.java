package org.usfirst.frc.team4131.robot.auto.procedure;

import org.usfirst.frc.team4131.robot.auto.Action;
import org.usfirst.frc.team4131.robot.auto.Procedure;
import org.usfirst.frc.team4131.robot.auto.Side;
import org.usfirst.frc.team4131.robot.auto.action.TurnAction;
import org.usfirst.frc.team4131.robot.subsystem.SubsystemProvider;

import java.util.List;

public class Turn180 implements Procedure {
    
	@Override
    public int estimateLen() {
        return 1;
    }

    @Override
    public void populate(SubsystemProvider provider, List<Side> data, List<Action> procedure) {
        procedure.add(new TurnAction(provider.getDriveBase(), 180));
    }
}