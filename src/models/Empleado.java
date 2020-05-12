/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "empleados")
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "ci_emp", length = 10)
    private String ci;
    @Column(name = "nom_emp")
    private String nombres;
    @Column(name = "ape_emp")
    private String apellidos;
    @Column(name = "edad_emp")
    private int edad;
    @Column(name = "fechaIngreso_emp")
    private String fecha_ingreso;
    @Column(name = "fechaCobro_emp")
    private String fecha_cobro;
    @Column(name = "sueldo_emp")
    private float sueldo;
    @Column(name = "adelanto_emp")
    private float adelanto;
    @Column(name = "descuento_emp")
    private float descuento;
    @OneToOne(mappedBy = "empleado")
    private Usuario user;

    public Empleado() {
    }

    public Empleado(int id, String ci, String nombres, String apellidos, int edad, String fecha_ingreso, String fecha_cobro, float sueldo, float adelanto, float descuento, Usuario user) {
        this.id = id;
        this.ci = ci;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.edad = edad;
        this.fecha_ingreso = fecha_ingreso;
        this.fecha_cobro = fecha_cobro;
        this.sueldo = sueldo;
        this.adelanto = adelanto;
        this.descuento = descuento;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Empleado{" + "id=" + id + ", ci=" + ci + ", nombres=" + nombres + ", apellidos=" + apellidos + ", edad=" + edad + ", fecha_ingreso=" + fecha_ingreso + ", fecha_cobro=" + fecha_cobro + ", sueldo=" + sueldo + ", adelanto=" + adelanto + ", descuento=" + descuento + ", user=" + user + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.id;
        hash = 37 * hash + Objects.hashCode(this.ci);
        hash = 37 * hash + Objects.hashCode(this.nombres);
        hash = 37 * hash + Objects.hashCode(this.apellidos);
        hash = 37 * hash + this.edad;
        hash = 37 * hash + Objects.hashCode(this.fecha_ingreso);
        hash = 37 * hash + Objects.hashCode(this.fecha_cobro);
        hash = 37 * hash + Float.floatToIntBits(this.sueldo);
        hash = 37 * hash + Float.floatToIntBits(this.adelanto);
        hash = 37 * hash + Float.floatToIntBits(this.descuento);
        hash = 37 * hash + Objects.hashCode(this.user);
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
        final Empleado other = (Empleado) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.edad != other.edad) {
            return false;
        }
        if (Float.floatToIntBits(this.sueldo) != Float.floatToIntBits(other.sueldo)) {
            return false;
        }
        if (Float.floatToIntBits(this.adelanto) != Float.floatToIntBits(other.adelanto)) {
            return false;
        }
        if (Float.floatToIntBits(this.descuento) != Float.floatToIntBits(other.descuento)) {
            return false;
        }
        if (!Objects.equals(this.ci, other.ci)) {
            return false;
        }
        if (!Objects.equals(this.nombres, other.nombres)) {
            return false;
        }
        if (!Objects.equals(this.apellidos, other.apellidos)) {
            return false;
        }
        if (!Objects.equals(this.fecha_ingreso, other.fecha_ingreso)) {
            return false;
        }
        if (!Objects.equals(this.fecha_cobro, other.fecha_cobro)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(String fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public String getFecha_cobro() {
        return fecha_cobro;
    }

    public void setFecha_cobro(String fecha_cobro) {
        this.fecha_cobro = fecha_cobro;
    }

    public float getSueldo() {
        return sueldo;
    }

    public void setSueldo(float sueldo) {
        this.sueldo = sueldo;
    }

    public float getAdelanto() {
        return adelanto;
    }

    public void setAdelanto(float adelanto) {
        this.adelanto = adelanto;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    

}
