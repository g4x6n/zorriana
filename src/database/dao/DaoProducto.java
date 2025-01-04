package database.dao;

import database.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DaoProducto extends Conexion {

    
    private String obtenerCodigoProveedor(String nombreProveedor) {
        String codigoProveedor = "";
        try {
            String sql = "SELECT ID_PROVEEDOR FROM PROVEEDOR WHERE UPPER(NOMBRE_EMPRESA) = UPPER(?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, nombreProveedor);
            rs = ps.executeQuery();

            if (rs.next()) {
                codigoProveedor = rs.getString(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener código del proveedor: " + ex.getMessage());
        }
        return codigoProveedor;
    }
    
     private String obtenerCodigoMarca(String nombreMarca) {
        String codigoMarca = "";
        try {
            String sql = "SELECT ID_MARCA FROM MARCA WHERE UPPER(NOMBRE_MARCA) = UPPER(?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, nombreMarca);
            rs = ps.executeQuery();

            if (rs.next()) {
                codigoMarca = rs.getString(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener código del proveedor: " + ex.getMessage());
        }
        return codigoMarca;
    }
    
    public List<String> obtenerProveedor() {
    conectar();
    List<String> proveedores = new ArrayList<>();
    try {
        String sql = "SELECT NOMBRE_EMPRESA FROM PROVEEDOR";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            proveedores.add(rs.getString("NOMBRE_EMPRESA")); // Agrega el nombre del proveedor a la lista
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener proveedores: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return proveedores;
}
    
    public List<String> obtenerMarca() {
    conectar();
    List<String> marcas = new ArrayList<>();
    try {
        String sql = "SELECT NOMBRE_MARCA FROM MARCA";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            marcas.add(rs.getString("NOMBRE_MARCA")); // Agrega el nombre de la marca a la lista
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener marcas: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return marcas;
}
    
    public List<String> obtenerEdoProd() {
    conectar();
    List<String> edoprods = new ArrayList<>();
    try {
        String sql = "SELECT CLASIFICACION FROM ESTADO_PRODUCTO";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            edoprods.add(rs.getString("CLASIFICACION")); // Agrega el nombre del estado del producto a la lista
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener estado del producto: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return edoprods;
}
    
    public List<String> obtenerCategoria() {
    conectar();
    List<String> categorias = new ArrayList<>();
    try {
        String sql = "SELECT TIPO FROM CATEGORIA";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            categorias.add(rs.getString("TIPO")); // Agrega el nombre de la categoría a la lista
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener categoría: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return categorias;
}
    
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
