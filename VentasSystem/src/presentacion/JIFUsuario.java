/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;
import dao.UsuarioDao;
import logica.UsuarioLogica;
import conexion.Conexion;
import dao.FacturaDao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author ERICK GALLARDO
 */
public class JIFUsuario extends javax.swing.JInternalFrame {

    /**
     * Creates new form JIFUsuario
     */
    public JIFUsuario() throws SQLException {
        initComponents();
        habilitarBotones(true,false,false,false, false);
        llenarTabla();
        ocultarColumnas();
    }
    
    //Metodo de Obtencion del Correlativo de la Usuario.
    private void obtenerCod(){
        UsuarioLogica cl = new UsuarioLogica();
        try{
            UsuarioDao dao = new UsuarioDao();
            int cod = dao.obtenerCodFactura(cl);
            this.jTFIdUsuario.setText(String.valueOf(cod));
            this.jTFIdUsuario.setEditable(false);
            
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Error al obtener el Código de la Factura: " + e);
        }
    
    }
    
    private void habilitarBotones( boolean nuevo, boolean guardar, boolean actualizar, boolean cancelar, boolean valor){
        this.jBtnNuevo.setEnabled(nuevo);
        this.jBtnGuardar.setEnabled(guardar);
        this.jBtnActualizar.setEnabled(actualizar);
        this.jBtnCancelar.setEnabled(cancelar);
        habilitarTexField(valor);
    }
    
    //Metodo de Habilitación de las Cajas de Texto.
    private void habilitarTexField(boolean valor){
        this.jTFIdUsuario.setEnabled(valor);
        this.jTFNombre.setEnabled(valor);
        this.jTFpassword.setEnabled(valor);
        this.jTFIdEmpleado.setEnabled(valor);
        this.jCboEstado.setEnabled(valor);
        this.jBtnBuscarProducto.setEnabled(valor);
    }
    //Metodo de limpieza de la Tabla de Datos.
    private void limpiarTabla(){
        DefaultTableModel temp = (DefaultTableModel) this.jTbMostrar.getModel(); 

        while (temp.getRowCount() > 0) {
            temp.removeRow(0);
        }
    }
    
    //Metodo del llenado de Datos de la Tabla de Datos.
    private void llenarTabla() throws SQLException{
        limpiarTabla();
        
        UsuarioDao dao = new UsuarioDao();
        
        List<UsuarioLogica> miLista = dao.getLista(this.jTFFiltro.getText());
        
        DefaultTableModel tabla = (DefaultTableModel) this.jTbMostrar.getModel();
        
        miLista.stream().map((cl) -> {
            Object [] fila = new Object [5];
            fila[0] = cl.getIdusuario();
            fila[1] = cl.getNombre();
            fila[2] = cl.getContrasenia();
            fila[3] = cl.getEstado();
            fila[4] = cl.getIdempleado();
            return fila;
        }).forEachOrdered((fila) -> {
            tabla.addRow(fila);
        });    
    }
    
        private void lineaSeleccionada(){
        if (this.jTbMostrar.getSelectedRow() != -1) {
            //Habilito los controles para que se pueda hacer una accion.
            this.jTFFiltro.setText("");
            habilitarBotones(false,false,true,true,true);
            this.jTFIdUsuario.setText(String.valueOf(this.jTbMostrar.getValueAt(jTbMostrar.getSelectedRow(), 0)));
            this.jTFNombre.setText(String.valueOf(this.jTbMostrar.getValueAt(jTbMostrar.getSelectedRow(), 1)));
            this.jTFpassword.setText(String.valueOf(this.jTbMostrar.getValueAt(jTbMostrar.getSelectedRow(), 2)));
            this.jCboEstado.setSelectedItem(String.valueOf(this.jTbMostrar.getValueAt(jTbMostrar.getSelectedRow(), 3)));
            this.jTFIdEmpleado.setText(String.valueOf(this.jTbMostrar.getValueAt(jTbMostrar.getSelectedRow(), 4)));
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione el Empleado a Editar");
        }
    }
    
        
    private void ocultarColumnas(){
        //Oculta la columna de Contraseña
        jTbMostrar.getColumnModel().getColumn(2).setMaxWidth(0);
        jTbMostrar.getColumnModel().getColumn(2).setMinWidth(0);
        jTbMostrar.getColumnModel().getColumn(2).setPreferredWidth(0);
    }
    private void limpiar(){
        this.jTFIdUsuario.setText("");
        this.jTFNombre.setText("");
        this.jTFpassword.setText("");
        this.jTFIdEmpleado.setText("");
        this.jCboEstado.setSelectedIndex(0);
    }   
    
    private void guardar(){
        int estado;
        boolean estados;
        estado=jCboEstado.getSelectedIndex();
        if(estado==1){
            estados=true;
        }else{
            estados=false;
        }
        
        UsuarioLogica cl = new UsuarioLogica();
        cl.setIdempleado(this.jTFIdUsuario.getText());
        cl.setNombre(this.jTFNombre.getText());
        cl.setContrasenia(this.jTFpassword.getText());
        cl.setEstado(estados);
        cl.setIdempleado(this.jTFIdEmpleado.getText());

        
        try{
            UsuarioDao dao = new UsuarioDao();
            dao.insertar(cl);
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al Almacenar el Usuario " + e);
        }
    }
    private void editar(){
        UsuarioLogica cl = new UsuarioLogica();
        String estado;
        boolean estados;
        estado=jCboEstado.getSelectedItem().toString();
        if(estado=="Activo"){
            estados=true;
        }else{
            estados=false;
        }
        cl.setIdusuario(this.jTFIdUsuario.getText());
        cl.setNombre(this.jTFNombre.getText());
        cl.setContrasenia(this.jTFpassword.getText());
        cl.setEstado(estados);
        cl.setIdempleado(this.jTFIdEmpleado.getText());
        try{
            UsuarioDao dao = new UsuarioDao();
            dao.editar(cl);     
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al Actualizar el Usuario: " + e);
            
        }
    }
    private boolean verificarDatos() throws SQLException{
        boolean valor = true;
        if (this.jTFIdUsuario.getText().length()==0){
            JOptionPane.showMessageDialog(null,"Ingrese el Codigo del Usuario",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            this.jTFIdUsuario.requestFocus();
            valor = false;
        }else if ((this.jTFNombre.getText().length())== 0 ){
            JOptionPane.showMessageDialog(null,"Ingrese el Nombre del Usuario",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            this.jTFNombre.requestFocus();
            valor = false;
        }else if ((this.jTFpassword.getText().length())== 0){
            JOptionPane.showMessageDialog(null,"Ingrese la Password",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            this.jTFpassword.requestFocus();
            valor = false;
        }else if (this.jTFIdEmpleado.getText().length()== 0){
            JOptionPane.showMessageDialog(null,"Ingrese el codigo del Empleado",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            this.jTFIdEmpleado.requestFocus();
            valor = false;
        }else if ((this.jCboEstado.getSelectedIndex())== 0 ){
            JOptionPane.showMessageDialog(null,"Seleccione el Estado del Usuario",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            valor = false;
        }
         return valor;
    }
        
      
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jCboEstado = new javax.swing.JComboBox<>();
        jTFIdUsuario = new javax.swing.JTextField();
        jTFNombre = new javax.swing.JTextField();
        jTFIdEmpleado = new javax.swing.JTextField();
        jTFFiltro = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTbMostrar = new javax.swing.JTable();
        jTFpassword = new javax.swing.JTextField();
        jBtnBuscarProducto = new javax.swing.JButton();
        jBtnNuevo = new javax.swing.JButton();
        jBtnCancelar = new javax.swing.JButton();
        jBtnActualizar = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setClosable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Código Usuario:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 120, -1, 20));

        jLabel2.setText("Nombre Usuario:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 150, -1, 20));

        jLabel3.setText("Contraseña:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 180, -1, 20));

        jLabel4.setText("Código Vendedor:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 120, -1, 20));

        jLabel5.setText("Estado:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 150, -1, 20));

        jCboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Seleccione --", "Activo", "Inactivo" }));
        getContentPane().add(jCboEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 150, 140, -1));
        getContentPane().add(jTFIdUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 120, 80, -1));
        getContentPane().add(jTFNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 150, 143, -1));

        jTFIdEmpleado.setEditable(false);
        getContentPane().add(jTFIdEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 120, 139, -1));

        jTFFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTFFiltroKeyReleased(evt);
            }
        });
        getContentPane().add(jTFFiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 310, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/zoom.png"))); // NOI18N
        jLabel8.setText("Buscar:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        jTbMostrar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IdUsuario", "NombreUsuario", "Password", "Estado", "NombreEmpleado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTbMostrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTbMostrarMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTbMostrar);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 123, 563, 180));
        getContentPane().add(jTFpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 180, 143, -1));

        jBtnBuscarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/application_add.png"))); // NOI18N
        jBtnBuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBuscarProductoActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnBuscarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 120, 20, 20));

        jBtnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/addnew.png"))); // NOI18N
        jBtnNuevo.setText("Nuevo");
        jBtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNuevoActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 260, -1, -1));

        jBtnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/delete.png"))); // NOI18N
        jBtnCancelar.setText("Cancelar");
        jBtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 260, -1, -1));

        jBtnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/update.png"))); // NOI18N
        jBtnActualizar.setText("Actualizar");
        jBtnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnActualizarActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 260, -1, -1));

        jBtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/disk.png"))); // NOI18N
        jBtnGuardar.setText("Guardar");
        jBtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 260, -1, -1));

        jLabel10.setFont(new java.awt.Font("Valken", 0, 36)); // NOI18N
        jLabel10.setText("Gestión Usuario");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, 300, 40));

        jLabel7.setFont(new java.awt.Font("Valken", 0, 24)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Picture2.png"))); // NOI18N
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 0, 350, 350));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/low.png"))); // NOI18N
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1060, 70));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo.png"))); // NOI18N
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -20, 1170, 370));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTbMostrarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTbMostrarMousePressed
        lineaSeleccionada();
        jTFIdUsuario.setEditable(false);
        jTFNombre.requestFocus();
  
    }//GEN-LAST:event_jTbMostrarMousePressed

    private void jBtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCancelarActionPerformed
        limpiar();
        habilitarBotones(true,false,false,false, false);
    }//GEN-LAST:event_jBtnCancelarActionPerformed

    private void jBtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnActualizarActionPerformed
        try{
            if(verificarDatos()== true){
                editar();
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
            if(verificarDatos()== true){
                habilitarBotones(true,false,false,false, false);
                guardar();
                limpiar();
                 JOptionPane.showMessageDialog(null,"Datos guardados satisfactoriamente",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
                llenarTabla();
            }
        }catch(SQLException ex){
            Logger.getLogger(JIFCliente.class.getName()).log(Level.SEVERE,null,ex);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jBtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnNuevoActionPerformed
        limpiar();
        obtenerCod();
        jTFNombre.requestFocus();
        habilitarBotones(false,true,false,true, true);
    }//GEN-LAST:event_jBtnNuevoActionPerformed

    private void jTFFiltroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFFiltroKeyReleased
        try {
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(JIFUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTFFiltroKeyReleased

    private void jBtnBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBuscarProductoActionPerformed
        JDFBuscarEmpleado buscarProv;
        try {
            buscarProv = new JDFBuscarEmpleado(null, true);
            buscarProv.setVisible(true);

            if (buscarProv.getIdEmpleado()!= "0"){
                this.jTFIdEmpleado.setText(String.valueOf(buscarProv.getIdEmpleado()));
            }
        } catch (SQLException ex) {
            Logger.getLogger(JIFFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnBuscarProductoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnActualizar;
    private javax.swing.JButton jBtnBuscarProducto;
    private javax.swing.JButton jBtnCancelar;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnNuevo;
    private javax.swing.JComboBox<String> jCboEstado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFFiltro;
    private javax.swing.JTextField jTFIdEmpleado;
    private javax.swing.JTextField jTFIdUsuario;
    private javax.swing.JTextField jTFNombre;
    private javax.swing.JTextField jTFpassword;
    private javax.swing.JTable jTbMostrar;
    // End of variables declaration//GEN-END:variables
}
