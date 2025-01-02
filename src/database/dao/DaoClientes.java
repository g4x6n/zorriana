package database.dao;

import database.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoClientes extends Conexion {

    // Método para buscar clientes según un filtro de nombre
    public List<Object[]> searchClientsByName(String filter) {
        conectar(); // Establece conexión con la base de datos
        List<Object[]> clients = new ArrayList<>(); // Lista para almacenar los resultados

        try {
            // Consulta SQL para buscar clientes por nombre o caracteres similares
            sentenciaSQL = "SELECT ID_CLIENTE, NOMBRE, AP_PATERNO, AP_MATERNO, FECHA_REG, CORREO " +
                           "FROM CLIENTE " +
                           "WHERE UPPER(NOMBRE) LIKE UPPER(?)";

            ps = conn.prepareStatement(sentenciaSQL);
            ps.setString(1, filter + "%"); // Agrega el filtro con un comodín al final

            rs = ps.executeQuery();

            while (rs.next()) {
                // Agrega cada resultado a la lista como un arreglo de objetos
                Object[] client = new Object[6];
                client[0] = rs.getObject("ID_CLIENTE");
                client[1] = rs.getString("NOMBRE");
                client[2] = rs.getString("AP_PATERNO");
                client[3] = rs.getString("AP_MATERNO");
                client[4] = rs.getDate("FECHA_REG");
                client[5] = rs.getString("CORREO");
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

    // Método para obtener la dirección de un cliente por nombre
    public String getDireccionByClientName(String nombre) {
        conectar(); // Establece conexión con la base de datos
        String direccion = null;

        try {
            String sentenciaSQL = "SELECT DIRECCION FROM CLIENTE WHERE NOMBRE = ?";
            PreparedStatement ps = conn.prepareStatement(sentenciaSQL);
            ps.setString(1, nombre); // Establece el parámetro del nombre

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                direccion = rs.getString("DIRECCION"); // Obtiene la dirección
            }
        } catch (SQLException ex) {
            System.out.println("Error SQL: " + ex.getSQLState());
            System.out.println("Mensaje: " + ex.getMessage());
        } finally {
            desconectar(); // Cierra la conexión
        }

        return direccion; // Devuelve la dirección o null si no se encontró
    }
}
