package com.example.zhaoxinwu.remote;

/*
 * Create a singleton for storing IP data derived from UDP broadcast
 */
public class IPAddr {
    public String ipAddr;
    public boolean ipGot = false;
    private static IPAddr instance = null;
    private void IPAddr() { }
    public static synchronized IPAddr getInstance() {
        if(instance == null) {
            instance = new IPAddr();
        }
        return instance;
    }
}
