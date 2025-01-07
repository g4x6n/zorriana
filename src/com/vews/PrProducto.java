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
        añadirProducto();
        
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
    
    void cargarMarcas() {
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
    
    void cargarCategoria() {
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
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Ninguna celda será editable
                return false;
            }
        };

        // Llena el modelo con los datos
        for (Object[] producto : productos) {
            model.addRow(new Object[]{
                producto[0] != null ? producto[0] : "N/A",                          // ID
                producto[1] != null ? producto[1] : "N/A",                          // Nombre
                producto[2] != null ? producto[2] : "N/A",                          // Descripción
                producto[3] != null ? producto[3] : "N/A",                          // SKU
                producto[4] != null ? producto[4] : "N/A",                          // Tipo
                producto[5] != null ? producto[5] : "N/A",                          // Clasificación
                producto[6] != null ? producto[6] : "N/A",                          // Empresa
                producto[7] != null ? producto[7] : "N/A",                          // Stock
                producto[8] != null ? producto[8] : "N/A",                          // Precio
                producto[9] != null ? producto[9] : "N/A",                          // Piso
                producto[10] != null ? producto[10] : "N/A",                        // Zona
                producto[11] != null ? producto[11] : "N/A",                        // Estantería
                producto[12] != null ? producto[12] : "N/A"                         // NOMBRE_MARCA
            });
        }

        // Asigna el modelo a la tabla
        resultsTable1.setModel(model);

        // Ocultar la columna de ID
        ocultarColumna(resultsTable1, 0);

        // Configurar columnas con tamaños predefinidos
        configurarColumnasTabla(resultsTable1);
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al cargar productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

private void ocultarColumna(JTable table, int columna) {
    TableColumn column = table.getColumnModel().getColumn(columna);
    column.setMinWidth(0);
    column.setMaxWidth(0);
    column.setPreferredWidth(0);
    column.setResizable(false);
}
private void buscarProducto() {
    String filtro = searchbar1.getText().trim();

    if (filtro.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, ingresa un criterio de búsqueda.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        List<Object[]> resultados = daoProducto.buscarProducto(filtro); // Llamada al DAO

        DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Descripción", "SKU", "Categoría", "Estado", 
                         "Proveedor", "Stock", "Precio", "Piso", "Zona", "Estantería", "Marca"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Ninguna celda editable
            }
        };

        for (Object[] fila : resultados) {
            model.addRow(fila); // Agregar datos al modelo
        }

        resultsTable1.setModel(model); // Asignar modelo a la tabla
        ocultarColumna(resultsTable1, 0); // Ocultar la columna "ID"
        configurarColumnasTabla(resultsTable1); // Configurar columnas restantes

        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron productos con el criterio ingresado.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al realizar la búsqueda: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
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
        String idProducto = resultsTable1.getValueAt(filaSeleccionada, 0).toString().trim();
        

        if (idProducto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El ID del producto seleccionado está vacío. Verifica los datos de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirmación antes de eliminar
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar el producto?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return; // El usuario canceló
        }

        // Llama al método para eliminar producto en el DAO
        boolean exito = daoProducto.deleteProducto(idProducto);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
            cargarProductos(); // Recargar tabla de productos
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar el producto. Puede estar asociado a ventas o compras.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al eliminar producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

   private void añadirProducto() {
   try {
        // Validar entradas del formulario
        String nombreProducto = NomTxtF.getText().trim();
        if (nombreProducto.isEmpty()) {
            //JOptionPane.showMessageDialog(this, "Por favor ingrese el nombre del producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String descripcion = DescTxtF.getText().trim();
        String sku = SKUTxtF.getText().trim();
        if (sku.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese el SKU.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String categoria = (String) Cat_Box.getSelectedItem();
        String idCategoria = daoProducto.obtenerCodigoCategoria(categoria);
        if (idCategoria == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una categoría válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String estado = (String) EdoProd_Box.getSelectedItem();
        String idEstado = daoProducto.obtenerCodigoEstado(estado);
        if (idEstado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un estado válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String proveedor = (String) Prov_Box.getSelectedItem();
        String idProveedor = daoProducto.obtenerCodigoProveedor(proveedor);
        if (idProveedor == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String marca = (String) Marca_Box.getSelectedItem();
        String idMarca = daoProducto.obtenerCodigoMarca(marca);
        if (idMarca == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una marca válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int stock = Integer.parseInt(StockTxtF.getText().trim());
        double precio = Double.parseDouble(PrecioTxtF.getText().trim());
        int piso = Integer.parseInt(PisoTxtF.getText().trim());
        String zona = ZonaTxtF.getText().trim();
        int estanteria = Integer.parseInt(EstantTxtF.getText().trim());

        
        // Llamar al DAO para insertar el producto
        boolean exito = daoProducto.insertarProducto(
           nombreProducto, descripcion, sku, idCategoria,
            idEstado, idProveedor, stock, precio, piso, zona, estanteria, idMarca
        );

        if (exito) {
            JOptionPane.showMessageDialog(this, "Producto agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarProductos(); // Actualizar la tabla de productos
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar el producto. Verifique los datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos para stock, precio, piso o estantería.", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al agregar el producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}


    private void manejarTablaProducto() {
    int filaSeleccionada = resultsTable1.getSelectedRow();
    if (filaSeleccionada != -1) {
        try {
            // Asignar valores a los campos
            NomTxtF.setText(resultsTable1.getValueAt(filaSeleccionada, 1).toString().trim());
            DescTxtF.setText(resultsTable1.getValueAt(filaSeleccionada, 2) != null ? resultsTable1.getValueAt(filaSeleccionada, 2).toString().trim() : "");
            SKUTxtF.setText(resultsTable1.getValueAt(filaSeleccionada, 3).toString().trim());
            StockTxtF.setText(resultsTable1.getValueAt(filaSeleccionada, 7).toString());
            PrecioTxtF.setText(resultsTable1.getValueAt(filaSeleccionada, 8).toString());
            PisoTxtF.setText(resultsTable1.getValueAt(filaSeleccionada, 9).toString());
            ZonaTxtF.setText(resultsTable1.getValueAt(filaSeleccionada, 10).toString().trim());
            EstantTxtF.setText(resultsTable1.getValueAt(filaSeleccionada, 11).toString());

            // Seleccionar categoría
            seleccionarEnComboBox(Cat_Box, resultsTable1.getValueAt(filaSeleccionada, 4).toString().trim());

            // Seleccionar proveedor
            seleccionarEnComboBox(Prov_Box, resultsTable1.getValueAt(filaSeleccionada, 6).toString().trim());

            // Seleccionar estado del producto
            seleccionarEnComboBox(EdoProd_Box, resultsTable1.getValueAt(filaSeleccionada, 5).toString().trim());

            // Seleccionar marca
            seleccionarEnComboBox(Marca_Box, resultsTable1.getValueAt(filaSeleccionada, 12).toString().trim());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos seleccionados: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Por favor selecciona una fila.", "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}

private void seleccionarEnComboBox(javax.swing.JComboBox<String> comboBox, String valor) {
    for (int i = 0; i < comboBox.getItemCount(); i++) {
        if (comboBox.getItemAt(i).equalsIgnoreCase(valor)) {
            comboBox.setSelectedIndex(i);
            break;
        }
    }
}

    private void limpiarCampos() {
    NomTxtF.setText("");
    DescTxtF.setText("");
    SKUTxtF.setText("");
    StockTxtF.setText("");
    PrecioTxtF.setText("");
    PisoTxtF.setText("");
    ZonaTxtF.setText("");
    EstantTxtF.setText("");
    Cat_Box.setSelectedIndex(-1); // Deseleccionar
    Prov_Box.setSelectedIndex(-1);
    EdoProd_Box.setSelectedIndex(-1);
    Marca_Box.setSelectedIndex(-1);
}

    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        prEmpleado1 = new com.vews.PrEmpleado();
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
        ELIMINAR_BOTON = new javax.swing.JButton();
        AñadirBoton = new javax.swing.JLabel();
        LimpiarButton = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
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
        MARCA.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        MARCA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MARCAMouseClicked(evt);
            }
        });

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
        Categoria.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Categoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CategoriaMouseClicked(evt);
            }
        });

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

        ELIMINAR_BOTON.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eliminar.png"))); // NOI18N
        ELIMINAR_BOTON.setContentAreaFilled(false);
        ELIMINAR_BOTON.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ELIMINAR_BOTON.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ELIMINAR_BOTONActionPerformed(evt);
            }
        });

        AñadirBoton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/añadir producto.png"))); // NOI18N
        AñadirBoton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AñadirBoton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AñadirBotonMouseClicked(evt);
            }
        });

        LimpiarButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/limpiar.png"))); // NOI18N
        LimpiarButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LimpiarButtonMouseClicked(evt);
            }
        });

        jButton1.setText("MARCA");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jButton2.setText("CATEGORIA");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel4.setText("¿NUEVA MARCA?");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel5.setText("¿NUEVA CATEGORÍA?");

        javax.swing.GroupLayout productosLayout = new javax.swing.GroupLayout(productos);
        productos.setLayout(productosLayout);
        productosLayout.setHorizontalGroup(
            productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productosLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(productosLayout.createSequentialGroup()
                        .addComponent(Nombre)
                        .addGap(11, 11, 11)
                        .addComponent(NomTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(PROVEEDOR)
                        .addGap(14, 14, 14)
                        .addComponent(Prov_Box, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(productosLayout.createSequentialGroup()
                        .addComponent(DESCRIPCION)
                        .addGap(6, 6, 6)
                        .addComponent(DescTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(productosLayout.createSequentialGroup()
                        .addComponent(MARCA)
                        .addGap(8, 8, 8)
                        .addComponent(Marca_Box, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(EdoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(EdoProd_Box, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(productosLayout.createSequentialGroup()
                        .addComponent(Categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Cat_Box, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(SKU)
                        .addGap(19, 19, 19)
                        .addComponent(SKUTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(productosLayout.createSequentialGroup()
                        .addComponent(Stock)
                        .addGap(14, 14, 14)
                        .addComponent(StockTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(110, 110, 110)
                        .addComponent(Precio)
                        .addGap(10, 10, 10)
                        .addComponent(PrecioTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(productosLayout.createSequentialGroup()
                        .addComponent(Piso)
                        .addGap(15, 15, 15)
                        .addComponent(PisoTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(Zona)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ZonaTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(Estantería)
                        .addGap(5, 5, 5)
                        .addComponent(EstantTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productosLayout.createSequentialGroup()
                        .addGroup(productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2)
                            .addGroup(productosLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jButton1)))
                        .addGap(35, 35, 35)
                        .addComponent(AñadirBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ELIMINAR_BOTON)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(LimpiarButton)
                        .addGap(19, 19, 19)))
                .addGap(18, 18, 18))
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
                    .addGroup(productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Cat_Box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(productosLayout.createSequentialGroup()
                        .addGroup(productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jLabel5)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productosLayout.createSequentialGroup()
                        .addGroup(productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(ELIMINAR_BOTON, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(LimpiarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(AñadirBoton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)))
                .addGap(18, 18, 18))
        );

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
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(fondoLayout.createSequentialGroup()
                        .addGap(320, 320, 320)
                        .addComponent(jLabel9)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        fondoLayout.setVerticalGroup(
            fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fondoLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(productos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(48, Short.MAX_VALUE))
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

    private void MARCAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MARCAMouseClicked
         
    }//GEN-LAST:event_MARCAMouseClicked

    private void CategoriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CategoriaMouseClicked

    }//GEN-LAST:event_CategoriaMouseClicked

    private void AñadirBotonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AñadirBotonMouseClicked
        añadirProducto();
    }//GEN-LAST:event_AñadirBotonMouseClicked

    private void LimpiarButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LimpiarButtonMouseClicked
        limpiarCampos();
    }//GEN-LAST:event_LimpiarButtonMouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
       TablaCategoria ventanaCategoria = new TablaCategoria(this);
        ventanaCategoria.setVisible(true);
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        TablaMarcas ventanaMarcas = new TablaMarcas(this);
        ventanaMarcas.setVisible(true);
    }//GEN-LAST:event_jButton1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AñadirBoton;
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
    private javax.swing.JLabel LimpiarButton;
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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private com.vews.PrEmpleado prEmpleado1;
    private javax.swing.JPanel productos;
    private javax.swing.JTable resultsTable1;
    private javax.swing.JTextField searchbar1;
    // End of variables declaration//GEN-END:variables

    public void setLocation(double d) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
