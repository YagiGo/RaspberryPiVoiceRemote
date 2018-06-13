package com.example.zhaoxinwu.remote;

/*
 * Linux command to send UDP:
 * #socat - UDP-DATAGRAM:192.168.96.255:11111,broadcast,sp=11111
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
