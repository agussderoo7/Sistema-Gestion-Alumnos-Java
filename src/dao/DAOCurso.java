package dao;
import Entidades.Curso;
import Entidades.Profesor;
import java.sql.*;
import java.util.ArrayList;
import java.util.*;

public class DAOCurso implements IDAO<Curso>{
    private DAOProfesor daoProfesor;
    private String DB_JDBC_DRIVER = "org.h2.Driver";
    private String DB_URL = "jdbc:h2:~/test";
    private String DB_USER = "sa";
    private String DB_PASSWORD = "";

    public DAOCurso() {
        this.daoProfesor = new DAOProfesor();
    }

    @Override
    public void insertar(Curso elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("conectó.");

            preparedStatement = connection.prepareStatement("INSERT INTO Curso(idProfesor, nombreCurso, precioCurso, cupo, notaAprobacion, cantidadParciales, fechaPromocionDesde, fechaPromocionHasta, precioPromocion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, elemento.getProfesor().getIdProfesor());
            preparedStatement.setString(2, elemento.getNombreCurso());
            preparedStatement.setFloat(3, elemento.getPrecioCurso());
            preparedStatement.setInt(4, elemento.getCupo());
            preparedStatement.setInt(5, elemento.getNotaAprobacion());
            preparedStatement.setInt(6, elemento.getCantidadParciales());
            preparedStatement.setDate(7, elemento.getFechaPromocionDesde());
            preparedStatement.setDate(8, elemento.getFechaPromocionHasta());
            preparedStatement.setFloat(9, elemento.getPrecioPromocion());
            int resultado = preparedStatement.executeUpdate(); // ejecución de la base de datos
            System.out.println("Se insertó el curso correctamente"); // técnica antigua de debuggeo.
        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException("Fallo en base de datos al insertar el curso: " + e.getMessage());
        }
    }

    @Override
    public void modificar(Curso elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("UPDATE Curso SET idProfesor = ?, nombreCurso = ?, precioCurso = ?, cupo = ?, notaAprobacion = ?, cantidadParciales = ?, fechaPromocionDesde = ?, fechaPromocionHasta = ?, precioPromocion = ? WHERE idCurso = ?");
            preparedStatement.setInt(1, elemento.getProfesor().getIdProfesor());
            preparedStatement.setString(2, elemento.getNombreCurso());
            preparedStatement.setFloat(3, elemento.getPrecioCurso());
            preparedStatement.setInt(4, elemento.getCupo());
            preparedStatement.setInt(5, elemento.getNotaAprobacion());
            preparedStatement.setInt(6, elemento.getCantidadParciales());
            preparedStatement.setDate(7, elemento.getFechaPromocionDesde());
            preparedStatement.setDate(8, elemento.getFechaPromocionHasta());
            preparedStatement.setFloat(9, elemento.getPrecioPromocion());
            preparedStatement.setInt(10, elemento.getIdCurso()); // se enumera según la consulta.
            int resultado = preparedStatement.executeUpdate();
            System.out.println("Se modificó el curso correctamente");
        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException("Fallo en base de datos al modificar el curso: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int idCurso) {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            preparedStatement = connection.prepareStatement("DELETE FROM Curso WHERE idCurso = ?");
            preparedStatement.setInt(1, idCurso);
            System.out.println("Se eliminó el curso correctamente");

            int resultado=preparedStatement.executeUpdate();
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw new DAOException("Fallo en base de datos al eliminar el curso: " + e.getMessage());
        }
    }

    @Override
    public Curso consultar(int idCurso) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Curso Curso = new Curso();
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Curso WHERE idCurso = ?");

            preparedStatement.setInt(1, idCurso);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){ // pregunta "si hay algo"
                Curso.setIdCurso(rs.getInt("idCurso"));
                int idProfesor = rs.getInt("idProfesor");
                Profesor p = daoProfesor.consultar(idProfesor);
                Curso.setProfesor(p);
                Curso.setNombreCurso(rs.getString("nombreCurso"));
                Curso.setPrecioCurso(rs.getFloat("precioCurso"));
                Curso.setCupo(rs.getInt("cupo"));
                Curso.setNotaAprobacion(rs.getInt("notaAprobacion"));
                Curso.setCantidadParciales(rs.getInt("cantidadParciales"));
                Curso.setFechaPromocionDesde(rs.getDate("fechaPromocionDesde"));
                Curso.setFechaPromocionHasta(rs.getDate("fechaPromocionHasta"));
                Curso.setPrecioPromocion(rs.getFloat("precioPromocion"));
            }

        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException("Fallo en base de datos al consultar el curso: " + e.getMessage());
        }
        return Curso;
    }

    @Override
    public List<Curso> consultarTodos() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Curso> cursos = new ArrayList<>(); // Nombre de variable en minúscula

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Curso");

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Curso curso = new Curso();

                curso.setIdCurso(rs.getInt("idCurso"));
                curso.setNombreCurso(rs.getString("nombreCurso"));
                curso.setPrecioCurso(rs.getFloat("precioCurso"));
                curso.setCupo(rs.getInt("cupo"));
                curso.setNotaAprobacion(rs.getInt("notaAprobacion"));
                curso.setCantidadParciales(rs.getInt("cantidadParciales"));
                curso.setFechaPromocionDesde(rs.getDate("fechaPromocionDesde"));
                curso.setFechaPromocionHasta(rs.getDate("fechaPromocionHasta"));
                curso.setPrecioPromocion(rs.getFloat("precioPromocion"));

                int idProfesor = rs.getInt("idProfesor");
                Profesor p = daoProfesor.consultar(idProfesor);
                curso.setProfesor(p);

                cursos.add(curso);
                /* En vez de poner:
                Curso curso = new Curso(rs.getInt("idCurso"), p, rs.getString("nombreCurso"), rs.getFloat("precioCurso"), rs.getInt("cupo"), rs.getInt("notaAprobacion"), rs.getInt("cantidadParciales"), rs.getDate("fechaPromocionDesde"), rs.getDate("fechaPromocionHasta"), rs.getFloat("precioPromocion"));
                Voy poniendo los setters así es más legible
                 */
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException("Fallo en base de datos al consultar todos los cursos: " + e.getMessage());
        }
        return cursos;
    }
}