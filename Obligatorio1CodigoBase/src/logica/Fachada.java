package logica;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Fachada {
    
    private static Fachada instancia;
    
    private ControlClientes cc = new ControlClientes();
    private ControlFacturas cf = new ControlFacturas();
    private ControlStock cs = new ControlStock();
    private ControlNotasDeCredito cn = new ControlNotasDeCredito();
    
    public static Fachada getInstancia(){
        
        if (instancia==null){
            instancia =  new Fachada();
        }
        return instancia;
    }
    
    public boolean devolverProducto(Producto p, int cantidad) {
    	return cs.devolverProducto(p, cantidad);
    }
    
    public ArrayList<NotaDeCredito> getNotasDeCredito() {
    	return cn.getNotasDeCredito(); 
    }
    
    public void agregarNotaDeCredito(NotaDeCredito nc, Factura facturaAsociada) {
    	cn.agregar(nc, facturaAsociada);
    }
    
    public boolean facturaAnulada(Factura factura) {
    	return cn.facturaAnulada(factura);
    }
    
    public ArrayList<Cliente> getClientes() {
        return cc.getClientes();
    }
    
    public ArrayList clientesNoCompraronProductoMenorPrecio(){
        return cc.clientesNoCompraronProductoMenorPrecio();
    }
    
    public ArrayList<Cliente> clientesCompraronProductoMenorPrecio() {
        return cc.clientesCompraronProductoMenorPrecio();
    }

    public boolean existeCliente(String unaCedula) {
           return cc.existeCliente(unaCedula);
    }
    
    public Cliente buscarClientePorCedula(String unaCedula) {
           return cc.buscarClientePorCedula(unaCedula);
    }
  
    public boolean agregarCliente(Cliente c){
        return cc.agregar(c);
    }
    
    public ArrayList<Factura> getFacturas() {
        return cf.getFacturas();
    }
    
    public void agregarFactura(Factura unaFactura){
        cf.agregar(unaFactura);
    }
    
    public boolean clienteComproProducto(Cliente c, Producto p){
        return cf.clienteComproProducto(c, p);
    }
    
    public Date clienteFechaUltimaFacturaPorProducto(Cliente c, Producto p){
        return cf.clienteFechaUltimaFacturaPorProducto(c, p);
    }
    
    public ArrayList<Producto> getProductos() {
        return cs.getProductos();
    }

    public ArrayList<Proveedor> getProveedores() {
        return cs.getProveedores();
    }
    
    public Proveedor getProveedorPorNombre(String nombre) {
    	return cs.buscarProveedorPorNombre(nombre);
    }
    
    public Producto getProductoMenorPrecio(){
        return cs.getProductoMenorPrecio();
    }
  
    public void agregarProveedor(Proveedor unProveedor){
        cs.agregar(unProveedor);
    }
    
    public  boolean agregarProducto(Producto unProducto){
        return cs.agregar(unProducto);
    }
    
    public Producto buscarProductoPorCodigo(int codigoProducto) {
        return cs.buscarProductoPorCodigo(codigoProducto);
    }

	public Factura buscarFacturaPorCodigo(int codigo) {
		return cf.buscarFacturaPorCodigo(codigo);
	}
	
	public ArrayList<Factura> obtenerFacturasPorProveedor(Proveedor pro){
		return cf.obtenerFacturasPorProveedor(pro);
	}
	
	public ArrayList<NotaDeCredito> obtenerNotasDeCreditoPorCliente(Cliente cli){
		return cn.obtenerNotasDeCreditoPorCliente(cli);
	}
}
