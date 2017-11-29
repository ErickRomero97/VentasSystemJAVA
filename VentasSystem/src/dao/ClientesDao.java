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
        String sql = "Insert into cliente (rtncliente, nombre, apellido, telefono, direccion, idsexo)"
        + "values (?,?,?,?,?,?)";
        
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
        String sql = "Update cliente set nombre = ?, apellido = ?, telefono = ?, direccion = ?, idsexo = ? "
        + "Where rtncliente = ?";
        
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
        String sql = "Delete From cliente where rtncliente = ?";
        
        PreparedStatement st = (PreparedStatement) this.con.prepareStatement(sql);
        st.setString(1, c1.getRtnCliente());
        
        st.execute();
    }
     
     public List<ClientesLogica> getLista(String filtro) throws SQLException{
         String sql = "";
         if(filtro.length()==0){      
             sql = "Select a.rtncliente,a.nombre, a.apellido, a.telefono, a.direccion,s.sexo "
                     + "from cliente a inner join sexo s on a.idsexo = s.idsexo ";
         }else{
             String buscar = ""+filtro+"%";
             sql = "Select a.rtncliente,a.nombre, a.apellido, a.telefono, a.direccion,s.sexo "
                     + "from cliente a inner join sexo s on a.idsexo = s.idsexo where a.nombre Like "+'"'+buscar+'"'+" or a.rtncliente Like "+'"'+buscar+'"';
             
         }
         List<ClientesLogica> miLista;
         try(PreparedStatement st = (PreparedStatement) this.con.prepareStatement(sql)){
             
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
