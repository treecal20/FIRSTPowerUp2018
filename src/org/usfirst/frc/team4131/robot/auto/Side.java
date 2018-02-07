package org.usfirst.frc.team4131.robot.auto;

/**
 * A unit of data sent by FMS
 */
public enum Side {
    LEFT, RIGHT;

    public static Side decode(char c) {
        return c == 'L' ? LEFT : RIGHT;
    }
}