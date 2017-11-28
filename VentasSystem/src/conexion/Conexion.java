package conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import javax.swing.JOptionPane;

public class Conexion {
     public static Connection conectar() throws SQLException{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Conectado a base de datos");      
            return
            DriverManager.getConnection("jdbc:mysql://localhost:3306/ventasystem","root", "null");
        }catch(ClassNotFoundException e){
            throw new SQLException(e.getMessage());
        }
    }
    public static String nombreapp() throws SQLException{
        return "VentasSystem";
    }
}
