/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producto;

/**
 *
 * @author Rojeru San CL
 */
public class Sentencias {

    public static String LISTAR = "SELECT * FROM productos ORDER BY tipo";
    public static String LISTAR_AL = "SELECT * FROM productos WHERE stock!='' ORDER BY tipo";

    public static String REGISTRAR = "INSERT INTO productos(cant_pro, cod_pro, des_pro, nom_pro, pcom_pro,pmayor_pro,pvp_pro,id_proveedor,tipo) "
            + "VALUES(?,?,?,?,?,?,?,?,?)";

    /*
    
    `id_pro`, `cant_pro`, `cod_pro`, `des_pro`, `nom_pro`, `pcom_pro`, `pmayor_pro`, `poveedor_pro`, `pvp_pro`, `id_proveedor`
     */
    public static String ACTUALIZAR = "UPDATE productos SET "
            + "cant_pro=?, "
            + "cod_pro=?, "
            + "des_pro=?, "
            + "nom_pro=?, "
            + "pcom_pro=?, "
            + "pmayor_pro=?, "
            + "pvp_pro=?, "
            + "id_proveedor=?, "
            + "tipo=? "
            + "WHERE id_pro=?";

    public static String ACTUALIZAR_STOCK = "UPDATE productos SET "
            + "cant_pro=? "
            + "WHERE id_pro=?";

    public static String ELIMINAR = "DELETE FROM productos WHERE id_pro = ?";

    public static String ELIMINAR_TODO = "TRUNCATE TABLE productos";

    private String cod ;
    private String nom ;
    private String des ;
    private double pc ;
    private double pv ;
    private double pm ;
    private int idprovedor;
    private int cantidad ;
    private String tipo ;
    private int id;

    public Sentencias() {
    }

    
    @Override
    public String toString() {
        return "Sentencias{" + "cod=" + cod + ", nom=" + nom + ", des=" + des + ", pc=" + pc + ", pv=" + pv + ", pm=" + pm + ", idprovedor=" + idprovedor + ", cantidad=" + cantidad + ", tipo=" + tipo + ", id=" + id + '}';
    }

    public Sentencias(String cod, String nom, String des, double pc, double pv, double pm, int idprovedor, int cantidad, String tipo) {
        this.cod = cod;
        this.nom = nom;
        this.des = des;
        this.pc = pc;
        this.pv = pv;
        this.pm = pm;
        this.idprovedor = idprovedor;
        this.cantidad = cantidad;
        this.tipo = tipo;
    }

    public Sentencias(int cantidad, int id) {
        this.cantidad = cantidad;
        this.id = id;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public double getPc() {
        return pc;
    }

    public void setPc(double pc) {
        this.pc = pc;
    }

    public double getPv() {
        return pv;
    }

    public void setPv(double pv) {
        this.pv = pv;
    }

    public double getPm() {
        return pm;
    }

    public void setPm(double pm) {
        this.pm = pm;
    }

    public int getIdprovedor() {
        return idprovedor;
    }

    public void setIdprovedor(int idprovedor) {
        this.idprovedor = idprovedor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
}
