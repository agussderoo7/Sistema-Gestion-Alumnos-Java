package Entidades;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Entidades.*;

public class Curso {
    private int idCurso;
    private Profesor profesor;
    private String nombreCurso;
    private float precioCurso;
    private int cupo;
    private int notaAprobacion;
    private int cantidadParciales;
    private Date fechaPromocionDesde;
    private Date fechaPromocionHasta;
    private float precioPromocion;

    public Curso() {
    }

    public Curso(int idCurso, Profesor profesor, String nombreCurso, float precioCurso, int cupo, int notaAprobacion, int cantidadParciales, Date fechaPromocionDesde, Date fechaPromocionHasta, float precioPromocion) {
        this.idCurso = idCurso;
        this.profesor = profesor;
        this.nombreCurso = nombreCurso;
        this.precioCurso = precioCurso;
        this.cupo = cupo;
        this.notaAprobacion = notaAprobacion;
        this.cantidadParciales = cantidadParciales;
        this.fechaPromocionDesde = fechaPromocionDesde;
        this.fechaPromocionHasta = fechaPromocionHasta;
        this.precioPromocion = precioPromocion;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public float getPrecioCurso() {
        return precioCurso;
    }

    public int getCupo() {
        return cupo;
    }

    public int getNotaAprobacion() {
        return notaAprobacion;
    }

    public int getCantidadParciales() {
        return cantidadParciales;
    }

    public Date getFechaPromocionDesde() {
        return fechaPromocionDesde;
    }

    public Date getFechaPromocionHasta() {
        return fechaPromocionHasta;
    }

    public float getPrecioPromocion() {
        return precioPromocion;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public void setPrecioCurso(float precioCurso) {
        this.precioCurso = precioCurso;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public void setNotaAprobacion(int notaAprobacion) {
        this.notaAprobacion = notaAprobacion;
    }

    public void setCantidadParciales(int cantidadParciales) {
        this.cantidadParciales = cantidadParciales;
    }

    public void setFechaPromocionDesde(Date fechaPromocionDesde) {
        this.fechaPromocionDesde = fechaPromocionDesde;
    }

    public void setFechaPromocionHasta(Date fechaPromocionHasta) {
        this.fechaPromocionHasta = fechaPromocionHasta;
    }

    public void setPrecioPromocion(float precioPromocion) {
        this.precioPromocion = precioPromocion;
    }

    @Override
    public String toString() {
        return "\nID Curso: " + idCurso +
                "\nProfesor: " + profesor +
                "\nNombre del curso: " + nombreCurso +
                "\nPrecio del curso: " + precioCurso +
                "\nCupo: " + cupo +
                "\nNota de aprobacion: " + notaAprobacion +
                "\nCantidad de parciales: " + cantidadParciales +
                "\nFecha de promocion desde: " + fechaPromocionDesde +
                "\nFecha de promocion hasta: " + fechaPromocionHasta +
                "\nPrecio de promocion: " + precioPromocion;
    }
}