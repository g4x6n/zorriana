package com.vews;
import database.dao.DaoProveedor;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import database.dao.DaoProveedor;

    public class PrProveedor extends javax.swing.JPanel {
    

    private final DaoProveedor daoProveedor = new DaoProveedor();

    public static PrProveedor pl;


    public PrProveedor() {
        initComponents();
        cargarEstados();
        cargarProveedores();
    }
    
    private void cargarEstados() {
        try {
            List<String> estados = daoProveedor.obtenerEstados(); // Implementar este método en DaoEmpleado
            EDO_BOX.removeAllItems();
            for (String estado : estados) {
                EDO_BOX.addItem(estado);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar estados: " + e.getMessage());
        }
    }

    private void cargarProveedores() {
    try {
        // Obtiene los proveedores desde el DAO
        List<Object[]> proveedores = daoProveedor.listProveedores();

        // Configura el modelo de la tabla con las columnas necesarias
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Empresa", "Contacto", "Lada", "Telefono", "Extensión", "Correo", "Dirección"}, 0
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Ninguna celda será editable
                return false;
            }
        };

        // Llena el modelo con los datos
        for (Object[] proveedor : proveedores) {
            model.addRow(new Object[]{
                proveedor[0], // ID
                proveedor[1], // Empresa
                proveedor[2], // Contacto
                proveedor[3], // LADA
                proveedor[4], // Teléfono
                proveedor[5], // Extensión
                proveedor[6], // Correo
                proveedor[7]  // Dirección
            });
        }

        // Asigna el modelo a la tabla
        resultsTable.setModel(model);

        // Ocultar la columna de ID
        resultsTable.getColumnModel().getColumn(0).setMinWidth(0);
        resultsTable.getColumnModel().getColumn(0).setMaxWidth(0);
        resultsTable.getColumnModel().getColumn(0).setWidth(0);

        configurarColumnasTabla(resultsTable);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al cargar proveedores: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

    
    private void configurarColumnasTabla(JTable table) {
    // Anchos preferidos para las columnas
    int[] anchos = {10, 200, 150, 50, 100, 80, 200, 300}; // Ajusta estos valores según tus necesidades
    for (int i = 0; i < anchos.length; i++) {
        table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        table.getColumnModel().getColumn(i).setMinWidth(anchos[i]);
    }
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
}

    private void manejarTablaProveedor() {
    int filaSeleccionada = resultsTable.getSelectedRow();
    if (filaSeleccionada != -1) {
        String idProveedor = resultsTable.getValueAt(filaSeleccionada, 0).toString(); // Obtiene el ID oculto

        // Llama al DAO para obtener los detalles completos del proveedor
        Object[] proveedor = daoProveedor.obtenerProveedorPorId(idProveedor);

        if (proveedor != null) {
            // Asigna valores a los campos del formulario
            jTextFieldNombreEmpresa.setText((String) proveedor[1]); // Nombre Empresa
            jTextFieldContacto.setText((String) proveedor[2]); // Nombre Contacto
            jTextFieldLada.setText(String.valueOf(proveedor[3])); // LADA
            jTextFieldTelefono.setText(String.valueOf(proveedor[4])); // Teléfono
            jTextFieldExtension.setText(String.valueOf(proveedor[5])); // Extensión
            jTextFieldCorreo.setText((String) proveedor[6]); // Correo

            // Asigna dirección
            CalleTxtF.setText((String) proveedor[7]); // Calle
            ExtTxtF.setText((String) proveedor[8]); // Exterior
            IntTxtF.setText((String) proveedor[9]); // Interior
            ColTxtF.setText((String) proveedor[10]); // Colonia
            CPTxtF.setText((String) proveedor[11]); // CP
            AlcalMunTxtF.setText((String) proveedor[12]); // Alcaldía/Municipio
            EDO_BOX.setSelectedItem((String) proveedor[13]); // Estado
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo cargar la información del proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

    
    private void BuscarProveedor() {
    String filtro = searchbar.getText().trim();

    if (filtro.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, ingresa un criterio de búsqueda.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        // Realiza la búsqueda
        List<Object[]> resultados = daoProveedor.buscarProveedor(filtro);

        // Configura el modelo de la tabla
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Nombre Empresa", "Contacto", "LADA", "Teléfono", "Extensión", "Correo", "Dirección"}, 0
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Ninguna celda será editable
                return false;
            }
        };

        // Llena el modelo con los resultados
        for (Object[] fila : resultados) {
            model.addRow(new Object[]{
                fila[0], // ID del proveedor
                fila[1], // Nombre de la empresa
                fila[2], // Contacto
                fila[3], // LADA
                fila[4], // Teléfono
                fila[5], // Extensión
                fila[6], // Correo
                fila[7]  // Dirección
            });
        }

        // Asigna el modelo a la tabla
        resultsTable.setModel(model);

        // Ocultar la columna de ID
        resultsTable.getColumnModel().getColumn(0).setMinWidth(0);
        resultsTable.getColumnModel().getColumn(0).setMaxWidth(0);
        resultsTable.getColumnModel().getColumn(0).setWidth(0);

        configurarColumnasTabla(resultsTable);

        // Muestra un mensaje si no hay resultados
        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron proveedores con el criterio ingresado.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al realizar la búsqueda: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
}

private String ajustarLongitud(String valor, int longitud) {
    if (valor == null) return String.format("%-" + longitud + "s", ""); // Espacios si es null
    return String.format("%-" + longitud + "s", valor.substring(0, Math.min(valor.length(), longitud)));
}

private void actualizarProveedor() {
    int filaSeleccionada = resultsTable.getSelectedRow();
    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this, "Selecciona un proveedor de la tabla para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String idProveedor = resultsTable.getValueAt(filaSeleccionada, 0).toString();

    // Obtener datos del formulario
    Object[] datosProveedor = obtenerDatosProveedor();
    if (datosProveedor == null) {
        return; // Hay error en los datos ingresados
    }

    try {
        // Llama al método del DAO para actualizar
        boolean actualizado = daoProveedor.actualizarProveedor(idProveedor, datosProveedor);

        if (actualizado) {
            JOptionPane.showMessageDialog(this, "Proveedor actualizado correctamente.");
            cargarProveedores(); // Refrescar la tabla
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar el proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

    
    
    
    
private Object[] obtenerDatosProveedor() {
    try {
        // Datos del proveedor
        String empresa = jTextFieldNombreEmpresa.getText().trim();
        String contacto = jTextFieldContacto.getText().trim();
        int lada = Integer.parseInt(jTextFieldLada.getText().trim());
        int telefono = Integer.parseInt(jTextFieldTelefono.getText().trim());
        int extension = Integer.parseInt(jTextFieldExtension.getText().trim());
        String correo = jTextFieldCorreo.getText().trim();

        // Datos de la dirección
        String calle = CalleTxtF.getText().trim();
        String exterior = ExtTxtF.getText().trim();
        String interior = IntTxtF.getText().trim();
        String colonia = ColTxtF.getText().trim();
        String cp = CPTxtF.getText().trim();
        String alcalMun = AlcalMunTxtF.getText().trim();
        String estado = (String) EDO_BOX.getSelectedItem();

        // Retorna un arreglo con los datos
        return new Object[]{
            empresa, contacto, lada, telefono, extension, correo,
            calle, exterior, interior, colonia, cp, alcalMun, estado
        };
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Error en el formato de LADA, teléfono o extensión. Asegúrate de ingresar números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        return null;
    }
}


      @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        fondo = new javax.swing.JPanel();
        TITULO = new javax.swing.JLabel();
        DIRECCIÓN = new javax.swing.JLabel();
        CALLE = new javax.swing.JLabel();
        CalleTxtF = new javax.swing.JTextField();
        EXTERIOR = new javax.swing.JLabel();
        ExtTxtF = new javax.swing.JTextField();
        INTERIOR = new javax.swing.JLabel();
        IntTxtF = new javax.swing.JTextField();
        CP = new javax.swing.JLabel();
        CPTxtF = new javax.swing.JTextField();
        COLONIA = new javax.swing.JLabel();
        ColTxtF = new javax.swing.JTextField();
        AlcalMun = new javax.swing.JLabel();
        AlcalMunTxtF = new javax.swing.JTextField();
        ESTADO = new javax.swing.JLabel();
        EDO_BOX = new javax.swing.JComboBox<>();
        NombreEmpresa = new javax.swing.JLabel();
        jTextFieldNombreEmpresa = new javax.swing.JTextField();
        NombreContacto = new javax.swing.JLabel();
        jTextFieldContacto = new javax.swing.JTextField();
        Lada = new javax.swing.JLabel();
        jTextFieldLada = new javax.swing.JTextField();
        Telefono = new javax.swing.JLabel();
        jTextFieldTelefono = new javax.swing.JTextField();
        Correo = new javax.swing.JLabel();
        jTextFieldCorreo = new javax.swing.JTextField();
        Extension = new javax.swing.JLabel();
        jTextFieldExtension = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        resultsTable = new javax.swing.JTable();
        searchbar = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        Anadir = new javax.swing.JButton();
        Editar = new javax.swing.JButton();
        Borrar = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(940, 570));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setPreferredSize(new java.awt.Dimension(1040, 560));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        fondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TITULO.setFont(new java.awt.Font("Jost", 0, 48)); // NOI18N
        TITULO.setText("PROVEEDOR");
        fondo.add(TITULO, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, 305, -1));

        DIRECCIÓN.setFont(new java.awt.Font("Jost", 1, 12)); // NOI18N
        DIRECCIÓN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DIRECCIÓN.setText("DIRECCIÓN");
        fondo.add(DIRECCIÓN, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 270, 80, -1));

        CALLE.setText("CALLE");
        fondo.add(CALLE, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 40, -1));
        fondo.add(CalleTxtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, 370, -1));

        EXTERIOR.setText("EXTERIOR");
        fondo.add(EXTERIOR, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 60, -1));

        ExtTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExtTxtFActionPerformed(evt);
            }
        });
        fondo.add(ExtTxtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 330, 80, -1));

        INTERIOR.setText("INTERIOR");
        fondo.add(INTERIOR, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 330, 60, -1));
        fondo.add(IntTxtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 330, 70, -1));

        CP.setText("C.P.");
        fondo.add(CP, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 330, 30, -1));
        fondo.add(CPTxtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 330, 90, 30));

        COLONIA.setText("COLONIA");
        fondo.add(COLONIA, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 60, -1));

        ColTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ColTxtFActionPerformed(evt);
            }
        });
        fondo.add(ColTxtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 370, 130, -1));

        AlcalMun.setText("ALCAL/MUN");
        fondo.add(AlcalMun, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 370, 70, -1));
        fondo.add(AlcalMunTxtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 370, 120, -1));

        ESTADO.setText("ESTADO");
        fondo.add(ESTADO, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 50, -1));

        EDO_BOX.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        EDO_BOX.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        fondo.add(EDO_BOX, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 410, 150, -1));

        NombreEmpresa.setText("NOMBRE EMPRESA");
        fondo.add(NombreEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, 20));

        jTextFieldNombreEmpresa.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTextFieldNombreEmpresa.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextFieldNombreEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNombreEmpresaActionPerformed(evt);
            }
        });
        fondo.add(jTextFieldNombreEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 310, -1));

        NombreContacto.setText("NOMBRE CONTACTO");
        fondo.add(NombreContacto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, 20));

        jTextFieldContacto.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextFieldContacto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldContactoActionPerformed(evt);
            }
        });
        fondo.add(jTextFieldContacto, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 300, -1));

        Lada.setText("LADA");
        fondo.add(Lada, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, 20));

        jTextFieldLada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldLadaActionPerformed(evt);
            }
        });
        fondo.add(jTextFieldLada, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, 90, -1));

        Telefono.setText("TELEFONO");
        fondo.add(Telefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, -1, -1));

        jTextFieldTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldTelefonoActionPerformed(evt);
            }
        });
        fondo.add(jTextFieldTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 180, 140, -1));

        Correo.setText("CORREO");
        fondo.add(Correo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, 20));

        jTextFieldCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCorreoActionPerformed(evt);
            }
        });
        fondo.add(jTextFieldCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 240, 350, -1));

        Extension.setText("EXTENSIÓN");
        fondo.add(Extension, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, -1, 20));

        jTextFieldExtension.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldExtensionActionPerformed(evt);
            }
        });
        fondo.add(jTextFieldExtension, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 80, -1));

        resultsTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
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
                "Nombre", "Apellido P", "Apellido M", "Fecha de Reg", "CORREO", "DIRECCIÓN"
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

        fondo.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(486, 204, 390, 270));

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
        fondo.add(searchbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(578, 171, 230, -1));

        jLabel7.setText("FILTRAR:");
        fondo.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(515, 181, -1, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        jButton1.setToolTipText("");
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        fondo.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 160, 60, -1));

        Anadir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/añadir cpem.png"))); // NOI18N
        Anadir.setContentAreaFilled(false);
        Anadir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Anadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnadirActionPerformed(evt);
            }
        });
        fondo.add(Anadir, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 110, -1, -1));

        Editar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/editar usuari.png"))); // NOI18N
        Editar.setContentAreaFilled(false);
        Editar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditarActionPerformed(evt);
            }
        });
        fondo.add(Editar, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 110, -1, -1));

        Borrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eliminar.png"))); // NOI18N
        Borrar.setContentAreaFilled(false);
        Borrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Borrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BorrarActionPerformed(evt);
            }
        });
        fondo.add(Borrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 110, -1, -1));

        bg.add(fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 900, 530));

        add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 570));

        getAccessibleContext().setAccessibleParent(this);
    }// </editor-fold>//GEN-END:initComponents

    private void EditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditarActionPerformed
        actualizarProveedor();
    }//GEN-LAST:event_EditarActionPerformed

    private void AnadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnadirActionPerformed
      

    }//GEN-LAST:event_AnadirActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        BuscarProveedor();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void searchbarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchbarActionPerformed

    }//GEN-LAST:event_searchbarActionPerformed

    private void resultsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resultsTableMouseClicked
   manejarTablaProveedor();
    }//GEN-LAST:event_resultsTableMouseClicked

    private void jTextFieldExtensionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldExtensionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldExtensionActionPerformed

    private void jTextFieldCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCorreoActionPerformed

        jTextFieldNombreEmpresa.setText(""); // Elimina el texto inicial
        jTextFieldNombreEmpresa.setEditable(false); // Hace que no se pueda editar
    }//GEN-LAST:event_jTextFieldCorreoActionPerformed

    private void jTextFieldTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTelefonoActionPerformed

        jTextFieldNombreEmpresa.setText(""); // Elimina el texto inicial
        jTextFieldNombreEmpresa.setEditable(false); // Hace que no se pueda editar
    }//GEN-LAST:event_jTextFieldTelefonoActionPerformed

    private void jTextFieldContactoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldContactoActionPerformed

        jTextFieldNombreEmpresa.setText(""); // Elimina el texto inicial
        jTextFieldNombreEmpresa.setEditable(false); // Hace que no se pueda editar
    }//GEN-LAST:event_jTextFieldContactoActionPerformed

    private void jTextFieldLadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldLadaActionPerformed

        jTextFieldNombreEmpresa.setText(""); // Elimina el texto inicial
        jTextFieldNombreEmpresa.setEditable(false); // Hace que no se pueda editar
    }//GEN-LAST:event_jTextFieldLadaActionPerformed

    private void jTextFieldNombreEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNombreEmpresaActionPerformed

        jTextFieldNombreEmpresa.setText(""); // Elimina el texto inicial
        jTextFieldNombreEmpresa.setEditable(false); // Hace que no se pueda editar
    }//GEN-LAST:event_jTextFieldNombreEmpresaActionPerformed

    private void ColTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ColTxtFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ColTxtFActionPerformed

    private void ExtTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExtTxtFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ExtTxtFActionPerformed

    private void BorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BorrarActionPerformed
        //actualizarProveedor();
    }//GEN-LAST:event_BorrarActionPerformed

    private void resultsTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_resultsTableKeyPressed
    if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
        manejarTablaProveedor();
        evt.consume();
    }
    }//GEN-LAST:event_resultsTableKeyPressed

    private void searchbarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchbarKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
        BuscarProveedor();
        evt.consume();
    }
    }//GEN-LAST:event_searchbarKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AlcalMun;
    private javax.swing.JTextField AlcalMunTxtF;
    private javax.swing.JButton Anadir;
    private javax.swing.JButton Borrar;
    private javax.swing.JLabel CALLE;
    private javax.swing.JLabel COLONIA;
    private javax.swing.JLabel CP;
    private javax.swing.JTextField CPTxtF;
    private javax.swing.JTextField CalleTxtF;
    private javax.swing.JTextField ColTxtF;
    private javax.swing.JLabel Correo;
    private javax.swing.JLabel DIRECCIÓN;
    private javax.swing.JComboBox<String> EDO_BOX;
    private javax.swing.JLabel ESTADO;
    private javax.swing.JLabel EXTERIOR;
    private javax.swing.JButton Editar;
    private javax.swing.JTextField ExtTxtF;
    private javax.swing.JLabel Extension;
    private javax.swing.JLabel INTERIOR;
    private javax.swing.JTextField IntTxtF;
    private javax.swing.JLabel Lada;
    private javax.swing.JLabel NombreContacto;
    private javax.swing.JLabel NombreEmpresa;
    private javax.swing.JLabel TITULO;
    private javax.swing.JLabel Telefono;
    private javax.swing.JPanel bg;
    private javax.swing.JPanel fondo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldContacto;
    private javax.swing.JTextField jTextFieldCorreo;
    private javax.swing.JTextField jTextFieldExtension;
    private javax.swing.JTextField jTextFieldLada;
    private javax.swing.JTextField jTextFieldNombreEmpresa;
    private javax.swing.JTextField jTextFieldTelefono;
    private javax.swing.JTable resultsTable;
    private javax.swing.JTextField searchbar;
    // End of variables declaration//GEN-END:variables

    public void setLocation(double d) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
