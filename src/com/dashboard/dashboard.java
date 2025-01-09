/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

package com.dashboard;

import java.awt.event.ActionEvent;
import com.vews.PrCliente;
import com.vews.AgVenta;
import com.dashboard.dashboard; // Asegúrate de importar la clase dashboard
import com.vews.PrVenta;
import com.vews.PrCompras;
import com.vews.PrEmpleado;
import com.vews.PrProducto;
import com.vews.muestra;
import com.vews.PrProveedor;
import com.vews.PrVenta;
import com.vews.Principal;
import java.awt.BorderLayout;
import java.time.LocalDate;
import tools.UtilsGUI;
import database.dao.DaoClientes;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Alex/ldelatorrep2000/CesarCarmona
 */
public class dashboard extends javax.swing.JFrame {
    private String usuarioAutenticado;
    private String idEmpleado;
    private List<String> permisos;

    public dashboard(String nombreCompleto, String idEmpleado, List<String> permisos) {
        this.usuarioAutenticado = nombreCompleto;
        this.idEmpleado = idEmpleado;
        this.permisos = permisos;
        
        initComponents();
        configComponents();
        configurarAcceso(); // Configura el acceso según los permisos
        SetUsuario();
        SetDate();
        startClock();
        initContent();
    }

       
       private void SetUsuario() {
        if (usuarioAutenticado != null && !usuarioAutenticado.isEmpty()) {
            USUARIOTXT.setText(usuarioAutenticado); // Establece el nombre del usuario
        } else {
            USUARIOTXT.setText("Usuario no identificado");
        }
    }
    
      private void configurarAcceso() {
    // Inicialmente, deshabilita todos los botones
    Boton_Cliente.setEnabled(false);
    Boton_Ventas.setEnabled(false);
    Boton_Empleados.setEnabled(false);
    Boton_Compras.setEnabled(false);
    Boton_Proveedores.setEnabled(false);
    Boton_Productos.setEnabled(false);
    Boton_Inicio.setEnabled(true); // Inicio siempre debe estar habilitado

    // Habilita solo los botones según los permisos del rol
    if (permisos.contains("Clientes")) {
        Boton_Cliente.setEnabled(true);
    }
    if (permisos.contains("Ventas")) {
        Boton_Ventas.setEnabled(true);
    }
    if (permisos.contains("Empleados")) {
        Boton_Empleados.setEnabled(true);
    }
    if (permisos.contains("Compras")) {
        Boton_Compras.setEnabled(true);
    }
    if (permisos.contains("Proveedores")) {
        Boton_Proveedores.setEnabled(true);
    }
    if (permisos.contains("Productos")) {
        Boton_Productos.setEnabled(true);
    }
}

      
    private void initContent(){
        ShowJPanel (new Principal());
        
    }
    
    private void ShowJPanel(JPanel p){
        p.setSize(920,580);
        p.setLocation(0,0);
        
        content.removeAll();
        content.add(p,BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }
    private void configComponents(){
        // Titulo de la ventana
        setTitle("Menu principal Zorriana");
        // posición de la ventana
        setLocationRelativeTo(null);
    }
    
 /* private void SetUsuario() {
    // Dividir el nombre completo en partes (separadas por espacios)
    String[] partesNombre = usuarioAutenticado.split(" ");

    // Verificar si hay al menos dos palabras
    if (partesNombre.length >= 2) {
        // El primer nombre es siempre la primera palabra
        String primerNombre = partesNombre[0];

        // Construir el apellido paterno desde la segunda palabra hasta la penúltima (si hay más de dos palabras)
        StringBuilder apellidoPaterno = new StringBuilder();
        for (int i = 1; i < partesNombre.length - 1; i++) {
            apellidoPaterno.append(partesNombre[i]).append(" ");
        }

        // Si el nombre tiene solo dos palabras, la segunda palabra es el apellido paterno
        if (partesNombre.length == 2) {
            apellidoPaterno.append(partesNombre[1]);
        }

        // Remover el espacio extra al final
        String apellidoPaternoFinal = apellidoPaterno.toString().trim();

        // Formatear y establecer el texto en USUARIOTXT
        USUARIOTXT.setText(primerNombre + " " + apellidoPaternoFinal);
    } else {
        // Si solo hay un nombre, mostrarlo
        USUARIOTXT.setText(usuarioAutenticado);
    }

    // Estilo del texto
    USUARIOTXT.setFont(new java.awt.Font("Jost", 1, 14)); // Estilo del texto
    USUARIOTXT.setForeground(new java.awt.Color(255, 255, 255)); // Color del texto
}
*/
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        Menu = new javax.swing.JPanel();
        Boton_Inicio = new javax.swing.JButton();
        Boton_Cliente = new javax.swing.JButton();
        Boton_Empleados = new javax.swing.JButton();
        Boton_Proveedores = new javax.swing.JButton();
        Boton_Ventas = new javax.swing.JButton();
        Boton_Compras = new javax.swing.JButton();
        Boton_Productos = new javax.swing.JButton();
        Boton_Salida = new javax.swing.JButton();
        Header = new javax.swing.JPanel();
        Label_Dia = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        dateText = new javax.swing.JLabel();
        USUARIOTXT = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        content = new javax.swing.JPanel();

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage( getClass().getResource("/img/icono.png")));
        setResizable(false);

        jPanel1.setPreferredSize(new java.awt.Dimension(642, 552));

        Menu.setBackground(new java.awt.Color(247, 139, 86));

        Boton_Inicio.setFont(new java.awt.Font("Jost", 1, 18)); // NOI18N
        Boton_Inicio.setForeground(new java.awt.Color(255, 255, 255));
        Boton_Inicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Home_Icon.png"))); // NOI18N
        Boton_Inicio.setText("INICIO");
        Boton_Inicio.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 10, 1, 1, new java.awt.Color(0, 0, 0)));
        Boton_Inicio.setBorderPainted(false);
        Boton_Inicio.setContentAreaFilled(false);
        Boton_Inicio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Boton_Inicio.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Boton_Inicio.setIconTextGap(25);
        Boton_Inicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Boton_InicioActionPerformed(evt);
            }
        });

        Boton_Cliente.setFont(new java.awt.Font("Jost", 1, 18)); // NOI18N
        Boton_Cliente.setForeground(new java.awt.Color(255, 255, 255));
        Boton_Cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cliente.png"))); // NOI18N
        Boton_Cliente.setText("CLIENTES");
        Boton_Cliente.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 10, 1, 1, new java.awt.Color(0, 0, 0)));
        Boton_Cliente.setBorderPainted(false);
        Boton_Cliente.setContentAreaFilled(false);
        Boton_Cliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Boton_Cliente.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Boton_Cliente.setIconTextGap(25);
        Boton_Cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Boton_ClienteActionPerformed(evt);
            }
        });

        Boton_Empleados.setFont(new java.awt.Font("Jost", 1, 18)); // NOI18N
        Boton_Empleados.setForeground(new java.awt.Color(255, 255, 255));
        Boton_Empleados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/empleado.png"))); // NOI18N
        Boton_Empleados.setText("EMPLEADOS");
        Boton_Empleados.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 10, 1, 1, new java.awt.Color(0, 0, 0)));
        Boton_Empleados.setBorderPainted(false);
        Boton_Empleados.setContentAreaFilled(false);
        Boton_Empleados.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Boton_Empleados.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Boton_Empleados.setIconTextGap(25);
        Boton_Empleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Boton_EmpleadosActionPerformed(evt);
            }
        });

        Boton_Proveedores.setFont(new java.awt.Font("Jost", 1, 18)); // NOI18N
        Boton_Proveedores.setForeground(new java.awt.Color(255, 255, 255));
        Boton_Proveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/camion.png"))); // NOI18N
        Boton_Proveedores.setText("PROVEEDORES");
        Boton_Proveedores.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 10, 1, 1, new java.awt.Color(0, 0, 0)));
        Boton_Proveedores.setBorderPainted(false);
        Boton_Proveedores.setContentAreaFilled(false);
        Boton_Proveedores.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Boton_Proveedores.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Boton_Proveedores.setIconTextGap(25);
        Boton_Proveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Boton_ProveedoresActionPerformed(evt);
            }
        });

        Boton_Ventas.setFont(new java.awt.Font("Jost", 1, 18)); // NOI18N
        Boton_Ventas.setForeground(new java.awt.Color(255, 255, 255));
        Boton_Ventas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/venta.png"))); // NOI18N
        Boton_Ventas.setText("VENTAS");
        Boton_Ventas.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 10, 1, 1, new java.awt.Color(0, 0, 0)));
        Boton_Ventas.setBorderPainted(false);
        Boton_Ventas.setContentAreaFilled(false);
        Boton_Ventas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Boton_Ventas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Boton_Ventas.setIconTextGap(25);
        Boton_Ventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Boton_VentasActionPerformed(evt);
            }
        });

        Boton_Compras.setFont(new java.awt.Font("Jost", 1, 18)); // NOI18N
        Boton_Compras.setForeground(new java.awt.Color(255, 255, 255));
        Boton_Compras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/compra.png"))); // NOI18N
        Boton_Compras.setText("COMPRAS");
        Boton_Compras.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 10, 1, 1, new java.awt.Color(0, 0, 0)));
        Boton_Compras.setBorderPainted(false);
        Boton_Compras.setContentAreaFilled(false);
        Boton_Compras.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Boton_Compras.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Boton_Compras.setIconTextGap(25);
        Boton_Compras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Boton_ComprasActionPerformed(evt);
            }
        });

        Boton_Productos.setFont(new java.awt.Font("Jost", 1, 18)); // NOI18N
        Boton_Productos.setForeground(new java.awt.Color(255, 255, 255));
        Boton_Productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/producto.png"))); // NOI18N
        Boton_Productos.setText("PRODUCTOS");
        Boton_Productos.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 10, 1, 1, new java.awt.Color(0, 0, 0)));
        Boton_Productos.setBorderPainted(false);
        Boton_Productos.setContentAreaFilled(false);
        Boton_Productos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Boton_Productos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Boton_Productos.setIconTextGap(25);
        Boton_Productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Boton_ProductosActionPerformed(evt);
            }
        });

        Boton_Salida.setFont(new java.awt.Font("Jost", 1, 24)); // NOI18N
        Boton_Salida.setForeground(new java.awt.Color(255, 255, 255));
        Boton_Salida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/salida.png"))); // NOI18N
        Boton_Salida.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 10, 1, 1, new java.awt.Color(0, 0, 0)));
        Boton_Salida.setBorderPainted(false);
        Boton_Salida.setContentAreaFilled(false);
        Boton_Salida.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Boton_Salida.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Boton_Salida.setIconTextGap(10);
        Boton_Salida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Boton_SalidaMouseClicked(evt);
            }
        });
        Boton_Salida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Boton_SalidaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout MenuLayout = new javax.swing.GroupLayout(Menu);
        Menu.setLayout(MenuLayout);
        MenuLayout.setHorizontalGroup(
            MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Boton_Salida, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(Boton_Inicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Boton_Cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Boton_Empleados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Boton_Ventas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Boton_Compras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Boton_Productos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Boton_Proveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        MenuLayout.setVerticalGroup(
            MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Boton_Inicio, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Boton_Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Boton_Empleados, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Boton_Proveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Boton_Ventas, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Boton_Compras, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Boton_Productos, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Boton_Salida, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        Header.setBackground(new java.awt.Color(102, 51, 0));

        Label_Dia.setFont(new java.awt.Font("Jost", 1, 36)); // NOI18N
        Label_Dia.setForeground(new java.awt.Color(255, 255, 255));
        Label_Dia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Label_Dia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo blanquito.png"))); // NOI18N

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator1.setAlignmentY(1.0F);
        jSeparator1.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N

        dateText.setFont(new java.awt.Font("Jost", 1, 14)); // NOI18N
        dateText.setForeground(new java.awt.Color(255, 255, 255));
        dateText.setText("{dayname} {day} {month} {year}");

        USUARIOTXT.setFont(new java.awt.Font("Jost", 1, 14)); // NOI18N
        USUARIOTXT.setForeground(new java.awt.Color(255, 255, 255));
        USUARIOTXT.setText("jLabel1");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/clock.png"))); // NOI18N
        jLabel1.setText("jLabel1");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/user.png"))); // NOI18N

        javax.swing.GroupLayout HeaderLayout = new javax.swing.GroupLayout(Header);
        Header.setLayout(HeaderLayout);
        HeaderLayout.setHorizontalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Label_Dia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 545, Short.MAX_VALUE)
                .addGroup(HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateText)
                    .addComponent(USUARIOTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
        );
        HeaderLayout.setVerticalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HeaderLayout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HeaderLayout.createSequentialGroup()
                        .addGroup(HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dateText, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(USUARIOTXT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(Label_Dia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        content.setBackground(new java.awt.Color(255, 255, 255));
        content.setMinimumSize(new java.awt.Dimension(0, 0));
        content.setName(""); // NOI18N
        content.setPreferredSize(new java.awt.Dimension(642, 552));

        javax.swing.GroupLayout contentLayout = new javax.swing.GroupLayout(content);
        content.setLayout(contentLayout);
        contentLayout.setHorizontalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        contentLayout.setVerticalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(Menu, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, 952, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(Header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1172, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    private void Boton_InicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Boton_InicioActionPerformed
     ShowJPanel (new Principal ());   
    }//GEN-LAST:event_Boton_InicioActionPerformed

    private void Boton_ClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Boton_ClienteActionPerformed
        ShowJPanel (new PrCliente ());  
    }//GEN-LAST:event_Boton_ClienteActionPerformed

    private void Boton_SalidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Boton_SalidaMouseClicked
        muestra broma = new muestra(); // Crea la pantalla de broma
        broma.mostrarConTemporizador(2); // Mostrarla por 3 segundos
        // TODO add your handling code here:
    }//GEN-LAST:event_Boton_SalidaMouseClicked

    private void Boton_ProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Boton_ProveedoresActionPerformed
    ShowJPanel (new PrProveedor ());  
        // TODO add your handling code here:
    }//GEN-LAST:event_Boton_ProveedoresActionPerformed

    private void Boton_ProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Boton_ProductosActionPerformed
        ShowJPanel (new PrProducto ());  
        // TODO add your handling code here:
    }//GEN-LAST:event_Boton_ProductosActionPerformed

    private void Boton_SalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Boton_SalidaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Boton_SalidaActionPerformed

    private void Boton_EmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Boton_EmpleadosActionPerformed
      ShowJPanel (new PrEmpleado ());  
    }//GEN-LAST:event_Boton_EmpleadosActionPerformed

    private void Boton_VentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Boton_VentasActionPerformed
   // Crear una nueva instancia de PrVenta pasando el dashboard actual
    PrVenta prVenta = new PrVenta(this); 
    ShowJPanel(prVenta);  // Mostrar el panel de ventas

    }//GEN-LAST:event_Boton_VentasActionPerformed

    private void Boton_ComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Boton_ComprasActionPerformed
    // Crear una nueva instancia de PrCompras y pasar el usuario autenticado
    PrCompras comprasPanel = new PrCompras(usuarioAutenticado);
    ShowJPanel(comprasPanel);  
    }//GEN-LAST:event_Boton_ComprasActionPerformed
    private void SetDate (){
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        
        int year = now.getYear();
        int dia = now.getDayOfMonth();
        int month = now.getMonthValue();
        String[] meses = {"Enero" , "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre" ,"Diciembre" ,};
        
        int hour = now.getHour();
        int minute = now.getMinute();
        int segundos = now.getSecond();
        
        // Convertir a formato de 12 horas
        String amPm = (hour >= 12) ? "PM" : "AM";
        hour = (hour % 12 == 0) ? 12 : (hour % 12);

        // Formatear la fecha y hora
        String fechaHora = String.format("%02d %s %d - %02d:%02d:%02d %s", dia, meses[month - 1], year, hour, minute,segundos,amPm);
        dateText.setText(fechaHora);
    }
   private void startClock() {
    javax.swing.Timer timer = new javax.swing.Timer(1000, e -> SetDate());
    timer.start();
}

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (ClassNotFoundException ex) {
        java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
        java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
        java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            String nombreCompleto = "Nombre del Usuario"; // Ejemplo de nombre completo
            String idEmpleado = "E12345"; // Ejemplo de ID de empleado
            List<String> permisos = java.util.Arrays.asList("Clientes", "Ventas", "Empleados", "Compras");
            new dashboard(nombreCompleto, idEmpleado, permisos).setVisible(true);
        }
    });
}

public String getUsuarioAutenticado() {
    return usuarioAutenticado;
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Boton_Cliente;
    private javax.swing.JButton Boton_Compras;
    private javax.swing.JButton Boton_Empleados;
    private javax.swing.JButton Boton_Inicio;
    private javax.swing.JButton Boton_Productos;
    private javax.swing.JButton Boton_Proveedores;
    private javax.swing.JButton Boton_Salida;
    private javax.swing.JButton Boton_Ventas;
    private javax.swing.JPanel Header;
    private javax.swing.JLabel Label_Dia;
    private javax.swing.JPanel Menu;
    private javax.swing.JLabel USUARIOTXT;
    private javax.swing.JPanel content;
    private javax.swing.JLabel dateText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
