package database.dao;

import database.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoProveedor extends Conexion {

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

    public boolean addProveedor(Object[] proveedor) {
    conectar(); // Conectar a la base de datos
    String idDireccion = ""; 
    String idProveedor = "";

    try {
        // Generar ID para la dirección
        String sqlGenerarIdDireccion = "SELECT 'D'||LPAD(NVL(MAX(TO_NUMBER(SUBSTR(ID_DIRECCION,2,3)))+1,1),3,'0') FROM DIRECCION";
        ps = conn.prepareStatement(sqlGenerarIdDireccion);
        rs = ps.executeQuery();
        if (rs.next()) {
            idDireccion = rs.getString(1);
        }

        // Generar ID para el proveedor
        String sqlGenerarIdProveedor = "SELECT 'P'||LPAD(NVL(MAX(TO_NUMBER(SUBSTR(ID_PROVEEDOR,2,3)))+1,1),3,'0') FROM PROVEEDOR";
        ps = conn.prepareStatement(sqlGenerarIdProveedor);
        rs = ps.executeQuery();
        if (rs.next()) {
            idProveedor = rs.getString(1);
        }

        // Obtener el código de estado
        String codigoEstado = obtenerCodigoEstado((String) proveedor[12]); // Estado

        // Validar la longitud de EXTERIOR y otros campos
        String exterior = proveedor[6] != null ? proveedor[6].toString().substring(0, Math.min(proveedor[6].toString().length(), 5)) : null;

        // Consulta SQL para insertar dirección
        String sqlDireccion = "INSERT INTO DIRECCION (ID_DIRECCION, ID_ESTADO, ALCAL_MUN, COLONIA, CP, CALLE, EXTERIOR, INTERIOR) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sqlDireccion);
        ps.setString(1, idDireccion);
        ps.setString(2, codigoEstado); // Código de estado
        ps.setString(3, (String) proveedor[11]); // Alcaldía/Municipio
        ps.setString(4, (String) proveedor[10]); // Colonia
        ps.setString(5, (String) proveedor[9]); // CP
        ps.setString(6, (String) proveedor[8]); // Calle
        ps.setString(7, exterior); // Exterior truncado
        ps.setString(8, (String) proveedor[7]); // Interior (puede ser null)

        int filasDireccion = ps.executeUpdate();

        // Si la dirección se inserta correctamente, proceder con el proveedor
        if (filasDireccion > 0) {
            // Consulta SQL para insertar proveedor
            String sqlProveedor = "INSERT INTO PROVEEDOR (ID_PROVEEDOR, NOMBRE_EMPRESA, NOMBRE_CONTACTO, LADA, TELEFONO, CORREO, ID_DIRECCION) " +
                                  "VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sqlProveedor);
            ps.setString(1, idProveedor);
            ps.setString(2, (String) proveedor[0]); // Nombre empresa
            ps.setString(3, (String) proveedor[1]); // Nombre contacto
            ps.setInt(4, Integer.parseInt(proveedor[2].toString())); // LADA
            ps.setInt(5, Integer.parseInt(proveedor[3].toString())); // Teléfono
            ps.setString(6, (String) proveedor[4]); // Correo
            ps.setString(7, idDireccion); // ID Dirección

            int filasProveedor = ps.executeUpdate();
            return filasProveedor > 0;
        }
    } catch (SQLException ex) {
        System.out.println("Error al agregar proveedor: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        desconectar(); // Cerrar la conexión
    }
    return false;
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

    // Editar un empleado existente
    public boolean updateEmployee(Object[] empleado) {
    conectar();
    try {
        if (empleado.length < 18) {
            throw new IllegalArgumentException("El array empleado no contiene los índices esperados.");
        }

        String idDireccion = (String) empleado[16];
        String idEmpleado = (String) empleado[17];

        // Actualizar dirección
        String sqlDireccion = "UPDATE DIRECCION SET CALLE = ?, EXTERIOR = ?, INTERIOR = ?, COLONIA = ?, CP = ?, ALCAL_MUN = ?, ID_ESTADO = ? " +
                              "WHERE ID_DIRECCION = ?";
        ps = conn.prepareStatement(sqlDireccion);
        ps.setString(1, (String) empleado[8]);  // Calle
        ps.setString(2, (String) empleado[9]);  // Exterior
        ps.setString(3, (String) empleado[10]); // Interior
        ps.setString(4, (String) empleado[11]); // Colonia
        ps.setString(5, (String) empleado[12]); // CP
        ps.setString(6, (String) empleado[13]); // Alcaldía/Municipio
        ps.setString(7, obtenerCodigoEstado((String) empleado[14])); // Estado
        ps.setString(8, idDireccion);          // ID Dirección

        ps.executeUpdate();

        // Actualizar empleado
        String sqlEmpleado = "UPDATE EMPLEADO SET NOMBRE = ?, AP_PATERNO = ?, AP_MATERNO = ?, FECHA_REG = ?, USUARIO_EMPLEADO = ?, " +
                             "CONTRASENIA_EMPLEADO = ?, CORREO = ?, ID_PUESTO = ?, SUELDO = ? " +
                             "WHERE ID_EMPLEADO = ?";
        ps = conn.prepareStatement(sqlEmpleado);
        ps.setString(1, (String) empleado[0]); // Nombre
        ps.setString(2, (String) empleado[1]); // Apellido Paterno
        ps.setString(3, (String) empleado[2]); // Apellido Materno
        ps.setDate(4, Date.valueOf(empleado[3].toString())); // Fecha de Registro
        ps.setString(5, (String) empleado[4]); // Usuario
        ps.setString(6, (String) empleado[5]); // Contraseña
        ps.setString(7, (String) empleado[6]); // Correo
        ps.setFloat(9, (Float) empleado[8]);  // Salario
        ps.setString(10, idEmpleado);          // ID Empleado

        int filasEmpleado = ps.executeUpdate();
        return filasEmpleado > 0;
    } catch (SQLException ex) {
        System.out.println("Error al editar empleado: " + ex.getMessage());
        ex.printStackTrace();
    } catch (IllegalArgumentException ex) {
        System.out.println("Error: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return false;
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

    public String obtenerIdDireccionPorEmpleado(String idProveedor) {
    conectar();
    String idDireccion = "";
    try {
        String sql = "SELECT ID_DIRECCION FROM EMPLEADO WHERE ID_PROVEEDOR = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, idProveedor);
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

    // Eliminar empleado
    public boolean deleteEmployee(String idEmpleado, String idDireccion) {
    conectar();
    try {
        // Eliminar empleado
        String sqlEmpleado = "DELETE FROM EMPLEADO WHERE ID_EMPLEADO = ?";
        ps = conn.prepareStatement(sqlEmpleado);
        ps.setString(1, idEmpleado);
        ps.executeUpdate();

        // Eliminar dirección
        String sqlDireccion = "DELETE FROM DIRECCION WHERE ID_DIRECCION = ?";
        ps = conn.prepareStatement(sqlDireccion);
        ps.setString(1, idDireccion);
        ps.executeUpdate();

        return true;
    } catch (SQLException ex) {
        System.out.println("Error al eliminar empleado: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return false;
}

    // Listar empleados con dirección concatenada
    public List<Object[]> listProveedores() {
    conectar();
    List<Object[]> proveedores = new ArrayList<>();
    try {
        String sql = "SELECT ID_PROVEEDOR, NOMBRE_EMPRESA, NOMBRE_CONTACTO, LADA, TELEFONO, EXTENSION, " +
                     "CORREO, " +
                     "CALLE || ' ' || NVL(EXTERIOR, '') || ' ' || NVL(INTERIOR, '') || ', ' || COLONIA || ', ' || ALCAL_MUN || ', ' || ESTADO AS DIRECCION " +
                     "FROM PROVEEDOR " +
                     "JOIN DIRECCION USING (ID_DIRECCION) " +
                     "JOIN ESTADO USING (ID_ESTADO)";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            Object[] proveedor = new Object[8];
            proveedor[0] = rs.getString("ID_PROVEEDOR");  // String
            proveedor[1] = rs.getString("NOMBRE_EMPRESA");  // String
            proveedor[2] = rs.getString("NOMBRE_CONTACTO");  // String
            proveedor[3] = rs.getInt("LADA");  // Número
            proveedor[4] = rs.getInt("TELEFONO");  // Número
            proveedor[5] = rs.getInt("EXTENSION");  // Número
            proveedor[6] = rs.getString("CORREO");  // String
            proveedor[7] = rs.getString("DIRECCION");  // String
            proveedores.add(proveedor);
        }
    } catch (SQLException ex) {
        System.out.println("Error al listar proveedores: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return proveedores;
}

}
