package src;


public class Cliente extends Thread
{
	/**
	 * Identificador �nico del cliente
	 */
	private int id;
	private int enviados;
	private Mensaje[] mensajes;
	/**
	 * Buffer compartido para establecer la comunicaci�n entre cliente-buffer-servidor
	 */
	private Buffer buff;
	/**
	 * M�todo constructor
	 * @param bf Buffer compartido
	 * @param id Identificador �nico del servidor
	 * @param numMensajes N�mero de mensajes que enviar� al buffer
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
	 * M�todo run del thread cliente
	 */
	public void run()
	{
		crearMensajes();
		//Ejecuta mientras haya mensajes
		while (enviados != mensajes.length)
		{
			System.out.println("El cliente " + id +" envi� su mensaje no."+(enviados+1)+": "+mensajes[enviados].darMensaje());
			buff.almacenar(mensajes[enviados]); 
			enviados++;
		}
		System.out.println("El cliente " + id +" envi� todos sus mensajes");
		//Verifica si es el �ltimo cliente
		buff.clienteTermino();
	}
	/**
	 * Crea todos los mensajes que ser�n enviados por el cliente
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
