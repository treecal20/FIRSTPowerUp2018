/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4131.robot;

/**
 * Constant mapping for the ports and channels used on the
 * robot to identify gears and controllers.
 */
public final class RobotMap {
    // Joysticks
    public static final int L_JOY_PORT = 0;
    public static final int R_JOY_PORT = 1;
    public static final int AUX_JOY_PORT = 2;

    // CAN devices
    public static final int PCM = 61;

    // Left motors
    public static final int L1 = 1;
    public static final int L2 = 2;

    // Right motors
    public static final int R1 = 3;
    public static final int R2 = 4;

    // Climber motors
    public static final int C1 = 5;
    public static final int C2 = 6;

    // Claw elevator motor
    public static final int E = 0;

    public static final int CLAWONE = 5;
    public static final int CLAWTWO = 4;
    public static final int EJECTONE = 6;
    public static final int EJECTTWO = 7;
    public static final int LOWERONE = 0;
    public static final int LOWERTWO = 0;
    
    private RobotMap() { // Prevent instantiation
    }
}