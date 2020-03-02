package src;

public class Mensaje 
{
	/**
	 * Cliente asociado al mensaje
	 */
	public Cliente cliente;
	/**
	 * Mensaje envíado al buffer
	 */
	private int mns;
	/**
	 * Respuesta recibida por el servidor
	 */
	private int respuesta;
	/**
	 * Método constructor
	 * @param cliente Cliente que envió el mensaje
	 */
	public Mensaje(Cliente cliente)
	{
		this.cliente = cliente;
		mns = (int) Math.floor(Math.random()*50);
		respuesta = -1;
	}
	/**
	 * Retorna el mensaje enviado por el cliente
	 * @return mensaje
	 */
	public int darMensaje()
	{
		return mns;
	}
	/**
	 * Retorna la respuesta enviada por el servidor
	 * @return respuesta
	 */
	public int darRespuesta()
	{
		return respuesta;
	}
	/**
	 * Retorna el cliente asociado
	 * @return cliente
	 */
	public Cliente darCliente()
	{
		return cliente;
	}
	/**
	 * Método que genera la respuesta del servidor
	 */
	public void setRespuesta()
	{
		respuesta = darMensaje()+1;
	}
	/**
	 * Método que duerme al cliente mientras espera respuesta del servidor
	 */
	public synchronized void dormir()
	{
		try {
			this.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Método que despierta al cliente cuando recibe respuesta del servidor
	 */
	public synchronized void despertar()
	{
		this.notify();
	}
}
