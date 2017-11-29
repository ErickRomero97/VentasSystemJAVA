
package presentacion;
import dao.ClientesDao;
import logica.ClientesLogica;
import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class JIFCliente extends javax.swing.JInternalFrame {

    public JIFCliente() throws SQLException {
        initComponents();
        llenarTabla();
        habilitarBotones(true,false,false,false, false);
        llenarComboBoxSexo();
    }
    private void habilitarBotones( boolean nuevo, boolean guardar, boolean actualizar, boolean cancelar, boolean valor){
        this.jBtnNuevo.setEnabled(nuevo);
        this.jBtnGuardar.setEnabled(guardar);
        this.jBtnActualizar.setEnabled(actualizar);
        this.jBtnCancelar.setEnabled(cancelar);
        habilitarTexField(valor);
    }
    
    private void habilitarTexField(boolean valor){
        this.jTFCodigo.setEnabled(valor);
        this.jTFNombre.setEnabled(valor);
        this.jTFApellido.setEnabled(valor);
        this.jTFTelefono.setEnabled(valor);
        this.jTFDireccion.setEnabled(valor);
    }
    private void limpiarTabla(){
        DefaultTableModel temp = (DefaultTableModel) this.jTbMostrar.getModel(); 

        while (temp.getRowCount() > 0) {
            temp.removeRow(0);
     }
    }
    
    private void llenarTabla() throws SQLException 
    {
        limpiarTabla();
        
        ClientesDao dao = new ClientesDao();
        
        List<ClientesLogica> miLista = dao.getLista(this.jTFFiltro.getText());
        
        DefaultTableModel tabla = (DefaultTableModel) this.jTbMostrar.getModel();
        
        miLista.stream().map((CLClientes) -> {
           Object [] fila = new Object [6];
           fila[0] = CLClientes.getRtnCliente();
            fila[1] = CLClientes.getNombre();
            fila[2] = CLClientes.getApellido();
            fila[3] = CLClientes.getTelefono();
            fila[4] = CLClientes.getDireccion();
            fila[5] = CLClientes.getIdSSexo();
            return fila;
        }).forEachOrdered((fila) -> {
            tabla.addRow(fila);
        });    
    }
    
    private void lineaSeleccionada(){
        if (this.jTbMostrar.getSelectedRow() != -1) {
            //Habilito los controles para que se pueda hacer una accion.
            
            
                this.jTPListarCliente.setSelectedIndex(0);
                this.jTFFiltro.setText("");
                habilitarBotones(false,false,true,true,true);
                this.jTFCodigo.setText(String.valueOf(this.jTbMostrar.getValueAt(jTbMostrar.getSelectedRow(), 0)));
                this.jTFNombre.setText(String.valueOf(this.jTbMostrar.getValueAt(jTbMostrar.getSelectedRow(), 1)));
                this.jTFApellido.setText(String.valueOf(this.jTbMostrar.getValueAt(jTbMostrar.getSelectedRow(), 2)));
                this.jTFTelefono.setText(String.valueOf(this.jTbMostrar.getValueAt(jTbMostrar.getSelectedRow(), 3)));
                this.jTFDireccion.setText(String.valueOf(this.jTbMostrar.getValueAt(jTbMostrar.getSelectedRow(), 4)));
                this.jCbosexo.setSelectedIndex(Integer.parseInt(String.valueOf(this.jTbMostrar.getValueAt(jTbMostrar.getSelectedRow(), 5))));
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione el Cliente a Editar");
        }
    }
    private void limpiar()
    {
        this.jTFCodigo.setText("");
        this.jTFNombre.setText("");
        this.jTFApellido.setText("");
        this.jTFTelefono.setText("");
        this.jTFDireccion.setText("");
    }
    private void guardarCliente(){
        
        ClientesLogica cl = new ClientesLogica();
        
        cl.setRtnCliente(this.jTFCodigo.getText());
        cl.setNombre(this.jTFNombre.getText());
        cl.setApellido(this.jTFApellido.getText());
        cl.setTelefono(this.jTFTelefono.getText());
        cl.setDireccion(this.jTFDireccion.getText());
        cl.setIdSexo(this.jCbosexo.getSelectedIndex());
        
        try{
            ClientesDao dao = new ClientesDao();
            dao.insertarCliente(cl);
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al Almacenar el Cliente " + e);
        }
    }
    private void llenarComboBoxSexo(){
        String consulta = "select sexo from sexo";
        try{
            PreparedStatement ps = Conexion.conectar().prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                jCbosexo.addItem(rs.getString("Sexo"));
            }
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
    }
    private void editarCliente()
    {
        ClientesLogica cl = new ClientesLogica();
        cl.setRtnCliente(this.jTFCodigo.getText());
        cl.setNombre(this.jTFNombre.getText());
        cl.setApellido(this.jTFApellido.getText());
        cl.setTelefono(this.jTFTelefono.getText());
        cl.setDireccion(this.jTFDireccion.getText());
        cl.setIdSexo(this.jCbosexo.getSelectedIndex());
        
        try{
            ClientesDao dao = new ClientesDao();
            dao.editarCliente(cl);     
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al Actualizar el Cliente: " + e);
            
        }
    }
    
    private boolean verificarDatos() throws SQLException
    {
        boolean valor = true;
        
        if (this.jTFCodigo.getText().length()==0){
            JOptionPane.showMessageDialog(null,"Ingrese el RTN del Cliente",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            this.jTFCodigo.requestFocus();
            valor = false;
        }else if ((this.jTFNombre.getText().length())== 0 ){
            JOptionPane.showMessageDialog(null,"Ingrese el Nombre del Cliente",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            this.jTFNombre.requestFocus();
            valor = false;
        }else if ((this.jTFApellido.getText().length())== 0){
            JOptionPane.showMessageDialog(null,"Ingrese el Apellido del Cliente",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            this.jTFApellido.requestFocus();
            valor = false;
        }else if (this.jTFTelefono.getText().length()== 0){
            JOptionPane.showMessageDialog(null,"Ingrese el Telefono del Cliente",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            this.jTFTelefono.requestFocus();
            valor = false;
        }else if ((this.jTFDireccion.getText().length())== 0){
            JOptionPane.showMessageDialog(null,"Ingrese la Direccion del Cliente",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            this.jTFDireccion.requestFocus();
            valor = false;
        }else if ((this.jCbosexo.getSelectedIndex())== 0 ){
            JOptionPane.showMessageDialog(null,"Seleccione el Sexo del Cliente",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            this.jTFDireccion.requestFocus();
            valor = false;
        }
         return valor;
    }
    public static void main(String args[]) {


        java.awt.EventQueue.invokeLater(() -> {
            try{
                new JIFCliente().setVisible(true);
            }catch (SQLException ex) {
                Logger.getLogger(JIFCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    private void eliminar() throws SQLException
    {
        int resp = JOptionPane.showConfirmDialog(this, "¿Esta seguro que desea Eliminar a este Cliente?", Conexion.nombreapp(), JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION)
        {
            ClientesLogica cl = new ClientesLogica();
            cl.setRtnCliente((String) this.jTbMostrar.getValueAt(jTbMostrar.getSelectedRow(), 0));
            try{
                ClientesDao dao = new ClientesDao();
                dao.eliminarCliente(cl);

            }catch (SQLException e){
                JOptionPane.showMessageDialog(null, "Error al eliminar Cliente: " + e);
            }
            
             try {
                    llenarTabla();
                } catch (SQLException ex) {
                    Logger.getLogger(JIFCliente.class.getName()).log(Level.SEVERE, null, ex);
                }

            JOptionPane.showMessageDialog(null, "Datos eliminados correctamente", Conexion.nombreapp(), JOptionPane.INFORMATION_MESSAGE);
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

        jTPListarCliente = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jTFCodigo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTFNombre = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTFApellido = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTFTelefono = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTFDireccion = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jCbosexo = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jBtnNuevo = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();
        jBtnActualizar = new javax.swing.JButton();
        jBtnCancelar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTFFiltro = new javax.swing.JTextField();
        jBtnBorrar = new javax.swing.JButton();
        jBtnEditar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTbMostrar = new javax.swing.JTable();

        jLabel8.setText("RTN Cliente:");

        jLabel9.setText("Nombre:");

        jLabel10.setText("Apellido:");

        jLabel11.setText("Telefono:");

        jLabel12.setText("Dirección:");

        jLabel1.setText("Sexo:");

        jCbosexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Seleccione --" }));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBtnNuevo)
                .addGap(18, 18, 18)
                .addComponent(jBtnGuardar)
                .addGap(18, 18, 18)
                .addComponent(jBtnActualizar)
                .addGap(18, 18, 18)
                .addComponent(jBtnCancelar)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnNuevo)
                    .addComponent(jBtnGuardar)
                    .addComponent(jBtnActualizar)
                    .addComponent(jBtnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTFDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTFNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTFCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTFApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTFTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(30, 30, 30)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCbosexo, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(105, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTFCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jCbosexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTFDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jTPListarCliente.addTab("Gestion Cliente", jPanel1);

        jLabel2.setText("Buscar:");

        jTFFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTFFiltroKeyReleased(evt);
            }
        });

        jBtnBorrar.setText("Borrar");
        jBtnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBorrarActionPerformed(evt);
            }
        });

        jBtnEditar.setText("editar");

        jTbMostrar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CodCliente", "Nombre", "Apellido", "Telefono", "Dirección", "Sexo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTbMostrar);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTFFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jBtnBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jBtnEditar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTFFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBtnBorrar)
                        .addComponent(jBtnEditar)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jTPListarCliente.addTab("Listar Cliente", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTPListarCliente)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTPListarCliente)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBorrarActionPerformed
        try {
            eliminar();
        } catch (SQLException ex) {
            Logger.getLogger(JIFCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnBorrarActionPerformed

    private void jTFFiltroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFFiltroKeyReleased
        try {
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(JIFCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTFFiltroKeyReleased

    private void jBtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCancelarActionPerformed
        limpiar();
        habilitarBotones(true,false,false,false, false);
    }//GEN-LAST:event_jBtnCancelarActionPerformed

    private void jBtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnActualizarActionPerformed
        try{
            if(verificarDatos()== true)
            {
                editarCliente();
                llenarTabla();
                habilitarBotones(true,false,false,false, false);
                limpiar();
                JOptionPane.showMessageDialog(null,"Datos actualizados satisfactoriamente",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);

            }
        }catch(SQLException ex){
            Logger.getLogger(JIFCliente.class.getName()).log(Level.SEVERE,null,ex);
        }
    }//GEN-LAST:event_jBtnActualizarActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        try{
            if(verificarDatos()== true)
            {
                guardarCliente();
                llenarTabla();
                habilitarBotones(true,false,false,false, false);
                limpiar();

                JOptionPane.showMessageDialog(null,"Datos guardados satisfactoriamente",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            }
        }catch(SQLException ex){
            Logger.getLogger(JIFCliente.class.getName()).log(Level.SEVERE,null,ex);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jBtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnNuevoActionPerformed
        limpiar();
        habilitarBotones(false,true,false,true, true);
    }//GEN-LAST:event_jBtnNuevoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnActualizar;
    private javax.swing.JButton jBtnBorrar;
    private javax.swing.JButton jBtnCancelar;
    private javax.swing.JButton jBtnEditar;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnNuevo;
    private javax.swing.JComboBox<String> jCbosexo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFApellido;
    private javax.swing.JTextField jTFCodigo;
    private javax.swing.JTextField jTFDireccion;
    private javax.swing.JTextField jTFFiltro;
    private javax.swing.JTextField jTFNombre;
    private javax.swing.JTextField jTFTelefono;
    private javax.swing.JTabbedPane jTPListarCliente;
    private javax.swing.JTable jTbMostrar;
    // End of variables declaration//GEN-END:variables

   
}
