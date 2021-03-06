package dao;
import logica.FacturaLogica;
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
 * @author Walter
 */
public class FacturaDao {
    
    //Instansación de la Conexión con el Servidor.
    private final Connection con;
    public FacturaDao() throws SQLException{
        this.con = Conexion.conectar();
    }
    
    //Declaracion del Metodo de Insercion de la Relación Factura
    public void insertarFactura(FacturaLogica c1) throws SQLException{
        String sql = "Insert into factura (fechafactura,idtipofactura,rtncliente,idusuario) "
        + "values (?,?,?,?)";
        PreparedStatement st = (PreparedStatement) this.con.prepareStatement(sql);
        st.setDate(1, c1.getFechafactura() );
        st.setInt(2, c1.getIdTipoFactura());     
        st.setString(3, c1.getRtnCliente());
        st.setInt(4,c1.getIdUsuario());
        st.execute();
    }
    
    //Declaracion del Metodo de Insercion de la Relación DetalleFactura
    public void insertarDetalle(FacturaLogica c1) throws SQLException{
        String sql = "{call sp_ingresardetallefactura(?,?,?,?)}";
        
        PreparedStatement st = (PreparedStatement) this.con.prepareStatement(sql);
        
        st.setInt(1, c1.getIdProducto());
        st.setInt(2, c1.getIdFactura());
        st.setDouble(3, c1.getCantidad());
        st.setDouble(4,c1.getPrecio());
        
        st.execute();
    }
     
    //Declaracion del Metodo de Diminución de Producto en la Relación Producto
    public void restarProducto(FacturaLogica c1) throws SQLException{
        String sql = "{call sp_restarproducto(?,?)}";
        PreparedStatement st = (PreparedStatement) this.con.prepareStatement(sql);
        st.setDouble(1, c1.getCantidad());
        st.setInt(2, c1.getIdProducto());
        st.execute();
    }
    
    //Declaracion del Metodo de Aumento de Producto en la Relación Producto
    public void sumarProducto(FacturaLogica c1) throws SQLException{
        String sql = "{call sp_sumarproducto(?,?)}";
        PreparedStatement st = (PreparedStatement) this.con.prepareStatement(sql);
        st.setDouble(1, c1.getCantidad());
        st.setInt(2, c1.getIdProducto());
        st.execute();
    }
    
    //Declaracion del Metodo de Investigación del Correlativo la Relación Factura
    public int obtenerCodFactura(FacturaLogica c1) throws SQLException{    
        int Id = 0;
        try{
            //Declaración del Procedimiento Almacenado de Investigar Correlativo de la Relación Factura.
            String sql = "{call sp_investigarcorrelativo()}";
            Statement ai = con.createStatement();
            ResultSet ia = ai.executeQuery(sql);
            ia.first();
            Id = ia.getInt("Cod");
        }catch(java.sql.SQLException e){
            JOptionPane.showMessageDialog(null, "Error" + e);
        }
        return Id;
    }
      
    //Declaracion del Metodo de Mostrar de la Relación Factura
    public List<FacturaLogica> getLista(String filtro) throws SQLException{
         String sql;
         boolean determinar = false;
         if(filtro.length()==0){  
             //Declaración del Procedimiento Almacenado Mostrar de la Relación Factura.
             sql = "{call sp_mostrarfactura()}";
         }else{
             //Declaración del Procedimiento Almacenado Mostrar con Busqueda de la Relación Factura.
             sql = "{call sp_buscarfactura(?)}";
             determinar = true;
             
         }
         List<FacturaLogica> miLista;
         try(PreparedStatement st = (PreparedStatement) this.con.prepareStatement(sql)){
            if(determinar == true){
                 st.setString(1, filtro);
             } 
            ResultSet rs;
            rs = st.executeQuery();
            miLista = (List<FacturaLogica>) new ArrayList<FacturaLogica>();
            
            while(rs.next()){
                FacturaLogica c1 = new FacturaLogica();
                c1.setIdFactura(rs.getInt("idfactura"));
                c1.setRtnCliente(rs.getString("rtncliente"));
                c1.setNombreCliente(rs.getString("nombre"));
                c1.setApellidoCliente(rs.getString("apellido"));
                c1.setTipoSFactura(rs.getString("tipofactura"));
                c1.setFechafactura(rs.getDate("fechafactura"));
                miLista.add(c1);
            }rs.close();
        } 
         return miLista;
    }
}
