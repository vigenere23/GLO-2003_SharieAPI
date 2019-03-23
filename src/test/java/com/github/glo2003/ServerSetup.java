package com.github.glo2003;

public class ServerSetup {
    private static boolean serverRunning = false;

    public static void launchServer() {
        if (!serverRunning) {
            SharieAPI.main();
            serverRunning = true;
        }
    }
}
