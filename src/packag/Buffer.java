/**
 * 
 */
package packag;

import java.util.ArrayList;
import java.util.List;

public class Buffer 
{
	private ArrayList<Mensaje> buff;
	private int N;
	Object lleno, vacio;

	public Buffer(int n, int tamBuffer)
	{
		lleno = new Object();
		vacio = new Object();
		this.N = n;
	}

	@SuppressWarnings("static-access")
	public void almacenar(Mensaje mns)
	{
		synchronized (lleno) 
		{
			while (buff.size()== N)
			{
				mns.cliente.yield();
			}
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
		synchronized( vacio ){
			while ( buff.size( ) == 0 ){ //Implementación
			try { vacio.wait( ); }
			catch( InterruptedException e ){}
			}
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
	
	
	

}
