
package com.vews;
import database.dao.DaoVentas;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;


public class PrVenta extends javax.swing.JPanel {
private final DaoVentas daoVentas = new DaoVentas();

    public static PrVenta cl;

    public PrVenta() {
    initComponents(); // Inicializa los componentes generados automáticamente
    cargarVentas();   // Carga los datos de la base de datos
    cargarEmpleados();
    cargarClientes();
    cargarEdoVenta();
    cargarMetodoPago();
        // Asignar evento de clic en la tabla
jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        int filaSeleccionada = jTable4.getSelectedRow();
        if (filaSeleccionada != -1) {
            // Obtén el ID_VENTA desde la columna oculta
            String idVenta = jTable4.getValueAt(filaSeleccionada, 0).toString().trim();
            

            // Verificar si el ID_VENTA no es nulo o vacío
            if (idVenta != null && !idVenta.isEmpty()) {
                cargarDetalleVenta(idVenta); // Cargar los detalles basados en el ID_VENTA
            } else {
                System.out.println("Error: ID_VENTA vacío o nulo.");
            }
        }cargarDatosDesdeTablaVenta();
        
    }
});

}
private void cargarVentas() {
    List<Object[]> ventas = daoVentas.listarVentas();

  
    // Crear el modelo de la tabla con las columnas necesarias
    DefaultTableModel modelo = new DefaultTableModel(
        new String[]{"ID_VENTA", "Fecha Venta", "Cliente", "Empleado", "Estado Venta", "Método Pago"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Hacer todas las celdas no editables
        }
    };

    // Añadir cada venta al modelo de la tabla
    for (Object[] venta : ventas) {
        modelo.addRow(venta);
        }

    // Asignar el modelo al JTable
    jTable4.setModel(modelo);

    // Ocultar la columna del ID_VENTA (columna 0)
    jTable4.getColumnModel().getColumn(0).setMinWidth(0);
    jTable4.getColumnModel().getColumn(0).setMaxWidth(0);
    jTable4.getColumnModel().getColumn(0).setWidth(0);
}


    private void cargarEmpleados() {
    // Llenar la lista de empleados en el combo box
    try {
        List<String> empleados = daoVentas.obtenerEmpleado(); // Obtener la lista de empleados
        jComboBox3.removeAllItems(); // Limpiar los ítems del combo box antes de llenarlo
        for (String empleado : empleados) {
            // Asegurarse de que los valores estén bien formateados antes de agregarlos
            jComboBox3.addItem(empleado.trim());
        }
    } catch (Exception e) {
        // Mostrar un mensaje de error en caso de fallo
        JOptionPane.showMessageDialog(this, "Error al cargar empleados: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

        private void cargarClientes() {
    // Llenar la lista de empleados en el combo box
    try {
        List<String> clientes = daoVentas.obtenerCliente(); // Obtener la lista de empleados
        jComboBox1.removeAllItems(); // Limpiar los ítems del combo box antes de llenarlo
        for (String cliente : clientes) {
            // Asegurarse de que los valores estén bien formateados antes de agregarlos
            jComboBox1.addItem(cliente.trim());
        }
    } catch (Exception e) {
        // Mostrar un mensaje de error en caso de fallo
        JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
   private void cargarEdoVenta() {
    // Llenar la lista de empleados en el combo box
    try {
        List<String> edoventas = daoVentas.obtenerEstadodeVenta(); // Obtener la lista de empleados
        jComboBox2.removeAllItems(); // Limpiar los ítems del combo box antes de llenarlo
        for (String edoventa : edoventas) {
            // Asegurarse de que los valores estén bien formateados antes de agregarlos
            jComboBox2.addItem(edoventa.trim());
        }
    } catch (Exception e) {
        // Mostrar un mensaje de error en caso de fallo
        JOptionPane.showMessageDialog(this, "Error al cargar estados de venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
      private void cargarMetodoPago() {
    // Llenar la lista de empleados en el combo box
    try {
        List<String> metodopago = daoVentas.obtenerMetodoPago(); // Obtener la lista de empleados
        jComboBox4.removeAllItems(); // Limpiar los ítems del combo box antes de llenarlo
        for (String metodospago : metodopago) {
            // Asegurarse de que los valores estén bien formateados antes de agregarlos
            jComboBox4.addItem(metodospago.trim());
        }
    } catch (Exception e) {
        // Mostrar un mensaje de error en caso de fallo
        JOptionPane.showMessageDialog(this, "Error al cargar metodos de pago: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
private void cargarDatosDesdeTablaVenta() {
    int filaSeleccionada = jTable4.getSelectedRow();
    if (filaSeleccionada != -1) {
        try {
            // Obtener los valores de la fila seleccionada
            String fecha = jTable4.getValueAt(filaSeleccionada, 1).toString().trim();
            String cliente = jTable4.getValueAt(filaSeleccionada, 2).toString().replaceAll("\\s+", " ").trim();
            String empleado = jTable4.getValueAt(filaSeleccionada, 3).toString().replaceAll("\\s+", " ").trim();
            String estadoVenta = jTable4.getValueAt(filaSeleccionada, 4).toString().trim();
            String metodoPago = jTable4.getValueAt(filaSeleccionada, 5).toString().trim();

            // Asignar los valores a los campos de texto y combos
            jTextField1.setText(fecha);

            // Estado de Venta
            for (int i = 0; i < jComboBox2.getItemCount(); i++) {
                if (jComboBox2.getItemAt(i).trim().equalsIgnoreCase(estadoVenta)) {
                    jComboBox2.setSelectedIndex(i);
                    break;
                }
            }

            // Cliente
            for (int i = 0; i < jComboBox1.getItemCount(); i++) {
                if (jComboBox1.getItemAt(i).replaceAll("\\s+", " ").trim().equalsIgnoreCase(cliente)) {
                    jComboBox1.setSelectedIndex(i);
                    break;
                }
            }

            // Empleado
            for (int i = 0; i < jComboBox3.getItemCount(); i++) {
                if (jComboBox3.getItemAt(i).replaceAll("\\s+", " ").trim().equalsIgnoreCase(empleado)) {
                    jComboBox3.setSelectedIndex(i);
                    break;
                }
            }

            // Método de Pago
            for (int i = 0; i < jComboBox4.getItemCount(); i++) {
                if (jComboBox4.getItemAt(i).trim().equalsIgnoreCase(metodoPago)) {
                    jComboBox4.setSelectedIndex(i);
                    break;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos de la tabla: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Por favor selecciona una fila.", "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}
private void eliminarVenta() {
    try {
        // Verifica si hay una venta seleccionada
        int filaSeleccionada = jTable4.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una venta en la tabla para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtén el ID de la venta seleccionada
        String idVenta = jTable4.getValueAt(filaSeleccionada, 0).toString().trim();
        

        if (idVenta.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El ID de la venta seleccionada está vacío. Verifica los datos de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirmación antes de eliminar
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar la venta?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return; // El usuario canceló
        }

        // Llama al método para eliminar venta en el DAO
        boolean exito = daoVentas.deleteVenta(idVenta);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Venta eliminada correctamente.");
            cargarVentas(); // Recargar tabla de ventas
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar la venta. Puede tener detalles asociados.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al eliminar la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}


private void cargarDetalleVenta(String idVenta) {
    List<Object[]> detalles = daoVentas.obtenerDetalleVenta(idVenta);

    DefaultTableModel modelo = new DefaultTableModel(
        new String[]{"Producto", "Cantidad", "Precio", "Total"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Hacer todas las celdas no editables
        }
    };

    for (Object[] detalle : detalles) {
        modelo.addRow(detalle);
    }

    jTable3.setModel(modelo);

    if (detalles.isEmpty()) {
        System.out.println("No se encontraron detalles para esta venta.");
    }
}


private void BuscarVenta() {
    String filtro = jTextField3.getText().trim(); // Obtener texto del campo de búsqueda

    if (filtro.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, ingresa un criterio de búsqueda.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        // Realiza la búsqueda a través del DAO
        List<Object[]> resultados = daoVentas.buscarVentas(filtro);

        // Configura el modelo de la tabla
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"Fecha Venta", "Cliente", "Empleado", "Estado Venta", "Método Pago"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Ninguna celda será editable
            }
        };

        // Llena el modelo con los datos obtenidos
        for (Object[] fila : resultados) {
            model.addRow(fila);
        }

        // Asigna el modelo a la tabla
        jTable4.setModel(model);

        // Muestra un mensaje si no hay resultados
        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron ventas con el criterio ingresado.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al realizar la búsqueda: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
}

private void jTextField3KeyPressed(java.awt.event.KeyEvent evt) {                                     
    if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
        BuscarVenta();
        evt.consume();
    }
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        fondo = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jTextField3 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButtonEliminar = new javax.swing.JButton();
        jButtonEditarVenta = new javax.swing.JButton();
        BUSQUEDA = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(940, 570));

        fondo.setPreferredSize(new java.awt.Dimension(1040, 560));
        fondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Jost", 0, 48)); // NOI18N
        jLabel9.setText("VENTAS");
        fondo.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 20, 210, 60));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), null));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("FECHA VENTA");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 20));

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 110, -1));

        jLabel2.setText("ESTADO VENTA");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, -1, 20));

        jLabel3.setText("CLIENTE");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, 330, -1));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, 80, -1));

        jLabel4.setText("EMPLEADO");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, 20));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 320, -1));

        jLabel5.setText("METODO PAGO");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, 20));

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 90, -1));

        jLabel6.setText("TOTAL A PAGAR ");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, -1, 20));

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 130, 100, -1));

        fondo.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, 420, 160));

        jLabel8.setFont(new java.awt.Font("Jost", 1, 18)); // NOI18N
        jLabel8.setText("DETALLE VENTA");
        fondo.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 300, -1, -1));

        jTable4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "FECHA VENTA", "CLIENTE", "EMPLEADO", "ESTADO VENTA", "METODO PAGO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTable4);

        fondo.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 140, 430, 140));

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        fondo.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 110, 320, -1));

        jLabel10.setText("FILTRAR:");
        fondo.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 110, -1, 20));

        jTable3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "PRODUCTO", "CANTIDAD", "PRECIO", "TOTAL"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        fondo.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 750, 180));

        jButtonEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eliminar.png"))); // NOI18N
        jButtonEliminar.setContentAreaFilled(false);
        jButtonEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarActionPerformed(evt);
            }
        });
        fondo.add(jButtonEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 400, 70, 60));

        jButtonEditarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/editar venta.png"))); // NOI18N
        jButtonEditarVenta.setContentAreaFilled(false);
        jButtonEditarVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonEditarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarVentaActionPerformed(evt);
            }
        });
        fondo.add(jButtonEditarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 340, 80, 50));

        BUSQUEDA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        BUSQUEDA.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BUSQUEDA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BUSQUEDAMouseClicked(evt);
            }
        });
        fondo.add(BUSQUEDA, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 100, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fondo, javax.swing.GroupLayout.DEFAULT_SIZE, 920, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fondo, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleParent(this);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed

        eliminarVenta();
    
    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void jButtonEditarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarVentaActionPerformed
       // Object[] datosEmpleado = obtenerDatosEmpleado();
       /* if (datosEmpleado == null) {
            return; // Si hay un error en los datos, no continúa
        }/*

        boolean exito = daoEmpleado.addEmployee(datosEmpleado);
        if (exito) {
            JOptionPane.showMessageDialog(this, "Empleado agregado correctamente.");
            cargarEmpleados(); // Recarga la tabla de empleados
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar el empleado.", "Error", JOptionPane.ERROR_MESSAGE);
        }/**/
    }//GEN-LAST:event_jButtonEditarVentaActionPerformed

    private void BUSQUEDAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BUSQUEDAMouseClicked
        jTextField3.setText(""); // Asegurarse de que el campo de búsqueda esté vacío
        BuscarVenta(); // Cargar todas las ventas inicialmente
    }//GEN-LAST:event_BUSQUEDAMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BUSQUEDA;
    private javax.swing.JPanel fondo;
    private javax.swing.JButton jButtonEditarVenta;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables

    public void setLocation(double d) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
