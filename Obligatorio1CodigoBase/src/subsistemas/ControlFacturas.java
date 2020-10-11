package subsistemas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import logica.Cliente;
import logica.Factura;
import logica.LineaFactura;
import logica.Producto;
import logica.Proveedor;

public class ControlFacturas {

	private ArrayList<Factura> facturas = new ArrayList();
	private int proximoNumero;

	public ControlFacturas() {

	}

	public ArrayList<Factura> getFacturas() {
		return facturas;
	}

	public void agregar(Factura unaFactura) {
		unaFactura.setNumero(generarProximoNumero());
		unaFactura.asignarFecha();
		unaFactura.bajarStock();
		facturas.add(unaFactura);
	}

	public boolean clienteComproProducto(Cliente c, Producto p) {
		boolean ret = false;
		for (Factura f : facturas) {
			if (f.getCliente().equals(c) && f.tieneProducto(p)) {
				ret = true;
			}
		}

		return ret;
	}

	public Date clienteFechaUltimaFacturaPorProducto(Cliente c, Producto p) {
		Date ret = null;

		for (Factura f : facturas) {
			if (f.getCliente().equals(c) && f.tieneProducto(p)) {
				ret = f.getFecha();
			}
		}
		return ret;
	}

	private int generarProximoNumero() {
		proximoNumero++;
		return proximoNumero;
	}

	public Factura buscarFacturaPorCodigo(int codigo) {
		for (Factura f : this.facturas) {
			if (f.getNumero() == codigo)
				return f;
		}
		return null;
	}

	public ArrayList<Factura> obtenerFacturasPorProveedor(Proveedor pro) {
		ArrayList<Factura> facturas = new ArrayList<>();
		ArrayList<LineaFactura> filtroLineasFactura = new ArrayList<>();
		this.facturas.forEach(factura -> filtroLineasFactura.addAll(factura.getLineas().stream()
				.filter(l -> l.getProducto().getProveedor() == pro).collect(Collectors.toList())));

		for (Factura f : this.facturas) {
			for (LineaFactura lf : filtroLineasFactura) {
				if (f.getLineas().contains(lf))
					facturas.add(f);
			}
		}
		return facturas;
	}

}
