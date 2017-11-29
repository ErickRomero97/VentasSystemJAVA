package presentacion;
import dao.EmpleadoDao;
import logica.EmpleadoLogica;
import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class JIFEmpleado extends javax.swing.JInternalFrame {

    /**
     * Creates new form JIFEmpleado
     */
    public JIFEmpleado() throws SQLException {
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
        
        EmpleadoDao dao = new EmpleadoDao();
        
        List<EmpleadoLogica> miLista = dao.getLista(this.jTFFiltro.getText());
        
        DefaultTableModel tabla = (DefaultTableModel) this.jTbMostrar.getModel();
        
        miLista.stream().map((cl) -> {
           Object [] fila = new Object [6];
           fila[0] = cl.getIdempleado();
            fila[1] = cl.getNombre();
            fila[2] = cl.getApellido();
            fila[3] = cl.getTelefono();
            fila[4] = cl.getDireccion();
            fila[5] = cl.getIdSSexo();
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
            JOptionPane.showMessageDialog(null, "Seleccione el Empleado a Editar");
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
    private void guardar(){
        EmpleadoLogica cl = new EmpleadoLogica();
        cl.setIdempleado(this.jTFCodigo.getText());
        cl.setNombre(this.jTFNombre.getText());
        cl.setApellido(this.jTFApellido.getText());
        cl.setTelefono(this.jTFTelefono.getText());
        cl.setDireccion(this.jTFDireccion.getText());
        cl.setIdSexo(this.jCbosexo.getSelectedIndex());
        
        try{
            EmpleadoDao dao = new EmpleadoDao();
            dao.insertar(cl);
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al Almacenar el Empleado " + e);
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
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
    }
    private void editar()
    {
        EmpleadoLogica cl = new EmpleadoLogica();
        cl.setIdempleado(this.jTFCodigo.getText());
        cl.setNombre(this.jTFNombre.getText());
        cl.setApellido(this.jTFApellido.getText());
        cl.setTelefono(this.jTFTelefono.getText());
        cl.setDireccion(this.jTFDireccion.getText());
        cl.setIdSexo(this.jCbosexo.getSelectedIndex());
        try{
            EmpleadoDao dao = new EmpleadoDao();
            dao.editar(cl);     
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al Actualizar el Empleado: " + e);
            
        }
    }
    
    private boolean verificarDatos() throws SQLException
    {
        boolean valor = true;
        
        if (this.jTFCodigo.getText().length()==0){
            JOptionPane.showMessageDialog(null,"Ingrese el Codigo del Empleado",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            this.jTFCodigo.requestFocus();
            valor = false;
        }else if ((this.jTFNombre.getText().length())== 0 ){
            JOptionPane.showMessageDialog(null,"Ingrese el Nombre del Empleado",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            this.jTFNombre.requestFocus();
            valor = false;
        }else if ((this.jTFApellido.getText().length())== 0){
            JOptionPane.showMessageDialog(null,"Ingrese el Apellido del Empleado",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            this.jTFApellido.requestFocus();
            valor = false;
        }else if (this.jTFTelefono.getText().length()== 0){
            JOptionPane.showMessageDialog(null,"Ingrese el Telefono del Empleado",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            this.jTFTelefono.requestFocus();
            valor = false;
        }else if ((this.jTFDireccion.getText().length())== 0){
            JOptionPane.showMessageDialog(null,"Ingrese la Direccion del Empleado",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            this.jTFDireccion.requestFocus();
            valor = false;
        }else if ((this.jCbosexo.getSelectedIndex())== 0 ){
            JOptionPane.showMessageDialog(null,"Seleccione el Sexo del Empleado",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            this.jTFDireccion.requestFocus();
            valor = false;
        }
         return valor;
    }
    public static void main(String args[]) {


        java.awt.EventQueue.invokeLater(() -> {
            try{
                new JIFEmpleado().setVisible(true);
            }catch (SQLException ex) {
                Logger.getLogger(JIFEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTbMostrar = new javax.swing.JTable();
        jBtnEditar = new javax.swing.JButton();

        jLabel8.setText("Código Empleado:");

        jLabel9.setText("Nombre:");

        jLabel10.setText("Apellido:");

        jLabel11.setText("Telefono:");

        jLabel12.setText("Dirección:");

        jLabel1.setText("Sexo:");

        jCbosexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Seleccione--" }));

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnActualizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnCancelar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(jTFTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel9))
                                        .addGap(14, 14, 14))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(18, 18, 18)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTFNombre)
                                    .addComponent(jTFCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                                    .addComponent(jTFApellido))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(44, 44, 44)
                        .addComponent(jCbosexo, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(78, 78, 78))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(jTFDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTFCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCbosexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTFDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTPListarCliente.addTab("Gestión Empleado", jPanel1);

        jLabel2.setText("Buscar:");

        jTFFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTFFiltroKeyReleased(evt);
            }
        });

        jTbMostrar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CodEmpleado", "Nombre", "Apellido", "Telefono", "Dirección", "Sexo"
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

        jBtnEditar.setText("editar");
        jBtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEditarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTFFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132)
                .addComponent(jBtnEditar)
                .addContainerGap(107, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTFFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jBtnEditar))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jTPListarCliente.addTab("Listar Empleado", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTPListarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTPListarCliente)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnNuevoActionPerformed
        limpiar();
        habilitarBotones(false,true,false,true, true);
    }//GEN-LAST:event_jBtnNuevoActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        try{
            if(verificarDatos()== true)
            {
                guardar();
                llenarTabla();
                habilitarBotones(true,false,false,false, false);
                limpiar();

                JOptionPane.showMessageDialog(null,"Datos guardados satisfactoriamente",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            }
        }catch(SQLException ex){
            Logger.getLogger(JIFCliente.class.getName()).log(Level.SEVERE,null,ex);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jBtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnActualizarActionPerformed
        try{
            if(verificarDatos()== true)
            {
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

    private void jBtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCancelarActionPerformed
        limpiar();
        habilitarBotones(true,false,false,false, false);
    }//GEN-LAST:event_jBtnCancelarActionPerformed

    private void jTFFiltroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFFiltroKeyReleased
        try {
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(JIFCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTFFiltroKeyReleased

    private void jBtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEditarActionPerformed
        lineaSeleccionada();
    }//GEN-LAST:event_jBtnEditarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnActualizar;
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
