package database.dao;

import database.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;


public class DaoClientes extends Conexion {

public String formatearFechaParaBD(String fecha) throws Exception {
    try {
        // Convertir la fecha del formato "DD/MM/YYYY" al formato "DD-MON-YYYY HH24:MI:SS"
        DateTimeFormatter formatterEntrada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterSalida = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);

        LocalDate fechaLocal = LocalDate.parse(fecha, formatterEntrada);
        return fechaLocal.atTime(LocalTime.now()).format(formatterSalida).toUpperCase();
    } catch (Exception e) {
        throw new Exception("Formato de fecha inválido. Asegúrate de usar el formato DD/MM/YYYY.", e);
    }
}
  public String obtenerIdDireccion(String calle, String exterior, String interior, String colonia, String cp, String alcalMun, String idEstado) {
    conectar(); // Conecta a la base de datos
    String idDireccion = null;
    try {
        // Consulta SQL para buscar la dirección
        String sql = "SELECT ID_DIRECCION FROM DIRECCION " +
                     "WHERE CALLE = ? AND EXTERIOR = ? AND INTERIOR = ? AND COLONIA = ? " +
                     "AND CP = ? AND ALCAL_MUN = ? AND ID_ESTADO = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, calle);       // Parámetro: Calle
        ps.setString(2, exterior);   // Parámetro: Número Exterior
        ps.setString(3, interior);   // Parámetro: Número Interior
        ps.setString(4, colonia);    // Parámetro: Colonia
        ps.setString(5, cp);         // Parámetro: Código Postal
        ps.setString(6, alcalMun);   // Parámetro: Alcaldía/Municipio
        ps.setString(7, idEstado);   // Parámetro: ID del Estado

        // Ejecuta la consulta
        rs = ps.executeQuery();
        if (rs.next()) {
            // Si encuentra una coincidencia, asigna el ID de la dirección
            idDireccion = rs.getString("ID_DIRECCION");
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener ID de dirección: " + ex.getMessage());
        ex.printStackTrace(); // Imprime el error en la consola
    } finally {
        desconectar(); // Desconecta de la base de datos
    }
    return idDireccion; // Devuelve el ID de la dirección o null si no existe
}

public String insertarDireccion(String calle, String exterior, String interior, String colonia, String cp, String alcalMun, String idEstado) {
    conectar(); // Conectar a la base de datos
    String idDireccion = null;
    try {
        String sql = "INSERT INTO DIRECCION (ID_DIRECCION, CALLE, EXTERIOR, INTERIOR, COLONIA, CP, ALCAL_MUN, ID_ESTADO) " +
                     "VALUES (SEQ_DIRECCION_ID.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, calle);
        ps.setString(2, exterior);
        ps.setString(3, interior);
        ps.setString(4, colonia);
        ps.setString(5, cp);
        ps.setString(6, alcalMun);
        ps.setString(7, idEstado);

        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            // Obtener el ID generado
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idDireccion = rs.getString(1); // ID generado
            }
        }
        System.out.println("Dirección insertada con ID: " + idDireccion);
    } catch (SQLException ex) {
        System.out.println("Error al insertar dirección: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        desconectar();
    }
    return idDireccion;
}

public boolean insertarCliente(String nombre, String apPaterno, String apMaterno, String fechaReg, String correo, String idDireccion) {
    conectar(); // Conectar a la base de datos
    try {
        // Formatear la fecha de registro al formato esperado por Oracle
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);

        LocalDateTime fechaRegistro = LocalDate.parse(fechaReg, inputFormatter).atStartOfDay();
        String fechaFormateada = fechaRegistro.format(outputFormatter).toUpperCase();

        // Consulta SQL para insertar un cliente
        String sql = "INSERT INTO CLIENTE (ID_CLIENTE, NOMBRE, AP_PATERNO, AP_MATERNO, FECHA_REG, CORREO, ID_DIRECCION) "
                   + "VALUES (SEQ_CLIENTE_ID.NEXTVAL, ?, ?, ?, TO_DATE(?, 'DD-MON-YYYY HH24:MI:SS'), ?, ?)";

        ps = conn.prepareStatement(sql);
        ps.setString(1, nombre); // Nombre
        ps.setString(2, apPaterno); // Apellido Paterno
        ps.setString(3, apMaterno); // Apellido Materno
        ps.setString(4, fechaFormateada); // Fecha de Registro (formateada)
        ps.setString(5, correo); // Correo
        ps.setString(6, idDireccion); // ID de Dirección

        // Ejecutar la consulta
        return ps.executeUpdate() > 0; // Devuelve true si la inserción fue exitosa
    } catch (SQLException ex) {
        System.out.println("Error al insertar cliente: " + ex.getMessage());
        ex.printStackTrace();
        return false;
    } catch (DateTimeParseException ex) {
        System.out.println("Formato de fecha inválido: " + ex.getMessage());
        ex.printStackTrace();
        return false;
    } finally {
        desconectar(); // Cerrar la conexión
    }
}


// Método para obtener el código del estado
public String obtenerCodigoEstado(String nombreEstado) {
    conectar(); // Asegurarse de conectar a la base de datos
    String codigoEstado = "";
    System.out.println("Valor recibido para buscar estado: " + nombreEstado);
    try {
        String sql = "SELECT ID_ESTADO FROM ESTADO WHERE UPPER(ESTADO) = UPPER(?)";
        ps = conn.prepareStatement(sql);
        ps.setString(1, nombreEstado.trim()); // Elimina espacios adicionales
        rs = ps.executeQuery();

        if (rs.next()) {
            codigoEstado = rs.getString(1);
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener código del estado: " + ex.getMessage());
    } finally {
        desconectar(); // Cierra la conexión después de ejecutar
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
