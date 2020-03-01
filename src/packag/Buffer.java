/**
 * 
 */
package packag;

import java.util.ArrayList;

public class Buffer 
{
	private ArrayList<Mensaje> buff;
	private int N;
	Object lleno, vacio;
	private int numClientesAtendidos;
	private int numThreads;

	public Buffer(int n, int numThreads)
	{
		lleno = new Object();
		vacio = new Object();
		this.N = n;
		this.numThreads = numThreads;
		numClientesAtendidos = 0;
	}

	public int darNumThreads()
	{
		return numThreads;
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
		if (buff.size() == 0)
		{
			try {vacio.wait();} 
			catch (InterruptedException e) 
			{e.printStackTrace();}
		}
		
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
		synchronized (this) {
			numThreads++;
		}
		if(numThreads == numClientesAtendidos)
			System.exit(0);
	}

}
