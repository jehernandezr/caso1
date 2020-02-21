/**
 * 
 */
package packag;

/**
 * @author je.hernandezr
 *
 */
public class Cliente extends Thread {

	private Mensaje[] mensaje;
	
	private Buffer buff;
	public Cliente(Buffer bf, int numMensajes)
	{
		buff=bf;
		mensaje = new Mensaje[numMensajes];
	}
	
	public void run(){
		
	}
	
	
	
}
