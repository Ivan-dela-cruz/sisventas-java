/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "empresas")
public class Local implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_empresa")
    private int id;
    @Column(name = "nom_empresa")
    private String nombre;
    @Column(name = "provincia_empresa")
    private String provincia;
    @Column(name = "canton_empresa")
    private String canton;
    @Column(name = "parroquia_empresa")
    private String parroquia;
    @Column(name = "calle_empresa")
    private String calle;
    @Column(name = "telf_empresa")
    private String telefono;
    @Column(name = "ruc_empresa")
    private String ruc;

    //relacion muchos a uno 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_admin",referencedColumnName = "id_admin")
    private Administrador admin;

    public Local() {
    }

    public Local(String nombre, String provincia, String canton, String parroquia, String calle, String telefono, String ruc, Administrador admin) {
        this.nombre = nombre;
        this.provincia = provincia;
        this.canton = canton;
        this.parroquia = parroquia;
        this.calle = calle;
        this.telefono = telefono;
        this.ruc = ruc;
        this.admin = admin;
    }

    public Local(int id, String nombre, String provincia, String canton, String parroquia, String calle, String telefono, String ruc, Administrador admin) {
        this.id = id;
        this.nombre = nombre;
        this.provincia = provincia;
        this.canton = canton;
        this.parroquia = parroquia;
        this.calle = calle;
        this.telefono = telefono;
        this.ruc = ruc;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public String getParroquia() {
        return parroquia;
    }

    public void setParroquia(String parroquia) {
        this.parroquia = parroquia;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public Administrador getAdmin() {
        return admin;
    }

    public void setAdmin(Administrador admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "Local{" + "id=" + id + ", nombre=" + nombre + ", provincia=" + provincia + ", canton=" + canton + ", parroquia=" + parroquia + ", calle=" + calle + ", telefono=" + telefono + ", ruc=" + ruc + ", admin=" + admin + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.nombre);
        hash = 67 * hash + Objects.hashCode(this.provincia);
        hash = 67 * hash + Objects.hashCode(this.canton);
        hash = 67 * hash + Objects.hashCode(this.parroquia);
        hash = 67 * hash + Objects.hashCode(this.calle);
        hash = 67 * hash + Objects.hashCode(this.telefono);
        hash = 67 * hash + Objects.hashCode(this.ruc);
        hash = 67 * hash + Objects.hashCode(this.admin);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Local other = (Local) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.provincia, other.provincia)) {
            return false;
        }
        if (!Objects.equals(this.canton, other.canton)) {
            return false;
        }
        if (!Objects.equals(this.parroquia, other.parroquia)) {
            return false;
        }
        if (!Objects.equals(this.calle, other.calle)) {
            return false;
        }
        if (!Objects.equals(this.telefono, other.telefono)) {
            return false;
        }
        if (!Objects.equals(this.ruc, other.ruc)) {
            return false;
        }
        if (!Objects.equals(this.admin, other.admin)) {
            return false;
        }
        return true;
    }

}
