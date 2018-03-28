package com.chatwork.sd.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerMain {
	
	public static final Map<InetAddress,Integer> infoMap = new HashMap<InetAddress,Integer>();

	public static void main(String[] args) throws IOException {
		DatagramSocket s = new DatagramSocket(4545);
		System.out.println("Servidor iniciado....");
		
		String envio;
		
		 while(true) {
			 DatagramPacket recebe = new DatagramPacket(new byte[512], 512);
			 envio ="";
			 s.receive(recebe);
			 
			 String res = new String(recebe.getData());	
			 		 
			 if(res.contains("connected")) {
				 infoMap.put(recebe.getAddress(), recebe.getPort());
				 DatagramPacket resp = new DatagramPacket(recebe.getData(), recebe.getLength(), recebe.getAddress(),  recebe.getPort());
				 s.send(resp);
			 }else {
				 for(Map.Entry<InetAddress,Integer> entry :infoMap.entrySet()){
					 DatagramPacket resp = new DatagramPacket(recebe.getData(), recebe.getLength(), entry.getKey(),  entry.getValue());
					 System.out.println("Data: "+recebe.getData()+" tamanho: "+recebe.getLength()+" Address: "+entry.getKey()+" Porta: "+entry.getValue());
					 s.send(resp);
				 }
			 }
		 }
	}

}
