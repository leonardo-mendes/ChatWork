package com.chatwork.sd.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientMain {

	public static void main(String[] args) throws IOException {
		DatagramSocket s = new DatagramSocket();
		InetAddress dest = InetAddress.getByName("localhost");
		
		String envio;
		String defaultConnected = "connected";
		
		byte[] defaultBuffer = defaultConnected.getBytes();
		DatagramPacket defaultMessage =  new DatagramPacket(defaultBuffer, defaultBuffer.length, dest, 4545);
		s.send(defaultMessage);
		
		DatagramPacket resposta = new DatagramPacket(new byte[512],512);
		s.receive(resposta);
		System.out.println("Conectado.");
		
		BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(">");
		envio = teclado.readLine();		
		
		
		while(!envio.equalsIgnoreCase("")) {
			
			byte[] buffer = envio.getBytes();
			DatagramPacket message =  new DatagramPacket(buffer, buffer.length, dest, 4545);
			DatagramPacket retorno = new DatagramPacket(new byte[512],512);
			s.send(message);
			
			s.receive(retorno);
			
			String res = new String(retorno.getData());			
			System.out.println(res);
			System.out.println(">");
			envio = teclado.readLine();
		}
		
		s.close();

	}

}
