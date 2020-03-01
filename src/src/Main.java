/**
 * 
 */
package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main
{
	private static int numClientes;
	private static int numMensajes;
	private static int numServidores;
	private static int tamBuffer;
	
	private static ArrayList<Cliente> clientes;
	private static ArrayList<Servidor> servidores;

	public static void main(String[] args) throws IOException 
	{     
		
		numClientes= 0;
		numMensajes=0;
		numServidores=0;
		clientes = new ArrayList<Cliente>();
		servidores = new ArrayList<Servidor>();
		
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
				Cliente cliente = new Cliente(i, numMensajes, buff);
				clientes.add(cliente);
			}
			System.out.println("---Los clientes fueron inicializados---");
			
			for (int i = 0; i < numServidores; i++) 
			{
				Servidor servidor = new Servidor(buff, i);
				servidores.add(servidor);
			}
			System.out.println("---Los servidores fueron inicializados---\n");
			
			for (int i = 0; i < servidores.size(); i++) 
			{
				Servidor servidor = servidores.get(i);
				servidor.start();
			}
			
			for (int i = 0; i < clientes.size(); i++) 
			{
				Cliente cliente = clientes.get(i);
				cliente.start();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}


	}


}
