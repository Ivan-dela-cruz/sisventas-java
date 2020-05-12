/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientes;

import producto.*;

/**
 *
 * @author Rojeru San CL
 */
public class Sentencias {

    /*
	
id_cli
ape_cli
ci_cli
dir_cli
nom_cli
ruc_cli
telf_cli*/
    public static String LISTAR = "SELECT * FROM clientes ORDER BY ape_cli";
    public static String LISTAR_AL = "SELECT * FROM clientes WHERE dir_cli!='' ORDER BY ape_cli";

    public static String REGISTRAR = "INSERT INTO clientes(id_cli, ape_cli, ci_cli, dir_cli, nom_cli,ruc_cli,telf_cli) "
            + "VALUES(?,?,?,?,?,?,?)";

    /*
    
    `id_pro`, `cant_pro`, `cod_pro`, `des_pro`, `nom_pro`, `pcom_pro`, `pmayor_pro`, `poveedor_pro`, `pvp_pro`, `id_proveedor`
     */
    public static String ACTUALIZAR = "UPDATE clientes SET "
            + "ape_cli=?, "
            + "ci_cli=?, "
            + "dir_cli=?, "
            + "nom_cli=?, "
            + "ruc_cli=?, "
            + "telf_cli=? "
            +"WHERE id_cli=?";

    public static String ACTUALIZAR_STOCK = "UPDATE productos SET "
            + "cant_pro=? "
            + "WHERE id_pro=?";

    public static String ELIMINAR = "DELETE FROM clientes WHERE id_cli = ?";

    public static String ELIMINAR_TODO = "TRUNCATE TABLE clientes";

    private String ci;
    private String nom;
    private String ruc;
    private String dir;
    private String ape;
    private String telf;
    private int id_cli;

    private int id;

    public Sentencias() {
    }

    public Sentencias(String ci, String nom, String ruc, String dir, String ape, String telf) {
        this.ci = ci;
        this.nom = nom;
        this.ruc = ruc;
        this.dir = dir;
        this.ape = ape;
        this.telf = telf;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getApe() {
        return ape;
    }

    public void setApe(String ape) {
        this.ape = ape;
    }

    public String getTelf() {
        return telf;
    }

    public void setTelf(String telf) {
        this.telf = telf;
    }

    public int getId_cli() {
        return id_cli;
    }

    public void setId_cli(int id_cli) {
        this.id_cli = id_cli;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Sentencias{" + "ci=" + ci + ", nom=" + nom + ", ruc=" + ruc + ", dir=" + dir + ", ape=" + ape + ", telf=" + telf + ", id_cli=" + id_cli + ", id=" + id + '}';
    }

}
