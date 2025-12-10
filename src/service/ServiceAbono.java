package service;
import Entidades.Abono;
import dao.*;
import java.util.List;

public class ServiceAbono {
    private DAOAbono daoAbono;

    public ServiceAbono() {
        this.daoAbono = new DAOAbono();
    }

    public void crearAbono(String nombre, double valor, double descuento) throws ServiceException {
        try {
            if (descuento < 0 || descuento > 100) {
                throw new ServiceException("El descuento debe ser un porcentaje entre 0 y 100.");
            }
            Abono abono = new Abono();
            abono.setNombre(nombre);
            abono.setValor(valor);
            abono.setDescuento(descuento);

            // idAbono es AUTO_INCREMENT
            daoAbono.insertar(abono);
        } catch (DAOException e) {
            throw new ServiceException("Error al crear el abono: " + e.getMessage());
        }
    }

    public void modificarAbono(int idAbono, String nombre, double valor, double descuento) throws ServiceException {
        try {
            if (descuento < 0 || descuento > 100) {
                throw new ServiceException("El descuento debe ser un porcentaje entre 0 y 100.");
            }

            Abono abonoExistente = daoAbono.consultar(idAbono);
            if (abonoExistente == null) {
                throw new ServiceException("No existe el abono con ID: " + idAbono);
            }
            abonoExistente.setNombre(nombre);
            abonoExistente.setValor(valor);
            abonoExistente.setDescuento(descuento);

            daoAbono.modificar(abonoExistente);
        } catch (DAOException e) {
            throw new ServiceException("Error al modificar el abono: " + e.getMessage());
        }
    }

    public void eliminarAbono(int idAbono) throws ServiceException {
        try {
            daoAbono.eliminar(idAbono);
        } catch (DAOException e) {
            throw new ServiceException("Error al eliminar el abono: " + e.getMessage());
        }
    }

    public Abono buscarAbono(int idAbono) throws ServiceException {
        try {
            return daoAbono.consultar(idAbono);
        } catch (DAOException e) {
            throw new ServiceException("Error al buscar el abono: " + e.getMessage());
        }
    }

    public List<Abono> buscarTodosLosAbonos() throws ServiceException {
        try {
            return daoAbono.consultarTodos();
        } catch (DAOException e) {
            throw new ServiceException("Error al buscar todos los abonos: " + e.getMessage());
        }
    }
}