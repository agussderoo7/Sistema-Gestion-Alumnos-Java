package dao;
import Entidades.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.*;

public class DAOInscripcion implements IDAO<Inscripcion>{
    private DAOCurso daoCurso;
    private DAOAlumno daoAlumno;
    private String DB_JDBC_DRIVER = "org.h2.Driver";
    private String DB_URL = "jdbc:h2:~/test";
    private String DB_USER = "sa";
    private String DB_PASSWORD = "";

    public DAOInscripcion() {
        this.daoCurso = new DAOCurso();
        this.daoAlumno = new DAOAlumno();
    }

    @Override
    public void insertar(Inscripcion elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("conectó.");

            // Al no incluir "idInscripcion" se activa el AUTO_INCREMENT en la base de datos en H2
            preparedStatement = connection.prepareStatement("INSERT INTO Inscripcion(idCurso, idAlumno, fechaInscripcion, notaFinal, importePagado) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, elemento.getCurso().getIdCurso());
            preparedStatement.setInt(2, elemento.getAlumno().getIdAlumno());
            preparedStatement.setDate(3, elemento.getFechaInscripcion());
            preparedStatement.setInt(4, elemento.getNotaFinal());
            preparedStatement.setDouble(5, elemento.getImportePagado());

            int resultado = preparedStatement.executeUpdate(); // ejecución de la base de datos
            System.out.println("Se insertó la inscripción correctamente"); // técnica antigua de debuggeo.
        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException("Fallo en base de datos al insertar la inscripcion: " + e.getMessage());
        }
    }

    @Override
    public void modificar(Inscripcion elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("UPDATE Inscripcion SET idCurso = ?, idAlumno = ?, fechaInscripcion = ?, notaFinal = ?, importePagado = ? WHERE idInscripcion = ?");
            preparedStatement.setInt(1, elemento.getCurso().getIdCurso());
            preparedStatement.setInt(2, elemento.getAlumno().getIdAlumno());
            preparedStatement.setDate(3, elemento.getFechaInscripcion());
            preparedStatement.setInt(4, elemento.getNotaFinal());
            preparedStatement.setDouble(5, elemento.getImportePagado());
            preparedStatement.setInt(6, elemento.getIdInscripcion());// se enumera según la consulta.
            int resultado = preparedStatement.executeUpdate();
            System.out.println("Se modificó la inscripción correctamente");
        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException("Fallo en base de datos al modificar la inscripción: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int idInscripcion) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            preparedStatement = connection.prepareStatement("DELETE FROM Inscripcion WHERE idInscripcion = ?");
            preparedStatement.setInt(1, idInscripcion);
            System.out.println("Se eliminó la inscripción correctamente");

            int resultado = preparedStatement.executeUpdate();
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw new DAOException("Fallo en base de datos al eliminar la inscripción: " + e.getMessage());
        }
    }

    @Override
    public Inscripcion consultar(int idInscripcion) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Inscripcion inscripcion = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Inscripcion WHERE idInscripcion = ?");

            preparedStatement.setInt(1, idInscripcion);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                inscripcion = new Inscripcion();
                inscripcion.setIdInscripcion(rs.getInt("idInscripcion"));
                int idCurso = rs.getInt("idCurso");
                Curso c = daoCurso.consultar(idCurso);
                inscripcion.setCurso(c);
                int idAlumno = rs.getInt("idAlumno");
                Alumno a = daoAlumno.consultar(idAlumno);
                inscripcion.setAlumno(a);
                inscripcion.setFechaInscripcion(rs.getDate("fechaInscripcion"));
                inscripcion.setNotaFinal(rs.getInt("notaFinal"));
                inscripcion.setImportePagado(rs.getDouble("importePagado"));
            }

        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException("Fallo en base de datos al consultar la inscripción: " + e.getMessage());
        }
        return inscripcion;
    }

    @Override
    public List<Inscripcion> consultarTodos() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        List<Inscripcion> inscripciones = new ArrayList<>();
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Inscripcion");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Inscripcion inscripcion = new Inscripcion();
                inscripcion.setIdInscripcion(rs.getInt("idInscripcion"));
                int idCurso = rs.getInt("idCurso");
                Curso c = daoCurso.consultar(idCurso);
                inscripcion.setCurso(c);
                int idAlumno = rs.getInt("idAlumno");
                Alumno a = daoAlumno.consultar(idAlumno);
                inscripcion.setAlumno(a);
                inscripcion.setFechaInscripcion(rs.getDate("fechaInscripcion"));
                inscripcion.setNotaFinal(rs.getInt("notaFinal"));
                inscripcion.setImportePagado(rs.getDouble("importePagado"));

                inscripciones.add(inscripcion);
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException("Fallo en base de datos al consultar todas las inscripciones: " + e.getMessage());
        }
        return inscripciones;
    }

    public List<Inscripcion> consultarPorAlumno(int idAlumno) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        List<Inscripcion> inscripciones = new ArrayList<>();
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Inscripcion WHERE idAlumno = ?");
            preparedStatement.setInt(1, idAlumno);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Inscripcion insc = consultar(rs.getInt("idInscripcion"));
                inscripciones.add(insc);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException("Fallo en base de datos al consultar inscripción por alumno: " + e.getMessage());
        }
        return inscripciones;
    }

    public int contarInscriptos(int idCurso) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int contador = 0;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM Inscripcion WHERE idCurso = ?");
            preparedStatement.setInt(1, idCurso);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                contador = rs.getInt(1);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException("Fallo en base de datos al contar inscriptos: " + e.getMessage());
        }
        return contador;
    }

    public List<Inscripcion> consultarPorCurso(int idCurso) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Inscripcion> inscripciones = new ArrayList<>();
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Inscripcion WHERE idCurso = ?");
            preparedStatement.setInt(1, idCurso);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                // Reutilizamos el metodo consultar() para no repetir código
                Inscripcion insc = consultar(rs.getInt("idInscripcion"));
                inscripciones.add(insc);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException("Fallo en base de datos al consultar inscripción por curso: " + e.getMessage());
        }
        return inscripciones;
    }
}