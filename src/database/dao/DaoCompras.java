package database.dao;

import database.Conexion;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex
 */
public class DaoCompras extends Conexion {

    public List<String> obtenerProveedor() {
    conectar();
    List<String> proveedores = new ArrayList<>();
    try {
        String sql = "SELECT NOMBRE_EMPRESA FROM PROVEEDOR";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            proveedores.add(rs.getString("NOMBRE_EMPRESA").trim()); // Agrega el nombre del proveedor a la lista
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener proveedores: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return proveedores;
}
 
    public List<String> obtenerEmpleado() {
    conectar(); // Método para conectar a la base de datos
    List<String> empleados = new ArrayList<>();
    try {
        // Consulta ajustada para manejar nombres completos correctamente
        String sql = "SELECT TRIM(NOMBRE) || ' ' || TRIM(AP_PATERNO) || ' ' || NVL(TRIM(AP_MATERNO), '') AS NOMBRE_COMPLETO FROM EMPLEADO";
        ps = conn.prepareStatement(sql); // Preparar la consulta
        rs = ps.executeQuery(); // Ejecutar la consulta
        while (rs.next()) {
            // Asegurarnos de que no haya espacios adicionales al agregar a la lista
            empleados.add(rs.getString("NOMBRE_COMPLETO").replaceAll("\\s+", " ").trim());
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener empleados: " + ex.getMessage());
    } finally {
        desconectar(); // Método para desconectar de la base de datos
    }
    return empleados; // Retornar la lista de empleados
}
    
    public List<String> obtenerEdoCompra() {
    conectar();
    List<String> edoscompras = new ArrayList<>();
    try {
        String sql = "SELECT ESTADO_COMPRA FROM EDO_COMPRA";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            edoscompras.add(rs.getString("ESTADO_COMPRA").trim()); // Agrega el nombre del proveedor a la lista
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener estados de compra: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return edoscompras;
}

    public List<Object[]> listCompras() {
    conectar();
    List<Object[]> compras = new ArrayList<>();
    try {
        String sql = "SELECT ID_COMPRA, FECHA_COMPRA, ESTADO_COMPRA, NOMBRE_EMPRESA, " +
                     "NOMBRE || ' ' || AP_PATERNO || ' ' || NVL(AP_MATERNO, '') AS EMPLEADO " +
                     "FROM COMPRA " +
                     "JOIN EDO_COMPRA USING (ID_EDO_COMPRA) " +
                     "JOIN PROVEEDOR USING (ID_PROVEEDOR) " +
                     "JOIN EMPLEADO USING (ID_EMPLEADO)" +
                     "ORDER BY FECHA_COMPRA DESC";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            Object[] compra = new Object[5];
            compra[0] = rs.getString("ID_COMPRA").trim();             // ID_COMPRA (NVARCHAR2(8))
            compra[1] = rs.getDate("FECHA_COMPRA");                  // FECHA_COMPRA (DATE)
            compra[2] = rs.getString("ESTADO_COMPRA").trim();        // ESTADO_COMPRA (CHAR(15))
            compra[3] = rs.getString("NOMBRE_EMPRESA").trim();       // NOMBRE_EMPRESA (CHAR(50))
            compra[4] = rs.getString("EMPLEADO").trim();             // EMPLEADO (Nombre completo)
            compras.add(compra);
        }
    } catch (SQLException ex) {
        System.out.println("Error al listar compras: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        desconectar();
    }
    return compras;
}

    public List<Object[]> obtenerDetalleCompra(String idCompra) {
    conectar();
    List<Object[]> detalleCompra = new ArrayList<>();
    try {
        String sql = "SELECT NOMBRE AS PRODUCTO, CANTIDAD " +
                     "FROM DETALLE_COMPRA " +
                     "JOIN PRODUCTO USING (ID_PRODUCTO) " +
                     "WHERE ID_COMPRA = ?";

        ps = conn.prepareStatement(sql);
        ps.setString(1, idCompra);
        rs = ps.executeQuery();

        while (rs.next()) {
            Object[] detalle = new Object[2];
            detalle[0] = rs.getString("PRODUCTO").trim(); // Nombre del producto
            detalle[1] = rs.getInt("CANTIDAD");          // Cantidad
            detalleCompra.add(detalle);
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener detalles de la compra: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return detalleCompra;
}

     
     
     
     
}

   