package dao;
import Entidades.Profesor;
import java.sql.*;
import java.util.ArrayList;
import java.util.*;

public class DAOProfesor implements IDAO<Profesor>{
    private String DB_JDBC_DRIVER = "org.h2.Driver";
    private String DB_URL = "jdbc:h2:~/test";
    private String DB_USER = "sa";
    private String DB_PASSWORD = "";

    @Override
    public void insertar(Profesor elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("conectó.");

            preparedStatement = connection.prepareStatement("INSERT INTO Profesor(nombre, apellido, email) VALUES (?, ?, ?)");
            preparedStatement.setString(1, elemento.getNombre());
            preparedStatement.setString(2, elemento.getApellido());
            preparedStatement.setString(3, elemento.getEmail());
            int resultado = preparedStatement.executeUpdate(); // ejecución de la base de datos
            System.out.println("Se insertó el profesor correctamente"); // técnica antigua de debuggeo.
        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException("Fallo en base de datos al insertar profesor: " + e.getMessage());
        }
    }

    @Override
    public void modificar(Profesor elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("UPDATE Profesor SET nombre = ?, apellido = ?, email = ? WHERE idProfesor = ?");
            preparedStatement.setString(1, elemento.getNombre());
            preparedStatement.setString(2, elemento.getApellido());
            preparedStatement.setString(3, elemento.getEmail());
            preparedStatement.setInt(4, elemento.getIdProfesor()); // se enumera según la consulta.
            int resultado = preparedStatement.executeUpdate();
            System.out.println("Se modificó el profesor correctamente");
        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException("Fallo en base de datos al modificar profesor: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int idProfesor) {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            preparedStatement = connection.prepareStatement("DELETE FROM Curso WHERE idProfesor = ?");
            preparedStatement.setInt(1, idProfesor);
            System.out.println("Se eliminó correctamente");

            int resultado=preparedStatement.executeUpdate();
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw new DAOException("Fallo en base de datos al eliminar profesor: " + e.getMessage());
        }
    }

    @Override
    public Profesor consultar(int idProfesor) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Profesor Profesor = new Profesor();
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Profesor WHERE idProfesor = ?");

            preparedStatement.setInt(1, idProfesor);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){ // pregunta "si hay algo"
                Profesor.setIdProfesor(rs.getInt("idProfesor"));
                Profesor.setNombre(rs.getString("nombre"));
                Profesor.setApellido(rs.getString("apellido"));
                Profesor.setEmail(rs.getString("email"));
            }

        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException("Fallo en base de datos al consultar profesor: " + e.getMessage());
        }
        return Profesor;
    }

    @Override
    public List<Profesor> consultarTodos() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        List<Profesor> Profesores;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Profesor");

            ResultSet rs = preparedStatement.executeQuery();

            Profesores = new ArrayList<>();

            while (rs.next()) {
                Profesores.add(new Profesor(rs.getInt("idProfesor"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("email")));
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException("Fallo en base de datos al consultar todos los profesores: " + e.getMessage());
        }
        return Profesores;
    }
}
