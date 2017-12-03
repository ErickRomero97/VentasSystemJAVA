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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import logica.ProductoLogica;

/**
 *
 * @author Miguel
 */
public class ProductoDao {
    private final Connection con;

    public ProductoDao() {
        this.con = Conexion.conectar();
    }
    
    //Funcion de tipo void que establece el  procedimiento para insertar un producto
    public void insertarProducto(ProductoLogica pl) throws SQLException{
        String sql = "{call sp_insertarproducto(?,?,?,?,?,?,?,?)}";
        
        PreparedStatement st = this.con.prepareStatement(sql);
        
        st.setInt(1, pl.getIdproducto());
        st.setString(2, pl.getNombreProducto());
        st.setInt(3, pl.getUnidadExistencia());
        st.setInt(4, pl.getExistenciaMinima());
        st.setDouble(5, pl.getPrecioCompra());
        st.setDouble(6, pl.getPrecioVenta());
        st.setString(7, pl.getProductoCol());
        st.setString(8, pl.getIdproveedor());
        
        st.execute();
    }
    
     //Funcion de tipo void que establece el  procedimiento para actualizar un producto
    public void actualizarProducto(ProductoLogica pl) throws SQLException{
        String sql = "{call sp_actualizarproducto(?,?,?,?,?,?,?,?)}";
        
        PreparedStatement st = this.con.prepareStatement(sql);
                
        st.setString(1, pl.getNombreProducto());
        st.setInt(2, pl.getUnidadExistencia());
        st.setInt(3, pl.getExistenciaMinima());
        st.setDouble(4, pl.getPrecioCompra());
        st.setDouble(5, pl.getPrecioVenta());
        st.setString(6, pl.getProductoCol());
        st.setString(7, pl.getIdproveedor());
        st.setInt(8, pl.getIdproducto());
        
        
        st.execute();
    }
    
     //Funcion de tipo void que establece el  procedimiento para eliminar un producto
    public void eliminarProducto(ProductoLogica p1) throws SQLException{
        String sql = "{call sp_eliminarproducto(?)}";
        
        PreparedStatement st = this.con.prepareStatement(sql);
        
        st.setInt(1, p1.getIdproducto());
        
        st.execute();
    }
    
     //Funcion de tipo void que establece el  procedimiento para llenar el jTable de la pantalla producto
    public List<ProductoLogica> getLista(String filtro) throws SQLException{
        String sql = "{call sp_listarproducto(?)}";
        
        List<ProductoLogica> miLista;
             
        try(PreparedStatement st = con.prepareStatement(sql)){
           
            st.setString(1, filtro);
             
            ResultSet rs = st.executeQuery();
            miLista = (List<ProductoLogica>) new ArrayList<ProductoLogica>();
            
            while (rs.next()) {
                ProductoLogica pl = new ProductoLogica();
                pl.setIdproducto(rs.getInt("idproducto"));
                pl.setNombreProducto(rs.getString("nombreproducto"));
                pl.setUnidadExistencia(rs.getInt("unidadexistencia"));
                pl.setExistenciaMinima(rs.getInt("existenciaminima"));
                pl.setPrecioCompra(rs.getDouble("preciocompra"));
                pl.setPrecioVenta(rs.getDouble("precioventa"));
                pl.setProductoCol(rs.getString("productocol"));
                pl.setIdproveedor(rs.getString("idproveedor"));
                pl.setProveedor(rs.getString("NombreProveedor"));
                miLista.add(pl);
            }rs.close();
        }
        return miLista;
    }
    
    //Funcion para recuperar el id proximo al hacer un nuevo registro.
     public int autoIncrementar() throws SQLException{
        int idproveedor = 0;
        
        String sql = "{call sp_autoincrementaridproducto}";
        
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        rs.first();
        
        idproveedor = rs.getInt("idciudad");
        if(idproveedor == 0){
            idproveedor = 1;
        }
        return idproveedor;
    }
}
