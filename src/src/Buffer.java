/**
 * 
 */
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
			{mns.esperar();}
		}

		synchronized (this) 
		{
			buff.add(mns);

			try {mns.cliente.wait();}
			catch (InterruptedException e)
			{e.printStackTrace();}
		}
		synchronized (vacio) 
		{vacio.notify();}
	}

	public void vaciar()
	{
<<<<<<< HEAD:src/src/Buffer.java
		if (buff.size() == 0)
		{
			try {vacio.wait();} 
			catch (InterruptedException e) 
			{e.printStackTrace();}
		}

=======
		synchronized( vacio ){
			while ( buff.size( ) == 0 ){ //Implementación
			try { vacio.wait( ); }
			catch( InterruptedException e ){}
			}
			}
		
>>>>>>> 25591e939086131b8365332a2e90f248bd86c969:src/packag/Buffer.java
		synchronized (this) 
		{
			Mensaje atendido = buff.remove(1);
			atendido.setRespuesta();
			atendido.cliente.notify();
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
