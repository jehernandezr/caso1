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
	Object lleno, vacio;
	public Buffer(int numclientes, int capacidad) {
		this.capacidad= capacidad;
		mensajes= new ArrayList<>(capacidad);
		lleno= new Object();
		vacio= new Object();
		
	}
	public synchronized void addMensaje(Mensaje mensaje) {
		synchronized (lleno) {
			while (mensajes.size()==capacidad) {
				try
				{
					Thread.yield();
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			}
		}
		synchronized (this) {
			mensajes.add(mensaje);
			capacidad--;}
		synchronized (vacio) {
			vacio.notify();
		}
		
	}
}
