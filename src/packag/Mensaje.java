/**
 * 
 */
package packag;

/**
 * @author je.hernandezr
 *
 */
public class Mensaje {

	private int mensaje;
	
	public Mensaje() {
		setMensaje(0);
		mensajeAlazar();
	}

	/**
	 * @return the mensaje
	 */
	public int getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(int mensaje) {
		this.mensaje = mensaje;
	}
	
	public void mensajeAlazar()
	{
		int M=1000;
		int N=2000;
		int valorEntero = (int) Math.floor(Math.random()*(N-M+1)+M);  // Valor entre M y N, ambos incluidos.
		
		mensaje= valorEntero;
	}
	
}
