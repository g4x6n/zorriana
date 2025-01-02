/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.vews;
import database.dao.DaoEmpleado;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Alex
 */
public class PrEmpleado extends javax.swing.JPanel {
    

     private final DaoEmpleado daoEmpleado = new DaoEmpleado(); // Instancia del DAO para manejo de empleados
 

    public static PrEmpleado cl;

    /**
     * Creates new form Inicio
     */
    public PrEmpleado() {
        initComponents();
        cargarEstados();
        cargarPuestos();
        cargarEmpleados();
    }
     private void cargarEstados() {
        // Llenar la lista de estados en el combo box
        try {
            List<String> estados = daoEmpleado.obtenerEstados(); // Implementar este método en DaoEmpleado
            EDO_BOX.removeAllItems();
            for (String estado : estados) {
                EDO_BOX.addItem(estado);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar estados: " + e.getMessage());
        }
    }

    private void cargarPuestos() {
        // Llenar la lista de puestos en el combo box
        try {
            List<String> puestos = daoEmpleado.obtenerPuestos(); // Implementar este método en DaoEmpleado
            PuestoCB.removeAllItems();
            for (String puesto : puestos) {
                PuestoCB.addItem(puesto);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar puestos: " + e.getMessage());
        }
    }

    private void cargarEmpleados() {
        // Cargar los empleados en la tabla
        try {
            List<Object[]> empleados = daoEmpleado.listEmployees();
            DefaultTableModel model = new DefaultTableModel(
                new String[]{"Nombre", "Apellido P", "Apellido M", "Fecha de Reg", "Correo", "Dirección"}, 0
            );
            for (Object[] empleado : empleados) {
                model.addRow(new Object[]{
                    empleado[1], empleado[2], empleado[3], empleado[4], empleado[6], empleado[9]
                });
            }
            resultsTable.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar empleados: " + e.getMessage());
        }
    }

    private void agregarEmpleado() {
        // Método para agregar un empleado
        try {
            Object[] empleado = obtenerDatosEmpleado();
            boolean exito = daoEmpleado.addEmployee(empleado);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Empleado agregado correctamente.");
                cargarEmpleados();
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

            Object[] empleado = obtenerDatosEmpleado();
            boolean exito = daoEmpleado.updateEmployee(empleado);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Empleado editado correctamente.");
                cargarEmpleados();
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
        String idDireccion = daoEmpleado.obtenerIdDireccionPorEmpleado(idEmpleado);

        if (idDireccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se pudo obtener la dirección asociada al empleado.");
            return;
        }

        // Llama al método deleteEmployee con ambos IDs
        boolean exito = daoEmpleado.deleteEmployee(idEmpleado, idDireccion);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Empleado eliminado correctamente.");
            cargarEmpleados(); // Recarga la tabla
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar el empleado.");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al eliminar el empleado: " + e.getMessage());
        e.printStackTrace();
    }
}


 private Object[] obtenerDatosEmpleado() {
    // Método para obtener los datos del formulario
    try {
        String nombre = jTextFieldNombre.getText().trim();
        String apPaterno = jTextFieldApPaterno.getText().trim();
        String apMaterno = jTextFieldApMaterno.getText().trim();
        String fechaReg = jTextFieldFechaReg.getText().trim();
        String usuario = UserTxtF.getText().trim();
        String contrasena = PswTxtF.getText().trim();
        String correo = jTextFieldCorreo.getText().trim();
        String puesto = (String) PuestoCB.getSelectedItem();
        float salario = Float.parseFloat(SalTxtF.getText().trim());
        String calle = CalleTxtF.getText().trim();
        String exterior = ExtTxtF.getText().trim();
        String interior = IntTxtF.getText().trim();
        String colonia = ColTxtF.getText().trim();
        String cp = CPTxtF.getText().trim();
        String alcalMun = AlcalMunTxtF.getText().trim();
        String estado = (String) EDO_BOX.getSelectedItem();

        return new Object[]{
            nombre, apPaterno, apMaterno, fechaReg, usuario, contrasena, correo,
            puesto, salario, calle, exterior, interior, colonia, cp, alcalMun, estado
        };
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Error en el formato de sueldo. Asegúrate de ingresar un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        return null;
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
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
        Nombre = new javax.swing.JLabel();
        jTextFieldNombre = new javax.swing.JTextField();
        ApPat = new javax.swing.JLabel();
        jTextFieldApPaterno = new javax.swing.JTextField();
        ApMat = new javax.swing.JLabel();
        jTextFieldApMaterno = new javax.swing.JTextField();
        FechaReg = new javax.swing.JLabel();
        jTextFieldFechaReg = new javax.swing.JTextField();
        Puesto = new javax.swing.JLabel();
        PuestoCB = new javax.swing.JComboBox<>();
        Correo = new javax.swing.JLabel();
        jTextFieldCorreo = new javax.swing.JTextField();
        Salario = new javax.swing.JLabel();
        SalTxtF = new javax.swing.JTextField();
        Usuario = new javax.swing.JLabel();
        UserTxtF = new javax.swing.JTextField();
        Contraseña = new javax.swing.JLabel();
        PswTxtF = new javax.swing.JTextField();
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

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(1040, 560));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TITULO.setFont(new java.awt.Font("Jost", 0, 48)); // NOI18N
        TITULO.setText("EMPLEADOS");
        jPanel1.add(TITULO, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, -1, -1));

        DIRECCIÓN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DIRECCIÓN.setText("DIRECCIÓN");
        jPanel1.add(DIRECCIÓN, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 330, 80, -1));

        CALLE.setText("CALLE");
        jPanel1.add(CALLE, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 40, -1));
        jPanel1.add(CalleTxtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 360, 370, -1));

        EXTERIOR.setText("EXTERIOR");
        jPanel1.add(EXTERIOR, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 60, -1));

        ExtTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExtTxtFActionPerformed(evt);
            }
        });
        jPanel1.add(ExtTxtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 390, 80, -1));

        INTERIOR.setText("INTERIOR");
        jPanel1.add(INTERIOR, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 390, 60, -1));
        jPanel1.add(IntTxtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 390, 70, -1));

        CP.setText("C.P.");
        jPanel1.add(CP, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 390, 30, -1));
        jPanel1.add(CPTxtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 390, 90, 30));

        COLONIA.setText("COLONIA");
        jPanel1.add(COLONIA, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, 60, -1));

        ColTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ColTxtFActionPerformed(evt);
            }
        });
        jPanel1.add(ColTxtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 430, 130, -1));

        AlcalMun.setText("ALCAL/MUN");
        jPanel1.add(AlcalMun, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 430, 70, -1));
        jPanel1.add(AlcalMunTxtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 430, 120, -1));

        ESTADO.setText("ESTADO");
        jPanel1.add(ESTADO, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, 50, -1));

        EDO_BOX.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(EDO_BOX, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 470, 150, -1));

        Nombre.setText("NOMBRE");
        jPanel1.add(Nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        jTextFieldNombre.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTextFieldNombre.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextFieldNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNombreActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, 360, -1));

        ApPat.setText("APELLIDO PATERNO");
        jPanel1.add(ApPat, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, 20));

        jTextFieldApPaterno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldApPaternoActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldApPaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 160, 80, -1));

        ApMat.setText("APELLIDO MATERNO");
        jPanel1.add(ApMat, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 160, -1, 20));

        jTextFieldApMaterno.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextFieldApMaterno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldApMaternoActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldApMaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, 90, -1));

        FechaReg.setText("FECHA DE REGISTRO");
        jPanel1.add(FechaReg, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        jTextFieldFechaReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFechaRegActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldFechaReg, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 200, 120, -1));

        Puesto.setText("PUESTO");
        jPanel1.add(Puesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 200, -1, -1));

        PuestoCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(PuestoCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 200, 110, -1));

        Correo.setText("CORREO");
        jPanel1.add(Correo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, -1, -1));

        jTextFieldCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCorreoActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 290, 360, -1));

        Salario.setText("SALARIO");
        jPanel1.add(Salario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, -1, -1));

        SalTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalTxtFActionPerformed(evt);
            }
        });
        jPanel1.add(SalTxtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, 180, -1));

        Usuario.setText("USUARIO");
        jPanel1.add(Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 230, -1, -1));

        UserTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserTxtFActionPerformed(evt);
            }
        });
        jPanel1.add(UserTxtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 230, 110, -1));

        Contraseña.setText("CONTRASEÑA");
        jPanel1.add(Contraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, -1));

        PswTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PswTxtFActionPerformed(evt);
            }
        });
        jPanel1.add(PswTxtF, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, 120, -1));

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
        jScrollPane1.setViewportView(resultsTable);
        resultsTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 220, 390, 270));

        searchbar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchbarActionPerformed(evt);
            }
        });
        jPanel1.add(searchbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 190, 250, -1));

        jLabel7.setText("FILTRAR:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 190, -1, -1));

        jButton1.setText("BUSCAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 190, 80, -1));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/añadir cpem.png"))); // NOI18N
        jButton3.setContentAreaFilled(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 120, -1, -1));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/editar usuari.png"))); // NOI18N
        jButton4.setContentAreaFilled(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 120, -1, -1));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/guardar.png"))); // NOI18N
        jButton5.setContentAreaFilled(false);
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 120, -1, -1));

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eliminar.png"))); // NOI18N
        jButton6.setContentAreaFilled(false);
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 120, -1, -1));

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

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
     editarEmpleado();   

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
agregarEmpleado();        

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     String filtro = searchbar.getText().trim(); // Obtén el texto de la barra de búsqueda

    if (filtro.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, ingresa un criterio de búsqueda.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        // Llama al método de búsqueda en el DAO
        List<Object[]> resultados = daoEmpleado.buscarEmpleado(filtro);

        // Configura el modelo de la tabla
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Apellido P", "Apellido M", "Fecha de Reg", "Correo", "Dirección"}, 0
        );

        // Llena la tabla con los resultados
        for (Object[] fila : resultados) {
            model.addRow(new Object[]{
                fila[0], fila[1], fila[2], fila[3], fila[4], fila[5], fila[6]
            });
        }

        resultsTable.setModel(model);

        // Verifica si no hay resultados
        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron empleados con el criterio ingresado.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al realizar la búsqueda: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void searchbarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchbarActionPerformed

    }//GEN-LAST:event_searchbarActionPerformed

    private void resultsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resultsTableMouseClicked
    int filaSeleccionada = resultsTable.getSelectedRow();
    if (filaSeleccionada != -1) {
        String idEmpleado = resultsTable.getValueAt(filaSeleccionada, 0).toString();

        // Llamar al DAO para obtener los detalles completos del empleado
        Object[] empleado = daoEmpleado.obtenerEmpleadoPorId(idEmpleado);

        if (empleado != null) {
            // Asignar valores a los campos del formulario
            jTextFieldNombre.setText((String) empleado[1]); // Nombre
            jTextFieldApPaterno.setText((String) empleado[2]); // Apellido Paterno
            jTextFieldApMaterno.setText((String) empleado[3]); // Apellido Materno
            jTextFieldFechaReg.setText(empleado[4] != null ? empleado[4].toString() : ""); // Fecha de Registro
            UserTxtF.setText((String) empleado[5]); // Usuario
            PswTxtF.setText((String) empleado[6]); // Contraseña
            jTextFieldCorreo.setText((String) empleado[7]); // Correo
            PuestoCB.setSelectedItem((String) empleado[8]); // Puesto
            SalTxtF.setText(String.valueOf(empleado[9])); // Sueldo

            // Asignar dirección
            CalleTxtF.setText((String) empleado[10]); // Calle
            ExtTxtF.setText((String) empleado[11]); // Exterior
            IntTxtF.setText((String) empleado[12]); // Interior
            ColTxtF.setText((String) empleado[13]); // Colonia
            CPTxtF.setText((String) empleado[14]); // CP
            AlcalMunTxtF.setText((String) empleado[15]); // Alcaldía/Municipio
            EDO_BOX.setSelectedItem((String) empleado[16]); // Estado
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo cargar la información del empleado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    }//GEN-LAST:event_resultsTableMouseClicked

    private void PswTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PswTxtFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PswTxtFActionPerformed

    private void UserTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UserTxtFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UserTxtFActionPerformed

    private void SalTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalTxtFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SalTxtFActionPerformed

    private void jTextFieldCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCorreoActionPerformed

        jTextFieldNombre.setText(""); // Elimina el texto inicial
        jTextFieldNombre.setEditable(false); // Hace que no se pueda editar
    }//GEN-LAST:event_jTextFieldCorreoActionPerformed

    private void jTextFieldFechaRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFechaRegActionPerformed

        jTextFieldNombre.setText(""); // Elimina el texto inicial
        jTextFieldNombre.setEditable(false); // Hace que no se pueda editar
    }//GEN-LAST:event_jTextFieldFechaRegActionPerformed

    private void jTextFieldApMaternoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldApMaternoActionPerformed

        jTextFieldNombre.setText(""); // Elimina el texto inicial
        jTextFieldNombre.setEditable(false); // Hace que no se pueda editar
    }//GEN-LAST:event_jTextFieldApMaternoActionPerformed

    private void jTextFieldApPaternoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldApPaternoActionPerformed

        jTextFieldNombre.setText(""); // Elimina el texto inicial
        jTextFieldNombre.setEditable(false); // Hace que no se pueda editar
    }//GEN-LAST:event_jTextFieldApPaternoActionPerformed

    private void jTextFieldNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNombreActionPerformed

        jTextFieldNombre.setText(""); // Elimina el texto inicial
        jTextFieldNombre.setEditable(false); // Hace que no se pueda editar
    }//GEN-LAST:event_jTextFieldNombreActionPerformed

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
      Object[] datosEmpleado = obtenerDatosEmpleado();
    if (datosEmpleado == null) {
        return; // Si hay un error en los datos, no continúa
    }
    
    boolean exito = daoEmpleado.addEmployee(datosEmpleado);
    if (exito) {
        JOptionPane.showMessageDialog(this, "Empleado agregado correctamente.");
        cargarEmpleados(); // Recarga la tabla de empleados
    } else {
        JOptionPane.showMessageDialog(this, "Error al agregar el empleado.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jButton5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AlcalMun;
    private javax.swing.JTextField AlcalMunTxtF;
    private javax.swing.JLabel ApMat;
    private javax.swing.JLabel ApPat;
    private javax.swing.JLabel CALLE;
    private javax.swing.JLabel COLONIA;
    private javax.swing.JLabel CP;
    private javax.swing.JTextField CPTxtF;
    private javax.swing.JTextField CalleTxtF;
    private javax.swing.JTextField ColTxtF;
    private javax.swing.JLabel Contraseña;
    private javax.swing.JLabel Correo;
    private javax.swing.JLabel DIRECCIÓN;
    private javax.swing.JComboBox<String> EDO_BOX;
    private javax.swing.JLabel ESTADO;
    private javax.swing.JLabel EXTERIOR;
    private javax.swing.JTextField ExtTxtF;
    private javax.swing.JLabel FechaReg;
    private javax.swing.JLabel INTERIOR;
    private javax.swing.JTextField IntTxtF;
    private javax.swing.JLabel Nombre;
    private javax.swing.JTextField PswTxtF;
    private javax.swing.JLabel Puesto;
    private javax.swing.JComboBox<String> PuestoCB;
    private javax.swing.JTextField SalTxtF;
    private javax.swing.JLabel Salario;
    private javax.swing.JLabel TITULO;
    private javax.swing.JTextField UserTxtF;
    private javax.swing.JLabel Usuario;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldApMaterno;
    private javax.swing.JTextField jTextFieldApPaterno;
    private javax.swing.JTextField jTextFieldCorreo;
    private javax.swing.JTextField jTextFieldFechaReg;
    private javax.swing.JTextField jTextFieldNombre;
    private javax.swing.JTable resultsTable;
    private javax.swing.JTextField searchbar;
    // End of variables declaration//GEN-END:variables

    public void setLocation(double d) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
