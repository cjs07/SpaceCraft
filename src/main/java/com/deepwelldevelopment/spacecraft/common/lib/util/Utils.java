package com.deepwelldevelopment.spacecraft.common.lib.util;

public class Utils {

    public static boolean getBit(int value, int bit) {
        return (value & 1 << bit) != 0;
    }

    public static int setBit(int value, int bit) {
         return value | 1 << bit;
    }

    public static int clearBit(int value, int bit) {
        return value & ~ (1<<bit);
    }

    public static int toggleBit(int value, int bit) {
        return value ^ 1 << bit;
    }
}
