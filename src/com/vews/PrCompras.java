
package com.vews;
import com.dashboard.dashboard;
import database.dao.DaoCompras;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class PrCompras extends javax.swing.JPanel {

     private final DaoCompras daoCompras = new DaoCompras();
     private dashboard mainDashboard;
    public static PrCompras comp;
    private String usuarioActivo;

   
    public PrCompras(String usuarioActivo) {
        this.usuarioActivo = usuarioActivo;
        initComponents();
        cargarProveedores();
        cargarEmpleados();
        cargarEdoCompra();
        cargarCompras();
    }

  public PrCompras(dashboard mainDashboard) {
        this.mainDashboard = mainDashboard; // Establece la referencia al dashboard
    }
    public void setMainDashboard(dashboard main) {
    this.mainDashboard = main;
}
    private void cargarProveedores() {
        // Llenar la lista de proveedores en el combo box
        try {
            List<String> proveedores = daoCompras.obtenerProveedor();
            Prov_Box.removeAllItems();
            for (String proveedor : proveedores) {
                Prov_Box.addItem(proveedor.trim());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar proveedores: " + e.getMessage());
        }
    }
    private void cargarEmpleados() {
    // Llenar la lista de empleados en el combo box
    try {
        List<String> empleados = daoCompras.obtenerEmpleado(); // Obtener la lista de empleados
        Empleado_Box.removeAllItems(); // Limpiar los ítems del combo box antes de llenarlo
        for (String empleado : empleados) {
            // Asegurarse de que los valores estén bien formateados antes de agregarlos
            Empleado_Box.addItem(empleado.trim());
        }
    } catch (Exception e) {
        // Mostrar un mensaje de error en caso de fallo
        JOptionPane.showMessageDialog(this, "Error al cargar empleados: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    private void ocultarColumnaID(javax.swing.JTable tabla, int indiceColumna) {
    tabla.getColumnModel().getColumn(indiceColumna).setMinWidth(0);
    tabla.getColumnModel().getColumn(indiceColumna).setMaxWidth(0);
    tabla.getColumnModel().getColumn(indiceColumna).setWidth(0);
}
    private void cargarEdoCompra() {
        // Llenar la lista de proveedores en el combo box
        try {
            List<String> edocompra = daoCompras.obtenerEdoCompra();
            EdoCompra_Box.removeAllItems();
            for (String edocompras : edocompra) {
                EdoCompra_Box.addItem(edocompras.trim());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar estados de compras: " + e.getMessage());
        }
    }
    private void cargarCompras() { 
    try {
        // Obtiene las compras desde el DAO
        List<Object[]> compras = daoCompras.listCompras();

        // Configura el modelo de la tabla con las columnas necesarias
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Fecha de Compra", "Estado", "Proveedor", "Empleado"}, 0
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Ninguna celda será editable
                return false;
            }
        };

        // Llena el modelo con los datos
        for (Object[] compra : compras) {
            model.addRow(new Object[]{
                compra[0] != null ? compra[0] : "N/A", // ID de la compra
                compra[1] != null ? compra[1] : "N/A", // Fecha de compra
                compra[2] != null ? compra[2] : "N/A", // Estado de la compra
                compra[3] != null ? compra[3] : "N/A", // Proveedor
                compra[4] != null ? compra[4] : "N/A"  // Empleado
            });
        }

        // Asigna el modelo a la tabla
        TablaCompra.setModel(model);

        // Ocultar la columna de ID (asumiendo que está en el índice 0)
        ocultarColumnaID(TablaCompra, 0);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al cargar compras: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
    private void configurarColumnasTabla(JTable table) {
    // Establecer tamaños mínimos y preferidos para las columnas
    int[] anchos = {10, 150, 120, 200, 200}; // Anchos para ID, Fecha, Estado, Proveedor, Empleado
    for (int i = 0; i < anchos.length; i++) {
        TableColumn columna = table.getColumnModel().getColumn(i);
        columna.setPreferredWidth(anchos[i]);
        columna.setMinWidth(anchos[i]);
    }

    // Habilitar desplazamiento horizontal
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
}
    private void manejarTablaCompra() {
    int filaSeleccionada = TablaCompra.getSelectedRow();
    if (filaSeleccionada != -1) {
        try {
            // Obtener los valores de la fila seleccionada
            String idCompra = TablaCompra.getValueAt(filaSeleccionada, 0).toString().trim();
            String fechaCompra = TablaCompra.getValueAt(filaSeleccionada, 1).toString().trim();
            String estadoCompra = TablaCompra.getValueAt(filaSeleccionada, 2).toString().trim();
            String proveedor = TablaCompra.getValueAt(filaSeleccionada, 3).toString().trim();
            String empleado = TablaCompra.getValueAt(filaSeleccionada, 4).toString().replaceAll("\\s+", " ").trim();

            cargarDetalleCompra(idCompra);
            // Asignar los valores a los campos
            FechaVTxtf.setText(fechaCompra);

            for (int i = 0; i < EdoCompra_Box.getItemCount(); i++) {
                if (EdoCompra_Box.getItemAt(i).trim().equalsIgnoreCase(estadoCompra)) {
                    EdoCompra_Box.setSelectedIndex(i);
                    break;
                }
            }

            for (int i = 0; i < Prov_Box.getItemCount(); i++) {
                if (Prov_Box.getItemAt(i).trim().equalsIgnoreCase(proveedor)) {
                    Prov_Box.setSelectedIndex(i);
                    break;
                }
            }

            boolean empleadoEncontrado = false;
            for (int i = 0; i < Empleado_Box.getItemCount(); i++) {
                String itemEmpleado = Empleado_Box.getItemAt(i).replaceAll("\\s+", " ").trim();
                if (itemEmpleado.equalsIgnoreCase(empleado)) {
                    Empleado_Box.setSelectedIndex(i);
                    empleadoEncontrado = true;
                    break;
                }
            }

            if (!empleadoEncontrado) {
                JOptionPane.showMessageDialog(this, "No se encontró coincidencia para el empleado: " + empleado, "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos seleccionados: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Por favor selecciona una fila.", "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}
    private void cargarDetalleCompra(String idCompra) {
    try {
        // Llamar al DAO para obtener los detalles de la compra
        List<Object[]> detalles = daoCompras.obtenerDetalleCompra(idCompra);

        // Configurar el modelo de la tabla
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"Producto", "Cantidad"}, 0
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Ninguna celda será editable
                return false;
            }
        };

        // Agregar los datos al modelo
        for (Object[] detalle : detalles) {
            model.addRow(detalle);
        }

        // Asignar el modelo a la tabla
        TablaEdoCompra.setModel(model);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al cargar el detalle de la compra: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
    private void BuscarCompras() {
    String filtro = BarraBusqueda.getText().trim();
    if (filtro.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, ingresa un criterio de búsqueda.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        // Realiza la búsqueda a través del DAO
        List<Object[]> resultados = daoCompras.buscarCompras(filtro);

        // Configura el modelo de la tabla
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Fecha de Compra", "Estado", "Proveedor", "Empleado"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer todas las celdas no editables
            }
        };

        // Llena el modelo con los datos obtenidos
        for (Object[] fila : resultados) {
            model.addRow(fila);
        }

        // Asigna el modelo a la tabla
        TablaCompra.setModel(model);

        // Oculta la columna de ID
        ocultarColumnaID(TablaCompra, 0);

        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron compras con el criterio ingresado.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al realizar la búsqueda: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
}
    private void eliminarCompra() {
    try {
        // Verificar si hay una fila seleccionada
        int filaSeleccionada = TablaCompra.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una compra en la tabla para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener el ID de la compra seleccionada
        String idCompra = TablaCompra.getValueAt(filaSeleccionada, 0).toString().trim();

        if (idCompra.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El ID de la compra seleccionada está vacío. Verifica los datos de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirmación antes de eliminar
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar la compra y todos sus detalles?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return; // El usuario canceló la operación
        }

        // Llamar al DAO para eliminar la compra
        boolean exito = daoCompras.eliminarCompra(idCompra);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Compra y detalles eliminados correctamente.");
            cargarCompras(); // Recargar la tabla de compras
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar la compra. Verifica que la compra exista y que no tenga restricciones asociadas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al eliminar la compra: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        fondo = new javax.swing.JPanel();
        COMPRAS = new javax.swing.JLabel();
        FILTRAR = new javax.swing.JLabel();
        BarraBusqueda = new javax.swing.JTextField();
        Compra = new javax.swing.JScrollPane();
        TablaCompra = new javax.swing.JTable();
        ELIMINAR_BOTON = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        compras = new javax.swing.JPanel();
        FechaVenta = new javax.swing.JLabel();
        FechaVTxtf = new javax.swing.JTextField();
        EdoCompra = new javax.swing.JLabel();
        EdoCompra_Box = new javax.swing.JComboBox<>();
        Proveedor = new javax.swing.JLabel();
        Prov_Box = new javax.swing.JComboBox<>();
        Empleado = new javax.swing.JLabel();
        Empleado_Box = new javax.swing.JComboBox<>();
        DetalleCompra = new javax.swing.JScrollPane();
        TablaEdoCompra = new javax.swing.JTable();
        Agregar_Compra_Botton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(940, 570));

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setPreferredSize(new java.awt.Dimension(1040, 560));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        fondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        COMPRAS.setFont(new java.awt.Font("Jost", 0, 48)); // NOI18N
        COMPRAS.setText("COMPRAS");
        fondo.add(COMPRAS, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, 250, 60));

        FILTRAR.setText("FILTRAR:");
        fondo.add(FILTRAR, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 110, -1, 20));

        BarraBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BarraBusquedaActionPerformed(evt);
            }
        });
        BarraBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BarraBusquedaKeyPressed(evt);
            }
        });
        fondo.add(BarraBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 110, 280, -1));

        TablaCompra.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        TablaCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "FECHA COMPRA", "PROVEEDOR", "EMPLEADO", "ESTADO COMPRA", "PRODUCTO", "CANTIDAD", "TOTAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaCompra.setCellSelectionEnabled(true);
        TablaCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaCompraMouseClicked(evt);
            }
        });
        TablaCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TablaCompraKeyPressed(evt);
            }
        });
        Compra.setViewportView(TablaCompra);
        TablaCompra.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (TablaCompra.getColumnModel().getColumnCount() > 0) {
            TablaCompra.getColumnModel().getColumn(0).setHeaderValue("FECHA COMPRA");
            TablaCompra.getColumnModel().getColumn(1).setHeaderValue("PROVEEDOR");
            TablaCompra.getColumnModel().getColumn(2).setHeaderValue("EMPLEADO");
            TablaCompra.getColumnModel().getColumn(3).setHeaderValue("ESTADO COMPRA");
            TablaCompra.getColumnModel().getColumn(4).setResizable(false);
            TablaCompra.getColumnModel().getColumn(5).setResizable(false);
            TablaCompra.getColumnModel().getColumn(6).setHeaderValue("TOTAL");
        }

        fondo.add(Compra, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 140, 400, 120));

        ELIMINAR_BOTON.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eliminar.png"))); // NOI18N
        ELIMINAR_BOTON.setContentAreaFilled(false);
        ELIMINAR_BOTON.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ELIMINAR_BOTON.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ELIMINAR_BOTONActionPerformed(evt);
            }
        });
        fondo.add(ELIMINAR_BOTON, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, -1, -1));

        jLabel8.setFont(new java.awt.Font("Jost", 1, 18)); // NOI18N
        jLabel8.setText("DETALLE COMPRAS");
        fondo.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 280, -1, -1));

        compras.setBackground(new java.awt.Color(255, 255, 255));
        compras.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        compras.setForeground(new java.awt.Color(204, 204, 204));

        FechaVenta.setText("FECHA DE COMPRA");

        FechaVTxtf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FechaVTxtfActionPerformed(evt);
            }
        });

        EdoCompra.setText("ESTADO COMPRA");

        EdoCompra_Box.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        EdoCompra_Box.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EdoCompra_Box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EdoCompra_BoxActionPerformed(evt);
            }
        });

        Proveedor.setText("PROVEEDOR");

        Prov_Box.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Prov_Box.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Prov_Box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Prov_BoxActionPerformed(evt);
            }
        });

        Empleado.setText("EMPLEADO");

        Empleado_Box.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Empleado_Box.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout comprasLayout = new javax.swing.GroupLayout(compras);
        compras.setLayout(comprasLayout);
        comprasLayout.setHorizontalGroup(
            comprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(comprasLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(comprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(comprasLayout.createSequentialGroup()
                        .addComponent(FechaVenta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FechaVTxtf, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(comprasLayout.createSequentialGroup()
                        .addGroup(comprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Proveedor)
                            .addComponent(Empleado))
                        .addGap(14, 14, 14)
                        .addGroup(comprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(comprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(comprasLayout.createSequentialGroup()
                                    .addComponent(EdoCompra)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(EdoCompra_Box, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(Prov_Box, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(Empleado_Box, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        comprasLayout.setVerticalGroup(
            comprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(comprasLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(comprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FechaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FechaVTxtf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EdoCompra_Box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EdoCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(comprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(comprasLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(Proveedor))
                    .addComponent(Prov_Box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(comprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Empleado, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Empleado_Box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        fondo.add(compras, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 440, 130));

        TablaEdoCompra.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        TablaEdoCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "PRODUCTO", "CANTIDAD"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaEdoCompra.setCellSelectionEnabled(true);
        TablaEdoCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaEdoCompraMouseClicked(evt);
            }
        });
        DetalleCompra.setViewportView(TablaEdoCompra);
        TablaEdoCompra.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (TablaEdoCompra.getColumnModel().getColumnCount() > 0) {
            TablaEdoCompra.getColumnModel().getColumn(0).setResizable(false);
            TablaEdoCompra.getColumnModel().getColumn(1).setResizable(false);
        }

        fondo.add(DetalleCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 860, 170));

        Agregar_Compra_Botton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/nueva venta.png"))); // NOI18N
        Agregar_Compra_Botton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Agregar_Compra_BottonActionPerformed(evt);
            }
        });
        fondo.add(Agregar_Compra_Botton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        fondo.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(835, 90, 60, -1));

        bg.add(fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 910, 530));

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

    private void BarraBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BarraBusquedaActionPerformed

    }//GEN-LAST:event_BarraBusquedaActionPerformed

    private void TablaEdoCompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaEdoCompraMouseClicked

    }//GEN-LAST:event_TablaEdoCompraMouseClicked

    private void EdoCompra_BoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EdoCompra_BoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EdoCompra_BoxActionPerformed

    private void FechaVTxtfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FechaVTxtfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FechaVTxtfActionPerformed

    private void TablaCompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaCompraMouseClicked
        manejarTablaCompra();
    }//GEN-LAST:event_TablaCompraMouseClicked

    private void TablaCompraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaCompraKeyPressed
       if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
        manejarTablaCompra();
        evt.consume();
    }
    }//GEN-LAST:event_TablaCompraKeyPressed

    private void Prov_BoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Prov_BoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Prov_BoxActionPerformed

    private void Agregar_Compra_BottonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Agregar_Compra_BottonActionPerformed
        try {
            // Pasa el usuario activo al constructor
            TablaDeCompraNueva nuevaCompra = new TablaDeCompraNueva(usuarioActivo);

            nuevaCompra.setVisible(true);
            nuevaCompra.setLocationRelativeTo(null);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al abrir la ventana de nueva compra: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    
    }//GEN-LAST:event_Agregar_Compra_BottonActionPerformed

    private void ELIMINAR_BOTONActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ELIMINAR_BOTONActionPerformed
       eliminarCompra();
    }//GEN-LAST:event_ELIMINAR_BOTONActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       BuscarCompras();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void BarraBusquedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BarraBusquedaKeyPressed
           if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
        BuscarCompras();
        evt.consume();
    }
    }//GEN-LAST:event_BarraBusquedaKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Agregar_Compra_Botton;
    private javax.swing.JTextField BarraBusqueda;
    private javax.swing.JLabel COMPRAS;
    private javax.swing.JScrollPane Compra;
    private javax.swing.JScrollPane DetalleCompra;
    private javax.swing.JButton ELIMINAR_BOTON;
    private javax.swing.JLabel EdoCompra;
    private javax.swing.JComboBox<String> EdoCompra_Box;
    private javax.swing.JLabel Empleado;
    private javax.swing.JComboBox<String> Empleado_Box;
    private javax.swing.JLabel FILTRAR;
    private javax.swing.JTextField FechaVTxtf;
    private javax.swing.JLabel FechaVenta;
    private javax.swing.JComboBox<String> Prov_Box;
    private javax.swing.JLabel Proveedor;
    private javax.swing.JTable TablaCompra;
    private javax.swing.JTable TablaEdoCompra;
    private javax.swing.JPanel bg;
    private javax.swing.JPanel compras;
    private javax.swing.JPanel fondo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel8;
    // End of variables declaration//GEN-END:variables

    public void setLocation(double d) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
