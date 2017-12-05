package presentacion;
import dao.FacturaDao;
import logica.FacturaLogica;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTFCodigoFactura = new javax.swing.JTextField();
        jTFCliente = new javax.swing.JTextField();
        jBtnBuscarCliente = new javax.swing.JButton();
        jCboTipoFactura = new javax.swing.JComboBox<>();
        jFTFFecha = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTFCodigoProducto = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTFCantidad = new javax.swing.JTextField();
        jTFPrecio = new javax.swing.JTextField();
        jBtnAgregar = new javax.swing.JButton();
        jBtnBuscarProducto = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTbMostrar = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jBtnNuevo = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();
        jBtnCancelar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTFSubtotal = new javax.swing.JTextField();
        jTFIsv = new javax.swing.JTextField();
        jTFTotal = new javax.swing.JTextField();

        setClosable(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Datos"), "Datos Factura:"));

        jLabel1.setText("Código:");

        jLabel2.setText("Cliente:");

        jLabel3.setText("Tipo Factura:");

        jLabel4.setText("Fecha:");

        jBtnBuscarCliente.setText("...");
        jBtnBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBuscarClienteActionPerformed(evt);
            }
        });

        jCboTipoFactura.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Seleccione --" }));

        try {
            jFTFFecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-##-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFCodigoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(117, 117, 117)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jFTFFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTFCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jCboTipoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(jTFCodigoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFTFFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTFCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jBtnBuscarCliente)
                    .addComponent(jCboTipoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Producto:"));

        jLabel6.setText("Código Producto:");

        jLabel7.setText("Cantidad:");

        jLabel8.setText("Precio:");

        jBtnAgregar.setText("Agregar");
        jBtnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAgregarActionPerformed(evt);
            }
        });

        jBtnBuscarProducto.setText("..");
        jBtnBuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBuscarProductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTFCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(28, 28, 28)
                        .addComponent(jTFPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jBtnBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(310, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTFCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(jBtnAgregar)
                        .addGap(66, 66, 66))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTFCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnBuscarProducto))
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTFPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnAgregar)
                    .addComponent(jTFCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap())
        );

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

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Opciones "));

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
                .addComponent(jBtnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBtnGuardar)
                .addGap(18, 18, 18)
                .addComponent(jBtnCancelar)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBtnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jBtnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel9.setText("SubTotal:");

        jLabel10.setText("Total:");

        jLabel11.setText("Impuesto:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTFSubtotal)
                    .addComponent(jTFIsv)
                    .addComponent(jTFTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
                .addGap(53, 53, 53))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jTFSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jTFIsv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jTFTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnAgregar;
    private javax.swing.JButton jBtnBuscarCliente;
    private javax.swing.JButton jBtnBuscarProducto;
    private javax.swing.JButton jBtnCancelar;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnNuevo;
    private javax.swing.JComboBox<String> jCboTipoFactura;
    private javax.swing.JFormattedTextField jFTFFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
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
