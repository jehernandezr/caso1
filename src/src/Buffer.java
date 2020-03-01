package src;

import java.util.ArrayList;

public class Buffer 
{
	private ArrayList<Mensaje> buff;
	private int N;
	Object lleno, vacio;
	private int numClientesAtendidos;
	private int numThreadsTotales;

	public Buffer(int n, int numThreads)
	{
		buff = new ArrayList<Mensaje>();
		lleno = new Object();
		vacio = new Object();
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
		synchronized (lleno) 
		{
			while (buff.size()== N)
			{Cliente.yield();}

		}

		synchronized (this) 
		{
			buff.add(mns);

			try {wait();}
			catch (InterruptedException e)
			{e.printStackTrace();}
		}
		
	}

	public void vaciar()
	{

		while ( buff.size( ) == 0 )
		{
			synchronized (this) {
				Servidor.yield();
			}


		}


		synchronized (this) 
		{
			while(!buff.isEmpty()) {
				Mensaje atendido = buff.remove(0);
				System.out.println("el servidor está atendiendo al cliente" +atendido.darCliente().darId());
				atendido.setRespuesta();

			}
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
