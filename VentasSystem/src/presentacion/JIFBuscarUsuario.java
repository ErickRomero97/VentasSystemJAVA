/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;
import dao.UsuarioDao;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import logica.UsuarioLogica;
/**
 *
 * @author Nixon Sanchez
 */
public class JIFBuscarUsuario extends javax.swing.JInternalFrame {

    /**
     * Creates new form JIFBuscarUsuario
     * @throws java.sql.SQLException
     */
    public JIFBuscarUsuario() throws SQLException{
        initComponents();
    }

    
        //Funcion para limpiar la jTable de los datos.
    private void limpiarTabla(){
        DefaultTableModel temp = (DefaultTableModel) this.jTbMostrar.getModel(); //
        // Limpiar los datos de la tabla.
        while (temp.getRowCount() > 0) {
            temp.removeRow(0);
        }
    }
//Esta función sire para cargar el jTable de los datos de busqueda de proveedor
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
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jTFFiltro = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTbMostrar = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setClosable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/zoom.png"))); // NOI18N
        jLabel2.setText("Buscar:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, -1, -1));

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
        getContentPane().add(jTFFiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 80, 288, -1));

        jTbMostrar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "IdUsuario", "Usuario", "Passwork", "Estado", "NombreEmpleado"
            }
        ));
        jScrollPane1.setViewportView(jTbMostrar);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 595, 223));

        jLabel5.setFont(new java.awt.Font("Valken", 0, 24)); // NOI18N
        jLabel5.setText("Busqueda Usuario");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, 270, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Picture5.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 570, 60));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Picture2.png"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, 340, 310));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 630, 340));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTFFiltroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFFiltroKeyReleased
        // TODO add your handling code here:
        //Esto al escribir convierte el texto a mayúscula.
        this.jTFFiltro.setText(this.jTFFiltro.getText().toUpperCase());
        //Aqui se actualizan los datos de acuerdo a lo que escribimos en el Textfield.
        try {
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(JIFBuscarUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTFFiltroKeyReleased

    private void jTFFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFFiltroActionPerformed

    }//GEN-LAST:event_jTFFiltroActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFFiltro;
    private javax.swing.JTable jTbMostrar;
    // End of variables declaration//GEN-END:variables
}
