package com.vews;
import database.dao.DaoProducto;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

/**
 *
 * @author Alex
 */
public class PrProducto extends javax.swing.JPanel {
    
    private final DaoProducto daoProducto = new DaoProducto();

    public static PrProducto prod;

    public PrProducto() {
        initComponents();
        cargarProveedores();
        cargarMarcas();
        cargarEdoProd();
        cargarCategoria();
        cargarProductos();
        eliminarProducto();
        
    }
    
    private void cargarProveedores() {
        // Llenar la lista de proveedores en el combo box
        try {
            List<String> proveedores = daoProducto.obtenerProveedor();
            Prov_Box.removeAllItems();
            for (String proveedor : proveedores) {
                Prov_Box.addItem(proveedor.trim());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar proveedores: " + e.getMessage());
        }
    }
    
    private void cargarMarcas() {
        // Llenar la lista de marcas en el combo box
        try {
            List<String> marcas = daoProducto.obtenerMarca();
            Marca_Box.removeAllItems();
            for (String marca : marcas) {
                Marca_Box.addItem(marca.trim());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar marcas: " + e.getMessage());
        }
    }
    
    private void cargarEdoProd() {
        // Llenar la lista de marcas en el combo box
        try {
            List<String> edoprods = daoProducto.obtenerEdoProd();
            EdoProd_Box.removeAllItems();
            for (String edoprod : edoprods) {
                EdoProd_Box.addItem(edoprod.trim());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar estado de productos: " + e.getMessage());
        }
    }
    
    private void cargarCategoria() {
        // Llenar la lista de marcas en el combo box
        try {
            List<String> categorias = daoProducto.obtenerCategoria();
            Cat_Box.removeAllItems();
            for (String categoria : categorias) {
                Cat_Box.addItem(categoria.trim());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar categorías: " + e.getMessage());
        }
    }
    
    private void cargarProductos() { 
    try {
        // Obtiene los productos desde el DAO
        List<Object[]> productos = daoProducto.listProductos();

        // Configura el modelo de la tabla con las columnas necesarias
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Descripción", "SKU", "Tipo", "Clasificación", "Proveedor", "Stock", "Precio", "Piso", "Zona", "Estantería", "Marca"}, 0
        );

        // Llena el modelo con los datos
        for (Object[] producto : productos) {
            model.addRow(new Object[]{
                producto[0]!= null ? producto[0] : "N/A",                          // ID
                producto[1]!= null ? producto[1] : "N/A",                          // Nombre
                producto[2]!= null ? producto[2] : "N/A",                          // Descripción
                producto[3]!= null ? producto[3] : "N/A",                          // SKU
                producto[4]!= null ? producto[4] : "N/A",                          // Tipo
                producto[5]!= null ? producto[5] : "N/A",                          // Clasificación
                producto[6]!= null ? producto[6] : "N/A",                          // Empresa
                producto[7]!= null ? producto[7] : "N/A",                          // Stock
                producto[8]!= null ? producto[8] : "N/A",                          // Precio
                producto[9]!= null ? producto[9] : "N/A",                          // Piso
                producto[10]!= null ? producto[10] : "N/A",                         // Zona
                producto[11]!= null ? producto[11] : "N/A",                         // Estantería
                producto[12]!= null ? producto[12] : "N/A"                          // NOMBRE_MARCA
            });
        }

        // Asigna el modelo a la tabla
        resultsTable1.setModel(model);
        
        configurarColumnasTabla(resultsTable1);
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al cargar productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
    
    private void configurarColumnasTabla(JTable table) {
    // Establecer tamaños mínimos y preferidos para las columnas
    int[] anchos = {0, 150, 200, 100, 100, 120, 150, 60, 80, 50, 50, 80, 100};
    for (int i = 0; i < anchos.length; i++) {
        TableColumn columna = table.getColumnModel().getColumn(i);
        columna.setPreferredWidth(anchos[i]);
        columna.setMinWidth(anchos[i]);
    }

    // Habilitar desplazamiento horizontal
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
}

    private void eliminarProducto() {
    try {
        // Verifica si hay un producto seleccionado
        int filaSeleccionada = resultsTable1.getSelectedRow();
        if (filaSeleccionada == -1) {
            //JOptionPane.showMessageDialog(this, "Selecciona un producto en la tabla para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtén el ID del producto seleccionado
        String idProducto = resultsTable1.getValueAt(filaSeleccionada, 0).toString();

        // Confirmación antes de eliminar
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar el producto?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return; // El usuario canceló
        }

        // Llama al método para eliminar producto en el DAO
        boolean exito = daoProducto.deleteProduct(idProducto);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
            cargarProductos(); // Recargar tabla de productos
            //limpiarCampos(); // Limpia los cuadros de texto
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al eliminar producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
    
    private void manejarTablaProducto() {
        int filaSeleccionada = resultsTable1.getSelectedRow();
    if (filaSeleccionada != -1) {
        try {
            String categoria = resultsTable1.getValueAt(filaSeleccionada, 4).toString().trim(); // Categoría (TIPO)
            String proveedor = resultsTable1.getValueAt(filaSeleccionada, 6).toString().trim(); // Proveedor (NOMBRE_EMPRESA)
            String estado = resultsTable1.getValueAt(filaSeleccionada, 5).toString().trim(); // Estado del producto (CLASIFICACION)
            String marca = resultsTable1.getValueAt(filaSeleccionada, 12).toString().trim(); // Marca (NOMBRE_MARCA)

            // Asignar valores a los campos
            NomTxtF.setText(resultsTable1.getValueAt(filaSeleccionada, 1).toString().trim()); // Nombre
            DescTxtF.setText(resultsTable1.getValueAt(filaSeleccionada, 2).toString().trim()); // Descripción
            SKUTxtF.setText(resultsTable1.getValueAt(filaSeleccionada, 3).toString().trim()); // SKU
            StockTxtF.setText(resultsTable1.getValueAt(filaSeleccionada, 7).toString()); // Stock
            PrecioTxtF.setText(resultsTable1.getValueAt(filaSeleccionada, 8).toString()); // Precio
            PisoTxtF.setText(resultsTable1.getValueAt(filaSeleccionada, 9).toString()); // Piso
            ZonaTxtF.setText(resultsTable1.getValueAt(filaSeleccionada, 10).toString().trim()); // Zona
            EstantTxtF.setText(resultsTable1.getValueAt(filaSeleccionada, 11).toString()); // Estantería

            // Seleccionar categoría
            for (int i = 0; i < Cat_Box.getItemCount(); i++) {
                if (Cat_Box.getItemAt(i).equalsIgnoreCase(categoria)) {
                    Cat_Box.setSelectedIndex(i);
                    break;
                }
            }

            // Seleccionar proveedor
            for (int i = 0; i < Prov_Box.getItemCount(); i++) {
                if (Prov_Box.getItemAt(i).equalsIgnoreCase(proveedor)) {
                    Prov_Box.setSelectedIndex(i);
                    break;
                }
            }

            // Seleccionar estado del producto
            for (int i = 0; i < EdoProd_Box.getItemCount(); i++) {
                if (EdoProd_Box.getItemAt(i).equalsIgnoreCase(estado)) {
                    EdoProd_Box.setSelectedIndex(i);
                    break;
                }
            }

            // Seleccionar marca
            for (int i = 0; i < Marca_Box.getItemCount(); i++) {
                if (Marca_Box.getItemAt(i).equalsIgnoreCase(marca)) {
                    Marca_Box.setSelectedIndex(i);
                    break;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos seleccionados: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Por favor selecciona una fila.", "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
    }
     
    private void buscarProducto()  {
         String filtro = searchbar1.getText().trim();

if (filtro.isEmpty()) {
    JOptionPane.showMessageDialog(this, "Por favor, ingresa un criterio de búsqueda.", "Advertencia", JOptionPane.WARNING_MESSAGE);
    return;
}

try {
    // Realiza la búsqueda
    List<Object[]> resultados = daoProducto.buscarProducto(filtro);

    // Configura el modelo de la tabla
    DefaultTableModel model = new DefaultTableModel(
        new String[]{
            "ID", "Nombre", "Descripción", "SKU", "Categoría", "Estado", 
            "Proveedor", "Stock", "Precio", "Piso", "Zona", "Estantería", "Marca"
        }, 0
    );

    // Llena el modelo con los resultados
    for (Object[] fila : resultados) {
        model.addRow(new Object[]{
            fila[0], // ID del producto
            fila[1], // Nombre
            fila[2], // Descripción
            fila[3], // SKU
            fila[4], // Categoría
            fila[5], // Estado del producto
            fila[6], // Proveedor
            fila[7], // Stock
            fila[8], // Precio
            fila[9], // Piso
            fila[10], // Zona
            fila[11], // Estantería
            fila[12]  // Marca
        });
    }

    resultsTable1.setModel(model);
    configurarColumnasTabla(resultsTable1);
    

    // Muestra un mensaje si no hay resultados
    if (resultados.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No se encontraron productos con el criterio ingresado.", "Información", JOptionPane.INFORMATION_MESSAGE);
    }
} catch (Exception ex) {
    JOptionPane.showMessageDialog(this, "Error al realizar la búsqueda: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    ex.printStackTrace();
}
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        fondo = new javax.swing.JPanel();
        searchbar1 = new javax.swing.JTextField();
        FILTRO = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        resultsTable1 = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        productos = new javax.swing.JPanel();
        Nombre = new javax.swing.JLabel();
        NomTxtF = new javax.swing.JTextField();
        PROVEEDOR = new javax.swing.JLabel();
        Prov_Box = new javax.swing.JComboBox<>();
        DESCRIPCION = new javax.swing.JLabel();
        DescTxtF = new javax.swing.JTextField();
        MARCA = new javax.swing.JLabel();
        Marca_Box = new javax.swing.JComboBox<>();
        EdoProducto = new javax.swing.JLabel();
        EdoProd_Box = new javax.swing.JComboBox<>();
        Categoria = new javax.swing.JLabel();
        Cat_Box = new javax.swing.JComboBox<>();
        SKU = new javax.swing.JLabel();
        SKUTxtF = new javax.swing.JTextField();
        Stock = new javax.swing.JLabel();
        StockTxtF = new javax.swing.JTextField();
        Precio = new javax.swing.JLabel();
        PrecioTxtF = new javax.swing.JTextField();
        Piso = new javax.swing.JLabel();
        PisoTxtF = new javax.swing.JTextField();
        Zona = new javax.swing.JLabel();
        ZonaTxtF = new javax.swing.JTextField();
        Estantería = new javax.swing.JLabel();
        EstantTxtF = new javax.swing.JTextField();
        GuardarBot = new javax.swing.JLabel();
        ELIMINAR_BOTON = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        AGREGAR_BOTON = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(940, 570));

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setPreferredSize(new java.awt.Dimension(1040, 560));

        searchbar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchbar1ActionPerformed(evt);
            }
        });
        searchbar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchbar1KeyPressed(evt);
            }
        });

        FILTRO.setText("FILTRAR:");

        jScrollPane2.setAutoscrolls(true);

        resultsTable1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        resultsTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "NOMBRE", "MARCA ", "CATEGORÍA", "PRECIO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        resultsTable1.setCellSelectionEnabled(true);
        resultsTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resultsTable1MouseClicked(evt);
            }
        });
        resultsTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                resultsTable1KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(resultsTable1);
        resultsTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jLabel9.setFont(new java.awt.Font("Jost", 0, 48)); // NOI18N
        jLabel9.setText("PRODUCTO");

        productos.setBackground(new java.awt.Color(255, 255, 255));
        productos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        productos.setForeground(new java.awt.Color(204, 204, 204));

        Nombre.setText("NOMBRE");

        NomTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NomTxtFActionPerformed(evt);
            }
        });

        PROVEEDOR.setText("PROVEEDOR");

        Prov_Box.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Prov_Box.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Prov_Box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Prov_BoxActionPerformed(evt);
            }
        });

        DESCRIPCION.setText("DESCRIPCIÓN");

        DescTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DescTxtFActionPerformed(evt);
            }
        });

        MARCA.setText("MARCA");

        Marca_Box.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Marca_Box.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Marca_Box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Marca_BoxActionPerformed(evt);
            }
        });

        EdoProducto.setText("ESTADO DEL PRODUCTO");

        EdoProd_Box.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        EdoProd_Box.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EdoProd_Box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EdoProd_BoxActionPerformed(evt);
            }
        });

        Categoria.setText("CATEGORIA");

        Cat_Box.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Cat_Box.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Cat_Box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Cat_BoxActionPerformed(evt);
            }
        });

        SKU.setText("SKU");

        SKUTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SKUTxtFActionPerformed(evt);
            }
        });

        Stock.setText("STOCK");

        StockTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StockTxtFActionPerformed(evt);
            }
        });

        Precio.setText("PRECIO");

        PrecioTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrecioTxtFActionPerformed(evt);
            }
        });

        Piso.setText("PISO");

        PisoTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PisoTxtFActionPerformed(evt);
            }
        });

        Zona.setText("ZONA");

        ZonaTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ZonaTxtFActionPerformed(evt);
            }
        });

        Estantería.setText("ESTANTERIA");

        EstantTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EstantTxtFActionPerformed(evt);
            }
        });

        GuardarBot.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/guardar.png"))); // NOI18N
        GuardarBot.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        ELIMINAR_BOTON.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eliminar.png"))); // NOI18N
        ELIMINAR_BOTON.setContentAreaFilled(false);
        ELIMINAR_BOTON.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ELIMINAR_BOTON.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ELIMINAR_BOTONActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout productosLayout = new javax.swing.GroupLayout(productos);
        productos.setLayout(productosLayout);
        productosLayout.setHorizontalGroup(
            productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productosLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(Nombre)
                .addGap(11, 11, 11)
                .addComponent(NomTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(PROVEEDOR)
                .addGap(14, 14, 14)
                .addComponent(Prov_Box, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(productosLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(DESCRIPCION)
                .addGap(6, 6, 6)
                .addComponent(DescTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(productosLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(MARCA)
                .addGap(8, 8, 8)
                .addComponent(Marca_Box, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(EdoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(EdoProd_Box, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(productosLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(Categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Cat_Box, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(SKU)
                .addGap(19, 19, 19)
                .addComponent(SKUTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(productosLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(Stock)
                .addGap(14, 14, 14)
                .addComponent(StockTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(110, 110, 110)
                .addComponent(Precio)
                .addGap(10, 10, 10)
                .addComponent(PrecioTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(productosLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(Piso)
                .addGap(15, 15, 15)
                .addComponent(PisoTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(Zona)
                .addGap(7, 7, 7)
                .addComponent(ZonaTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(Estantería)
                .addGap(5, 5, 5)
                .addComponent(EstantTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(productosLayout.createSequentialGroup()
                .addGap(369, 369, 369)
                .addGroup(productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GuardarBot, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(productosLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(ELIMINAR_BOTON))))
        );
        productosLayout.setVerticalGroup(
            productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productosLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NomTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PROVEEDOR, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Prov_Box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DESCRIPCION, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DescTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MARCA, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Marca_Box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EdoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EdoProd_Box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Cat_Box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SKU, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SKUTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Stock, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(StockTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Precio, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PrecioTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Piso, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PisoTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Zona, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ZonaTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Estantería, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EstantTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addGroup(productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GuardarBot, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ELIMINAR_BOTON)))
        );

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/añadir producto.png"))); // NOI18N
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        AGREGAR_BOTON.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/producto.png"))); // NOI18N
        AGREGAR_BOTON.setContentAreaFilled(false);
        AGREGAR_BOTON.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AGREGAR_BOTONActionPerformed(evt);
            }
        });

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout fondoLayout = new javax.swing.GroupLayout(fondo);
        fondo.setLayout(fondoLayout);
        fondoLayout.setHorizontalGroup(
            fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoLayout.createSequentialGroup()
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(fondoLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(productos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(fondoLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(FILTRO)
                                .addGap(4, 4, 4)
                                .addComponent(searchbar1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel11))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(fondoLayout.createSequentialGroup()
                        .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(fondoLayout.createSequentialGroup()
                                .addGap(320, 320, 320)
                                .addComponent(jLabel9))
                            .addGroup(fondoLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(AGREGAR_BOTON)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        fondoLayout.setVerticalGroup(
            fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoLayout.createSequentialGroup()
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(fondoLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(fondoLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(productos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(fondoLayout.createSequentialGroup()
                                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(fondoLayout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(FILTRO, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(searchbar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(fondoLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel11)))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(fondoLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(AGREGAR_BOTON)))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleParent(this);
    }// </editor-fold>//GEN-END:initComponents

    private void searchbar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchbar1ActionPerformed

    }//GEN-LAST:event_searchbar1ActionPerformed

    private void resultsTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resultsTable1MouseClicked
        manejarTablaProducto();
    }//GEN-LAST:event_resultsTable1MouseClicked

    private void AGREGAR_BOTONActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AGREGAR_BOTONActionPerformed
 
    }//GEN-LAST:event_AGREGAR_BOTONActionPerformed

    private void Prov_BoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Prov_BoxActionPerformed
   
    }//GEN-LAST:event_Prov_BoxActionPerformed

    private void Cat_BoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cat_BoxActionPerformed
 
    }//GEN-LAST:event_Cat_BoxActionPerformed

    private void NomTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NomTxtFActionPerformed
    
    }//GEN-LAST:event_NomTxtFActionPerformed

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
       buscarProducto();
    }//GEN-LAST:event_jLabel11MouseClicked

    private void DescTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DescTxtFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DescTxtFActionPerformed

    private void Marca_BoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Marca_BoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Marca_BoxActionPerformed

    private void EdoProd_BoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EdoProd_BoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EdoProd_BoxActionPerformed

    private void SKUTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SKUTxtFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SKUTxtFActionPerformed

    private void StockTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StockTxtFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_StockTxtFActionPerformed

    private void PrecioTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrecioTxtFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PrecioTxtFActionPerformed

    private void PisoTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PisoTxtFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PisoTxtFActionPerformed

    private void ZonaTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ZonaTxtFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ZonaTxtFActionPerformed

    private void EstantTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EstantTxtFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EstantTxtFActionPerformed

    private void ELIMINAR_BOTONActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ELIMINAR_BOTONActionPerformed
       eliminarProducto(); // TODO add your handling code here:
    }//GEN-LAST:event_ELIMINAR_BOTONActionPerformed

    private void resultsTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_resultsTable1KeyPressed
     if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
        manejarTablaProducto();
        evt.consume();
    }
    }//GEN-LAST:event_resultsTable1KeyPressed

    private void searchbar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchbar1KeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
        buscarProducto();
        evt.consume();
    }
    }//GEN-LAST:event_searchbar1KeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AGREGAR_BOTON;
    private javax.swing.JComboBox<String> Cat_Box;
    private javax.swing.JLabel Categoria;
    private javax.swing.JLabel DESCRIPCION;
    private javax.swing.JTextField DescTxtF;
    private javax.swing.JButton ELIMINAR_BOTON;
    private javax.swing.JComboBox<String> EdoProd_Box;
    private javax.swing.JLabel EdoProducto;
    private javax.swing.JTextField EstantTxtF;
    private javax.swing.JLabel Estantería;
    private javax.swing.JLabel FILTRO;
    private javax.swing.JLabel GuardarBot;
    private javax.swing.JLabel MARCA;
    private javax.swing.JComboBox<String> Marca_Box;
    private javax.swing.JTextField NomTxtF;
    private javax.swing.JLabel Nombre;
    private javax.swing.JLabel PROVEEDOR;
    private javax.swing.JLabel Piso;
    private javax.swing.JTextField PisoTxtF;
    private javax.swing.JLabel Precio;
    private javax.swing.JTextField PrecioTxtF;
    private javax.swing.JComboBox<String> Prov_Box;
    private javax.swing.JLabel SKU;
    private javax.swing.JTextField SKUTxtF;
    private javax.swing.JLabel Stock;
    private javax.swing.JTextField StockTxtF;
    private javax.swing.JLabel Zona;
    private javax.swing.JTextField ZonaTxtF;
    private javax.swing.JPanel bg;
    private javax.swing.JPanel fondo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel productos;
    private javax.swing.JTable resultsTable1;
    private javax.swing.JTextField searchbar1;
    // End of variables declaration//GEN-END:variables

    public void setLocation(double d) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
