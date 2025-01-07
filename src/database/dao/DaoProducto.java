package database.dao;

import database.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DaoProducto extends Conexion {

    
    
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
    public String obtenerIdProductoPorNombre(String nombreProducto) {
    conectar();
    String idProducto = null;
    try {
        String sql = "SELECT ID_PRODUCTO FROM PRODUCTO WHERE NOMBRE = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, nombreProducto);
        rs = ps.executeQuery();
        if (rs.next()) {
            idProducto = rs.getString("ID_PRODUCTO");
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener el ID del producto: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return idProducto;
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
    
public boolean deleteProducto(String idProducto) {
    conectar();
    boolean exito = false; // Indica si la operación fue exitosa
    try {
        
        // Verificar si el producto está asociado en DETALLE_VENTA
        String sqlDetalleVenta = "SELECT COUNT(*) FROM DETALLE_VENTA WHERE TRIM(ID_PRODUCTO) = ?";
        ps = conn.prepareStatement(sqlDetalleVenta);
        ps.setString(1, idProducto);
        rs = ps.executeQuery();
        if (rs.next() && rs.getInt(1) > 0) {
            System.out.println("No se puede eliminar el producto porque está asociado a una venta.");
            return false;
        }

        // Verificar si el producto está asociado en DETALLE_COMPRA
        String sqlDetalleCompra = "SELECT COUNT(*) FROM DETALLE_COMPRA WHERE TRIM(ID_PRODUCTO) = ?";
        ps = conn.prepareStatement(sqlDetalleCompra);
        ps.setString(1, idProducto);
        rs = ps.executeQuery();
        if (rs.next() && rs.getInt(1) > 0) {
            System.out.println("No se puede eliminar el producto porque está asociado a una compra.");
            return false;
        }

        // Eliminar el producto
        String sqlProducto = "DELETE FROM PRODUCTO WHERE TRIM(ID_PRODUCTO) = ?";
        ps = conn.prepareStatement(sqlProducto);
        ps.setString(1, idProducto);
        int filasAfectadas = ps.executeUpdate();

        exito = filasAfectadas > 0;

        if (exito) {
            System.out.println("Producto eliminado exitosamente.");
        } else {
            System.out.println("No se encontró el producto con el ID proporcionado.");
        }

    } catch (SQLException ex) {
        System.out.println("Error al eliminar el producto: " + ex.getMessage());
        return false;
    } finally {
        desconectar(); // Cierra la conexión a la base de datos
    }
    return exito;
}

public boolean insertarProducto(String nombre, String descripcion, String sku, String idCategoria,
                                String idEstadoProducto, String idProveedor, int stock, double precio,
                                int piso, String zona, int estanteria, String idMarca) {
    conectar(); // Abre la conexión a la base de datos
    boolean exito = false; // Indica si la operación fue exitosa

    try {
        // Consulta SQL para insertar un nuevo producto (sin especificar ID_PRODUCTO)
        String sql = "INSERT INTO PRODUCTO (NOMBRE, DESCRIPCION, SKU, ID_CATEGORIA, ID_EDO_PRODUCTO, " +
                     "ID_PROVEEDOR, STOCK, PRECIO, PISO, ZONA, ESTANTERIA, ID_MARCA) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        ps = conn.prepareStatement(sql); // Prepara la consulta SQL
        ps.setString(1, nombre); // Nombre del producto
        ps.setString(2, descripcion); // Descripción del producto
        ps.setString(3, sku); // SKU del producto
        ps.setString(4, idCategoria); // ID de la categoría
        ps.setString(5, idEstadoProducto); // ID del estado del producto
        ps.setString(6, idProveedor); // ID del proveedor
        ps.setInt(7, stock); // Cantidad en stock
        ps.setDouble(8, precio); // Precio del producto
        ps.setInt(9, piso); // Piso donde se encuentra el producto
        ps.setString(10, zona); // Zona donde se encuentra el producto
        ps.setInt(11, estanteria); // Estantería donde se encuentra el producto
        ps.setString(12, idMarca); // ID de la marca

        // Ejecuta la consulta y verifica si se insertaron filas
        exito = ps.executeUpdate() > 0;

      
    } catch (SQLException ex) {
        // Maneja cualquier error que ocurra durante la inserción
        System.out.println("Error al insertar producto: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        desconectar(); // Cierra la conexión a la base de datos
    }

    return exito; // Devuelve el estado de la operación
}




public String obtenerCodigoCategoria(String tipo) {
    conectar();
    String idCategoria = null;
    try {
        String sql = "SELECT ID_CATEGORIA FROM CATEGORIA WHERE TRIM(UPPER(TIPO)) = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, tipo.trim().toUpperCase());
        rs = ps.executeQuery();
        if (rs.next()) {
            idCategoria = rs.getString("ID_CATEGORIA");
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener código de la categoría: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return idCategoria;
}


public String obtenerCodigoEstado(String estadoProducto) {
    conectar();
    String idEstado = null;
    try {
        String sql = "SELECT ID_EDO_PRODUCTO FROM ESTADO_PRODUCTO WHERE CLASIFICACION = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, estadoProducto);
        rs = ps.executeQuery();
        if (rs.next()) {
            idEstado = rs.getString("ID_EDO_PRODUCTO");
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener código del estado del producto: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return idEstado;
}

public String obtenerCodigoProveedor(String proveedor) {
    conectar();
    String idProveedor = null;
    try {
        String sql = "SELECT ID_PROVEEDOR FROM PROVEEDOR WHERE TRIM(UPPER(NOMBRE_EMPRESA)) = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, proveedor.trim().toUpperCase());
        rs = ps.executeQuery();
        if (rs.next()) {
            idProveedor = rs.getString("ID_PROVEEDOR");
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener código del proveedor: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return idProveedor;
}

public String obtenerCodigoMarca(String marca) {
    conectar();
    String idMarca = null;
    try {
        String sql = "SELECT ID_MARCA FROM MARCA WHERE NOMBRE_MARCA = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, marca);
        rs = ps.executeQuery();
        if (rs.next()) {
            idMarca = rs.getString("ID_MARCA");
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener código de la marca: " + ex.getMessage());
    } finally {
        desconectar();
    }
    return idMarca;
}


public boolean actualizarProducto(String idProducto, String nombre, String descripcion, String sku, String idCategoria,
                                  String idEstadoProducto, String idProveedor, int stock, double precio,
                                  int piso, String zona, int estanteria, String idMarca) {
    conectar();
    boolean exito = false;

    try {
        String sql = "UPDATE PRODUCTO " +
                     "SET NOMBRE = ?, " +
                     "DESCRIPCION = ?, " +
                     "SKU = ?, " +
                     "ID_CATEGORIA = ?, " +
                     "ID_EDO_PRODUCTO = ?, " +
                     "ID_PROVEEDOR = ?, " +
                     "STOCK = ?, " +
                     "PRECIO = ?, " +
                     "PISO = ?, " +
                     "ZONA = ?, " +
                     "ESTANTERIA = ?, " +
                     "ID_MARCA = ? " +
                     "WHERE TRIM(ID_PRODUCTO) = ?";

        ps = conn.prepareStatement(sql);
        ps.setString(1, nombre);
        ps.setString(2, descripcion);
        ps.setString(3, sku);
        ps.setString(4, idCategoria);
        ps.setString(5, idEstadoProducto);
        ps.setString(6, idProveedor);
        ps.setInt(7, stock);
        ps.setDouble(8, precio);
        ps.setInt(9, piso);
        ps.setString(10, zona);
        ps.setInt(11, estanteria);
        ps.setString(12, idMarca);
        ps.setString(13, idProducto);

        int filasAfectadas = ps.executeUpdate();
        if (filasAfectadas > 0) {
            exito = true;
        }
    } catch (SQLException ex) {
        System.out.println("Error al actualizar el producto: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        desconectar();
    }
    return exito;
}





}


