package dao;
import java.util.*;

public interface IDAO<T> {
    public void insertar(T elemento) throws DAOException;

    public void modificar(T elemento) throws DAOException;

    public void eliminar(int idAlumno);

    public T consultar(int idAlumno) throws DAOException;

    public List<T> consultarTodos() throws DAOException;
}
