package src;

import java.util.ArrayList;

public class Buffer 
{
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_RESET = "\u001B[0m";

	private ArrayList<Mensaje> buff;
	private int N;
	private int numClientesAtendidos;
	private int numThreadsTotales;

	public Buffer(int n, int numThreads)
	{
		buff = new ArrayList<Mensaje>();
		this.N = n;
		this.numThreadsTotales = numThreads;
		numClientesAtendidos = 0;
	}

	public synchronized void agregarMensaje(Mensaje m) 
	{
		buff.add(m);
	}

	public synchronized int consultarTamBuff() 
	{
		return buff.size();
	}

	public int darNumThreads()
	{
		return numThreadsTotales;
	}

	public void almacenar(Mensaje mns)
	{
		synchronized (mns.darCliente()) 
		{
			while (consultarTamBuff()== N)
			{Cliente.yield();}
		}

		synchronized (this) {
			agregarMensaje(mns);
			System.out.println("El cliente "+mns.darCliente().darId() +" dej� el mensaje en el buffer. TamBuff: "+buff.size());
		}
		synchronized(mns) 
		{
			mns.dormir();
		}

	}

	public void vaciar(Servidor s)
	{

		while ( consultarTamBuff() == 0 )
		{
			synchronized (this) 
			{Servidor.yield();}
		}

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

	public synchronized void clienteTermino() 
	{
		synchronized (this) 
		{numClientesAtendidos++;}
		termino();
	}

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
