package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Main
{
	/**
	 * N�mero de clientes
	 */
	private static int numClientes;
	/**
	 * N�mero de mensajes por cliente
	 */
	private static int numMensajes;
	/**
	 * N�mero de servidores disponibles
	 */
	private static int numServidores;
	/**
	 * Tama�o de mensajes que se pueden guardar al tiempo en el Buffer
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
			System.out.println("N�mero de clientes: "+numClientes);
			System.out.println("Cada cliente env�a "+numMensajes + " mensaje(s)");
			System.out.println("N�mero de servidores: "+numServidores);
			System.out.println("N�mero de tama�o del Buffer: "+tamBuffer +"\n");

			Buffer buff = new Buffer(tamBuffer, numClientes);

			//-----------------------------------------------------
			// Creaci�n de los clientes
			//-----------------------------------------------------
			for (int i = 0; i < numClientes; i++) 
			{
				Cliente cliente = new Cliente(i, numMensajes, buff);
				listaClie.add(cliente);

			}
			System.out.println("---Los clientes fueron inicializados---");
			//-----------------------------------------------------
			// Creaci�n de los servidores
			//-----------------------------------------------------
			for (int i = 0; i < numServidores; i++) 
			{
				Servidor servidor = new Servidor(buff, i);
				listaSer.add(servidor);
			}
			System.out.println("---Los servidores fueron inicializados---\n");
			//-----------------------------------------------------
			// Inicializaci�n de los thread(clientes)
			//-----------------------------------------------------
			for (int i = 0; i < numClientes; i++)
			{
				listaClie.get(i).start();
			}
			//-----------------------------------------------------
			// Inicializaci�n de los thread(servidor)
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
