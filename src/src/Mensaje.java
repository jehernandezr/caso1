package src;

public class Mensaje 
{
	public Cliente cliente;
	private int mns;
	private int respuesta;

	public Mensaje(Cliente cliente)
	{
		this.cliente = cliente;
		mns = (int) Math.floor(Math.random()*50);
		respuesta = -1;
	}
	
	public int darMensaje()
	{
		return mns;
	}
	
	public int darRespuesta()
	{
		return respuesta;
	}
	
	public Cliente darCliente()
	{
		return cliente;
	}
	
	public void setRespuesta()
	{
		respuesta = darMensaje()+1;
	}
	

}
