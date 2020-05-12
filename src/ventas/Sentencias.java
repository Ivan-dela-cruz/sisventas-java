/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventas;

/**
 *
 * @author Rojeru San CL
 */
public class Sentencias {

    public static String LISTAR = "SELECT * FROM productos ORDER BY nom_pro";
    public static String LISTAR1 = "SELECT * FROM ventas, productos WHERE id_pro = idpro";
//id_venta	fecha_venta	cantidad	num_venta	total	id_caja	,id_factura	precio_unitario,	id_pro

    public static String REGISTRAR = "INSERT INTO ventas(id_venta, fecha_venta, cantidad, num_venta, total,id_caja,id_factura,precio_unitario,id_pro) "
            + "VALUES(?,?,?,?,?,?,?,?,?)";

    public static String ACTUALIZAR = "UPDATE productos SET "
            + "cant_pro=?, "
            + "cod_pro=?, "
            + "des_pro=?, "
            + "nom_pro=?, "
            + "pcom_pro=? "
            + "pmayor_pro=?, "
            + "pvp_pro=?, "
            + "id_proveedor=? "
            + "WHERE id_pro=?";

    public static String ELIMINAR = "DELETE FROM ventas WHERE id_venta = ?";

    public static String ELIMINAR_TODO = "TRUNCATE TABLE ventas";
//id_venta	fecha_venta	cantidad	num_venta	
    //total   id_caja	id_factura	precio_unitario	id_producto

    private int id_venta;
    private String fecha;
    private int cantidad;
    private int numFactura;
    private double total;
    private int id_caja;
    private int id_factura;
    private double precioUnitario;
    ;
    private int id_producto;

    public Sentencias() {
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(int numFactura) {
        this.numFactura = numFactura;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getId_caja() {
        return id_caja;
    }

    public void setId_caja(int id_caja) {
        this.id_caja = id_caja;
    }

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    @Override
    public String toString() {
        return "Sentencias{" + "id_venta=" + id_venta + ", fecha=" + fecha + ", cantidad=" + cantidad + ", numFactura=" + numFactura + ", total=" + total + ", id_caja=" + id_caja + ", id_factura=" + id_factura + ", precioUnitario=" + precioUnitario + ", id_producto=" + id_producto + '}';
    }

}
