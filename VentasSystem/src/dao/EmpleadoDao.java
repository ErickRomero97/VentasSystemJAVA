package dao;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import logica.EmpleadoLogica;
/**
 *
 * @author Walter
 */
public class EmpleadoDao {
     private final Connection con;
    
    public EmpleadoDao() throws SQLException{
        this.con = Conexion.conectar();
    }
    
    public void insertar(EmpleadoLogica c1) throws SQLException{
        String sql = "Insert into empleado (idempleado, nombre, apellido, telefono, direccion, idsexo)"
        + "values (?,?,?,?,?,?)";
        
        PreparedStatement st = (PreparedStatement) this.con.prepareStatement(sql);
        
        st.setString(1, c1.getIdempleado());
        st.setString(2, c1.getNombre());
        st.setString(3, c1.getApellido());
        st.setString(4, c1.getTelefono());
        st.setString(5, c1.getDireccion());
        st.setInt(6, c1.getIdSexo());
        
        st.execute();
    }
    
     public void editar(EmpleadoLogica c1) throws SQLException{
        String sql = "Update empleado set nombre = ?, apellido = ?, telefono = ?, direccion = ?, idsexo = ? "
        + "Where idempleado = ?";
        
        PreparedStatement st = (PreparedStatement) this.con.prepareStatement(sql);
               
        st.setString(1, c1.getNombre());
        st.setString(2, c1.getApellido());
        st.setString(3, c1.getTelefono());
        st.setString(4, c1.getDireccion());
        st.setInt(5, c1.getIdSexo());
        st.setString(6, c1.getIdempleado());
        
        st.execute();
    }
     public List<EmpleadoLogica> getLista(String filtro) throws SQLException{
         String sql = "";
         if(filtro.length()==0){      
             sql = "Select a.idempleado,a.nombre, a.apellido, a.telefono, a.direccion,s.sexo "
                     + "from empleado a inner join sexo s on a.idsexo = s.idsexo ";
         }else{
             String buscar = ""+filtro+"%";
             sql = "Select a.idempleado,a.nombre, a.apellido, a.telefono, a.direccion,s.sexo "
                     + "from empleado a inner join sexo s on a.idsexo = s.idsexo where a.nombre Like "+'"'+buscar+'"'+" or a.idempleado Like "+'"'+buscar+'"';
             
         }
         List<EmpleadoLogica> miLista;
         try(PreparedStatement st = (PreparedStatement) this.con.prepareStatement(sql)){
             
            ResultSet rs;
            rs = st.executeQuery();
            miLista = (List<EmpleadoLogica>) new ArrayList<EmpleadoLogica>();
            
            while(rs.next()){
                EmpleadoLogica c1 = new EmpleadoLogica();
                c1.setIdempleado(rs.getString("idempleado"));
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
