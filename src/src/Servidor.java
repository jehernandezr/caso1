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
			System.out.println("servidor"+ this.id+ "ha empezado");
			try {buffer.vaciar();}
			catch (Exception e) 
			{e.printStackTrace();}
			System.out.println();
			System.out.println("servidor"+ this.id+ "ha terminado");
			
	}
	
}
