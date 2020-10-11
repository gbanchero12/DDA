package logica;

public class LineaFactura {
    
    private Producto producto;
    private int cantidad;

    public LineaFactura(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public boolean tieneProducto(Producto unP){
        return this.getProducto().equals(unP);
    }

    @Override
    public String toString() {
        return getProducto().getCodigo() + " - " + 
                    getProducto().getNombre() + " - " + 
                    getCantidad() + " - " + 
                    " $ " + total();
    }

    boolean incrementar(int cantidad) {
        if (this.cantidad + cantidad > producto.getUnidades()) {
            return false;
        }
        this.cantidad += cantidad;
        return true;
    }
    
    public float total() {
        return cantidad * producto.getPrecio();
    }
    
    protected void bajarStock() {
        producto.modificarStock(-cantidad);
    }
}
