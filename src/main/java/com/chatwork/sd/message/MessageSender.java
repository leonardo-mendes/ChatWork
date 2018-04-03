package com.chatwork.sd.message;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MessageSender implements Runnable {
    public final static int PORT = 4545;
    private DatagramSocket sock;
    private String hostname;
    
    public MessageSender(DatagramSocket s, String h) {
        sock = s;
        hostname = h;
    }
    
    private void sendMessage(String s) throws Exception {
        byte buf[] = s.getBytes();
        InetAddress address = InetAddress.getByName(hostname);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT);
        sock.send(packet);
    }
    
    public void run() {
        boolean connected = false;
        do {
            try {
                sendMessage("Connected");
                connected = true;
            } catch (Exception e) {
                
            }
        } while (!connected);
        
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        while (true) {
            try {
            	// Enquanto não for digitado nada a thread vai se manter em repouso.
                while (!in.ready()) {
                    Thread.sleep(100);
                }
                sendMessage(in.readLine());
            } catch(Exception e) {
                System.err.println(e);
            }
        }
    }
    
}
