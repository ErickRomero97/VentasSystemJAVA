/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;
import dao.ClientesDao;
import logica.ClientesLogica;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class JIFBuscarCliente extends javax.swing.JInternalFrame {
    private String rtncliente = "0";

    private JIFBuscarCliente(JFrame jFrame, boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public String getRtncliente() {
        return rtncliente;
    }

    public void setRtncliente(String rtncliente) {
        this.rtncliente = rtncliente;
    }
    
    public JIFBuscarCliente() throws SQLException {
        initComponents();
        //super(parent, modal);
        //llenarTabla();
        setDefaultCloseOperation(0);
        //this.setLocationRelativeTo(null);
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
           Object [] fila = new Object [5];
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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

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

        jTFFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTFFiltroKeyReleased(evt);
            }
        });

        jLabel2.setText("Buscar:");

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));

        jLabel1.setText("Busqueda de Cliente");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTFFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTFFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTFFiltroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFFiltroKeyReleased
        try {
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(JIFBuscarCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTFFiltroKeyReleased

    private void jTbMostrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTbMostrarMouseClicked
        if(evt.getClickCount() == 2){
            try {
                filaSeleccionada();
            } catch (SQLException ex) {
                Logger.getLogger(JIFBuscarCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }//GEN-LAST:event_jTbMostrarMouseClicked
     private void filaSeleccionada() throws SQLException
    {
        if (this.jTbMostrar.getSelectedRow() != -1)
        {     
            rtncliente = String.valueOf(this.jTbMostrar.getValueAt(this.jTbMostrar.getSelectedRow(), 0));
            this.dispose();
        }
    }
    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JIFBuscarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JIFBuscarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JIFBuscarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JIFBuscarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JIFBuscarCliente dialog;
                /*try {
                    dialog = new JIFBuscarCliente(new javax.swing.JFrame(), true);
                    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(JIFBuscarCliente.class.getName()).log(Level.SEVERE, null, ex);
                }*/
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFFiltro;
    private javax.swing.JTable jTbMostrar;
    // End of variables declaration//GEN-END:variables
}
