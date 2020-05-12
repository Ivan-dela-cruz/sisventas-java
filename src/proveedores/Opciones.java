/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proveedores;

import clientes.*;
import producto.*;
import conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import paneles.PnlInventario;
import static paneles.PnlInventario.Table_Proveedores;

/**
 *
 * @author Rojeru San CL
 */
public class Opciones {

    static ConexionBD cc = new ConexionBD();
    static Connection cn = cc.conexion();
    static PreparedStatement ps;
    DefaultTableModel modelo2;

    public static int registrar(Sentencias uc) {
        int rsu = 0;
        String sql = Sentencias.REGISTRAR;

     /*
                                                            Textos completos	
                                                            id_proveedor
                                                            ape_proveedor
                                                            ci_proveedor
                                                            dir_proveedor
                                                            email_proveedor
                                                            empresa_proveedor
                                                            nom_proveedor
                                                            ruc_proveedor
                                                            telf_proveedor*/
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, uc.getApe());
            ps.setString(2, uc.getCi());
            ps.setString(3, uc.getDir());
            ps.setString(4, uc.getEmail());
            ps.setString(5, uc.getEmpresa());
            ps.setString(6, uc.getNom());
            ps.setString(7, uc.getRuc());
            ps.setString(8, uc.getTelf());

            rsu = ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return rsu;
    }

    public static int actualizar(Sentencias uc) {
        int rsu = 0;
        String sql = Sentencias.ACTUALIZAR;
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, uc.getApe());
            ps.setString(2, uc.getCi());
            ps.setString(3, uc.getDir());
            ps.setString(4, uc.getEmail());
            ps.setString(5, uc.getEmpresa());
            ps.setString(6, uc.getNom());
            ps.setString(7, uc.getRuc());
            ps.setString(8, uc.getTelf());
            ps.setInt(9, uc.getId());
            rsu = ps.executeUpdate();
        } catch (SQLException ex) {
        }
        System.out.println(sql);
        return rsu;
    }

    public static int eliminar(int id) {
        int rsu = 0;
        String sql = Sentencias.ELIMINAR;

        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            rsu = ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println(sql);
        return rsu;
    }

    public static int eliminarTodo() {
        int rsu = 0;
        String sql = Sentencias.ELIMINAR_TODO;

        try {
            ps = cn.prepareStatement(sql);
            rsu = ps.executeUpdate();
            rsu++;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println(sql);
        return rsu;
    }
//cant_pro, cod_pro, des_pro, nom_pro, pcom_pro,pmayor_pro,pvp_pro,id_proveedor
    
    /*
                                                            Textos completos	
                                                            id_proveedor
                                                            ape_proveedor
                                                            ci_proveedor
                                                            dir_proveedor
                                                            email_proveedor
                                                            empresa_proveedor
                                                            nom_proveedor
                                                            ruc_proveedor
                                                            telf_proveedor*/

    public static void listarProveedor(String busca) {
        DefaultTableModel modelo = (DefaultTableModel) paneles.PnlProveedor.Table_Proveedores.getModel();

        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
        String sql = "";
        if (busca.equals("")) {
            sql = Sentencias.LISTAR;
        } else {

            sql = "SELECT * FROM proveedores WHERE (id_proveedor LIKE'" + busca + "%' OR "
                    + "ape_proveedor LIKE'" + busca + "%' OR empresa_proveedor LIKE'" + busca + "%' OR "
                    + "dir_proveedor LIKE'" + busca + "%' OR ruc_proveedor LIKE'" + busca + "%' OR "
                    + "ci_proveedor LIKE'" + busca + "%') "
                    + "ORDER BY ape_proveedor";
        }
        String datos[] = new String[9];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                //cant_pro, cod_pro, des_pro, nom_pro, pcom_pro,pmayor_pro,pvp_pro,id_proveedor
               /* id_proveedor
                                                            ape_proveedor
                                                            ci_proveedor
                                                            dir_proveedor
                                                            email_proveedor
                                                            empresa_proveedor
                                                            nom_proveedor
                                                            ruc_proveedor
                                                            telf_proveedor*/
                datos[0] = rs.getString("id_proveedor");
                datos[1] = rs.getString("ci_proveedor");
                datos[2] = rs.getString("ruc_proveedor");
                datos[3] = rs.getString("nom_proveedor");
                datos[4] = rs.getString("ape_proveedor");
                datos[5] = rs.getString("dir_proveedor");
                datos[6] = rs.getString("email_proveedor");
                datos[7] = rs.getString("telf_proveedor");
                datos[8] = rs.getString("empresa_proveedor");
                modelo.addRow(datos);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Opciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String[] listarClienteVenta(String busca) {

        String sql = "";
        if (busca.equals("")) {
            sql = Sentencias.LISTAR;
        } else {

            /*

                            id_cli
                            ape_cli
                            ci_cli
                            dir_cli
                            nom_cli
                            ruc_cli
                            telf_cli*/
            sql = "SELECT * FROM clientes WHERE (id_cli LIKE'" + busca + "%' OR "
                    + "nom_cli LIKE'" + busca + "%' OR dir_cli LIKE'" + busca + "%' OR "
                    + "ruc_cli LIKE'" + busca + "%' OR ci_cli LIKE'" + busca + "%' OR "
                    + "telf_cli LIKE'" + busca + "%') "
                    + "ORDER BY ape_cli";
        }
        String datos[] = new String[7];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                //cant_pro, cod_pro, des_pro, nom_pro, pcom_pro,pmayor_pro,pvp_pro,id_proveedor
                datos[0] = rs.getString("id_cli");
                datos[1] = rs.getString("ci_cli");
                datos[2] = rs.getString("nom_cli");
                datos[3] = rs.getString("ape_cli");
                datos[4] = rs.getString("dir_cli");
                datos[5] = rs.getString("telf_cli");
                datos[6] = rs.getString("ruc_cli");

            }
            return datos;
        } catch (SQLException ex) {
            
            Logger.getLogger(Opciones.class.getName()).log(Level.SEVERE, null, ex);
            return datos;
        }
    }

    public static void listar(String busca) {
        DefaultTableModel modelo = (DefaultTableModel) paneles.PnlCliente.Table_Clientes.getModel();

        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
        String sql = "";
        if (busca.equals("")) {
            sql = Sentencias.LISTAR;
        } else {

            /*

                            id_cli
                            ape_cli
                            ci_cli
                            dir_cli
                            nom_cli
                            ruc_cli
                            telf_cli*/
            sql = "SELECT * FROM clientes WHERE (id_cli LIKE'" + busca + "%' OR "
                    + "nom_cli LIKE'" + busca + "%' OR dir_cli LIKE'" + busca + "%' OR "
                    + "ruc_cli LIKE'" + busca + "%' OR ci_cli LIKE'" + busca + "%' OR "
                    + "telf_cli LIKE'" + busca + "%') "
                    + "ORDER BY ape_cli";
        }
        String datos[] = new String[7];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                //cant_pro, cod_pro, des_pro, nom_pro, pcom_pro,pmayor_pro,pvp_pro,id_proveedor
                datos[0] = rs.getString("id_cli");
                datos[1] = rs.getString("ci_cli");
                datos[2] = rs.getString("nom_cli");
                datos[3] = rs.getString("ape_cli");
                datos[4] = rs.getString("dir_cli");
                datos[5] = rs.getString("telf_cli");
                datos[6] = rs.getString("ruc_cli");
                modelo.addRow(datos);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Opciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int extraerID() {
        int c = 0;
        String SQL = "SELECT MAX(id_cli) FROM clientes";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()) {
                c = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Opciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

    public static void iniciarTransaccion() {
        try {
            cn.setAutoCommit(false);
            System.out.println("iniciarTransaccion() :   abierta");
        } catch (SQLException ex) {
            Logger.getLogger(Opciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void finalizarTransaccion() {
        try {
            cn.commit();
            System.out.println("finalizarTransaccion() :   cerrada");
        } catch (SQLException ex) {
            Logger.getLogger(Opciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void cancelarTransaccion() {
        try {
            cn.rollback();
        } catch (SQLException ex) {
            Logger.getLogger(Opciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
