/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package database.dao;

import database.Conexion;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex
 */
public class DaoEstado extends Conexion {
    
  
    public String insertarEstado(String nombreEstado) {
        conectar(); // Conectar a la base de datos
        String idEstado = null;

        try {
            // Obtener el próximo ID de la secuencia
            String sql = "SELECT SEQ_ID_ESTADO.NEXTVAL FROM DUAL";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                idEstado = rs.getString(1); // Obtener el ID generado por la secuencia
            } else {
                System.out.println("No se pudo generar un ID para el estado.");
                return null;
            }

            // Inserción de la nueva marca
            sql = "INSERT INTO ESTADO (ID_ESTADO, ESTADO) VALUES (?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, idEstado); // Usar el ID generado
            ps.setString(2, nombreEstado.trim()); // Usar el nombre ingresado por el usuario

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Error: No se pudo insertar el estado.");
                idEstado = null;
            }
        } catch (SQLException ex) {
            System.out.println("Error al insertar estado: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            desconectar(); // Cerrar la conexión
        }

        return idEstado; // Retornar el ID de la marca generada
    }
    
    public List<String> obtenerEstados() {
    List<String> estados = new ArrayList<>();
    conectar();
    try {
        String sql = "SELECT ESTADO FROM ESTADO ORDER BY ESTADO";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            estados.add(rs.getString("ESTADO"));
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener estados: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return estados;
}
    public boolean eliminarEstadoPorNombre(String nombreMarca) {
    conectar();
    boolean exito = false;
    try {
        String sql = "DELETE FROM ESTADO WHERE ESTADO = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, nombreMarca);
        exito = ps.executeUpdate() > 0;
    } catch (SQLException ex) {
        System.out.println("Error al eliminar estado: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return exito;
}


    
    
}
