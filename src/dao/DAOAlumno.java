package dao;
import Entidades.Abono;
import Entidades.Alumno;
import dao.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.*;

public class DAOAlumno implements IDAO<Alumno>{
    private DAOAbono daoAbono;
    private String DB_JDBC_DRIVER = "org.h2.Driver";
    private String DB_URL = "jdbc:h2:~/test";
    private String DB_USER = "sa";
    private String DB_PASSWORD = "";

    public DAOAlumno() {
        this.daoAbono = new DAOAbono();
    }

    @Override
    public void insertar(Alumno elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("conectó.");

            preparedStatement = connection.prepareStatement("INSERT INTO Alumno(nombre, apellido, email, limiteCursos, idAbono) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, elemento.getNombre());
            preparedStatement.setString(2, elemento.getApellido());
            preparedStatement.setString(3, elemento.getEmail());
            preparedStatement.setInt(4, elemento.getLimiteCursos());
            if (elemento.getAbono() != null) {
                preparedStatement.setInt(5, elemento.getAbono().getIdAbono());
            } else {
                preparedStatement.setNull(5, java.sql.Types.INTEGER);
            }
            int resultado = preparedStatement.executeUpdate(); // ejecución de la base de datos
            System.out.println("Se insertó el alumno correctamente"); // técnica antigua de debuggeo.
        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException("Fallo en base de datos al insertar alumno: " + e.getMessage());
        }
    }

    @Override
    public void modificar(Alumno elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("UPDATE Alumno SET nombre = ?, apellido = ?, email = ?, limiteCursos = ?, idAbono = ? WHERE idAlumno = ?");
            preparedStatement.setString(1, elemento.getNombre());
            preparedStatement.setString(2, elemento.getApellido());
            preparedStatement.setString(3, elemento.getEmail());
            preparedStatement.setInt(4, elemento.getLimiteCursos());
            if (elemento.getAbono() != null) {
                preparedStatement.setInt(5, elemento.getAbono().getIdAbono());
            } else {
                preparedStatement.setNull(5, java.sql.Types.INTEGER);
            }
            preparedStatement.setInt(6, elemento.getIdAlumno()); // El ID para el WHERE

            int resultado = preparedStatement.executeUpdate();
            System.out.println("Se modificó el alumno correctamente");
        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException("Fallo en base de datos al modificar alumno: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int idAlumno) {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("DELETE FROM Alumno WHERE idAlumno = ?");
            preparedStatement.setInt(1, idAlumno);
            System.out.println("Se eliminó el alumno correctamente.");

            int resultado=preparedStatement.executeUpdate();
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw new DAOException("Fallo en base de datos al eliminar alumno: " + e.getMessage());
        }
    }

    @Override
    public Alumno consultar(int idAlumno) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Alumno alumno = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Alumno WHERE idAlumno = ?");
            preparedStatement.setInt(1, idAlumno);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){ // pregunta "si hay algo"
                alumno = new Alumno();
                alumno.setIdAlumno(rs.getInt("idAlumno"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setEmail(rs.getString("email"));
                alumno.setLimiteCursos(rs.getInt("limiteCursos"));
                int idAbono = rs.getInt("idAbono");
                if (idAbono > 0) {
                    Abono abono = daoAbono.consultar(idAbono);
                    alumno.setAbono(abono);
                }
            }

        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException("Fallo en base de datos al consultar alumno: " + e.getMessage());
        }
        return alumno;
    }

    @Override
    public List<Alumno> consultarTodos() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        List<Alumno> alumnos;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Alumno");

            ResultSet rs = preparedStatement.executeQuery();

            alumnos = new ArrayList<>();

            while (rs.next()) {
                // Reutilizo el metodo consultar que ya "ensambla" el objeto Abono
                int idAlumno = rs.getInt("idAlumno");
                Alumno alumno = consultar(idAlumno);
                alumnos.add(alumno);
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException("Fallo en base de datos al consultar todos los alumnos: " + e.getMessage());
        }
        return alumnos;
    }
}