package com.vews;
import database.dao.DaoProveedor;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class PrProveedor extends javax.swing.JPanel {
    

private final DaoProveedor daoProveedor = new DaoProveedor(); // Instancia del DAO para manejo de empleados
 

    public static PrProveedor pl;


    public PrProveedor() {
        initComponents();
        cargarEstados();
        cargarProveedores();
    }
    
    private void cargarEstados() {
        // Llenar la lista de estados en el combo box
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
                proveedor[0] != null ? proveedor[0] : "N/A",        // ID
                proveedor[1] != null ? proveedor[1] : "N/A",        // Empresa
                proveedor[2] != null ? proveedor[2] : "N/A",        // Contacto
                proveedor[3] != null ? proveedor[3] : "N/A",        // LADA
                proveedor[4] != null ? proveedor[4] : "N/A", // TELEFONO (Convertido a String)
                proveedor[5] != null ? proveedor[5] : "N/A",        // Extensión
                proveedor[6] != null ? proveedor[6] : "N/A",        // Correo
                proveedor[7] != null ? proveedor[7] : "N/A"         // Dirección
            });
        }

        // Asigna el modelo a la tabla
        resultsTable.setModel(model);
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
    String idProveedor = resultsTable.getValueAt(filaSeleccionada, 0).toString(); // Obtiene el ID (columna 0)

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

    resultsTable.setModel(model);
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

    private void agregarEmpleado() {
        // Método para agregar un empleado
        try {
            Object[] empleado = obtenerDatosProveedor();
            boolean exito = daoProveedor.addProveedor(empleado);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Empleado agregado correctamente.");
                cargarProveedores();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar empleado.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar empleado: " + e.getMessage());
        }
    }

    private void editarEmpleado() {
        // Método para editar un empleado
        try {
            int filaSeleccionada = resultsTable.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un empleado para editar.");
                return;
            }

            Object[] empleado = obtenerDatosProveedor();
            boolean exito = daoProveedor.updateEmployee(empleado);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Empleado editado correctamente.");
                cargarProveedores();
            } else {
                JOptionPane.showMessageDialog(this, "Error al editar empleado.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al editar empleado: " + e.getMessage());
        }
    }

    private void eliminarEmpleado() {
    try {
        int filaSeleccionada = resultsTable.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un empleado para eliminar.");
            return;
        }

        // Obtén el ID del empleado seleccionado
        String idEmpleado = resultsTable.getValueAt(filaSeleccionada, 0).toString();

        // Obtén el ID de la dirección asociada al empleado
        String idDireccion = daoProveedor.obtenerIdDireccionPorEmpleado(idEmpleado);

        if (idDireccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se pudo obtener la dirección asociada al empleado.");
            return;
        }

        // Llama al método deleteEmployee con ambos IDs
        boolean exito = daoProveedor.deleteEmployee(idEmpleado, idDireccion);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Empleado eliminado correctamente.");
            cargarProveedores(); // Recarga la tabla
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar el empleado.");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al eliminar el empleado: " + e.getMessage());
        e.printStackTrace();
    }
}
  
    private Object[] obtenerDatosProveedor() {
    try {
        // Obtiene los datos del formulario
        String empresa = jTextFieldNombreEmpresa.getText().trim();
        String contacto = jTextFieldContacto.getText().trim();
        int lada = Integer.parseInt(jTextFieldLada.getText().trim());
        int telefono = Integer.parseInt(jTextFieldTelefono.getText().trim());
        int extension = Integer.parseInt(jTextFieldExtension.getText().trim());
        String correo = jTextFieldCorreo.getText().trim();
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
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

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

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/añadir cpem.png"))); // NOI18N
        jButton3.setContentAreaFilled(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        fondo.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 110, -1, -1));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/editar usuari.png"))); // NOI18N
        jButton4.setContentAreaFilled(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        fondo.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 110, -1, -1));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/guardar.png"))); // NOI18N
        jButton5.setContentAreaFilled(false);
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        fondo.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 110, -1, -1));

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eliminar.png"))); // NOI18N
        jButton6.setContentAreaFilled(false);
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        fondo.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 110, -1, -1));

        bg.add(fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 900, 530));

        add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 570));

        getAccessibleContext().setAccessibleParent(this);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
     editarEmpleado();   

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    agregarEmpleado();        

    }//GEN-LAST:event_jButton3ActionPerformed

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

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
       eliminarEmpleado();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
  Object[] datosProveedor = obtenerDatosProveedor();
if (datosProveedor == null) {
    return; // Si hay un error en los datos, no continúa
}

boolean exito = daoProveedor.addProveedor(datosProveedor);
if (exito) {
    JOptionPane.showMessageDialog(this, "Proveedor agregado correctamente.");
    cargarProveedores(); // Recarga la tabla de proveedores
} else {
    JOptionPane.showMessageDialog(this, "Error al agregar el proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
}

    }//GEN-LAST:event_jButton5ActionPerformed

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
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
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
