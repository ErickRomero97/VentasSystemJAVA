package presentacion;
import dao.ClientesDao;
import logica.ClientesLogica;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Walter
 */
public class JDFBuscarCliente extends javax.swing.JDialog {
    private String rtnCliente;
    public String getRtnCliente() {
        return rtnCliente;
    }

    public void setRtnCliente(String rtnCliente) {
        this.rtnCliente = rtnCliente;
    }
    
    /**
     * Creates new form JDFBuscarCliente
     */
    public JDFBuscarCliente(java.awt.Frame parent, boolean modal) throws SQLException {
        super(parent, modal);
        initComponents();
        llenarTabla();
        this.setLocationRelativeTo(null);
    }
    
     //Metodo de Limpiar la Tabla de Datos.
    private void limpiarTabla(){
        DefaultTableModel temp = (DefaultTableModel) this.jTbMostrar.getModel(); 

        while (temp.getRowCount() > 0) {
            temp.removeRow(0);
        }
    }
    
    //Metodo de llenar la Tabla de Datos.
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
        jTFFiltro = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTbMostrarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTbMostrar);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 840, 223));

        jTFFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFFiltroActionPerformed(evt);
            }
        });
        jTFFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTFFiltroKeyReleased(evt);
            }
        });
        getContentPane().add(jTFFiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 120, 350, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/zoom.png"))); // NOI18N
        jLabel2.setText("Buscar:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 120, -1, -1));

        jLabel5.setFont(new java.awt.Font("Valken", 0, 24)); // NOI18N
        jLabel5.setText("Busqueda Cliente");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, 240, 30));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Picture5.png"))); // NOI18N
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 10, 840, 70));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Picture2.png"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 20, 340, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 910, 400));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void filaSeleccionada() throws SQLException{
        if (this.jTbMostrar.getSelectedRow() != -1){
        rtnCliente = String.valueOf(this.jTbMostrar.getValueAt(this.jTbMostrar.getSelectedRow(), 0));
        this.dispose();
        }
    }
    
    private void jTbMostrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTbMostrarMouseClicked
        if (evt.getClickCount() == 2){
            try {
                filaSeleccionada();
            } catch (SQLException ex) {
                Logger.getLogger(JIFBuscarProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jTbMostrarMouseClicked

    private void jTFFiltroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFFiltroKeyReleased
        try {
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(JIFCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTFFiltroKeyReleased

    private void jTFFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFFiltroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFFiltroActionPerformed

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
            java.util.logging.Logger.getLogger(JDFBuscarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDFBuscarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDFBuscarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDFBuscarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDFBuscarCliente dialog = null;
                try {
                    dialog = new JDFBuscarCliente(new javax.swing.JFrame(), true);
                } catch (SQLException ex) {
                    Logger.getLogger(JDFBuscarCliente.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFFiltro;
    private javax.swing.JTable jTbMostrar;
    // End of variables declaration//GEN-END:variables
}
