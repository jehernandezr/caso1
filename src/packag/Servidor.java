/**
 * 
 */
package packag;

/**
 * @author je.hernandezr
 *
 */
public class Servidor extends Thread {
	public int id;
	private static int contador;
	private Buffer buffer;
	public Servidor(int pId, Buffer buff)
	{
		id=pId;
		buffer=buff;
	}
	
	public void run() {
		System.out.println("\n"+ "el servidor" +id+"atendiendo mensaje");
		try {
			
			buffer.vaciar();
		} catch (Exception e) {
			
		}
		System.out.println("\n"+ "el servidor" +id+"ha terminado");
	}
	
	

}
