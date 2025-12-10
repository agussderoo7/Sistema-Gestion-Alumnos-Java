package Entidades;
import java.sql.*;

public class Inscripcion {
    private int idInscripcion;
    private Curso curso;
    private Alumno alumno;
    private Date fechaInscripcion;
    private int notaFinal;
    private double importePagado;

    public Inscripcion() {
    }

    public Inscripcion(int idInscripcion, Curso curso, Alumno alumno, Date fechaInscripcion, int notaFinal) {
        this.idInscripcion = idInscripcion;
        this.curso = curso;
        this.alumno = alumno;
        this.fechaInscripcion = fechaInscripcion;
        this.notaFinal = notaFinal;
        this.importePagado = importePagado;
    }

    public int getIdInscripcion() {
        return idInscripcion;
    }

    public Curso getCurso() {
        return curso;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public int getNotaFinal() {
        return notaFinal;
    }

    public double getImportePagado() {
        return importePagado;
    }

    public void setIdInscripcion(int idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public void setNotaFinal(int notaFinal) { this.notaFinal = notaFinal; }

    public void setImportePagado(double importePagado) {
        this.importePagado = importePagado;
    }

    @Override
    public String toString() {
        return "\nID Inscripcion: " + idInscripcion + "\nCurso: " + curso + "\nAlumno: " + alumno + "\nFecha de Inscripcion: " + fechaInscripcion + "\nNota final: " + notaFinal;
    }
}