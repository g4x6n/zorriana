/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.vews;

import javax.swing.*;
import database.dao.DaoEstado;
/**
 *
 * @author Alex
 */
public class NuevoEstado extends javax.swing.JFrame {
    
    private final DaoEstado daoEstado = new DaoEstado();
    private final PrProveedor prProveedor;

    /**
     * Creates new form
     */
    public NuevoEstado(PrProveedor prProveedor) {
        this.prProveedor = prProveedor;
        setLocationRelativeTo(null); //
        initComponents();
        configComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
     
    }
    
    private void botonAceptar(){
       try {
            // Obtener el nombre de la marca ingresado por el usuario
            String nombreEstado = jTextField1.getText().trim();

            // Validar que no esté vacío
            if (nombreEstado.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, completa el campo obligatorio.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Llamar al método del DAO para insertar la marca
            String idEstado = daoEstado.insertarEstado(nombreEstado);

            // Verificar si la marca fue insertada correctamente
            if (idEstado != null) {
                JOptionPane.showMessageDialog(this, "Estado insertado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
                 
                prProveedor.cargarEstados();
                
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo insertar el estado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar el estado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
           dispose();
       }
    }
    

    
private void configComponents(){
        // Titulo de la ventana
        setTitle("Insertar Estado");
        // posición de la ventana
        setLocationRelativeTo(null);
    }
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        CANCELARBUTTON = new javax.swing.JButton();
        ACEPTARBUTTON = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage( getClass().getResource("/img/icono.png")));

        jLabel1.setText("NOMBRE:");

        CANCELARBUTTON.setText("CANCELAR");
        CANCELARBUTTON.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CANCELARBUTTONMouseClicked(evt);
            }
        });

        ACEPTARBUTTON.setText("ACEPTAR");
        ACEPTARBUTTON.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ACEPTARBUTTONMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ACEPTARBUTTON)
                .addGap(32, 32, 32)
                .addComponent(CANCELARBUTTON)
                .addGap(63, 63, 63))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CANCELARBUTTON)
                    .addComponent(ACEPTARBUTTON))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ACEPTARBUTTONMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ACEPTARBUTTONMouseClicked
        botonAceptar();
    }//GEN-LAST:event_ACEPTARBUTTONMouseClicked

    private void CANCELARBUTTONMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CANCELARBUTTONMouseClicked
       dispose();
    }//GEN-LAST:event_CANCELARBUTTONMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        PrProveedor prProveedor = new PrProveedor(); // Crea una instancia de PrProducto
        java.awt.EventQueue.invokeLater(() -> new NuevoEstado(prProveedor).setVisible(true));
   
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
            java.util.logging.Logger.getLogger(NuevoEstado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NuevoEstado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NuevoEstado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NuevoEstado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                PrProveedor prProveedor = new PrProveedor(); // Crear instancia de PrProducto
                new NuevoEstado(prProveedor).setVisible(true);  
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ACEPTARBUTTON;
    private javax.swing.JButton CANCELARBUTTON;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
