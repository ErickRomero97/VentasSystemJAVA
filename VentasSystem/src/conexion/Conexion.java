package conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class Conexion {
     public static Connection conectar(){
         //Creación de la Conexión con el Servidor ubicado en MySQL.
        try{
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/ventasystem","root","TITO141397");
        }catch(ClassNotFoundException e){
            JOptionPane.showMessageDialog(null,e.getMessage());
            return null;
        } catch (SQLException ex) {
             Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
             JOptionPane.showMessageDialog(null,ex.getMessage());
              return null;
         }
        
    }
    public static String nombreapp() throws SQLException{
        return "VentasSystem";
    }
}
