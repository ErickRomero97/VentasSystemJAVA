package presentacion;

import conexion.Conexion;
import dao.ProductoDao;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import logica.ProductoLogica;

/**
 *
 * @author Miguel
 */
public class JIFProducto extends javax.swing.JInternalFrame {

    /**
     * Creates new form JIFProducto
     */
    public JIFProducto() throws SQLException {
        initComponents();
        ocultarColumnas();
        llenarTabla();
        habilitarBotones(true, false, false, false, false);
    }

    //Funcion que limpia los campos de texto
    private void limpiar(){
        this.jTFCodigoProducto.setText("");
        this.jTFNombreProducto.setText("");
        this.jTFUnidadExistencia.setText("");
        this.jTFExistenciaMinima.setText("");
        this.jTFPrecioVenta.setText("");
        this.jTFPrecioCompra.setText("");
        this.jTFCodigoProveedor.setText("");
        this.jTFNombreProveedor.setText("");
    }
    
    //Funcion para habilitar los botones y la funcion habilitarTextField
    private void habilitarBotones( boolean nuevo, boolean guardar, boolean actualizar, boolean cancelar, boolean valor){
        this.jBtnNuevo.setEnabled(nuevo);
        this.jBtnGuardar.setEnabled(guardar);
        this.jBtnActualizar.setEnabled(actualizar);
        this.jBtnCancelar.setEnabled(cancelar);
        habilitarTexField(valor);
    }
    
    //Esta funcion habilita los campos de texto
    private void habilitarTexField(boolean valor){
        this.jTFCodigoProducto.setEnabled(valor);
        this.jTFNombreProducto.setEnabled(valor);
        this.jTFUnidadExistencia.setEnabled(valor);
        this.jTFExistenciaMinima.setEnabled(valor);
        this.jTFPrecioVenta.setEnabled(valor);
        this.jTFPrecioCompra.setEnabled(valor);
        this.jTFCodigoProveedor.setEnabled(valor);
        this.jTFNombreProveedor.setEnabled(valor);
        this.jBtnBuscarProveedor.setEnabled(valor);
    }
    
    //Esta funcion verifica que los datos se llenen completamente
    private boolean verificarDatos() throws SQLException{
        boolean estado = true;
        
        if(this.jTFCodigoProducto.getText().length()==0){
            jTFCodigoProducto.requestFocus();
            JOptionPane.showMessageDialog(null,"Ingrese el Código del producto",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            estado = false;
        }else if(this.jTFNombreProducto.getText().length()==0){
            jTFNombreProducto.requestFocus();
            JOptionPane.showMessageDialog(null,"Ingrese el nombre del producto",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            estado = false;
        }else if(this.jTFUnidadExistencia.getText().length()==0){
            jTFUnidadExistencia.requestFocus();
            JOptionPane.showMessageDialog(null,"Ingrese la unidad de existencia del producto",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            estado = false;
        }else if(this.jTFExistenciaMinima.getText().length()==0){
            jTFExistenciaMinima.requestFocus();
            JOptionPane.showMessageDialog(null,"Ingrese la existencia mínima del producto",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            estado = false;
        }else if(this.jTFPrecioVenta.getText().length()==0){
            jTFPrecioVenta.requestFocus();
            JOptionPane.showMessageDialog(null,"Ingrese el precio de venta del producto",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            estado = false;
        }else if(this.jTFPrecioCompra.getText().length()==0){
            jTFPrecioCompra.requestFocus();
            JOptionPane.showMessageDialog(null,"Ingrese el precio de compra del producto",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            estado = false;
        }else if(this.jTFCodigoProveedor.getText().length() == 0 && this.jTFNombreProveedor.getText().length() == 0){
            jTFNombreProducto.requestFocus();
            JOptionPane.showMessageDialog(null,"Por favor elija un proveedor",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            estado = false;
        }
        
        return estado;
    }
    
    //Esta funcion oculta la columna idproveedor en el jTable
    private void ocultarColumnas(){
        //Oculta la columna de IdProveedor
        jTblDatosProducto.getColumnModel().getColumn(6).setMaxWidth(0);
        jTblDatosProducto.getColumnModel().getColumn(6).setMinWidth(0);
        jTblDatosProducto.getColumnModel().getColumn(6).setPreferredWidth(0);
    }
    
    //Esta funcion limpia la jTable
    private void limpiarTabla(){      
        DefaultTableModel temp = (DefaultTableModel) this.jTblDatosProducto.getModel(); //
        
        // Limpiar los datos de la tabla.
        while (temp.getRowCount() > 0) {
            temp.removeRow(0);
        }
    }
    
    //Esta funcion llena la jTable con los datos del producto    
    private void llenarTabla() throws SQLException{
        limpiarTabla();
        ProductoDao dao = new ProductoDao();
        List<ProductoLogica> miLista = dao.getLista(this.jTFFiltro.getText());
        
        DefaultTableModel temp = (DefaultTableModel) this.jTblDatosProducto.getModel(); 
        miLista.stream().map((c1) -> {
            Object[] fila = new Object[8];
            fila[0] = c1.getIdproducto();
            fila[1] = c1.getNombreProducto();
            fila[2] = c1.getUnidadExistencia();
            fila[3] = c1.getExistenciaMinima();
            fila[4] = c1.getPrecioCompra();
            fila[5] = c1.getPrecioVenta();
            fila[6] = c1.getIdproveedor();
            fila[7] = c1.getProveedor();
            return fila;
        }).forEachOrdered((fila) -> {   
            temp.addRow(fila);            
        });
    }
    
    //Esta funcion devuelve los datos de la fila seleccionada en el jTable colocandolos en lso campos de texto
    private void filaSeleccionada(){        
        if(this.jTblDatosProducto.getSelectedRow() != -1){
            if(this.jTblDatosProducto.isEnabled() == true){
                this.jTFCodigoProducto.setText(String.valueOf(jTblDatosProducto.getValueAt(this.jTblDatosProducto.getSelectedRow(), 0)));
                this.jTFNombreProducto.setText(String.valueOf(jTblDatosProducto.getValueAt(this.jTblDatosProducto.getSelectedRow(), 1)));
                this.jTFUnidadExistencia.setText(String.valueOf(jTblDatosProducto.getValueAt(this.jTblDatosProducto.getSelectedRow(), 2)));
                this.jTFExistenciaMinima.setText(String.valueOf(jTblDatosProducto.getValueAt(this.jTblDatosProducto.getSelectedRow(), 3)));
                this.jTFPrecioCompra.setText(String.valueOf(jTblDatosProducto.getValueAt(this.jTblDatosProducto.getSelectedRow(), 4)));
                this.jTFPrecioVenta.setText(String.valueOf(jTblDatosProducto.getValueAt(this.jTblDatosProducto.getSelectedRow(), 5)));
                this.jTFCodigoProveedor.setText(String.valueOf(jTblDatosProducto.getValueAt(this.jTblDatosProducto.getSelectedRow(), 6)));
                this.jTFNombreProveedor.setText(String.valueOf(jTblDatosProducto.getValueAt(this.jTblDatosProducto.getSelectedRow(), 7)));
            }            
        }else{
            //limpiar();
        }       
    }
    
    //Esta funcion guarda los datos del producto haciendo un insert a la base de datos
    private void guardarProducto(){
        ProductoLogica pl = new ProductoLogica();
        
        pl.setIdproducto(Integer.parseInt(this.jTFCodigoProducto.getText()));
        pl.setNombreProducto(this.jTFNombreProducto.getText());
        pl.setUnidadExistencia(Double.parseDouble(this.jTFUnidadExistencia.getText()));
        pl.setExistenciaMinima(Double.parseDouble(this.jTFExistenciaMinima.getText()));
        pl.setPrecioCompra(Double.parseDouble(this.jTFPrecioCompra.getText()));
        pl.setPrecioVenta(Double.parseDouble(this.jTFPrecioVenta.getText()));
        pl.setIdproveedor(this.jTFCodigoProveedor.getText());
        
        try {
            ProductoDao dao = new ProductoDao();
            dao.insertarProducto(pl);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al almacenar el Producto " + e);
        }
    }
    
    //Esta funcion elimina el producto seleccionado en la jTable
    private void eliminarProducto() throws SQLException{
        
        int resp = JOptionPane.showConfirmDialog(this, "¿Esta seguro que desea eliminar este producto?", Conexion.nombreapp(), JOptionPane.YES_NO_OPTION);
        if(resp == JOptionPane.YES_OPTION){
          ProductoLogica pl = new ProductoLogica();
        
            pl.setIdproducto(Integer.parseInt(String.valueOf(this.jTblDatosProducto.getValueAt(this.jTblDatosProducto.getSelectedRow(), 0))));

            try {
                ProductoDao dao = new ProductoDao();
                dao.eliminarProducto(pl);
                JOptionPane.showMessageDialog(null, "Datos eliminados correctamente", Conexion.nombreapp(), JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al eliminar el Producto " + e);
            }  
        }        
        
        llenarTabla();        
        limpiar();        
        habilitarBotones(true, false, false, false, false);
    }
    
    //Esta funcion sirve para colocar el id del proveedor cuando se hace un nuevo registro
    private void autoIncrementarIdProveedor() throws SQLException{
        ProductoLogica pl = new ProductoLogica();
        ProductoDao dao = new ProductoDao();
        
        pl.setIdproducto(dao.autoIncrementar());
        jTFCodigoProducto.setText(String.valueOf(pl.getIdproducto()));
    }
    
    //Esta funcion sirve para editar un producto seleccionado en la jTable
    private void editarProducto() throws SQLException{
        ProductoLogica pl = new ProductoLogica();
        
        pl.setNombreProducto(this.jTFNombreProducto.getText());
        pl.setUnidadExistencia(Double.parseDouble(this.jTFUnidadExistencia.getText()));
        pl.setExistenciaMinima(Double.parseDouble(this.jTFExistenciaMinima.getText()));
        pl.setPrecioCompra(Double.parseDouble(this.jTFPrecioCompra.getText()));
        pl.setPrecioVenta(Double.parseDouble(this.jTFPrecioVenta.getText()));
        pl.setIdproveedor(this.jTFCodigoProveedor.getText());
        pl.setIdproducto(Integer.parseInt(this.jTFCodigoProducto.getText()));
        
        try {
            ProductoDao dao = new ProductoDao();
            dao.actualizarProducto(pl);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el Producto " + e);
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblDatosProducto = new javax.swing.JTable();
        jTFFiltro = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jBtnEliminar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTFCodigoProducto = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTFNombreProducto = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTFUnidadExistencia = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTFPrecioVenta = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTFPrecioCompra = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTFCodigoProveedor = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTFNombreProveedor = new javax.swing.JTextField();
        jBtnBuscarProveedor = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jTFExistenciaMinima = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jBtnCancelar = new javax.swing.JButton();
        jBtnActualizar = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();
        jBtnNuevo = new javax.swing.JButton();

        setClosable(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Listado de Prodcutos"));

        jTblDatosProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CodigoProducto", "NombreProducto", "UnidadExistencia", "ExistenciaMinima", "PrecioCompra", "PrecioVenta", "IdProveedor", "Proveedor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTblDatosProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTblDatosProductoMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTblDatosProducto);

        jTFFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTFFiltroKeyReleased(evt);
            }
        });

        jLabel10.setText("Buscar:");

        jBtnEliminar.setText("Eliminar registro");
        jBtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnEliminar)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTFFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnEliminar))
                .addGap(3, 3, 3)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Datos del Producto"));

        jLabel1.setText("Código Producto:");

        jTFCodigoProducto.setEditable(false);

        jLabel2.setText("Nombre del Producto:");

        jLabel3.setText("Unidad existencia:");

        jLabel4.setText("Precio venta:");

        jLabel6.setText("Precio Compra:");

        jLabel8.setText("Codigo Proveedor:");

        jTFCodigoProveedor.setEditable(false);

        jLabel9.setText("Nombre proveedor:");

        jTFNombreProveedor.setEditable(false);

        jBtnBuscarProveedor.setText("Buscar");
        jBtnBuscarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBuscarProveedorActionPerformed(evt);
            }
        });

        jLabel12.setText("Existencia mínima:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFNombreProducto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addGap(10, 10, 10))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTFCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTFUnidadExistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTFPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTFPrecioCompra)
                                .addGap(14, 14, 14))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTFExistenciaMinima, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTFCodigoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnBuscarProveedor))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTFNombreProveedor)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTFCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTFNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTFUnidadExistencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jTFExistenciaMinima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTFPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jTFPrecioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTFCodigoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnBuscarProveedor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTFNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Opciones"));

        jBtnCancelar.setText("Cancelar");
        jBtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCancelarActionPerformed(evt);
            }
        });

        jBtnActualizar.setText("Actualizar");
        jBtnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnActualizarActionPerformed(evt);
            }
        });

        jBtnGuardar.setText("Guardar");
        jBtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarActionPerformed(evt);
            }
        });

        jBtnNuevo.setText("Nuevo");
        jBtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNuevoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBtnNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnActualizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnCancelar)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnNuevo)
                    .addComponent(jBtnGuardar)
                    .addComponent(jBtnActualizar)
                    .addComponent(jBtnCancelar)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(720, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(18, 18, 18)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(385, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Este evento llena los campos de texto con los datos de la fila selecionada en la jTable
    private void jTblDatosProductoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblDatosProductoMousePressed
        // TODO add your handling code here:
        habilitarBotones(false, false, true, true, true);
        filaSeleccionada();
        this.jTFCodigoProducto.requestFocus();
    }//GEN-LAST:event_jTblDatosProductoMousePressed

    //Este evento actualiza la jTable con los datos de la busqueda que ingresamos
    private void jTFFiltroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFFiltroKeyReleased
        this.jTFFiltro.setText(this.jTFFiltro.getText().toUpperCase());
        try {
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(JDFBuscarProveedor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTFFiltroKeyReleased
    
    //Este evento llama a una funcion para eliminar un producto
    private void jBtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminarActionPerformed
        try {
            eliminarProducto();
        } catch (SQLException ex) {
            Logger.getLogger(JIFProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnEliminarActionPerformed

    //Este evento abre un jDialogForm para traer los datos del proveedor
    private void jBtnBuscarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBuscarProveedorActionPerformed
       
        JDFBuscarProveedor buscarProv;
        try {
            buscarProv = new JDFBuscarProveedor(null, true);
            buscarProv.setVisible(true);

            if (buscarProv.getCodigoProveedor()!= "0")
            {
                this.jTFCodigoProveedor.setText(buscarProv.getCodigoProveedor());
                this.jTFNombreProveedor.setText(buscarProv.getNombreProveedor());
            }

        } catch (SQLException ex) {
            Logger.getLogger(JIFProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnBuscarProveedorActionPerformed

    //Este evento cancela lo que estamos realizando
    private void jBtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCancelarActionPerformed
        limpiar();
        habilitarBotones(true,false,false,false, false);
    }//GEN-LAST:event_jBtnCancelarActionPerformed
    
    //Este evento llama a la funcion de actualizar
    private void jBtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnActualizarActionPerformed
        try {
            if(verificarDatos()==true){
                editarProducto();
                llenarTabla();
                habilitarBotones(true, false, false, false, false);
                limpiar();
                JOptionPane.showMessageDialog(null,"Datos actualizados satisfactoriamente",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JIFProducto.class.getName()).log(Level.SEVERE,null,ex);
        }
    }//GEN-LAST:event_jBtnActualizarActionPerformed

    //Este evento llama a la funcion de guardar
    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        try {
            if(verificarDatos() == true){
                guardarProducto();
                llenarTabla();
                habilitarBotones(true,false,false,false, false);
                limpiar();
                JOptionPane.showMessageDialog(null,"Datos guardados satisfactoriamente",Conexion.nombreapp(),JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JIFCliente.class.getName()).log(Level.SEVERE,null,ex);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    //Este evento nos permite crear un nuevo registro
    private void jBtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnNuevoActionPerformed
        limpiar();
        jTFNombreProducto.requestFocus();
        habilitarBotones(false,true,false,true, true);
        try {
            autoIncrementarIdProveedor();
        } catch (SQLException ex) {
            Logger.getLogger(JIFProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.jTFCodigoProducto.requestFocus();
    }//GEN-LAST:event_jBtnNuevoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnActualizar;
    private javax.swing.JButton jBtnBuscarProveedor;
    private javax.swing.JButton jBtnCancelar;
    private javax.swing.JButton jBtnEliminar;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFCodigoProducto;
    private javax.swing.JTextField jTFCodigoProveedor;
    private javax.swing.JTextField jTFExistenciaMinima;
    private javax.swing.JTextField jTFFiltro;
    private javax.swing.JTextField jTFNombreProducto;
    private javax.swing.JTextField jTFNombreProveedor;
    private javax.swing.JTextField jTFPrecioCompra;
    private javax.swing.JTextField jTFPrecioVenta;
    private javax.swing.JTextField jTFUnidadExistencia;
    private javax.swing.JTable jTblDatosProducto;
    // End of variables declaration//GEN-END:variables
}
