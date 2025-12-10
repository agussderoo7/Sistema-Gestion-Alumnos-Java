package Entidades;

public class NotaParcial {
    private int idNota;
    private Inscripcion inscripcion;
    private int nota;

    public NotaParcial() {
    }

    public NotaParcial(int idNota, Inscripcion inscripcion, int nota) {
        this.idNota = idNota;
        this.inscripcion = inscripcion;
        this.nota = nota;
    }

    public int getIdNota() {
        return idNota;
    }

    public Inscripcion getInscripcion() {
        return inscripcion;
    }

    public int getNota() {
        return nota;
    }

    public void setIdNota(int idNota){
        this.idNota = idNota;
    }

    public void setInscripcion(Inscripcion inscripcion) {
        this.inscripcion = inscripcion;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }
}
