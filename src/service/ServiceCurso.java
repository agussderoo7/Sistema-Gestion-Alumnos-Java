package service;
import Entidades.*;
import dao.*;
import java.sql.Date;
import java.util.List;

public class ServiceCurso {
    private DAOCurso daoCurso;
    private DAOInscripcion daoInscripcion;
    private DAOProfesor daoProfesor;

    public ServiceCurso() {
        this.daoCurso = new DAOCurso();
        this.daoInscripcion = new DAOInscripcion();
        this.daoProfesor = new DAOProfesor();
    }

    public void crearCurso(String nombreCurso, float precioCurso, int cupo, int notaAprobacion, int cantidadParciales, Date fechaPromocionDesde, Date fechaPromocionHasta, float precioPromocion, int idProfesor) throws ServiceException {
        try {
            Profesor p = daoProfesor.consultar(idProfesor);
            if (p == null) {
                throw new ServiceException("No existe el profesor con ID: " + idProfesor);
            }

            // Evitar que se creen 2 cursos iguales
            List<Curso> cursos = daoCurso.consultarTodos();
            for (Curso c : cursos){
                if (c.getNombreCurso().equalsIgnoreCase(nombreCurso)){
                    throw new ServiceException("Ya existe un curso con el nombre '" + c.getNombreCurso() + "'");
                }
            }

            Curso curso = new Curso();
            curso.setProfesor(p);
            curso.setNombreCurso(nombreCurso);
            curso.setPrecioCurso(precioCurso);
            curso.setCupo(cupo);
            curso.setNotaAprobacion(notaAprobacion);
            curso.setCantidadParciales(cantidadParciales);
            curso.setFechaPromocionDesde(fechaPromocionDesde);
            curso.setFechaPromocionHasta(fechaPromocionHasta);
            curso.setPrecioPromocion(precioPromocion);


            // El idCurso es AUTO_INCREMENT en la base de datos por lo tanto no lo seteamos
            daoCurso.insertar(curso);

        } catch (DAOException e) {
            throw new ServiceException("Error al crear el curso: " + e.getMessage());
        }
    }

    public void modificarCurso(Curso curso) throws ServiceException {
        try {
            daoCurso.modificar(curso);
        } catch (DAOException e) {
            throw new ServiceException("Error al modificar el curso: " + e.getMessage());
        }
    }

    public void eliminarCurso(int idCurso) throws ServiceException {
        try {
            daoCurso.eliminar(idCurso);
        } catch (DAOException e) {
            throw new ServiceException("Error al eliminar el curso con ID: " + idCurso);
        }
    }

    public Curso buscarCurso(int idCurso) throws ServiceException {
        try {
            return daoCurso.consultar(idCurso);
        } catch (DAOException e) {
            throw new ServiceException("Error al buscar el curso con ID: " + idCurso);
        }
    }

    public List<Curso> buscarTodosLosCursos() throws ServiceException {
        try {
            return daoCurso.consultarTodos();
        } catch (DAOException e) {
            throw new ServiceException("Error al buscar todos los cursos: " + e.getMessage());
        }
    }

    public String generarReporteCurso(int idCurso) throws ServiceException {
        try {
            Curso curso = daoCurso.consultar(idCurso);
            if (curso == null) {
                throw new ServiceException("El curso con ID " + idCurso + " no existe.");
            }

            List<Inscripcion> inscripciones = daoInscripcion.consultarPorCurso(idCurso);
            int totalInscriptos = inscripciones.size();
            int totalAprobados = 0;
            double recaudacionTotal = 0.0;
            int notaMinimaAprobacion = curso.getNotaAprobacion();

            for (Inscripcion insc : inscripciones) {
                recaudacionTotal += insc.getImportePagado();
                if (insc.getNotaFinal() >= notaMinimaAprobacion) {
                    totalAprobados++;
                }
            }

            String reporte = "";
            reporte += "\nReporte del curso\n";
            reporte += "\nCurso: " + curso.getNombreCurso() + " (ID: " + idCurso + ")";
            reporte += "\nProfesor: " + curso.getProfesor().getNombre() + " " + curso.getProfesor().getApellido();
            reporte += "\nTotal de Alumnos Inscriptos: " + totalInscriptos;
            reporte += "\nTotal de Alumnos Aprobados: " + totalAprobados;
            reporte += "\nRecaudación Total del Curso: $" + recaudacionTotal;
            return reporte;

        } catch (DAOException e) {
            throw new ServiceException("Error al generar el reporte: " + e.getMessage());
        }
    }
}
