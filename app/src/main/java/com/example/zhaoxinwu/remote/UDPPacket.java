package com.example.zhaoxinwu.remote;


import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class UDPPacket implements Runnable {
    public String ipAddr;
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
                IPAddr.getInstance().ipAddr = text;
                IPAddr.getInstance().ipGot = true;
            }
        }

        catch (IOException e) {
                Log.e("IOException", "error: ", e);
                run = false;
            }
    }

    public String getIpAddr() {return ipAddr;}
}
