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
            proveedores.add(rs.getString("NOMBRE_EMPRESA").trim()); // Agrega el nombre del proveedor a la lista
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
            marcas.add(rs.getString("NOMBRE_MARCA").trim()); // Agrega el nombre de la marca a la lista
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
            edoprods.add(rs.getString("CLASIFICACION").trim()); // Agrega el nombre del estado del producto a la lista
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
            categorias.add(rs.getString("TIPO").trim()); // Agrega el nombre de la categoría a la lista
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener categoría: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return categorias;
}
    
     // Listar proveedores
    public List<Object[]> listProductos() {
    conectar();
    List<Object[]> productos = new ArrayList<>();
    try {
        String sql = "SELECT ID_PRODUCTO, NOMBRE, DESCRIPCION, SKU, TIPO, CLASIFICACION, " +
                     "NOMBRE_EMPRESA, STOCK, PRECIO, PISO, ZONA, ESTANTERIA, NOMBRE_MARCA " +
                     "FROM PRODUCTO " +
                     "JOIN CATEGORIA USING (ID_CATEGORIA) " +
                     "JOIN ESTADO_PRODUCTO USING (ID_EDO_PRODUCTO) " +
                     "JOIN PROVEEDOR USING (ID_PROVEEDOR) " +
                     "JOIN MARCA USING (ID_MARCA)" +
                     "ORDER BY NOMBRE";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            Object[] producto = new Object[13];
            producto[0] = rs.getString("ID_PRODUCTO").trim();         // ID_PRODUCTO (NCHAR(8))
            producto[1] = rs.getString("NOMBRE").trim();             // NOMBRE (NVARCHAR2(20))
            producto[2] = rs.getString("DESCRIPCION");               // DESCRIPCION (NVARCHAR2(300))
            producto[3] = rs.getString("SKU").trim();                // SKU (VARCHAR2(20))
            producto[4] = rs.getString("TIPO").trim();               // TIPO (CHAR(20))
            producto[5] = rs.getString("CLASIFICACION").trim();      // CLASIFICACION (VARCHAR2(8))
            producto[6] = rs.getString("NOMBRE_EMPRESA").trim();     // NOMBRE_EMPRESA (CHAR(50))
            producto[7] = rs.getInt("STOCK");                       // STOCK (NUMBER(4))
            producto[8] = rs.getDouble("PRECIO");                   // PRECIO (FLOAT(6))
            producto[9] = rs.getInt("PISO");                        // PISO (NUMBER(2))
            producto[10] = rs.getString("ZONA").trim();             // ZONA (CHAR(1))
            producto[11] = rs.getInt("ESTANTERIA");                 // ESTANTERIA (NUMBER(2))
            producto[12] = rs.getString("NOMBRE_MARCA").trim();      // NOMBRE_MARCA (NVARCHAR2(30))
            productos.add(producto);
        }
    } catch (SQLException ex) {
        System.out.println("Error al listar productos: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        desconectar();
    }
    return productos;
}
  
    public Object[] obtenerProductoPorId(String idProducto) {
    conectar();
    Object[] producto = new Object[16]; // Tamaño ajustado según las columnas seleccionadas en la consulta
    try {
        String sql = "SELECT ID_PRODUCTO, NOMBRE, DESCRIPCION, SKU, TIPO, CLASIFICACION, " +
                     "NOMBRE_EMPRESA, STOCK, PRECIO, PISO, ZONA, ESTANTERIA, NOMBRE_MARCA, ID_MARCA, ID_PROVEEDOR, ID_CATEGORIA " +
                     "FROM PRODUCTO " +
                     "JOIN CATEGORIA USING (ID_CATEGORIA) " +
                     "JOIN ESTADO_PRODUCTO USING (ID_EDO_PRODUCTO) " +
                     "JOIN PROVEEDOR USING (ID_PROVEEDOR) " +
                     "JOIN MARCA USING (ID_MARCA) " +
                     "WHERE ID_PRODUCTO = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, idProducto);
        rs = ps.executeQuery();

        if (rs.next()) {
            producto[0] = rs.getString("ID_PRODUCTO");          // ID_PRODUCTO (String)
            producto[1] = rs.getString("NOMBRE").trim();       // NOMBRE (String)
            producto[2] = rs.getString("DESCRIPCION");         // DESCRIPCION (String)
            producto[3] = rs.getString("SKU").trim();          // SKU (String)
            producto[4] = rs.getString("TIPO").trim();         // TIPO (String)
            producto[5] = rs.getString("CLASIFICACION").trim();// CLASIFICACION (String)
            producto[6] = rs.getString("NOMBRE_EMPRESA").trim();// NOMBRE_EMPRESA (String)
            producto[7] = rs.getInt("STOCK");                 // STOCK (int)
            producto[8] = rs.getDouble("PRECIO");             // PRECIO (double)
            producto[9] = rs.getInt("PISO");                  // PISO (int)
            producto[10] = rs.getString("ZONA").trim();       // ZONA (String)
            producto[11] = rs.getInt("ESTANTERIA");           // ESTANTERIA (int)
            producto[12] = rs.getString("NOMBRE_MARCA").trim();// NOMBRE_MARCA (String)
            producto[13] = rs.getString("ID_MARCA");      // String
            producto[14] = rs.getString("ID_PROVEEDOR");      // String
            producto[15] = rs.getString("ID_CATEGORIA");      // String
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener detalles del producto: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        desconectar();
    }
    return producto;
}

    public List<String[]> obtenerCategoriaConIds() {
    conectar();
    List<String[]> categorias = new ArrayList<>();
    try {
        String sql = "SELECT ID_CATEGORIA, TIPO FROM CATEGORIA";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            categorias.add(new String[]{rs.getString("ID_CATEGORIA"), rs.getString("TIPO")});
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener categorías: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return categorias;
}

public List<String[]> obtenerProveedorConIds() {
    conectar();
    List<String[]> proveedores = new ArrayList<>();
    try {
        String sql = "SELECT ID_PROVEEDOR, NOMBRE_EMPRESA FROM PROVEEDOR";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            proveedores.add(new String[]{rs.getString("ID_PROVEEDOR"), rs.getString("NOMBRE_EMPRESA")});
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener proveedores: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return proveedores;
}

 public List<Object[]> buscarProducto(String filtro) {
    conectar();
    List<Object[]> productos = new ArrayList<>();
    try {
        // Consulta SQL con JOIN USING sin renombrar la tabla PRODUCTO
        String sql = "SELECT ID_PRODUCTO, NOMBRE, DESCRIPCION, SKU, " +
                     "TIPO AS CATEGORIA, CLASIFICACION AS ESTADO_PRODUCTO, " +
                     "NOMBRE_EMPRESA AS PROVEEDOR, STOCK, PRECIO, PISO, ZONA, ESTANTERIA, " +
                     "NOMBRE_MARCA AS MARCA " +
                     "FROM PRODUCTO " +
                     "JOIN CATEGORIA USING (ID_CATEGORIA) " +
                     "JOIN ESTADO_PRODUCTO USING (ID_EDO_PRODUCTO) " +
                     "JOIN PROVEEDOR USING (ID_PROVEEDOR) " +
                     "JOIN MARCA USING (ID_MARCA) " +
                     "WHERE UPPER(NOMBRE) LIKE UPPER(?) OR UPPER(DESCRIPCION) LIKE UPPER(?) " +
                     "OR UPPER(NOMBRE_EMPRESA) LIKE UPPER(?) OR UPPER(NOMBRE_MARCA) LIKE UPPER(?)";

        ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + filtro + "%");
        ps.setString(2, "%" + filtro + "%");
        ps.setString(3, "%" + filtro + "%");
        ps.setString(4, "%" + filtro + "%");
        rs = ps.executeQuery();

        while (rs.next()) {
            Object[] producto = new Object[13]; // Ajusta según las columnas seleccionadas
            producto[0] = rs.getString("ID_PRODUCTO").trim();         // ID del producto
            producto[1] = rs.getString("NOMBRE").trim();             // Nombre del producto
            producto[2] = rs.getString("DESCRIPCION").trim();         // Descripción
            producto[3] = rs.getString("SKU").trim();                 // SKU
            producto[4] = rs.getString("CATEGORIA").trim();           // Categoría
            producto[5] = rs.getString("ESTADO_PRODUCTO").trim();     // Estado del producto
            producto[6] = rs.getString("PROVEEDOR").trim();           // Nombre del proveedor
            producto[7] = rs.getInt("STOCK");                        // Stock
            producto[8] = rs.getDouble("PRECIO");                    // Precio
            producto[9] = rs.getInt("PISO");                         // Piso
            producto[10] = rs.getString("ZONA").trim();              // Zona
            producto[11] = rs.getInt("ESTANTERIA");                  // Estantería
            producto[12] = rs.getString("MARCA").trim();             // Marca
            productos.add(producto);
        }
    } catch (SQLException ex) {
        System.out.println("Error al buscar productos: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return productos;
}   
    
 public boolean deleteProduct(String idProducto) {
    conectar(); // Conectar a la base de datos
    try {
        // Eliminar producto
        String sqlProducto = "DELETE FROM PRODUCTO WHERE ID_PRODUCTO = ?";
        ps = conn.prepareStatement(sqlProducto);
        ps.setString(1, idProducto);
        int filasAfectadas = ps.executeUpdate();

        return filasAfectadas > 0; // Devuelve true si se eliminó
    } catch (SQLException ex) {
        System.out.println("Error al eliminar producto: " + ex.getMessage());
        return false;
    } finally {
        desconectar(); // Cierra la conexión
    }
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
