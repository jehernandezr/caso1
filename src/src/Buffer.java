package src;

import java.util.ArrayList;

public class Buffer 
{
	/**
	 * Vector que cuenta con los mensajes del buffer
	 */
	private ArrayList<Mensaje> buff;
	/**
	 * N�mero de mensajes que pueden estar al tiempo en el buffer
	 */
	private int N;
	/**
	 * N�mero de clientes atendidos actualmente
	 */
	private int numClientesAtendidos;
	/**
	 * N�mero de clientes que ser�n atendidos
	 */
	private int numThreadsTotales;
	/**
	 * M�todo constructor de Buffer
	 * @param n N�mero de mensajes posibles en el buffer
	 * @param numThreads N�mero de clientes que ser�n atendidos
	 */
	public Buffer(int n, int numThreads)
	{
		buff = new ArrayList<Mensaje>();
		this.N = n;
		this.numThreadsTotales = numThreads;
		numClientesAtendidos = 0;
	}
	/**
	 * Agrega un mensaje al buffer
	 * @param m Mensaje a agregar
	 */
	public synchronized void agregarMensaje(Mensaje m) 
	{
		buff.add(m);
	}
	/**
	 * Retorna el tama�o del buffer
	 * @return tama�o del buffer
	 */
	public synchronized int consultarTamBuff() 
	{
		return buff.size();
	}
	/**
	 * Retorna el n�mero de clientes que se van a atender
	 * @return n�mero de clientes a atender 
	 */
	public int darNumThreads()
	{
		return numThreadsTotales;
	}
	/**
	 * Almacenar un mensaje en el buffer
	 * @param mns Mensaje a almacenar
	 */
	public void almacenar(Mensaje mns)
	{
		//En caso de que el buffer est� lleno
		synchronized (mns.darCliente()) 
		{
			while (consultarTamBuff()== N)
			{Cliente.yield();}
		}
		//Se agrega el mensaje cuando hay espacio
		synchronized (this) {
			agregarMensaje(mns);
			System.out.println("El cliente "+mns.darCliente().darId() +" dej� el mensaje en el buffer. TamBuff: "+buff.size());
		}
		//El cliente se duerme mientras espera la respuesta del servidor
		synchronized(mns) 
		{
			mns.dormir();
		}
	}
	/**
	 * Se saca un mensaje de buffer por parte del servidor
	 * @param s Servidor
	 */
	public void vaciar(Servidor s)
	{
		//En caso de que no hayan mensajes en el buffer
		while ( consultarTamBuff() == 0 )
		{
			synchronized (this) 
			{Servidor.yield();}
		}
		//Se atiende un mensaje por parte del servidor
		Mensaje atendido;
		synchronized (this) 
		{
			if( consultarTamBuff() != 0 )
			{
				atendido = buff.remove(0);
				System.out.println("El servidor est� atendiendo al cliente " +atendido.darCliente().darId());
				atendido.setRespuesta();
				System.out.println("Se atendi� al cliente "+atendido.darCliente().darId()+". Petici�n: "+ atendido.darMensaje()+" - Respuesta: "+atendido.darRespuesta());
				atendido.despertar();
			}

		}
	}
	/**
	 * M�todo que registra que un cliente ha terminado de enviar todas sus solicitudes
	 */
	public synchronized void clienteTermino() 
	{
		synchronized (this) 
		{numClientesAtendidos++;}
		termino();
	}
	/**
	 * Cierra el proceso cuando todos los clientes han enviado todos sus mensajes
	 * @return True si todos los clientes terminaron. False en caso de lo contrario
	 */
	public boolean termino()
	{
		if(numThreadsTotales == numClientesAtendidos)
		{	
			System.out.println("---Se ha cerrado el proceso---");
			System.exit(0);
			return true;
		}
		else
			return false;

	}
}
