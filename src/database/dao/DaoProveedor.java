package database.dao;

import database.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoProveedor extends Conexion {
    
     
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

    public String obtenerCodigoEstado(String nombreEstado) {
    conectar(); // Asegurarse de conectar a la base de datos
    String codigoEstado = "";
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
    
    public boolean insertarProveedor(String nombreEmpresa, String nombreContacto, String lada, String telefono, String extension, String correo, String calle, String exterior, String interior, String colonia, String cp, String alcalMun, String estado) {
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

        // Validaciones de entrada
        if (!lada.matches("\\d{1,3}")) {
            System.out.println("Error: LADA debe ser un número de hasta 3 dígitos.");
            return false;
        }

        if (extension != null && !extension.isEmpty() && !extension.matches("\\d{1,3}")) {
            System.out.println("Error: La extensión debe ser un número de hasta 3 dígitos.");
            return false;
        }

        // Insertar el cliente
        String sql = "INSERT INTO PROVEEDOR (NOMBRE_EMPRESA, NOMBRE_CONTACTO, LADA, TELEFONO, EXTENSION, CORREO, ID_DIRECCION) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql);
        ps.setString(1, nombreEmpresa);
        ps.setString(2, nombreContacto);
        ps.setString(3, lada);
        ps.setString(4, telefono);

        // Manejar la extensión (puede ser nula)
        if (extension == null || extension.isEmpty()) {
            ps.setNull(5, java.sql.Types.INTEGER);
        } else {
            ps.setString(5, extension);
        }

        ps.setString(6, correo);
        ps.setString(7, idDireccion);

        exito = ps.executeUpdate() > 0;
    } catch (SQLException ex) {
        System.out.println("Error al insertar proveedor: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        // Cerrar recursos
        try {
            if (ps != null) ps.close();
            desconectar(); // Cerrar conexión
        } catch (SQLException ex) {
            System.out.println("Error al cerrar los recursos: " + ex.getMessage());
        }
    }

    return exito;
}

    public String obtenerIdDireccionPorProveedor(String idProveedor) {
    conectar(); // Asegura la conexión a la base de datos
    String idDireccion = null;
    try {
        String sql = "SELECT ID_DIRECCION FROM PROVEEDOR WHERE ID_PROVEEDOR = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, idProveedor.trim());
        rs = ps.executeQuery();

        if (rs.next()) {
            idDireccion = rs.getString("ID_DIRECCION");
        } 
    } catch (SQLException ex) {
        System.out.println("Error al obtener ID de dirección: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        desconectar(); // Cierra la conexión a la base de datos
    }
    return idDireccion;
}

    
    public Object[] obtenerProveedorPorId(String idProveedor) {
    conectar();
    Object[] proveedor = new Object[15]; // Tamaño ajustado según las columnas seleccionadas en la consulta
    try {
        String sql = "SELECT ID_PROVEEDOR, NOMBRE_EMPRESA, NOMBRE_CONTACTO, LADA, TELEFONO, " +
                     "EXTENSION, CORREO, " +
                     "CALLE, EXTERIOR, INTERIOR, COLONIA, CP, ALCAL_MUN, ESTADO, " +
                     "ID_DIRECCION " +
                     "FROM PROVEEDOR " +
                     "JOIN DIRECCION USING (ID_DIRECCION) " +
                     "JOIN ESTADO USING (ID_ESTADO) " +
                     "WHERE ID_PROVEEDOR = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, idProveedor);
        rs = ps.executeQuery();

        if (rs.next()) {
            proveedor[0] = rs.getString("ID_PROVEEDOR");        // String
            proveedor[1] = rs.getString("NOMBRE_EMPRESA");      // String
            proveedor[2] = rs.getString("NOMBRE_CONTACTO");     // String
            proveedor[3] = rs.getInt("LADA");                  // Número
            proveedor[4] = rs.getInt("TELEFONO");              // Número
            proveedor[5] = rs.getInt("EXTENSION");             // Número
            proveedor[6] = rs.getString("CORREO");             // String
            proveedor[7] = rs.getString("CALLE");              // String
            proveedor[8] = rs.getString("EXTERIOR");           // String
            proveedor[9] = rs.getString("INTERIOR");           // String
            proveedor[10] = rs.getString("COLONIA");           // String
            proveedor[11] = rs.getString("CP");                // String
            proveedor[12] = rs.getString("ALCAL_MUN");         // String
            proveedor[13] = rs.getString("ESTADO");            // String
            proveedor[14] = rs.getString("ID_DIRECCION");      // String
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener detalles del proveedor: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return proveedor;
}

    public List<String> obtenerEstados() {
    conectar();
    List<String> estados = new ArrayList<>();
    try {
        String sql = "SELECT ESTADO FROM ESTADO";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            estados.add(rs.getString("ESTADO")); // Agrega el nombre del estado a la lista
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener estados: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return estados;
}

    public List<Object[]> buscarProveedor(String filtro) {
    conectar();
    List<Object[]> proveedores = new ArrayList<>();
    try {
        // Consulta SQL con alias para la dirección
        String sql = "SELECT ID_PROVEEDOR, NOMBRE_EMPRESA, NOMBRE_CONTACTO, LADA, TELEFONO, EXTENSION, CORREO, " +
                     "CALLE || ' ' || NVL(EXTERIOR, '') || ' ' || NVL(INTERIOR, '') || ', ' || COLONIA || ', ' || ALCAL_MUN || ', ' || ESTADO AS DIR " +
                     "FROM PROVEEDOR " +
                     "JOIN DIRECCION USING (ID_DIRECCION) " +
                     "JOIN ESTADO USING (ID_ESTADO) " +
                     "WHERE UPPER(NOMBRE_EMPRESA) LIKE UPPER(?) OR UPPER(NOMBRE_CONTACTO) LIKE UPPER(?) OR UPPER(CORREO) LIKE UPPER(?)";

        ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + filtro + "%");
        ps.setString(2, "%" + filtro + "%");
        ps.setString(3, "%" + filtro + "%");
        rs = ps.executeQuery();

        while (rs.next()) {
            Object[] proveedor = new Object[8]; // Ajusta según las columnas seleccionadas
            proveedor[0] = rs.getString("ID_PROVEEDOR");       // ID del proveedor
            proveedor[1] = rs.getString("NOMBRE_EMPRESA");     // Nombre de la empresa
            proveedor[2] = rs.getString("NOMBRE_CONTACTO");    // Nombre del contacto
            proveedor[3] = rs.getInt("LADA");                 // LADA (número)
            proveedor[4] = rs.getInt("TELEFONO");             // Teléfono (número)
            proveedor[5] = rs.getInt("EXTENSION");            // Extensión (número)
            proveedor[6] = rs.getString("CORREO");            // Correo electrónico
            proveedor[7] = rs.getString("DIR");               // Dirección completa
            proveedores.add(proveedor);
        }
    } catch (SQLException ex) {
        System.out.println("Error al buscar proveedores: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return proveedores;
}

    public List<Object[]> listProveedores() {
    conectar();
    List<Object[]> proveedores = new ArrayList<>();
    try {
        String sql = "SELECT ID_PROVEEDOR, NOMBRE_EMPRESA, NOMBRE_CONTACTO, LPAD(LADA, 3, '0') AS LADA, TO_CHAR(TELEFONO) AS TELEFONO, NVL(EXTENSION, '') AS EXTENSION, " +
                     "CORREO, " +
                     "CALLE || ' ' || NVL(EXTERIOR, '') || ' ' || NVL(INTERIOR, '') || ', ' || COLONIA || ', ' || ALCAL_MUN || ', ' || ESTADO AS DIRECCION " +
                     "FROM PROVEEDOR " +
                     "JOIN DIRECCION USING (ID_DIRECCION) " +
                     "JOIN ESTADO USING (ID_ESTADO)";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            Object[] proveedor = new Object[8];
            proveedor[0] = rs.getString("ID_PROVEEDOR");  // ID (String)
            proveedor[1] = rs.getString("NOMBRE_EMPRESA").trim();  // Nombre de Empresa (String)
            proveedor[2] = rs.getString("NOMBRE_CONTACTO").trim();  // Nombre de Contacto (String)
            proveedor[3] = rs.getString("LADA").trim();  // LADA (String)
            proveedor[4] = rs.getString("TELEFONO").trim();  // TELEFONO (long para manejar números grandes)
            proveedor[5] = rs.getObject("EXTENSION") == null ? "N/A" : rs.getInt("EXTENSION");  // Extensión (int)
            proveedor[6] = rs.getString("CORREO").trim();  // Correo (String)
            proveedor[7] = rs.getString("DIRECCION").trim();  // Dirección (String)
            proveedores.add(proveedor);
        }
    } catch (SQLException ex) {
        System.out.println("Error al listar proveedores: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        desconectar();
    }
    return proveedores;
}

    public boolean deleteProveedor(String idProveedor, String idDireccion) {
    conectar(); 
    boolean exito = false;// Conectar a la base de datos
    try {
        // Eliminar proveedor
        String sqlProveedor = "DELETE FROM PROVEEDOR WHERE ID_PROVEEDOR = ?";
        ps = conn.prepareStatement(sqlProveedor);
        ps.setString(1, idProveedor);
        int filasAfectadas = ps.executeUpdate();

        if (filasAfectadas > 0) {
          String sqlDireccion = "DELETE FROM DIRECCION WHERE ID_DIRECCION = ?";
            ps = conn.prepareStatement(sqlDireccion);
            ps.setString(1, idDireccion);
            int filasDireccion = ps.executeUpdate();
            exito = filasDireccion > 0;  
        }
    } catch (SQLException ex) {
        System.out.println("Error al eliminar proveedor y direccion: " + ex.getMessage());
        return false;
    } finally {
        desconectar(); // Cierra la conexión
    }
    return exito;
}

    public boolean updateProveedor(Object[] datosProveedor) {
    conectar(); // Establecer conexión a la base de datos
    String sql = "UPDATE PROVEEDOR SET " +
                 "NOMBRE_EMPRESA = ?, " +
                 "NOMBRE_CONTACTO = ?, " +
                 "LADA = ?, " +
                 "TELEFONO = ?, " +
                 "EXTENSION = ?, " +
                 "CORREO = ?, " +
                 "ID_DIRECCION = ? " +
                 "WHERE ID_PROVEEDOR = ?";

    try {
        // Preparar la consulta
        ps = conn.prepareStatement(sql);

        // Asignar parámetros con el orden correcto
        ps.setString(1, (String) datosProveedor[1]); // NOMBRE_EMPRESA
        ps.setString(2, (String) datosProveedor[2]); // NOMBRE_CONTACTO
        ps.setInt(3, Integer.parseInt((String) datosProveedor[3])); // LADA
        ps.setString(4, (String) datosProveedor[4]); // TELEFONO
        ps.setInt(5, datosProveedor[5] != null ? Integer.parseInt((String) datosProveedor[5]) : 0); // EXTENSION
        ps.setString(6, (String) datosProveedor[6]); // CORREO
        ps.setString(7, (String) datosProveedor[7]); // ID_DIRECCION
        ps.setString(8, (String) datosProveedor[0]); // ID_PROVEEDOR

        // Ejecutar la actualización
        int filasActualizadas = ps.executeUpdate();

        return filasActualizadas > 0; // Retornar si se actualizaron filas
    } catch (SQLException e) {
        System.out.println("Error al actualizar proveedor: " + e.getMessage());
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
