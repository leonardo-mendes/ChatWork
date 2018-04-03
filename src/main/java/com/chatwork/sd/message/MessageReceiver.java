package com.chatwork.sd.message;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class MessageReceiver implements Runnable {
    DatagramSocket sock;
    byte buf[];
    
    public MessageReceiver(DatagramSocket s) {
        sock = s;
        buf = new byte[1024];
    }
    
    public void run() {
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                sock.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println(received);
            } catch(Exception e) {
                System.err.println(e);
            }
        }
    }
    
}