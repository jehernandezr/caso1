/**
 * 
 */
package packag;

import java.awt.font.NumericShaper;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
			while((cadena = b.readLine())!=null) {
				switch (cadena.substring(0, 12)+"") {
				case "numClientes:":
					numClientes= Integer.parseInt(cadena.substring(12, cadena.length()-1));
					System.out.println(cadena);
					break;
				case "numMensajes:":
					numMensajes= Integer.parseInt(cadena.substring(12, cadena.length()-1));
					System.out.println(cadena);
					break;
				case "numServidores:":
					numServidores= Integer.parseInt(cadena.substring(12, cadena.length()-1));
					System.out.println(cadena);
					break;
				case "tamBuffer:":
					tamBuffer= Integer.parseInt(cadena.substring(12, cadena.length()-1));
					System.out.println(cadena);
					break;
				default:
					break;
				}
			}
			b.close();

			Buffer buff = new Buffer(numClientes, tamBuffer);

			for (int i = 0; i < numClientes; i++) {
				Cliente cliente = new Cliente(10+i,numMensajes, buff);
				cliente.start();
			}
			
			for (int i = 0; i < numServidores; i++) {
				Servidor servidor = new Servidor(i, buff);
				servidor.start();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}


}
