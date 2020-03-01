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
		while(true)
		{			
			try {buffer.vaciar();}
			catch (Exception e) 
			{e.printStackTrace();}
		}
	}
	
}
