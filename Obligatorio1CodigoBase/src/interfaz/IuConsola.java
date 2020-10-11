package interfaz;

import java.io.Console;
import java.util.ArrayList;
import logica.Cliente;
import logica.Fachada;
import logica.Factura;
import logica.LineaFactura;
import logica.NotaDeCredito;
import logica.Producto;
import logica.Proveedor;
import utilidades.Consola;

public class IuConsola {

	Fachada logica = Fachada.getInstancia();

	/**
	 * Ejecuta la consola
	 */
	public void mostrarConsola() {
		boolean salir = false;
		do {

			int opcion = imprimirMenu();
			salir = procesarOpcion(opcion);

		} while (!salir);
	}

	/**
	 * Imprime el menÃº y sus opciones a pantalla
	 */
	private int imprimirMenu() {
		System.out.println("MENU");
		System.out.println("====");

		ArrayList<String> opciones = new ArrayList<>();
		opciones.add("Alta de Cliente");
		opciones.add("Alta de Producto");
		opciones.add("Alta de Factura");
		opciones.add("Consulta clientes");
		opciones.add("Alta de Nota de credito");
		opciones.add("Consulta de Notas de credito emitidas");
		opciones.add("Salir del menú");
		return Consola.menu(opciones);
	}

	/**
	 * Captura la opciÃ³n seleccionada por el usuario y ejecuta la acciÃ³n
	 * correspondiente
	 */
	private boolean procesarOpcion(int opcion) {
		boolean salir = false;
		int numero;

		switch (opcion) {
		case 0:
			this.nuevoCliente();
			break;
		case 1:
			nuevoProducto();
			break;

		case 2:
			nuevoFactura();
			break;

		case 3:
			consultarClientes();
			break;

		case 4:
			emisionNotaDeCredito();
			break;
		case 5:
			consultaNotasDeCredito();
			break;
		case 6:
			salir = true;
			break;

		}
		return salir;
	}

	private void consultaNotasDeCredito() {
		ArrayList<NotaDeCredito> listado = new ArrayList<>();
		System.out.println("Lista Clientes:");
		Consola.listar(logica.getClientes());
		System.out.println("Ingrese cedula de cliente a consultar:");
		Cliente cli = logica.buscarClientePorCedula(Consola.leer());
		if (cli == null) {
			System.out.println("Cliente no encontrado");
			return;
		} else {
			System.out.println("Has seleccionado: " + cli.getNombre());
		}
		
		System.out.println("Lista Proveedores:");
		Consola.listar(logica.getProveedores());
		System.out.println("Ingrese nombre de proveedor a consultar:");
		Proveedor prov = logica.getProveedorPorNombre(Consola.leer());
		if (prov == null) {
			System.out.println("Proveedor no encontrado");
			return;
		} else {
			System.out.println("Has seleccionado: " + prov.getNombre());
		}
		
		ArrayList<Factura> facturas = logica.obtenerFacturasPorProveedor(prov);
		ArrayList<NotaDeCredito> notasDeCredito = logica.obtenerNotasDeCreditoPorCliente(cli);

		for(NotaDeCredito nc : notasDeCredito) {
			if(logica.buscarFacturaPorCodigo(nc.getFacturaAsociada().getNumero()) != null)
				listado.add(nc);
		}
		
		mostrarListadoNc(listado);
	}
	
	private void mostrarListadoNc(ArrayList<NotaDeCredito> listado) {
		for (NotaDeCredito nc : listado) {
			System.out.println("CodigoNotaCredito:" + nc.getNumero() + 
					" - CodigoFac: " + nc.getFacturaAsociada().getNumero()
					+ " - Cantidad de Productos " + nc.getFacturaAsociada().getLineas().size()
					+ " - Fecha NC: " + nc.getFecha()
					+ " - Fecha F: " + nc.getFacturaAsociada().getFecha()
					+ " Monto Total: " + nc.getFacturaAsociada().total());
		}
	}

	private void emisionNotaDeCredito() {
		System.out.println("ALTA DE NOTA DE CREDITO");
		System.out.println("===============");
		if (logica.getFacturas().isEmpty()) {
			System.out.println("No existen facturas!");
			return;
		}
		Factura factura = null;
		while ((factura = logica
				.buscarFacturaPorCodigo(Consola.leerInt("Ingrese numero de factura para asociar NC: "))) == null) {
			System.out.println("No se encontro factura con numero proporcionado.");
			return;
		}
		mostrarFactura(factura);
		System.out.println("¿Confirma emision de nota de credito por esta factura?");
		System.out.println("Ingrese 'Y' para continuar");

		if (!(Consola.leer().toUpperCase()).equals("Y")) {
			System.out.println("No se emitio nota de credito...");
			return;
		}
		NotaDeCredito nc = new NotaDeCredito();
		System.out.println("Intentando crear nota de credito...");

		if (!factura.getMedioDePago().equals("Tarjeta de Crédito")) {
			System.out.println("ERROR: Solo se pueden anular facturas pagas con Tarjeta.");
			return;
		}

		logica.agregarNotaDeCredito(nc, factura);
		factura.getLineas().stream()
				.forEach((linea) -> logica.devolverProducto(linea.getProducto(), linea.getCantidad()));

		System.out.println("Se anuló la factura correctamente");

	}

	private void nuevoCliente() {

		System.out.println("ALTA DE CLIENTE");
		System.out.println("===============");

		Cliente unCliente = new Cliente();
		unCliente.setCedula(Consola.leer("Cedula:"));
		unCliente.setNombre(Consola.leer("Nombre:"));
		if (logica.agregarCliente(unCliente)) {
			mostrarClientes();
		} else
			System.out.println("EL CLIENTE NO FUE INGRESADO");

	}

	private void mostrarClientes() {
		System.out.println("=================");
		System.out.println("CLIENTES ACTUALES");
		System.out.println("=================");
		ArrayList<Cliente> clientes = logica.getClientes();
		for (Cliente c : clientes) {
			System.out.println(c.getCedula() + " - " + c.getNombre());
		}
	}

	private void nuevoProducto() {

		System.out.println("ALTA DE PRODUCTO");
		System.out.println("===============");

		Producto prod = new Producto();

		boolean ok = false;
		do {
			ok = prod.setNombre(Consola.leer("Nombre:"));
		} while (!ok);

		while (!prod.setUnidades(Consola.leerInt("Unidades:")))
			;

		ArrayList<Proveedor> provs = logica.getProveedores();

		int p = Consola.menu(provs);

		Proveedor select = provs.get(p);

		prod.setProveedor(select);

		while (!prod.setPrecio(Consola.leerInt("Precio:")))
			;

		if (logica.agregarProducto(prod)) {
			System.out.println("PRODUCTO AGREGADO");
		} else {
			System.out.println("ERROR AL AGREGAR EL PRODUCTO");
		}
	}

	private void nuevoFactura() {
		System.out.println("ALTA DE FACTURA");
		System.out.println("===============");

		Cliente c = logica.buscarClientePorCedula(Consola.leer("Cedula:"));
		if (c == null) {
			System.out.println("No existe el cliente");
			return;
		}
		System.out.println("Ingrese medio de pago: ");
		System.out.println("1.Tarjeta");
		System.out.println("2.Efectivo");
		System.out.println("3.Cheque");
		Integer medioDePago = 0;
		while ((medioDePago = Consola.leerInt()) > 3 || medioDePago <= 0) {
			System.out.println("Especifique medio de pago.");
		}

		Factura nuevaFactura = new Factura(c);
		nuevaFactura.setMedioDePago(medioDePago);
		boolean ok, salir = false;
		while (!salir) {
			ok = nuevaFactura.agregarPorCodigoProducto(Consola.leerInt("Cantidad:"), Consola.leerInt("Cod. Producto:"));

			if (ok) {
				mostrarFactura(nuevaFactura);
			} else {
				System.out.println("Error al agregar el producto.");
			}
			ArrayList<String> opciones = new ArrayList<>();
			opciones.add("Finalizar");
			opciones.add("Continuar");
			opciones.add("Descartar");
			int opcion = Consola.menu(opciones);
			switch (opcion) {
			case 0:
				logica.agregarFactura(nuevaFactura);
				mostrarFactura(nuevaFactura);
				salir = true;
				break;

			case 1:
				break;

			case 2:
				salir = true;
				break;
			}
		}
	}

	private void mostrarFactura(Factura factura) {
		int x = 1;
		String mensaje = null;

		System.out.println("Factura Código: " + factura.getNumero());
		System.out.println("Cliente: " + factura.getCliente().getNombre());
		System.out.println("Medio de pago: " + factura.getMedioDePago());

		System.out.println("Detalle:");

		for (LineaFactura linea : factura.getLineas()) {
			mensaje = x + " - Cod Producto: " + linea.getProducto().getCodigo() + " - Nombre del producto: "
					+ linea.getProducto().getNombre() + " - Cantidad: " + linea.getCantidad() + " - Precio Unitario: "
					+ linea.getProducto().getPrecio() + " - SubTotal: " + " $ " + linea.total();
			System.out.println(mensaje);
			x++;
		}
		System.out.println("***************");
		System.out.println("Total $ : " + factura.total());
	}

	private void consultarClientes() {
		System.out.println("=================");
		System.out.println("CONSULTAR CLIENTES");
		System.out.println("=================");
		if (logica.getProductos().isEmpty()) {
			System.out.println("No existen productos ingresados.");
			return;
		}
		String mensaje;
		Producto menor = logica.getProductoMenorPrecio();
		mensaje = "(" + menor.getCodigo() + ") " + menor.getNombre() + " - " + " $ " + menor.getCodigo() + " - "
				+ menor.getUnidades();
		System.out.println(mensaje);
		ArrayList<Cliente> clientes = logica.clientesCompraronProductoMenorPrecio();

		if (clientes.isEmpty()) {
			System.out.println("No existen clientes que compraron el producto de menor precio.");
			return;
		}

		for (Cliente c : clientes) {
			mensaje = c.getCedula() + " - " + c.getNombre() + " - "
					+ logica.clienteFechaUltimaFacturaPorProducto(c, menor);
			System.out.println(mensaje);
		}
	}
}
