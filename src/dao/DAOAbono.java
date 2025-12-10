package dao;
import Entidades.Abono;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOAbono implements IDAO<Abono> {
    private String DB_JDBC_DRIVER = "org.h2.Driver";
    private String DB_URL = "jdbc:h2:~/test";
    private String DB_USER = "sa";
    private String DB_PASSWORD = "";

    @Override
    public void insertar(Abono elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Asumimos que idAbono es AUTO_INCREMENT
            preparedStatement = connection.prepareStatement("INSERT INTO Abono(nombre, valor, descuento) VALUES (?, ?, ?)");
            preparedStatement.setString(1, elemento.getNombre());
            preparedStatement.setDouble(2, elemento.getValor());
            preparedStatement.setDouble(3, elemento.getDescuento());

            int resultado = preparedStatement.executeUpdate();
            System.out.println("Se insertó Abono correctamente");

        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException("Fallo en base de datos al insertar abono: " + e.getMessage());
        }
    }

    @Override
    public void modificar(Abono elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            preparedStatement = connection.prepareStatement("UPDATE Abono SET nombre = ?, valor = ?, descuento = ? WHERE idAbono = ?");
            preparedStatement.setString(1, elemento.getNombre());
            preparedStatement.setDouble(2, elemento.getValor());
            preparedStatement.setDouble(3, elemento.getDescuento());
            preparedStatement.setInt(4, elemento.getIdAbono());

            int resultado = preparedStatement.executeUpdate();
            System.out.println("Se modificó el abono correctamente");

        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException("Fallo en base de datos: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int idAbono) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            preparedStatement = connection.prepareStatement("DELETE FROM Abono WHERE idAbono = ?");
            preparedStatement.setInt(1, idAbono);

            int resultado = preparedStatement.executeUpdate();
            System.out.println("Se eliminó el abono correctamente");

        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException("Fallo en base de datos al eliminar Abono: " + e.getMessage());
        }
    }

    @Override
    public Abono consultar(int idAbono) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Abono abono = new Abono();
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Abono WHERE idAbono = ?");
            preparedStatement.setInt(1, idAbono);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                abono.setIdAbono(rs.getInt("idAbono"));
                abono.setNombre(rs.getString("nombre"));
                abono.setValor(rs.getDouble("valor"));
                abono.setDescuento(rs.getDouble("descuento"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException("Fallo en base de datos al consultar abono: " + e.getMessage());
        }
        return abono;
    }

    @Override
    public List<Abono> consultarTodos() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Abono> Abonos = new ArrayList<>();

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            preparedStatement = connection.prepareStatement("SELECT * FROM Abono");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Abono abono = new Abono();
                abono.setIdAbono(rs.getInt("idAbono"));
                abono.setNombre(rs.getString("nombre"));
                abono.setValor(rs.getDouble("valor"));
                abono.setDescuento(rs.getDouble("descuento"));

                Abonos.add(abono);
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException("Fallo en base de datos al consultar todos los abonos: " + e.getMessage());
        }
        return Abonos;
    }
}