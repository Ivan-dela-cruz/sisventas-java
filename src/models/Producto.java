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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "productos")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_pro")
    private int id;
    @Column(name = "cod_pro")
    private String codigo;
    @Column(name = "nom_pro")
    private String nombre;
    @Column(name = "des_pro")
    private String descripcion;
    @Column(name = "cant_pro")
    private int cantidad;
    @Column(name = "pvp_pro")
    private double pvp;
    @Column(name = "pcom_pro")
    private double pcom;
    @Column(name = "pmayor_pro")
    private double pmayor;
    @Column(name = "poveedor_pro")
    private double poveedor;

    //relacion uno a varios entidades productos
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<DetalleFactura> dtfactura = new ArrayList<>();

    //relacion  varios a uno entidades proveedores
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor")
    private Proveedor proveedor;

    public Producto() {
    }

    public Producto(int id, String codigo, String nombre, String descripcion, int cantidad, double pvp, double pcom, double pmayor, double poveedor, Proveedor proveedor) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.pvp = pvp;
        this.pcom = pcom;
        this.pmayor = pmayor;
        this.poveedor = poveedor;
        this.proveedor = proveedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPvp() {
        return pvp;
    }

    public void setPvp(double pvp) {
        this.pvp = pvp;
    }

    public double getPcom() {
        return pcom;
    }

    public void setPcom(double pcom) {
        this.pcom = pcom;
    }

    public double getPmayor() {
        return pmayor;
    }

    public void setPmayor(double pmayor) {
        this.pmayor = pmayor;
    }

    public double getPoveedor() {
        return poveedor;
    }

    public void setPoveedor(double poveedor) {
        this.poveedor = poveedor;
    }

    public List<DetalleFactura> getDtfactura() {
        return dtfactura;
    }

    public void setDtfactura(List<DetalleFactura> dtfactura) {
        this.dtfactura = dtfactura;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    @Override
    public String toString() {
        return "Producto{" + "id=" + id + ", codigo=" + codigo + ", nombre=" + nombre + ", descripcion=" + descripcion + ", cantidad=" + cantidad + ", pvp=" + pvp + ", pcom=" + pcom + ", pmayor=" + pmayor + ", poveedor=" + poveedor + ", dtfactura=" + dtfactura + ", proveedor=" + proveedor + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + this.id;
        hash = 19 * hash + Objects.hashCode(this.codigo);
        hash = 19 * hash + Objects.hashCode(this.nombre);
        hash = 19 * hash + Objects.hashCode(this.descripcion);
        hash = 19 * hash + this.cantidad;
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.pvp) ^ (Double.doubleToLongBits(this.pvp) >>> 32));
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.pcom) ^ (Double.doubleToLongBits(this.pcom) >>> 32));
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.pmayor) ^ (Double.doubleToLongBits(this.pmayor) >>> 32));
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.poveedor) ^ (Double.doubleToLongBits(this.poveedor) >>> 32));
        hash = 19 * hash + Objects.hashCode(this.dtfactura);
        hash = 19 * hash + Objects.hashCode(this.proveedor);
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
        final Producto other = (Producto) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.cantidad != other.cantidad) {
            return false;
        }
        if (Double.doubleToLongBits(this.pvp) != Double.doubleToLongBits(other.pvp)) {
            return false;
        }
        if (Double.doubleToLongBits(this.pcom) != Double.doubleToLongBits(other.pcom)) {
            return false;
        }
        if (Double.doubleToLongBits(this.pmayor) != Double.doubleToLongBits(other.pmayor)) {
            return false;
        }
        if (Double.doubleToLongBits(this.poveedor) != Double.doubleToLongBits(other.poveedor)) {
            return false;
        }
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.dtfactura, other.dtfactura)) {
            return false;
        }
        if (!Objects.equals(this.proveedor, other.proveedor)) {
            return false;
        }
        return true;
    }

}
