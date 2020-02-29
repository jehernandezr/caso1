/**
 * 
 */
package packag;


public class Cliente extends Thread
{

	private int enviados;
	private Mensaje[] mensajes;
	private Buffer buff;
	
	public Cliente(int numMensajes, Buffer bf)
	{
		enviados=0;
		buff=bf;
		mensajes = new Mensaje[numMensajes];
	}

	public void run()
	{
		crearMensajes();
		while (enviados != mensajes.length)
		{
			enviarMensaje();
		}
	}

	public synchronized void enviarMensaje()
	{
		buff.addMensaje(mensajes[mensajes.length-enviados-1]);
		enviados++;
	}

	public void crearMensajes()
	{
		for (int i = 0; i < mensajes.length; i++) 
		{
			Mensaje m= new Mensaje(this);
			mensajes[i]=m;
		}
	}
}
