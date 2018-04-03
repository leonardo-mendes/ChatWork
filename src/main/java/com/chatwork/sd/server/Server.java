package com.chatwork.sd.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Server extends Thread {
	
    public final static int PORT = 4545;    
    private final static int BUFFER = 1024;
    
    private DatagramSocket socket;
	private ArrayList<InetAddress> clientAddresses;
    private ArrayList<Integer> clientPorts;
    private HashSet<String> existingClients;
    
    public Server() throws IOException {
        socket = new DatagramSocket(PORT);
        clientAddresses = new ArrayList();
        clientPorts = new ArrayList();
        existingClients = new HashSet();
    }

	public void run() {
        byte[] buf = new byte[BUFFER];
        while (true) {
            try {
            	
                Arrays.fill(buf, (byte)0);
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                
                String content = new String(buf);
                
                InetAddress clientAddress = packet.getAddress();
                int clientPort = packet.getPort();    
                // O cliente vai ser identficado pelo seu Ip-Porta
                String idClient = "IpAddress-Port "+clientAddress.toString() + "-"+clientPort;
                
                // Validar se o usuário que enviou a mensagem já vai estar mapeado.
                if (!existingClients.contains(idClient)) {
                    existingClients.add( idClient );
                    clientPorts.add( clientPort );
                    clientAddresses.add(clientAddress);
                }
                
                byte[] message = (idClient + " said: " +  content + "\n ..>").getBytes();
                
                // Envio para todos os clients que tem salvo ip e porta.
                for (int i=0; i < clientAddresses.size(); i++) {
                    InetAddress address = clientAddresses.get(i);
                    int port = clientPorts.get(i);
                    packet = new DatagramPacket(message, message.length, address, port);
                    socket.send(packet);
                }

                
            } catch(Exception e) {
                System.err.println(e);
            }
         }
     } 
}   