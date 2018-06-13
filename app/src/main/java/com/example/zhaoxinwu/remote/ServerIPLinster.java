package com.example.zhaoxinwu.remote;


import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerIPLinster implements Runnable {
    private String ipAddr;
    private boolean hasIP = false;
    private static ServerIPLinster instance = null;
    private Thread threadHandler = null;

    private void ServerIPLinster() { }
    public static synchronized ServerIPLinster getInstance() {
        if(instance == null) {
            instance = new ServerIPLinster();
        }
        return instance;
    }

    public void start_listening() {
        threadHandler = new Thread(this);
        threadHandler.start();//Start listening to broadcast!
    }

    public void stop_listening() {
        threadHandler.interrupt();
    }

    public String getServerIP() {
        return ipAddr;
    }

    public boolean hasServerIP() {
        return hasIP;
    }

    @Override
    public void run() {
        boolean run = true;
        try{
            DatagramSocket udpSocket = new DatagramSocket(10000);
            // udpSocket.setSoTimeout(5000);
            udpSocket.setBroadcast(true);

            while(run) {
                byte[] message = new byte[64];
                DatagramPacket packet = new DatagramPacket(message,message.length);
                Log.i("UDP client: ", "about to wait to receive");
                udpSocket.receive(packet);
                String text = new String(message, 0, packet.getLength());
                Log.d("Received data", text);
                this.ipAddr = text;
                this.hasIP = true;
            }
        } catch (IOException e) {
            Log.e("IOException", "error: ", e);
            run = false;
        }
    }
}
