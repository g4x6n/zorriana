package database.dao;

import database.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoEmpleado extends Conexion {

    // Método para obtener el ID del empleado con formato "E(yy)(secuencia)"
    private String generarIdEmpleado() {
        String idEmpleado = "";
        try {
            // Obtenemos el año actual
            String anno = String.valueOf(java.time.Year.now().getValue()).substring(2);
            
            // Consulta para obtener la última secuencia del año
            String sql = "SELECT MAX(ID_EMPLEADO) FROM EMPLEADO WHERE ID_EMPLEADO LIKE ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "E" + anno + "%");
            rs = ps.executeQuery();
            
            if (rs.next()) {
                String maxId = rs.getString(1);
                if (maxId != null) {
                    // Extraemos la secuencia
                    int secuencia = Integer.parseInt(maxId.substring(4)) + 1; // Sumar 1 a la última secuencia
                    idEmpleado = "E" + anno + String.format("%02d", secuencia); // Generar el nuevo ID con formato
                } else {
                    idEmpleado = "E" + anno + "01"; // Si no hay registros, el primer ID será Eyy01
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al generar el ID del empleado: " + ex.getMessage());
        }
        return idEmpleado;
    }

    // Método para obtener un empleado por usuario y contraseña (para login)
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

    // Método para buscar empleados por nombre
    public List<Object[]> searchEmployeesByName(String filtro) {
        conectar();
        List<Object[]> empleados = new ArrayList<>();
        
        try {
            String sentenciaSQL = "SELECT ID_EMPLEADO, NOMBRE, AP_PATERNO, AP_MATERNO, FECHA_REGISTRO, CORREO, CALLE " +
                                  "FROM EMPLEADO WHERE NOMBRE LIKE ? OR AP_PATERNO LIKE ? OR AP_MATERNO LIKE ?";

            ps = conn.prepareStatement(sentenciaSQL);
            ps.setString(1, "%" + filtro + "%");
            ps.setString(2, "%" + filtro + "%");
            ps.setString(3, "%" + filtro + "%");

            rs = ps.executeQuery();

            while (rs.next()) {
                Object[] empleado = new Object[7]; // 6 datos + ID
                empleado[0] = rs.getInt("ID_EMPLEADO");
                empleado[1] = rs.getString("NOMBRE");
                empleado[2] = rs.getString("AP_PATERNO");
                empleado[3] = rs.getString("AP_MATERNO");
                empleado[4] = rs.getString("FECHA_REGISTRO");
                empleado[5] = rs.getString("CORREO");
                empleado[6] = rs.getString("CALLE");
                empleados.add(empleado);
            }
        } catch (SQLException ex) {
            System.out.println("Error al buscar empleados: " + ex.getMessage());
        } finally {
            desconectar();
        }
        return empleados;
    }

    // Método para agregar un nuevo empleado
    public boolean saveEmployee(Object[] empleado) {
        String idEmpleado = generarIdEmpleado(); // Generamos el ID del empleado
        
        try {
            String sql = "INSERT INTO EMPLEADO (ID_EMPLEADO, NOMBRE, AP_PATERNO, AP_MATERNO, FECHA_REGISTRO, USUARIO_EMPLEADO, " +
                         "CONTRASENIA_EMPLEADO, CORREO, ID_PUESTO, CALLE, EXTERIOR, INTERIOR, COLONIA, CP, ALCALDIA_MUNICIPIO, ESTADO) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            ps = conn.prepareStatement(sql);
            ps.setString(1, idEmpleado); // Asignar el ID generado
            for (int i = 0; i < 14; i++) {
                ps.setObject(i + 2, empleado[i]); // Rellenar el resto de los campos
            }

            int filas = ps.executeUpdate();
            return filas > 0; // Si se insertaron filas, el empleado se guardó con éxito
        } catch (SQLException ex) {
            System.out.println("Error al guardar empleado: " + ex.getMessage());
            return false;
        }
    }

    // Método para eliminar un empleado por su ID
    public boolean deleteEmployee(int idEmpleado) {
        try {
            String sql = "DELETE FROM EMPLEADO WHERE ID_EMPLEADO = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idEmpleado);
            int filas = ps.executeUpdate();
            return filas > 0; // Si se eliminaron filas, el empleado se eliminó con éxito
        } catch (SQLException ex) {
            System.out.println("Error al eliminar empleado: " + ex.getMessage());
            return false;
        }
    }

    // Método para editar un empleado
    public boolean updateEmployee(Object[] empleado) {
        try {
            String sql = "UPDATE EMPLEADO SET NOMBRE = ?, AP_PATERNO = ?, AP_MATERNO = ?, FECHA_REGISTRO = ?, " +
                         "USUARIO_EMPLEADO = ?, CONTRASENIA_EMPLEADO = ?, CORREO = ?, ID_PUESTO = ?, CALLE = ?, " +
                         "EXTERIOR = ?, INTERIOR = ?, COLONIA = ?, CP = ?, ALCALDIA_MUNICIPIO = ?, ESTADO = ? " +
                         "WHERE ID_EMPLEADO = ?";

            ps = conn.prepareStatement(sql);
            for (int i = 0; i < 15; i++) {
                ps.setObject(i + 1, empleado[i]);
            }
            ps.setInt(16, (int) empleado[0]); // ID_EMPLEADO para la actualización

            int filas = ps.executeUpdate();
            return filas > 0; // Si se actualizó la fila, el empleado fue actualizado con éxito
        } catch (SQLException ex) {
            System.out.println("Error al actualizar empleado: " + ex.getMessage());
            return false;
        }
    }
}
