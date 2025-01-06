/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.vews;
import database.dao.DaoProducto;
import database.dao.DaoVentas; // Para interactuar con el DAO
import java.text.SimpleDateFormat; // Para formatear fechas
import java.util.Date; // Para manejar la fecha actual
import java.util.List; // Para manejar listas
import javax.swing.*; // Para componentes gráficos como JComboBox, JLabel, etc.
import javax.swing.table.DefaultTableModel;

public class AgVenta extends javax.swing.JFrame {
    private final DaoProducto daoProducto; // Acceso a la base de datos para productos
    private final String empleadoActual; // Empleado que inició sesión
    private final DaoVentas daoVentas = new DaoVentas(); // Acceso a la base de datos
    
    public AgVenta(String empleadoActual) {
        this.empleadoActual = empleadoActual;
        this.daoProducto = new DaoProducto(); // Inicializa DaoProducto
        initComponents();
        configurarComponentes();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        cargarProductos(); // Cargar productos al inicializar
        TOTAL.setEditable(false); 
    }
    public AgVenta(String empleadoActual, Object[] datosEmpleado) {
    this.empleadoActual = empleadoActual;
    this.daoProducto = new DaoProducto();
    initComponents();
    configurarComponentes();

    // Mostrar el nombre completo del empleado en jLabel7
    if (datosEmpleado != null) {
        String nombreCompleto = datosEmpleado[3] + " " + datosEmpleado[4] + " " + (datosEmpleado[5] != null ? datosEmpleado[5] : "");
        jLabel7.setText(nombreCompleto.trim());
    } else {
        jLabel7.setText("Empleado no encontrado");
    }

    setLocationRelativeTo(null);
    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    cargarProductos();
    TOTAL.setEditable(false);
}

    private void cargarProductos() {
        try {
            List<Object[]> listaProductos = daoProducto.listProductos(); // Asume que listProductos() devuelve los datos correctos
            DefaultTableModel modelo = (DefaultTableModel) TABLAPRODUCTOS.getModel();
            modelo.setRowCount(0);

            for (Object[] producto : listaProductos) {
                modelo.addRow(new Object[]{
                    producto[1], // Nombre
                    producto[7], // Stock
                    producto[8]  // Precio
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

private void configurarComponentes() {
    // Verifica si el ID del empleado es válido
    System.out.println("ID Empleado Actual: " + empleadoActual);

    // Obtener el nombre del empleado por su ID desde DaoVentas
    String nombreEmpleado = daoVentas.obtenerNombreEmpleadoPorId(empleadoActual); // Usamos el ID para obtener el nombre
    if (nombreEmpleado != null) {
        System.out.println("Nombre del empleado: " + nombreEmpleado); // Verifica el nombre obtenido
        jLabel7.setText(nombreEmpleado); // Mostrar el nombre del empleado en el JLabel
    } else {
        System.out.println("Empleado no encontrado con ID: " + empleadoActual); // Si no se encuentra el empleado
        jLabel7.setText("Empleado no encontrado");
    }

    // Configurar los demás componentes (clientes, fecha, etc.)
    cargarClientes(); // Cargar clientes desde la base de datos
    java.time.LocalDate fechaActual = java.time.LocalDate.now(); // Obtener la fecha actual
    AGFECHADEVENTA.setText(fechaActual.toString()); // Mostrar la fecha en el campo
    AGFECHADEVENTA.setEditable(false); // Desactivar la edición del campo de fecha
    cargarEstadosVenta(); // Cargar los estados de venta
    cargarMetodosPago(); // Cargar los métodos de pago
}


    private void cargarClientes() {
        try {
            List<String> clientes = daoVentas.obtenerCliente(); // Obtener clientes desde la base de datos
            AGCLIENTE.removeAllItems(); // Limpiar JComboBox
            for (String cliente : clientes) {
                AGCLIENTE.addItem(cliente.trim());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarEstadosVenta() {
        try {
            List<String> estados = daoVentas.obtenerEstadodeVenta(); // Obtener estados desde la base de datos
            AGEDOVENTA.removeAllItems(); // Limpiar JComboBox
            for (String estado : estados) {
                AGEDOVENTA.addItem(estado.trim());
            }
            // Establecer "Pagado" como predeterminado si existe
            if (estados.contains("Pagado")) {
                AGEDOVENTA.setSelectedItem("Pagado");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar estados de venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarMetodosPago() {
        try {
            List<String> metodosPago = daoVentas.obtenerMetodoPago(); // Obtener métodos de pago
            AGMETODOPAGO.removeAllItems(); // Limpiar JComboBox
            for (String metodo : metodosPago) {
                AGMETODOPAGO.addItem(metodo.trim());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar métodos de pago: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
private void configComponents(){
        // Titulo de la ventana
        setTitle("Marcas");
        // posición de la ventana
        setLocationRelativeTo(null);
    }
 private javax.swing.JScrollPane jScrollPaneProductos;
private javax.swing.JTable tablaProductos;

private void actualizarTotal() {
    DefaultTableModel modelo = (DefaultTableModel) AgDVenta.getModel();
    double total = 0.0;
    for (int i = 0; i < modelo.getRowCount(); i++) {
        double subtotal = (Double) modelo.getValueAt(i, 3); // Asegúrate de que es la columna correcta
        System.out.println("Subtotal de fila " + i + ": " + subtotal); // Log para depuración
        total += subtotal;
    }
    TOTAL.setText(String.format("%.2f", total));
}




    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        AGCLIENTE = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        AGFECHADEVENTA = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        AGEDOVENTA = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        AGMETODOPAGO = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TABLAPRODUCTOS = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        AgDVenta = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        AGREGAR = new javax.swing.JButton();
        QUITAR = new javax.swing.JButton();
        CONFIRMAR = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        TOTAL = new javax.swing.JTextField();

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

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTable3);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("EMPLEADO");

        jLabel2.setText("CLIENTE");

        AGCLIENTE.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        AGCLIENTE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AGCLIENTEActionPerformed(evt);
            }
        });

        jLabel3.setText("FECHA DE VENTA");

        AGFECHADEVENTA.setText("jTextField1");
        AGFECHADEVENTA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AGFECHADEVENTAActionPerformed(evt);
            }
        });

        jLabel4.setText("ESTADO DE VENTA");

        AGEDOVENTA.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        AGEDOVENTA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AGEDOVENTAActionPerformed(evt);
            }
        });

        jLabel5.setText("METODO DE PAGO");

        AGMETODOPAGO.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        AGMETODOPAGO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AGMETODOPAGOActionPerformed(evt);
            }
        });

        jLabel6.setText("DETALLE VENTA");

        TABLAPRODUCTOS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nombre", "Precio", "Cantidad"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TABLAPRODUCTOS);

        jLabel9.setText("PRODUCTOS");

        AgDVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nombre", "Cantidad", "Precio", "Subtotal"
            }
        ));
        jScrollPane4.setViewportView(AgDVenta);

        jLabel7.setText("jLabel7");

        AGREGAR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/agregar (2).png"))); // NOI18N
        AGREGAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AGREGARActionPerformed(evt);
            }
        });

        QUITAR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/boton-menos.png"))); // NOI18N
        QUITAR.setToolTipText("");
        QUITAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QUITARActionPerformed(evt);
            }
        });

        CONFIRMAR.setText("CONFIRMAR COMPRA");
        CONFIRMAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CONFIRMARActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setText("TOTAL");

        TOTAL.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        TOTAL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TOTALActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addGap(18, 18, 18)
                            .addComponent(AGFECHADEVENTA, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(AGEDOVENTA, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(AGMETODOPAGO, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(AGCLIENTE, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addComponent(jLabel7)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(204, 204, 204))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(152, 152, 152)
                .addComponent(jLabel6)
                .addGap(0, 616, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(TOTAL, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(AGREGAR)
                        .addGap(35, 35, 35)
                        .addComponent(QUITAR)
                        .addGap(34, 34, 34)
                        .addComponent(CONFIRMAR)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(AGCLIENTE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(AGFECHADEVENTA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(AGEDOVENTA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(AGMETODOPAGO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)))
                .addComponent(jLabel6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(37, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(AGREGAR)
                            .addComponent(QUITAR)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(CONFIRMAR)
                                .addGap(12, 12, 12)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TOTAL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AGFECHADEVENTAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AGFECHADEVENTAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AGFECHADEVENTAActionPerformed

    private void AGEDOVENTAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AGEDOVENTAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AGEDOVENTAActionPerformed

    private void AGMETODOPAGOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AGMETODOPAGOActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AGMETODOPAGOActionPerformed

    private void AGCLIENTEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AGCLIENTEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AGCLIENTEActionPerformed

    private void QUITARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_QUITARActionPerformed
    int filaSeleccionada = AgDVenta.getSelectedRow();
    if (filaSeleccionada != -1) { // Verificar si se seleccionó alguna fila
        int cantidadEnCarrito = (Integer) AgDVenta.getValueAt(filaSeleccionada, 1); // Asumiendo que la cantidad está en la columna 1
        int cantidadAEliminar = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese la cantidad a eliminar", cantidadEnCarrito));
        
        if (cantidadAEliminar > cantidadEnCarrito) {
            JOptionPane.showMessageDialog(this, "No puede eliminar más de lo que hay en el carrito", "Error de cantidad", JOptionPane.ERROR_MESSAGE);
        } else if (cantidadAEliminar == cantidadEnCarrito) {
            // Eliminar toda la fila si la cantidad a eliminar es igual a la del carrito
            ((DefaultTableModel) AgDVenta.getModel()).removeRow(filaSeleccionada);
        } else {
            // Reducir la cantidad
            int nuevaCantidad = cantidadEnCarrito - cantidadAEliminar;
            AgDVenta.setValueAt(nuevaCantidad, filaSeleccionada, 1); // Actualizar la cantidad en la tabla
            double precio = (Double) AgDVenta.getValueAt(filaSeleccionada, 2);
            AgDVenta.setValueAt(precio * nuevaCantidad, filaSeleccionada, 3); // Actualizar el subtotal
        }
        actualizarTotal(); // Actualiza el total después de realizar los cambios
    } else {
        JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
    }//GEN-LAST:event_QUITARActionPerformed

    private void AGREGARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AGREGARActionPerformed
     int cantidadDeseada = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese la cantidad deseada"));
    int filaSeleccionada = TABLAPRODUCTOS.getSelectedRow();
    if (filaSeleccionada != -1) {
        int stockDisponible = (Integer) TABLAPRODUCTOS.getValueAt(filaSeleccionada, 1);
        if (cantidadDeseada > stockDisponible) {
            JOptionPane.showMessageDialog(this, "No hay suficiente stock para la cantidad solicitada", "Error de stock", JOptionPane.ERROR_MESSAGE);
        } else {
            double precio = (Double) TABLAPRODUCTOS.getValueAt(filaSeleccionada, 2);
            double subtotal = cantidadDeseada * precio;

            DefaultTableModel modeloDetalle = (DefaultTableModel) AgDVenta.getModel();
            String nombreProducto = TABLAPRODUCTOS.getValueAt(filaSeleccionada, 0).toString();
            // Inserta la fila en la posición cero
            modeloDetalle.insertRow(0, new Object[]{nombreProducto, cantidadDeseada, precio, subtotal});
        }
    } else {
        JOptionPane.showMessageDialog(this, "Seleccione un producto para agregar", "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
    actualizarTotal();
    }//GEN-LAST:event_AGREGARActionPerformed

    private void CONFIRMARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CONFIRMARActionPerformed
       
    }//GEN-LAST:event_CONFIRMARActionPerformed

    private void TOTALActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TOTALActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TOTALActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> AGCLIENTE;
    private javax.swing.JComboBox<String> AGEDOVENTA;
    private javax.swing.JTextField AGFECHADEVENTA;
    private javax.swing.JComboBox<String> AGMETODOPAGO;
    private javax.swing.JButton AGREGAR;
    private javax.swing.JTable AgDVenta;
    private javax.swing.JButton CONFIRMAR;
    private javax.swing.JButton QUITAR;
    private javax.swing.JTable TABLAPRODUCTOS;
    private javax.swing.JTextField TOTAL;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    // End of variables declaration//GEN-END:variables
}
