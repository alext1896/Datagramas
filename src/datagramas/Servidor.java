package datagramas;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Servidor {

	public static void main(String[] args) {

		DatagramSocket s = null;
		DatagramPacket in;
		DatagramPacket out;
		InetAddress direccion_cliente = null;
		int puerto_cliente;
		byte bufferEnviar [] = new byte [100];
		byte bufferRecibir [] = new byte [100];
		int num [], resultado;
		
		try {
			s = new DatagramSocket (20000);
			in = new DatagramPacket (bufferRecibir, 100); //Paquete para recibir la solicitud
			
			while (true) {
				s.receive(in); //Esperamos a recibir el paquete del cliente
				
				//Obtener datos
				bufferRecibir = in.getData(); //Se obtiene los datos del paquete mandado por el cliente
				direccion_cliente = in.getAddress(); //Se obtiene la direccion del cliente mandado por el cliente
				puerto_cliente = in.getPort(); //Se obtiene el puerto
				
				//Desempaquetamos los datos
				ByteArrayInputStream bais = new ByteArrayInputStream(bufferRecibir);
				ObjectInputStream dis = new ObjectInputStream(bais);
				
				num = (int []) dis.readObject();
				resultado = num [0] + num [1];
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream ();
				DataOutputStream dos = new DataOutputStream(baos);
				dos.writeInt(resultado);
				bufferEnviar = baos.toByteArray();
				
				out = new DatagramPacket (bufferEnviar, bufferEnviar.length, direccion_cliente, puerto_cliente);
				s.send(out);
				
				
			}
			
		}catch (Exception e) {
			System.err.println("excepcion " + e.toString() );
			e.printStackTrace();
		}
		
	}

}
