package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Main
{
	/**
	 * Número de clientes
	 */
	private static int numClientes;
	/**
	 * Número de mensajes por cliente
	 */
	private static int numMensajes;
	/**
	 * Número de servidores disponibles
	 */
	private static int numServidores;
	/**
	 * Tamaño de mensajes que se pueden guardar al tiempo en el Buffer
	 */
	private static int tamBuffer;

	public static void main(String args[])
	{

		numClientes= 0;
		numMensajes=0;
		numServidores=0;
		//-----------------------------------------------------
		// Para leer el archivo .txt
		//-----------------------------------------------------
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

			/**
			 * Lista de los clientes
			 */
			ArrayList<Cliente> listaClie = new ArrayList<Cliente>();
			/**
			 * Lista de los servidores
			 */
			ArrayList<Servidor> listaSer = new ArrayList<Servidor>();

			System.out.println("---Caso 1: Manejo de la concurrencia---");
			System.out.println("Número de clientes: "+numClientes);
			System.out.println("Cada cliente envía "+numMensajes + " mensaje(s)");
			System.out.println("Número de servidores: "+numServidores);
			System.out.println("Número de tamaño del Buffer: "+tamBuffer +"\n");

			Buffer buff = new Buffer(tamBuffer, numClientes);

			//-----------------------------------------------------
			// Creación de los clientes
			//-----------------------------------------------------
			for (int i = 0; i < numClientes; i++) 
			{
				Cliente cliente = new Cliente(i, numMensajes, buff);
				listaClie.add(cliente);

			}
			System.out.println("---Los clientes fueron inicializados---");
			//-----------------------------------------------------
			// Creación de los servidores
			//-----------------------------------------------------
			for (int i = 0; i < numServidores; i++) 
			{
				Servidor servidor = new Servidor(buff, i);
				listaSer.add(servidor);
			}
			System.out.println("---Los servidores fueron inicializados---\n");
			//-----------------------------------------------------
			// Inicialización de los thread(clientes)
			//-----------------------------------------------------
			for (int i = 0; i < numClientes; i++)
			{
				listaClie.get(i).start();
			}
			//-----------------------------------------------------
			// Inicialización de los thread(servidor)
			//-----------------------------------------------------
			for (int i = 0; i < numServidores; i++)
			{
				listaSer.get(i).start();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}


}
