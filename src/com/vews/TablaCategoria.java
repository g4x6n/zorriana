/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.vews;

import javax.swing.*;
import database.dao.DaoCategoria;
import javax.swing.table.DefaultTableModel;
import java.util.List;
/**
 *
 * @author Alex
 */
public class TablaCategoria extends javax.swing.JFrame {
    
    private final DaoCategoria daoCategoria = new DaoCategoria();
    private final PrProducto prProducto;

    /**
     * Creates new form
     */
    public TablaCategoria(PrProducto prProducto) {
        this.prProducto = prProducto;
        setLocationRelativeTo(null); //
        initComponents();
        configComponents();
        cargarCategorias();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
     
    }
    
     private void cargarCategorias() {
        try {
            List<String> categorias = daoCategoria.obtenerCategoria();
            DefaultTableModel model = new DefaultTableModel(new String[]{"CATEGORIA"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            for (String categoria : categorias) {
                model.addRow(new Object[]{categoria});
            }
            resultsTable1.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar categorias: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
     
     private void eliminarCategoria() {
        int filaSeleccionada = resultsTable1.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una categoria para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String nombreMarca = resultsTable1.getValueAt(filaSeleccionada, 0).toString();
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar la categoria \" " + nombreMarca + "\"?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean exito = daoCategoria.eliminarCategoriaPorNombre(nombreMarca);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Categoria eliminada correctamente.");
                cargarCategorias();
                prProducto.cargarCategoria(); // Actualiza el combo box en la pantalla principal
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar la categoria.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
     
     private void anadirCategoria() {
        NuevaCategoria ventanaCategoria = new NuevaCategoria(prProducto);
        ventanaCategoria.setVisible(true);
        // Esperar a que se cierre la ventana y luego actualizar la tabla
        ventanaCategoria.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosed(java.awt.event.WindowEvent e) {
            cargarCategorias(); // Actualizar la tabla de marcas
            cargarCategorias(); // Actualizar la tabla de marcas
            prProducto.cargarCategoria(); // Actualizar también el comboBox en PrProducto
        }
    });
    }

    
  
    
private void configComponents(){
        // Titulo de la ventana
        setTitle("Insertar Categoria");
        // posición de la ventana
        setLocationRelativeTo(null);
    }
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        CANCELARBUTTON = new javax.swing.JButton();
        ACEPTARBUTTON = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        resultsTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage( getClass().getResource("/img/icono.png")));

        CANCELARBUTTON.setText("ELIMINAR");
        CANCELARBUTTON.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CANCELARBUTTONMouseClicked(evt);
            }
        });

        ACEPTARBUTTON.setText("AÑADIR");
        ACEPTARBUTTON.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ACEPTARBUTTONMouseClicked(evt);
            }
        });

        jScrollPane2.setAutoscrolls(true);

        resultsTable1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        resultsTable1.setModel(new javax.swing.table.DefaultTableModel(
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
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "MARCA "
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(ACEPTARBUTTON)
                        .addGap(32, 32, 32)
                        .addComponent(CANCELARBUTTON))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CANCELARBUTTON)
                    .addComponent(ACEPTARBUTTON))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ACEPTARBUTTONMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ACEPTARBUTTONMouseClicked
        anadirCategoria();
    }//GEN-LAST:event_ACEPTARBUTTONMouseClicked

    private void CANCELARBUTTONMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CANCELARBUTTONMouseClicked
       eliminarCategoria();
    }//GEN-LAST:event_CANCELARBUTTONMouseClicked

    private void resultsTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resultsTable1MouseClicked
       
    }//GEN-LAST:event_resultsTable1MouseClicked

    private void resultsTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_resultsTable1KeyPressed
       
    }//GEN-LAST:event_resultsTable1KeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        PrProducto prProducto = new PrProducto(); // Crea una instancia de PrProducto
        java.awt.EventQueue.invokeLater(() -> new TablaCategoria(prProducto).setVisible(true));
   
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
            java.util.logging.Logger.getLogger(TablaCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TablaCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TablaCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TablaCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PrProducto prProducto = new PrProducto(); // Crear instancia de PrProducto
                TablaCategoria tablaCategoria = new TablaCategoria(prProducto); // Pasar la instancia de PrProducto
                tablaCategoria.setVisible(true); // Mostrar la ventana 
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ACEPTARBUTTON;
    private javax.swing.JButton CANCELARBUTTON;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable resultsTable1;
    // End of variables declaration//GEN-END:variables
}
