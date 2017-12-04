/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import logica.ClientesLogica;
import logica.UsuarioLogica;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author Nixon Sanchez
 */
public class UsuarioDao {
    
    private final Connection con;
    public UsuarioDao() throws SQLException{
        this.con = Conexion.conectar();
    }
    
        public void insertar(UsuarioLogica c1) throws SQLException{
        //Declaración del Procedimiento Almacenado de Inserción de Datos en la Relación empleado
        String sql = "{call sp_ingresarusuario(?,?,?,?,?)}";
        PreparedStatement st = (PreparedStatement) this.con.prepareStatement(sql);
        st.setString(1, c1.getIdusuario());
        st.setString(2, c1.getNombre());
        st.setString(3, c1.getContrasenia());
        st.setBoolean(4, c1.getEstado());
        st.setString(5, c1.getIdempleado());
        st.execute();
    }
        
     public void editar(UsuarioLogica c1) throws SQLException{
        //Declaración del Procedimiento Almacenado de Edición de Datos en la Relación empleado
        String sql = "{call sp_actualizarusuario(?,?,?,?,?)}";
        PreparedStatement st = (PreparedStatement) this.con.prepareStatement(sql);
        st.setString(1, c1.getIdusuario());
        st.setString(2, c1.getNombre());
        st.setString(3, c1.getContrasenia());
        st.setBoolean(4, c1.getEstado());
        st.setString(5, c1.getIdempleado());
        st.execute();
    }
     
  public List<UsuarioLogica> getLista(String filtro) throws SQLException{
         String sql ;
         boolean determinar = false;
         if(filtro.length()==0){
             //Declaración del Procedimiento Almacenado de Mostrar Datos dn la Relación Empleado
             sql = "{call sp_listarusuario()} ";
         }else{
             //Declaración del Procedimiento Almacenado de Busqueda de Datos en la Relación Empleado
             sql = "{call sp_buscarusuario(?)}";
             determinar = true;
         }
         List<UsuarioLogica> miLista;
         try(PreparedStatement st = (PreparedStatement) this.con.prepareStatement(sql)){
            if(determinar == true){
                 st.setString(1, filtro);
             }  
            ResultSet rs;
            rs = st.executeQuery();
            miLista = (List<UsuarioLogica>) new ArrayList<UsuarioLogica>();
            
            while(rs.next()){
                UsuarioLogica c1 = new UsuarioLogica();
                c1.setIdusuario(rs.getString("idusuario"));
                c1.setNombre(rs.getString("nombre"));
                c1.setContrasenia(rs.getString("contraseña"));
                c1.setEstado(rs.getBoolean("estado"));
                c1.setIdempleado(rs.getString("idempleado"));
                
                miLista.add(c1);
            }rs.close();
        } 
         return miLista;
    }
  
      public Integer existeUsuario(UsuarioLogica cl1) throws SQLException
    {
        Integer retorno = 0;
        
        String sql = "select * from usuario where nombre = '"+cl1.getNombre()+"' "
                + " and contraseña = '"+cl1.getContrasenia()+"' "
                + " and estado = '1' ";
        
        Statement st = Conexion.conectar().createStatement();
        ResultSet rs = st.executeQuery(sql);
        rs.first();
        
        try
        {
            retorno = rs.getInt("idusuario");
        }catch(SQLException e)
        {
            
        }
        return retorno;
    }

    

       
}
