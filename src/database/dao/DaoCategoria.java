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
public class DaoCategoria extends Conexion {
    
  
    public String insertarCategoria(String nombreCategoria) {
        conectar(); // Conectar a la base de datos
        String idCategoria = null;

        try {
            // Obtener el próximo ID de la secuencia
            String sql = "SELECT SEQ_ID_CATEGORIA.NEXTVAL FROM DUAL";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                idCategoria = rs.getString(1); // Obtener el ID generado por la secuencia
            } else {
                System.out.println("No se pudo generar un ID para la marca.");
                return null;
            }

            // Inserción de la nueva marca
            sql = "INSERT INTO CATEGORIA (ID_CATEGORIA, TIPO) VALUES (?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, idCategoria); // Usar el ID generado
            ps.setString(2, nombreCategoria.trim()); // Usar el nombre ingresado por el usuario

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Error: No se pudo insertar la categoria.");
                idCategoria = null;
            }
        } catch (SQLException ex) {
            System.out.println("Error al insertar categoria: " + ex.getMessage());
        } finally {
            desconectar(); // Cerrar la conexión
        }

        return idCategoria; // Retornar el ID de la marca generada
    }
    
    public List<String> obtenerCategoria() {
    List<String> categorias = new ArrayList<>();
    conectar();
    try {
        String sql = "SELECT TIPO FROM CATEGORIA ORDER BY TIPO";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            categorias.add(rs.getString("TIPO"));
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener categorias: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return categorias;
}
    public boolean eliminarCategoriaPorNombre(String nombreCategoria) {
    conectar();
    boolean exito = false;
    try {
        String sql = "DELETE FROM CATEGORIA WHERE TIPO = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, nombreCategoria);
        exito = ps.executeUpdate() > 0;
    } catch (SQLException ex) {
        System.out.println("Error al eliminar cattegoria: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return exito;
}


    
    
}
