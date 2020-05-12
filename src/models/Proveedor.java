/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "proveedores")
public class Proveedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_proveedor")
    private int id;
    @Column(name = "ruc_proveedor")
    private String rucProveedor;
    @Column(name = "ci_proveedor")
    private String ciProveedor;
    @Column(name = "nom_proveedor")
    private String nomProveedor;
    @Column(name = "ape_proveedor")
    private String apeProveedor;
    @Column(name = "empresa_proveedor")
    private String empresaProveedor;
    @Column(name = "dir_proveedor")
    private String dirProveedor;
    @Column(name = "telf_proveedor")
    private String telefono;
    @Column(name = "email_proveedor")
    private String email;

    //relacion uno a varios entidades productos
    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL)
    private List<Producto> producto = new ArrayList<>();

    public Proveedor() {
    }

    public Proveedor(int id, String rucProveedor, String ciProveedor, String nomProveedor, String apeProveedor, String empresaProveedor, String dirProveedor, String telefono, String email) {
        this.id = id;
        this.rucProveedor = rucProveedor;
        this.ciProveedor = ciProveedor;
        this.nomProveedor = nomProveedor;
        this.apeProveedor = apeProveedor;
        this.empresaProveedor = empresaProveedor;
        this.dirProveedor = dirProveedor;
        this.telefono = telefono;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRucProveedor() {
        return rucProveedor;
    }

    public void setRucProveedor(String rucProveedor) {
        this.rucProveedor = rucProveedor;
    }

    public String getCiProveedor() {
        return ciProveedor;
    }

    public void setCiProveedor(String ciProveedor) {
        this.ciProveedor = ciProveedor;
    }

    public String getNomProveedor() {
        return nomProveedor;
    }

    public void setNomProveedor(String nomProveedor) {
        this.nomProveedor = nomProveedor;
    }

    public String getApeProveedor() {
        return apeProveedor;
    }

    public void setApeProveedor(String apeProveedor) {
        this.apeProveedor = apeProveedor;
    }

    public String getEmpresaProveedor() {
        return empresaProveedor;
    }

    public void setEmpresaProveedor(String empresaProveedor) {
        this.empresaProveedor = empresaProveedor;
    }

    public String getDirProveedor() {
        return dirProveedor;
    }

    public void setDirProveedor(String dirProveedor) {
        this.dirProveedor = dirProveedor;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Producto> getProducto() {
        return producto;
    }

    public void setProducto(List<Producto> producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "Proveedor{" + "id=" + id + ", rucProveedor=" + rucProveedor + ", ciProveedor=" + ciProveedor + ", nomProveedor=" + nomProveedor + ", apeProveedor=" + apeProveedor + ", empresaProveedor=" + empresaProveedor + ", dirProveedor=" + dirProveedor + ", telefono=" + telefono + ", email=" + email + ", producto=" + producto + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.id;
        hash = 53 * hash + Objects.hashCode(this.rucProveedor);
        hash = 53 * hash + Objects.hashCode(this.ciProveedor);
        hash = 53 * hash + Objects.hashCode(this.nomProveedor);
        hash = 53 * hash + Objects.hashCode(this.apeProveedor);
        hash = 53 * hash + Objects.hashCode(this.empresaProveedor);
        hash = 53 * hash + Objects.hashCode(this.dirProveedor);
        hash = 53 * hash + Objects.hashCode(this.telefono);
        hash = 53 * hash + Objects.hashCode(this.email);
        hash = 53 * hash + Objects.hashCode(this.producto);
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
        final Proveedor other = (Proveedor) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.rucProveedor, other.rucProveedor)) {
            return false;
        }
        if (!Objects.equals(this.ciProveedor, other.ciProveedor)) {
            return false;
        }
        if (!Objects.equals(this.nomProveedor, other.nomProveedor)) {
            return false;
        }
        if (!Objects.equals(this.apeProveedor, other.apeProveedor)) {
            return false;
        }
        if (!Objects.equals(this.empresaProveedor, other.empresaProveedor)) {
            return false;
        }
        if (!Objects.equals(this.dirProveedor, other.dirProveedor)) {
            return false;
        }
        if (!Objects.equals(this.telefono, other.telefono)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.producto, other.producto)) {
            return false;
        }
        return true;
    }

}
