package src;


public class Cliente extends Thread
{
	/**
	 * Identificador único del cliente
	 */
	private int id;
	private int enviados;
	private Mensaje[] mensajes;
	/**
	 * Buffer compartido para establecer la comunicación entre cliente-buffer-servidor
	 */
	private Buffer buff;
	/**
	 * Método constructor
	 * @param bf Buffer compartido
	 * @param id Identificador único del servidor
	 * @param numMensajes Número de mensajes que enviará al buffer
	 */
	public Cliente(int id, int numMensajes, Buffer bf)
	{
		enviados=0;
		buff=bf;
		mensajes = new Mensaje[numMensajes];
		this.id = id;
	}
	/**
	 * Retorna el identificador del cliente
	 * @return id del cliente
	 */
	public int darId()
	{
		return this.id;
	}
	/**
	 * Método run del thread cliente
	 */
	public void run()
	{
		crearMensajes();
		//Ejecuta mientras haya mensajes
		while (enviados != mensajes.length)
		{
			System.out.println("El cliente " + id +" envió su mensaje no."+(enviados+1)+": "+mensajes[enviados].darMensaje());
			buff.almacenar(mensajes[enviados]); 
			enviados++;
		}
		System.out.println("El cliente " + id +" envió todos sus mensajes");
		//Verifica si es el último cliente
		buff.clienteTermino();
	}
	/**
	 * Crea todos los mensajes que serán enviados por el cliente
	 */
	public void crearMensajes()
	{
		for (int i = 0; i < mensajes.length; i++) 
		{
			Mensaje m = new Mensaje(this);
			mensajes[i] = m;
		}
	}
}
