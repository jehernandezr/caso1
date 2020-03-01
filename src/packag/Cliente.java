/**
 * 
 */
package packag;


public class Cliente extends Thread
{

	private int id;
	private int enviados;
	private Mensaje[] mensajes;
	private Buffer buff;
	
	public Cliente(int pId, int numMensajes, Buffer bf)
	{
		id=pId;
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
			enviados++;
		}
	}

	public synchronized void enviarMensaje()
	{
		try {
			System.out.println("\n"+ "el cliente " +id+"está enviando un mensaje");
			buff.almacenar(mensajes[mensajes.length-enviados-1]);
			enviados++;
		} catch (Exception e) {
			
		}
		
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
