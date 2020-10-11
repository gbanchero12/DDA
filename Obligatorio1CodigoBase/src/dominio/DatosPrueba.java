package dominio;

import dominio.Cliente;
import dominio.Factura;
import dominio.Producto;
import dominio.Proveedor;
import fachada.Fachada;

public class DatosPrueba {

    public static void cargar(){
       
        Fachada logica = Fachada.getInstancia();
        
        Proveedor pA = new Proveedor("Proveedor A");
        Proveedor pB = new Proveedor("Proveedor B");
        Proveedor pC = new Proveedor("Proveedor C");
        
        logica.agregarProveedor(pA);
        logica.agregarProveedor(pB);
        logica.agregarProveedor(pC);
        
        Producto caramelo = new Producto("Caramelo",2,3000,pA);
        Producto camisa = new Producto("Camisa",1300,1000,pB);
        Producto computadora = new Producto("Computadora",20000,40,pC);
        
        logica.agregarProducto(caramelo);
        logica.agregarProducto(camisa);
        logica.agregarProducto(computadora);

        Cliente juan = new Cliente("12345678","Juan");
        Cliente ana = new Cliente("13456789","Ana");
        Cliente mario = new Cliente("21234567","Mario");
        
        logica.agregarCliente(juan);
        logica.agregarCliente(ana);
        logica.agregarCliente(mario);
        
        Factura f1 = new Factura(juan);
        f1.agregar(30, caramelo);
        f1.agregar(2, camisa);
        f1.agregar(1, computadora);
        f1.setMedioDePago(1);
        
        logica.agregarFactura(f1);
        
        Factura f2 = new Factura(ana);
        f2.agregar(400, caramelo);
        f2.agregar(20, camisa);
        f2.agregar(10, computadora);
        f2.setMedioDePago(1);
        
        logica.agregarFactura(f2);
        
        Factura f3 = new Factura(mario);
      
        f3.agregar(1, camisa);
        f3.agregar(1, computadora);
        f3.setMedioDePago(3);
        
        logica.agregarFactura(f3);
    }
}
