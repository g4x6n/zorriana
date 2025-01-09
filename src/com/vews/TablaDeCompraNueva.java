/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.vews;

import database.dao.DaoCompras;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;


public class TablaDeCompraNueva extends javax.swing.JFrame {
private String usuarioActivo;
private void ocultarColumnaID(javax.swing.JTable tabla, int indiceColumna) {
    tabla.getColumnModel().getColumn(indiceColumna).setMinWidth(0);
    tabla.getColumnModel().getColumn(indiceColumna).setMaxWidth(0);
    tabla.getColumnModel().getColumn(indiceColumna).setWidth(0);
    tabla.getColumnModel().getColumn(indiceColumna).setPreferredWidth(0);
}

private void resetFormulario() {
    // Limpiar tablas
    DefaultTableModel modeloCarrito = (DefaultTableModel) CarritoTabla.getModel();
    modeloCarrito.setRowCount(0);

    // Resetear listas desplegables
    EstadosDeCompra.setSelectedIndex(0);
    Proveedores.setSelectedIndex(0);

    // Recargar productos
    cargarProductosTabla();
}


public TablaDeCompraNueva(String empleadoActual) {
    this.usuarioActivo = empleadoActual; // Guarda el empleado actual como usuario activo
    initComponents(); // Inicializa los componentes de la interfaz gráfica
    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null); // Centrar ventana
    setTitle("Nueva Compra"); // Título de la ventana

    // Configurar los componentes iniciales
    configurarNombreEmpleado(usuarioActivo); // Establecer el nombre del empleado actual
    cargarProductosTabla(); // Cargar productos
    cargarProveedores(); // Cargar proveedores
    cargarEstadosDeCompra(); // Cargar estados de compra

    // Configurar campos de la interfaz
    setFechaActual(); // Configurar la fecha actual
}


private void configComponents(){
        // Titulo de la ventana
        setTitle("Nueva Compra");
        // posición de la ventana
        setLocationRelativeTo(null);
    }

private void configurarNombreEmpleado(String nombreEmpleado) {
    Usuario.setText(nombreEmpleado); 
}
private void cargarEstadosDeCompra() {
    try {
        DaoCompras daoCompras = new DaoCompras();
        List<String> estados = daoCompras.obtenerEstadosDeCompra(); // Método ajustado para obtener estados desde la base de datos

        EstadosDeCompra.removeAllItems();
        for (String estado : estados) {
            EstadosDeCompra.addItem(estado);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al cargar los estados de compra: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

 private void filtrarProductosPorProveedor() {
    String proveedorSeleccionado = (String) Proveedores.getSelectedItem();
    String idProveedor = (String) Proveedores.getClientProperty(proveedorSeleccionado); // Obtén el ID

    if (idProveedor != null) {
        try {
            DaoCompras daoCompras = new DaoCompras();
            List<Object[]> productos = daoCompras.obtenerProductosPorProveedor(idProveedor);

            // Configura la tabla de productos
            DefaultTableModel modelo = new DefaultTableModel(
                new String[]{"ID Producto", "Nombre"}, 0
            ) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            for (Object[] producto : productos) {
                modelo.addRow(producto);
            }

            ProductosTabla.setModel(modelo);
            ocultarColumnaID(ProductosTabla, 0);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al filtrar productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
private void cargarProveedores() {
    try {
        DaoCompras daoCompras = new DaoCompras();
        List<Object[]> proveedores = daoCompras.obtenerProveedores(); // ID y Nombre

        Proveedores.removeAllItems();
        for (Object[] proveedor : proveedores) {
            Proveedores.addItem(proveedor[1].toString()); // Mostrar NOMBRE_EMPRESA
            Proveedores.putClientProperty(proveedor[1].toString(), proveedor[0].toString()); // Asocia ID_PROVEEDOR
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al cargar proveedores: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
private void quitarProductoDelCarrito() {
    int filaSeleccionada = CarritoTabla.getSelectedRow(); // Obtiene la fila seleccionada en CarritoTabla

    if (filaSeleccionada != -1) { // Verifica que haya una fila seleccionada
        DefaultTableModel modeloCarrito = (DefaultTableModel) CarritoTabla.getModel();
        
        // Obtiene la cantidad actual
        int cantidadActual = Integer.parseInt(modeloCarrito.getValueAt(filaSeleccionada, 1).toString());

        if (cantidadActual > 1) {
            // Reduce la cantidad en 1
            modeloCarrito.setValueAt(cantidadActual - 1, filaSeleccionada, 1);
        } else {
            // Si la cantidad es 1, elimina la fila
            modeloCarrito.removeRow(filaSeleccionada);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Por favor, selecciona un producto para quitar del carrito.", "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}

private void agregarProductoAlCarrito() {
    int filaSeleccionada = ProductosTabla.getSelectedRow(); // Obtiene la fila seleccionada

    if (filaSeleccionada != -1) { // Verifica que haya una fila seleccionada
        // Obtiene los datos del producto seleccionado
        String nombreProducto = ProductosTabla.getValueAt(filaSeleccionada, 1).toString(); // Nombre Producto

        // Obtiene el modelo de CarritoTabla
        DefaultTableModel modeloCarrito = (DefaultTableModel) CarritoTabla.getModel();

        boolean productoEncontrado = false;

        // Busca si el producto ya está en el carrito
        for (int i = 0; i < modeloCarrito.getRowCount(); i++) {
            Object valorCelda = modeloCarrito.getValueAt(i, 0); // Producto está en la columna 0
            if (valorCelda != null && valorCelda.toString().equals(nombreProducto)) {
                // Producto encontrado, incrementa la cantidad
                int cantidadActual = Integer.parseInt(modeloCarrito.getValueAt(i, 1).toString());
                modeloCarrito.setValueAt(cantidadActual + 1, i, 1);
                productoEncontrado = true;
                break;
            }
        }

        if (!productoEncontrado) {
            // Producto no encontrado, agrega una nueva fila
            modeloCarrito.addRow(new Object[]{nombreProducto, 1});
        }
    } else {
        JOptionPane.showMessageDialog(this, "Por favor, selecciona un producto para agregar al carrito.", "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}



 private void cargarProductosTabla() {
    try {
        // Crear instancia del DAO
        DaoCompras daoCompras = new DaoCompras();
        
        // Obtener los productos desde la base de datos
        List<Object[]> productos = daoCompras.obtenerProductos();

        // Crear el modelo de la tabla
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID Producto", "Nombre"}, 0 // Encabezados de la tabla
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Hacer todas las celdas no editables
                return false;
            }
        };

        // Agregar los productos al modelo
        for (Object[] producto : productos) {
            model.addRow(producto);
        }

        // Asignar el modelo a la tabla
        ProductosTabla.setModel(model);

        // Ocultar la columna de ID (asumiendo que está en el índice 0)
        ocultarColumnaID(ProductosTabla, 0);

    } catch (Exception e) {
        // Mostrar mensaje de error si ocurre un problema
        JOptionPane.showMessageDialog(this, "Error al cargar los productos: " + e.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}




  private void setFechaActual() {
        LocalDate fechaActual = LocalDate.now(); // Obtiene la fecha actual
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD"); // Define el formato deseado
        FechaDelDia.setText(fechaActual.format(formatter)); // Establece la fecha en el JTextField
    }
   
// Constructor sin argumentos
public TablaDeCompraNueva() {
    this("UsuarioDesconocido"); // Llama al constructor principal con un valor por defecto
    
}

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Productos = new javax.swing.JScrollPane();
        ProductosTabla = new javax.swing.JTable();
        Carrito = new javax.swing.JScrollPane();
        CarritoTabla = new javax.swing.JTable();
        Fecha = new javax.swing.JLabel();
        FechaDelDia = new javax.swing.JTextField();
        QuitarDeLaCompra = new javax.swing.JButton();
        AgregarALaCompra = new javax.swing.JButton();
        Comprar = new javax.swing.JButton();
        ProveedoresLabe = new javax.swing.JLabel();
        Proveedores = new javax.swing.JComboBox<>();
        EstadoDeCompra = new javax.swing.JLabel();
        EstadosDeCompra = new javax.swing.JComboBox<>();
        UsuarioLable = new javax.swing.JLabel();
        Usuario = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage( getClass().getResource("/img/icono.png")));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Productos.setAutoscrolls(true);

        ProductosTabla.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ProductosTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "NOMBRE"
            }
        ));
        ProductosTabla.setCellSelectionEnabled(true);
        ProductosTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ProductosTablaMouseClicked(evt);
            }
        });
        ProductosTabla.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProductosTablaKeyPressed(evt);
            }
        });
        Productos.setViewportView(ProductosTabla);
        ProductosTabla.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jPanel1.add(Productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 640, 130));

        Carrito.setAutoscrolls(true);

        CarritoTabla.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        CarritoTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

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
        CarritoTabla.setCellSelectionEnabled(true);
        CarritoTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CarritoTablaMouseClicked(evt);
            }
        });
        CarritoTabla.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CarritoTablaKeyPressed(evt);
            }
        });
        Carrito.setViewportView(CarritoTabla);
        CarritoTabla.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jPanel1.add(Carrito, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 370, 130));

        Fecha.setText("FECHA DE COMPRA");
        jPanel1.add(Fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 140, 20));

        FechaDelDia.setEditable(false);
        FechaDelDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FechaDelDiaActionPerformed(evt);
            }
        });
        jPanel1.add(FechaDelDia, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 140, 30));

        QuitarDeLaCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/boton-menos.png"))); // NOI18N
        QuitarDeLaCompra.setBorder(null);
        QuitarDeLaCompra.setBorderPainted(false);
        QuitarDeLaCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QuitarDeLaCompraActionPerformed(evt);
            }
        });
        jPanel1.add(QuitarDeLaCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 300, 60, 50));

        AgregarALaCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/agregar (2).png"))); // NOI18N
        AgregarALaCompra.setBorder(null);
        AgregarALaCompra.setBorderPainted(false);
        AgregarALaCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarALaCompraActionPerformed(evt);
            }
        });
        jPanel1.add(AgregarALaCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 300, 60, 50));

        Comprar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/compra.png"))); // NOI18N
        Comprar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComprarActionPerformed(evt);
            }
        });
        jPanel1.add(Comprar, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 300, -1, -1));

        ProveedoresLabe.setText("PROVEEDOR");
        jPanel1.add(ProveedoresLabe, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, 90, 30));

        Proveedores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Proveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProveedoresActionPerformed(evt);
            }
        });
        jPanel1.add(Proveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, 130, 30));

        EstadoDeCompra.setText("ESTADO DE COMPRA");
        jPanel1.add(EstadoDeCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 260, 140, 30));

        EstadosDeCompra.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        EstadosDeCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EstadosDeCompraActionPerformed(evt);
            }
        });
        jPanel1.add(EstadosDeCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 260, 110, 30));

        UsuarioLable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/user.png"))); // NOI18N
        jPanel1.add(UsuarioLable, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 20, -1, -1));

        Usuario.setText("jLabel2");
        jPanel1.add(Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 60, 160, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ProductosTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProductosTablaMouseClicked
        
    }//GEN-LAST:event_ProductosTablaMouseClicked

    private void ProductosTablaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProductosTablaKeyPressed
      
    }//GEN-LAST:event_ProductosTablaKeyPressed

    private void CarritoTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CarritoTablaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_CarritoTablaMouseClicked

    private void CarritoTablaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CarritoTablaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CarritoTablaKeyPressed

    private void FechaDelDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FechaDelDiaActionPerformed
        setFechaActual();
       

        // TODO add your handling code here:
    }//GEN-LAST:event_FechaDelDiaActionPerformed

    private void AgregarALaCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarALaCompraActionPerformed
        agregarProductoAlCarrito(); // Llama al método 
        // TODO add your handling code here:
    }//GEN-LAST:event_AgregarALaCompraActionPerformed

    private void QuitarDeLaCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_QuitarDeLaCompraActionPerformed
        quitarProductoDelCarrito();
        // TODO add your handling code here:
    }//GEN-LAST:event_QuitarDeLaCompraActionPerformed

    private void ProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProveedoresActionPerformed
         
         filtrarProductosPorProveedor();
        // TODO add your handling code here:
    }//GEN-LAST:event_ProveedoresActionPerformed

    private void EstadosDeCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EstadosDeCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EstadosDeCompraActionPerformed

    private void ComprarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComprarActionPerformed
        try {
        DaoCompras dao = new DaoCompras();

        // Obtener y limpiar la entrada del usuario
        String empleado = Usuario.getText().trim().replaceAll("\\s+", " ");
        System.out.println("Empleado procesado para búsqueda: '" + empleado + "'");

        String fechaCompra = FechaDelDia.getText().trim();
        String estadoCompra = (String) EstadosDeCompra.getSelectedItem();
        String proveedor = (String) Proveedores.getSelectedItem();

        // Validar empleado
        String idEmpleado = dao.obtenerIdEmpleadoPorNombre(empleado);
        if (idEmpleado == null) {
            JOptionPane.showMessageDialog(this, "Error: No se encontró el empleado '" + empleado + "' en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener ID del proveedor
        String idProveedor = dao.obtenerIdProveedorPorNombre(proveedor);
        if (idProveedor == null) {
            JOptionPane.showMessageDialog(this, "Error: No se encontró el proveedor '" + proveedor + "' en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener ID del estado de compra
        String idEstadoCompra = dao.obtenerIdEstadoCompraPorNombre(estadoCompra);
        if (idEstadoCompra == null) {
            JOptionPane.showMessageDialog(this, "Error: No se encontró el estado de compra '" + estadoCompra + "' en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear compra y obtener el ID generado
        String idCompra = dao.crearCompra(idEmpleado, fechaCompra, idEstadoCompra, idProveedor);
        if (idCompra != null) {
            JOptionPane.showMessageDialog(this, "Compra creada exitosamente.");
            dispose();

            // Iterar sobre los productos seleccionados y agregar los detalles
            for (int i = 0; i < CarritoTabla.getRowCount(); i++) {
                String nombreProducto = CarritoTabla.getValueAt(i, 0).toString().trim(); // Columna de nombre del producto
                int cantidad = Integer.parseInt(CarritoTabla.getValueAt(i, 1).toString().trim()); // Columna de cantidad

                // Obtener el ID del producto
                String idProducto = dao.obtenerIdProductoPorNombre(nombreProducto);
                if (idProducto == null) {
                    System.out.println("Error: No se encontró el producto '" + nombreProducto + "' en la base de datos.");
                    continue;
                }

                // Insertar el detalle de la compra
                boolean detalleCreado = dao.crearDetalleCompra(idCompra, idProducto, cantidad);
                if (!detalleCreado) {
                    System.out.println("Error al insertar detalle para el producto: " + nombreProducto);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Error al crear la compra.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    }//GEN-LAST:event_ComprarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TablaDeCompraNueva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TablaDeCompraNueva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TablaDeCompraNueva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TablaDeCompraNueva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TablaDeCompraNueva().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AgregarALaCompra;
    private javax.swing.JScrollPane Carrito;
    private javax.swing.JTable CarritoTabla;
    private javax.swing.JButton Comprar;
    private javax.swing.JLabel EstadoDeCompra;
    private javax.swing.JComboBox<String> EstadosDeCompra;
    private javax.swing.JLabel Fecha;
    private javax.swing.JTextField FechaDelDia;
    private javax.swing.JScrollPane Productos;
    private javax.swing.JTable ProductosTabla;
    private javax.swing.JComboBox<String> Proveedores;
    private javax.swing.JLabel ProveedoresLabe;
    private javax.swing.JButton QuitarDeLaCompra;
    private javax.swing.JLabel Usuario;
    private javax.swing.JLabel UsuarioLable;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
