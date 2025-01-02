package database.dao;

import database.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoClientes extends Conexion {

    // Generar el ID del cliente
    private String generarIdCliente() {
        String idCliente = "";
        try {
            String sql = "SELECT MAX(ID_CLIENTE) FROM CLIENTE";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                String maxId = rs.getString(1);
                if (maxId != null) {
                    int secuencia = Integer.parseInt(maxId.substring(1)) + 1;
                    idCliente = "C" + String.format("%04d", secuencia);
                } else {
                    idCliente = "C0001"; // Primer ID
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al generar ID del cliente: " + ex.getMessage());
        }
        return idCliente;
    }

    // Generar el ID de la dirección
    private String generarIdDireccion() {
        String idDireccion = "";
        try {
            String sql = "SELECT MAX(ID_DIRECCION) FROM DIRECCION WHERE ID_DIRECCION LIKE 'D%'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                String maxId = rs.getString(1);
                if (maxId != null) {
                    int secuencia = Integer.parseInt(maxId.substring(1)) + 1;
                    idDireccion = "D" + secuencia;
                } else {
                    idDireccion = "D80";
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al generar ID de la dirección: " + ex.getMessage());
        }
        return idDireccion;
    }

    // Obtener código del estado
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

    // Agregar un nuevo cliente
    public boolean addClient(Object[] cliente) {
        conectar();
        String idCliente = generarIdCliente();
        String idDireccion = generarIdDireccion();
        try {
            // Obtener código del estado
            String codigoEstado = obtenerCodigoEstado((String) cliente[7]);

            // Insertar dirección
            String sqlDireccion = "INSERT INTO DIRECCION (ID_DIRECCION, CALLE, EXTERIOR, INTERIOR, COLONIA, CP, ALCAL_MUN, ID_ESTADO) " +
                                  "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sqlDireccion);
            ps.setString(1, idDireccion);
            ps.setString(2, (String) cliente[3]); // Calle
            ps.setString(3, (String) cliente[4]); // Exterior
            ps.setString(4, (String) cliente[5]); // Interior
            ps.setString(5, (String) cliente[6]); // Colonia
            ps.setString(6, (String) cliente[7]); // CP
            ps.setString(7, (String) cliente[8]); // Alcaldía/Municipio
            ps.setString(8, codigoEstado);

            int filasDireccion = ps.executeUpdate();
            if (filasDireccion > 0) {
                // Insertar cliente
                String sqlCliente = "INSERT INTO CLIENTE (ID_CLIENTE, NOMBRE, AP_PATERNO, AP_MATERNO, FECHA_REG, CORREO, ID_DIRECCION) " +
                                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
                ps = conn.prepareStatement(sqlCliente);
                ps.setString(1, idCliente);
                ps.setString(2, (String) cliente[0]); // Nombre
                ps.setString(3, (String) cliente[1]); // Apellido Paterno
                ps.setString(4, (String) cliente[2]); // Apellido Materno
                ps.setDate(5, (Date) cliente[9]); // Fecha de Registro
                ps.setString(6, (String) cliente[10]); // Correo
                ps.setString(7, idDireccion); // ID Dirección

                int filasCliente = ps.executeUpdate();
                return filasCliente > 0;
            }
        } catch (SQLException ex) {
            System.out.println("Error al agregar cliente: " + ex.getMessage());
        } finally {
            desconectar();
        }
        return false;
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
