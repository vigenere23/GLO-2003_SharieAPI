package com.github.glo2003.helpers;

import java.util.concurrent.ThreadLocalRandom;

public class MathHelper {
    public static long randomLong() {
        return ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE - 1);
    }
}