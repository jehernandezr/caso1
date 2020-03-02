package src;

import java.util.ArrayList;

public class Buffer 
{
	/**
	 * Vector que cuenta con los mensajes del buffer
	 */
	private ArrayList<Mensaje> buff;
	/**
	 * Número de mensajes que pueden estar al tiempo en el buffer
	 */
	private int N;
	/**
	 * Número de clientes atendidos actualmente
	 */
	private int numClientesAtendidos;
	/**
	 * Número de clientes que serán atendidos
	 */
	private int numThreadsTotales;
	/**
	 * Método constructor de Buffer
	 * @param n Número de mensajes posibles en el buffer
	 * @param numThreads Número de clientes que serán atendidos
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
	 * Retorna el tamaño del buffer
	 * @return tamaño del buffer
	 */
	public synchronized int consultarTamBuff() 
	{
		return buff.size();
	}
	/**
	 * Retorna el número de clientes que se van a atender
	 * @return número de clientes a atender 
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
		//En caso de que el buffer esté lleno
		synchronized (mns.darCliente()) 
		{
			while (consultarTamBuff()== N)
			{Cliente.yield();}
		}
		//Se agrega el mensaje cuando hay espacio
		synchronized (this) {
			agregarMensaje(mns);
			System.out.println("El cliente "+mns.darCliente().darId() +" dejó el mensaje en el buffer. TamBuff: "+buff.size());
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
				System.out.println("El servidor está atendiendo al cliente " +atendido.darCliente().darId());
				atendido.setRespuesta();
				System.out.println("Se atendió al cliente "+atendido.darCliente().darId()+". Petición: "+ atendido.darMensaje()+" - Respuesta: "+atendido.darRespuesta());
				atendido.despertar();
			}

		}
	}
	/**
	 * Método que registra que un cliente ha terminado de enviar todas sus solicitudes
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
