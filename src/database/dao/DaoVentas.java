
package database.dao;

import database.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DaoVentas extends Conexion {

    public boolean deleteVenta(String idVenta) {
        conectar();
        boolean exito = false; // Indica si la operación fue exitosa
        try {
            System.out.println("Intentando eliminar venta con ID: '" + idVenta + "'");

            // Primero, eliminar detalles de la venta
            String sqlDetalleVenta = "DELETE FROM DETALLE_VENTA WHERE TRIM(ID_VENTA) = ?";
            ps = conn.prepareStatement(sqlDetalleVenta);
            ps.setString(1, idVenta);
            int detallesEliminados = ps.executeUpdate();

            if (detallesEliminados > 0) {
                System.out.println("Detalles de la venta eliminados exitosamente.");
            }

            // Ahora, eliminar la venta
            String sqlVenta = "DELETE FROM VENTA WHERE TRIM(ID_VENTA) = ?";
            ps = conn.prepareStatement(sqlVenta);
            ps.setString(1, idVenta);
            int filasAfectadas = ps.executeUpdate();

            exito = filasAfectadas > 0;
            if (exito) {
                System.out.println("Venta eliminada exitosamente.");
            } else {
                System.out.println("No se encontró la venta con el ID proporcionado o no se pudo eliminar.");
            }

        } catch (SQLException ex) {
            System.out.println("Error al eliminar la venta y/o detalles de la venta: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            desconectar(); // Cierra la conexión a la base de datos
        }
        return exito;
    }
    public String obtenerNombreEmpleadoPorId(String idEmpleado) {
        conectar();
        String nombreEmpleado = null;
        try {
            String sql = """
                SELECT TRIM(NOMBRE) || ' ' || TRIM(AP_PATERNO) || ' ' || NVL(TRIM(AP_MATERNO, '')) AS NOMBRE_COMPLETO
                FROM EMPLEADO
                WHERE ID_EMPLEADO = ?
            """;
            ps = conn.prepareStatement(sql);
            ps.setString(1, idEmpleado);
            rs = ps.executeQuery();
            if (rs.next()) {
                nombreEmpleado = rs.getString("NOMBRE_COMPLETO");
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener el nombre del empleado: " + ex.getMessage());
        } finally {
            desconectar();
        }
        return nombreEmpleado;
    }
    public boolean actualizarVenta(String idVenta, String idCliente, String idEmpleado, String idEstadoVenta, String idMetodoPago, Date fechaVenta) {
        conectar();
        boolean exito = false;

        try {
            String sql = """
                UPDATE VENTA
                SET ID_CLIENTE = ?, 
                    ID_EMPLEADO = ?, 
                    ID_ESTADO_VENTA = ?, 
                    ID_METODO_PAGO = ?, 
                    FECHA_VENTA = ?
                WHERE ID_VENTA = ?
            """;

            ps = conn.prepareStatement(sql);
            ps.setString(1, idCliente);        // Cliente asociado a la venta
            ps.setString(2, idEmpleado);      // Empleado que realizó la venta
            ps.setString(3, idEstadoVenta);   // Estado de la venta
            ps.setString(4, idMetodoPago);    // Método de pago
            ps.setDate(5, new java.sql.Date(fechaVenta.getTime())); // Fecha de la venta
            ps.setString(6, idVenta);         // ID de la venta a actualizar

            int filasActualizadas = ps.executeUpdate();
            exito = filasActualizadas > 0;

            if (exito) {
                System.out.println("Venta actualizada exitosamente.");
            } else {
                System.out.println("No se encontró la venta con el ID proporcionado.");
            }

        } catch (SQLException ex) {
            System.out.println("Error al actualizar la venta: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return exito;
    }
    public String obtenerIdCliente(String nombreCompleto) {
        conectar();
        String idCliente = null;
        try {
            // Asegúrate de utilizar la función TRIM y COALESCE correctamente
            // y de que todos los nombres están en mayúsculas o en minúsculas según sea necesario
            String sql = "SELECT ID_CLIENTE FROM CLIENTE " +
                         "WHERE UPPER(TRIM(NOMBRE) || ' ' || TRIM(AP_PATERNO) || ' ' || TRIM(COALESCE(AP_MATERNO, ''))) = UPPER(?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, nombreCompleto.trim());
            rs = ps.executeQuery();
            if (rs.next()) {
                idCliente = rs.getString("ID_CLIENTE");
            } else {
                System.out.println("Cliente no encontrado para: " + nombreCompleto);
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener ID del cliente: " + ex.getMessage());
        } finally {
            desconectar();
        }
        return idCliente;
    }
    public String obtenerIdEmpleado(String nombreCompleto) {
        conectar();
        String idEmpleado = null;
        try {
            String sql = "SELECT ID_EMPLEADO FROM EMPLEADO WHERE TRIM(NOMBRE || ' ' || AP_PATERNO || ' ' || NVL(AP_MATERNO, '')) = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, nombreCompleto.trim());
            rs = ps.executeQuery();
            if (rs.next()) {
                idEmpleado = rs.getString("ID_EMPLEADO");
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener ID del empleado: " + ex.getMessage());
        } finally {
            desconectar();
        }
        return idEmpleado;
    }
    public String obtenerIdEstadoVenta(String estadoVentaSeleccionado) {
        conectar(); // Conectar a la base de datos
        String idEstadoVenta = null;
        String query = "SELECT ID_EDO_VENTA FROM ESTADO_VENTA WHERE ESTADO = ?";
        try {
            ps = conn.prepareStatement(query); // Preparar la consulta
            ps.setString(1, estadoVentaSeleccionado); // Asignar el parámetro
            rs = ps.executeQuery(); // Ejecutar la consulta
            if (rs.next()) {
                idEstadoVenta = rs.getString("ID_EDO_VENTA"); // Obtener el resultado
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener ID del estado de venta: " + e.getMessage());
        } finally {
            desconectar(); // Desconectar de la base de datos
        }
        return idEstadoVenta;
    }
    public String obtenerIdMetodoPago(String metodoPago) {
        conectar(); // Conectar a la base de datos
        String idMetodoPago = null;
        String query = "SELECT ID_MET_PAGO FROM METODO_PAGO WHERE TRIM(METODO) = ?";
        try {
            ps = conn.prepareStatement(query); // Preparar la consulta
            ps.setString(1, metodoPago.trim()); // Asignar el parámetro
            rs = ps.executeQuery(); // Ejecutar la consulta
            if (rs.next()) {
                idMetodoPago = rs.getString("ID_MET_PAGO"); // Obtener el resultado
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener ID del método de pago: " + ex.getMessage());
        } finally {
            desconectar(); // Desconectar de la base de datos
        }
        return idMetodoPago;
    }
    public List<Object[]> buscarVentas(String filtro) {
    conectar();
    List<Object[]> ventas = new ArrayList<>();
    try {
        String sql = """
            SELECT V.ID_VENTA, V.FECHA_VENTA, 
                   C.NOMBRE || ' ' || C.AP_PATERNO || ' ' || NVL(C.AP_MATERNO, '') AS CLIENTE, 
                   E.NOMBRE || ' ' || E.AP_PATERNO || ' ' || NVL(E.AP_MATERNO, '') AS EMPLEADO, 
                   EV.ESTADO AS ESTADO_VENTA, MP.METODO AS METODO_PAGO
            FROM VENTA V
            JOIN CLIENTE C ON V.ID_CLIENTE = C.ID_CLIENTE
            JOIN EMPLEADO E ON V.ID_EMPLEADO = E.ID_EMPLEADO
            JOIN ESTADO_VENTA EV ON V.ID_EDO_VENTA = EV.ID_EDO_VENTA
            JOIN METODO_PAGO MP ON V.ID_MET_PAGO = MP.ID_MET_PAGO
            WHERE UPPER(C.NOMBRE || ' ' || C.AP_PATERNO || ' ' || NVL(C.AP_MATERNO, '')) LIKE UPPER(?)
               OR UPPER(E.NOMBRE || ' ' || E.AP_PATERNO || ' ' || NVL(E.AP_MATERNO, '')) LIKE UPPER(?)
               OR TO_CHAR(V.FECHA_VENTA, 'YYYY-MM-DD') LIKE ?
               OR UPPER(EV.ESTADO) LIKE UPPER(?)
               OR UPPER(MP.METODO) LIKE UPPER(?)
        """;

        ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + filtro + "%");
        ps.setString(2, "%" + filtro + "%");
        ps.setString(3, "%" + filtro + "%");
        ps.setString(4, "%" + filtro + "%");
        ps.setString(5, "%" + filtro + "%");

        rs = ps.executeQuery();

        while (rs.next()) {
            Object[] venta = new Object[6];
            venta[0] = rs.getString("ID_VENTA");
            venta[1] = rs.getDate("FECHA_VENTA");
            venta[2] = rs.getString("CLIENTE");
            venta[3] = rs.getString("EMPLEADO");
            venta[4] = rs.getString("ESTADO_VENTA");
            venta[5] = rs.getString("METODO_PAGO");
            ventas.add(venta);
        }
    } catch (SQLException ex) {
        System.out.println("Error al buscar ventas: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return ventas;
}
    public List<Object[]> obtenerDetalleVenta(String idVenta) {
        conectar();
        List<Object[]> detalles = new ArrayList<>();
        try {
            // Consulta SQL corregida
            String sql = 
                "SELECT " +
                "    P.NOMBRE AS PRODUCTO, " +
                "    DV.CANTIDAD, " +
                "    P.PRECIO, " +
                "    (DV.CANTIDAD * P.PRECIO) AS TOTAL " +
                "FROM " +
                "    DETALLE_VENTA DV " +
                "JOIN " +
                "    PRODUCTO P ON DV.ID_PRODUCTO = P.ID_PRODUCTO " +
                "WHERE " +
                "    DV.ID_VENTA = ?";

            ps = conn.prepareStatement(sql); // Preparar la consulta
            ps.setString(1, idVenta); // Asignar el parámetro ID_VENTA
            rs = ps.executeQuery(); // Ejecutar la consulta

            // Iterar sobre los resultados
            while (rs.next()) {
                Object[] detalle = new Object[4];
                detalle[0] = rs.getString("PRODUCTO");
                detalle[1] = rs.getInt("CANTIDAD");
                detalle[2] = rs.getFloat("PRECIO");
                detalle[3] = rs.getFloat("TOTAL");
                detalles.add(detalle);
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener detalles de la venta: " + ex.getMessage());
        } finally {
            desconectar();
        }
        return detalles;
    }
    public List<Object[]> listarVentas() {
        conectar(); // Conecta a la base de datos
        List<Object[]> ventas = new ArrayList<>();
        try {
            String sql = """
                SELECT 
                    V.ID_VENTA,
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
                Object[] venta = new Object[6];
                venta[0] = rs.getString("ID_VENTA");
                venta[1] = rs.getDate("FECHA_VENTA");
                venta[2] = rs.getString("Cliente");
                venta[3] = rs.getString("Empleado");
                venta[4] = rs.getString("EstadoVenta");
                venta[5] = rs.getString("MetodoPago");
                ventas.add(venta);

                // Depuración

            }
        } catch (SQLException ex) {
            System.out.println("Error al listar ventas: " + ex.getMessage());
        } finally {
            desconectar(); // Desconecta de la base de datos
        }
        return ventas;
    }
    public boolean guardarDetalleVenta(String idVenta, String idProducto, int cantidad) {
        conectar();
        boolean exito = false;
        try {
            String sql = "INSERT INTO DETALLE_VENTA (ID_VENTA, ID_PRODUCTO, CANTIDAD) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, idVenta);
            ps.setString(2, idProducto);
            ps.setInt(3, cantidad);
            exito = ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Error al guardar el detalle de la venta: " + ex.getMessage());
        } finally {
            desconectar();
        }
        return exito;
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
    public boolean guardarVenta(String idVenta, String idCliente, String idEmpleado, String idEstadoVenta, String idMetodoPago, Date fechaVenta) {
        conectar(); // Conectar a la base de datos
        boolean exito = false;
        String query = "INSERT INTO VENTA (ID_VENTA, ID_CLIENTE, ID_EMPLEADO, ID_EDO_VENTA, ID_MET_PAGO, FECHA_VENTA) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            ps = conn.prepareStatement(query); // Preparar la consulta
            ps.setString(1, idVenta); // Asignar valores
            ps.setString(2, idCliente);
            ps.setString(3, idEmpleado);
            ps.setString(4, idEstadoVenta);
            ps.setString(5, idMetodoPago);
            ps.setDate(6, new java.sql.Date(fechaVenta.getTime())); // Convertir la fecha
            exito = ps.executeUpdate() > 0; // Ejecutar y verificar si se insertó
        } catch (SQLException ex) {
            System.out.println("Error al guardar la venta: " + ex.getMessage());
        } finally {
            desconectar(); // Desconectar de la base de datos
        }
        return exito;
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





