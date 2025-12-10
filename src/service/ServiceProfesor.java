package service;
import Entidades.Profesor;
import dao.*;
import java.util.List;

public class ServiceProfesor {
    private DAOProfesor daoProfesor;

    public ServiceProfesor() {
        this.daoProfesor = new DAOProfesor();
    }

    public void crearProfesor(String nombre, String apellido, String email) throws ServiceException {
        try {
            Profesor profesor = new Profesor();
            profesor.setNombre(nombre);
            profesor.setApellido(apellido);
            profesor.setEmail(email);

            // idProfesor es AUTO_INCREMENT
            daoProfesor.insertar(profesor);

        } catch (DAOException e) {
            throw new ServiceException("Error al crear el profesor: " + e.getMessage());
        }
    }

    public void modificarProfesor(int idProfesor, String nombre, String apellido, String email) throws ServiceException {
        try {
            Profesor profesorExistente = daoProfesor.consultar(idProfesor);
            if (profesorExistente == null) {
                throw new ServiceException("No existe el profesor con ID: " + idProfesor);
            }

            profesorExistente.setNombre(nombre);
            profesorExistente.setApellido(apellido);
            profesorExistente.setEmail(email);

            daoProfesor.modificar(profesorExistente);
        } catch (DAOException e) {
            throw new ServiceException("Error al modificar el profesor: " + e.getMessage());
        }
    }

    public void eliminarProfesor(int idProfesor) throws ServiceException {
        try {
            daoProfesor.eliminar(idProfesor);
        } catch (DAOException e) {
            throw new ServiceException("Error al eliminar el profesor: " + e.getMessage());
        }
    }

    public Profesor buscarProfesor(int idProfesor) throws ServiceException {
        try {
            return daoProfesor.consultar(idProfesor);
        } catch (DAOException e) {
            throw new ServiceException("Error al buscar el profesor: " + e.getMessage());
        }
    }

    public List<Profesor> buscarTodosLosProfesores() throws ServiceException {
        try {
            return daoProfesor.consultarTodos();
        } catch (DAOException e) {
            throw new ServiceException("Error al buscar todos los profesores: " + e.getMessage());
        }
    }
}