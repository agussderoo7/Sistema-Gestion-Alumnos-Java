package service;
import Entidades.*;
import dao.*;
import java.util.List;

public class ServiceNotaParcial {
    private DAONotaParcial daoNotaParcial;
    private DAOInscripcion daoInscripcion;

    public ServiceNotaParcial() {
        this.daoNotaParcial = new DAONotaParcial();
        this.daoInscripcion = new DAOInscripcion();
    }

    public void agregarNotaParcial(int idInscripcion, int nota) throws ServiceException {
        try {
            Inscripcion inscripcion = daoInscripcion.consultar(idInscripcion);
            if (inscripcion == null) {
                throw new ServiceException("No se puede cargar la nota porque no existe la inscripción con ID: " + idInscripcion);
            }
            if (inscripcion.getNotaFinal() != 0) {
                throw new ServiceException("No se puede cargar la nota porque esta inscripción ya está cerrada con nota final.");
            }

            NotaParcial nuevaNota = new NotaParcial();
            nuevaNota.setInscripcion(inscripcion);
            nuevaNota.setNota(nota);
            // idNota es AUTO_INCREMENT
            daoNotaParcial.insertar(nuevaNota);
            actualizarNotaFinal(idInscripcion);
        } catch (DAOException e) {
            throw new ServiceException("Fallo de base de datos al agregar nota." + e.getMessage());
        }
    }

    private void actualizarNotaFinal(int idInscripcion) throws DAOException {
        Inscripcion inscripcion = daoInscripcion.consultar(idInscripcion);
        Curso curso = inscripcion.getCurso();

        int parcialesRequeridos = curso.getCantidadParciales();
        int notaMinimaAprobacion = curso.getNotaAprobacion();

        List<NotaParcial> parcialesCargados = daoNotaParcial.consultarPorInscripcion(idInscripcion);

        // Pregunta si se cargaron todas las notas necesarias
        if (parcialesCargados.size() < parcialesRequeridos) {
            return;
        }

        boolean todosAprobados = true;
        int sumaDeNotas = 0;
        for (NotaParcial nota : parcialesCargados) {
            sumaDeNotas += nota.getNota();
            if (nota.getNota() < notaMinimaAprobacion) {
                todosAprobados = false;
            }
        }

        if (todosAprobados) {
            // Si aprobó se calcula el promedio
            int notaFinalCalculada = sumaDeNotas / parcialesCargados.size();
            inscripcion.setNotaFinal(notaFinalCalculada);
        } else {
            // Si desaprobó se le asigna una nota de desaprobado (2)
            inscripcion.setNotaFinal(2);
        }

        daoInscripcion.modificar(inscripcion);
    }
}