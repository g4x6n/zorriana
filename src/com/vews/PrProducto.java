package com.vews;
import database.dao.DaoProducto;
import database.dao.DaoProveedor;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Alex
 */
public class PrProducto extends javax.swing.JPanel {
    
    private final DaoProducto daoProducto = new DaoProducto();

    public static PrProducto prod;

    public PrProducto() {
        initComponents();
        cargarProveedores();
        cargarMarcas();
    }
    
    private void cargarProveedores() {
        // Llenar la lista de proveedores en el combo box
        try {
            List<String> proveedores = daoProducto.obtenerProveedor();
            Prov_Box.removeAllItems();
            for (String proveedor : proveedores) {
                Prov_Box.addItem(proveedor);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar proveedores: " + e.getMessage());
        }
    }
    
    private void cargarMarcas() {
        // Llenar la lista de marcas en el combo box
        try {
            List<String> marcas = daoProducto.obtenerMarca();
            Marca_Box.removeAllItems();
            for (String marca : marcas) {
                Marca_Box.addItem(marca);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar marcas: " + e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        fondo = new javax.swing.JPanel();
        searchbar1 = new javax.swing.JTextField();
        FILTRO = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        resultsTable1 = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        productos = new javax.swing.JPanel();
        Nombre = new javax.swing.JLabel();
        NomTxtF = new javax.swing.JTextField();
        PROVEEDOR = new javax.swing.JLabel();
        Prov_Box = new javax.swing.JComboBox<>();
        DESCRIPCION = new javax.swing.JLabel();
        DescTxtF = new javax.swing.JTextField();
        MARCA = new javax.swing.JLabel();
        Marca_Box = new javax.swing.JComboBox<>();
        EdoProducto = new javax.swing.JLabel();
        EdoProd_Box = new javax.swing.JComboBox<>();
        Categoria = new javax.swing.JLabel();
        CatTxtF = new javax.swing.JComboBox<>();
        SKU = new javax.swing.JLabel();
        SKUTxtF = new javax.swing.JTextField();
        Stock = new javax.swing.JLabel();
        StockTxtF = new javax.swing.JTextField();
        Precio = new javax.swing.JLabel();
        PrecioTxtF = new javax.swing.JTextField();
        Piso = new javax.swing.JLabel();
        PisoTxtF = new javax.swing.JTextField();
        Zona = new javax.swing.JLabel();
        ZonaTxtF = new javax.swing.JTextField();
        Estantería = new javax.swing.JLabel();
        EstantTxtF = new javax.swing.JTextField();
        GuardarBot = new javax.swing.JLabel();
        ELIMINAR_BOTON = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        AGREGAR_BOTON = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(940, 570));

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setPreferredSize(new java.awt.Dimension(1040, 560));

        searchbar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchbar1ActionPerformed(evt);
            }
        });

        FILTRO.setText("FILTRAR:");

        resultsTable1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        resultsTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "NOMBRE", "MARCA ", "CATEGORÍA", "PRECIO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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
        jScrollPane2.setViewportView(resultsTable1);
        resultsTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jLabel9.setFont(new java.awt.Font("Jost", 0, 48)); // NOI18N
        jLabel9.setText("PRODUCTO");

        productos.setBackground(new java.awt.Color(255, 255, 255));
        productos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        productos.setForeground(new java.awt.Color(204, 204, 204));

        Nombre.setText("NOMBRE");

        NomTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NomTxtFActionPerformed(evt);
            }
        });

        PROVEEDOR.setText("PROVEEDOR");

        Prov_Box.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Prov_Box.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Prov_Box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Prov_BoxActionPerformed(evt);
            }
        });

        DESCRIPCION.setText("DESCRIPCIÓN");

        DescTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DescTxtFActionPerformed(evt);
            }
        });

        MARCA.setText("MARCA");

        Marca_Box.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Marca_Box.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Marca_Box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Marca_BoxActionPerformed(evt);
            }
        });

        EdoProducto.setText("ESTADO DEL PRODUCTO");

        EdoProd_Box.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        EdoProd_Box.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EdoProd_Box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EdoProd_BoxActionPerformed(evt);
            }
        });

        Categoria.setText("CATEGORIA");

        CatTxtF.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        CatTxtF.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CatTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CatTxtFActionPerformed(evt);
            }
        });

        SKU.setText("SKU");

        SKUTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SKUTxtFActionPerformed(evt);
            }
        });

        Stock.setText("STOCK");

        StockTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StockTxtFActionPerformed(evt);
            }
        });

        Precio.setText("PRECIO");

        PrecioTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrecioTxtFActionPerformed(evt);
            }
        });

        Piso.setText("PISO");

        PisoTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PisoTxtFActionPerformed(evt);
            }
        });

        Zona.setText("ZONA");

        ZonaTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ZonaTxtFActionPerformed(evt);
            }
        });

        Estantería.setText("ESTANTERIA");

        EstantTxtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EstantTxtFActionPerformed(evt);
            }
        });

        GuardarBot.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/guardar.png"))); // NOI18N
        GuardarBot.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        ELIMINAR_BOTON.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eliminar.png"))); // NOI18N
        ELIMINAR_BOTON.setContentAreaFilled(false);
        ELIMINAR_BOTON.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout productosLayout = new javax.swing.GroupLayout(productos);
        productos.setLayout(productosLayout);
        productosLayout.setHorizontalGroup(
            productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productosLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(Nombre)
                .addGap(11, 11, 11)
                .addComponent(NomTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(PROVEEDOR)
                .addGap(14, 14, 14)
                .addComponent(Prov_Box, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(productosLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(DESCRIPCION)
                .addGap(6, 6, 6)
                .addComponent(DescTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(productosLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(MARCA)
                .addGap(8, 8, 8)
                .addComponent(Marca_Box, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(EdoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(EdoProd_Box, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(productosLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(Categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(CatTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(SKU)
                .addGap(19, 19, 19)
                .addComponent(SKUTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(productosLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(Stock)
                .addGap(14, 14, 14)
                .addComponent(StockTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(110, 110, 110)
                .addComponent(Precio)
                .addGap(10, 10, 10)
                .addComponent(PrecioTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(productosLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(Piso)
                .addGap(15, 15, 15)
                .addComponent(PisoTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(Zona)
                .addGap(7, 7, 7)
                .addComponent(ZonaTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(Estantería)
                .addGap(5, 5, 5)
                .addComponent(EstantTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(productosLayout.createSequentialGroup()
                .addGap(369, 369, 369)
                .addGroup(productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GuardarBot, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(productosLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(ELIMINAR_BOTON))))
        );
        productosLayout.setVerticalGroup(
            productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productosLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NomTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PROVEEDOR, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Prov_Box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DESCRIPCION, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DescTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MARCA, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Marca_Box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EdoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EdoProd_Box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CatTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SKU, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SKUTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Stock, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(StockTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Precio, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PrecioTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Piso, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PisoTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Zona, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ZonaTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Estantería, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EstantTxtF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addGroup(productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GuardarBot, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ELIMINAR_BOTON)))
        );

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/añadir producto.png"))); // NOI18N
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        AGREGAR_BOTON.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/producto.png"))); // NOI18N
        AGREGAR_BOTON.setContentAreaFilled(false);
        AGREGAR_BOTON.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AGREGAR_BOTONActionPerformed(evt);
            }
        });

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout fondoLayout = new javax.swing.GroupLayout(fondo);
        fondo.setLayout(fondoLayout);
        fondoLayout.setHorizontalGroup(
            fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoLayout.createSequentialGroup()
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(fondoLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(productos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(fondoLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(FILTRO)
                                .addGap(4, 4, 4)
                                .addComponent(searchbar1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel11))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(fondoLayout.createSequentialGroup()
                        .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(fondoLayout.createSequentialGroup()
                                .addGap(320, 320, 320)
                                .addComponent(jLabel9))
                            .addGroup(fondoLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(AGREGAR_BOTON)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        fondoLayout.setVerticalGroup(
            fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoLayout.createSequentialGroup()
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(fondoLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(fondoLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(productos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(fondoLayout.createSequentialGroup()
                                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(fondoLayout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(FILTRO, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(searchbar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(fondoLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel11)))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(fondoLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(AGREGAR_BOTON)))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

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

    private void searchbar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchbar1ActionPerformed

    }//GEN-LAST:event_searchbar1ActionPerformed

    private void resultsTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resultsTable1MouseClicked

    }//GEN-LAST:event_resultsTable1MouseClicked

    private void AGREGAR_BOTONActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AGREGAR_BOTONActionPerformed
 
    }//GEN-LAST:event_AGREGAR_BOTONActionPerformed

    private void Prov_BoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Prov_BoxActionPerformed
   
    }//GEN-LAST:event_Prov_BoxActionPerformed

    private void CatTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CatTxtFActionPerformed
 
    }//GEN-LAST:event_CatTxtFActionPerformed

    private void NomTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NomTxtFActionPerformed
    
    }//GEN-LAST:event_NomTxtFActionPerformed

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel11MouseClicked

    private void DescTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DescTxtFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DescTxtFActionPerformed

    private void Marca_BoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Marca_BoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Marca_BoxActionPerformed

    private void EdoProd_BoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EdoProd_BoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EdoProd_BoxActionPerformed

    private void SKUTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SKUTxtFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SKUTxtFActionPerformed

    private void StockTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StockTxtFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_StockTxtFActionPerformed

    private void PrecioTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrecioTxtFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PrecioTxtFActionPerformed

    private void PisoTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PisoTxtFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PisoTxtFActionPerformed

    private void ZonaTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ZonaTxtFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ZonaTxtFActionPerformed

    private void EstantTxtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EstantTxtFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EstantTxtFActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AGREGAR_BOTON;
    private javax.swing.JComboBox<String> CatTxtF;
    private javax.swing.JLabel Categoria;
    private javax.swing.JLabel DESCRIPCION;
    private javax.swing.JTextField DescTxtF;
    private javax.swing.JButton ELIMINAR_BOTON;
    private javax.swing.JComboBox<String> EdoProd_Box;
    private javax.swing.JLabel EdoProducto;
    private javax.swing.JTextField EstantTxtF;
    private javax.swing.JLabel Estantería;
    private javax.swing.JLabel FILTRO;
    private javax.swing.JLabel GuardarBot;
    private javax.swing.JLabel MARCA;
    private javax.swing.JComboBox<String> Marca_Box;
    private javax.swing.JTextField NomTxtF;
    private javax.swing.JLabel Nombre;
    private javax.swing.JLabel PROVEEDOR;
    private javax.swing.JLabel Piso;
    private javax.swing.JTextField PisoTxtF;
    private javax.swing.JLabel Precio;
    private javax.swing.JTextField PrecioTxtF;
    private javax.swing.JComboBox<String> Prov_Box;
    private javax.swing.JLabel SKU;
    private javax.swing.JTextField SKUTxtF;
    private javax.swing.JLabel Stock;
    private javax.swing.JTextField StockTxtF;
    private javax.swing.JLabel Zona;
    private javax.swing.JTextField ZonaTxtF;
    private javax.swing.JPanel bg;
    private javax.swing.JPanel fondo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel productos;
    private javax.swing.JTable resultsTable1;
    private javax.swing.JTextField searchbar1;
    // End of variables declaration//GEN-END:variables

    public void setLocation(double d) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
