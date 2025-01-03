package database.dao;

import database.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoProveedor extends Conexion {

    // Generar el ID del empleado
    private String generarIdEmpleado() {
        String idEmpleado = "";
        try {
            String sql = "SELECT MAX(ID_EMPLEADO) FROM EMPLEADO";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                String maxId = rs.getString(1);
                if (maxId != null) {
                    int secuencia = Integer.parseInt(maxId.substring(1)) + 1;
                    idEmpleado = "E" + String.format("%04d", secuencia);
                } else {
                    idEmpleado = "E0001"; // Primer ID
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al generar ID del empleado: " + ex.getMessage());
        }
        return idEmpleado;
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


    // Agregar un nuevo empleado
public boolean addProveedor(Object[] empleado) {
    conectar(); // Conectar a la base de datos
    String idEmpleado = generarIdEmpleado();
    String idDireccion = generarIdDireccion();

    try {
        // Obtener códigos necesarios
        String codigoEstado = obtenerCodigoEstado((String) empleado[14]); // Estado
        
        // Consulta SQL para insertar dirección
        String sqlDireccion = "INSERT INTO DIRECCION (ID_DIRECCION, CALLE, EXTERIOR, INTERIOR, COLONIA, CP, ALCAL_MUN, ID_ESTADO) " +
                              "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sqlDireccion);
        ps.setString(1, idDireccion);
        ps.setString(2, (String) empleado[8]);  // Calle
        ps.setString(3, (String) empleado[9]);  // Exterior
        ps.setString(4, (String) empleado[10]); // Interior
        ps.setString(5, (String) empleado[11]); // Colonia
        ps.setString(6, (String) empleado[12]); // CP
        ps.setString(7, (String) empleado[13]); // Alcaldía/Municipio
        ps.setString(8, codigoEstado);         // Código de estado

        // Ejecutar la consulta de inserción de dirección
        int filasDireccion = ps.executeUpdate();

        // Si la dirección se inserta correctamente, proceder con empleado
        if (filasDireccion > 0) {
            // Consulta SQL para insertar empleado
            String sqlEmpleado = "INSERT INTO EMPLEADO (ID_EMPLEADO, NOMBRE, AP_PATERNO, AP_MATERNO, FECHA_REG, USUARIO_EMPLEADO, " +
                                 "CONTRASENIA_EMPLEADO, CORREO, ID_PUESTO, SUELDO, ID_DIRECCION) " +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sqlEmpleado);
            ps.setString(1, idEmpleado);
            ps.setString(2, (String) empleado[0]); // Nombre
            ps.setString(3, (String) empleado[1]); // Apellido Paterno
            ps.setString(4, (String) empleado[2]); // Apellido Materno
            ps.setDate(5, Date.valueOf(empleado[3].toString())); // Fecha de Registro
            ps.setString(6, (String) empleado[4]); // Usuario
            ps.setString(7, (String) empleado[5]); // Contraseña
            ps.setString(8, (String) empleado[6]); // Correo
            ps.setFloat(10, (float) empleado[8]); // Sueldo
            ps.setString(11, idDireccion);       // ID Dirección

            // Ejecutar la consulta de inserción de empleado
            int filasEmpleado = ps.executeUpdate();
            System.out.println("Empleado agregado correctamente. Sueldo enviado: " + empleado[8]);
            return filasEmpleado > 0;
        }
    } catch (SQLException ex) {
        System.out.println("Error al agregar empleado: " + ex.getMessage());
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


public String obtenerIdDireccionPorEmpleado(String idEmpleado) {
    conectar();
    String idDireccion = "";
    try {
        String sql = "SELECT ID_DIRECCION FROM EMPLEADO WHERE ID_EMPLEADO = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, idEmpleado);
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
