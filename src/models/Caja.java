/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "cajas")
public class Caja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_caja")
    private int id;
    @Column(name = "ciEmple_caja")
    private String idEmpleado;
    @Column(name = "valorIni_caja")
    private float valorInicial;
    @Column(name = "valoFin_caja")
    private float valorFinal;
    @Column(name = "fecha_caja")
    private String fecha;

    //relacion 1 a 1 usuarios
    @OneToOne(mappedBy = "caja")
    private Usuario user;

    //relacion varios a uno ventas
    @OneToMany(mappedBy = "caja", cascade = CascadeType.ALL)
    private List<Venta> venta = new ArrayList<>();

    public Caja() {
    }

    public Caja(int id, String idEmpleado, float valorInicial, float valorFinal, String fecha, Usuario user) {
        this.id = id;
        this.idEmpleado = idEmpleado;
        this.valorInicial = valorInicial;
        this.valorFinal = valorFinal;
        this.fecha = fecha;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public float getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(float valorInicial) {
        this.valorInicial = valorInicial;
    }

    public float getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(float valorFinal) {
        this.valorFinal = valorFinal;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public List<Venta> getVenta() {
        return venta;
    }

    public void setVenta(List<Venta> venta) {
        this.venta = venta;
    }

    @Override
    public String toString() {
        return "Caja{" + "id=" + id + ", idEmpleado=" + idEmpleado + ", valorInicial=" + valorInicial + ", valorFinal=" + valorFinal + ", fecha=" + fecha + ", user=" + user + ", venta=" + venta + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Caja other = (Caja) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Float.floatToIntBits(this.valorInicial) != Float.floatToIntBits(other.valorInicial)) {
            return false;
        }
        if (Float.floatToIntBits(this.valorFinal) != Float.floatToIntBits(other.valorFinal)) {
            return false;
        }
        if (!Objects.equals(this.idEmpleado, other.idEmpleado)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.venta, other.venta)) {
            return false;
        }
        return true;
    }

}
