package database.dao;

import database.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoClientes extends Conexion {



    public String insertarDireccion(String calle, String exterior, String interior, String colonia, String cp, String alcalMun, String idEstado) {
    conectar(); // Conectar a la base de datos
    String idDireccion = null;

    try {
        // Obtener el próximo ID de la secuencia
        String sql = "SELECT SEQ_DIRECCION_ID.NEXTVAL FROM DUAL";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        if (rs.next()) {
            idDireccion = rs.getString(1);
        } else {
            System.out.println("No se pudo generar un ID para la dirección.");
            return null;
        }

        // Inserción de la nueva dirección
        sql = "INSERT INTO DIRECCION (ID_DIRECCION, CALLE, EXTERIOR, INTERIOR, COLONIA, CP, ALCAL_MUN, ID_ESTADO) " +
              "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql);
        ps.setString(1, idDireccion);
        ps.setString(2, calle);
        ps.setString(3, exterior);
        ps.setString(4, interior);
        ps.setString(5, colonia);
        ps.setString(6, cp);
        ps.setString(7, alcalMun);
        ps.setString(8, idEstado);

        int rowsAffected = ps.executeUpdate();
        if (rowsAffected == 0) {
            System.out.println("Error: No se pudo insertar la dirección.");
            idDireccion = null;
        } else {
            System.out.println("Dirección insertada con ID: " + idDireccion);
        }
    } catch (SQLException ex) {
        System.out.println("Error al insertar dirección: " + ex.getMessage());
        ex.printStackTrace();
    }

    return idDireccion;
}

    public String obtenerIdDireccion(String calle, String exterior, String interior, String colonia, String cp, String alcalMun, String idEstado) {
    conectar(); // Conectar a la base de datos
    String idDireccion = null;
    try {
        // Consulta SQL para buscar la dirección
        String sql = "SELECT ID_DIRECCION FROM DIRECCION WHERE CALLE = ? AND EXTERIOR = ? AND INTERIOR = ? " +
                     "AND COLONIA = ? AND CP = ? AND ALCAL_MUN = ? AND ID_ESTADO = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, calle.trim());
        ps.setString(2, exterior.trim());
        ps.setString(3, interior.trim());
        ps.setString(4, colonia.trim());
        ps.setString(5, cp.trim());
        ps.setString(6, alcalMun.trim());
        ps.setString(7, idEstado.trim());
        rs = ps.executeQuery();

        if (rs.next()) {
            idDireccion = rs.getString("ID_DIRECCION"); // Obtener el ID de la dirección
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener ID de la dirección: " + ex.getMessage());
        ex.printStackTrace();
    }
    return idDireccion; // Retornar el ID de la dirección o null si no se encontró
}

    public boolean insertarCliente(String nombre, String apPaterno, String apMaterno, String fechaReg, String correo, String calle, String exterior, String interior, String colonia, String cp, String alcalMun, String estado) {
    conectar(); // Conectar a la base de datos
    boolean exito = false;

    try {
        // Obtener el ID del estado
        String idEstado = obtenerCodigoEstado(estado);
        if (idEstado == null) {
            System.out.println("Error: El estado especificado no existe.");
            return false;
        }

        // Insertar la dirección o verificar si ya existe
        String idDireccion = obtenerIdDireccion(calle, exterior, interior, colonia, cp, alcalMun, idEstado);
        if (idDireccion == null) {
            idDireccion = insertarDireccion(calle, exterior, interior, colonia, cp, alcalMun, idEstado);
        }

        if (idDireccion == null) {
            System.out.println("Error: No se pudo insertar o encontrar la dirección.");
            return false;
        }

        // Insertar el cliente
        String sql = "INSERT INTO CLIENTE (NOMBRE, AP_PATERNO, AP_MATERNO, FECHA_REG, CORREO, ID_DIRECCION) " +
                     "VALUES (?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?)";
        ps = conn.prepareStatement(sql);
        ps.setString(1, nombre);
        ps.setString(2, apPaterno);
        ps.setString(3, apMaterno);
        ps.setString(4, fechaReg);
        ps.setString(5, correo);
        ps.setString(6, idDireccion);

        exito = ps.executeUpdate() > 0;
    } catch (SQLException ex) {
        System.out.println("Error al insertar cliente: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        desconectar(); // Cerrar conexión
    }

    return exito;
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

    public boolean deleteClient(String idCliente, String idDireccion) {
    conectar(); 
    boolean exito = false;// Conectar a la base de datos
    try {
        // Eliminar cliente
        String sqlCliente = "DELETE FROM CLIENTE WHERE ID_CLIENTE = ?";
        ps = conn.prepareStatement(sqlCliente);
        ps.setString(1, idCliente);
        int filasAfectadas = ps.executeUpdate();

        if (filasAfectadas > 0) {
          String sqlDireccion = "DELETE FROM DIRECCION WHERE ID_DIRECCION = ?";
            ps = conn.prepareStatement(sqlDireccion);
            ps.setString(1, idDireccion);
            int filasDireccion = ps.executeUpdate();
            exito = filasDireccion > 0;  
        }
    } catch (SQLException ex) {
        System.out.println("Error al eliminar cliente y direccion: " + ex.getMessage());
        return false;
    } finally {
        desconectar(); // Cierra la conexión
    }
    return exito;
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


    public boolean updateClient(Object[] datosCliente) {
    conectar(); // Establecer conexión a la base de datos
    String sql = "UPDATE CLIENTE SET " +
                 "NOMBRE = ?, " +
                 "AP_PATERNO = ?, " +
                 "AP_MATERNO = ?, " +
                 "FECHA_REG = TO_DATE(?, 'YYYY-MM-DD'), " +
                 "CORREO = ?, " +
                 "ID_DIRECCION = ? " +
                 "WHERE ID_CLIENTE = ?";

    try {
        // Truncar el ID de dirección si excede los 3 caracteres
        String idDireccion = (String) datosCliente[6];
        if (idDireccion.length() > 3) {
            idDireccion = idDireccion.substring(0, 3);
        }

        // Preparar la consulta
        ps = conn.prepareStatement(sql);

        // Asignar parámetros con el orden correcto
        ps.setString(1, (String) datosCliente[1]); // NOMBRE
        ps.setString(2, (String) datosCliente[2]); // AP_PATERNO
        ps.setString(3, (String) datosCliente[3]); // AP_MATERNO
        ps.setString(4, (String) datosCliente[4]); // FECHA_REG
        ps.setString(5, (String) datosCliente[5]); // CORREO
        ps.setString(6, idDireccion);              // ID_DIRECCION (truncado si es necesario)
        ps.setString(7, (String) datosCliente[0]); // ID_CLIENTE

        // Ejecutar la actualización
        int filasActualizadas = ps.executeUpdate();

        return filasActualizadas > 0; // Retornar si se actualizaron filas

    } catch (SQLException e) {
        System.out.println("Error al actualizar cliente: " + e.getMessage());
        return false;
    } finally {
        desconectar(); // Cerrar la conexión en el bloque finally
    }
}


    public boolean updateDireccion(Object[] datoDireccion) {
    boolean isUpdated = false;
    conectar(); // Conectar a la base de datos
    try {
        String sql = "UPDATE DIRECCION SET " +
                     "ID_ESTADO = ?, " +
                     "ALCAL_MUN = ?, " +
                     "COLONIA = ?, " +
                     "CP = ?, " +
                     "CALLE = ?, " +
                     "EXTERIOR = ?, " +
                     "INTERIOR = ? " +
                     "WHERE ID_DIRECCION = ?";
        ps = conn.prepareStatement(sql);

        // Asignar valores a los parámetros
        ps.setString(1, (String) datoDireccion[0]); // ID_ESTADO
        ps.setString(2, (String) datoDireccion[1]); // ALCAL_MUN
        ps.setString(3, (String) datoDireccion[2]); // COLONIA
        ps.setString(4, (String) datoDireccion[3]); // CP
        ps.setString(5, (String) datoDireccion[4]); // CALLE
        ps.setString(6, (String) datoDireccion[5]); // EXTERIOR
        ps.setString(7, (String) datoDireccion[6]); // INTERIOR
        ps.setString(8, (String) datoDireccion[7]); // ID_DIRECCION

        // Ejecutar la actualización
        int rowsAffected = ps.executeUpdate();
        isUpdated = rowsAffected > 0; // Si se actualizó al menos una fila, es exitoso
    } catch (SQLException ex) {
        System.out.println("Error al actualizar la dirección: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        desconectar(); // Cerrar conexión
    }
    return isUpdated;  
}


}
