package database.dao;

import database.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



/**
 * Clase DAO para manejar las operaciones relacionadas con los productos en la base de datos.
 */
public class DaoProducto extends Conexion {

    /**
     * Genera un ID único para un nuevo producto.
     * 
     * @return El ID generado.
     */
    private String generarIdProducto() {
        String idProducto = "";
        try {
            conectar();
            String sql = "SELECT MAX(ID_PRODUCTO) FROM PRODUCTOS";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                String maxId = rs.getString(1);
                if (maxId != null) {
                    int secuencia = Integer.parseInt(maxId.substring(1)) + 1;
                    idProducto = "P" + String.format("%04d", secuencia);
                } else {
                    idProducto = "P0001"; // Primer ID si no hay productos
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al generar ID del producto: " + ex.getMessage());
        } finally {
            desconectar();
        }
        return idProducto;
    }

    /**
     * Agrega un nuevo producto a la base de datos.
     * 
     * @param producto Arreglo con los datos del producto.
     * @return `true` si el producto fue agregado correctamente, `false` de lo contrario.
     */
    public boolean agregarProducto(Object[] producto) {
        conectar();
        String idProducto = generarIdProducto(); // Generar un nuevo ID para el producto
        try {
            String sql = "INSERT INTO PRODUCTOS (ID_PRODUCTO, NOMBRE, DESCRIPCION, MARCA, PROVEEDOR, ESTADO, CATEGORIA, SKU, PRECIO, STOCK, PISO, ZONA, ESTANTERIA) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, idProducto);              // ID Producto
            ps.setString(2, (String) producto[0]);    // Nombre
            ps.setString(3, (String) producto[1]);    // Descripción
            ps.setString(4, (String) producto[2]);    // Marca
            ps.setString(5, (String) producto[3]);    // Proveedor
            ps.setString(6, (String) producto[4]);    // Estado
            ps.setString(7, (String) producto[5]);    // Categoría
            ps.setString(8, (String) producto[6]);    // SKU
            ps.setDouble(9, (double) producto[7]);    // Precio
            ps.setInt(10, (int) producto[8]);         // Stock
            ps.setString(11, (String) producto[9]);   // Piso
            ps.setString(12, (String) producto[10]);  // Zona
            ps.setString(13, (String) producto[11]);  // Estantería

            int filasInsertadas = ps.executeUpdate();
            return filasInsertadas > 0;
        } catch (SQLException ex) {
            System.out.println("Error al agregar producto: " + ex.getMessage());
        } finally {
            desconectar();
        }
        return false;
    }

    /**
     * Busca productos en la base de datos por nombre o marca.
     * 
     * @param filtro Texto para buscar coincidencias.
     * @return Lista de productos que coinciden con el filtro.
     */
    public List<Object[]> buscarProductos(String filtro) {
        conectar();
        List<Object[]> productos = new ArrayList<>();
        try {
            String sql = "SELECT ID_PRODUCTO, NOMBRE, DESCRIPCION, MARCA, PROVEEDOR, PRECIO, STOCK " +
                         "FROM PRODUCTOS WHERE UPPER(NOMBRE) LIKE UPPER(?) OR UPPER(MARCA) LIKE UPPER(?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + filtro + "%");
            ps.setString(2, "%" + filtro + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                Object[] producto = new Object[7];
                producto[0] = rs.getString("ID_PRODUCTO");
                producto[1] = rs.getString("NOMBRE");
                producto[2] = rs.getString("DESCRIPCION");
                producto[3] = rs.getString("MARCA");
                producto[4] = rs.getString("PROVEEDOR");
                producto[5] = rs.getDouble("PRECIO");
                producto[6] = rs.getInt("STOCK");
                productos.add(producto);
            }
        } catch (SQLException ex) {
            System.out.println("Error al buscar productos: " + ex.getMessage());
        } finally {
            desconectar();
        }
        return productos;
    }

    /**
     * Lista todos los productos en la base de datos.
     * 
     * @return Lista con los productos.
     */
    public List<Object[]> listarProductos() {
        conectar();
        List<Object[]> productos = new ArrayList<>();
        try {
            String sql = "SELECT ID_PRODUCTO, NOMBRE, DESCRIPCION, MARCA, PROVEEDOR, PRECIO, STOCK " +
                         "FROM PRODUCTOS";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Object[] producto = new Object[7];
                producto[0] = rs.getString("ID_PRODUCTO");
                producto[1] = rs.getString("NOMBRE");
                producto[2] = rs.getString("DESCRIPCION");
                producto[3] = rs.getString("MARCA");
                producto[4] = rs.getString("PROVEEDOR");
                producto[5] = rs.getDouble("PRECIO");
                producto[6] = rs.getInt("STOCK");
                productos.add(producto);
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar productos: " + ex.getMessage());
        } finally {
            desconectar();
        }
        return productos;
    }

    /**
     * Elimina un producto de la base de datos.
     * 
     * @param idProducto ID del producto a eliminar.
     * @return `true` si el producto fue eliminado correctamente, `false` de lo contrario.
     */
    public boolean eliminarProducto(String idProducto) {
        conectar();
        try {
            String sql = "DELETE FROM PRODUCTOS WHERE ID_PRODUCTO = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, idProducto);

            int filasEliminadas = ps.executeUpdate();
            return filasEliminadas > 0;
        } catch (SQLException ex) {
            System.out.println("Error al eliminar producto: " + ex.getMessage());
        } finally {
            desconectar();
        }
        return false;
    }
}
