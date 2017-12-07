/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import logica.ProveedorLogica;

/**
 *
 * @author Miguel
 */
public class ProveedorDao {
    private final Connection con;

    public ProveedorDao() throws SQLException{
        this.con = Conexion.conectar();
    }
    
    //Funcion de tipo void que stablece el procedimiento para insertar un proveedor
    public void insertarProveedor(ProveedorLogica c1) throws SQLException{
        String sql = "{call sp_insertarproveedor(?,?,?,?,?,?)}";
        
        PreparedStatement st = this.con.prepareStatement(sql);
        
        st.setString(1, c1.getIdProveedor());
        st.setString(2, c1.getNombre());
        st.setString(3, c1.getApellido());
        st.setString(4, c1.getDireccion());
        st.setString(5, c1.getTelefono());
        st.setInt(6, c1.getIdSexo());
        
        st.execute();
    }
    
    //Funcion de tipo void que stablece el procedimiento para actualizar un proveedor
    public void actualizarProveedor(ProveedorLogica p1) throws SQLException{
        String sql = "{call sp_actualizarproveedor(?,?,?,?,?,?)}";
        
        PreparedStatement st = this.con.prepareStatement(sql);
                
        st.setString(1, p1.getNombre());
        st.setString(2, p1.getApellido());
        st.setString(3, p1.getDireccion());
        st.setString(4, p1.getTelefono());
        st.setInt(5, p1.getIdSexo());
        st.setString(6, p1.getIdProveedor());
        
        st.execute();
    }
    
    //Funcion de tipo void que stablece el procedimiento para eliminar un proveedor
    public void eliminarProveedor(ProveedorLogica c1) throws SQLException{
        String sql = "{call sp_eliminarproveedor(?)}";
        
        PreparedStatement st = this.con.prepareStatement(sql);
        
        st.setString(1, c1.getIdProveedor());
        
        st.execute();
    }
    
    //Funcion de tipo void que establece el procedimiento para llenar un jTable que muestra todo los datos del proveedor
    public List<ProveedorLogica> getLista(String filtro) throws SQLException{
        String sql= "{call sp_buscarproveedor(?)}";
          
        
        List<ProveedorLogica> miLista;
             
        try(PreparedStatement st = con.prepareStatement(sql)){
            
            st.setString(1, filtro);
              
            ResultSet rs = st.executeQuery();
            miLista = (List<ProveedorLogica>) new ArrayList<ProveedorLogica>();
            
            while (rs.next()) {
                ProveedorLogica cl = new ProveedorLogica();
                cl.setIdProveedor(rs.getString("idproveedor"));
                cl.setNombre(rs.getString("nombre"));
                cl.setApellido(rs.getString("apellido"));
                cl.setDireccion(rs.getString("direccion"));
                cl.setTelefono(rs.getString("telefono"));
                cl.setSexo(rs.getString("sexo"));
                
                miLista.add(cl);
            }rs.close();
        }
        return miLista;
    }
    
    //Funcion de tipo void que stablece el procedimiento para llenar el jTable de buscar un proveedor en la pantalla de producto
    public List<ProveedorLogica> getListaProveedor(String filtro) throws SQLException{
        String sql= "{call sp_buscarproveedor(?)}";
        
        List<ProveedorLogica> miLista;
             
        try(PreparedStatement st = con.prepareStatement(sql)){
           
            st.setString(1, filtro);
             
            ResultSet rs = st.executeQuery();
            miLista = (List<ProveedorLogica>) new ArrayList<ProveedorLogica>();
            
            while (rs.next()) {
                ProveedorLogica cl = new ProveedorLogica();
                cl.setIdProveedor(rs.getString("idproveedor"));
                cl.setNombre(rs.getString("nombre"));
                cl.setApellido(rs.getString("apellido"));
                cl.setDireccion(rs.getString("direccion"));
                cl.setTelefono(rs.getString("telefono"));
                cl.setSexo(rs.getString("sexo"));
                miLista.add(cl);
            }rs.close();
        }
        return miLista;
    }
    
    //Funcion de tipo void que stablece el procedimiento para llenar un jTable en la busqueda de proveedor
    public List<ProveedorLogica> getListaBuscarProveedor(String filtro) throws SQLException{
        String sql= "{call sp_buscarproveedor(?)}";
        
        List<ProveedorLogica> miLista;
             
        try(PreparedStatement st = con.prepareStatement(sql)){
           
            st.setString(1, filtro);
             
            ResultSet rs = st.executeQuery();
            miLista = (List<ProveedorLogica>) new ArrayList<ProveedorLogica>();
            
            while (rs.next()) {
                ProveedorLogica cl = new ProveedorLogica();
                cl.setIdProveedor(rs.getString("idproveedor"));
                cl.setNombre(rs.getString("nombre"));
                cl.setApellido(rs.getString("apellido"));
                cl.setDireccion(rs.getString("direccion"));
                cl.setTelefono(rs.getString("telefono"));
                cl.setSexo(rs.getString("sexo"));
                miLista.add(cl);
            }rs.close();
        }
        return miLista;
    }
}
