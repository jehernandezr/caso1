package src;

public class Servidor extends Thread 
{
	/**
	 * Identificador único del servidor
	 */
	private int id;
	/**
	 * Buffer compartido para establecer la comunicación entre cliente-buffer-servidor
	 */
	private Buffer buffer;
	/**
	 * Método constructor
	 * @param buff Buffer compartido
	 * @param id Identificador único del servidor
	 */
	public Servidor(Buffer buff, int id)
	{
		buffer=buff;
		this.id = id;
	}
	/**
	 * Retorna el identificador del servidor
	 * @return id del servidor
	 */
	public int darId()
	{
		return id;
	}
	/**
	 * Método run del thread servidor
	 */
	public void run() 
	{
		//Se ejecuta mientras que haya clientes
		while(buffer.termino()==false)
		{
			try {buffer.vaciar(this);} //Toma y procesa un mensaje del buffer
			catch (Exception e) 
			{e.printStackTrace();}
			System.out.println();
		}
	}
	/**
	 * Método que cede el procesador cuando no hay mensajes en el buffer
	 */
	@SuppressWarnings("static-access")
	public synchronized void bufferVacio()
	{
		this.yield();
	}

}
