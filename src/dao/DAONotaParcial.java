package dao;
import Entidades.Inscripcion;
import Entidades.NotaParcial;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAONotaParcial implements IDAO<NotaParcial> {
    private String DB_JDBC_DRIVER = "org.h2.Driver";
    private String DB_URL = "jdbc:h2:~/test";
    private String DB_USER = "sa";
    private String DB_PASSWORD = "";
    private DAOInscripcion daoInscripcion;

    public DAONotaParcial() {
        this.daoInscripcion = new DAOInscripcion();
    }

    @Override
    public void insertar(NotaParcial elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Asumimos que idNota es AUTO_INCREMENT
            preparedStatement = connection.prepareStatement("INSERT INTO NotaParcial(idInscripcion, nota) VALUES (?, ?)");
            preparedStatement.setInt(1, elemento.getInscripcion().getIdInscripcion());
            preparedStatement.setInt(2, elemento.getNota());

            int resultado = preparedStatement.executeUpdate();
            System.out.println("Se insertó la nota del parcial correctamente");

        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException("Fallo en la base de datos al insertar nota del parcial: " + e.getMessage());
        }
    }

    @Override
    public void modificar(NotaParcial elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            preparedStatement = connection.prepareStatement("UPDATE NotaParcial SET idInscripcion = ?, nota = ? WHERE idNota = ?");

            preparedStatement.setInt(1, elemento.getInscripcion().getIdInscripcion());
            preparedStatement.setInt(2, elemento.getNota());
            preparedStatement.setInt(3, elemento.getIdNota()); // ID para el WHERE

            int resultado = preparedStatement.executeUpdate();
            System.out.println("Se modificó la nota del parcial correctamente");

        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException("Fallo en la base de datos al modificar la nota del parcial: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int idNota) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            preparedStatement = connection.prepareStatement("DELETE FROM NotaParcial WHERE idNota = ?");
            preparedStatement.setInt(1, idNota);

            int resultado = preparedStatement.executeUpdate();
            System.out.println("Se eliminó la nota del parcial correctamente");

        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException("Fallo en la base de datos al eliminar la nota del parcial: " + e.getMessage());
        }
    }

    @Override
    public NotaParcial consultar(int idNota) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        NotaParcial nota = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM NotaParcial WHERE idNota = ?");
            preparedStatement.setInt(1, idNota);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                nota = new NotaParcial();
                nota.setIdNota(rs.getInt("idNota"));
                nota.setNota(rs.getInt("nota"));

                int idInscripcion = rs.getInt("idInscripcion");
                Inscripcion insc = daoInscripcion.consultar(idInscripcion);
                nota.setInscripcion(insc);
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException("Fallo en la base de datos al consultar la nota del parcial: " + e.getMessage());
        }
        return nota; // Devuelve la nota encontrada o null
    }

    @Override
    public List<NotaParcial> consultarTodos() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<NotaParcial> listaNotas = new ArrayList<>();

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            preparedStatement = connection.prepareStatement("SELECT * FROM NotaParcial");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                // Reutilizamos consultar() para no repetir código
                NotaParcial nota = consultar(rs.getInt("idNota"));
                listaNotas.add(nota);
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException("Fallo en la base de datos al consultar todas las notas: " + e.getMessage());
        }
        return listaNotas;
    }

    public List<NotaParcial> consultarPorInscripcion(int idInscripcion) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<NotaParcial> listaNotas = new ArrayList<>();

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            preparedStatement = connection.prepareStatement("SELECT * FROM NotaParcial WHERE idInscripcion = ?");
            preparedStatement.setInt(1, idInscripcion);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                NotaParcial nota = consultar(rs.getInt("idNota"));
                listaNotas.add(nota);
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException("Fallo en base de datos al consultar notas por inscripción: " + e.getMessage());
        }
        return listaNotas;
    }
}