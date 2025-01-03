
package database.dao;

import database.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoVentas extends Conexion {

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

    // Obtener ID del puesto
    private String obtenerIdPuesto(String nombrePuesto) {
        String idPuesto = "";
        try {
            String sql = "SELECT ID_PUESTO FROM PUESTO WHERE UPPER(PUESTO) = UPPER(?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, nombrePuesto);
            rs = ps.executeQuery();

            if (rs.next()) {
                idPuesto = rs.getString(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener ID del puesto: " + ex.getMessage());
        }
        return idPuesto;
    }

    // Agregar un nuevo empleado
    public boolean addEmployee(Object[] empleado) {
        conectar();
        String idEmpleado = generarIdEmpleado();
        String idDireccion = generarIdDireccion();
        try {
            // Obtener códigos
            String codigoEstado = obtenerCodigoEstado((String) empleado[14]);
            String idPuesto = obtenerIdPuesto((String) empleado[7]);

            // Insertar dirección
            String sqlDireccion = "INSERT INTO DIRECCION (ID_DIRECCION, CALLE, EXTERIOR, INTERIOR, COLONIA, CP, ALCAL_MUN, ID_ESTADO) " +
                                  "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sqlDireccion);
            ps.setString(1, idDireccion);
            ps.setString(2, (String) empleado[8]); // Calle
            ps.setString(3, (String) empleado[9]); // Exterior
            ps.setString(4, (String) empleado[10]); // Interior
            ps.setString(5, (String) empleado[11]); // Colonia
            ps.setString(6, (String) empleado[12]); // CP
            ps.setString(7, (String) empleado[13]); // Alcaldía/Municipio
            ps.setString(8, codigoEstado);

            int filasDireccion = ps.executeUpdate();
            if (filasDireccion > 0) {
                // Insertar empleado
                String sqlEmpleado = "INSERT INTO EMPLEADO (ID_EMPLEADO, NOMBRE, AP_PATERNO, AP_MATERNO, FECHA_REG, USUARIO_EMPLEADO, " +
                                     "CONTRASENIA_EMPLEADO, CORREO, ID_PUESTO, SUELDO, ID_DIRECCION) " +
                                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                ps = conn.prepareStatement(sqlEmpleado);
                ps.setString(1, idEmpleado);
                ps.setString(2, (String) empleado[0]); // Nombre
                ps.setString(3, (String) empleado[1]); // Apellido Paterno
                ps.setString(4, (String) empleado[2]); // Apellido Materno
                ps.setDate(5, (Date) empleado[3]); // Fecha de Registro
                ps.setString(6, (String) empleado[4]); // Usuario
                ps.setString(7, (String) empleado[5]); // Contraseña
                ps.setString(8, (String) empleado[6]); // Correo
                ps.setString(9, idPuesto); // ID del puesto
                ps.setFloat(10, (Float) empleado[15]); // Sueldo
                ps.setString(11, idDireccion); // ID Dirección

                int filasEmpleado = ps.executeUpdate();
                return filasEmpleado > 0;
            }
        } catch (SQLException ex) {
            System.out.println("Error al agregar empleado: " + ex.getMessage());
        } finally {
            desconectar();
        }
        return false;
    }

    public Object[] obtenerEmpleadoPorId(String idEmpleado) {
    conectar();
    Object[] empleado = new Object[18]; // Tamaño actualizado según la consulta SQL
    try {
        String sql = "SELECT E.ID_EMPLEADO, E.NOMBRE, E.AP_PATERNO, E.AP_MATERNO, E.FECHA_REG, " +
                     "E.USUARIO_EMPLEADO, E.CONTRASENIA_EMPLEADO, E.CORREO, P.PUESTO, E.SUELDO, " +
                     "D.CALLE, D.EXTERIOR, D.INTERIOR, D.COLONIA, D.CP, D.ALCAL_MUN, EST.ESTADO, " +
                     "E.ID_DIRECCION " +
                     "FROM EMPLEADO E " +
                     "JOIN DIRECCION D ON E.ID_DIRECCION = D.ID_DIRECCION " +
                     "JOIN ESTADO EST ON D.ID_ESTADO = EST.ID_ESTADO " +
                     "JOIN PUESTO P ON E.ID_PUESTO = P.ID_PUESTO " +
                     "WHERE E.ID_EMPLEADO = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, idEmpleado);
        rs = ps.executeQuery();

        if (rs.next()) {
            empleado[0] = rs.getString("ID_EMPLEADO");
            empleado[1] = rs.getString("NOMBRE");
            empleado[2] = rs.getString("AP_PATERNO");
            empleado[3] = rs.getString("AP_MATERNO");
            empleado[4] = rs.getDate("FECHA_REG"); // Asegúrate de que es de tipo Date
            empleado[5] = rs.getString("USUARIO_EMPLEADO");
            empleado[6] = rs.getString("CONTRASENIA_EMPLEADO");
            empleado[7] = rs.getString("CORREO");
            empleado[8] = rs.getString("PUESTO");
            empleado[9] = rs.getFloat("SUELDO"); // Asegúrate de que es un Float
            empleado[10] = rs.getString("CALLE");
            empleado[11] = rs.getString("EXTERIOR");
            empleado[12] = rs.getString("INTERIOR");
            empleado[13] = rs.getString("COLONIA");
            empleado[14] = rs.getString("CP");
            empleado[15] = rs.getString("ALCAL_MUN");
            empleado[16] = rs.getString("ESTADO");
            empleado[17] = rs.getString("ID_DIRECCION");
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener detalles del empleado: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return empleado;
}


    // Editar un empleado existente
    public boolean updateEmployee(Object[] empleado) {
        conectar();
        try {
            String idDireccion = (String) empleado[16];
            String idEmpleado = (String) empleado[17];

            // Actualizar dirección
            String sqlDireccion = "UPDATE DIRECCION SET CALLE = ?, EXTERIOR = ?, INTERIOR = ?, COLONIA = ?, CP = ?, ALCAL_MUN = ?, ID_ESTADO = ? " +
                                  "WHERE ID_DIRECCION = ?";
            ps = conn.prepareStatement(sqlDireccion);
            ps.setString(1, (String) empleado[8]);
            ps.setString(2, (String) empleado[9]);
            ps.setString(3, (String) empleado[10]);
            ps.setString(4, (String) empleado[11]);
            ps.setString(5, (String) empleado[12]);
            ps.setString(6, (String) empleado[13]);
            ps.setString(7, obtenerCodigoEstado((String) empleado[14]));
            ps.setString(8, idDireccion);

            ps.executeUpdate();

            // Actualizar empleado
            String sqlEmpleado = "UPDATE EMPLEADO SET NOMBRE = ?, AP_PATERNO = ?, AP_MATERNO = ?, FECHA_REG = ?, USUARIO_EMPLEADO = ?, " +
                                 "CONTRASENIA_EMPLEADO = ?, CORREO = ?, ID_PUESTO = ?, SUELDO = ? " +
                                 "WHERE ID_EMPLEADO = ?";
            ps = conn.prepareStatement(sqlEmpleado);
            ps.setString(1, (String) empleado[0]);
            ps.setString(2, (String) empleado[1]);
            ps.setString(3, (String) empleado[2]);
            ps.setDate(4, (Date) empleado[3]);
            ps.setString(5, (String) empleado[4]);
            ps.setString(6, (String) empleado[5]);
            ps.setString(7, (String) empleado[6]);
            ps.setString(8, obtenerIdPuesto((String) empleado[7]));
            ps.setFloat(9, (Float) empleado[15]);
            ps.setString(10, idEmpleado);

            int filasEmpleado = ps.executeUpdate();
            return filasEmpleado > 0;
        } catch (SQLException ex) {
            System.out.println("Error al editar empleado: " + ex.getMessage());
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

public List<String> obtenerPuestos() {
    conectar();
    List<String> puestos = new ArrayList<>();
    try {
        String sql = "SELECT PUESTO FROM PUESTO";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            puestos.add(rs.getString("PUESTO")); // Agrega el nombre del puesto a la lista
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener puestos: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return puestos;
}
public List<Object[]> buscarEmpleado(String filtro) {
    conectar();
    List<Object[]> empleados = new ArrayList<>();
    try {
        // Consulta SQL con alias más corto para la concatenación
        String sql = "SELECT E.ID_EMPLEADO, E.NOMBRE, E.AP_PATERNO, E.AP_MATERNO, E.FECHA_REG, E.CORREO, " +
                     "D.CALLE || ' ' || NVL(D.EXTERIOR, '') || ' ' || NVL(D.INTERIOR, '') || ', ' || D.COLONIA || ', ' || D.ALCAL_MUN || ', ' || EST.ESTADO AS DIR " +
                     "FROM EMPLEADO E " +
                     "JOIN DIRECCION D ON E.ID_DIRECCION = D.ID_DIRECCION " +
                     "JOIN ESTADO EST ON D.ID_ESTADO = EST.ID_ESTADO " +
                     "WHERE UPPER(E.NOMBRE) LIKE UPPER(?) OR UPPER(E.AP_PATERNO) LIKE UPPER(?) OR UPPER(E.AP_MATERNO) LIKE UPPER(?)";

        ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + filtro + "%");
        ps.setString(2, "%" + filtro + "%");
        ps.setString(3, "%" + filtro + "%");
        rs = ps.executeQuery();

        while (rs.next()) {
            Object[] empleado = new Object[7]; // Ajusta según las columnas seleccionadas
            empleado[0] = rs.getString("ID_EMPLEADO");
            empleado[1] = rs.getString("NOMBRE");
            empleado[2] = rs.getString("AP_PATERNO");
            empleado[3] = rs.getString("AP_MATERNO");
            empleado[4] = rs.getDate("FECHA_REG");
            empleado[5] = rs.getString("CORREO");
            empleado[6] = rs.getString("DIR"); // Alias corto para la concatenación
            empleados.add(empleado);
        }
    } catch (SQLException ex) {
        System.out.println("Error al buscar empleados: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return empleados;
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

public Object[] getEmployeeByUsr(String usr, char[] psw) {
        conectar(); // Establece conexión con la base de datos
        Object[] employee = new Object[6]; // Arreglo para almacenar datos del empleado

        try {
            // Consulta SQL para obtener los datos del empleado
            String sentenciaSQL = "SELECT ID_EMPLEADO, ID_PUESTO, PUESTO, NOMBRE, AP_PATERNO, AP_MATERNO " +
                                  "FROM EMPLEADO " +
                                  "JOIN PUESTO USING (ID_PUESTO) " +
                                  "WHERE UPPER(USUARIO_EMPLEADO) = UPPER(?) " +
                                  "AND UPPER(CONTRASENIA_EMPLEADO) = UPPER(?)";

            ps = conn.prepareStatement(sentenciaSQL);
            ps.setString(1, usr); // Establece el parámetro del usuario
            ps.setString(2, String.valueOf(psw)); // Establece el parámetro de la contraseña

            rs = ps.executeQuery();

            if (rs.next()) {
                // Verifica y asigna valores del resultado
                employee[0] = rs.getObject("ID_EMPLEADO");
                employee[1] = rs.getObject("ID_PUESTO");
                employee[2] = rs.getString("PUESTO");
                employee[3] = rs.getString("NOMBRE");
                employee[4] = rs.getString("AP_PATERNO");
                employee[5] = rs.getString("AP_MATERNO");
            } else {
                employee = null; // No se encontró ningún registro
            }
        } catch (SQLException ex) {
            System.out.println("Error SQL: " + ex.getSQLState());
            System.out.println("Mensaje: " + ex.getMessage());
            employee = null; // Devuelve nulo en caso de error
        } finally {
            desconectar(); // Cierra la conexión a la base de datos
        }
        return employee; // Retorna el arreglo con los datos o nulo
    }

    // Listar empleados con dirección concatenada
    public List<Object[]> listEmployees() {
        conectar();
        List<Object[]> empleados = new ArrayList<>();
        try {
            String sql = "SELECT E.ID_EMPLEADO, E.NOMBRE, E.AP_PATERNO, E.AP_MATERNO, E.FECHA_REG, E.USUARIO_EMPLEADO, " +
                         "E.CORREO, P.PUESTO, E.SUELDO, " +
                         "D.CALLE || ' ' || NVL(D.EXTERIOR, '') || ' ' || NVL(D.INTERIOR, '') || ', ' || D.COLONIA || ', ' || D.ALCAL_MUN || ', ' || EST.ESTADO AS DIRECCION " +
                         "FROM EMPLEADO E " +
                         "JOIN DIRECCION D ON E.ID_DIRECCION = D.ID_DIRECCION " +
                         "JOIN ESTADO EST ON D.ID_ESTADO = EST.ID_ESTADO " +
                         "JOIN PUESTO P ON E.ID_PUESTO = P.ID_PUESTO";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Object[] empleado = new Object[10];
                empleado[0] = rs.getString("ID_EMPLEADO");
                empleado[1] = rs.getString("NOMBRE");
                empleado[2] = rs.getString("AP_PATERNO");
                empleado[3] = rs.getString("AP_MATERNO");
                empleado[4] = rs.getDate("FECHA_REG");
                empleado[5] = rs.getString("USUARIO_EMPLEADO");
                empleado[6] = rs.getString("CORREO");
                empleado[7] = rs.getString("PUESTO");
                empleado[8] = rs.getFloat("SUELDO");
                empleado[9] = rs.getString("DIRECCION");
                empleados.add(empleado);
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar empleados: " + ex.getMessage());
        } finally {
            desconectar();
        }
        return empleados;
    }
}
