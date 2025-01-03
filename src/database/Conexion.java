/*
 * Clase: Conexion.java
 * Fecha: 15/08/2013
 *
 * Desarrollador: Carlos Cortés Bazán
 * 
 * Descripción: Realiza la conexión a la base de datos
 *
 */

package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class Conexion {
    public Connection conn = null;
    public ArrayList<Statement> statements;
    public Statement s;
    public String sentenciaSQL;
    public ResultSet rs;
    public PreparedStatement ps;
    public CallableStatement cstmt;
    
    // Conecta a la base de datos del sistema y la crea
    public void conectar() {
    if (conn != null) {
        try {
            if (!conn.isClosed()) {
                return; // Si la conexión está activa, no hacer nada
            }
        } catch (SQLException e) {
            System.out.println("Error verificando la conexión: " + e.getMessage());
        }
    }

    // Crear nueva conexión si está cerrada o nula
    try {
        Properties props = new Properties();
        props.put("user", ConfigDataBase.JDBC_USER);
        props.put("password", ConfigDataBase.JDBC_PSW);
        conn = DriverManager.getConnection(ConfigDataBase.JDBC_PROTOCOL, props);
        conn.setAutoCommit(true);
        statements = new ArrayList<>();
        s = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statements.add(s);
    } catch (SQLException sqle) {
        System.out.println(ConfigDataBase.DB_T_ERROR + sqle.getSQLState() + ConfigDataBase.DB_NOCONECT);
        s = null;
    }
}



    
    // Lee los controladores de Oracle
    private void loadDriver(){
        try {
            Class.forName(ConfigDataBase.JDBC_DRIVER_ORACLE).newInstance();
        } 
        catch (ClassNotFoundException cnfe) {
            System.out.println(ConfigDataBase.DB_T_ERROR + ConfigDataBase.DB_DRIVERNO_REC);
        } 
        catch (InstantiationException ie) {
            System.out.println(ConfigDataBase.DB_T_ERROR + ConfigDataBase.DB_NOINST_DRIVER);
        } 
        catch (IllegalAccessException iae) {
            System.out.println(ConfigDataBase.DB_T_ERROR + ConfigDataBase.DB_NOACCESS_DRIVER);
        }
    }
    
public void desconectar() {
    try {
        // Cerrar todas las sentencias si existen
        if (statements != null) {
            for (Statement stmt : statements) {
                if (stmt != null && !stmt.isClosed()) {
                    stmt.close();
                }
            }
            statements.clear(); // Limpiar la lista después de cerrar
        }

        // Cerrar la sentencia principal si está abierta
        if (s != null && !s.isClosed()) {
            s.close();
        }

        // Cerrar la conexión si está abierta
        if (conn != null && !conn.isClosed()) {
            conn.close();
            System.out.println("Conexión cerrada correctamente.");
        }
    } catch (SQLException sqle) {
        System.out.println(ConfigDataBase.DB_T_ERROR + sqle.getSQLState() +
                ConfigDataBase.DB_CLOSECON_ERR + " " + sqle.getMessage());
        sqle.printStackTrace();
    }
}
}



