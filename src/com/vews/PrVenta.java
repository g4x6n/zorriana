
package com.vews;
import database.dao.DaoVentas;
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
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        manejarTablaVenta();
    }
});

}

private void cargarVentas() {
    DaoVentas daoVentas = new DaoVentas();
    List<Object[]> ventas = daoVentas.listarVentas();

    // Verifica si los datos están llegando
    for (Object[] venta : ventas) {
        System.out.println("Venta: " + java.util.Arrays.toString(venta));
    }

    // Modelo de la tabla
    DefaultTableModel modelo = (DefaultTableModel) jTable4.getModel();
    modelo.setRowCount(0); // Limpia las filas existentes

    // Agrega los datos al modelo de la tabla
    for (Object[] venta : ventas) {
        modelo.addRow(venta);
    }
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
private void seleccionarItemComboBox(javax.swing.JComboBox<String> comboBox, String valor) {
    for (int i = 0; i < comboBox.getItemCount(); i++) {
        if (comboBox.getItemAt(i).trim().equalsIgnoreCase(valor.trim())) {
            comboBox.setSelectedIndex(i);
            return; // Sale del bucle una vez encontrado
        }
    }
    JOptionPane.showMessageDialog(this, "No se encontró el valor \"" + valor + "\" en el combo.", "Advertencia", JOptionPane.WARNING_MESSAGE);
}


private void manejarTablaVenta() {
    int filaSeleccionada = jTable4.getSelectedRow();
    if (filaSeleccionada != -1) {
        try {
            // Obtener los valores de la fila seleccionada
            String fechaVenta = jTable4.getValueAt(filaSeleccionada, 0).toString().trim();
            String cliente = jTable4.getValueAt(filaSeleccionada, 1).toString().replaceAll("\\s+", " ").trim();
            String empleado = jTable4.getValueAt(filaSeleccionada, 2).toString().replaceAll("\\s+", " ").trim();
            String estadoVenta = jTable4.getValueAt(filaSeleccionada, 3).toString().trim();
            String metodoPago = jTable4.getValueAt(filaSeleccionada, 4).toString().trim();

            // Asignar los valores a los campos
            jTextField1.setText(fechaVenta); // Fecha de Venta

            // Estado de Venta
            boolean estadoEncontrado = false;
            for (int i = 0; i < jComboBox2.getItemCount(); i++) {
                if (jComboBox2.getItemAt(i).trim().equalsIgnoreCase(estadoVenta)) {
                    jComboBox2.setSelectedIndex(i);
                    estadoEncontrado = true;
                    break;
                }
            }
            if (!estadoEncontrado) {
                JOptionPane.showMessageDialog(this, "No se encontró coincidencia para el estado de venta: " + estadoVenta, "Advertencia", JOptionPane.WARNING_MESSAGE);
            }

            // Cliente
            boolean clienteEncontrado = false;
            for (int i = 0; i < jComboBox1.getItemCount(); i++) {
                if (jComboBox1.getItemAt(i).replaceAll("\\s+", " ").trim().equalsIgnoreCase(cliente)) {
                    jComboBox1.setSelectedIndex(i);
                    clienteEncontrado = true;
                    break;
                }
            }
            if (!clienteEncontrado) {
                JOptionPane.showMessageDialog(this, "No se encontró coincidencia para el cliente: " + cliente, "Advertencia", JOptionPane.WARNING_MESSAGE);
            }

            // Empleado
            boolean empleadoEncontrado = false;
            for (int i = 0; i < jComboBox3.getItemCount(); i++) {
                if (jComboBox3.getItemAt(i).replaceAll("\\s+", " ").trim().equalsIgnoreCase(empleado)) {
                    jComboBox3.setSelectedIndex(i);
                    empleadoEncontrado = true;
                    break;
                }
            }
            if (!empleadoEncontrado) {
                JOptionPane.showMessageDialog(this, "No se encontró coincidencia para el empleado: " + empleado, "Advertencia", JOptionPane.WARNING_MESSAGE);
            }

            // Método de Pago
            boolean metodoPagoEncontrado = false;
            for (int i = 0; i < jComboBox4.getItemCount(); i++) {
                if (jComboBox4.getItemAt(i).trim().equalsIgnoreCase(metodoPago)) {
                    jComboBox4.setSelectedIndex(i);
                    metodoPagoEncontrado = true;
                    break;
                }
            }
            if (!metodoPagoEncontrado) {
                JOptionPane.showMessageDialog(this, "No se encontró coincidencia para el método de pago: " + metodoPago, "Advertencia", JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos seleccionados: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Por favor selecciona una fila.", "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}




private void cargarDetalleVenta(String fechaVenta) {
    try {
        // Llamar al DAO para obtener los detalles de la venta
        List<Object[]> detalles = daoVentas.obtenerDetalleVenta(fechaVenta);

        // Configurar el modelo de la tabla
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"Producto", "Cantidad", "Precio Unitario", "Subtotal"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Ninguna celda será editable
            }
        };

        // Agregar los datos al modelo
        for (Object[] detalle : detalles) {
            model.addRow(detalle);
        }

        // Asignar el modelo a la tabla secundaria
        jTable3.setModel(model);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al cargar el detalle de la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jTextField3 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButtonEliminar = new javax.swing.JButton();
        jButtonEditarVenta = new javax.swing.JButton();
        guardado = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();

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

        jLabel7.setFont(new java.awt.Font("Jost", 1, 18)); // NOI18N
        jLabel7.setText("VENTA");
        fondo.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 80, -1));

        jLabel8.setFont(new java.awt.Font("Jost", 1, 18)); // NOI18N
        jLabel8.setText("DETALLE VENTA");
        fondo.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, -1, -1));

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

        fondo.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 140, 430, 370));

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        fondo.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 110, 230, -1));

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

        fondo.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 420, 180));

        jButtonEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eliminar.png"))); // NOI18N
        jButtonEliminar.setContentAreaFilled(false);
        jButtonEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarActionPerformed(evt);
            }
        });
        fondo.add(jButtonEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 100, -1, -1));

        jButtonEditarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/editar venta.png"))); // NOI18N
        jButtonEditarVenta.setContentAreaFilled(false);
        jButtonEditarVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonEditarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarVentaActionPerformed(evt);
            }
        });
        fondo.add(jButtonEditarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 290, -1, -1));

        guardado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/guardar.png"))); // NOI18N
        guardado.setContentAreaFilled(false);
        guardado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        guardado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardadoActionPerformed(evt);
            }
        });
        fondo.add(guardado, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 290, -1, -1));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        fondo.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 100, -1, -1));

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
//        eliminarEmpleado();
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

    private void guardadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_guardadoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel fondo;
    private javax.swing.JButton guardado;
    private javax.swing.JButton jButtonEditarVenta;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
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
