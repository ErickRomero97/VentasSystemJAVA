/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.awt.Dimension;
import java.sql.SQLException;
import java.util.logging.Logger;
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
public class JFraMDI extends javax.swing.JFrame {
public static int idUsuario;
    /**
     * Creates new form JFraMDI
     */
    public JFraMDI() {
        initComponents();
        setExtendedState(MAXIMIZED_BOTH);
    }
    public boolean n = true;
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBoxMenuItem3 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem6 = new javax.swing.JCheckBoxMenuItem();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMIFactura = new javax.swing.JMenuItem();
        jMIDetalleFactura = new javax.swing.JMenuItem();
        jMIBuscarFactura = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMIEmpleados = new javax.swing.JMenuItem();
        jMIBusquedaEmpleado = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMICliente = new javax.swing.JMenuItem();
        jMIBusquedaCliente = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMIUsuarios = new javax.swing.JMenuItem();
        jMIBusquedaUsuarios = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMIProveedor = new javax.swing.JMenuItem();
        jMIBuscarProveedor = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMIProducto = new javax.swing.JMenuItem();
        jMIBuscarProducto = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMIFacturaReporteContado = new javax.swing.JMenuItem();
        jMIFacturaReporteCredito = new javax.swing.JMenuItem();
        jMIClientesReporte = new javax.swing.JMenuItem();
        jMIEmpleadoReporte = new javax.swing.JMenuItem();
        jMIProductoReporte = new javax.swing.JMenuItem();
        jMIProveedorReporte = new javax.swing.JMenuItem();

        jCheckBoxMenuItem3.setSelected(true);
        jCheckBoxMenuItem3.setText("jCheckBoxMenuItem3");

        jCheckBoxMenuItem6.setSelected(true);
        jCheckBoxMenuItem6.setText("jCheckBoxMenuItem6");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jDesktopPane1.setPreferredSize(new java.awt.Dimension(1251, 1947));

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1380, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 889, Short.MAX_VALUE)
        );

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Factura.png"))); // NOI18N
        jMenu1.setText("Factura");
        jMenu1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMenu1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jMenu1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jMenu1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jMIFactura.setText("Factura");
        jMIFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIFacturaActionPerformed(evt);
            }
        });
        jMenu1.add(jMIFactura);

        jMIDetalleFactura.setText("Detalle Factura");
        jMIDetalleFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIDetalleFacturaActionPerformed(evt);
            }
        });
        jMenu1.add(jMIDetalleFactura);

        jMIBuscarFactura.setText("Buscar Factura");
        jMIBuscarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIBuscarFacturaActionPerformed(evt);
            }
        });
        jMenu1.add(jMIBuscarFactura);

        jMenuBar1.add(jMenu1);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Empleado.png"))); // NOI18N
        jMenu2.setText("Empleado");
        jMenu2.setHideActionText(true);
        jMenu2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMenu2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jMenu2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jMenu2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jMIEmpleados.setText("Gestión Empleado");
        jMIEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIEmpleadosActionPerformed(evt);
            }
        });
        jMenu2.add(jMIEmpleados);

        jMIBusquedaEmpleado.setText("Buscar Empleado");
        jMIBusquedaEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIBusquedaEmpleadoActionPerformed(evt);
            }
        });
        jMenu2.add(jMIBusquedaEmpleado);

        jMenuBar1.add(jMenu2);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Cliente.png"))); // NOI18N
        jMenu4.setText("Cliente");
        jMenu4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMenu4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jMenu4.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jMenu4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jMICliente.setText("Gestión Cliente");
        jMICliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIClienteActionPerformed(evt);
            }
        });
        jMenu4.add(jMICliente);

        jMIBusquedaCliente.setText("Buscar Cliente");
        jMIBusquedaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIBusquedaClienteActionPerformed(evt);
            }
        });
        jMenu4.add(jMIBusquedaCliente);

        jMenuBar1.add(jMenu4);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Usuario.png"))); // NOI18N
        jMenu5.setText("Usuario");
        jMenu5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMenu5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jMenu5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jMIUsuarios.setText("Gestión Usuario");
        jMIUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIUsuariosActionPerformed(evt);
            }
        });
        jMenu5.add(jMIUsuarios);

        jMIBusquedaUsuarios.setText("Buscar Usuario");
        jMIBusquedaUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIBusquedaUsuariosActionPerformed(evt);
            }
        });
        jMenu5.add(jMIBusquedaUsuarios);

        jMenuBar1.add(jMenu5);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Proveedor.png"))); // NOI18N
        jMenu3.setText("Proveedor");
        jMenu3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMenu3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jMenu3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jMenu3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jMIProveedor.setText("Gestión Proveedor");
        jMIProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIProveedorActionPerformed(evt);
            }
        });
        jMenu3.add(jMIProveedor);

        jMIBuscarProveedor.setText("Buscar Proveedor");
        jMIBuscarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIBuscarProveedorActionPerformed(evt);
            }
        });
        jMenu3.add(jMIBuscarProveedor);

        jMenuBar1.add(jMenu3);

        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Productos.png"))); // NOI18N
        jMenu6.setText("Producto");
        jMenu6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMenu6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jMenu6.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jMenu6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jMIProducto.setText("Gestión Producto");
        jMIProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIProductoActionPerformed(evt);
            }
        });
        jMenu6.add(jMIProducto);

        jMIBuscarProducto.setText("Buscar Producto");
        jMIBuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIBuscarProductoActionPerformed(evt);
            }
        });
        jMenu6.add(jMIBuscarProducto);

        jMenuBar1.add(jMenu6);

        jMenu7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/reportes.png"))); // NOI18N
        jMenu7.setText("Reportes");
        jMenu7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMenu7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jMenu7.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jMenu7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jMIFacturaReporteContado.setText("Reporte Factura Contado");
        jMIFacturaReporteContado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIFacturaReporteContadoActionPerformed(evt);
            }
        });
        jMenu7.add(jMIFacturaReporteContado);

        jMIFacturaReporteCredito.setText("Reporte Factura Credito");
        jMIFacturaReporteCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIFacturaReporteCreditoActionPerformed(evt);
            }
        });
        jMenu7.add(jMIFacturaReporteCredito);

        jMIClientesReporte.setText("Reporte Cliente");
        jMIClientesReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIClientesReporteActionPerformed(evt);
            }
        });
        jMenu7.add(jMIClientesReporte);

        jMIEmpleadoReporte.setText("Reporte Empleado");
        jMIEmpleadoReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIEmpleadoReporteActionPerformed(evt);
            }
        });
        jMenu7.add(jMIEmpleadoReporte);

        jMIProductoReporte.setText("Reporte Producto");
        jMIProductoReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIProductoReporteActionPerformed(evt);
            }
        });
        jMenu7.add(jMIProductoReporte);

        jMIProveedorReporte.setText("Reporte Proveedor");
        jMIProveedorReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIProveedorReporteActionPerformed(evt);
            }
        });
        jMenu7.add(jMIProveedorReporte);

        jMenuBar1.add(jMenu7);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1380, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 889, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMIBuscarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIBuscarProveedorActionPerformed
        JIFBuscarProveedor frm = null;
        try {
            frm = new JIFBuscarProveedor();
            jDesktopPane1.add(frm);
            frm.show();
            Dimension dim = jDesktopPane1.getSize();
            Dimension dimf = frm.getSize();
            frm.setLocation((dim.width - dimf.width)/2, (dim.height - dimf.height)/2);
        } catch (SQLException ex) {
            Logger.getLogger(JFraMDI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMIBuscarProveedorActionPerformed

    private void jMIProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIProveedorActionPerformed
        JIFProveedor frm = null;
        try {
            frm = new JIFProveedor();
            jDesktopPane1.add(frm);
            frm.show();
            Dimension dim = jDesktopPane1.getSize();
            Dimension dimf = frm.getSize();
            frm.setLocation((dim.width - dimf.width)/2, (dim.height - dimf.height)/2);
        } catch (SQLException ex) {
            Logger.getLogger(JFraMDI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMIProveedorActionPerformed

    private void jMIClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIClienteActionPerformed
        JIFCliente frm;
        try {
            frm = new JIFCliente();
            jDesktopPane1.add(frm);
            frm.show();
            Dimension dim = jDesktopPane1.getSize();
            Dimension dimf = frm.getSize();
            frm.setLocation((dim.width - dimf.width)/2, (dim.height - dimf.height)/2);
        } catch (SQLException ex) {
            Logger.getLogger(JFraMDI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMIClienteActionPerformed

    private void jMIBusquedaEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIBusquedaEmpleadoActionPerformed
        JIFBuscarEmpleado frm;
        try {
            frm = new JIFBuscarEmpleado();
            jDesktopPane1.add(frm);
            frm.show();
            Dimension dim = jDesktopPane1.getSize();
            Dimension dimf = frm.getSize();
            frm.setLocation((dim.width - dimf.width)/2, (dim.height - dimf.height)/2);
        } catch (SQLException ex) {
            Logger.getLogger(JFraMDI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMIBusquedaEmpleadoActionPerformed

    private void jMIEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIEmpleadosActionPerformed
        JIFEmpleado frm;
        try {
            frm = new JIFEmpleado();
            jDesktopPane1.add(frm);
            frm.show();
            Dimension dim = jDesktopPane1.getSize();
            Dimension dimf = frm.getSize();
            frm.setLocation((dim.width - dimf.width)/2, (dim.height - dimf.height)/2);
        } catch (SQLException ex) {
            Logger.getLogger(JFraMDI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMIEmpleadosActionPerformed

    private void jMIFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIFacturaActionPerformed
        JIFFactura frm;
        try {
            frm = new JIFFactura();
            jDesktopPane1.add(frm);
            frm.show();
            Dimension dim = jDesktopPane1.getSize();
            Dimension dimf = frm.getSize();
            frm.setLocation((dim.width - dimf.width)/2, (dim.height - dimf.height)/2);
        } catch (SQLException ex) {
            Logger.getLogger(JFraMDI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMIFacturaActionPerformed

    private void jMIDetalleFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIDetalleFacturaActionPerformed
        JIFDetalleFactura frm;
        try {
            frm = new JIFDetalleFactura();
            jDesktopPane1.add(frm);
            frm.show();
            Dimension dim = jDesktopPane1.getSize();
            Dimension dimf = frm.getSize();
            frm.setLocation((dim.width - dimf.width)/2, (dim.height - dimf.height)/2);
        } catch (SQLException ex) {
            Logger.getLogger(JFraMDI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMIDetalleFacturaActionPerformed

    private void jMIBuscarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIBuscarFacturaActionPerformed
        JIFBuscarFactura frm;
        try {
            frm = new JIFBuscarFactura();
            jDesktopPane1.add(frm);
            frm.show();
            Dimension dim = jDesktopPane1.getSize();
            Dimension dimf = frm.getSize();
            frm.setLocation((dim.width - dimf.width)/2, (dim.height - dimf.height)/2);
        } catch (SQLException ex) {
            Logger.getLogger(JFraMDI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMIBuscarFacturaActionPerformed

    private void jMIBusquedaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIBusquedaClienteActionPerformed
        JIFBuscarCliente frm;
        try {
            frm = new JIFBuscarCliente();
            jDesktopPane1.add(frm);
            frm.show();
            Dimension dim = jDesktopPane1.getSize();
            Dimension dimf = frm.getSize();
            frm.setLocation((dim.width - dimf.width)/2, (dim.height - dimf.height)/2);
        } catch (SQLException ex) {
            Logger.getLogger(JFraMDI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMIBusquedaClienteActionPerformed

    private void jMIUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIUsuariosActionPerformed
        JIFUsuario frm;
        try {
            frm = new JIFUsuario();
            jDesktopPane1.add(frm);
            frm.show();
            Dimension dim = jDesktopPane1.getSize();
            Dimension dimf = frm.getSize();
            frm.setLocation((dim.width - dimf.width)/2, (dim.height - dimf.height)/2);
        } catch (SQLException ex) {
            Logger.getLogger(JFraMDI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMIUsuariosActionPerformed

    private void jMIBusquedaUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIBusquedaUsuariosActionPerformed
        JIFBuscarUsuario frm;
        try {
            frm = new JIFBuscarUsuario();
            jDesktopPane1.add(frm);
            frm.show();
            Dimension dim = jDesktopPane1.getSize();
            Dimension dimf = frm.getSize();
            frm.setLocation((dim.width - dimf.width)/2, (dim.height - dimf.height)/2);
        } catch (SQLException ex) {
            Logger.getLogger(JFraMDI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMIBusquedaUsuariosActionPerformed

    private void jMIProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIProductoActionPerformed
        JIFProducto frm;
        try {
            frm = new JIFProducto();
            jDesktopPane1.add(frm);
            frm.show();
            Dimension dim = jDesktopPane1.getSize();
            Dimension dimf = frm.getSize();
            frm.setLocation((dim.width - dimf.width)/2, (dim.height - dimf.height)/2);
        } catch (SQLException ex) {
            Logger.getLogger(JFraMDI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMIProductoActionPerformed

    private void jMIBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIBuscarProductoActionPerformed
        JIFBuscarProducto frm;
        try {
            frm = new JIFBuscarProducto();
            jDesktopPane1.add(frm);
            frm.show();
            Dimension dim = jDesktopPane1.getSize();
            Dimension dimf = frm.getSize();
            frm.setLocation((dim.width - dimf.width)/2, (dim.height - dimf.height)/2);
        } catch (SQLException ex) {
            Logger.getLogger(JFraMDI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMIBuscarProductoActionPerformed

    private void jMIFacturaReporteContadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIFacturaReporteContadoActionPerformed
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
    }//GEN-LAST:event_jMIFacturaReporteContadoActionPerformed

    private void jMIFacturaReporteCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIFacturaReporteCreditoActionPerformed
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
    }//GEN-LAST:event_jMIFacturaReporteCreditoActionPerformed

    private void jMIClientesReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIClientesReporteActionPerformed
        String path = "";
        try {
            path = getClass().getResource("/reportes/RptCliente.jasper").getPath();
            path = URLDecoder.decode(path,"UTF-8");
            Connection cn = Conexion.conectar();
            Map parametros = new HashMap();  
            
            //parametros.put("pIdFactura",Integer.parseInt(id));
         
            JasperReport reporte = (JasperReport)JRLoader.loadObject(path);
            JasperPrint imprimir = JasperFillManager.fillReport(reporte,parametros,cn);
            JasperViewer visor = new JasperViewer(imprimir,false);
          
            visor.setTitle("Reporte General de los Clientes");
            visor.setExtendedState(MAXIMIZED_BOTH);
            visor.setVisible(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al Mostrar el reporte: "+e.getMessage());
        }
    }//GEN-LAST:event_jMIClientesReporteActionPerformed

    private void jMIEmpleadoReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIEmpleadoReporteActionPerformed
       String path = "";
        try {
            path = getClass().getResource("/reportes/RptEmpleado.jasper").getPath();
            path = URLDecoder.decode(path,"UTF-8");
            Connection cn = Conexion.conectar();
            Map parametros = new HashMap();  
            
            //parametros.put("pIdFactura",Integer.parseInt(id));
         
            JasperReport reporte = (JasperReport)JRLoader.loadObject(path);
            JasperPrint imprimir = JasperFillManager.fillReport(reporte,parametros,cn);
            JasperViewer visor = new JasperViewer(imprimir,false);
          
            visor.setTitle("Reporte General de los Clientes");
            visor.setExtendedState(MAXIMIZED_BOTH);
            visor.setVisible(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al Mostrar el reporte: "+e.getMessage());
        }
    }//GEN-LAST:event_jMIEmpleadoReporteActionPerformed

    private void jMIProductoReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIProductoReporteActionPerformed
         String path = "";
        try {
            path = getClass().getResource("/reportes/RptProducto.jasper").getPath();
            path = URLDecoder.decode(path,"UTF-8");
            Connection cn = Conexion.conectar();
            Map parametros = new HashMap();  
            
            //parametros.put("pIdFactura",Integer.parseInt(id));
         
            JasperReport reporte = (JasperReport)JRLoader.loadObject(path);
            JasperPrint imprimir = JasperFillManager.fillReport(reporte,parametros,cn);
            JasperViewer visor = new JasperViewer(imprimir,false);
          
            visor.setTitle("Reporte General de Producto");
            visor.setExtendedState(MAXIMIZED_BOTH);
            visor.setVisible(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al Mostrar el reporte: "+e.getMessage());
        }
        
    }//GEN-LAST:event_jMIProductoReporteActionPerformed

    private void jMIProveedorReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIProveedorReporteActionPerformed
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
    }//GEN-LAST:event_jMIProveedorReporteActionPerformed

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
            java.util.logging.Logger.getLogger(JFraMDI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFraMDI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFraMDI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFraMDI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFraMDI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem3;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem6;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JMenuItem jMIBuscarFactura;
    private javax.swing.JMenuItem jMIBuscarProducto;
    private javax.swing.JMenuItem jMIBuscarProveedor;
    private javax.swing.JMenuItem jMIBusquedaCliente;
    private javax.swing.JMenuItem jMIBusquedaEmpleado;
    private javax.swing.JMenuItem jMIBusquedaUsuarios;
    private javax.swing.JMenuItem jMICliente;
    private javax.swing.JMenuItem jMIClientesReporte;
    private javax.swing.JMenuItem jMIDetalleFactura;
    private javax.swing.JMenuItem jMIEmpleadoReporte;
    private javax.swing.JMenuItem jMIEmpleados;
    private javax.swing.JMenuItem jMIFactura;
    private javax.swing.JMenuItem jMIFacturaReporteContado;
    private javax.swing.JMenuItem jMIFacturaReporteCredito;
    private javax.swing.JMenuItem jMIProducto;
    private javax.swing.JMenuItem jMIProductoReporte;
    private javax.swing.JMenuItem jMIProveedor;
    private javax.swing.JMenuItem jMIProveedorReporte;
    private javax.swing.JMenuItem jMIUsuarios;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuBar jMenuBar1;
    // End of variables declaration//GEN-END:variables
}
