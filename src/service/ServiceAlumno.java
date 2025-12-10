package service;
import Entidades.*;
import dao.*;
import java.util.List;

public class ServiceAlumno {
    private DAOAlumno daoAlumno;
    private DAOAbono daoAbono;
    private DAOInscripcion daoInscripcion;

    public ServiceAlumno() {
        this.daoAlumno = new DAOAlumno();
        this.daoAbono = new DAOAbono();
        this.daoInscripcion = new DAOInscripcion();
    }

    public void crearAlumno(String nombre, String apellido, String email, int limiteCursos, int idAbono) throws ServiceException {
        try {
            Abono abono = null; // Por defecto, el alumno no tiene abono
            if (idAbono > 0) {
                abono = daoAbono.consultar(idAbono);
                if (abono == null) {
                    throw new ServiceException("No existe Abono con ID: " + idAbono);
                }
            }

            // Validamos que no hayan 2 alumnos iguales a través de su email (puede haber 2 "Juan Pérez" pero no 2 mails iguales)
            List<Alumno> alumnosExistentes = daoAlumno.consultarTodos();
            for (Alumno a : alumnosExistentes) {
                if (a.getEmail().equalsIgnoreCase(email)) {
                    throw new ServiceException("Ya existe un alumno con el email: " + email);
                }
            }

            Alumno alumno = new Alumno();
            alumno.setNombre(nombre);
            alumno.setApellido(apellido);
            alumno.setEmail(email);
            alumno.setLimiteCursos(limiteCursos);
            alumno.setAbono(abono);

            // idAlumno es AUTO_INCREMENT
            daoAlumno.insertar(alumno);
        } catch (DAOException e) {
            throw new ServiceException("Error al crear el alumno: " + e.getMessage());
        }
    }

    public void modificarAlumno(int idAlumno, String nombre, String apellido, String email, int limiteCursos, int idAbono) throws ServiceException {
        try {
            Alumno alumnoExistente = daoAlumno.consultar(idAlumno);
            if (alumnoExistente == null) {
                throw new ServiceException("No existe alumno con ID: " + idAlumno);
            }

            Abono abono = null; // Por defecto el alumno no tiene abono por eso null

            // Funcionalidad: Si se proporcionó un ID de abono, hay que buscarlo
            if (idAbono > 0) {
                abono = daoAbono.consultar(idAbono);
                if (abono == null) {
                    throw new ServiceException("No existe el abono con id: " + idAbono);
                }
            }
            alumnoExistente.setIdAlumno(idAlumno);
            alumnoExistente.setNombre(nombre);
            alumnoExistente.setApellido(apellido);
            alumnoExistente.setEmail(email);
            alumnoExistente.setLimiteCursos(limiteCursos);
            alumnoExistente.setAbono(abono); // Asigna el nuevo objeto Abono (o null)

            daoAlumno.modificar(alumnoExistente);
        } catch (DAOException e) {
            throw new ServiceException("Error al modificar el alumno: " + e.getMessage());
        }
    }

    public void eliminarAlumno(int idAlumno) throws ServiceException {
        try {
            daoAlumno.eliminar(idAlumno);
        } catch (DAOException e) {
            throw new ServiceException("Error al eliminar el alumno: " + e.getMessage());
        }
    }

    public Alumno buscarAlumno(int idAlumno) throws ServiceException {
        try {
            return daoAlumno.consultar(idAlumno);
        } catch (DAOException e) {
            throw new ServiceException("Error al buscar el alumno: " + e.getMessage());
        }
    }

    public List<Alumno> buscarTodosLosAlumnos() throws ServiceException {
        try {
            return daoAlumno.consultarTodos();
        } catch (DAOException e) {
            throw new ServiceException("Error al buscar todos los alumnos: " + e.getMessage());
        }
    }

    public double calcularTotalPagadoPorAlumno(int idAlumno) throws ServiceException {
        try {
            List<Inscripcion> inscripciones = daoInscripcion.consultarPorAlumno(idAlumno);

            double totalPagado = 0.0;
            for (Inscripcion insc : inscripciones) {
                totalPagado += insc.getImportePagado();
            }

            return totalPagado;
        } catch (DAOException e) {
            throw new ServiceException("Error al calcular el total pagado por el alumno: " + e.getMessage());
        }
    }
}
