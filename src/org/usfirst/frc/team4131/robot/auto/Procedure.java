package org.usfirst.frc.team4131.robot.auto;

import org.usfirst.frc.team4131.robot.subsystem.SubsystemProvider;

import java.util.List;

/**
 * Represents a procedure that will be run autonomously.
 * <p>See the examples in the {@link org.usfirst.frc.team4131.robot.auto}.</p>
 */
@FunctionalInterface
public interface Procedure {
    /**
     * Estimates the procedure length in order to prevent
     * resizing.
     *
     * @return the estimated length of the procedure
     */
    default int estimateLen() {
        return 16;
    }

    /*
     * A method overridden to populate the procedure with
     * actions to execute when run.
     *
     * @param provider the provider for all the subsystems
     * @param data the data sent by FMS
     * @param procedure the actions run in this procedure
     */
    void populate(SubsystemProvider provider, List<Side> data, List<Action> procedure);
}