package datagramas;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Cliente {

	public static void main(String[] args) {
		byte bufferEnviar [] = new byte [100];
		byte bufferRecibir [] = new byte [100];
		
		InetAddress direccion_server = null;
		DatagramSocket s = null;
		DatagramPacket in = null;
		DatagramPacket out = null;
		int recibido;
		int num [] = new int [2];
		
		/////////////PREGUNTAR PARA QUE ES ESTO///////////
		if (args.length != 1) {
			System.out.println("Uso: Cliente <host>");
			System.exit(0);
		}
		//////////////////////////////////////////////////
		
		try {
			//Se crea el socket del cliente
			s = new DatagramSocket ();
			
			//Direccion del servidor
			/////////////PREGUNTAR PARA QUE ES args [0]////////////
			direccion_server = InetAddress.getByName(args [0]);
			///////////////////////////////////////////////////
			
			num [0] = 1;
			num [1] = 5;
			
			//Se empaquetan los datos
			ByteArrayOutputStream baos =new ByteArrayOutputStream(); //Con este objeto la salida de datos se almacenan en un array de bytes
			ObjectOutputStream dos = new ObjectOutputStream(baos);//Con este objeto reconstruimos el array de bytes
			dos.writeObject(num); //Con este método lo escribe
			
			bufferEnviar = baos.toByteArray(); //Se obtiene el buffer (datagrama)
			
			//Un unico envio
			out = new DatagramPacket (bufferEnviar, bufferEnviar.length, direccion_server, 2500); //Se crea el paquete datagrama con el buffer, la longitud, direccion y puerto
			s.send(out); //Se envia
			
			//Se recibe el datagrama de respuesta
			in = new DatagramPacket (bufferRecibir, 100); //Se le pasa el buffer y la longitud del buffer
			
			//Se obtiene el buffer
			bufferRecibir = in.getData();
			
			//Se desempaqueta
			ByteArrayInputStream bais = new ByteArrayInputStream (bufferRecibir);
			DataInputStream dis = new DataInputStream (bais);
			
			recibido = dis.readInt();
			System.out.println("Datos recibidos " + recibido);
		
		}catch (Exception e){
			System.err.println("<<<<<excepcion " + e.toString() );
			e.printStackTrace();
		}
	}
}
