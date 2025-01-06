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
public class DaoMarca extends Conexion {
    
  
    public String insertarMarca(String nombreMarca) {
        conectar(); // Conectar a la base de datos
        String idMarca = null;

        try {
            // Obtener el próximo ID de la secuencia
            String sql = "SELECT SEQ_ID_MARCA.NEXTVAL FROM DUAL";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                idMarca = rs.getString(1); // Obtener el ID generado por la secuencia
            } else {
                System.out.println("No se pudo generar un ID para la marca.");
                return null;
            }

            // Inserción de la nueva marca
            sql = "INSERT INTO MARCA (ID_MARCA, NOMBRE_MARCA) VALUES (?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, idMarca); // Usar el ID generado
            ps.setString(2, nombreMarca.trim()); // Usar el nombre ingresado por el usuario

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Error: No se pudo insertar la marca.");
                idMarca = null;
            }
        } catch (SQLException ex) {
            System.out.println("Error al insertar marca: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            desconectar(); // Cerrar la conexión
        }

        return idMarca; // Retornar el ID de la marca generada
    }
    
    public List<String> obtenerMarcas() {
    List<String> marcas = new ArrayList<>();
    conectar();
    try {
        String sql = "SELECT NOMBRE_MARCA FROM MARCA ORDER BY NOMBRE_MARCA";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            marcas.add(rs.getString("NOMBRE_MARCA"));
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener marcas: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return marcas;
}
    public boolean eliminarMarcaPorNombre(String nombreMarca) {
    conectar();
    boolean exito = false;
    try {
        String sql = "DELETE FROM MARCA WHERE NOMBRE_MARCA = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, nombreMarca);
        exito = ps.executeUpdate() > 0;
    } catch (SQLException ex) {
        System.out.println("Error al eliminar marca: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return exito;
}


    
    
}
