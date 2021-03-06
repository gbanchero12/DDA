package subsistemas;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import logica.Cliente;
import logica.Factura;
import logica.NotaDeCredito;

public class ControlNotasDeCredito {

	private ArrayList<NotaDeCredito> notasDeCredito = new ArrayList<>();
	private int proximoNumero;

	public ControlNotasDeCredito() {

	}

	public ArrayList<NotaDeCredito> getNotasDeCredito() {
		return notasDeCredito;
	}

	public void agregar(NotaDeCredito unaNc, Factura facturaAsociada) {

		if (!facturaAsociada.getMedioDePago().equals("Tarjeta de Cr�dito")) {
			return;
		}

		
		unaNc.setNumero(generarProximoNumero());
		unaNc.asignarFecha();
		unaNc.setFacturaAsociada(facturaAsociada);
		notasDeCredito.add(unaNc);
	}

	public boolean facturaAnulada(Factura f) {
		for (NotaDeCredito nc : notasDeCredito) {
			if (nc.getFacturaAsociada().equals(f))
				return true;
		}
		return false;
	}

	private int generarProximoNumero() {
		proximoNumero++;
		return proximoNumero;
	}

	public NotaDeCredito buscarNcPorNumero(int numero) {
		for (NotaDeCredito nc : this.notasDeCredito) {
			if (nc.getNumero() == numero)
				return nc;
		}
		return null;
	}

	public ArrayList<NotaDeCredito> obtenerNotasDeCreditoPorCliente(Cliente cli) {
		ArrayList<NotaDeCredito> lista = (ArrayList<NotaDeCredito>) this.getNotasDeCredito().stream().filter((nc) -> nc.getFacturaAsociada().getCliente() == cli)
				.collect(Collectors.toList());
		return lista;
		}
	
	public ArrayList<NotaDeCredito> mapearNcQueContengaFactura(ArrayList<NotaDeCredito> notasDeCredito, ArrayList<Factura> facturas ) {
		ArrayList<NotaDeCredito> retorno = new ArrayList<NotaDeCredito>();
		for(NotaDeCredito nc : notasDeCredito) {
			if(facturas.contains(nc.getFacturaAsociada()))
				retorno.add(nc);
		}
		return retorno;
	}
	
	
}
