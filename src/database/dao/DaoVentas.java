
package database.dao;

import database.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoVentas extends Conexion {

public List<Object[]> listarVentas() {
    conectar(); // Conecta a la base de datos
    List<Object[]> ventas = new ArrayList<>();
    try {
        String sql = """
            SELECT 
                V.FECHA_VENTA,
                C.NOMBRE || ' ' || C.AP_PATERNO || ' ' || NVL(C.AP_MATERNO, '') AS Cliente,
                E.NOMBRE || ' ' || E.AP_PATERNO || ' ' || NVL(E.AP_MATERNO, '') AS Empleado,
                EV.ESTADO AS EstadoVenta,
                MP.METODO AS MetodoPago
            FROM 
                VENTA V
            JOIN 
                CLIENTE C ON V.ID_CLIENTE = C.ID_CLIENTE
            JOIN 
                EMPLEADO E ON V.ID_EMPLEADO = E.ID_EMPLEADO
            JOIN 
                ESTADO_VENTA EV ON V.ID_EDO_VENTA = EV.ID_EDO_VENTA
            JOIN 
                METODO_PAGO MP ON V.ID_MET_PAGO = MP.ID_MET_PAGO
        """;
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            Object[] venta = new Object[5];
            venta[0] = rs.getDate("FECHA_VENTA");
            venta[1] = rs.getString("Cliente");
            venta[2] = rs.getString("Empleado");
            venta[3] = rs.getString("EstadoVenta");
            venta[4] = rs.getString("MetodoPago");
            ventas.add(venta);
        }
    } catch (SQLException ex) {
        System.out.println("Error al listar ventas: " + ex.getMessage());
    } finally {
        desconectar(); // Desconecta de la base de datos
    }
    return ventas;
}
public List<Object[]> obtenerDetalleVenta(String fechaVenta) {
    conectar();
    List<Object[]> detalles = new ArrayList<>();
    try {
        String sql = """
            SELECT 
                P.NOMBRE AS Producto,
                DV.CANTIDAD,
                DV.PRECIO_UNITARIO,
                (DV.CANTIDAD * DV.PRECIO_UNITARIO) AS Subtotal
            FROM 
                DETALLE_VENTA DV
            JOIN 
                PRODUCTO P ON DV.ID_PRODUCTO = P.ID_PRODUCTO
            JOIN 
                VENTA V ON DV.ID_VENTA = V.ID_VENTA
            WHERE 
                V.FECHA_VENTA = ?
        """;
        ps = conn.prepareStatement(sql);
        ps.setString(1, fechaVenta);
        rs = ps.executeQuery();

        while (rs.next()) {
            Object[] detalle = new Object[4];
            detalle[0] = rs.getString("Producto"); // Nombre del producto
            detalle[1] = rs.getInt("CANTIDAD"); // Cantidad
            detalle[2] = rs.getDouble("PRECIO_UNITARIO"); // Precio unitario
            detalle[3] = rs.getDouble("Subtotal"); // Subtotal
            detalles.add(detalle);
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener detalle de la venta: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return detalles;
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

public List<String> obtenerCliente() {
    conectar(); // Método para conectar a la base de datos
    List<String> clientes = new ArrayList<>();
    try {
        // Consulta ajustada para manejar nombres completos correctamente
        String sql = "SELECT TRIM(NOMBRE) || ' ' || TRIM(AP_PATERNO) || ' ' || NVL(TRIM(AP_MATERNO), '') AS NOMBRE_COMPLETO FROM CLIENTE";
        ps = conn.prepareStatement(sql); // Preparar la consulta
        rs = ps.executeQuery(); // Ejecutar la consulta
        while (rs.next()) {
            // Asegurarnos de que no haya espacios adicionales al agregar a la lista
            clientes.add(rs.getString("NOMBRE_COMPLETO").replaceAll("\\s+", " ").trim());
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener clientes: " + ex.getMessage());
    } finally {
        desconectar(); // Método para desconectar de la base de datos
    }
    return clientes; // Retornar la lista de empleados
    }
public List<String> obtenerEstadodeVenta() {
    conectar(); // Método para conectar a la base de datos
    List<String> edo_venta = new ArrayList<>();
    try {
        // Consulta ajustada para manejar nombres completos correctamente
        String sql = "SELECT ESTADO FROM ESTADO_VENTA";
        ps = conn.prepareStatement(sql); // Preparar la consulta
        rs = ps.executeQuery(); // Ejecutar la consulta
        while (rs.next()) {
            // Asegurarnos de que no haya espacios adicionales al agregar a la lista
            edo_venta.add(rs.getString("ESTADO").replaceAll("\\s+", " ").trim());
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener estados de venta: " + ex.getMessage());
    } finally {
        desconectar(); // Método para desconectar de la base de datos
    }
    return edo_venta; // Retornar la lista de empleados
    }
public List<String> obtenerMetodoPago() {
    conectar(); // Método para conectar a la base de datos
    List<String> metodopago = new ArrayList<>();
    try {
        // Consulta ajustada para manejar nombres completos correctamente
        String sql = "SELECT METODO FROM METODO_PAGO";
        ps = conn.prepareStatement(sql); // Preparar la consulta
        rs = ps.executeQuery(); // Ejecutar la consulta
        while (rs.next()) {
            // Asegurarnos de que no haya espacios adicionales al agregar a la lista
            metodopago.add(rs.getString("METODO").replaceAll("\\s+", " ").trim());
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener metodos de pago: " + ex.getMessage());
    } finally {
        desconectar(); // Método para desconectar de la base de datos
    }
    return metodopago; // Retornar la lista de empleados
    }
}


