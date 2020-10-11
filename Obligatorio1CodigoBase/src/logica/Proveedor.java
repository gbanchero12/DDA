package logica;

import java.util.ArrayList;

public class Proveedor {
    
    private String nombre;
    private ArrayList<Producto> productos = new ArrayList();

    public Proveedor(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }
    public void agregar(Producto p){
        productos.add(p);
    }

    @Override
    public String toString() {
        return "Proveedor{" + "nombre=" + nombre + '}';
    }
}
