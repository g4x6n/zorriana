package database.dao;

import database.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoProveedor extends Conexion {

    // Generar el ID del proveedor
    private String generarIdProveedor() {
        String idProveedor = "";
        try {
            String sql = "SELECT MAX(ID_PROVEEDOR) FROM PROVEEDOR";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                String maxId = rs.getString(1);
                if (maxId != null) {
                    int secuencia = Integer.parseInt(maxId.substring(1)) + 1;
                    idProveedor = "P" + String.format("%03d", secuencia);
                } else {
                    idProveedor = "P001"; // Primer ID
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al generar ID del proveedor: " + ex.getMessage());
        }
        return idProveedor;
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

    // Obtener lista de estados
    public List<String> obtenerEstados() {
        conectar();
        List<String> estados = new ArrayList<>();
        try {
            String sql = "SELECT ESTADO FROM ESTADO";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                estados.add(rs.getString("ESTADO"));
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener estados: " + ex.getMessage());
        } finally {
            desconectar();
        }
        return estados;
    }

    // Listar proveedores
    public List<Object[]> listProveedores() {
        conectar();
        List<Object[]> proveedores = new ArrayList<>();
        try {
            String sql = "SELECT P.ID_PROVEEDOR, P.NOMBRE_EMPRESA, P.NOMBRE_CONTACTO, P.LADA, P.TELEFONO, " +
                         "D.CALLE || ' ' || NVL(D.EXTERIOR, '') || ' ' || NVL(D.INTERIOR, '') || ', ' || D.COLONIA || ', ' || D.ALCAL_MUN || ', ' || E.ESTADO AS DIRECCION " +
                         "FROM PROVEEDOR P " +
                         "JOIN DIRECCION D ON P.ID_DIRECCION = D.ID_DIRECCION " +
                         "JOIN ESTADO E ON D.ID_ESTADO = E.ID_ESTADO";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Object[] proveedor = new Object[6];
                proveedor[0] = rs.getString("ID_PROVEEDOR");
                proveedor[1] = rs.getString("NOMBRE_EMPRESA");
                proveedor[2] = rs.getString("NOMBRE_CONTACTO");
                proveedor[3] = rs.getString("LADA");
                proveedor[4] = rs.getString("TELEFONO");
                proveedor[5] = rs.getString("DIRECCION");
                proveedores.add(proveedor);
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar proveedores: " + ex.getMessage());
        } finally {
            desconectar();
        }
        return proveedores;
    }

    // Agregar proveedor
    public boolean addProveedor(Object[] proveedor) {
        conectar();
        String idProveedor = generarIdProveedor();
        String idDireccion = generarIdDireccion();

        try {
            String codigoEstado = obtenerCodigoEstado((String) proveedor[9]);

            // Insertar dirección
            String sqlDireccion = "INSERT INTO DIRECCION (ID_DIRECCION, CALLE, EXTERIOR, INTERIOR, COLONIA, CP, ALCAL_MUN, ID_ESTADO) " +
                                  "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sqlDireccion);
            ps.setString(1, idDireccion);
            ps.setString(2, (String) proveedor[4]);
            ps.setString(3, (String) proveedor[5]);
            ps.setString(4, (String) proveedor[6]);
            ps.setString(5, (String) proveedor[7]);
            ps.setString(6, (String) proveedor[8]);
            ps.setString(7, (String) proveedor[9]);
            ps.setString(8, codigoEstado);

            int filasDireccion = ps.executeUpdate();

            // Insertar proveedor
            if (filasDireccion > 0) {
                String sqlProveedor = "INSERT INTO PROVEEDOR (ID_PROVEEDOR, NOMBRE_EMPRESA, NOMBRE_CONTACTO, LADA, TELEFONO, EXTENSION, CORREO, ID_DIRECCION) " +
                                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                ps = conn.prepareStatement(sqlProveedor);
                ps.setString(1, idProveedor);
                ps.setString(2, (String) proveedor[0]);
                ps.setString(3, (String) proveedor[1]);
                ps.setString(4, (String) proveedor[2]);
                ps.setString(5, (String) proveedor[3]);
                ps.setString(6, (String) proveedor[4]);
                ps.setString(7, (String) proveedor[5]);
                ps.setString(8, idDireccion);

                int filasProveedor = ps.executeUpdate();
                return filasProveedor > 0;
            }
        } catch (SQLException ex) {
            System.out.println("Error al agregar proveedor: " + ex.getMessage());
        } finally {
            desconectar();
        }
        return false;
    }

    // Eliminar proveedor
    public boolean deleteProveedor(String idProveedor, String idDireccion) {
        conectar();
        try {
            String sqlProveedor = "DELETE FROM PROVEEDOR WHERE ID_PROVEEDOR = ?";
            ps = conn.prepareStatement(sqlProveedor);
            ps.setString(1, idProveedor);
            ps.executeUpdate();

            String sqlDireccion = "DELETE FROM DIRECCION WHERE ID_DIRECCION = ?";
            ps = conn.prepareStatement(sqlDireccion);
            ps.setString(1, idDireccion);
            ps.executeUpdate();

            return true;
        } catch (SQLException ex) {
            System.out.println("Error al eliminar proveedor: " + ex.getMessage());
        } finally {
            desconectar();
        }
        return false;
    }

    // Actualizar proveedor (placeholder)
    public boolean updateProveedor(Object[] proveedor) {
        // Implementación opcional
        return false;
    }

    // Obtener código del estado
    private String obtenerCodigoEstado(String nombreEstado) {
        conectar();
        String codigoEstado = "";
        try {
            String sql = "SELECT ID_ESTADO FROM ESTADO WHERE UPPER(ESTADO) = UPPER(?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, nombreEstado);
            rs = ps.executeQuery();

            if (rs.next()) {
                codigoEstado = rs.getString("ID_ESTADO");
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener código del estado: " + ex.getMessage());
        } finally {
            desconectar();
        }
        return codigoEstado;
    }
}
