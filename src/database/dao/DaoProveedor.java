package database.dao;

import database.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import database.dao.DaoProveedor;

public class DaoProveedor extends Conexion {
    
public boolean actualizarProveedor(String idProveedor, Object[] proveedor) {
    conectar();
    try {
        
        // Actualizar la tabla DIRECCION
        String sqlDireccion = "UPDATE DIRECCION " +
                              "SET CALLE = ?, EXTERIOR = ?, INTERIOR = ?, COLONIA = ?, CP = ?, ALCAL_MUN = ?, ID_ESTADO = ? " +
                              "WHERE ID_DIRECCION = (SELECT ID_DIRECCION FROM PROVEEDOR WHERE ID_PROVEEDOR = ?)";
        ps = conn.prepareStatement(sqlDireccion);

        // Asignar parámetros para DIRECCION
        ps.setString(1, (String) proveedor[6]);  // CALLE
        ps.setString(2, (String) proveedor[7]);  // EXTERIOR
        ps.setString(3, (String) proveedor[8]);  // INTERIOR
        ps.setString(4, (String) proveedor[9]);  // COLONIA
        ps.setString(5, (String) proveedor[10]); // CP
        ps.setString(6, (String) proveedor[11]); // ALCAL_MUN
        ps.setString(7, obtenerCodigoEstado((String) proveedor[12])); // ID_ESTADO
        ps.setString(8, idProveedor); // ID_PROVEEDOR

      
        int filasDireccion = ps.executeUpdate();

        // Actualizar la tabla PROVEEDOR si DIRECCION se actualizó correctamente
        if (filasDireccion > 0) {
            String sqlProveedor = "UPDATE PROVEEDOR " +
                                  "SET NOMBRE_EMPRESA = ?, NOMBRE_CONTACTO = ?, LADA = ?, TELEFONO = ?, EXTENSION = ?, CORREO = ? " +
                                  "WHERE ID_PROVEEDOR = ?";
            ps = conn.prepareStatement(sqlProveedor);

            // Asignar parámetros para PROVEEDOR
            ps.setString(1, ajustarLongitud((String) proveedor[0], 50)); // NOMBRE_EMPRESA (CHAR(50))
            ps.setString(2, ajustarLongitud((String) proveedor[1], 30)); // NOMBRE_CONTACTO (CHAR(30))
            ps.setInt(3, (int) proveedor[2]);                           // LADA (NUMBER)
            ps.setString(4, ajustarLongitud((String) proveedor[3], 11)); // TELEFONO (CHAR(11))
            ps.setInt(5, (int) proveedor[4]);                           // EXTENSION (NUMBER)
            ps.setString(6, (String) proveedor[5]);                     // CORREO (NVARCHAR2(60))
            ps.setString(7, idProveedor);                               // ID_PROVEEDOR (NVARCHAR2(8))

            
            int filasProveedor = ps.executeUpdate();
            return filasProveedor > 0;
        }
    } catch (SQLException ex) {
        System.out.println("Error al actualizar proveedor: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        System.out.println("Consulta SQL para DIRECCION:");
        System.out.println("UPDATE DIRECCION SET CALLE = ?, EXTERIOR = ?, INTERIOR = ?, COLONIA = ?, CP = ?, ALCAL_MUN = ?, ID_ESTADO = ? WHERE ID_DIRECCION = (SELECT ID_DIRECCION FROM PROVEEDOR WHERE ID_PROVEEDOR = ?)");
        System.out.println("Parámetros:");
        System.out.println("1: " + proveedor[6]);
        System.out.println("2: " + proveedor[7]);
        System.out.println("3: " + proveedor[8]);
        System.out.println("4: " + proveedor[9]);
        System.out.println("5: " + proveedor[10]);
        System.out.println("6: " + proveedor[11]);
        System.out.println("7: " + obtenerCodigoEstado((String) proveedor[12]));
        System.out.println("8: " + idProveedor);
        System.out.println("Consulta SQL para PROVEEDOR:");
        System.out.println("UPDATE PROVEEDOR SET NOMBRE_EMPRESA = ?, NOMBRE_CONTACTO = ?, LADA = ?, TELEFONO = ?, EXTENSION = ?, CORREO = ? WHERE ID_PROVEEDOR = ?");
        System.out.println("Parámetros:");
        System.out.println("1: " + proveedor[0]);
        System.out.println("2: " + proveedor[1]);
        System.out.println("3: " + proveedor[2]);
        System.out.println("4: " + proveedor[3]);
        System.out.println("5: " + proveedor[4]);
        System.out.println("6: " + proveedor[5]);
        System.out.println("7: " + idProveedor);


        desconectar();
    }
    return false;
}

private String ajustarLongitud(String valor, int longitud) {
    if (valor == null) {
        // Retorna una cadena con espacios en blanco si el valor es null
        return String.format("%-" + longitud + "s", "");
    }
    // Ajusta la longitud: recorta si es demasiado largo, rellena con espacios si es corto
    return String.format("%-" + longitud + "s", valor.substring(0, Math.min(valor.length(), longitud)));
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



}
