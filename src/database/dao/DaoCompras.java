package database.dao;

import database.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * @author Alex
 */
public class DaoCompras extends Conexion {

    public List<String> obtenerEstadosDeCompra() {
    conectar(); // Método para conectar a la base de datos
    List<String> estados = new ArrayList<>();
    try {
        String sql = "SELECT ESTADO_COMPRA FROM EDO_COMPRA"; // SELECT ESTADO_COMPRA FROM EDO_COMPRA
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        
        while (rs.next()) {
            estados.add(rs.getString("ESTADO_COMPRA"));
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener estados de compra: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        desconectar(); // Método para cerrar la conexión
    }
    return estados;
}
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
    public String obtenerIdProductoPorNombre(String nombreProducto) {
    conectar();
    String idProducto = null;
    try {
        String sql = "SELECT id_producto FROM productos WHERE nombre = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nombreProducto);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            idProducto = rs.getString("id_producto");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        desconectar();
    }
    return idProducto;
}
    public List<Object[]> buscarCompras(String filtro) {
    conectar();
    List<Object[]> compras = new ArrayList<>();
    try {
        String sql = """
            SELECT ID_COMPRA, FECHA_COMPRA, ESTADO_COMPRA, NOMBRE_EMPRESA, 
                   NOMBRE || ' ' || AP_PATERNO || ' ' || NVL(AP_MATERNO, '') AS EMPLEADO
            FROM COMPRA
            JOIN EDO_COMPRA USING (ID_EDO_COMPRA)
            JOIN PROVEEDOR USING (ID_PROVEEDOR)
            JOIN EMPLEADO USING (ID_EMPLEADO)
            WHERE UPPER(NOMBRE_EMPRESA) LIKE UPPER(?) OR
                  UPPER(ESTADO_COMPRA) LIKE UPPER(?) OR
                  UPPER(NOMBRE || ' ' || AP_PATERNO || ' ' || NVL(AP_MATERNO, '')) LIKE UPPER(?) OR
                  TO_CHAR(FECHA_COMPRA, 'YYYY-MM-DD') LIKE ?
            ORDER BY FECHA_COMPRA DESC
        """;

        ps = conn.prepareStatement(sql);
        String likeFilter = "%" + filtro.trim() + "%";
        ps.setString(1, likeFilter);
        ps.setString(2, likeFilter);
        ps.setString(3, likeFilter);
        ps.setString(4, likeFilter);

        rs = ps.executeQuery();

        while (rs.next()) {
            Object[] compra = new Object[5];
            compra[0] = rs.getString("ID_COMPRA").trim();
            compra[1] = rs.getDate("FECHA_COMPRA");
            compra[2] = rs.getString("ESTADO_COMPRA").trim();
            compra[3] = rs.getString("NOMBRE_EMPRESA").trim();
            compra[4] = rs.getString("EMPLEADO").trim();
            compras.add(compra);
        }
    } catch (SQLException ex) {
        System.out.println("Error al buscar compras: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return compras;
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
    public List<Object[]> obtenerProductosPorProveedor(String idProveedor) {
    conectar(); // Conexión a la base de datos
    List<Object[]> productos = new ArrayList<>();
    try {
        // Consulta SQL funcional
        String sql = """
                     SELECT ID_PRODUCTO, NOMBRE 
                     FROM PRODUCTO 
                     JOIN PROVEEDOR USING (ID_PROVEEDOR) 
                     WHERE ID_PROVEEDOR = ?
                     """;
        ps = conn.prepareStatement(sql);
        ps.setString(1, idProveedor); // Filtrar por ID del proveedor
        rs = ps.executeQuery();

        // Procesar resultados
        while (rs.next()) {
            Object[] producto = new Object[2];
            producto[0] = rs.getString("ID_PRODUCTO").trim(); // ID del producto
            producto[1] = rs.getString("NOMBRE").trim();      // Nombre del producto
            productos.add(producto);
        }

        // Mensaje de depuración
        System.out.println("Productos encontrados: " + productos.size());

    } catch (SQLException ex) {
        System.out.println("Error al obtener productos por proveedor: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        desconectar(); // Cierra la conexión
    }
    return productos; // Retorna la lista de productos
}
    public List<Object[]> obtenerProveedores() {
    conectar(); // Conexión a la base de datos
    List<Object[]> proveedores = new ArrayList<>();
    try {
        // Consulta SQL para obtener ID_PROVEEDOR y NOMBRE_EMPRESA
        String sql = "SELECT ID_PROVEEDOR, NOMBRE_EMPRESA FROM PROVEEDOR";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();

        // Procesar resultados
        while (rs.next()) {
            Object[] proveedor = new Object[2];
            proveedor[0] = rs.getString("ID_PROVEEDOR").trim(); // ID del proveedor
            proveedor[1] = rs.getString("NOMBRE_EMPRESA").trim(); // Nombre del proveedor
            proveedores.add(proveedor);
        }

    } catch (SQLException ex) {
        System.out.println("Error al obtener proveedores: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        desconectar(); // Cierra la conexión
    }
    return proveedores; // Retorna la lista de proveedores
}
    public List<Object[]> obtenerProductos() {
    conectar(); // Método para conectar a la base de datos
    List<Object[]> productos = new ArrayList<>();
    try {
        // Consulta para obtener el ID y el NOMBRE de los productos
        String sql = "SELECT ID_PRODUCTO, NOMBRE FROM PRODUCTO";
        ps = conn.prepareStatement(sql); // Preparar la consulta
        rs = ps.executeQuery(); // Ejecutar la consulta

        while (rs.next()) {
            // Crear un array para almacenar los datos del producto
            Object[] producto = new Object[2];
            producto[0] = rs.getString("ID_PRODUCTO").trim(); // ID del producto
            producto[1] = rs.getString("NOMBRE").trim(); // Nombre del producto
            productos.add(producto); // Agregar el producto a la lista
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener productos: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        desconectar(); // Método para desconectar de la base de datos
    }
    return productos; // Retornar la lista de productos
}
    public String obtenerIdEmpleadoPorNombre(String nombreEmpleado) {
        conectar();
        try {
            String sql = "SELECT id_empleado FROM empleados WHERE nombre = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombreEmpleado);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("id_empleado");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return null;
    }
    public boolean crearCompra(String idEmpleado, String fechaCompra, String idEstadoCompra) {
        conectar();
        try {
            String sql = "INSERT INTO compras (id_empleado, fecha_compra, id_estado_compra) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idEmpleado);
            ps.setString(2, fechaCompra);
            ps.setString(3, idEstadoCompra);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
    }
    public String obtenerUltimaCompraId() {
        conectar();
        try {
            String sql = "SELECT MAX(id_compra) AS last_id FROM compras";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("last_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            desconectar();
        }
        return null;
    }
    public String obtenerIdEstadoCompraPorNombre(String nombreEstado) {
    conectar();
    String idEstado = null;
    try {
        String sql = "SELECT ID_EDO_COMPRA FROM EDO_COMPRA WHERE ESTADO_COMPRA = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, nombreEstado);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            idEstado = rs.getString("ID_EDO_COMPRA");
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener el ID del estado de compra: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return idEstado;
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
    public boolean eliminarCompra(String idCompra) {
    conectar();
    boolean exito = false;

    try {
        // Eliminar los detalles de la compra primero (clave foránea)
        String sqlDetalles = "DELETE FROM DETALLE_COMPRA WHERE ID_COMPRA = ?";
        ps = conn.prepareStatement(sqlDetalles);
        ps.setString(1, idCompra);
        ps.executeUpdate();

        // Eliminar la compra
        String sqlCompra = "DELETE FROM COMPRA WHERE ID_COMPRA = ?";
        ps = conn.prepareStatement(sqlCompra);
        ps.setString(1, idCompra);
        int filasAfectadas = ps.executeUpdate();

        if (filasAfectadas > 0) {
            exito = true;
        }
    } catch (SQLException ex) {
        System.out.println("Error al eliminar la compra: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        desconectar();
    }

    return exito;
}
    public boolean crearDetalleCompra(String idCompra, String idProducto, int cantidad) {
    conectar();
    try {
        String sql = "INSERT INTO detalle_compra (id_compra, id_producto, cantidad) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, idCompra);
        ps.setString(2, idProducto);
        ps.setInt(3, cantidad);
        int result = ps.executeUpdate();
        return result > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    } finally {
        desconectar();
    }
}

     
     
     
     
}

   