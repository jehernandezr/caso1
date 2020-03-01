package src;

import java.util.ArrayList;

public class Buffer 
{
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_RESET = "\u001B[0m";
	
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
			{
				mns.esperar();
				System.out.println("Buffer se encuentra lleno");
			}
		}

		synchronized (buff) 
		{
			System.out.println("El cliente "+mns.darCliente().darId() +" dejó el mensaje en el buffer. TamBuff: "+buff.size());
			buff.add(mns);
		}
		
		synchronized (this) 
		{
			mns.dormir();
		}

		synchronized (vacio) 
		{vacio.notify();}
	}

	@SuppressWarnings("static-access")
	public void vaciar(Servidor s)
	{
		synchronized( this )
		{
			while ( buff.size( ) == 0 )
			{
				s.yield(); 
			}
		}

		synchronized (this) 
		{
			Mensaje atendido = buff.remove(0);
			atendido.setRespuesta();
			atendido.despertar();
		}

		synchronized (lleno) 
		{lleno.notify();}
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
