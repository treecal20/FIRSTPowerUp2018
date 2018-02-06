package org.usfirst.frc.team4131.robot.auto.procedure;

import java.util.List;

import org.usfirst.frc.team4131.robot.Robot;
import org.usfirst.frc.team4131.robot.auto.Action;
import org.usfirst.frc.team4131.robot.auto.Procedure;
import org.usfirst.frc.team4131.robot.auto.Side;
import org.usfirst.frc.team4131.robot.auto.action.DistanceMoveAction;
import org.usfirst.frc.team4131.robot.auto.action.TurnAction;
import org.usfirst.frc.team4131.robot.subsystem.SubsystemProvider;

public class rightToSwitchOrScale implements Procedure{

	String fmsAt0 = "" + Robot.FMS_DATA.charAt(0);
	String fmsAt1 = "" + Robot.FMS_DATA.charAt(1);

	@Override
	public void populate(SubsystemProvider provider, List<Side> data, List<Action> procedure) {

		try {
			if (fmsAt0.equals("R")) {
				//if the switch is right
				//drive straight 168 inches (until level with center of the switch
				procedure.add(new DistanceMoveAction(provider.getDriveBase(), 168));
				//turn left 90
				procedure.add(new TurnAction(provider.getDriveBase(), -90));
				//TODO drive straight until against switch (what distance?)
				procedure.add(new DistanceMoveAction(provider.getDriveBase(), 19.5));
				//TODO put cube on thing
			}
			else if (fmsAt0.equals("L") && fmsAt1.equals("R")) {
				//if the switch is left but the scale is right
				//drive until level with the scale
				procedure.add(new DistanceMoveAction(provider.getDriveBase(), 168));
				//turn left 90
				procedure.add(new TurnAction(provider.getDriveBase(), -90));
				//TODO drive until against the scale
				procedure.add(new DistanceMoveAction(provider.getDriveBase(), 5.8));
				//TODO put cube on thing
			}
			else if (fmsAt0.equals("L") && fmsAt1.equals("L")) {
				//if both are left (either drive straight or go to center)
				//Currently runs same as LRBaseline
				procedure.add(new DistanceMoveAction(provider.getDriveBase(), 144));
				//TODO sort this out
				//May want other options
			}
			else {
				System.err.println("Bad FMS data!");
			}
		}
		catch (IllegalArgumentException e) {
			System.err.print("Bad FMS data! error: " + e );
		}
	}


}
