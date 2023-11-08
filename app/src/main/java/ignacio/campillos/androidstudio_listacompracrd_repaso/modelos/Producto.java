package ignacio.campillos.androidstudio_listacompracrd_repaso.modelos;

import java.io.Serializable;

public class Producto implements Serializable {

    private String nombre;
    private int precio, cantidad;

    private boolean comprado;

    public Producto(String nombre, int precio, int cantidad, boolean comprado) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.comprado = comprado;
    }

    public boolean isComprado() {
        return comprado;
    }

    public void setComprado(boolean comprado) {
        this.comprado = comprado;
    }

    public Producto() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", cantidad=" + cantidad +
                '}';
    }
}
