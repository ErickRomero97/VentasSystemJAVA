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
    //Instansiación de la Clase Conexión para poder Conectar con el Servidor
    private final Connection con;
    public ClientesDao() throws SQLException{
        this.con = Conexion.conectar();
    }
    //Declaración de Metodo de Inserción de Datos en la Relación cliente
    public void insertarCliente(ClientesLogica c1) throws SQLException{
        //Declaración del Procedimiento Almacenado de Ingreso de Datos en la Relación cliente
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
    //Declaración de Metodo de Actualización de Datos en la Relación cliente
    public void editarCliente(ClientesLogica c1) throws SQLException{
        //Declaración del Procedimiento Almacenado de Actualización de Datos en la Relación cliente
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
    //Declaración de Metodo de Eliminación de Datos en la Relación cliente
    public void eliminarCliente(ClientesLogica c1) throws SQLException{
        //Declaración del Procedimiento Almacenado de Eliminación de Datos en la Relación cliente
        String sql = "{call sp_eliminarcliente(?)}";
        PreparedStatement st = (PreparedStatement) this.con.prepareStatement(sql);
        st.setString(1, c1.getRtnCliente());
        st.execute();
    }
    //Metodo para Mostrar Datos dentro de la jTable
    public List<ClientesLogica> getLista(String filtro) throws SQLException{
         String sql = "";
         boolean determinar = false;
         if(filtro.length()==0){  
             //Declaración del Procedimiento Almacenado de Mostrar Datos en la Relación cliente
             sql = "{call sp_listarcliente()}";
         }else{
             //Declaración del Procedimiento Almacenado de Busqueda de Datos en la Relación cliente
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
