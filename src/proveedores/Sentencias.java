/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proveedores;

import clientes.*;
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
    public static String LISTAR = "SELECT * FROM proveedores ORDER BY ape_proveedor";
    public static String LISTAR_AL = "SELECT * FROM proveedores WHERE dir_proveedor!='' ORDER BY ape_proveedor";
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
    public static String REGISTRAR = "INSERT INTO proveedores(ape_proveedor, ci_proveedor, dir_proveedor, email_proveedor, empresa_proveedor,nom_proveedor,ruc_proveedor,telf_proveedor) "
            + "VALUES(?,?,?,?,?,?,?,?)";

    /*
    
    `id_pro`, `cant_pro`, `cod_pro`, `des_pro`, `nom_pro`, `pcom_pro`, `pmayor_pro`, `poveedor_pro`, `pvp_pro`, `id_proveedor`
     */
    public static String ACTUALIZAR = "UPDATE proveedores SET "
            + "ape_proveedor=?, "
            + "ci_proveedor=?, "
            + "dir_proveedor=?, "
            + "email_proveedor=?, "
            + "empresa_proveedor=?, "
            + "nom_proveedor=?, "
            + "ruc_proveedor=?, "
            + "telf_proveedor=? "
            + "WHERE id_proveedor=?";

   

    public static String ELIMINAR = "DELETE FROM proveedores WHERE id_proveedor = ?";

    public static String ELIMINAR_TODO = "TRUNCATE TABLE clientes";
    private String ape;
    private String ci;
    private String dir;
    private String email;
    private String empresa;
    private String nom;

    private String ruc;

    private String telf;
    private int id_cli;

    private int id;

    public Sentencias(String ape, String ci, String dir, String email, String empresa, String nom, String ruc, String telf) {
        this.ape = ape;
        this.ci = ci;
        this.dir = dir;
        this.email = email;
        this.empresa = empresa;
        this.nom = nom;
        this.ruc = ruc;
        this.telf = telf;
    }

    public String getApe() {
        return ape;
    }

    public void setApe(String ape) {
        this.ape = ape;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
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

    

}
