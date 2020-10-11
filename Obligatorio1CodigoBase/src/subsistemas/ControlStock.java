
package subsistemas;

import java.util.ArrayList;
import java.util.Optional;

import dominio.Producto;
import dominio.Proveedor;
import jdk.nashorn.internal.runtime.FindProperty;

public class ControlStock {

	private int proxIdProd = 1;

	private ArrayList<Producto> productos = new ArrayList();
	private ArrayList<Proveedor> proveedores = new ArrayList();

	public ControlStock() {
	}

	public ArrayList<Producto> getProductos() {
		return productos;
	}

	public ArrayList<Proveedor> getProveedores() {
		return proveedores;
	}

	public Producto getProductoMenorPrecio() {
		Producto menor = productos.get(0);
		Producto p;
		for (int x = 1; x < productos.size(); x++) {
			p = productos.get(x);
			if (p.getPrecio() < menor.getPrecio()) {
				menor = p;
			}
		}

		return menor;
	}

	public void agregar(Proveedor unProveedor) {
		proveedores.add(unProveedor);
	}

	public boolean agregar(Producto unProducto) {
		if (!unProducto.validar())
			return false;
		unProducto.setCodigo(proxIdProd);
		proxIdProd++;
		productos.add(unProducto);
		unProducto.getProveedor().agregar(unProducto);
		return true;
	}

	public Producto buscarProductoPorCodigo(int codigoProducto) {
		for (Producto p : productos) {
			if (p.getCodigo() == codigoProducto) {
				return p;
			}
		}
		return null;
	}

	public boolean agregarProductoDevuelto(Producto unProducto) {
		if (!unProducto.validar())
			return false;
		productos.add(unProducto);
		unProducto.getProveedor().agregar(unProducto);
		return true;
	}

	public boolean devolverProducto(Producto p, int cantidad) {
		for (int i = 0; i < cantidad; i++) {
			p.devolverUnidad(p);
			if (!agregarProductoDevuelto(p))
				return false;
		}
		return true;
	}

	public Proveedor buscarProveedorPorNombre(String nombre) {

		// busqueda exacta
		Optional<Proveedor> proveedor = this.getProveedores().stream()
				.filter(prov -> prov.getNombre().toUpperCase().equals(nombre.toUpperCase())).findAny();
		if (proveedor.isPresent()) {
			return (Proveedor) proveedor.get();
		} else {
			// busqueda por aproximacion
			proveedor = this.getProveedores().stream()
					.filter(prov -> prov.getNombre().toUpperCase().contains(nombre.toUpperCase())).findAny();

			if (proveedor.isPresent()) {
				return (Proveedor) proveedor.get();
			}

		}
		return null;
	}
}
