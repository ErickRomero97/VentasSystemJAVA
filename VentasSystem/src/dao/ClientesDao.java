package dao;
import logica.ClientesLogica;
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
public class ClientesDao {
    private final Connection con;
    
    public ClientesDao() throws SQLException{
        this.con = Conexion.conectar();
    }
    
    public void insertarCliente(ClientesLogica c1) throws SQLException{
        String sql = "{ call sp_ingresarcliente(?,?,?,?,?,?)}";
        
        PreparedStatement st = (PreparedStatement) this.con.prepareStatement(sql);
        
        st.setString(1, c1.getRtnCliente());
        st.setString(2, c1.getNombre());
        st.setString(3, c1.getApellido());
        st.setString(4, c1.getTelefono());
        st.setString(5, c1.getDireccion());
        st.setInt(6, c1.getIdSexo());
        
        st.execute();
    }
    
     public void editarCliente(ClientesLogica c1) throws SQLException{
        String sql = "{ call sp_actualizarcliente(?,?,?,?,?,?)}";
        
        PreparedStatement st = (PreparedStatement) this.con.prepareStatement(sql);
               
        st.setString(1, c1.getNombre());
        st.setString(2, c1.getApellido());
        st.setString(3, c1.getTelefono());
        st.setString(4, c1.getDireccion());
        st.setInt(5, c1.getIdSexo());
        st.setString(6, c1.getRtnCliente());
        
        st.execute();
    }
     
     public void eliminarCliente(ClientesLogica c1) throws SQLException{
        String sql = "{call sp_eliminarcliente(?)}";
        
        PreparedStatement st = (PreparedStatement) this.con.prepareStatement(sql);
        st.setString(1, c1.getRtnCliente());
        
        st.execute();
    }
     
     public List<ClientesLogica> getLista(String filtro) throws SQLException{
         String sql = "";
         boolean determinar = false;
         if(filtro.length()==0){      
             sql = "{call sp_listarcliente()}";
         }else{
             sql = "{call sp_buscarcliente1(?)}";
             determinar = true;
               
         }
         List<ClientesLogica> miLista;
         try(PreparedStatement st = (PreparedStatement) this.con.prepareStatement(sql)){
             if(determinar == true){
                 st.setString(1, filtro);
             } 
            ResultSet rs;
            rs = st.executeQuery();
            miLista = (List<ClientesLogica>) new ArrayList<ClientesLogica>();
            
            while(rs.next()){
                ClientesLogica c1 = new ClientesLogica();
                c1.setRtnCliente(rs.getString("rtncliente"));
                c1.setNombre(rs.getString("nombre"));
                c1.setApellido(rs.getString("apellido"));
                c1.setTelefono(rs.getString("telefono"));
                c1.setDireccion(rs.getString("direccion"));
                c1.setIdSSexo(rs.getString("sexo"));
                miLista.add(c1);
            }rs.close();
        } 
         return miLista;
    }
    
}
