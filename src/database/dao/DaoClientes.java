package database.dao;

import database.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoClientes extends Conexion {

    // Método para buscar clientes según un filtro de nombre, incluyendo dirección concatenada
    public List<Object[]> searchClientsByName(String filter) {
        conectar(); // Establece conexión con la base de datos
        List<Object[]> clients = new ArrayList<>(); // Lista para almacenar los resultados

        try {
            // Consulta SQL para buscar clientes por nombre con dirección concatenada
            sentenciaSQL = "SELECT " +
                           "C.NOMBRE, " +
                           "C.AP_PATERNO, " +
                           "C.AP_MATERNO, " +
                           "C.FECHA_REG, " +
                           "C.CORREO, " +
                           "CONCAT(D.CALLE, ' ', " +
                           "D.EXTERIOR, ' ', " +
                           "COALESCE(D.INTERIOR, ''), ', ', " +
                           "D.COLONIA, ', ', " +
                           "D.ALCAL_MUN, ', ', " +
                           "D.CP, ', ', " +
                           "E.ESTADO) AS DIRECCION " +
                           "FROM CLIENTE C " +
                           "JOIN DIRECCION D ON C.ID_DIRECCION = D.ID_DIRECCION " +
                           "JOIN ESTADO E ON D.ID_ESTADO = E.ID_ESTADO " +
                           "WHERE UPPER(C.NOMBRE) LIKE UPPER(?)";

            ps = conn.prepareStatement(sentenciaSQL);
            ps.setString(1, filter + "%"); // Agrega el filtro con un comodín al final

            rs = ps.executeQuery();

            while (rs.next()) {
                // Agrega cada resultado a la lista como un arreglo de objetos
                Object[] client = new Object[6];
                client[0] = rs.getString("NOMBRE");
                client[1] = rs.getString("AP_PATERNO");
                client[2] = rs.getString("AP_MATERNO");
                client[3] = rs.getDate("FECHA_REG");
                client[4] = rs.getString("CORREO");
                client[5] = rs.getString("DIRECCION");
                clients.add(client);
            }
        } catch (SQLException ex) {
            System.out.println("Error SQL: " + ex.getSQLState());
            System.out.println("Mensaje: " + ex.getMessage());
            System.out.println("Consulta: " + sentenciaSQL);
            clients = null; // Devuelve nulo en caso de error
        } finally {
            desconectar(); // Cierra la conexión a la base de datos
        }

        return clients; // Retorna la lista de clientes o nulo si ocurrió un error
    }
}
