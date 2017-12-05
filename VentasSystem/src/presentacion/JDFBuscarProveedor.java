package presentacion;

import dao.ProveedorDao;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import logica.ProveedorLogica;

public class JDFBuscarProveedor extends javax.swing.JDialog {
    //Variables creadas para capturar la informacion que devolvera el jDialogForm
    private String codigoProveedor, nombreProveedor;

    public String getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(String codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }
    /**
     * Creates new form JDFBuscarProveedor
     */
    public JDFBuscarProveedor(java.awt.Frame parent, boolean modal) throws SQLException {
        super(parent, modal);
        initComponents();
        llenarTabla();
        this.setLocationRelativeTo(null);
    }

    //Limpia la jTable
    private void limpiarTabla(){
      
        DefaultTableModel temp = (DefaultTableModel) this.jTblDatosProveedor.getModel(); //
        
        // Limpiar los datos de la tabla.
        while (temp.getRowCount() > 0) {
            temp.removeRow(0);
        }
    }
    
    //Llena la jTable con los datos basicos del proveedor
    private void llenarTabla() throws SQLException{
        limpiarTabla();
        ProveedorDao dao = new ProveedorDao();
        List<ProveedorLogica> miLista = dao.getListaProveedor(this.jTFFiltro.getText());
        
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
    
    //Devuelve los datos de la fila seleccionada
    private void filaSeleccionada() throws SQLException{
        if (this.jTblDatosProveedor.getSelectedRow() != -1){
        codigoProveedor = String.valueOf(this.jTblDatosProveedor.getValueAt(this.jTblDatosProveedor.getSelectedRow(), 0));
        nombreProveedor = String.valueOf(this.jTblDatosProveedor.getValueAt(this.jTblDatosProveedor.getSelectedRow(), 1));
         this.dispose();
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

        jLabel1 = new javax.swing.JLabel();
        jTFFiltro = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblDatosProveedor = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Filtro:");

        jTFFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTFFiltroKeyReleased(evt);
            }
        });

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
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblDatosProveedorMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTblDatosProveedor);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTFFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 577, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTFFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    //Este evento actualiza la jTable de acuerdo a  la busqueda que realizemos
    private void jTFFiltroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFFiltroKeyReleased
        // TODO add your handling code here:
        this.jTFFiltro.setText(this.jTFFiltro.getText().toUpperCase());
        try {
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(JDFBuscarProveedor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTFFiltroKeyReleased

    private void jTblDatosProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblDatosProveedorMouseClicked
         if (evt.getClickCount() == 2){
            try {
                filaSeleccionada();
            } catch (SQLException ex) {
                Logger.getLogger(JIFBuscarProveedor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jTblDatosProveedorMouseClicked
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JDFBuscarProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDFBuscarProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDFBuscarProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDFBuscarProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDFBuscarProveedor dialog = null;
                try {
                    dialog = new JDFBuscarProveedor(new javax.swing.JFrame(), true);
                } catch (SQLException ex) {
                    Logger.getLogger(JDFBuscarProveedor.class.getName()).log(Level.SEVERE, null, ex);
                }
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFFiltro;
    private javax.swing.JTable jTblDatosProveedor;
    // End of variables declaration//GEN-END:variables
}
