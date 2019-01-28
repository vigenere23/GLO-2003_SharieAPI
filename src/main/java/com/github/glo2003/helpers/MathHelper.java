package com.github.glo2003.helpers;

import java.util.concurrent.ThreadLocalRandom;

public class MathHelper {
  public static int randomInt() {
    return ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE - 1);
  }
}