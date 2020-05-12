/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producto;

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
import javax.swing.table.TableColumnModel;
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

        //cant_pro, cod_pro, des_pro, nom_pro, pcom_pro,pmayor_pro,pvp_pro,id_proveedor,tipo
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, uc.getCantidad());
            ps.setString(2, uc.getCod());
            ps.setString(3, uc.getDes());
            ps.setString(4, uc.getNom());
            ps.setDouble(5, uc.getPc());
            ps.setDouble(6, uc.getPm());
            ps.setDouble(7, uc.getPv());
            ps.setInt(8, uc.getIdprovedor());
            ps.setString(9, uc.getTipo());
            rsu = ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return rsu;
    }

    public static String[] buscarEdit(int busca) {

        String sql  = "SELECT * FROM productos WHERE id_pro='" + busca + "'";

        String datos[] = new String[10];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                //cant_pro, cod_pro, des_pro, nom_pro, pcom_pro,pmayor_pro,pvp_pro,id_proveedor,tipo
                datos[0] = rs.getString("id_pro");
                datos[1] = rs.getString("cant_pro");
                datos[2] = rs.getString("cod_pro");
                datos[3] = rs.getString("des_pro");
                datos[4] = rs.getString("nom_pro");
                datos[5] = rs.getString("pcom_pro");
                datos[6] = rs.getString("pmayor_pro");
                datos[7] = rs.getString("pvp_pro");
                datos[8] = rs.getString("id_proveedor");
                datos[9] = rs.getString("tipo");

            }
            return datos;

        } catch (SQLException ex) {
            
            Logger.getLogger(Opciones.class.getName()).log(Level.SEVERE, null, ex);
            return datos;
        }
        
    }

    public static int actualizar(Sentencias uc) {
        int rsu = 0;
        String sql = Sentencias.ACTUALIZAR;
        try {
            ps = cn.prepareStatement(sql);

            ps.setInt(1, uc.getCantidad());

            ps.setString(2, uc.getCod());

            ps.setString(3, uc.getDes());

            ps.setString(4, uc.getNom());

            ps.setDouble(5, uc.getPc());

            ps.setDouble(6, uc.getPm());

            ps.setDouble(7, uc.getPv());

            ps.setInt(8, uc.getIdprovedor());

            ps.setString(9, uc.getTipo());
            ps.setInt(10, uc.getId());

//            ps.setInt(1, uc.getId());
            rsu = ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println(sql);
        return rsu;
    }

    public static int actualizarStock(Sentencias uc) {
        int rsu = 0;
        String sql = Sentencias.ACTUALIZAR_STOCK;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, uc.getCantidad());

            ps.setInt(2, uc.getId());

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

    public static void listar(String busca) {
        DefaultTableModel modelo = (DefaultTableModel) paneles.PnlCompras.Table_Proveedores.getModel();

        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
        String sql = "";
        if (busca.equals("")) {
            sql = Sentencias.LISTAR;
        } else {
            sql = "SELECT * FROM productos WHERE (id_pro LIKE'" + busca + "%' OR "
                    + "nom_pro LIKE'" + busca + "%' OR cod_pro LIKE'" + busca + "%' OR "
                    + "tipo LIKE'" + busca + "%' OR pvp_pro LIKE'" + busca + "%' OR "
                    + "id_pro LIKE'" + busca + "%') "
                    + "ORDER BY id_pro";
        }
        String datos[] = new String[6];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                //cant_pro, cod_pro, des_pro, nom_pro, pcom_pro,pmayor_pro,pvp_pro,id_proveedor
                datos[0] = rs.getString("id_pro");
                datos[1] = rs.getString("nom_pro");
                datos[2] = rs.getString("cod_pro");
                datos[3] = rs.getString("tipo");
                datos[4] = rs.getString("pvp_pro");
                datos[5] = rs.getString("cant_pro");
                modelo.addRow(datos);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Opciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    public static void listarProductos(String busca) {
//        DefaultTableModel modelo = (DefaultTableModel) paneles.PnlCompras.Table_Proveedores.getModel();
//
//        while (modelo.getRowCount() > 0) {
//            modelo.removeRow(0);
//        }
//        String sql = "";
//        if (busca.equals("")) {
//            sql = Sentencias.LISTAR;
//        } else {
//            
//            //"id", "Codigo", "Nombre", "Descripción", "Tipo", "Cantidad", "P. compra", "P. venta", "Proveedor"
//            sql = "SELECT * FROM productos WHERE (id_pro LIKE'" + busca + "%' OR "
//                    + "nom_pro LIKE'" + busca + 
//                    "%' OR cod_pro LIKE'" + busca +
//                    "%' OR nom_pro LIKE'" + busca + 
//                    "%' OR des_pro LIKE'" + busca + 
//                    "%' OR pcom_pro LIKE'" + busca +
//                    "%' OR pmayor_pro LIKE'" + busca +
//                    "%' OR pvp_pro LIKE'" + busca +
//                    "%') ORDER BY id_pro";
//        }
//        String datos[] = new String[8];
//        try {
//            Statement st = cn.createStatement();
//            ResultSet rs = st.executeQuery(sql);
//            while (rs.next()) {
//                //cant_pro, cod_pro, des_pro, nom_pro, pcom_pro,pmayor_pro,pvp_pro,id_proveedor
//                datos[0] = rs.getString("id_pro");
//                datos[1] = rs.getString("nom_pro");
//                datos[2] = rs.getString("cod_pro");
//                datos[1] = rs.getString("tipo");
//                datos[2] = rs.getString("cod_pro");
//                datos[3] = rs.getString("tipo");
//                datos[4] = rs.getString("pvp_pro");
//                datos[5] = rs.getString("cant_pro");
//                modelo.addRow(datos);
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(Opciones.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    public static void listartodoProductos() {
        paneles.PnlProductos.Table_Proveedores.removeAll();
        String[] titulos = {"id", "Codigo", "Nombre", "Proveedor", "Categoría", "Stock", "P. compra", "P. venta"};

        DefaultTableModel modelo;
        modelo = new DefaultTableModel(null, titulos);

        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
        String sql = "SELECT productos.id_pro,productos.cod_pro,productos.nom_pro,productos.des_pro,productos.tipo,productos.cant_pro,productos.pcom_pro,productos.pvp_pro,productos.pmayor_pro,proveedores.empresa_proveedor FROM proveedores,productos WHERE productos.id_proveedor = proveedores.id_proveedor ORDER BY productos.nom_pro;";

        String datos[] = new String[8];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                //cant_pro, cod_pro, des_pro, nom_pro, pcom_pro,pmayor_pro,pvp_pro,id_proveedor
                datos[0] = rs.getString("id_pro");
                datos[1] = rs.getString("cod_pro");
                datos[2] = rs.getString("nom_pro");
                datos[3] = rs.getString("empresa_proveedor");
                datos[4] = rs.getString("tipo");
                datos[5] = rs.getString("cant_pro");
                datos[6] = rs.getString("pcom_pro");
                datos[7] = rs.getString("pvp_pro");

                modelo.addRow(datos);
            }
            paneles.PnlProductos.Table_Proveedores.setModel(modelo);

            TableColumnModel columnModel = paneles.PnlProductos.Table_Proveedores.getColumnModel();

            columnModel.getColumn(0).setPreferredWidth(10);
            columnModel.getColumn(1).setPreferredWidth(70);
            columnModel.getColumn(2).setPreferredWidth(100);
            columnModel.getColumn(3).setPreferredWidth(100);
            columnModel.getColumn(4).setPreferredWidth(70);
            columnModel.getColumn(5).setPreferredWidth(20);
            columnModel.getColumn(6).setPreferredWidth(20);
            columnModel.getColumn(7).setPreferredWidth(20);

        } catch (SQLException ex) {
            Logger.getLogger(Opciones.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public static void listartodo() {
        Table_Proveedores.removeAll();
        String[] titulos = {"id", "Codigo", "Nombre", "Descripción", "Tipo", "Cantidad", "P. compra", "P. venta", "Proveedor"};

        DefaultTableModel modelo;
        modelo = new DefaultTableModel(null, titulos);

        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
        String sql = "SELECT productos.id_pro,productos.cod_pro,productos.nom_pro,productos.des_pro,productos.tipo,productos.cant_pro,productos.pcom_pro,productos.pvp_pro,productos.pmayor_pro,proveedores.empresa_proveedor FROM proveedores,productos WHERE productos.id_proveedor = proveedores.id_proveedor ORDER BY productos.nom_pro;";

        String datos[] = new String[9];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                //cant_pro, cod_pro, des_pro, nom_pro, pcom_pro,pmayor_pro,pvp_pro,id_proveedor
                datos[0] = rs.getString("id_pro");
                datos[1] = rs.getString("cod_pro");
                datos[2] = rs.getString("nom_pro");
                datos[3] = rs.getString("des_pro");
                datos[4] = rs.getString("tipo");
                datos[5] = rs.getString("cant_pro");
                datos[6] = rs.getString("pcom_pro");
                datos[7] = rs.getString("pvp_pro");
                datos[8] = rs.getString("empresa_proveedor");

                modelo.addRow(datos);
            }
            PnlInventario.Table_Proveedores.setModel(modelo);

            TableColumnModel columnModel = Table_Proveedores.getColumnModel();

            columnModel.getColumn(0).setPreferredWidth(10);
            columnModel.getColumn(1).setPreferredWidth(70);
            columnModel.getColumn(2).setPreferredWidth(100);
            columnModel.getColumn(3).setPreferredWidth(100);
            columnModel.getColumn(4).setPreferredWidth(70);
            columnModel.getColumn(5).setPreferredWidth(20);
            columnModel.getColumn(6).setPreferredWidth(20);
            columnModel.getColumn(7).setPreferredWidth(20);
            columnModel.getColumn(8).setPreferredWidth(70);

        } catch (SQLException ex) {
            Logger.getLogger(Opciones.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public static int extraerCantidad(int id) {
        int c = 0;
        //SELECT cant_pro FROM productos WHERE id_pro = '7';
        String SQL = "SELECT cant_pro FROM productos WHERE id_pro = '" + id + "'";

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

    public static int extraerID() {
        int c = 0;
        String SQL = "SELECT MAX(id_pro) FROM productos";

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
