package Entidades;
import java.util.*;

public class Alumno {
    private int idAlumno;
    private String nombre;
    private String apellido;
    private String email;
    private int limiteCursos;
    private Abono abono;;

    public Alumno() {
    }

    public Alumno(int idAlumno, String nombre, String apellido, String email, int limiteCursos) {
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.limiteCursos = limiteCursos;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public Abono getAbono() {
        return abono;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public int getLimiteCursos() { return limiteCursos; }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public void setAbono(Abono abono) {
        this.abono = abono;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLimiteCursos(int limiteCursos) {
        this.limiteCursos = limiteCursos;
    }

    @Override
    public String toString() {
        return "\nID Alumno: " + idAlumno + "\nNombre: " + nombre + "\nApellido: " + apellido + "\nEmail: " + email + "\nLimite de cursos: " + limiteCursos;
    }
}