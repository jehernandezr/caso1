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


	public int darId()
	{
		return this.id;
	}

	public void run()
	{
		crearMensajes();
		while (enviados != mensajes.length-1)
		{
			System.out.println("El cliente " + id +" envió su mensaje no."+(enviados+1)+": "+mensajes[enviados].darMensaje());
			buff.almacenar(mensajes[enviados]); 
			enviados++;
		}
		System.out.println("El cliente " + id +" envió todos sus mensajes");
		buff.clienteTermino();
	}

	public void crearMensajes()
	{
		for (int i = 0; i < mensajes.length; i++) 
		{
			Mensaje m = new Mensaje(this);
			mensajes[i] = m;
		}
	}

	public synchronized void dormir()
	{
		try 
		{
			this.wait();
		} 
		catch (InterruptedException e) 
		{e.printStackTrace();}
	}
}
