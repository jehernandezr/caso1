/**
 * 
 */
package packag;

import java.nio.channels.Pipe.SinkChannel;

/**
 * @author je.hernandezr
 *
 */
public class Cliente extends Thread {

	private int enviados;
	private Mensaje[] mensajes;

	private Buffer buff;
	public Cliente(Buffer bf, int numMensajes)
	{
		enviados=0;
		buff=bf;
		mensajes = new Mensaje[numMensajes];
	}

	public void run(){
		while (enviados != mensajes.length) {
			enviarMensaje();
			
		}
	}

	public synchronized void enviarMensaje()
	{
		buff.addMensaje(mensajes[mensajes.length-enviados-1]);
		enviados++;
	}

	public void crearMensaje()
	{
		for (int i = 0; i < mensajes.length; i++) {
			Mensaje m= new Mensaje();
			mensajes[i]=m;

		}
	}
}
