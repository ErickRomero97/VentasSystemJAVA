/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;
import Java.sql.*;
import dao.UsuarioDao;
import logica.UsuarioLogica;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author ERICK GALLARDO
 */
public class JIFLogin extends javax.swing.JInternalFrame {

    /**
     * Creates new form JIFLogin
     */
    public JIFLogin() {
        initComponents();
        this.setLocationRelativeTo(null);
    }
    private void login() throws SQLException{
        try {
            UsuarioLogica cu = new UsuarioLogica();
            UsuarioDao du = new UsuarioDao();
            cu.setNombre(this.jTFUsuario.getText());
            cu.setContrasenia(this.jPFContrasena.getText());
            int cod = du.existeUsuario(cu);
            if(du.existeUsuario(cu)>0){
                try{
                    this.hide();
                    JFraMDI a = new JFraMDI();             
                    a.show();
                    
                    this.dispose();
                }catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "error" + e);
                }
                }else{
                if(!"".equals(jTFUsuario.getText()) && !"".equals(jPFContrasena.getText())){
                    JOptionPane.showMessageDialog(null, "Usuario o Contraseña no válidos");
                    this.jTFUsuario.requestFocus();
                }
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error" +e);
        }
    }
    
    private void validar(){
        if("".equals(this.jTFUsuario.getText()) && "".equals(this.jPFContrasena.getText())) {
            JOptionPane.showMessageDialog(null, "Debe ingresar los datos de inicio de sesión");
            this.jTFUsuario.requestFocus();
        }else if("".equals(this.jPFContrasena.getText())){
            JOptionPane.showMessageDialog(null, "Debe ingresar la contraseña");
            this.jPFContrasena.requestFocus();
        }else if("".equals(this.jTFUsuario.getText())){
            JOptionPane.showMessageDialog(null, "Debe ingresar su nombre de usuario");
            this.jTFUsuario.requestFocus();
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
        jLabel2 = new javax.swing.JLabel();
        jTFUsuario = new javax.swing.JTextField();
        jBtnIngresar = new javax.swing.JButton();
        jBtnCanselar = new javax.swing.JButton();
        jPFContrasena = new javax.swing.JPasswordField();

        jLabel1.setText("Usuario:");

        jLabel2.setText("Contraseña:");

        jBtnIngresar.setText("Ingresar");
        jBtnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnIngresarActionPerformed(evt);
            }
        });

        jBtnCanselar.setText("Cancelar");
        jBtnCanselar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCanselarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTFUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPFContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBtnIngresar)
                    .addComponent(jBtnCanselar))
                .addContainerGap(108, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTFUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnIngresar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jBtnCanselar)
                    .addComponent(jPFContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnCanselarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCanselarActionPerformed
        this.dispose();
    }//GEN-LAST:event_jBtnCanselarActionPerformed

    private void jBtnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnIngresarActionPerformed
      try{
          validar();
          login();
      }catch (SQLException e){
          
      }
    }//GEN-LAST:event_jBtnIngresarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnCanselar;
    private javax.swing.JButton jBtnIngresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPasswordField jPFContrasena;
    private javax.swing.JTextField jTFUsuario;
    // End of variables declaration//GEN-END:variables

    private void setLocationRelativeTo(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
