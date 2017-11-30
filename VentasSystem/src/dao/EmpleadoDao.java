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
    //Instansacion de la Clase de Conexión.
    private final Connection con;
    public EmpleadoDao() throws SQLException{
        this.con = Conexion.conectar();
    }
    //Declaración del Metodo de Inserción de Datos en la Relación empleado
    public void insertar(EmpleadoLogica c1) throws SQLException{
        //Declaración del Procedimiento Almacenado de Inserción de Datos en la Relación empleado
        String sql = "{call sp_ingresarempleado(?,?,?,?,?,?)}";
        PreparedStatement st = (PreparedStatement) this.con.prepareStatement(sql);
        st.setString(1, c1.getIdempleado());
        st.setString(2, c1.getNombre());
        st.setString(3, c1.getApellido());
        st.setString(4, c1.getTelefono());
        st.setString(5, c1.getDireccion());
        st.setInt(6, c1.getIdSexo());
        st.execute();
    }
    
    //Declaración delMetodo de Edición de Datos en la Relación empleado
    public void editar(EmpleadoLogica c1) throws SQLException{
        //Declaración del Procedimiento Almacenado de Edición de Datos en la Relación empleado
        String sql = "{call sp_actualizarempleado(?,?,?,?,?,?)}";
        PreparedStatement st = (PreparedStatement) this.con.prepareStatement(sql);
        st.setString(1, c1.getNombre());
        st.setString(2, c1.getApellido());
        st.setString(3, c1.getTelefono());
        st.setString(4, c1.getDireccion());
        st.setInt(5, c1.getIdSexo());
        st.setString(6, c1.getIdempleado());
        st.execute();
    }
    
    //Declaración del Metodo de Lista de Datos en la Relación empleado
    public List<EmpleadoLogica> getLista(String filtro) throws SQLException{
         String sql = "";
         boolean determinar = false;
         if(filtro.length()==0){
             //Declaración del Procedimiento Almacenado de Mostrar Datos dn la Relación empleado
             sql = "{call sp_listarempleado()} ";
         }else{
             //Declaración del Procedimiento Almacenado de Busqueda de Datos en la Relación empleado
             sql = "{call sp_buscarempleado(?)}";
             determinar = true;
         }
         List<EmpleadoLogica> miLista;
         try(PreparedStatement st = (PreparedStatement) this.con.prepareStatement(sql)){
            if(determinar == true){
                 st.setString(1, filtro);
             }  
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
