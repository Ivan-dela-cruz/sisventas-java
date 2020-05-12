package conexion;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Sony
 */
public class ConexionBD {

    Connection conect = null;
    static Statement sentencia;
    static ResultSet resultado;
    static java.sql.Connection conexion=null;

    public static ArrayList<String> llenar_combo(String tabla, String columna) {
        ArrayList<String> lista = new ArrayList<String>();
        String q = "SELECT * FROM " + tabla;
        try {
            resultado = sentencia.executeQuery(q);
            System.out.println("Correcto");
        } catch (Exception e) {
            System.out.println("No Correcto"+e);
        }
        try {
            while (resultado.next()) {
                lista.add(resultado.getString(columna));
            }
        } catch (Exception e) {
        }
        return lista;
    }

    public Connection conexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conect = DriverManager.getConnection("jdbc:mysql://localhost/sisventas2", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la conexión" + e);
        }
        return conect;
    }
    public static void conectar(){
        String ruta="jdbc:mysql://localhost/sisventas2";
        String user="root";
        String pass="";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion=DriverManager.getConnection(ruta,user,pass); 
            sentencia= conexion.createStatement();
            System.out.println("Conectado");
        } catch (Exception e) {
            System.out.println("No conectado");
        }
    }
//    
//     public Connection Desconectar() {
//        try {
//            conect.close();
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Error en la conexión" + e);
//        }
//        return conect;
//    }   
}
