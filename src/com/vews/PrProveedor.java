package com.vews;
import database.dao.DaoProveedor;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

public class PrProveedor extends javax.swing.JPanel {

    private DaoProveedor daoProveedor = new DaoProveedor();
    
    
    public static PrProveedor cl;

    public PrProveedor() {
        initComponents();
        cargarEstados();
        cargarProveedores();
    }

    // Cargar los estados en el combo box
    private void cargarEstados() {
        try {
            List<String> estados = daoProveedor.obtenerEstados();
            Est_Box.removeAllItems();
            for (String estado : estados) {
                Est_Box.addItem(estado);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar estados: " + e.getMessage());
        }
    }

    // Cargar los proveedores en la tabla
    private void cargarProveedores() {
        try {
            List<Object[]> proveedores = daoProveedor.listProveedores();
            DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID", "Nombre Empresa", "Contacto", "LADA", "Teléfono", "Dirección"}, 0
            );
            for (Object[] proveedor : proveedores) {
                model.addRow(proveedor);
            }
            resultsTable2.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar proveedores: " + e.getMessage());
        }
    }

    // Agregar un nuevo proveedor
    private void agregarProveedor() {
        try {
            Object[] proveedor = obtenerDatosProveedor();
            if (proveedor == null) return;

            boolean exito = daoProveedor.addProveedor(proveedor);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Proveedor agregado correctamente.");
                cargarProveedores();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar proveedor.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar proveedor: " + e.getMessage());
        }
    }

    // Editar un proveedor existente
    private void editarProveedor() {
        try {
            int filaSeleccionada = resultsTable2.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un proveedor para editar.");
                return;
            }

            Object[] proveedor = obtenerDatosProveedor();
            if (proveedor == null) return;

            boolean exito = daoProveedor.updateProveedor(proveedor);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Proveedor editado correctamente.");
                cargarProveedores();
            } else {
                JOptionPane.showMessageDialog(this, "Error al editar proveedor.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al editar proveedor: " + e.getMessage());
        }
    }

    // Eliminar proveedor seleccionado
    private void eliminarProveedor() {
        try {
            int filaSeleccionada = resultsTable2.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un proveedor para eliminar.");
                return;
            }

            String idProveedor = resultsTable2.getValueAt(filaSeleccionada, 0).toString();
            String idDireccion = daoProveedor.obtenerIdDireccionPorProveedor(idProveedor);

            if (daoProveedor.deleteProveedor(idProveedor, idDireccion)) {
                JOptionPane.showMessageDialog(this, "Proveedor eliminado correctamente.");
                cargarProveedores();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar proveedor.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar proveedor: " + e.getMessage());
        }
    }

    // Buscar proveedores por filtro
    private void buscarProveedor() {
        try {
            String filtro = FiltrarTxtF.getText().trim();
            if (filtro.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingresa un criterio de búsqueda.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            List<Object[]> proveedores = daoProveedor.buscarProveedor(filtro);
            DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID", "Nombre Empresa", "Contacto", "LADA", "Teléfono", "Dirección"}, 0
            );
            for (Object[] proveedor : proveedores) {
                model.addRow(proveedor);
            }
            resultsTable2.setModel(model);

            if (proveedores.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron resultados.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al buscar proveedor: " + e.getMessage());
        }
    }

    // Obtener datos del formulario
    private Object[] obtenerDatosProveedor() {
        try {
            String nombreEmpresa = NombreTxtF.getText().trim();
            String nombreContacto = NContTxtF.getText().trim();
            String lada = LadaTxtF.getText().trim();
            String telefono = TelTxtF.getText().trim();
            String extension = ExtTxtF.getText().trim();
            String correo = CorreoTxtF.getText().trim();
            String calle = CalleTxtF.getText().trim();
            String exterior = ExteriorTxtF.getText().trim();
            String interior = InteriorTxtF.getText().trim();
            String colonia = ColoniaTxtF.getText().trim();
            String cp = CPTxtF.getText().trim();
            String alcalMun = AlcalMunTxtF.getText().trim();
            String estado = (String) Est_Box.getSelectedItem();

            if (nombreEmpresa.isEmpty() || nombreContacto.isEmpty() || telefono.isEmpty() || correo.isEmpty() || calle.isEmpty() || colonia.isEmpty() || cp.isEmpty() || alcalMun.isEmpty() || estado.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, llena todos los campos obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return null;
            }

            return new Object[]{nombreEmpresa, nombreContacto, lada, telefono, extension, correo, calle, exterior, interior, colonia, cp, alcalMun, estado};
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al obtener datos del formulario: " + e.getMessage());
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        fondo = new javax.swing.JPanel();
        PROVEEDOR = new javax.swing.JLabel();
        productos = new javax.swing.JPanel();
        Nombre = new javax.swing.JLabel();
        NombreTxtF = new javax.swing.JTextField();
        NomContacto = new javax.swing.JLabel();
        NContTxtF = new javax.swing.JTextField();
        Lada = new javax.swing.JLabel();
        LadaTxtF = new javax.swing.JTextField();
        Telefono = new javax.swing.JLabel();
        TelTxtF = new javax.swing.JTextField();
        Extension = new javax.swing.JLabel();
        ExtTxtF = new javax.swing.JTextField();
        Correo = new javax.swing.JLabel();
        CorreoTxtF = new javax.swing.JTextField();
        DIRECCIÓN = new javax.swing.JLabel();
        CALLE = new javax.swing.JLabel();
        CalleTxtF = new javax.swing.JTextField();
        CP = new javax.swing.JLabel();
        CPTxtF = new javax.swing.JTextField();
        EXTERIOR = new javax.swing.JLabel();
        ExteriorTxtF = new javax.swing.JTextField();
        COLONIA = new javax.swing.JLabel();
        ColoniaTxtF = new javax.swing.JTextField();
        INTERIOR = new javax.swing.JLabel();
        InteriorTxtF = new javax.swing.JTextField();
        ESTADO = new javax.swing.JLabel();
        Est_Box = new javax.swing.JComboBox<>();
        AlcaldiaMun = new javax.swing.JLabel();
        AlcalMunTxtF = new javax.swing.JTextField();
        BotEliminar = new javax.swing.JButton();
        BotEditar = new javax.swing.JLabel();
        BotAgregar = new javax.swing.JButton();
        BotGuardar = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        resultsTable2 = new javax.swing.JTable();
        FiltrarTxtF = new javax.swing.JTextField();
        FILTRAR = new javax.swing.JLabel();
        BotBuscar = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(940, 570));

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setPreferredSize(new java.awt.Dimension(1040, 560));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        fondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PROVEEDOR.setFont(new java.awt.Font("Jost", 0, 48)); // NOI18N
        PROVEEDOR.setText("PROVEEDOR");
        fondo.add(PROVEEDOR, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, 306, 60));

        productos.setBackground(new java.awt.Color(255, 255, 255));
        productos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        productos.setForeground(new java.awt.Color(204, 204, 204));
        productos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Nombre.setText("NOMBRE EMPRESA");
        productos.add(Nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, 20));

        NombreTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NombreTxtFActionPerformed(evt);
            }
        });
        productos.add(NombreTxtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 320, -1));

        NomContacto.setText("NOMBRE CONTACTO");
        productos.add(NomContacto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, 20));

        NContTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NContTxtFActionPerformed(evt);
            }
        });
        productos.add(NContTxtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 320, -1));

        Lada.setText("LADA");
        productos.add(Lada, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, 20));

        LadaTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LadaTxtFActionPerformed(evt);
            }
        });
        productos.add(LadaTxtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, 99, -1));

        Telefono.setText("TELEFONO");
        productos.add(Telefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 120, 90, 20));

        TelTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TelTxtFActionPerformed(evt);
            }
        });
        productos.add(TelTxtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 120, 140, -1));

        Extension.setText("EXTENSIÓN");
        productos.add(Extension, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, 20));

        ExtTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExtTxtFActionPerformed(evt);
            }
        });
        productos.add(ExtTxtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, 64, -1));

        Correo.setText("CORREO");
        productos.add(Correo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 150, 60, 20));

        CorreoTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CorreoTxtFActionPerformed(evt);
            }
        });
        productos.add(CorreoTxtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 150, 220, -1));

        DIRECCIÓN.setFont(new java.awt.Font("Jost", 0, 12)); // NOI18N
        DIRECCIÓN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DIRECCIÓN.setText("DIRECCIÓN");
        productos.add(DIRECCIÓN, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 70, 20));

        CALLE.setText("CALLE");
        productos.add(CALLE, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, 20));

        CalleTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CalleTxtFActionPerformed(evt);
            }
        });
        productos.add(CalleTxtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 190, -1));

        CP.setText("C.P.");
        productos.add(CP, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, 40, 20));

        CPTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CPTxtFActionPerformed(evt);
            }
        });
        productos.add(CPTxtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 210, 90, -1));

        EXTERIOR.setText("EXTERIOR");
        productos.add(EXTERIOR, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, -1, 20));

        ExteriorTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExteriorTxtFActionPerformed(evt);
            }
        });
        productos.add(ExteriorTxtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, 100, -1));

        COLONIA.setText("COLONIA");
        productos.add(COLONIA, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 260, -1, 20));

        ColoniaTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ColoniaTxtFActionPerformed(evt);
            }
        });
        productos.add(ColoniaTxtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 260, 100, -1));

        INTERIOR.setText("INTERIOR");
        productos.add(INTERIOR, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, 20));

        InteriorTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InteriorTxtFActionPerformed(evt);
            }
        });
        productos.add(InteriorTxtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, 100, -1));

        ESTADO.setText("ESTADO");
        productos.add(ESTADO, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 310, -1, 20));

        Est_Box.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Est_Box.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Est_Box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Est_BoxActionPerformed(evt);
            }
        });
        productos.add(Est_Box, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 310, 160, -1));

        AlcaldiaMun.setText("ALCAL/MUN");
        productos.add(AlcaldiaMun, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, -1, -1));

        AlcalMunTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AlcalMunTxtFActionPerformed(evt);
            }
        });
        productos.add(AlcalMunTxtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 360, 110, -1));

        BotEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eliminar.png"))); // NOI18N
        BotEliminar.setContentAreaFilled(false);
        BotEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotEliminarActionPerformed(evt);
            }
        });
        productos.add(BotEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 346, -1, -1));

        BotEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/editar usuari.png"))); // NOI18N
        BotEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotEditarMouseClicked(evt);
            }
        });
        productos.add(BotEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(296, 346, 40, 40));

        BotAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/añadir cpem.png"))); // NOI18N
        BotAgregar.setContentAreaFilled(false);
        BotAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotAgregarActionPerformed(evt);
            }
        });
        productos.add(BotAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(236, 346, -1, -1));

        BotGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/guardar.png"))); // NOI18N
        BotGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        productos.add(BotGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 336, 50, 50));

        fondo.add(productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, 410));

        resultsTable2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        resultsTable2.setModel(new javax.swing.table.DefaultTableModel(
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
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "NOMBRE", "CONTACTO", "LADA", "TELEFONO", "DIRECCIÓN"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        resultsTable2.setCellSelectionEnabled(true);
        resultsTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resultsTable2MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(resultsTable2);
        resultsTable2.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        fondo.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(489, 130, 410, 380));

        FiltrarTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FiltrarTxtFActionPerformed(evt);
            }
        });
        fondo.add(FiltrarTxtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(569, 100, 280, -1));

        FILTRAR.setText("FILTRAR:");
        fondo.add(FILTRAR, new org.netbeans.lib.awtextra.AbsoluteConstraints(509, 100, -1, 20));

        BotBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        BotBuscar.setBorder(null);
        BotBuscar.setBorderPainted(false);
        BotBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotBuscarActionPerformed(evt);
            }
        });
        fondo.add(BotBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(859, 90, -1, -1));

        bg.add(fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 19, 910, 530));

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

    private void Est_BoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Est_BoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Est_BoxActionPerformed

    private void BotAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotAgregarActionPerformed
        agregarProveedor();
    }//GEN-LAST:event_BotAgregarActionPerformed

    private void TelTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TelTxtFActionPerformed
       
    }//GEN-LAST:event_TelTxtFActionPerformed

    private void resultsTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resultsTable2MouseClicked
         int filaSeleccionada = resultsTable2.getSelectedRow();
        if (filaSeleccionada != -1) {
            String idProveedor = resultsTable2.getValueAt(filaSeleccionada, 0).toString();
            Object[] proveedor = daoProveedor.obtenerProveedorPorId(idProveedor);

            if (proveedor != null) {
                NombreTxtF.setText((String) proveedor[1]);
                NContTxtF.setText((String) proveedor[2]);
                LadaTxtF.setText((String) proveedor[3]);
                TelTxtF.setText((String) proveedor[4]);
                ExtTxtF.setText((String) proveedor[5]);
                CorreoTxtF.setText((String) proveedor[6]);
                CalleTxtF.setText((String) proveedor[7]);
                ExteriorTxtF.setText((String) proveedor[8]);
                InteriorTxtF.setText((String) proveedor[9]);
                ColoniaTxtF.setText((String) proveedor[10]);
                CPTxtF.setText((String) proveedor[11]);
                AlcalMunTxtF.setText((String) proveedor[12]);
                Est_Box.setSelectedItem((String) proveedor[13]);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo cargar la información del proveedor.");
            }
        }
    }//GEN-LAST:event_resultsTable2MouseClicked

    private void FiltrarTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FiltrarTxtFActionPerformed
       
    }//GEN-LAST:event_FiltrarTxtFActionPerformed

    private void BotBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotBuscarActionPerformed
        buscarProveedor();
    }//GEN-LAST:event_BotBuscarActionPerformed

    private void NombreTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NombreTxtFActionPerformed
  
    }//GEN-LAST:event_NombreTxtFActionPerformed

    private void CorreoTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CorreoTxtFActionPerformed
 
    }//GEN-LAST:event_CorreoTxtFActionPerformed

    private void BotEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotEliminarActionPerformed
        eliminarProveedor();
    }//GEN-LAST:event_BotEliminarActionPerformed

    private void BotEditarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotEditarMouseClicked
        editarProveedor();
    }//GEN-LAST:event_BotEditarMouseClicked

    private void NContTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NContTxtFActionPerformed
       
    }//GEN-LAST:event_NContTxtFActionPerformed

    private void LadaTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LadaTxtFActionPerformed
       
    }//GEN-LAST:event_LadaTxtFActionPerformed

    private void ExtTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExtTxtFActionPerformed
        
    }//GEN-LAST:event_ExtTxtFActionPerformed

    private void CalleTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CalleTxtFActionPerformed
        
    }//GEN-LAST:event_CalleTxtFActionPerformed

    private void CPTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CPTxtFActionPerformed
        
    }//GEN-LAST:event_CPTxtFActionPerformed

    private void ExteriorTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExteriorTxtFActionPerformed
        
    }//GEN-LAST:event_ExteriorTxtFActionPerformed

    private void ColoniaTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ColoniaTxtFActionPerformed
      
    }//GEN-LAST:event_ColoniaTxtFActionPerformed

    private void InteriorTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InteriorTxtFActionPerformed
       
    }//GEN-LAST:event_InteriorTxtFActionPerformed

    private void AlcalMunTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlcalMunTxtFActionPerformed
        
    }//GEN-LAST:event_AlcalMunTxtFActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AlcalMunTxtF;
    private javax.swing.JLabel AlcaldiaMun;
    private javax.swing.JButton BotAgregar;
    private javax.swing.JButton BotBuscar;
    private javax.swing.JLabel BotEditar;
    private javax.swing.JButton BotEliminar;
    private javax.swing.JLabel BotGuardar;
    private javax.swing.JLabel CALLE;
    private javax.swing.JLabel COLONIA;
    private javax.swing.JLabel CP;
    private javax.swing.JTextField CPTxtF;
    private javax.swing.JTextField CalleTxtF;
    private javax.swing.JTextField ColoniaTxtF;
    private javax.swing.JLabel Correo;
    private javax.swing.JTextField CorreoTxtF;
    private javax.swing.JLabel DIRECCIÓN;
    private javax.swing.JLabel ESTADO;
    private javax.swing.JLabel EXTERIOR;
    private javax.swing.JComboBox<String> Est_Box;
    private javax.swing.JTextField ExtTxtF;
    private javax.swing.JLabel Extension;
    private javax.swing.JTextField ExteriorTxtF;
    private javax.swing.JLabel FILTRAR;
    private javax.swing.JTextField FiltrarTxtF;
    private javax.swing.JLabel INTERIOR;
    private javax.swing.JTextField InteriorTxtF;
    private javax.swing.JLabel Lada;
    private javax.swing.JTextField LadaTxtF;
    private javax.swing.JTextField NContTxtF;
    private javax.swing.JLabel NomContacto;
    private javax.swing.JLabel Nombre;
    private javax.swing.JTextField NombreTxtF;
    private javax.swing.JLabel PROVEEDOR;
    private javax.swing.JTextField TelTxtF;
    private javax.swing.JLabel Telefono;
    private javax.swing.JPanel bg;
    private javax.swing.JPanel fondo;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel productos;
    private javax.swing.JTable resultsTable2;
    // End of variables declaration//GEN-END:variables

    public void setLocation(double d) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
