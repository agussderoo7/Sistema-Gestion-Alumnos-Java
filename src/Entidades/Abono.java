package Entidades;

public class Abono {
    private int idAbono;
    private String nombre;
    private double valor;
    private double descuento;

    public Abono() {
    }

    public Abono(int idAbono, String nombre, double valor, double descuento) {
        this.idAbono = idAbono;
        this.nombre = nombre;
        this.valor = valor;
        this.descuento = descuento;
    }

    public int getIdAbono() {
        return idAbono;
    }

    public String getNombre() {
        return nombre;
    }

    public double getValor() {
        return valor;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setIdAbono(int idAbono) {
        this.idAbono = idAbono;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    @Override
    public String toString() {
        // JComboBox usa este texto para mostrar las opciones (Ej: "Ninguno", "Abono Beca", "Abono Premium")
        return this.nombre;
    }
}
