package com.vews;

import javax.swing.*;
import database.dao.DaoEstado;
import javax.swing.table.DefaultTableModel;
import java.util.List;


    public class TablaEstado extends javax.swing.JFrame {
    
    private final DaoEstado daoEstado = new DaoEstado();
    private final PrProveedor prProveedor;

    public TablaEstado(PrProveedor prProveedor) {
        this.prProveedor = prProveedor;
        setLocationRelativeTo(null); //
        initComponents();
        configComponents();
        cargarEstados();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }
    
     private void cargarEstados() {
        try {
            List<String> categorias = daoEstado.obtenerEstados();
            DefaultTableModel model = new DefaultTableModel(new String[]{"ESTADO"}, 0) {
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
            JOptionPane.showMessageDialog(this, "Error al cargar estados: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
     
      private void eliminarEstado() {
        int filaSeleccionada = resultsTable1.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un estado para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nombreEstado = resultsTable1.getValueAt(filaSeleccionada, 0).toString();
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar el estado \"" + nombreEstado + "\"?", "Confirmación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean exito = daoEstado.eliminarEstadoPorNombre(nombreEstado);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Estado eliminado correctamente.");
                cargarEstados();
                prProveedor.cargarEstados(); // Actualiza el combo box en la pantalla principal
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el estado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
     
    private void anadirEstado() {
        NuevoEstado ventanaEstado = new NuevoEstado(prProveedor);
        ventanaEstado.setVisible(true);
        // Esperar a que se cierre la ventana y luego actualizar la tabla
        ventanaEstado.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosed(java.awt.event.WindowEvent e) {
            cargarEstados(); // Actualizar la tabla de marcas
            cargarEstados(); // Actualizar la tabla de marcas
            prProveedor.cargarEstados(); // Actualizar también el comboBox en PrProducto
        }
    });
    }

    
  
    
private void configComponents(){
        // Titulo de la ventana
        setTitle("Estados");
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
        setResizable(false);

        CANCELARBUTTON.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/boton-menos.png"))); // NOI18N
        CANCELARBUTTON.setBorderPainted(false);
        CANCELARBUTTON.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CANCELARBUTTONMouseClicked(evt);
            }
        });

        ACEPTARBUTTON.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/agregar (2).png"))); // NOI18N
        ACEPTARBUTTON.setBorderPainted(false);
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
                "ESTADO"
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
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(ACEPTARBUTTON)
                        .addGap(18, 18, 18)
                        .addComponent(CANCELARBUTTON))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ACEPTARBUTTON)
                    .addComponent(CANCELARBUTTON))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ACEPTARBUTTONMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ACEPTARBUTTONMouseClicked
        anadirEstado();
    }//GEN-LAST:event_ACEPTARBUTTONMouseClicked

    private void CANCELARBUTTONMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CANCELARBUTTONMouseClicked
      eliminarEstado();
    }//GEN-LAST:event_CANCELARBUTTONMouseClicked

    private void resultsTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resultsTable1MouseClicked
       
    }//GEN-LAST:event_resultsTable1MouseClicked

    private void resultsTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_resultsTable1KeyPressed
       
    }//GEN-LAST:event_resultsTable1KeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        PrProveedor prProveedor = new PrProveedor(); // Crea una instancia de PrProducto
        java.awt.EventQueue.invokeLater(() -> new TablaEstado(prProveedor).setVisible(true));
   
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
            java.util.logging.Logger.getLogger(TablaEstado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TablaEstado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TablaEstado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TablaEstado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                PrProveedor prProveedor = new PrProveedor(); // Crear instancia de PrProducto
                TablaEstado tablaEstado = new TablaEstado(prProveedor); // Pasar la instancia de PrProducto
                tablaEstado.setVisible(true); // Mostrar la ventana 
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
