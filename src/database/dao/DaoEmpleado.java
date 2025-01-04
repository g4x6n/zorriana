package database.dao;

import database.Conexion;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DaoEmpleado extends Conexion {
    // Obtener código del estado
public String obtenerCodigoEstado(String nombreEstado) {
    conectar(); // Conectar a la base de datos
    String codigoEstado = "";
    System.out.println("Valor recibido para buscar estado: " + nombreEstado);
    try {
        // Consulta para obtener el ID del estado basado en el nombre del estado
        String sql = "SELECT ID_ESTADO FROM ESTADO WHERE UPPER(ESTADO) = UPPER(?)";
        ps = conn.prepareStatement(sql);
        ps.setString(1, nombreEstado.trim()); // Elimina espacios adicionales y establece el valor
        rs = ps.executeQuery();

        if (rs.next()) {
            codigoEstado = rs.getString(1); // Obtén el ID del estado
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener código del estado: " + ex.getMessage());
    } finally {
        desconectar(); // Cerrar la conexión después de ejecutar
    }
    return codigoEstado; // Devuelve el código del estado o vacío si no se encontró
}
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
    public boolean insertarEmpleado(String nombre, String apPaterno, String apMaterno, String fechaReg, String correo, String idDireccion, String idPuesto, float sueldo, String usuario, String contrasena) {
    conectar(); // Conectar a la base de datos
    try {
        // Formatear la fecha de registro al formato esperado por Oracle
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);

        LocalDateTime fechaRegistro = LocalDate.parse(fechaReg, inputFormatter).atStartOfDay();
        String fechaFormateada = fechaRegistro.format(outputFormatter).toUpperCase();

        // Consulta SQL para insertar un empleado
        String sql = "INSERT INTO EMPLEADO (ID_EMPLEADO, NOMBRE, AP_PATERNO, AP_MATERNO, FECHA_REG, CORREO, ID_DIRECCION, ID_PUESTO, SUELDO, USUARIO_EMPLEADO, CONTRASENIA_EMPLEADO) "
                   + "VALUES (SEQ_EMPLEADO_ID.NEXTVAL, ?, ?, ?, TO_DATE(?, 'DD-MON-YYYY HH24:MI:SS'), ?, ?, ?, ?, ?, ?)";

        ps = conn.prepareStatement(sql);
        ps.setString(1, nombre);        // Nombre
        ps.setString(2, apPaterno);    // Apellido Paterno
        ps.setString(3, apMaterno);    // Apellido Materno
        ps.setString(4, fechaFormateada); // Fecha de Registro (formateada)
        ps.setString(5, correo);       // Correo
        ps.setString(6, idDireccion);  // ID de Dirección
        ps.setString(7, idPuesto);     // ID de Puesto
        ps.setFloat(8, sueldo);        // Sueldo
        ps.setString(9, usuario);      // Usuario
        ps.setString(10, contrasena);  // Contraseña

        // Ejecutar la consulta
        return ps.executeUpdate() > 0; // Devuelve true si la inserción fue exitosa
    } catch (SQLException ex) {
        System.out.println("Error al insertar empleado: " + ex.getMessage());
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
public String obtenerIdPuesto(String nombrePuesto) {
    conectar(); // Conectar a la base de datos
    String idPuesto = null;

    try {
        String sql = "SELECT ID_PUESTO FROM PUESTO WHERE TRIM(UPPER(PUESTO)) = TRIM(UPPER(?))";
        ps = conn.prepareStatement(sql);
        ps.setString(1, nombrePuesto.trim());
        rs = ps.executeQuery();

        if (rs.next()) {
            idPuesto = rs.getString("ID_PUESTO"); // Devuelve el ID si el puesto existe
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener ID del puesto: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        desconectar(); // Cerrar conexión
    }

    return idPuesto; // Devuelve el ID o null si no se encontró
}

public String insertarPuestoSiNoExiste(String nombrePuesto) {
    conectar(); // Conectar a la base de datos
    String idPuesto = null;

    try {
        // Verificar si el puesto ya existe
        String sqlBuscar = "SELECT ID_PUESTO FROM PUESTO WHERE TRIM(UPPER(PUESTO)) = TRIM(UPPER(?))";
        ps = conn.prepareStatement(sqlBuscar);
        ps.setString(1, nombrePuesto.trim());
        rs = ps.executeQuery();

        if (rs.next()) {
            idPuesto = rs.getString("ID_PUESTO"); // Si existe, obtener el ID
        } else {
            // Generar un nuevo ID para el puesto
            String sqlNuevoId = "SELECT MAX(ID_PUESTO) + 1 AS NEXT_ID FROM PUESTO";
            ps = conn.prepareStatement(sqlNuevoId);
            rs = ps.executeQuery();

            if (rs.next()) {
                idPuesto = rs.getString("NEXT_ID");
            } else {
                throw new SQLException("No se pudo generar un nuevo ID para el puesto.");
            }

            // Insertar el nuevo puesto
            String sqlInsertar = "INSERT INTO PUESTO (ID_PUESTO, PUESTO) VALUES (?, ?)";
            ps = conn.prepareStatement(sqlInsertar);
            ps.setString(1, idPuesto);
            ps.setString(2, nombrePuesto.trim());
            ps.executeUpdate();
            System.out.println("Puesto insertado: " + nombrePuesto + " con ID " + idPuesto);
        }
    } catch (SQLException ex) {
        System.out.println("Error al insertar o verificar el puesto: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        desconectar(); // Cerrar la conexión
    }

    return idPuesto;
}




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
    } finally {
        desconectar(); // Cerrar conexión
    }

    return idDireccion;
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
    } finally {
        desconectar(); // Cerrar conexión
    }
    return idDireccion; // Retornar el ID de la dirección o null si no se encontró
}

   
    // Agregar un nuevo empleado
public boolean addEmployee(Object[] empleado) {
    conectar(); 
    String idEmpleado = generarIdEmpleado();
    String idDireccion = generarIdDireccion();

    try {
        String codigoEstado = obtenerCodigoEstado((String) empleado[14]); 
        String idPuesto = obtenerIdPuesto((String) empleado[7]);

        String sqlDireccion = "INSERT INTO DIRECCION (ID_DIRECCION, CALLE, EXTERIOR, INTERIOR, COLONIA, CP, ALCAL_MUN, ID_ESTADO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sqlDireccion);
        ps.setString(1, idDireccion);
        ps.setString(2, (String) empleado[8]);  
        ps.setString(3, (String) empleado[9]);  
        ps.setString(4, (String) empleado[10]); 
        ps.setString(5, (String) empleado[11]); 
        ps.setString(6, (String) empleado[12]); 
        ps.setString(7, (String) empleado[13]); 
        ps.setString(8, codigoEstado); 

        int filasDireccion = ps.executeUpdate();
        if (filasDireccion > 0) {
            String sqlEmpleado = "INSERT INTO EMPLEADO (ID_EMPLEADO, NOMBRE, AP_PATERNO, AP_MATERNO, FECHA_REG, USUARIO_EMPLEADO, CONTRASENIA_EMPLEADO, CORREO, ID_PUESTO, SUELDO, ID_DIRECCION) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sqlEmpleado);
            ps.setString(1, idEmpleado);
            ps.setString(2, (String) empleado[0]); 
            ps.setString(3, (String) empleado[1]); 
            ps.setString(4, (String) empleado[2]); 
            ps.setDate(5, Date.valueOf(empleado[3].toString())); 
            ps.setString(6, (String) empleado[4]); 
            ps.setString(7, (String) empleado[5]); 
            ps.setString(8, (String) empleado[6]); 
            ps.setString(9, idPuesto);            
            ps.setFloat(10, (float) empleado[8]); 
            ps.setString(11, idDireccion);       

            return ps.executeUpdate() > 0;
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
                     "WHERE E.ID_EMPLEADO = ?" +
                     "ORDER BY E.ID_EMPLEADO";
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

public boolean updateEmployee(Object[] datosEmpleado) {
    conectar(); // Establecer conexión a la base de datos
    String sql = "UPDATE EMPLEADO SET " +
                 "NOMBRE = ?, " +
                 "AP_PATERNO = ?, " +
                 "AP_MATERNO = ?, " +
                 "FECHA_REG = TO_DATE(?, 'DD-MON-YY'), " +
                 "CORREO = ?, " +
                 "ID_PUESTO = ?, " +
                 "SUELDO = ?, " +
                 "USUARIO_EMPLEADO = ?, " +
                 "CONTRASENIA_EMPLEADO = ?, " +
                 "ID_DIRECCION = ? " +
                 "WHERE ID_EMPLEADO = ?";

    try {
        // Preparar la consulta
        ps = conn.prepareStatement(sql);

        // Asignar parámetros
        ps.setString(1, (String) datosEmpleado[1]); // NOMBRE
        ps.setString(2, (String) datosEmpleado[2]); // AP_PATERNO
        ps.setString(3, (String) datosEmpleado[3]); // AP_MATERNO
        ps.setString(4, (String) datosEmpleado[4]); // FECHA_REG
        ps.setString(5, (String) datosEmpleado[6]); // CORREO
        ps.setString(6, (String) datosEmpleado[7]); // ID_PUESTO
        ps.setFloat(7, (Float) datosEmpleado[8]);   // SUELDO
        ps.setString(8, (String) datosEmpleado[5]); // USUARIO_EMPLEADO
        ps.setString(9, (String) datosEmpleado[6]); // CONTRASENIA_EMPLEADO
        ps.setString(10, (String) datosEmpleado[9]); // ID_DIRECCION
        ps.setString(11, (String) datosEmpleado[0]); // ID_EMPLEADO

        // Ejecutar la actualización
        int filasActualizadas = ps.executeUpdate();

        return filasActualizadas > 0; // Retornar si se actualizaron filas

    } catch (SQLException e) {
        System.out.println("Error al actualizar empleado: " + e.getMessage());
        return false;
    } finally {
        desconectar(); // Cerrar la conexión en el bloque finally
    }
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
    conectar(); // Conectar a la base de datos
    String idDireccion = null;
    try {
        // Consulta SQL para obtener el ID de dirección basado en el ID del empleado
        String sql = "SELECT ID_DIRECCION FROM EMPLEADO WHERE ID_EMPLEADO = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, idEmpleado); // Asignar el parámetro ID_EMPLEADO
        rs = ps.executeQuery();

        if (rs.next()) {
            idDireccion = rs.getString("ID_DIRECCION"); // Obtener el valor del campo ID_DIRECCION
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener ID de la dirección del empleado: " + ex.getMessage());
    } finally {
        desconectar(); // Cerrar la conexión
    }
    return idDireccion; // Retornar el ID de dirección o null si no se encontró
}

public String obtenerSiguienteIdDireccion() {
    conectar(); // Conectar a la base de datos
    String idDireccion = null;

    try {
        // Consulta SQL para obtener el próximo valor de la secuencia
        String sql = "SELECT SEQ_DIRECCION_ID.NEXTVAL FROM DUAL";
        ps = conn.prepareStatement(sql); // Preparar la consulta
        rs = ps.executeQuery(); // Ejecutar la consulta

        if (rs.next()) {
            idDireccion = rs.getString(1); // Obtener el próximo valor de la secuencia
            System.out.println("ID de Dirección generado: " + idDireccion);
        } else {
            System.out.println("No se pudo obtener el siguiente valor de la secuencia.");
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener el siguiente ID de dirección: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        desconectar(); // Cerrar la conexión
    }

    return idDireccion; // Devolver el ID generado
}




    // Eliminar empleado
    public boolean deleteEmployee(String idEmpleado, String idDireccion) {
    conectar();
    try {
        String sqlEmpleado = "DELETE FROM EMPLEADO WHERE ID_EMPLEADO = ?";
        ps = conn.prepareStatement(sqlEmpleado);
        ps.setString(1, idEmpleado);
        ps.executeUpdate();

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
                                  "WHERE USUARIO_EMPLEADO = ? " +
                                  "AND CONTRASENIA_EMPLEADO = ?";

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
