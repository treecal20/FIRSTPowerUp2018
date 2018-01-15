package org.usfirst.frc.team4131.robot.auto;

public enum Side {
    LEFT, RIGHT;

    public static Side decode(char c) {
        return c == 'L' ? LEFT : RIGHT;
    }
}