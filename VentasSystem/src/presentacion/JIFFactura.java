package presentacion;
import dao.FacturaDao;
import logica.FacturaLogica;
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
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class JIFFactura extends javax.swing.JInternalFrame {
    
    //Declaración de las Variables Necesarias para los datos caculados.
    public double existencia, existenciaMinima;
    private int registro = 0;
    private int codFactura = 0;
    
    public JIFFactura() throws SQLException {
        initComponents();
        llenarComboTipo();
        habilitarBotones(true,false, false, false);
        limpiar();
    }
    
    //Metodo de Habilitación de Botones de Factura.
    private void habilitarBotones( boolean nuevo, boolean guardar,  boolean cancelar, boolean valor){
        this.jBtnNuevo.setEnabled(nuevo);
        this.jBtnGuardar.setEnabled(guardar);
        this.jBtnCancelar.setEnabled(cancelar);
        habilitarTexField(valor);
    }
    
    //Metodo de Limpieza de las Cajas de Texto y ComboBox de Factura.
    private void limpiar(){
        this.jTFCodigoFactura.setText("");
        this.jTFCliente.setText("");
        this.jFTFFecha.setText("");
        this.jCboTipoFactura.setSelectedIndex(0);
        this.jTFSubtotal.setText("");
        this.jTFIsv.setText("");
        this.jTFTotal.setText("");
        limpiarArticulos(); 
        limpiarTable();
    }
    
    
    //Metodo de Limpieza de las Cajas de Texto de Producto.
    private void limpiarArticulos(){
        this.jTFCodigoProducto.setText("");
        this.jTFCantidad.setText("");
        this.jTFPrecio.setText("");
    }
    
    //Metodo de Limpieza de la Tabla de Datos de Factura.
    private void limpiarTable(){
        DefaultTableModel modeloMostrar = (DefaultTableModel) this.jTbMostrar.getModel();
        while(this.jTbMostrar.getRowCount()>0) modeloMostrar.removeRow(0);
    }
    
    //Metodo de LLenado del CoboBox del Tipo de Factura.
    private void llenarComboTipo() throws SQLException{
        FacturaDao dao  = new FacturaDao();
        PreparedStatement ps;
        try(Connection cnn = Conexion.conectar()){
            ps = cnn.prepareStatement("{call sp_mostrartipofactura()}");
            ResultSet rs = ps.executeQuery ();
            while(rs.next()){
               jCboTipoFactura.addItem(rs.getString("tipofactura"));
            }
            ps.close();
        }
    }
    
    //Metodo de Obtencion del Correlativo de la Factura.
    private void obtenerCodFactura(){
        FacturaLogica cl = new FacturaLogica();
        try{
            FacturaDao dao = new FacturaDao();
            int cod = dao.obtenerCodFactura(cl);
            this.jTFCodigoFactura.setText(String.valueOf(cod));
            
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Error al obtener el Código de la Factura: " + e);
        }
    
    }
    
    //Metodo de Habilitación de las cajas de Texto de Factura.
    private void habilitarTexField(boolean valor){
        this.jTFCodigoFactura.setEnabled(valor);
        this.jTFCliente.setEnabled(valor);
        this.jCboTipoFactura.setEnabled(valor);
        this.jFTFFecha.setEnabled(valor);
        this.jTFCodigoProducto.setEnabled(valor);
        this.jTFCantidad.setEnabled(valor);
        this.jTFPrecio.setEnabled(valor);
        this.jBtnBuscarCliente.setEnabled(valor);
        this.jBtnBuscarProducto.setEnabled(valor);
        this.jBtnAgregar.setEnabled(valor);
        this.jTFSubtotal.setEnabled(valor);
        this.jTFIsv.setEnabled(valor);
        this.jTFTotal.setEnabled(valor);
    }
     
    //Metodo de Inserción de Datos en la Relacion Factura.
    private void guardarFactura(){
        FacturaLogica cl = new FacturaLogica();
        cl.setFechaFactura(this.jFTFFecha.getText()); 
        cl.setIdTipoFactura(this.jCboTipoFactura.getSelectedIndex());
        cl.setRtnCliente(this.jTFCliente.getText());
        cl.setIdUsuario(2);
        try{
            FacturaDao dao = new FacturaDao();
            dao.insertarFactura(cl);
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al almacenar la Factura " + e);
        }
    }
    
    //Metodo de Inserción de Datos en la Relación de Detalle de Factura
    private void agregarDetalle(){
        for(int i = 0; i < this.jTbMostrar.getRowCount(); i++){
            FacturaLogica cf = new FacturaLogica();
            cf.setIdProducto(Integer.parseInt((String) this.jTbMostrar.getValueAt(i, 0)));
            cf.setIdFactura(codFactura);
            cf.setCantidad(Double.parseDouble((String) this.jTbMostrar.getValueAt(i, 2)));
            cf.setPrecio(Double.parseDouble((String) this.jTbMostrar.getValueAt(i, 1)));
            try{
                FacturaDao dao = new FacturaDao();
                dao.insertarDetalle(cf);     
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null,"Error al Agregar el Detalle de Factura: " + e);

            }
        }      
    }
    
    //Metodo de Resta de Producto en la Relación de Producto
    private void restarProducto(){
        FacturaLogica cf = new FacturaLogica();
        cf.setCantidad(Double.parseDouble(this.jTFCantidad.getText()));
        cf.setIdProducto(Integer.parseInt(this.jTFCodigoProducto.getText()));
        try{
            FacturaDao dao = new FacturaDao();
            dao.restarProducto(cf);     
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al restar Producto del Inventario: " + e);
            
        }
    }
    
    //Metodo de Suma de Producto en la Relación de Producto
    private void sumarProducto(){
        for(int i = 0; i < this.jTbMostrar.getRowCount(); i++){
            FacturaLogica cf = new FacturaLogica();
            cf.setCantidad(Double.parseDouble((String) this.jTbMostrar.getValueAt(i, 3)) );
            cf.setIdProducto(Integer.parseInt((String) this.jTbMostrar.getValueAt(i, 0)));

            try{
                FacturaDao dao = new FacturaDao();
                dao.sumarProducto(cf);     
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null,"Error al sumar Producto al Inventario: " + e);

            }
        }      
    }
    
    //Metodo de Verificación de Datos de la Factura.
    private boolean verificarDatos() throws SQLException{
        boolean bien = true;
        if (this.jTFCliente.getText().length()==0){
            JOptionPane.showMessageDialog(null,"Seleccione el Codigo del Cliente",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            this.jTFCliente.requestFocus();
            bien = false;
        }else if (this.jCboTipoFactura.getSelectedIndex()==0 && bien == true){
            JOptionPane.showMessageDialog(null,"Seleccione el tipo de factura",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            this.jCboTipoFactura.requestFocus();
            bien = false;
        }else if (this.jFTFFecha.getText().length()==0 && bien == true){
            JOptionPane.showMessageDialog(null,"Ingrese la Fecha de la Factura",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            this.jFTFFecha.requestFocus();
            bien = false;
        } 
        return bien;
    }
    
    //Metodo de Verificación de Datos del Producto para la Factura.
    private boolean verificarArticulos() throws SQLException{
        boolean bien = true;
        if (this.jTFCodigoProducto.getText().length()==0){
            JOptionPane.showMessageDialog(null,"Seleccione el Codigo del Producto",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            this.jTFCodigoProducto.requestFocus();
            bien = false;
        }else if (this.jTFPrecio.getText().length()==0 && bien == true){
            JOptionPane.showMessageDialog(null,"Ingrese el precio del producto",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            this.jTFPrecio.requestFocus();
            bien = false;
        }else if (this.jTFCantidad.getText().length()==0 && bien == true){
            JOptionPane.showMessageDialog(null,"Ingrese la cantidad de producto",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            this.jTFCantidad.requestFocus();
            bien = false;
        } 
        return bien;
    }
    
    //Metodo de Realización de Calculos de la Factura.
    private void calculos(){
        double subtotal = 0;
        double isv;
        double total;
        for(int i = 0; i < this.jTbMostrar.getRowCount(); i++){
            subtotal = subtotal + (double)(this.jTbMostrar.getValueAt(i, 3));
        }
        isv = subtotal * 0.15;
        total = subtotal + isv;
        this.jTFSubtotal.setText(String.valueOf(subtotal));
        this.jTFIsv.setText(String.valueOf(isv));
        this.jTFTotal.setText(String.valueOf(total));
    }
    
    //Metodo de Agregar Articulos a la Tabla de Datos de Factura.
    private void agregarArticulos(){
        double total;
        total = Double.parseDouble(this.jTFCantidad.getText()) * Double.parseDouble(this.jTFPrecio.getText());             
        DefaultTableModel modeloMostrar = (DefaultTableModel) this.jTbMostrar.getModel();
        Object[] fila = new Object[4];     
        fila[0] = this.jTFCodigoProducto.getText();
        fila[1] = this.jTFPrecio.getText();
        fila[2] = this.jTFCantidad.getText();
        fila[3] = total;
        modeloMostrar.addRow(fila);
        this.jTbMostrar.setModel(modeloMostrar);
    }
    
    //Metodo Main Principal del Formulario Factura.
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new JIFFactura().setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(JIFFactura.class.getName()).log(Level.SEVERE, null, ex);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTbMostrar = new javax.swing.JTable();
        jBtnReporte = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTFSubtotal = new javax.swing.JTextField();
        jTFIsv = new javax.swing.JTextField();
        jTFTotal = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTFCodigoFactura = new javax.swing.JTextField();
        jTFCliente = new javax.swing.JTextField();
        jBtnBuscarCliente = new javax.swing.JButton();
        jCboTipoFactura = new javax.swing.JComboBox<>();
        jFTFFecha = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTFCantidad = new javax.swing.JTextField();
        jTFPrecio = new javax.swing.JTextField();
        jBtnAgregar = new javax.swing.JButton();
        jBtnBuscarProducto = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTFCodigoProducto = new javax.swing.JTextField();
        jBtnNuevo = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();
        jBtnCancelar = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        setClosable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTbMostrar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CodProducto", "Precio", "Cantidad", "SubTotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTbMostrar);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 563, 160));

        jBtnReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/chart_bar_add.png"))); // NOI18N
        jBtnReporte.setText("Reporte");
        jBtnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnReporteActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnReporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 490, 110, 24));

        jLabel9.setText("SubTotal:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 440, 80, 20));

        jLabel10.setText("Total:");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 500, -1, 20));

        jLabel11.setText("Impuesto:");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 470, -1, 20));
        getContentPane().add(jTFSubtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 440, 99, -1));
        getContentPane().add(jTFIsv, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 470, 99, -1));
        getContentPane().add(jTFTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 500, 99, -1));

        jLabel2.setText("Cliente:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, -1, 20));

        jLabel3.setText("Tipo Factura:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 110, -1, 20));

        jLabel4.setText("Fecha:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, -1, 20));
        getContentPane().add(jTFCodigoFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 80, -1));
        getContentPane().add(jTFCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 140, -1));

        jBtnBuscarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/application_add.png"))); // NOI18N
        jBtnBuscarCliente.setBorder(null);
        jBtnBuscarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBtnBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBuscarClienteActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnBuscarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, 20, 20));

        jCboTipoFactura.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Seleccione --" }));
        getContentPane().add(jCboTipoFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 110, 150, -1));

        try {
            jFTFFecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-##-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        getContentPane().add(jFTFFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 70, 153, -1));

        jLabel1.setText("Código:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, -1, 20));

        jLabel7.setText("Cantidad:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, -1, 20));

        jLabel8.setText("Precio:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, -1, 20));
        getContentPane().add(jTFCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 200, 68, -1));
        getContentPane().add(jTFPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, 68, -1));

        jBtnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/wishlist_add.png"))); // NOI18N
        jBtnAgregar.setText("Agregar");
        jBtnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAgregarActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 200, -1, -1));

        jBtnBuscarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/application_add.png"))); // NOI18N
        jBtnBuscarProducto.setBorder(null);
        jBtnBuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBuscarProductoActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnBuscarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 150, 20, 20));

        jLabel6.setText("Código Producto:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 130, 20));
        getContentPane().add(jTFCodigoProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 90, -1));

        jBtnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/addnew.png"))); // NOI18N
        jBtnNuevo.setText("Nuevo");
        jBtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNuevoActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 450, 90, 24));

        jBtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/disk.png"))); // NOI18N
        jBtnGuardar.setText("Guardar");
        jBtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 450, -1, -1));

        jBtnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancelnew.png"))); // NOI18N
        jBtnCancelar.setText("Cancelar");
        jBtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 450, -1, -1));

        jLabel14.setFont(new java.awt.Font("Valken", 0, 36)); // NOI18N
        jLabel14.setText("Gestión Factura");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 310, 40));

        jLabel12.setFont(new java.awt.Font("Valken", 0, 24)); // NOI18N
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Picture2.png"))); // NOI18N
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, 340, 320));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/low.png"))); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 70));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo.png"))); // NOI18N
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -20, 580, 570));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    //Boton de Agregar Datos a la Tabla de Datos de Factura.
    private void jBtnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAgregarActionPerformed

        try {
            if(verificarArticulos()== true)
            {

                if(Integer.parseInt(this.jTFCantidad.getText()) > existencia){
                    JOptionPane.showMessageDialog(null, "No hay suficientes Productos");
                }else{
                    if( existencia -  Integer.parseInt(this.jTFCantidad.getText()) <= existenciaMinima){
                        JOptionPane.showMessageDialog(null, "El Producto no tiene Existencia en el Inventario: ");
                    }
                    double total = 0;
                    int existe = 0;
                    int cantidad = 0;

                    int  comparar,valor, sumaCantidad;
                    double totalLinea;

                    if(this.jTbMostrar.getRowCount() == 0){
                        agregarArticulos();
                    }else{
                        comparar = Integer.parseInt(this.jTFCodigoProducto.getText());

                        for(int i = 0; i < this.jTbMostrar.getRowCount(); i++){
                            valor = Integer.parseInt((String) this.jTbMostrar.getValueAt(i, 0));

                            if( comparar == valor ){

                                cantidad = Integer.parseInt((String) this.jTbMostrar.getValueAt(i, 3));
                                sumaCantidad = cantidad + Integer.parseInt(this.jTFCantidad.getText());

                                this.jTbMostrar.setValueAt( String.valueOf(sumaCantidad),i, 3);

                                totalLinea = sumaCantidad * Double.parseDouble((String)this.jTbMostrar.getValueAt(i, 2));
                                this.jTbMostrar.setValueAt(totalLinea,i, 4);

                                existe = 1;
                            }
                        }
                        if(existe == 0){
                            agregarArticulos();
                            existe = 0;
                        }
                    }

                    restarProducto();
                    limpiarArticulos();
                    calculos();
                    registro = 1;
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(JIFFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnAgregarActionPerformed

    // Boton de Buscar el Cliente para la Factura.
    private void jBtnBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBuscarClienteActionPerformed
        JDFBuscarCliente buscarProv;
        try {
            buscarProv = new JDFBuscarCliente(null, true);
            buscarProv.setVisible(true);

            if (buscarProv.getRtnCliente()!= "0")
            {
                this.jTFCliente.setText(String.valueOf(buscarProv.getRtnCliente()));
            }
        } catch (SQLException ex) {
            Logger.getLogger(JIFFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnBuscarClienteActionPerformed
    
    //Boton de Busqueda del Producto.
    private void jBtnBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBuscarProductoActionPerformed
        JDFBuscarProducto buscarProv;
        try {
            buscarProv = new JDFBuscarProducto(null, true);
            buscarProv.setVisible(true);

            if (buscarProv.getCodProducto()!= 0)
            {
                this.jTFCodigoProducto.setText(String.valueOf(buscarProv.getCodProducto()));
                this.jTFPrecio.setText(String.valueOf(buscarProv.getPrecioVenta()));
                existencia = Double.parseDouble(String.valueOf(buscarProv.getExistencia()));
                existenciaMinima = Double.parseDouble(String.valueOf(buscarProv.getExistenciaMinima()));
            }
        } catch (SQLException ex) {
            Logger.getLogger(JIFFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnBuscarProductoActionPerformed
    
    //Boton de Nuevo Registro en la Relación Factura
    private void jBtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnNuevoActionPerformed
        // TODO add your handling code here:
        obtenerCodFactura();
        codFactura = Integer.parseInt(this.jTFCodigoFactura.getText());
        habilitarBotones(false,true, true, true);
    }//GEN-LAST:event_jBtnNuevoActionPerformed
    
    //Boton para Guardar el Nuevo Registro de Factura.
    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed

        try{
            if(verificarDatos()== true)
            {
                guardarFactura();
                agregarDetalle();
                limpiar();
                habilitarBotones(true,false, false, false);
                JOptionPane.showMessageDialog(null,"Facturación exitosa",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);

            }
        }catch(SQLException ex){
            Logger.getLogger(JIFFactura.class.getName()).log(Level.SEVERE,null,ex);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed
    
    //Boton para Cancelar la Creación de una nueva Factura.
    private void jBtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCancelarActionPerformed
        if(registro == 1){
            sumarProducto();
            limpiar();
            habilitarBotones(true,false, false, false);
            registro = 0;
        }else{
            limpiar();
            habilitarBotones(true,false, false, false);
        }

    }//GEN-LAST:event_jBtnCancelarActionPerformed

    private void jBtnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnReporteActionPerformed
        try {
            int resp = JOptionPane.showConfirmDialog(this, "¿Desea Ver El Reporte de la Factura por Contado?", Conexion.nombreapp(), JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION){
                String path = "";
                try {
                    path = getClass().getResource("/reportes/RptFacturaContado.jasper").getPath();
                    path = URLDecoder.decode(path,"UTF-8");
                    Connection cn = Conexion.conectar();
                    Map parametros = new HashMap();  

                    //parametros.put("pIdFactura",Integer.parseInt(id));

                    JasperReport reporte = (JasperReport)JRLoader.loadObject(path);
                    JasperPrint imprimir = JasperFillManager.fillReport(reporte,parametros,cn);
                    JasperViewer visor = new JasperViewer(imprimir,false);

                    visor.setTitle("Reporte de Factura");
                    visor.setExtendedState(MAXIMIZED_BOTH);
                    visor.setVisible(true);
            
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Error al Mostrar el reporte: "+e.getMessage());
                    }
            }else{
                String path = "";
                try {
                    path = getClass().getResource("/reportes/RptFacturaCredito.jasper").getPath();
                    path = URLDecoder.decode(path,"UTF-8");
                    Connection cn = Conexion.conectar();
                    Map parametros = new HashMap();  

                    //parametros.put("pIdFactura",Integer.parseInt(id));

                    JasperReport reporte = (JasperReport)JRLoader.loadObject(path);
                    JasperPrint imprimir = JasperFillManager.fillReport(reporte,parametros,cn);
                    JasperViewer visor = new JasperViewer(imprimir,false);

                    visor.setTitle("Reporte de Factura");
                    visor.setExtendedState(MAXIMIZED_BOTH);
                    visor.setVisible(true);
            
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Error al Mostrar el reporte: "+e.getMessage());
                    }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JIFFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnReporteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnAgregar;
    private javax.swing.JButton jBtnBuscarCliente;
    private javax.swing.JButton jBtnBuscarProducto;
    private javax.swing.JButton jBtnCancelar;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnNuevo;
    private javax.swing.JButton jBtnReporte;
    private javax.swing.JComboBox<String> jCboTipoFactura;
    private javax.swing.JFormattedTextField jFTFFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFCantidad;
    private javax.swing.JTextField jTFCliente;
    private javax.swing.JTextField jTFCodigoFactura;
    private javax.swing.JTextField jTFCodigoProducto;
    private javax.swing.JTextField jTFIsv;
    private javax.swing.JTextField jTFPrecio;
    private javax.swing.JTextField jTFSubtotal;
    private javax.swing.JTextField jTFTotal;
    private javax.swing.JTable jTbMostrar;
    // End of variables declaration//GEN-END:variables
}
