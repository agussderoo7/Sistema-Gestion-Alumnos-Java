package service;
import Entidades.*;
import dao.*;
import java.util.Date;
import java.util.List;

public class ServiceInscripcion {
    private DAOInscripcion daoInscripcion = new DAOInscripcion();
    private DAOAlumno daoAlumno = new DAOAlumno();
    private DAOCurso daoCurso = new DAOCurso();

    public void inscribirAlumno(int idAlumno, int idCurso) throws ServiceException {
        try {
            Alumno alumno = daoAlumno.consultar(idAlumno);
            Curso curso = daoCurso.consultar(idCurso);

            if (alumno == null) {
                throw new ServiceException("No existe el alumno con ID: " + idAlumno);
            }
            if (curso == null) {
                throw new ServiceException("No existe el curso con ID: " + idCurso);
            }

            List<Inscripcion> inscripcionesActuales = daoInscripcion.consultarPorAlumno(idAlumno);

            // Funcionalidad: Verificar si el alumno ya está inscripto en el curso
            for (Inscripcion i : inscripcionesActuales) {
                if (i.getCurso().getIdCurso() == idCurso) {
                    throw new ServiceException("El alumno: '" + alumno.getNombre() + " " + alumno.getApellido() + "' ya está inscripto en el curso '" + curso.getNombreCurso());
                }
            }

            // Funcionalidad: Límite de cursos del alumno
            if (inscripcionesActuales.size() >= alumno.getLimiteCursos()) {
                throw new ServiceException("El alumno '" + alumno.getNombre() + "' ya alcanzó su límite de " + alumno.getLimiteCursos() + " cursos.");
            }

            // Funcionalidad: Manejar cupos
            int cupoActual = daoInscripcion.contarInscriptos(idCurso);
            if (cupoActual >= curso.getCupo()) {
                throw new ServiceException("El curso '" + curso.getNombreCurso() + "' ya no tiene cupo disponible.");
            }

            float precioAPagar = curso.getPrecioCurso();
            Date hoy = new Date();

            // Funcionalidad: Manejar promociones por fecha
            if (curso.getFechaPromocionDesde() != null && curso.getFechaPromocionHasta() != null) {
                // Comprueba si la fecha de hoy está dentro del rango de promoción
                if (hoy.after(curso.getFechaPromocionDesde()) && hoy.before(curso.getFechaPromocionHasta())) {
                    precioAPagar = curso.getPrecioPromocion();
                }
            }

            // Funcionalidad bonus
            Abono abono = alumno.getAbono(); // Obtiene el abono del alumno (si es que tiene)
            if (abono != null) {
                // Cumple la funcionalidad del bonus: Si el abono es gratis (valor 0 o descuento 100%) anotarse le cuesta 0 pesos.
                if (abono.getValor() == 0 || abono.getDescuento() == 100) {
                    precioAPagar = 0;
                }
                // Si el abono tiene un descuento (ej: 50%)
                else if (abono.getDescuento() > 0) {
                    precioAPagar = precioAPagar - (precioAPagar * (float) (abono.getDescuento() / 100.0));
                }
            }

            Inscripcion nuevaInscripcion = new Inscripcion();
            nuevaInscripcion.setAlumno(alumno);
            nuevaInscripcion.setCurso(curso);
            nuevaInscripcion.setFechaInscripcion(new java.sql.Date(hoy.getTime()));
            nuevaInscripcion.setImportePagado(precioAPagar);
            nuevaInscripcion.setNotaFinal(0);

            daoInscripcion.insertar(nuevaInscripcion);
        } catch (DAOException e) {
            throw new ServiceException("Error de base de datos al inscribir alumno: " + e.getMessage());
        }
    }

    public List<Inscripcion> consultarPorAlumno(int idAlumno) throws ServiceException {
        try {
            return daoInscripcion.consultarPorAlumno(idAlumno);

        } catch (DAOException e) {
            throw new ServiceException("Error al consultar las inscripciones del alumno: " + e.getMessage());
        }
    }

    public List<Inscripcion> consultarPorCurso(int idCurso) throws ServiceException {
        try {
            return daoInscripcion.consultarPorCurso(idCurso);

        } catch (DAOException e) {
            throw new ServiceException("Error al consultar las inscripciones del curso: " + e.getMessage());
        }
    }

    public List<Inscripcion> consultarTodas() throws ServiceException{
        try {
            return daoInscripcion.consultarTodos();
        }
        catch (DAOException e){
            throw new ServiceException("Error al consultar todas las inscripciones: " + e.getMessage());
        }
    }

    public void cancelarInscripcion(int idInscripcion) throws ServiceException {
        try {
            daoInscripcion.eliminar(idInscripcion);
        } catch (DAOException e) {
            throw new ServiceException("Error al cancelar la inscripción del alumno: " + e.getMessage());
        }
    }
}
