package dao;
import logica.DetalleLogica;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Walter
 */
public class DetalleDao {
    
    //Instansación de la conexión con el servidor.
    private final Connection con;
    public DetalleDao() throws SQLException{
        this.con = Conexion.conectar();
    }
   
    //Lista para Mostrar los Detalle de Factura.
    public List<DetalleLogica> getLista(String filtro) throws SQLException{
         String sql;
         boolean determinar = false;
         if(filtro.length()==0){ 
             //Declaración del Procedimiento Almacenado de Mostrar Datos en la Relación Detalle Factura
             sql = "{call sp_detallefactura()}";
         }else{
             //Declaración del Procedimiento Almacenado de Busqueda de Datos en la Relación Detalle Factura
             sql = "{call sp_mostrardetallefactura(?)}";
             determinar = true;     
         }
         List<DetalleLogica> miLista;
         try(PreparedStatement st = (PreparedStatement) this.con.prepareStatement(sql)){
            if(determinar == true){
                 st.setString(1, filtro);
            }
            ResultSet rs;
            rs = st.executeQuery();
            miLista = (List<DetalleLogica>) new ArrayList<DetalleLogica>();
            
            while(rs.next()){
                DetalleLogica c1 = new DetalleLogica();
                c1.setIdFactura(rs.getInt("idfactura"));
                c1.setIdProducto(rs.getInt("idproducto"));
                c1.setNombreProducto(rs.getString("nombreproducto"));
                c1.setCantidad(rs.getDouble("cantidad"));
                c1.setPrecio(rs.getDouble("precioventa"));
                miLista.add(c1);
            }rs.close();
        } 
         return miLista;
    }
     
}
