/**
 * 
 */
package packag;

import java.util.ArrayList;
import java.util.List;

/**
 * @author je.hernandezr
 *
 */
public class Buffer {
	private int capacidad;
	List<Mensaje> mensajes;
	List<Mensaje> mensajesModif;
	
	public Buffer(int numclientes, int capacidad) {
		
		mensajes= new ArrayList<>(capacidad);
		mensajesModif= new ArrayList<>(capacidad);
	}
}
