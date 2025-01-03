package database.dao;

import database.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


public class DaoClientes extends Conexion {

    public boolean addClient(Object[] cliente) {
    conectar(); // Conectar a la base de datos
    String idCliente = generarIdCliente(); // Generar el ID del cliente automáticamente
    String idDireccion = generarIdDireccion(); // Generar el ID de la dirección automáticamente

    try {
        // Consulta SQL para insertar la dirección
        String sqlDireccion = "INSERT INTO DIRECCION (ID_DIRECCION, CALLE, EXTERIOR, INTERIOR, COLONIA, CP, ALCAL_MUN, ID_ESTADO) " +
                              "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sqlDireccion);
        ps.setString(1, idDireccion);
        ps.setString(2, (String) cliente[3]);  // Calle
        ps.setString(3, (String) cliente[4]);  // Exterior
        ps.setString(4, (String) cliente[5]);  // Interior
        ps.setString(5, (String) cliente[6]);  // Colonia
        ps.setString(6, (String) cliente[7]);  // CP
        ps.setString(7, (String) cliente[8]);  // Alcaldía/Municipio
        ps.setString(8, obtenerCodigoEstado((String) cliente[10])); // Código del estado

        // Ejecutar la consulta para insertar la dirección
        int filasDireccion = ps.executeUpdate();

        if (filasDireccion > 0) {
            // Consulta SQL para insertar el cliente
            String sqlCliente = "INSERT INTO CLIENTE (ID_CLIENTE, NOMBRE, AP_PATERNO, AP_MATERNO, CORREO, FECHA_REG, ID_DIRECCION) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sqlCliente);
            ps.setString(1, idCliente); // ID del cliente generado
            ps.setString(2, (String) cliente[0]); // Nombre
            ps.setString(3, (String) cliente[1]); // Apellido paterno
            ps.setString(4, (String) cliente[2]); // Apellido materno
            ps.setString(5, (String) cliente[11]); // Correo
            ps.setDate(6, Date.valueOf((String) cliente[9])); // Fecha de registro
            ps.setString(7, idDireccion); // ID de la dirección

            // Ejecutar la consulta para insertar el cliente
            int filasCliente = ps.executeUpdate();
            return filasCliente > 0; // Si se insertó, devolver verdadero
        }
    } catch (SQLException e) {
        System.out.println("Error al agregar cliente: " + e.getMessage());
        e.printStackTrace();
    } finally {
        desconectar(); // Cerrar la conexión a la base de datos
    }

    return false; // Si ocurre algún error, devolver falso
}

// Método para generar el ID del cliente
// Método para generar el ID del cliente
private String generarIdCliente() {
    String idCliente = "";
    try {
        String sql = "SELECT MAX(ID_CLIENTE) FROM CLIENTE";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();

        if (rs.next()) {
            String maxId = rs.getString(1);
            int year = LocalDate.now().getYear() % 100; // Obtener los dos últimos dígitos del año
            if (maxId != null && maxId.startsWith("C")) {
                int secuencia = Integer.parseInt(maxId.substring(3)) + 1; // Incrementar la secuencia
                idCliente = "C" + String.format("%02d", year) + String.format("%02d", secuencia);
            } else {
                idCliente = "C" + String.format("%02d", year) + "01"; // Primer ID del año
            }
        }
    } catch (SQLException ex) {
        System.out.println("Error al generar ID del cliente: " + ex.getMessage());
    }
    return idCliente;
}


// Método para generar el ID de la dirección
private String generarIdDireccion() {
    String idDireccion = "";
    try {
        String sql = "SELECT MAX(ID_DIRECCION) FROM DIRECCION WHERE ID_DIRECCION LIKE 'D%'";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();

        if (rs.next()) {
            String maxId = rs.getString(1);
            if (maxId != null && maxId.startsWith("D")) {
                int secuencia = Integer.parseInt(maxId.substring(1)) + 1;
                idDireccion = "D" + String.format("%04d", secuencia);
            } else {
                idDireccion = "D0001"; // Primer ID
            }
        }
    } catch (SQLException ex) {
        System.out.println("Error al generar ID de la dirección: " + ex.getMessage());
    }
    return idDireccion;
}

// Método para obtener el código del estado
private String obtenerCodigoEstado(String nombreEstado) {
    String codigoEstado = "";
    try {
        String sql = "SELECT ID_ESTADO FROM ESTADO WHERE UPPER(ESTADO) = UPPER(?)";
        ps = conn.prepareStatement(sql);
        ps.setString(1, nombreEstado);
        rs = ps.executeQuery();

        if (rs.next()) {
            codigoEstado = rs.getString(1);
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener código del estado: " + ex.getMessage());
    }
    return codigoEstado;
}
    // Buscar clientes con filtro
    public List<Object[]> buscarClientes(String filtro) {
        conectar();
        List<Object[]> clientes = new ArrayList<>();
        try {
            String sql = "SELECT C.ID_CLIENTE, C.NOMBRE, C.AP_PATERNO, C.AP_MATERNO, C.FECHA_REG, C.CORREO, " +
                         "D.CALLE || ' ' || NVL(D.EXTERIOR, '') || ' ' || NVL(D.INTERIOR, '') || ', ' || D.COLONIA || ', ' || D.ALCAL_MUN || ', ' || EST.ESTADO AS DIR " +
                         "FROM CLIENTE C " +
                         "JOIN DIRECCION D ON C.ID_DIRECCION = D.ID_DIRECCION " +
                         "JOIN ESTADO EST ON D.ID_ESTADO = EST.ID_ESTADO " +
                         "WHERE UPPER(C.NOMBRE) LIKE UPPER(?) OR UPPER(C.AP_PATERNO) LIKE UPPER(?) OR UPPER(C.AP_MATERNO) LIKE UPPER(?)";

            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + filtro + "%");
            ps.setString(2, "%" + filtro + "%");
            ps.setString(3, "%" + filtro + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                Object[] cliente = new Object[7];
                cliente[0] = rs.getString("ID_CLIENTE");
                cliente[1] = rs.getString("NOMBRE");
                cliente[2] = rs.getString("AP_PATERNO");
                cliente[3] = rs.getString("AP_MATERNO");
                cliente[4] = rs.getDate("FECHA_REG");
                cliente[5] = rs.getString("CORREO");
                cliente[6] = rs.getString("DIR"); // Dirección concatenada
                clientes.add(cliente);
            }
        } catch (SQLException ex) {
            System.out.println("Error al buscar clientes: " + ex.getMessage());
        } finally {
            desconectar();
        }
        return clientes;
    }
    
    public List<String> obtenerEstados() {
        conectar(); // Conectar a la base de datos
        List<String> estados = new ArrayList<>();
        try {
            String sql = "SELECT ESTADO FROM ESTADO"; // Consulta para obtener los nombres de los estados
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                estados.add(rs.getString("ESTADO")); // Agregar cada estado a la lista
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener estados: " + ex.getMessage());
        } finally {
            desconectar(); // Cerrar la conexión
        }
        return estados; // Retornar la lista de estados
    }


  public boolean updateClient(Object[] cliente) {
    conectar();
    try {
        // Actualizar los datos del cliente
        String sqlCliente = "UPDATE CLIENTE SET NOMBRE = ?, AP_PATERNO = ?, AP_MATERNO = ?, FECHA_REG = ?, CORREO = ? WHERE ID_CLIENTE = ?";
        ps = conn.prepareStatement(sqlCliente);
        ps.setString(1, (String) cliente[0]); // Nombre
        ps.setString(2, (String) cliente[1]); // Apellido Paterno
        ps.setString(3, (String) cliente[2]); // Apellido Materno
        ps.setDate(4, Date.valueOf((String) cliente[3])); // Fecha de Registro
        ps.setString(5, (String) cliente[4]); // Correo
        ps.setString(6, (String) cliente[5]); // ID del Cliente
        return ps.executeUpdate() > 0;
    } catch (SQLException ex) {
        System.out.println("Error al actualizar cliente: " + ex.getMessage());
        return false;
    } finally {
        desconectar();
    }
}

  public boolean deleteClient(String idCliente) {
    conectar(); // Conectar a la base de datos
    try {
        // Eliminar cliente
        String sqlCliente = "DELETE FROM CLIENTE WHERE ID_CLIENTE = ?";
        ps = conn.prepareStatement(sqlCliente);
        ps.setString(1, idCliente);
        int filasAfectadas = ps.executeUpdate();

        return filasAfectadas > 0; // Devuelve true si se eliminó
    } catch (SQLException ex) {
        System.out.println("Error al eliminar cliente: " + ex.getMessage());
        return false;
    } finally {
        desconectar(); // Cierra la conexión
    }
}


  public String obtenerIdDireccionPorCliente(String idCliente) {
    conectar();
    String idDireccion = null;
    try {
        String sql = "SELECT ID_DIRECCION FROM CLIENTE WHERE ID_CLIENTE = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, idCliente);
        rs = ps.executeQuery();

        if (rs.next()) {
            idDireccion = rs.getString("ID_DIRECCION");
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener ID de la dirección: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return idDireccion;
}
private javax.swing.JTable resultsTable;

  public List<Object[]> cargarClientes() {
    conectar();
    List<Object[]> clientes = new ArrayList<>();
    try {
        String sql = "SELECT C.ID_CLIENTE, C.NOMBRE, C.AP_PATERNO, C.AP_MATERNO, C.FECHA_REG, C.CORREO, " +
                     "D.CALLE || ' ' || NVL(D.EXTERIOR, '') || ' ' || NVL(D.INTERIOR, '') || ', ' || D.COLONIA || ', ' || D.ALCAL_MUN || ', ' || E.ESTADO AS DIR " +
                     "FROM CLIENTE C " +
                     "JOIN DIRECCION D ON C.ID_DIRECCION = D.ID_DIRECCION " +
                     "JOIN ESTADO E ON D.ID_ESTADO = E.ID_ESTADO";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            Object[] cliente = new Object[7];
            cliente[0] = rs.getString("ID_CLIENTE");
            cliente[1] = rs.getString("NOMBRE");
            cliente[2] = rs.getString("AP_PATERNO");
            cliente[3] = rs.getString("AP_MATERNO");
            cliente[4] = rs.getDate("FECHA_REG");
            cliente[5] = rs.getString("CORREO");
            cliente[6] = rs.getString("DIR"); // Dirección concatenada
            clientes.add(cliente);
        }
    } catch (SQLException ex) {
        System.out.println("Error al cargar clientes: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return clientes;
}

 
    
    // Obtener cliente por ID
    public Object[] obtenerClientePorId(String idCliente) {
        conectar(); 
        Object[] cliente = new Object[13]; 
        try {
            String sql = "SELECT C.ID_CLIENTE, C.NOMBRE, C.AP_PATERNO, C.AP_MATERNO, C.FECHA_REG, " +
                         "C.CORREO, D.CALLE, D.EXTERIOR, D.INTERIOR, D.COLONIA, D.CP, D.ALCAL_MUN, E.ESTADO " +
                         "FROM CLIENTE C " +
                         "JOIN DIRECCION D ON C.ID_DIRECCION = D.ID_DIRECCION " +
                         "JOIN ESTADO E ON D.ID_ESTADO = E.ID_ESTADO " +
                         "WHERE C.ID_CLIENTE = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, idCliente);
            rs = ps.executeQuery();

            if (rs.next()) {
                cliente[0] = rs.getString("ID_CLIENTE");
                cliente[1] = rs.getString("NOMBRE");
                cliente[2] = rs.getString("AP_PATERNO");
                cliente[3] = rs.getString("AP_MATERNO");
                cliente[4] = rs.getDate("FECHA_REG");
                cliente[5] = rs.getString("CORREO");
                cliente[6] = rs.getString("CALLE");
                cliente[7] = rs.getString("EXTERIOR");
                cliente[8] = rs.getString("INTERIOR");
                cliente[9] = rs.getString("COLONIA");
                cliente[10] = rs.getString("CP");
                cliente[11] = rs.getString("ALCAL_MUN");
                cliente[12] = rs.getString("ESTADO");
            } else {
                cliente = null;
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener detalles del cliente: " + ex.getMessage());
        } finally {
            desconectar();
        }
        return cliente;
    }
}
