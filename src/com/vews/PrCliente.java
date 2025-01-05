/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.vews;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.JTable;
import database.dao.DaoClientes;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Alex
 */
public class PrCliente extends javax.swing.JPanel {

    public static PrCliente cl;

    public PrCliente() {
        initComponents(); // Inicializa los componentes del formulario
        cargarEstados();
        GUARDAR_BOTON.setEnabled(false);// Llama al método para cargar los estados en el ComboBox
        cargarClientes();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        NOMBRE = new javax.swing.JLabel();
        AP_PATERNO = new javax.swing.JLabel();
        AP_MATERNO = new javax.swing.JLabel();
        FECHA_REG = new javax.swing.JLabel();
        CORREO = new javax.swing.JLabel();
        DIRECCIÓN = new javax.swing.JLabel();
        jTextFieldNombre = new javax.swing.JTextField();
        jTextFieldApPaterno = new javax.swing.JTextField();
        jTextFieldApMaterno = new javax.swing.JTextField();
        jTextFieldFechaReg = new javax.swing.JTextField();
        jTextFieldCorreo = new javax.swing.JTextField();
        searchbar = new javax.swing.JTextField();
        FILTRO = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        resultsTable = new javax.swing.JTable();
        BUSCAR = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        CALLE = new javax.swing.JLabel();
        EXTERIOR = new javax.swing.JLabel();
        jTextFieldExterior = new javax.swing.JTextField();
        jTextFieldCalle = new javax.swing.JTextField();
        INTERIOR = new javax.swing.JLabel();
        jTextFieldInterior = new javax.swing.JTextField();
        COLONIA = new javax.swing.JLabel();
        jTextFieldColonia = new javax.swing.JTextField();
        DELEGACIÓN = new javax.swing.JLabel();
        jTextFieldAlcalMun = new javax.swing.JTextField();
        CP = new javax.swing.JLabel();
        jTextFieldCP = new javax.swing.JTextField();
        AGREGAR_BOTON = new javax.swing.JButton();
        GUARDAR_BOTON = new javax.swing.JButton();
        EDITAR_BOTON = new javax.swing.JButton();
        ELIMINAR_BOTON = new javax.swing.JButton();
        ESTADO = new javax.swing.JLabel();
        jComboBoxEstado = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(940, 570));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(1040, 560));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        NOMBRE.setText("NOMBRE");
        jPanel1.add(NOMBRE, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        AP_PATERNO.setText("APELLIDO PATERNO");
        jPanel1.add(AP_PATERNO, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, 20));

        AP_MATERNO.setText("APELLIDO MATERNO");
        jPanel1.add(AP_MATERNO, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        FECHA_REG.setText("FECHA DE REGISTRO");
        jPanel1.add(FECHA_REG, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, -1));

        CORREO.setText("CORREO");
        jPanel1.add(CORREO, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, -1, -1));

        DIRECCIÓN.setFont(new java.awt.Font("Jost", 0, 12)); // NOI18N
        DIRECCIÓN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DIRECCIÓN.setText("DIRECCIÓN");
        jPanel1.add(DIRECCIÓN, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 70, -1));

        jTextFieldNombre.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextFieldNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNombreActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, 320, -1));

        jTextFieldApPaterno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldApPaternoActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldApPaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, 260, -1));

        jTextFieldApMaterno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldApMaternoActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldApMaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 200, 260, -1));

        jTextFieldFechaReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFechaRegActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldFechaReg, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 240, 250, -1));

        jTextFieldCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCorreoActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 280, 320, -1));

        searchbar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchbarActionPerformed(evt);
            }
        });
        searchbar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchbarKeyPressed(evt);
            }
        });
        jPanel1.add(searchbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 200, 280, -1));

        FILTRO.setText("FILTRAR:");
        jPanel1.add(FILTRO, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 200, -1, -1));

        resultsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "NOMBRE", "AP PAT", "AP MAT", "FECHA REG", "CORREO", "DIRECCIÓN"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        resultsTable.setCellSelectionEnabled(true);
        resultsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resultsTableMouseClicked(evt);
            }
        });
        resultsTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                resultsTableKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(resultsTable);
        resultsTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 230, 450, 250));

        BUSCAR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        BUSCAR.setBorder(null);
        BUSCAR.setBorderPainted(false);
        BUSCAR.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BUSCAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BUSCARActionPerformed(evt);
            }
        });
        jPanel1.add(BUSCAR, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 190, -1, -1));

        jLabel8.setFont(new java.awt.Font("Jost", 0, 48)); // NOI18N
        jLabel8.setText("CLIENTE");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, 210, 60));

        CALLE.setText("CALLE");
        jPanel1.add(CALLE, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, -1, -1));

        EXTERIOR.setText("EXTERIOR");
        jPanel1.add(EXTERIOR, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, -1, -1));

        jTextFieldExterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldExteriorActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldExterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 390, 70, -1));

        jTextFieldCalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCalleActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldCalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 360, 330, -1));

        INTERIOR.setText("INTERIOR");
        jPanel1.add(INTERIOR, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 390, -1, -1));

        jTextFieldInterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldInteriorActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldInterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 390, 70, -1));

        COLONIA.setText("COLONIA");
        jPanel1.add(COLONIA, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, -1, -1));

        jTextFieldColonia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldColoniaActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldColonia, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 430, 140, -1));

        DELEGACIÓN.setText("ALCAL/MUN");
        jPanel1.add(DELEGACIÓN, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 430, -1, -1));

        jTextFieldAlcalMun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAlcalMunActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldAlcalMun, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 430, 110, -1));

        CP.setText("C.P.");
        jPanel1.add(CP, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 390, -1, -1));

        jTextFieldCP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCPActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldCP, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 390, 60, 30));

        AGREGAR_BOTON.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/añadir cpem.png"))); // NOI18N
        AGREGAR_BOTON.setContentAreaFilled(false);
        AGREGAR_BOTON.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AGREGAR_BOTON.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AGREGAR_BOTONActionPerformed(evt);
            }
        });
        jPanel1.add(AGREGAR_BOTON, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 130, -1, -1));

        GUARDAR_BOTON.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/guardar.png"))); // NOI18N
        GUARDAR_BOTON.setContentAreaFilled(false);
        GUARDAR_BOTON.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GUARDAR_BOTON.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GUARDAR_BOTONActionPerformed(evt);
            }
        });
        jPanel1.add(GUARDAR_BOTON, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 130, -1, -1));

        EDITAR_BOTON.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/editar usuari.png"))); // NOI18N
        EDITAR_BOTON.setContentAreaFilled(false);
        EDITAR_BOTON.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EDITAR_BOTON.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EDITAR_BOTONActionPerformed(evt);
            }
        });
        jPanel1.add(EDITAR_BOTON, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 130, -1, -1));

        ELIMINAR_BOTON.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eliminar.png"))); // NOI18N
        ELIMINAR_BOTON.setContentAreaFilled(false);
        ELIMINAR_BOTON.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ELIMINAR_BOTON.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ELIMINAR_BOTONActionPerformed(evt);
            }
        });
        jPanel1.add(ELIMINAR_BOTON, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 130, -1, -1));

        ESTADO.setText("ESTADO");
        jPanel1.add(ESTADO, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, -1, -1));

        jComboBoxEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxEstado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(jComboBoxEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 470, 110, -1));

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 470, -1, -1));

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 900, 530));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleParent(this);
    }// </editor-fold>//GEN-END:initComponents

    
private final DaoClientes daoCliente = new DaoClientes(); // Instancia de DaoCliente

private void cargarEstados() {
    try {
        List<String> estados = daoCliente.obtenerEstados(); // Obtener la lista de estados desde la base de datos
        jComboBoxEstado.removeAllItems(); // Limpiar los elementos actuales del ComboBox
        for (String estado : estados) {
            jComboBoxEstado.addItem(estado.trim()); // Agregar cada estado al ComboBox
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al cargar estados: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void manejarTablaCliente() {
        // Obtener la fila seleccionada
    int filaSeleccionada = resultsTable.getSelectedRow();
if (filaSeleccionada != -1) {
    // Obtener el ID del cliente desde la tabla
    String idCliente = resultsTable.getValueAt(filaSeleccionada, 0).toString();

    // Llamar al DAO para obtener los detalles completos del cliente
    Object[] cliente = daoCliente.obtenerClientePorId(idCliente);

    if (cliente != null) {
        // Asignar valores a los campos del formulario
        jTextFieldNombre.setText((String) cliente[1]); // Nombre
        jTextFieldApPaterno.setText((String) cliente[2]); // Apellido Paterno
        jTextFieldApMaterno.setText((String) cliente[3]); // Apellido Materno
        jTextFieldFechaReg.setText(cliente[4] != null ? cliente[4].toString() : ""); // Fecha de Registro
        jTextFieldCorreo.setText((String) cliente[5]); // Correo

        // Asignar dirección
        jTextFieldCalle.setText((String) cliente[6]); // Calle
        jTextFieldExterior.setText((String) cliente[7]); // Exterior
        jTextFieldInterior.setText((String) cliente[8]); // Interior
        jTextFieldColonia.setText((String) cliente[9]); // Colonia
        jTextFieldCP.setText((String) cliente[10]); // CP
        jTextFieldAlcalMun.setText((String) cliente[11]); // Alcaldía/Municipio
        jComboBoxEstado.setSelectedItem((String) cliente[12]); // Estado

       
    } else {
        JOptionPane.showMessageDialog(this, "No se pudo cargar la información del cliente.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

   }

private void BuscarCliente() {
    String filtro = searchbar.getText().trim(); // Obtén el texto de la barra de búsqueda
GUARDAR_BOTON.setEnabled(false);
if (filtro.isEmpty()) {
    JOptionPane.showMessageDialog(this, "Por favor, ingresa un criterio de búsqueda.", "Advertencia", JOptionPane.WARNING_MESSAGE);
    return;
}

try {
    // Llama al método de búsqueda en el DAO de clientes
    DaoClientes daoCliente = new DaoClientes();
    List<Object[]> resultados = daoCliente.buscarClientes(filtro);

    // Configura el modelo de la tabla
    DefaultTableModel model = new DefaultTableModel(
        new String[]{"ID", "Nombre", "Apellido P", "Apellido M", "Fecha de Reg", "Correo", "Dirección"}, 0
    ){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Ninguna celda será editable
                return false;
            }
        };

    // Llena la tabla con los resultados
    for (Object[] fila : resultados) {
        model.addRow(new Object[]{
            fila[0], fila[1], fila[2], fila[3], fila[4], fila[5], fila[6]
        });
    }

    resultsTable.setModel(model);
    
    configurarColumnasTabla(resultsTable);

    // Verifica si no hay resultados
    if (resultados.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No se encontraron clientes con el criterio ingresado.", "Información", JOptionPane.INFORMATION_MESSAGE);
    }
} catch (Exception ex) {
    JOptionPane.showMessageDialog(this, "Error al realizar la búsqueda: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    ex.printStackTrace();
}

}


    private void jTextFieldNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNombreActionPerformed
    JTextField jTextFieldNombre = new JTextField();
    jTextFieldNombre.setColumns(20);

    }//GEN-LAST:event_jTextFieldNombreActionPerformed

    private void jTextFieldApPaternoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldApPaternoActionPerformed
    JTextField jTextFieldNombre = new JTextField();
    jTextFieldNombre.setColumns(20);


    }//GEN-LAST:event_jTextFieldApPaternoActionPerformed

    private void jTextFieldApMaternoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldApMaternoActionPerformed

    JTextField jTextFieldNombre = new JTextField();
    jTextFieldNombre.setColumns(20);
    }//GEN-LAST:event_jTextFieldApMaternoActionPerformed

    private void jTextFieldFechaRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFechaRegActionPerformed

    JTextField jTextFieldNombre = new JTextField();
    jTextFieldNombre.setColumns(20);
    }//GEN-LAST:event_jTextFieldFechaRegActionPerformed

    private void jTextFieldCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCorreoActionPerformed
    JTextField jTextFieldNombre = new JTextField();
    jTextFieldNombre.setColumns(20);
    }//GEN-LAST:event_jTextFieldCorreoActionPerformed

    private void searchbarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchbarActionPerformed
        
    }//GEN-LAST:event_searchbarActionPerformed
    private void cargarEstado() {
        try {
            // Llama al método de DaoCliente para obtener la lista de estados
            List<String> estados = daoCliente.obtenerEstados();

            // Limpia el ComboBox antes de agregar nuevos elementos
            jComboBoxEstado.removeAllItems();

            // Agregar los estados al ComboBox
            for (String estado : estados) {
                jComboBoxEstado.addItem(estado);
            }

            // Selecciona el primer estado por defecto si hay estados disponibles
            if (!estados.isEmpty()) {
                jComboBoxEstado.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontraron estados para mostrar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar estados: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
}
    private void limpiarCampos() {
    jTextFieldNombre.setText("");
    jTextFieldApPaterno.setText("");
    jTextFieldApMaterno.setText("");
    jTextFieldCorreo.setText("");
    jTextFieldFechaReg.setText("");
    jTextFieldCalle.setText("");
    jTextFieldExterior.setText("");
    jTextFieldInterior.setText("");
    jTextFieldColonia.setText("");
    jTextFieldCP.setText("");
    jTextFieldAlcalMun.setText("");
    jComboBoxEstado.setSelectedIndex(0); // Restablecer a "Seleccionar Estado"
}

private void eliminarCliente() {
    try {
        // Verifica si hay un cliente seleccionado
        int filaSeleccionada = resultsTable.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un cliente en la tabla para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtén el ID del cliente seleccionado
        String idCliente = resultsTable.getValueAt(filaSeleccionada, 0).toString();

        // Confirmación antes de eliminar
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar al cliente?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return; // El usuario canceló
        }

        // Llama al método para eliminar cliente en el DAO
        boolean exito = daoCliente.deleteClient(idCliente);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.");
            cargarClientes(); // Recargar tabla de clientes
            limpiarCampos(); // Limpia los cuadros de texto
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al eliminar cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}





    private void resultsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resultsTableMouseClicked
        manejarTablaCliente();
    }//GEN-LAST:event_resultsTableMouseClicked

    private void BUSCARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BUSCARActionPerformed
      BuscarCliente();
} 

// Método para mostrar un solo cliente (puedes adaptar esto a tu interfaz)
private void mostrarCliente(Object[] cliente) {
    // Mostrar un mensaje con los datos del cliente, por ejemplo:
    String clienteInfo = "Nombre: " + cliente[1] + " " + cliente[2] + " " + cliente[3] +
                         "\nCorreo: " + cliente[5] + "\nDirección: " + cliente[6];
    JOptionPane.showMessageDialog(this, clienteInfo, "Cliente Encontrado", JOptionPane.INFORMATION_MESSAGE);

} 

// Método para mostrar los resultados en una tabla o en otro componente
private void mostrarResultados(List<Object[]> resultados) {
    // Establecer el modelo de la tabla sin la columna "DIRECCIÓN"
    DefaultTableModel model = new DefaultTableModel(
        new Object [][] {}, // Inicializar la tabla vacía
        new String [] {
            "NOMBRE", "AP PAT", "AP MAT", "FECHA REG", "CORREO"
        }
    ) {
        boolean[] canEdit = new boolean [] {
            false, false, false, false, false
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit[columnIndex];
        }
    };

    // Limpiar la tabla antes de agregar los nuevos resultados
    model.setRowCount(0);

    // Recorrer los resultados y agregar cada cliente a la tabla
    for (Object[] cliente : resultados) {
        model.addRow(cliente); // Agregar los datos del cliente a la tabla
    }

    // Establecer el modelo actualizado a la JTable
    resultsTable.setModel(model);

    }//GEN-LAST:event_BUSCARActionPerformed

    private void jTextFieldExteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldExteriorActionPerformed
     JTextField jTextFieldNombre = new JTextField();
        jTextFieldNombre.setColumns(20);
    }//GEN-LAST:event_jTextFieldExteriorActionPerformed

    private void jTextFieldColoniaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldColoniaActionPerformed
        JTextField jTextFieldNombre = new JTextField();
    jTextFieldNombre.setColumns(20);
    }//GEN-LAST:event_jTextFieldColoniaActionPerformed

private void cargarClientes() {
    try {
        // Instancia del DAO para obtener los clientes
        DaoClientes daoCliente = new DaoClientes();

        // Obtener todos los clientes (puedes modificar el filtro si lo necesitas)
        List<Object[]> clientes = daoCliente.buscarClientes("");

        // Crear un modelo para la tabla
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Apellido P", "Apellido M", "Fecha de Reg", "Correo", "Dirección"}, 0
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Ninguna celda será editable
                return false;
            }
        };

        // Agregar cada cliente al modelo
        for (Object[] cliente : clientes) {
            model.addRow(cliente);
        }

        // Establecer el modelo en la tabla
        resultsTable.setModel(model);
        
        configurarColumnasTabla(resultsTable);
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

private void configurarColumnasTabla(JTable table) {
    // Definir anchos preferidos y mínimos para las columnas
    int[] anchos = {10, 100, 100, 100, 150, 300}; // Ajusta estos valores según el contenido esperado
    for (int i = 0; i < anchos.length; i++) {
        table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        table.getColumnModel().getColumn(i).setMinWidth(anchos[i]);
    }
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
}

private Object[] obtenerDatosCliente() {
    try {
        // Obtener valores del formulario
        String nombre = jTextFieldNombre.getText().trim();
        String apPaterno = jTextFieldApPaterno.getText().trim();
        String apMaterno = jTextFieldApMaterno.getText().trim();
        String correo = jTextFieldCorreo.getText().trim();
        String fechaReg = jTextFieldFechaReg.getText().trim();
        String calle = jTextFieldCalle.getText().trim();
        String exterior = jTextFieldExterior.getText().trim();
        String interior = jTextFieldInterior.getText().trim();
        String colonia = jTextFieldColonia.getText().trim();
        String cp = jTextFieldCP.getText().trim();
        String alcalMun = jTextFieldAlcalMun.getText().trim();
        String estado = (String) jComboBoxEstado.getSelectedItem();

        // Validar campos obligatorios
        if (nombre.isEmpty() || apPaterno.isEmpty() || correo.isEmpty() || fechaReg.isEmpty() || estado == null) {
            throw new IllegalArgumentException("Por favor, completa todos los campos obligatorios.");
        }

        // Retornar los datos del cliente como un arreglo
        return new Object[]{
            nombre, apPaterno, apMaterno, fechaReg, correo, calle, exterior, interior, colonia, cp, alcalMun, estado
        };
    } catch (IllegalArgumentException ex) {
        // Mostrar error si hay campos vacíos o inválidos
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        return null;
    }
}

private void setFechaActual() {
    // Obtener la fecha actual
    LocalDate now = LocalDate.now();

    // Formatear la fecha en el formato "YYYY-MM-DD"
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String fechaActual = now.format(formatter);

    // Establecer la fecha en el campo correspondiente
    jTextFieldFechaReg.setText(fechaActual);
}


    private void EDITAR_BOTONActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EDITAR_BOTONActionPerformed
        int filaSeleccionada = resultsTable.getSelectedRow();
    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this, "Selecciona un cliente de la tabla para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }
    GUARDAR_BOTON.setEnabled(true); // Activar el botón "Guardar"
    }//GEN-LAST:event_EDITAR_BOTONActionPerformed

    private void jTextFieldCalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCalleActionPerformed
    JTextField jTextFieldNombre = new JTextField();
    jTextFieldNombre.setColumns(20);
    }//GEN-LAST:event_jTextFieldCalleActionPerformed

    private void GUARDAR_BOTONActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GUARDAR_BOTONActionPerformed
        System.out.println("Botón Guardar presionado");
try {
    if (datosConfirmados == null) {
        JOptionPane.showMessageDialog(this, "No hay datos confirmados para guardar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Verificar si se trata de un cliente nuevo o uno existente
    boolean esNuevoCliente = resultsTable.getSelectedRow() == -1;

    if (esNuevoCliente) {
        // Validar estado seleccionado
        String estado = jComboBoxEstado.getSelectedItem().toString().trim();
        if (estado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona un estado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener los datos confirmados
        String nombre = (String) datosConfirmados[0];
        String apPaterno = (String) datosConfirmados[1];
        String apMaterno = (String) datosConfirmados[2];
        String fechaReg = (String) datosConfirmados[3];
        String correo = (String) datosConfirmados[4];
        String calle = (String) datosConfirmados[5];
        String exterior = (String) datosConfirmados[6];
        String interior = (String) datosConfirmados[7];
        String colonia = (String) datosConfirmados[8];
        String cp = (String) datosConfirmados[9];
        String alcalMun = (String) datosConfirmados[10];

        // Obtener el ID del estado
        String idEstado = daoCliente.obtenerCodigoEstado(estado);
        if (idEstado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Estado no válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar si la dirección ya existe
        System.out.println("Datos para insertar dirección:");
        System.out.println("Calle: " + calle);
        System.out.println("Exterior: " + exterior);
        System.out.println("Interior: " + interior);
        System.out.println("Colonia: " + colonia);
        System.out.println("CP: " + cp);
        System.out.println("Alcal/Mun: " + alcalMun);
        System.out.println("ID Estado: " + idEstado);

        String idDireccion = daoCliente.obtenerIdDireccion(calle, exterior, interior, colonia, cp, alcalMun, idEstado);
        if (idDireccion == null) {
            // Insertar nueva dirección si no existe
            idDireccion = daoCliente.insertarDireccion(calle, exterior, interior, colonia, cp, alcalMun, idEstado);
            if (idDireccion == null) {
                JOptionPane.showMessageDialog(this, "Error al guardar la dirección.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Añadir cliente
        boolean exito = daoCliente.insertarCliente(nombre, apPaterno, apMaterno, fechaReg, correo, idDireccion);
        if (exito) {
            JOptionPane.showMessageDialog(this, "Cliente añadido correctamente.");
        } else {
            JOptionPane.showMessageDialog(this, "Error al añadir el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        // Actualizar un cliente existente
        String idCliente = resultsTable.getValueAt(resultsTable.getSelectedRow(), 0).toString();
        Object[] clienteEditado = new Object[]{
            datosConfirmados[0], datosConfirmados[1], datosConfirmados[2],
            datosConfirmados[3], datosConfirmados[4], idCliente // Agrega el ID para la actualización
        };

        boolean exito = daoCliente.updateClient(clienteEditado);
        if (exito) {
            JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.");
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Limpiar y bloquear campos después de guardar
    limpiarCampos();
    GUARDAR_BOTON.setEnabled(false); // Desactivar el botón Guardar
    datosConfirmados = null; // Limpiar datos confirmados
    cargarClientes(); // Recargar la tabla de clientes

} catch (Exception e) {
    JOptionPane.showMessageDialog(this, "Error al guardar cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    e.printStackTrace();
}


    }//GEN-LAST:event_GUARDAR_BOTONActionPerformed

    private void ELIMINAR_BOTONActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ELIMINAR_BOTONActionPerformed

    eliminarCliente(); // Llama al método para eliminar cliente

    }//GEN-LAST:event_ELIMINAR_BOTONActionPerformed

    private void jTextFieldInteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldInteriorActionPerformed
    JTextField jTextFieldNombre = new JTextField();
    jTextFieldNombre.setColumns(20);
    }//GEN-LAST:event_jTextFieldInteriorActionPerformed

    private void jTextFieldCPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCPActionPerformed
    JTextField jTextFieldNombre = new JTextField();
    jTextFieldNombre.setColumns(20);
    }//GEN-LAST:event_jTextFieldCPActionPerformed

    private void jTextFieldAlcalMunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAlcalMunActionPerformed
       JTextField jTextFieldNombre = new JTextField();
    jTextFieldNombre.setColumns(20);
    }//GEN-LAST:event_jTextFieldAlcalMunActionPerformed
private Object[] datosConfirmados = null;
    private void AGREGAR_BOTONActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AGREGAR_BOTONActionPerformed
       
    }//GEN-LAST:event_AGREGAR_BOTONActionPerformed

    private void resultsTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_resultsTableKeyPressed
     if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
        manejarTablaCliente();
        evt.consume();
    }
    }//GEN-LAST:event_resultsTableKeyPressed

    private void searchbarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchbarKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
        BuscarCliente();
        evt.consume();
    }
    }//GEN-LAST:event_searchbarKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        limpiarCampos();
        setFechaActual();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AGREGAR_BOTON;
    private javax.swing.JLabel AP_MATERNO;
    private javax.swing.JLabel AP_PATERNO;
    private javax.swing.JButton BUSCAR;
    private javax.swing.JLabel CALLE;
    private javax.swing.JLabel COLONIA;
    private javax.swing.JLabel CORREO;
    private javax.swing.JLabel CP;
    private javax.swing.JLabel DELEGACIÓN;
    private javax.swing.JLabel DIRECCIÓN;
    private javax.swing.JButton EDITAR_BOTON;
    private javax.swing.JButton ELIMINAR_BOTON;
    private javax.swing.JLabel ESTADO;
    private javax.swing.JLabel EXTERIOR;
    private javax.swing.JLabel FECHA_REG;
    private javax.swing.JLabel FILTRO;
    private javax.swing.JButton GUARDAR_BOTON;
    private javax.swing.JLabel INTERIOR;
    private javax.swing.JLabel NOMBRE;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBoxEstado;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldAlcalMun;
    private javax.swing.JTextField jTextFieldApMaterno;
    private javax.swing.JTextField jTextFieldApPaterno;
    private javax.swing.JTextField jTextFieldCP;
    private javax.swing.JTextField jTextFieldCalle;
    private javax.swing.JTextField jTextFieldColonia;
    private javax.swing.JTextField jTextFieldCorreo;
    private javax.swing.JTextField jTextFieldExterior;
    private javax.swing.JTextField jTextFieldFechaReg;
    private javax.swing.JTextField jTextFieldInterior;
    private javax.swing.JTextField jTextFieldNombre;
    private javax.swing.JTable resultsTable;
    private javax.swing.JTextField searchbar;
    // End of variables declaration//GEN-END:variables

    public void setLocation(double d) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
