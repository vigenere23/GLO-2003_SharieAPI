package com.github.glo2003.functional;

import com.github.glo2003.SharieAPI;

public class ServerSetup {
    private static boolean serverRunning = false;

    public static void launchServer() {
        if (!serverRunning) {
            SharieAPI.main();
            serverRunning = true;
        }
    }
}
