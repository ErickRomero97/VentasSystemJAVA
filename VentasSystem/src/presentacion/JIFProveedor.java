/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import conexion.Conexion;
import dao.ProveedorDao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import logica.ProveedorLogica;

/**
 *
 * @author ERICK GALLARDO
 */
public class JIFProveedor extends javax.swing.JInternalFrame {

    /**
     * Creates new form JIFProveedor
     */
    public JIFProveedor() throws SQLException {
        initComponents();
        llenarTabla();
        llenarComboboxSexo();
        habilitarBotones(true, false, false, false, false);
    }

    //Está función valida los datos antes de realizar un insert o update para que no vayan datos en blanco.
    private boolean verificarDatos() throws SQLException{
        boolean estado = true;
        
        if(jFFCodigoProveedor.getText().trim().length() != 15){            
            jFFCodigoProveedor.requestFocus();
            JOptionPane.showMessageDialog(null,"Ingrese el Código del proveedor",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            estado = false;
        }else if(jTFNombre.getText().length() == 0){            
            jTFNombre.requestFocus();
            JOptionPane.showMessageDialog(null,"Ingrese el Nombre del proveedor",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            estado = false;
        }else if(jTFApellido.getText().length() == 0){            
            jTFApellido.requestFocus();
            JOptionPane.showMessageDialog(null,"Ingrese el Apellido del proveedor",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            estado = false;
        }else if(jTADireccion.getText().length() == 0){            
            jTADireccion.requestFocus();
            JOptionPane.showMessageDialog(null,"Ingrese la Dirección del proveedor",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            estado = false;
        }else if(jFFTelefono.getText().trim().length() != 9){            
            jFFTelefono.requestFocus();
            JOptionPane.showMessageDialog(null,"Ingrese el Teléfono del proveedor",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            estado = false;
        }else if(jCboSexo.getSelectedIndex()==0){            
            jCboSexo.requestFocus();
            JOptionPane.showMessageDialog(null,"Seleccione el sexo del proveedor",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            estado = false;
        }
        
        return estado;
    }
    
    //Esta función limpia todos los campos
    private void limpiar(){
        this.jFFCodigoProveedor.setText("");
        this.jTFNombre.setText("");
        this.jTFApellido.setText("");
        this.jTADireccion.setText("");
        this.jFFTelefono.setText("");       
        this.jCboSexo.setSelectedIndex(0);
    }
    
    //Esta función habilita los botones y la función habilitarTextField de acuerdo a donde se presione.
    private void habilitarBotones( boolean nuevo, boolean guardar, boolean actualizar, boolean cancelar, boolean valor){
        this.jFFCodigoProveedor.requestFocus();
        this.jBtnNuevo.setEnabled(nuevo);
        this.jBtnGuardar.setEnabled(guardar);
        this.jBtnActualizar.setEnabled(actualizar);
        this.jBtnCancelar.setEnabled(cancelar);
        habilitarTexField(valor);
    }
    
    //Esta función habilita los campos de texto
    private void habilitarTexField(boolean valor){
        this.jFFCodigoProveedor.setEnabled(valor);
        this.jTFNombre.setEnabled(valor);
        this.jTFApellido.setEnabled(valor);
        this.jTADireccion.setEnabled(valor);
        this.jFFTelefono.setEnabled(valor);
        this.jCboSexo.setEnabled(valor);
    }
    
    //Esta funcion carga el combobox Sexo con los datos.
    private void llenarComboboxSexo(){
        String consulta = "call sp_listarcomboboxsexo";
        try{
            PreparedStatement ps = Conexion.conectar().prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                jCboSexo.addItem(rs.getString("Sexo"));
            }
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
    }
    
    //Esta funcion limpia la jTable
    private void limpiarTabla(){
      
        DefaultTableModel temp = (DefaultTableModel) this.jTblDatosProveedor.getModel(); //
        
        // Limpiar los datos de la tabla.
        while (temp.getRowCount() > 0) {
            temp.removeRow(0);
        }
    }
    
        //Esta función llena la jTable de los datos del proveedoor
    private void llenarTabla() throws SQLException{
        limpiarTabla();
        ProveedorDao dao = new ProveedorDao();
        List<ProveedorLogica> miLista = dao.getLista(this.jTFFiltro.getText());
        
        DefaultTableModel temp = (DefaultTableModel) this.jTblDatosProveedor.getModel(); 
        
        miLista.stream().map((c1) -> {
            Object[] fila = new Object[6];
            fila[0] = c1.getIdProveedor();
            fila[1] = c1.getNombre();
            fila[2] = c1.getApellido();
            fila[3] = c1.getDireccion();
            fila[4] = c1.getTelefono();
            fila[5] = c1.getSexo();
            return fila;
        }).forEachOrdered((fila) -> {   
            temp.addRow(fila);            
        });
    }
    
    //Esta función guarda los datos del proveedor haciendo un insert en la base de datos.
    private void guardarCliente(){
        ProveedorLogica pl = new ProveedorLogica();
        
        pl.setIdProveedor(this.jFFCodigoProveedor.getText());
        pl.setNombre(this.jTFNombre.getText());
        pl.setApellido(this.jTFApellido.getText());
        pl.setDireccion(this.jTADireccion.getText());
        pl.setTelefono(this.jFFTelefono.getText());
        pl.setIdSexo(this.jCboSexo.getSelectedIndex());
        
        try {
            ProveedorDao dao = new ProveedorDao();
            dao.insertarProveedor(pl);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al almacenar el Proveedor " + e);
        }
    }
    
    //Esta función actualiza los datos del proveedor.
    private void editarProveedor(){
        ProveedorLogica pl = new ProveedorLogica();
        
        pl.setIdProveedor(this.jFFCodigoProveedor.getText());
        pl.setNombre(this.jTFNombre.getText());
        pl.setApellido(this.jTFApellido.getText());
        pl.setDireccion(this.jTADireccion.getText());
        pl.setTelefono(this.jFFTelefono.getText());
        pl.setIdSexo(this.jCboSexo.getSelectedIndex());
        
        try {
            ProveedorDao dao = new ProveedorDao();
            dao.actualizarProveedor(pl);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al editar el Proveedor " + e);
        }            
    }
    
    //Esta función devuelve los datos una vez seleccionado un registro en la jTable.
    private void filaSeleccionada(){
        String sexo = "";
        
        if(this.jTblDatosProveedor.getSelectedRow() != -1){
            if(this.jTblDatosProveedor.isEnabled() == true){
                this.jFFCodigoProveedor.setText(String.valueOf(jTblDatosProveedor.getValueAt(this.jTblDatosProveedor.getSelectedRow(), 0)));
                this.jTFNombre.setText(String.valueOf(jTblDatosProveedor.getValueAt(this.jTblDatosProveedor.getSelectedRow(), 1)));
                this.jTFApellido.setText(String.valueOf(jTblDatosProveedor.getValueAt(this.jTblDatosProveedor.getSelectedRow(), 2)));
                this.jTADireccion.setText(String.valueOf(jTblDatosProveedor.getValueAt(this.jTblDatosProveedor.getSelectedRow(), 3)));
                this.jFFTelefono.setText(String.valueOf(jTblDatosProveedor.getValueAt(this.jTblDatosProveedor.getSelectedRow(), 4)));
                this.jCboSexo.setSelectedItem(String.valueOf(this.jTblDatosProveedor.getValueAt(jTblDatosProveedor.getSelectedRow(), 5)));                         
            }            
        }else{
            limpiar();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblDatosProveedor = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jTFFiltro = new javax.swing.JTextField();
        jPDatosProveedor = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTFNombre = new javax.swing.JTextField();
        jTFApellido = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jFFTelefono = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTADireccion = new javax.swing.JTextArea();
        jFFCodigoProveedor = new javax.swing.JFormattedTextField();
        jCboSexo = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jBtnNuevo = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();
        jBtnActualizar = new javax.swing.JButton();
        jBtnCancelar = new javax.swing.JButton();

        setClosable(true);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Listado de Proveedores"));

        jTblDatosProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IdProveedor", "Nombre", "Apellido", "Direccion", "Telefono", "Sexo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTblDatosProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTblDatosProveedorMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTblDatosProveedor);

        jLabel7.setText("Buscar:");

        jTFFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTFFiltroKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTFFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPDatosProveedor.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Datos del Proveedor"));

        jLabel1.setText("Código Proveedor:");

        jLabel2.setText("Nombre:");

        jLabel3.setText("Apellido:");

        jLabel4.setText("Direccion:");

        jLabel5.setText("Teléfono:");

        try {
            jFFTelefono.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel6.setText("Sexo:");

        jTADireccion.setColumns(20);
        jTADireccion.setRows(5);
        jScrollPane2.setViewportView(jTADireccion);

        try {
            jFFCodigoProveedor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-####-#####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jCboSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Seleccione--" }));

        javax.swing.GroupLayout jPDatosProveedorLayout = new javax.swing.GroupLayout(jPDatosProveedor);
        jPDatosProveedor.setLayout(jPDatosProveedorLayout);
        jPDatosProveedorLayout.setHorizontalGroup(
            jPDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPDatosProveedorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPDatosProveedorLayout.createSequentialGroup()
                        .addGroup(jPDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPDatosProveedorLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFFCodigoProveedor))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPDatosProveedorLayout.createSequentialGroup()
                                .addGroup(jPDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPDatosProveedorLayout.createSequentialGroup()
                                        .addComponent(jTFNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTFApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane2))))
                        .addGap(22, 22, 22))
                    .addGroup(jPDatosProveedorLayout.createSequentialGroup()
                        .addGroup(jPDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jFFTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(jCboSexo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPDatosProveedorLayout.setVerticalGroup(
            jPDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPDatosProveedorLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jFFCodigoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTFNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jTFApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPDatosProveedorLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(81, 81, 81))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPDatosProveedorLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jFFTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPDatosProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jCboSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Opciones"));

        jBtnNuevo.setText("Nuevo");
        jBtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNuevoActionPerformed(evt);
            }
        });

        jBtnGuardar.setText("Guardar");
        jBtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarActionPerformed(evt);
            }
        });

        jBtnActualizar.setText("Actualizar");
        jBtnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnActualizarActionPerformed(evt);
            }
        });

        jBtnCancelar.setText("Cancelar");
        jBtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jBtnNuevo)
                .addGap(2, 2, 2)
                .addComponent(jBtnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBtnActualizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnCancelar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnNuevo)
                    .addComponent(jBtnGuardar)
                    .addComponent(jBtnActualizar)
                    .addComponent(jBtnCancelar))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPDatosProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPDatosProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Aqui se evalua cuando el usuario presiona un registro en la jTable y devuelve los datos en cada campo de edición.
    private void jTblDatosProveedorMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblDatosProveedorMousePressed
        habilitarBotones(false, false, true, true, true);
        filaSeleccionada();
        jFFCodigoProveedor.setEditable(false);
        jTFNombre.requestFocus();
    }//GEN-LAST:event_jTblDatosProveedorMousePressed

    //En este evento se evalua cuando el usuario escribe en la TextField  y devuelve el resultado actualizando la jTable.
    private void jTFFiltroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFFiltroKeyReleased
        //Este codigo convierte lo que escribimos en mayúscula
        this.jTFFiltro.setText(this.jTFFiltro.getText().toUpperCase());
        try {
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(JIFCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTFFiltroKeyReleased

    //En este evento del boton nuevo se habilitan los botones y los campos de texto
    private void jBtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnNuevoActionPerformed
        // TODO add your handling code here:
        limpiar();
        habilitarBotones(false,true,false,true, true);
        this.jFFCodigoProveedor.requestFocus();
        this.jFFCodigoProveedor.setEditable(true);
    }//GEN-LAST:event_jBtnNuevoActionPerformed

    //En este evento se verifican lso datos antes de proceder a guardar los datos.
    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        // TODO add your handling code here:
        try {
            if(verificarDatos() == true){
                guardarCliente();
                llenarTabla();
                habilitarBotones(true,false,false,false, false);
                limpiar();
                JOptionPane.showMessageDialog(null,"Datos guardados satisfactoriamente",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JIFCliente.class.getName()).log(Level.SEVERE,null,ex);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    //En este evente se verifican los datos y se actualiza la informacion del proveedor.
    private void jBtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnActualizarActionPerformed
        // TODO add your handling code here:
        try {
            if(verificarDatos() == true){
                editarProveedor();
                llenarTabla();
                habilitarBotones(true,false,false,false, false);
                limpiar();
                JOptionPane.showMessageDialog(null,"Datos actualizados satisfactoriamente",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JIFProveedor.class.getName()).log(Level.SEVERE,null,ex);
        }
    }//GEN-LAST:event_jBtnActualizarActionPerformed
    
    //En este evento se cancela los cambios que se están realizando.
    private void jBtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCancelarActionPerformed
        limpiar();
        habilitarBotones(true,false,false,false, false);
    }//GEN-LAST:event_jBtnCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnActualizar;
    private javax.swing.JButton jBtnCancelar;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnNuevo;
    private javax.swing.JComboBox<String> jCboSexo;
    private javax.swing.JFormattedTextField jFFCodigoProveedor;
    private javax.swing.JFormattedTextField jFFTelefono;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPDatosProveedor;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTADireccion;
    private javax.swing.JTextField jTFApellido;
    private javax.swing.JTextField jTFFiltro;
    private javax.swing.JTextField jTFNombre;
    private javax.swing.JTable jTblDatosProveedor;
    // End of variables declaration//GEN-END:variables
}
