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

<<<<<<< HEAD
		synchronized(mns) 
=======
		synchronized (this) 
>>>>>>> 3b9bda0b88f005ab422b4f40c8a2cc3a5790935a
		{
			System.out.println("El cliente "+mns.darCliente().darId() +" dejó el mensaje en el buffer. TamBuff: "+buff.size());
			buff.add(mns);
			mns.dormir();
		}
		

	}

<<<<<<< HEAD
	public void vaciar(Servidor s)
=======
	public void vaciar()
>>>>>>> 3b9bda0b88f005ab422b4f40c8a2cc3a5790935a
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
