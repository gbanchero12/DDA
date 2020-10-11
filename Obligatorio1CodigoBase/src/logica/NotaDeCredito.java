package logica;
import java.util.Date;

public class NotaDeCredito {

	private Factura facturaAsociada;
	private int numero;
	private Date fecha;
	
		
	public NotaDeCredito() {
		super();
	}
	
	public NotaDeCredito(Factura facturaAsociada, int numero, Date fecha, int codigo) {
		super();
		this.facturaAsociada = facturaAsociada;
		this.numero = numero;
		this.fecha = fecha;
	}

	public Factura getFacturaAsociada() {
		return facturaAsociada;
	}

	public void setFacturaAsociada(Factura facturaAsociada) {
		this.facturaAsociada = facturaAsociada;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public void asignarFecha() {
		this.fecha = new Date();
	}

	
	
	
}
