/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import conexion.Conexion;
import dao.ProveedorDao;
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
import logica.ProveedorLogica;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTblDatosProveedor = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jTFFiltro = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTFNombre = new javax.swing.JTextField();
        jTFApellido = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jFFTelefono = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTADireccion = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jFFCodigoProveedor = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        jCboSexo = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jBtnNuevo = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();
        jBtnActualizar = new javax.swing.JButton();
        jBtnCancelar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setClosable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 152, 565, 320));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/zoom.png"))); // NOI18N
        jLabel12.setText("Buscar:");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, -1, -1));

        jTFFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTFFiltroKeyReleased(evt);
            }
        });
        getContentPane().add(jTFFiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, 300, -1));

        jLabel4.setText("Direccion:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 210, -1, -1));
        getContentPane().add(jTFNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 170, 104, -1));
        getContentPane().add(jTFApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 170, 104, -1));

        jLabel5.setText("Teléfono:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 320, -1, 20));

        try {
            jFFTelefono.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        getContentPane().add(jFFTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 320, 125, -1));

        jLabel6.setText("Sexo:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 350, -1, 20));

        jTADireccion.setColumns(20);
        jTADireccion.setRows(5);
        jScrollPane2.setViewportView(jTADireccion);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 210, 263, -1));

        jLabel1.setText("Código Proveedor:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 130, -1, 20));

        try {
            jFFCodigoProveedor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-####-#####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        getContentPane().add(jFFCodigoProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 130, 226, -1));

        jLabel2.setText("Nombre:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 170, -1, 20));

        jCboSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Seleccione--" }));
        getContentPane().add(jCboSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 350, 125, -1));

        jLabel3.setText("Apellido:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 170, -1, 20));

        jBtnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/addnew.png"))); // NOI18N
        jBtnNuevo.setText("Nuevo");
        jBtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNuevoActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 400, -1, -1));

        jBtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/disk.png"))); // NOI18N
        jBtnGuardar.setText("Guardar");
        jBtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 400, -1, -1));

        jBtnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/update.png"))); // NOI18N
        jBtnActualizar.setText("Actualizar");
        jBtnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnActualizarActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 400, -1, -1));

        jBtnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancelnew.png"))); // NOI18N
        jBtnCancelar.setText("Cancelar");
        jBtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 400, -1, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/chart_bar_add.png"))); // NOI18N
        jButton1.setText("Reporte");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 120, 110, -1));

        jLabel11.setFont(new java.awt.Font("Valken", 0, 36)); // NOI18N
        jLabel11.setText("Gestión Proveedor");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 40, 350, 40));

        jLabel9.setFont(new java.awt.Font("Valken", 0, 24)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Picture2.png"))); // NOI18N
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 50, 340, 350));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/low.png"))); // NOI18N
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 1030, 80));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo.png"))); // NOI18N
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 490));

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String path = "";
        try {
            path = getClass().getResource("/reportes/RptProveedor.jasper").getPath();
            path = URLDecoder.decode(path,"UTF-8");
            Connection cn = Conexion.conectar();
            Map parametros = new HashMap();  
            
            //parametros.put("pIdFactura",Integer.parseInt(id));
         
            JasperReport reporte = (JasperReport)JRLoader.loadObject(path);
            JasperPrint imprimir = JasperFillManager.fillReport(reporte,parametros,cn);
            JasperViewer visor = new JasperViewer(imprimir,false);
          
            visor.setTitle("Reporte General de Proveedor");
            visor.setExtendedState(MAXIMIZED_BOTH);
            visor.setVisible(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al Mostrar el reporte: "+e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnActualizar;
    private javax.swing.JButton jBtnCancelar;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnNuevo;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jCboSexo;
    private javax.swing.JFormattedTextField jFFCodigoProveedor;
    private javax.swing.JFormattedTextField jFFTelefono;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTADireccion;
    private javax.swing.JTextField jTFApellido;
    private javax.swing.JTextField jTFFiltro;
    private javax.swing.JTextField jTFNombre;
    private javax.swing.JTable jTblDatosProveedor;
    // End of variables declaration//GEN-END:variables
}
