package com.chatwork.sd.client;

import java.net.DatagramSocket;

import com.chatwork.sd.message.MessageReceiver;
import com.chatwork.sd.message.MessageSender;


public class ClientMain {
    
    public static void main(String args[]) throws Exception {
        String host = null;

        DatagramSocket socket = new DatagramSocket();
        
        MessageReceiver r = new MessageReceiver(socket);
        MessageSender s = new MessageSender(socket, host);
        Thread rt = new Thread(r);
        Thread st = new Thread(s);
        rt.start(); st.start();
    }
    
}