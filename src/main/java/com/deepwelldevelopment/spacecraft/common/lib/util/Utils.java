package com.deepwelldevelopment.spacecraft.common.lib.util;

public class Utils {

    public static boolean getBit(int value, int bit) {
        return (value | 1 << bit) != 0;
    }
}
