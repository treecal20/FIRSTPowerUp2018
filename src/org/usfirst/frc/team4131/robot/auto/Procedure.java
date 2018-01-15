package org.usfirst.frc.team4131.robot.auto;

import org.usfirst.frc.team4131.robot.subsystem.SubsystemProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents a procedure that will be run autonomously.
 */
public abstract class Procedure {
    // TODO perhaps move this to populate to reduce ref memory
    /** Lazily initialized FMS data */
    private List<Side> data;

    /**
     * Initializes data in this procedure with the FMS data.
     *
     * @param data the FMS data
     */
    public final void init(Side[] data) {
        this.data = Collections.unmodifiableList(Arrays.asList(data));
    }

    /**
     * Obtains an estimate for the number of actions that
     * will be added to this procedure in order to reduce
     * the number of data resizing done.
     *
     * @return the estimated procedure length
     */
    public int estimateLen() {
        return 16;
    }

    /**
     * Obtains the data sent by the FMS system.
     *
     * @return the collection of data sent by FMS.
     */
    public List<Side> getData() {
        return this.data;
    }

    /**
     * A method overridden to populate the procedure with
     * actions to execute when run.
     *
     * @param provider the provider for all the subsystems
     * @param procedure the actions run in this procedure
     */
    public abstract void populate(SubsystemProvider provider, List<Action> procedure);
}