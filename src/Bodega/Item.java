package Bodega;

public class Item {
    private long id;
    private String nombre;
    private double precio;

    public Item() {}

    public Item(long id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public long getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
}