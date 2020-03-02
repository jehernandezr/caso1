/**
 * 
 */
package src;

public class Servidor extends Thread 
{
	private int id;
	private Buffer buffer;
	
	public Servidor(Buffer buff, int id)
	{
		buffer=buff;
		this.id = id;
	}
	
	public int darId()
	{
		return id;
	}
	
	public void run() 
	{
		while(buffer.termino()==false)
		{
			try {buffer.vaciar(this);}
			catch (Exception e) 
			{e.printStackTrace();}
			System.out.println();
		}
	}
	
	@SuppressWarnings("static-access")
	public synchronized void bufferVacio()
	{
		this.yield();
	}
	
}
