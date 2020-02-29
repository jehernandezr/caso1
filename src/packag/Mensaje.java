package packag;


public class Mensaje 
{
	public Cliente cliente;
	private int mns;

	public Mensaje(Cliente cliente)
	{
		this.cliente = cliente;
		mns = (int) Math.floor(Math.random()*10);
	}
	
	public int darMensaje()
	{
		return mns;
	}
	
	public Cliente darCliente()
	{
		return cliente;
	}
	
	public void setRespuesta()
	{
		mns = mns++;
	}
}
