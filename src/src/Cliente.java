/**
 * 
 */
package src;


public class Cliente extends Thread
{

	private int id;
	private int enviados;
	private Mensaje[] mensajes;
	private Buffer buff;


	public Cliente(int id, int numMensajes, Buffer bf)
	{
		enviados=0;
		buff=bf;
		mensajes = new Mensaje[numMensajes];
		this.id = id;
	}

	public void run()
	{
		crearMensajes();
		while (enviados != mensajes.length)
		{
			System.out.println("El cliente " + id +" envió su mensaje no."+(enviados+1)+": "+mensajes[enviados].darMensaje());
			enviarMensaje();
			enviados++;
		}
		System.out.println("El cliente " + id +" envió todos sus mensajes");
		buff.clienteTermino();
	}

	public synchronized void enviarMensaje()
	{
		buff.almacenar(mensajes[enviados]);
		enviados++;
	}

	public void crearMensajes()
	{
		for (int i = 0; i < mensajes.length; i++) 
		{
			Mensaje m = new Mensaje(this);
			mensajes[i] = m;
		}
	}
}
