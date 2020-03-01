/**
 * 
 */
package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	private static int numClientes;
	private static int numMensajes;
	private static int numServidores;
	private static int tamBuffer;
	public Main()
	{
		numClientes= 0;
		numMensajes=0;
		numServidores=0;
	}

	public static void main(String[] args) throws IOException {     
		String cadena;
		try{
			FileReader f = new FileReader("./data/datos");
			BufferedReader b = new BufferedReader(f);
			while((cadena = b.readLine())!=null) 
			{
				String llave = cadena.split(":")[0];
				String valor = cadena.split(":")[1];
				switch (llave) 
				{
				case "numClientes":
					numClientes= Integer.parseInt(valor.split(";")[0]);
					break;
				case "numMensajes":
					numMensajes= Integer.parseInt(valor.split(";")[0]);
					break;
				case "numServidores":
					numServidores= Integer.parseInt(valor.split(";")[0]);
					break;
				case "tamBuffer":
					tamBuffer= Integer.parseInt(valor.split(";")[0]);
					break;
				default:
					break;
				}
			}
			b.close();

			System.out.println("---Caso 1: Manejo de la concurrencia---");
			System.out.println("Número de clientes: "+numClientes);
			System.out.println("Cada cliente envía "+numMensajes + " mensaje(s)");
			System.out.println("Número de servidores: "+numServidores);
			System.out.println("Número de tamaño del Buffer: "+tamBuffer +"\n");


			Buffer buff = new Buffer(tamBuffer, numClientes);

			for (int i = 0; i < numClientes; i++) 
			{
				Cliente cliente = new Cliente(numMensajes, buff, i);
				cliente.start();
			}
			System.out.println("---Los clientes fueron inicializados---");
			
			for (int i = 0; i < numServidores; i++) 
			{
				Servidor servidor = new Servidor(buff, i);
				servidor.start();
			}
			System.out.println("---Los servidores fueron inicializados---\n");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


}
