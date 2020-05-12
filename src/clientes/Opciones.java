/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientes;

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
import static paneles.PnlVentas.lbcod_cli;

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
	
id_cli
ape_cli
ci_cli
dir_cli
nom_cli
ruc_cli
telf_cli*/
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, uc.getId_cli());
            ps.setString(2, uc.getApe());
            ps.setString(3, uc.getCi());
            ps.setString(4, uc.getDir());
            ps.setString(5, uc.getNom());
            ps.setString(6, uc.getRuc());
            ps.setString(7, uc.getTelf());

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
            ps.setString(4, uc.getNom());
            ps.setString(5, uc.getRuc());
            ps.setString(6, uc.getTelf());
            ps.setInt(7, uc.getId());
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

    public static void listarVenta(String busca) {
        DefaultTableModel modelo = (DefaultTableModel) ventas.ClientesVenta.tablaClientes.getModel();

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

    public static String[] BuscarCliente(String busca) {

        String sql = "";
        /*id_cli,ape_cli,ci_cli,dir_cli,nom_cli,ruc_cli,telf_cli*/
        sql = "SELECT * FROM clientes WHERE ci_cli='"+busca+"'";

        String datos[] = new String[2];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                //cant_pro, cod_pro, des_pro, nom_pro, pcom_pro,pmayor_pro,pvp_pro,id_proveedor
                datos[0] = rs.getString("id_cli");
                datos[1] = rs.getString("ci_cli");

            }
            //lbcod_cli.setText(datos[0]);
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
