/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "facturas")
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;

    //datos factura
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_fact")
    private int id;
    @Column(name = "num_fact")
    private String numeroFactura;
    @Column(name = "fecha_fact")
    
    private String fechaFactura;
    @Column(name = "fechaPago_fact")
   
    private String fechaPago;
    //datos transacciones
    @Column(name = "pago_fact")
    private float pagoCliente;
    @Column(name = "cambio_fact")
    private float cambioCliente;
    @Column(name = "total_fact")
    private float totalCompra;
    @Column(name = "subtotal_fact")
    private float subtotal;
    @Column(name = "iva_fact")
    private float iva;

    //relacion detalle factura
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_detalle_fact")
    private DetalleFactura detalle_factura;

    //relacion 1 a 1 factura
    @OneToOne(mappedBy = "factura")
    private Venta venta;

    //relacion muchos a uno cliente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    public Factura() {
    }

    public Factura(int id, String numeroFactura, String fechaFactura, String fechaPago, float pagoCliente, float cambioCliente, float totalCompra, float subtotal, float iva, DetalleFactura detalle_factura, Venta venta, Cliente cliente) {
        this.id = id;
        this.numeroFactura = numeroFactura;
        this.fechaFactura = fechaFactura;
        this.fechaPago = fechaPago;
        this.pagoCliente = pagoCliente;
        this.cambioCliente = cambioCliente;
        this.totalCompra = totalCompra;
        this.subtotal = subtotal;
        this.iva = iva;
        this.detalle_factura = detalle_factura;
        this.venta = venta;
        this.cliente = cliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public float getPagoCliente() {
        return pagoCliente;
    }

    public void setPagoCliente(float pagoCliente) {
        this.pagoCliente = pagoCliente;
    }

    public float getCambioCliente() {
        return cambioCliente;
    }

    public void setCambioCliente(float cambioCliente) {
        this.cambioCliente = cambioCliente;
    }

    public float getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(float totalCompra) {
        this.totalCompra = totalCompra;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public float getIva() {
        return iva;
    }

    public void setIva(float iva) {
        this.iva = iva;
    }

    public DetalleFactura getDetalle_factura() {
        return detalle_factura;
    }

    public void setDetalle_factura(DetalleFactura detalle_factura) {
        this.detalle_factura = detalle_factura;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Factura{" + "id=" + id + ", numeroFactura=" + numeroFactura + ", fechaFactura=" + fechaFactura + ", fechaPago=" + fechaPago + ", pagoCliente=" + pagoCliente + ", cambioCliente=" + cambioCliente + ", totalCompra=" + totalCompra + ", subtotal=" + subtotal + ", iva=" + iva + ", detalle_factura=" + detalle_factura + ", venta=" + venta + ", cliente=" + cliente + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        hash = 97 * hash + Objects.hashCode(this.numeroFactura);
        hash = 97 * hash + Objects.hashCode(this.fechaFactura);
        hash = 97 * hash + Objects.hashCode(this.fechaPago);
        hash = 97 * hash + Float.floatToIntBits(this.pagoCliente);
        hash = 97 * hash + Float.floatToIntBits(this.cambioCliente);
        hash = 97 * hash + Float.floatToIntBits(this.totalCompra);
        hash = 97 * hash + Float.floatToIntBits(this.subtotal);
        hash = 97 * hash + Float.floatToIntBits(this.iva);
        hash = 97 * hash + Objects.hashCode(this.detalle_factura);
        hash = 97 * hash + Objects.hashCode(this.venta);
        hash = 97 * hash + Objects.hashCode(this.cliente);
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
        final Factura other = (Factura) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Float.floatToIntBits(this.pagoCliente) != Float.floatToIntBits(other.pagoCliente)) {
            return false;
        }
        if (Float.floatToIntBits(this.cambioCliente) != Float.floatToIntBits(other.cambioCliente)) {
            return false;
        }
        if (Float.floatToIntBits(this.totalCompra) != Float.floatToIntBits(other.totalCompra)) {
            return false;
        }
        if (Float.floatToIntBits(this.subtotal) != Float.floatToIntBits(other.subtotal)) {
            return false;
        }
        if (Float.floatToIntBits(this.iva) != Float.floatToIntBits(other.iva)) {
            return false;
        }
        if (!Objects.equals(this.numeroFactura, other.numeroFactura)) {
            return false;
        }
        if (!Objects.equals(this.fechaFactura, other.fechaFactura)) {
            return false;
        }
        if (!Objects.equals(this.fechaPago, other.fechaPago)) {
            return false;
        }
        if (!Objects.equals(this.detalle_factura, other.detalle_factura)) {
            return false;
        }
        if (!Objects.equals(this.venta, other.venta)) {
            return false;
        }
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        return true;
    }

    
}
