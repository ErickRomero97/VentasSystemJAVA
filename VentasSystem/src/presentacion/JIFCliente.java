
package presentacion;
import dao.ClientesDao;
import logica.ClientesLogica;
import conexion.Conexion;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static jdk.nashorn.internal.runtime.Debug.id;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
public class JIFCliente extends javax.swing.JInternalFrame {

    public JIFCliente() throws SQLException {
        initComponents();
        llenarTabla();
        habilitarBotones(true,false,false,false, false);
        llenarComboBoxSexo();
    }
    
    //Metodo Habilitar Botones.
    private void habilitarBotones( boolean nuevo, boolean guardar, boolean actualizar, boolean cancelar, boolean valor){
        this.jBtnNuevo.setEnabled(nuevo);
        this.jBtnGuardar.setEnabled(guardar);
        this.jBtnActualizar.setEnabled(actualizar);
        this.jBtnCancelar.setEnabled(cancelar);
        habilitarTexField(valor);
    }
    
    //Metodo Habilitar CAjas de Textos.
    private void habilitarTexField(boolean valor){
        this.jFTFCodigo.setEnabled(valor);
        this.jTFNombre.setEnabled(valor);
        this.jTFApellido.setEnabled(valor);
        this.jFTFTelefono.setEnabled(valor);
        this.jTADireccion.setEnabled(valor);
        this.jCbosexo.setEnabled(valor);
    }
    
    //Metodo de Limpieza de la Tabla de Datos.
    private void limpiarTabla(){
        DefaultTableModel temp = (DefaultTableModel) this.jTbMostrar.getModel(); 

        while (temp.getRowCount() > 0) {
            temp.removeRow(0);
        }
    }
    
    //Metodo de llenado de Tabla de Datos.
    private void llenarTabla() throws SQLException{
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
    
    //Metodo de la Linea Seleccionada dentro de la Tabla de Datos.
    private void lineaSeleccionada(){
        if (this.jTbMostrar.getSelectedRow() != -1) {
            //Habilito los controles para que se pueda hacer una accion.
            this.jTFFiltro.setText("");
            habilitarBotones(false,false,true,true,true);
            this.jFTFCodigo.setText(String.valueOf(this.jTbMostrar.getValueAt(jTbMostrar.getSelectedRow(), 0)));
            this.jTFNombre.setText(String.valueOf(this.jTbMostrar.getValueAt(jTbMostrar.getSelectedRow(), 1)));
            this.jTFApellido.setText(String.valueOf(this.jTbMostrar.getValueAt(jTbMostrar.getSelectedRow(), 2)));
            this.jFTFTelefono.setText(String.valueOf(this.jTbMostrar.getValueAt(jTbMostrar.getSelectedRow(), 3)));
            this.jTADireccion.setText(String.valueOf(this.jTbMostrar.getValueAt(jTbMostrar.getSelectedRow(), 4)));
            this.jCbosexo.setSelectedItem(String.valueOf(this.jTbMostrar.getValueAt(jTbMostrar.getSelectedRow(), 5)));
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione el Cliente a Editar");
        }
    }
    
    //Metodo de Limpieza de las Cajas de Texto.
    private void limpiar(){
        this.jFTFCodigo.setText("");
        this.jTFNombre.setText("");
        this.jTFApellido.setText("");
        this.jFTFTelefono.setText("");
        this.jTADireccion.setText("");
        this.jCbosexo.setSelectedIndex(0);
    }
    
    //Metodo de Guardar Nuevo Registro del Cliente.
    private void guardarCliente(){
        ClientesLogica cl = new ClientesLogica();
        cl.setRtnCliente(this.jFTFCodigo.getText());
        cl.setNombre(this.jTFNombre.getText());
        cl.setApellido(this.jTFApellido.getText());
        cl.setTelefono(this.jFTFTelefono.getText());
        cl.setDireccion(this.jTADireccion.getText());
        cl.setIdSexo(this.jCbosexo.getSelectedIndex());
        try{
            ClientesDao dao = new ClientesDao();
            dao.insertarCliente(cl);
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al Almacenar el Cliente " + e);
        }
    }
    
    //Metodo de LLenado de los Sexos de los Clientes.
    private void llenarComboBoxSexo(){
        String consulta = "call sp_listarsexo()";
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
    
    //Metodo de Edición de los Datos de los Clientes.
    private void editarCliente(){
        ClientesLogica cl = new ClientesLogica();
        cl.setRtnCliente(this.jFTFCodigo.getText());
        cl.setNombre(this.jTFNombre.getText());
        cl.setApellido(this.jTFApellido.getText());
        cl.setTelefono(this.jFTFTelefono.getText());
        cl.setDireccion(this.jTADireccion.getText());
        cl.setIdSexo(this.jCbosexo.getSelectedIndex());
        try{
            ClientesDao dao = new ClientesDao();
            dao.editarCliente(cl);     
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al Actualizar el Cliente: " + e);
            
        }
    }
    
    //Metodo de Verificación de los Datos dentro de las Cajas de Texto.
    private boolean verificarDatos() throws SQLException{
        boolean valor = true;
        if (jFTFCodigo.getText().trim().length() != 16){
            JOptionPane.showMessageDialog(null,"Ingrese el RTN del Cliente",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            this.jFTFCodigo.requestFocus();
            valor = false;
        }else if ((this.jTFNombre.getText().length())== 0 ){
            JOptionPane.showMessageDialog(null,"Ingrese el Nombre del Cliente",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            this.jTFNombre.requestFocus();
            valor = false;
        }else if ((this.jTFApellido.getText().length())== 0){
            JOptionPane.showMessageDialog(null,"Ingrese el Apellido del Cliente",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            this.jTFApellido.requestFocus();
            valor = false;
        }else if (this.jFTFTelefono.getText().length()== 0){
            JOptionPane.showMessageDialog(null,"Ingrese el Telefono del Cliente",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            this.jFTFTelefono.requestFocus();
            valor = false;
        }else if ((this.jTADireccion.getText().length())== 0){
            JOptionPane.showMessageDialog(null,"Ingrese la Direccion del Cliente",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            this.jTADireccion.requestFocus();
            valor = false;
        }else if ((this.jCbosexo.getSelectedIndex())== 0 ){
            JOptionPane.showMessageDialog(null,"Seleccione el Sexo del Cliente",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            this.jTADireccion.requestFocus();
            valor = false;
        }
         return valor;
    }
    
    //Metodo principal Main.
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            try{
                new JIFCliente().setVisible(true);
            }catch (SQLException ex) {
                Logger.getLogger(JIFCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    //Metodo Eliminar los Datos del Cliente.
    private void eliminar() throws SQLException{
        int resp = JOptionPane.showConfirmDialog(this, "¿Esta seguro que desea Eliminar a este Cliente?", Conexion.nombreapp(), JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION){
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTbMostrar = new javax.swing.JTable();
        jBtnReportC = new javax.swing.JButton();
        jTFFiltro = new javax.swing.JTextField();
        jBtnBorrar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTFApellido = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTFNombre = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jCbosexo = new javax.swing.JComboBox<>();
        jBtnNuevo = new javax.swing.JButton();
        jFTFTelefono = new javax.swing.JFormattedTextField();
        jFTFCodigo = new javax.swing.JFormattedTextField();
        jBtnGuardar = new javax.swing.JButton();
        jBtnActualizar = new javax.swing.JButton();
        jBtnCancelar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTADireccion = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setClosable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jTbMostrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTbMostrarMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTbMostrar);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 632, 204));

        jBtnReportC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/chart_bar_add.png"))); // NOI18N
        jBtnReportC.setText("Reporte");
        jBtnReportC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnReportCActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnReportC, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 100, -1, -1));

        jTFFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTFFiltroKeyReleased(evt);
            }
        });
        getContentPane().add(jTFFiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 250, -1));

        jBtnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/delete.png"))); // NOI18N
        jBtnBorrar.setText("Eliminar registro");
        jBtnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBorrarActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnBorrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 100, 170, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/zoom.png"))); // NOI18N
        jLabel3.setText("Buscar:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));
        getContentPane().add(jTFApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 130, 139, -1));

        jLabel8.setText("RTN Cliente:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 100, -1, -1));

        jLabel1.setText("Sexo:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 170, -1, -1));

        jLabel12.setText("Dirección:");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 210, -1, -1));
        getContentPane().add(jTFNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 130, 128, -1));

        jLabel9.setText("Nombre:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 130, -1, -1));

        jCbosexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Seleccione --" }));
        getContentPane().add(jCbosexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 170, 139, -1));

        jBtnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/addnew.png"))); // NOI18N
        jBtnNuevo.setText("Nuevo");
        jBtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNuevoActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 310, -1, -1));

        try {
            jFTFTelefono.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        getContentPane().add(jFTFTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 170, 130, -1));

        try {
            jFTFCodigo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-####-######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        getContentPane().add(jFTFCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 100, 210, -1));

        jBtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/disk.png"))); // NOI18N
        jBtnGuardar.setText("Guardar");
        jBtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 310, -1, -1));

        jBtnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/update.png"))); // NOI18N
        jBtnActualizar.setText("Actualizar");
        jBtnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnActualizarActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 310, -1, -1));

        jBtnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancelnew.png"))); // NOI18N
        jBtnCancelar.setText("Cancelar");
        jBtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 310, -1, -1));

        jTADireccion.setColumns(20);
        jTADireccion.setRows(5);
        jScrollPane2.setViewportView(jTADireccion);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 210, 340, -1));

        jLabel11.setText("Telefono:");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 170, -1, -1));

        jLabel10.setText("Apellido:");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 130, -1, -1));

        jLabel7.setFont(new java.awt.Font("Valken", 0, 36)); // NOI18N
        jLabel7.setText("Gestión Cliente");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, 280, 40));

        jLabel6.setFont(new java.awt.Font("Valken", 0, 24)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Picture2.png"))); // NOI18N
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 40, 350, 350));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/low.png"))); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1060, 70));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo.png"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1110, 390));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    //Metodo de Accion del Boton Borrar
    private void jBtnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBorrarActionPerformed
        try {
            eliminar();
        } catch (SQLException ex) {
            Logger.getLogger(JIFCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnBorrarActionPerformed
    
    //Evento de la Caja de Texto Filtro.
    private void jTFFiltroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFFiltroKeyReleased
        try {
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(JIFCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTFFiltroKeyReleased

    //Metodo de Accion del Boton Cancelar.
    private void jBtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCancelarActionPerformed
        limpiar();
        habilitarBotones(true,false,false,false, false);
    }//GEN-LAST:event_jBtnCancelarActionPerformed
    
    //Metodo de Accion del Boton Actualizar.
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
    
    //Metodo de Accion del Boton Guardar
    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        try{
            if(verificarDatos()== true){
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
    
    //Metodo de Accion del Boton Nuevo.
    private void jBtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnNuevoActionPerformed
        limpiar();
        jFTFCodigo.requestFocus();
        jFTFCodigo.setEditable(true);
        habilitarBotones(false,true,false,true, true);
    }//GEN-LAST:event_jBtnNuevoActionPerformed
    
    private void jTbMostrarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTbMostrarMousePressed
        lineaSeleccionada();
        jFTFCodigo.setEditable(false);
        jTFNombre.requestFocus();
    }//GEN-LAST:event_jTbMostrarMousePressed

    private void jBtnReportCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnReportCActionPerformed
        
        String path = "";
        try {
            path = getClass().getResource("/reportes/RptCliente.jasper").getPath();
            path = URLDecoder.decode(path,"UTF-8");
            Connection cn = Conexion.conectar();
            Map parametros = new HashMap();  
            
            //parametros.put("pIdFactura",Integer.parseInt(id));
         
            JasperReport reporte = (JasperReport)JRLoader.loadObject(path);
            JasperPrint imprimir = JasperFillManager.fillReport(reporte,parametros,cn);
            JasperViewer visor = new JasperViewer(imprimir,false);
          
            visor.setTitle("Reporte General de los Clientes");
            visor.setExtendedState(MAXIMIZED_BOTH);
            visor.setVisible(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al Mostrar el reporte: "+e.getMessage());
        }
    }//GEN-LAST:event_jBtnReportCActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnActualizar;
    private javax.swing.JButton jBtnBorrar;
    private javax.swing.JButton jBtnCancelar;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnNuevo;
    private javax.swing.JButton jBtnReportC;
    private javax.swing.JComboBox<String> jCbosexo;
    private javax.swing.JFormattedTextField jFTFCodigo;
    private javax.swing.JFormattedTextField jFTFTelefono;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTADireccion;
    private javax.swing.JTextField jTFApellido;
    private javax.swing.JTextField jTFFiltro;
    private javax.swing.JTextField jTFNombre;
    private javax.swing.JTable jTbMostrar;
    // End of variables declaration//GEN-END:variables

   
}
