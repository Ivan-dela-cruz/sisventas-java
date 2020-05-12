/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventas;

import conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import paneles.PnlVentas;
import static paneles.PnlVentas.TextField_rucClienteVenta;
import static paneles.PnlVentas.lbcod_cli;
import static paneles.PnlVentas.lblTotal;
import static paneles.PnlVentas.lblsubTotal1;
import static paneles.PnlVentas.lblvaTotal2;
import static paneles.PnlVentas.numVenta;
import static paneles.PnlVentas.tablaVentas;
import static paneles.PnlVentas.txtFecha;

/**
 *
 * @author Rojeru San CL
 */
public class Opciones {

    static ConexionBD cc = new ConexionBD();
    public static Connection cn = cc.conexion();
    static PreparedStatement ps;
//id_venta	fecha_venta	cantidad	num_venta	
    //total   id_caja	id_factura	precio_unitario	id_producto

    public static int registrar(Sentencias uc) {
        int rsu = 0;
        String sql = Sentencias.REGISTRAR;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, uc.getId_venta());
            ps.setString(2, uc.getFecha());
            ps.setInt(3, uc.getCantidad());
            ps.setInt(4, uc.getNumFactura());
            ps.setDouble(5, uc.getTotal());
            ps.setInt(6, uc.getId_caja());
            ps.setInt(7, uc.getId_factura());
            ps.setDouble(8, uc.getPrecioUnitario());
            ps.setInt(9, uc.getId_producto());

            rsu = ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        System.out.println(sql);
        return rsu;
    }

    public static int actualizar(Sentencias uc) {
        int rsu = 0;
        String sql = Sentencias.ACTUALIZAR;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, uc.getCantidad());
            ps.setDouble(2, uc.getTotal());
            ps.setString(3, uc.getFecha());
            ps.setInt(4, uc.getId_producto());
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

    public static void listartodoFacturasVendidas(String fecha1, String fecha2) {
        paneles.PnlGestionVentas.Table_Proveedores.removeAll();
        String[] titulos = {"N° FACTURA", "FECHA", "CLIENTE", "CI CLIENTE", "IGV", "SUBTOTAL", "TOTAL"};

        DefaultTableModel modelo;
        modelo = new DefaultTableModel(null, titulos);
        String sql = "";

        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }

        sql = "SELECT factura.num_fac, factura.fecha_fac,clientes.nom_cli,clientes.ci_cli,factura.igv,factura.subtotal, factura.total FROM clientes,factura WHERE factura.fecha_fac BETWEEN '" + fecha1 + "' AND '" + fecha2 + "' AND factura.id_clie = clientes.id_cli ORDER BY factura.num_fac;";

        //sql = "SELECT factura.num_fac,detallefactura.pre_total, factura.fecha_fac,detallefactura.des_pro,detallefactura.cant_pro,clientes.nom_cli,clientes.ci_cli FROM clientes, detallefactura, factura,productos WHERE factura.num_fac = '"+filterStrign+"' AND factura.id_clie = clientes.id_cli AND detallefactura.num_fac = factura.num_fac AND detallefactura.id_pro = productos.id_pro ORDER BY factura.num_fac;";
        String datos[] = new String[7];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                //cant_pro, cod_pro, des_pro, nom_pro, pcom_pro,pmayor_pro,pvp_pro,id_proveedor
                datos[0] = rs.getString("num_fac");
                datos[1] = rs.getString("fecha_fac");
                datos[2] = rs.getString("nom_cli");
                datos[3] = rs.getString("ci_cli");
                datos[4] = rs.getString("igv");
                datos[5] = rs.getString("subtotal");
                datos[6] = rs.getString("total");

                modelo.addRow(datos);
            }
            paneles.PnlGestionVentas.Table_Proveedores.setModel(modelo);

            TableColumnModel columnModel = paneles.PnlGestionVentas.Table_Proveedores.getColumnModel();

            columnModel.getColumn(0).setPreferredWidth(30);
            columnModel.getColumn(1).setPreferredWidth(30);
            columnModel.getColumn(2).setPreferredWidth(100);
            columnModel.getColumn(3).setPreferredWidth(100);
            columnModel.getColumn(4).setPreferredWidth(100);
            columnModel.getColumn(5).setPreferredWidth(20);
            columnModel.getColumn(6).setPreferredWidth(20);

        } catch (SQLException ex) {
            Logger.getLogger(Opciones.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public static void listartodoFacturasRegistro(String factura) {
        paneles.PnlGestionFacturas.Table_Proveedores.removeAll();
        String[] titulos = {"N° FACTURA", "COD PRODUCTO", "DESCRIPCCIÓN", "CANTIDAD", "PRECIO UNIT", "TOTAL"};

        DefaultTableModel modelo;
        modelo = new DefaultTableModel(null, titulos);
        String sql = "";

        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }

        sql = "SELECT detallefactura.num_fac,productos.cod_pro, detallefactura.des_pro, detallefactura.cant_pro, detallefactura.pre_unit, detallefactura.pre_total FROM detallefactura, factura, productos WHERE factura.num_fac = '" + factura + "' AND detallefactura.num_fac = factura.num_fac AND detallefactura.id_pro = productos.id_pro;";

        //sql = "SELECT factura.num_fac,detallefactura.pre_total, factura.fecha_fac,detallefactura.des_pro,detallefactura.cant_pro,clientes.nom_cli,clientes.ci_cli FROM clientes, detallefactura, factura,productos WHERE factura.num_fac = '"+filterStrign+"' AND factura.id_clie = clientes.id_cli AND detallefactura.num_fac = factura.num_fac AND detallefactura.id_pro = productos.id_pro ORDER BY factura.num_fac;";
        String datos[] = new String[7];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                //cant_pro, cod_pro, des_pro, nom_pro, pcom_pro,pmayor_pro,pvp_pro,id_proveedor
                datos[0] = rs.getString("num_fac");
                datos[1] = rs.getString("cod_pro");
                datos[2] = rs.getString("des_pro");
                datos[3] = rs.getString("cant_pro");
                datos[4] = rs.getString("pre_unit");
                datos[5] = rs.getString("pre_total");

                modelo.addRow(datos);
            }
            paneles.PnlGestionFacturas.Table_Proveedores.setModel(modelo);

            TableColumnModel columnModel = paneles.PnlGestionFacturas.Table_Proveedores.getColumnModel();

            columnModel.getColumn(0).setPreferredWidth(30);
            columnModel.getColumn(1).setPreferredWidth(70);
            columnModel.getColumn(2).setPreferredWidth(200);
            columnModel.getColumn(3).setPreferredWidth(50);
            columnModel.getColumn(4).setPreferredWidth(50);
            columnModel.getColumn(5).setPreferredWidth(50);

        } catch (SQLException ex) {
            Logger.getLogger(Opciones.class.getName()).log(Level.SEVERE, null, ex);

        }

    }
    
     public static void listarFacturasClientesAnuladas(String busca) {
        DefaultTableModel modelo = (DefaultTableModel) paneles.PnlGestionFacturas.Table_Proveedores.getModel();

        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
        String sql = "";
        if (busca.equals("")) {
            sql = "SELECT * FROM factura WHERE estado = 'ANULADA' ORDER BY num_fac";
        } else {
            
            sql = "SELECT * FROM factura WHERE (num_fac LIKE'" + busca + "%' OR "
                    + "ci LIKE'" + busca + "%' OR subtotal LIKE'" + busca + "%' OR "
                    + "igv LIKE'" + busca + "%' OR total LIKE'" + busca + "%' OR "
                    + "fecha_fac LIKE'" + busca + "%')  AND estado = 'ANULADA'"
                    + "ORDER BY num_fac";
        }
        String datos[] = new String[7];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("num_fac");
                datos[1] = rs.getString("ci");
                datos[2] = rs.getString("subtotal");
                datos[3] = rs.getString("igv");
                datos[4] = rs.getString("total");
                datos[5] = rs.getString("fecha_fac");
                datos[6] = rs.getString("estado");
                modelo.addRow(datos);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Opciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     public static void listarFacturasClientesCanceladas(String busca) {
        DefaultTableModel modelo = (DefaultTableModel) paneles.PnlGestionFacturas.Table_Proveedores.getModel();

        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
        String sql = "";
        if (busca.equals("")) {
            sql = "SELECT * FROM factura WHERE estado = 'CANCELADA' ORDER BY num_fac";
        } else {
            
            sql = "SELECT * FROM factura WHERE (num_fac LIKE'" + busca + "%' OR "
                    + "ci LIKE'" + busca + "%' OR subtotal LIKE'" + busca + "%' OR "
                    + "igv LIKE'" + busca + "%' OR total LIKE'" + busca + "%' OR "
                    + "fecha_fac LIKE'" + busca + "%')  AND estado = 'CANCELADA'"
                    + "ORDER BY num_fac";
        }
        String datos[] = new String[7];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("num_fac");
                datos[1] = rs.getString("ci");
                datos[2] = rs.getString("subtotal");
                datos[3] = rs.getString("igv");
                datos[4] = rs.getString("total");
                datos[5] = rs.getString("fecha_fac");
                datos[6] = rs.getString("estado");
                modelo.addRow(datos);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Opciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void listarFacturasClientes(String busca) {
        DefaultTableModel modelo = (DefaultTableModel) paneles.PnlGestionFacturas.Table_Proveedores.getModel();

        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
        String sql = "";
        if (busca.equals("")) {
            sql = "SELECT * FROM factura ORDER BY num_fac";
        } else {
            sql = "SELECT * FROM factura WHERE (num_fac LIKE'" + busca + "%' OR "
                    + "ci LIKE'" + busca + "%' OR subtotal LIKE'" + busca + "%' OR "
                    + "igv LIKE'" + busca + "%' OR total LIKE'" + busca + "%' OR "
                    + "fecha_fac LIKE'" + busca + "%') "
                    + "ORDER BY num_fac";
        }
        String datos[] = new String[7];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("num_fac");
                datos[1] = rs.getString("ci");
                datos[2] = rs.getString("subtotal");
                datos[3] = rs.getString("igv");
                datos[4] = rs.getString("total");
                datos[5] = rs.getString("fecha_fac");
                datos[6] = rs.getString("estado");
                modelo.addRow(datos);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Opciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void listartodoProductosVendidos(String fecha1, String fecha2) {
        paneles.PnlGestionVentas.Table_Proveedores.removeAll();
        String[] titulos = {"N° FACTURA", "FECHA", "CLIENTE", "CI CLIENTE", "PRODUCTO", "CANTIDAD", "IMPORTE"};

        DefaultTableModel modelo;
        modelo = new DefaultTableModel(null, titulos);
        String sql = "";

        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }

        sql = "SELECT factura.num_fac, factura.fecha_fac,clientes.ruc_cli,clientes.ci_cli,detallefactura.des_pro,detallefactura.cant_pro,detallefactura.pre_total FROM clientes, detallefactura, factura,productos WHERE factura.fecha_fac BETWEEN '" + fecha1 + "' AND '" + fecha2 + "' AND factura.id_clie = clientes.id_cli AND detallefactura.num_fac = factura.num_fac AND detallefactura.id_pro = productos.id_pro;";

        //sql = "SELECT factura.num_fac,detallefactura.pre_total, factura.fecha_fac,detallefactura.des_pro,detallefactura.cant_pro,clientes.nom_cli,clientes.ci_cli FROM clientes, detallefactura, factura,productos WHERE factura.num_fac = '"+filterStrign+"' AND factura.id_clie = clientes.id_cli AND detallefactura.num_fac = factura.num_fac AND detallefactura.id_pro = productos.id_pro ORDER BY factura.num_fac;";
        String datos[] = new String[7];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                //cant_pro, cod_pro, des_pro, nom_pro, pcom_pro,pmayor_pro,pvp_pro,id_proveedor
                datos[0] = rs.getString("num_fac");
                datos[1] = rs.getString("fecha_fac");
                datos[2] = rs.getString("ruc_cli");
                datos[3] = rs.getString("ci_cli");
                datos[4] = rs.getString("des_pro");
                datos[5] = rs.getString("cant_pro");
                datos[6] = rs.getString("pre_total");

                modelo.addRow(datos);
            }
            paneles.PnlGestionVentas.Table_Proveedores.setModel(modelo);

            TableColumnModel columnModel = paneles.PnlGestionVentas.Table_Proveedores.getColumnModel();

            columnModel.getColumn(0).setPreferredWidth(30);
            columnModel.getColumn(1).setPreferredWidth(30);
            columnModel.getColumn(2).setPreferredWidth(100);
            columnModel.getColumn(3).setPreferredWidth(100);
            columnModel.getColumn(4).setPreferredWidth(100);
            columnModel.getColumn(5).setPreferredWidth(20);
            columnModel.getColumn(6).setPreferredWidth(20);

        } catch (SQLException ex) {
            Logger.getLogger(Opciones.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public static void restablcerstock(String codi, String can) {
        int des = Integer.parseInt(can);
        String cap = "";
        int desfinal;
        String consul = "SELECT * FROM productos WHERE  cod_pro='" + codi + "'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(consul);
            while (rs.next()) {
                cap = rs.getString(2);
            }

        } catch (Exception e) {
        }

        desfinal = Integer.parseInt(cap) + des;
        String modi = "UPDATE productos SET cant_pro='" + desfinal + "' WHERE cod_pro = '" + codi + "'";
        try {
            PreparedStatement pst = cn.prepareStatement(modi);
            pst.executeUpdate();
        } catch (Exception e) {
        }

    }

    public static void descontarstock(String codi, String can) {
        int des = Integer.parseInt(can);
        String cap = "";
        int desfinal;
        String consul = "SELECT * FROM productos WHERE  id_pro='" + codi + "'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(consul);
            while (rs.next()) {
                cap = rs.getString(2);
            }

        } catch (Exception e) {
        }
        desfinal = Integer.parseInt(cap) - des;
        String modi = "UPDATE productos SET cant_pro='" + desfinal + "' WHERE id_pro = '" + codi + "'";
        try {
            PreparedStatement pst = cn.prepareStatement(modi);
            pst.executeUpdate();
        } catch (Exception e) {
        }

    }

    public static void anularFactura(String numero) {
        String anularfac = "UPDATE factura SET estado='ANULADA' WHERE num_fac = '" + numero + "'";
        try {
            PreparedStatement pst = cn.prepareStatement(anularfac);
            pst.executeUpdate();
            System.out.println("LA FACTURA FUE ANULADA CORRECTAMENTE ");
        } catch (Exception e) {
            System.out.println("NO SE PUDO ANULAR LA FACTURA  " + e);
        }
    }

    public static void numeros() {
        int j;
        int cont = 1;
        String num = "";
        String c = "";
        String SQL = "select max(num_fac) from factura";
        // String SQL="select count(*) from factura";
        //String SQL="SELECT MAX(cod_emp) AS cod_emp FROM empleado";
        //String SQL="SELECT @@identity AS ID";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {
                c = rs.getString(1);
            }

            if (c == null) {
                numVenta.setText("00000001");
            } else {
                j = Integer.parseInt(c);
                GenerarNumero gen = new GenerarNumero();
                gen.generar(j);
                numVenta.setText(gen.serie());

            }

        } catch (SQLException ex) {
            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void factura() {
        String InsertarSQL = "INSERT INTO factura (num_fac,id_clie,ruc_cli,subtotal,igv,total,fecha_fac,estado,nombres,direccion,ci) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        String numfac = numVenta.getText();
        String codcli = lbcod_cli.getText();
        String ruccli = TextField_rucClienteVenta.getText();
        String subtotal = lblsubTotal1.getText();
        String igv = lblvaTotal2.getText();
        String total = lblTotal.getText();
        String fecha = txtFecha.getText();
        String nombres = paneles.PnlVentas.TextField_NombreClienteVenta.getText() + " " + paneles.PnlVentas.TextField_ApellidioClienteVenta.getText();
        String direccion = paneles.PnlVentas.TextField_DireccioClienteVenta.getText();
        String ci = paneles.PnlVentas.TextField_CiClienteVenta.getText();
        String estado = "CANCELADA";

        try {
            PreparedStatement pst = cn.prepareStatement(InsertarSQL);
            pst.setString(1, numfac);
            pst.setString(2, codcli);
            pst.setString(3, ruccli);
            pst.setString(4, subtotal);
            pst.setString(5, igv);
            pst.setString(6, total);
            pst.setString(7, fecha);
            pst.setString(8, estado);
            pst.setString(9, nombres);
            pst.setString(10, direccion);
            pst.setString(11, ci);
            int n = pst.executeUpdate();
            if (n > 0) {
                System.out.println("Los datos se guardaron correctamente");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void detallefactura() {
        for (int i = 0; i < tablaVentas.getRowCount(); i++) {
            String InsertarSQL = "INSERT INTO detallefactura(num_fac,des_pro,cant_pro,pre_unit,pre_total,id_pro) VALUES (?,?,?,?,?,?)";
            String numfac = numVenta.getText();
            String codpro = tablaVentas.getValueAt(i, 0).toString();
            String despro = tablaVentas.getValueAt(i, 2).toString();
            String cantpro = tablaVentas.getValueAt(i, 3).toString();
            String preunit = tablaVentas.getValueAt(i, 4).toString();
            String importe = tablaVentas.getValueAt(i, 5).toString();

            try {
                PreparedStatement pst = cn.prepareStatement(InsertarSQL);
                pst.setString(1, numfac);
                pst.setString(2, despro);
                pst.setString(3, cantpro);
                pst.setString(4, preunit);
                pst.setString(5, importe);
                pst.setString(6, codpro);

                pst.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static String comparar(String cod) {
        String cant = "";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM productos WHERE id_pro='" + cod + "'");
            while (rs.next()) {
                cant = rs.getString(2);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cant;

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

    public static void listar(String busca) {
        DefaultTableModel modelo = (DefaultTableModel) ventas.Productos.tabla.getModel();

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

    public static void listarVentas(String busca) {
        DefaultTableModel modelo = (DefaultTableModel) ventas.ListaVentas.tabla.getModel();

        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
        String sql = "";
        if (busca.equals("")) {
            sql = Sentencias.LISTAR1;
        } else {
            sql = "SELECT * FROM ventas, productos WHERE (idventa LIKE'" + busca + "%' OR "
                    + "nom_pro LIKE'" + busca + "%' OR des_pro LIKE'" + busca + "%' OR "
                    + "total LIKE'" + busca + "%' OR fecha LIKE'" + busca + "%') "
                    + "AND id_pro = idpro";
        }
        String datos[] = new String[5];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("idventa");
                datos[1] = rs.getString("nombre");
                datos[2] = rs.getString("descripcion");
                datos[3] = rs.getString("total");
                datos[4] = rs.getString("fecha");
                modelo.addRow(datos);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Opciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void listarEntradas(String fecha) {
        DefaultTableModel modelo = (DefaultTableModel) ventas.ModalCorteDia.ListaEntradas.getModel();

        String sql = "SELECT * FROM ventas, productos WHERE fecha = '" + fecha + "' AND id_producto = idproducto";
        String datos[] = new String[3];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("nombre");
                datos[1] = (rs.getString("descripcion") + " - " + rs.getString("cantidad"));
                datos[2] = rs.getString("total");
                modelo.addRow(datos);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Opciones.class.getName()).log(Level.SEVERE, null, ex);
        }

        corteEntradas();
    }

    public static void corteEntradas() {
        int filas = ventas.ModalCorteDia.ListaEntradas.getRowCount();
        double totalE = 0.0;
        for (int i = 0; i < filas; i++) {
            totalE = totalE + Double.parseDouble(ventas.ModalCorteDia.ListaEntradas.getValueAt(i, 2).toString());
        }
        ventas.ModalCorteDia.lblE.setText(String.valueOf(totalE));
    }

    public static void listarSalidas(String fecha) {
        DefaultTableModel modelo = (DefaultTableModel) ventas.ModalCorteDia.ListaSalidas.getModel();

        String sql = "SELECT * FROM gastos WHERE fecha_gasto = '" + fecha + "'";
        String datos[] = new String[2];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("descripcion");
                datos[1] = rs.getString("gastado");
                modelo.addRow(datos);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Opciones.class.getName()).log(Level.SEVERE, null, ex);
        }

        corteSalidas();
    }

    public static void corteSalidas() {
        int filas = ventas.ModalCorteDia.ListaSalidas.getRowCount();
        double totalE = 0.0;
        for (int i = 0; i < filas; i++) {
            totalE = totalE + Double.parseDouble(ventas.ModalCorteDia.ListaSalidas.getValueAt(i, 1).toString());
        }
        ventas.ModalCorteDia.lblS.setText(String.valueOf(totalE));
    }

    public static void corteTotal() {
        double entradas = Double.parseDouble(ventas.ModalCorteDia.lblE.getText());
        double salidas = Double.parseDouble(ventas.ModalCorteDia.lblS.getText());
        ventas.ModalCorteDia.total.setText(String.valueOf(entradas - salidas));
    }

    public static void calcular() {
        String pre;
        String can;
        double total = 0;
        double precio;
        double subtotoal = 0;
        double iva = 0.14;
        double valIva = 0;

        int cantidad;
        double imp = 0.0;

        for (int i = 0; i < paneles.PnlVentas.tablaVentas.getRowCount(); i++) {
            System.out.println(paneles.PnlVentas.tablaVentas.getValueAt(i, 4).toString());
            System.out.println(paneles.PnlVentas.tablaVentas.getValueAt(i, 3).toString());

            pre = paneles.PnlVentas.tablaVentas.getValueAt(i, 4).toString();
            can = paneles.PnlVentas.tablaVentas.getValueAt(i, 3).toString();
            precio = Double.parseDouble(pre);
            cantidad = Integer.parseInt(can);
            imp = precio * cantidad;
            subtotoal = subtotoal + (imp - (imp * iva));

            total = total + imp;
            valIva = valIva + (total * iva);

            paneles.PnlVentas.tablaVentas.setValueAt(Math.rint(imp * 100) / 100, i, 5);

        }
        paneles.PnlVentas.lblTotal.setText("" + Math.rint(total * 100) / 100);
        paneles.PnlVentas.lblsubTotal1.setText("" + Math.rint(subtotoal * 100) / 100);
        paneles.PnlVentas.lblvaTotal2.setText("" + Math.rint(valIva * 100) / 100);
    }

    public static void corteCaja() {
        int filas = ventas.ListaVentas.tabla.getRowCount();
        double totalE = 0.0;
        for (int i = 0; i < filas; i++) {
            totalE = totalE + Double.parseDouble(ventas.ListaVentas.tabla.getValueAt(i, 3).toString());
        }
        ventas.ListaVentas.lblTotal1.setText(String.valueOf(totalE));
    }

    public static void enviar(int codigo, int cantidad) {
        DefaultTableModel tabladet = (DefaultTableModel) paneles.PnlVentas.tablaVentas.getModel();
        String[] dato = new String[6];
        int fila = ventas.Productos.tabla.getSelectedRow();

        String sql = "SELECT * FROM productos WHERE id_pro = " + codigo;
        String cod = String.valueOf(codigo);
        String nombre = null;
        String descripcion = null;
        String precio = null;
        String cant = String.valueOf(cantidad);
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                nombre = rs.getString("nom_pro");
                descripcion = rs.getString("des_pro");
                precio = rs.getString("pvp_pro");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Opciones.class.getName()).log(Level.SEVERE, null, ex);
        }

        int c = 0;
        int j = 0;

        for (int i = 0; i < paneles.PnlVentas.tablaVentas.getRowCount(); i++) {
            Object com = paneles.PnlVentas.tablaVentas.getValueAt(i, 0);
            Object cant1 = paneles.PnlVentas.tablaVentas.getValueAt(i, 3);
            if (cod.equals(com)) {
                j = i;
                int cantT = Integer.parseInt(cant) + Integer.parseInt((String) cant1);
                paneles.PnlVentas.tablaVentas.setValueAt(String.valueOf(cantT), i, 3);
                c++;
                calcular();
                paneles.PnlVentas.txtImporte.setText("");
                paneles.PnlVentas.txtCambio.setText("");
            }
        }
        if (c == 0) {

            dato[0] = cod;
            dato[1] = nombre;
            dato[2] = descripcion;
            dato[3] = cant;
            dato[4] = precio;

            tabladet.addRow(dato);

            paneles.PnlVentas.tablaVentas.setModel(tabladet);
            calcular();

            paneles.PnlVentas.txtImporte.setText("");
            paneles.PnlVentas.txtCambio.setText("");
        }
    }

    public static void numerosVenta() {
        int c = 0;
        String SQL = "SELECT MAX(id_venta) FROM ventas";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {
                c = rs.getInt(1);
            }

            if (c == 0) {
                paneles.PnlVentas.numVenta.setText("1");
            } else {
                paneles.PnlVentas.numVenta.setText(String.valueOf(c + 1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Opciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
