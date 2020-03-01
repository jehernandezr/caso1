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

	public int darNumThreads()
	{
		return numThreadsTotales;
	}

	public void almacenar(Mensaje mns)
	{
		synchronized (this) 
		{
			while (buff.size()== N)
			{Cliente.yield();}
		}

		synchronized(mns) 
		{
			System.out.println("El cliente "+mns.darCliente().darId() +" dejó el mensaje en el buffer. TamBuff: "+buff.size());
			buff.add(mns);
			mns.dormir();
		}

	}

	public void vaciar(Servidor s)
{
		while ( buff.size( ) == 0 )
		{
			s.bufferVacio();
		}
		
		synchronized (this) 
		{
				Mensaje atendido = buff.remove(0);
				System.out.println("El servidor está atendiendo al cliente " +atendido.darCliente().darId());
				atendido.setRespuesta();
				atendido.despertar();
		}
	}

	public void clienteTermino() 
	{
		synchronized (this) 
		{numThreadsTotales++;}

		if(numThreadsTotales == numClientesAtendidos)
		{	
			System.exit(0);
			System.out.println("Se ha cerrado el proceso");
		}
	}

}
