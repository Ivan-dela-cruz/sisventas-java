/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import models.DetalleFactura;
import models.Venta;
import models.Cliente;
import models.Factura;

/**
 *
 * @author USUARIO
 */
public class FacturaJpaController implements Serializable {

    public FacturaJpaController() {
       this.emf = Persistence.createEntityManagerFactory("sisventasPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Factura factura) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleFactura detalle_factura = factura.getDetalle_factura();
            if (detalle_factura != null) {
                detalle_factura = em.getReference(detalle_factura.getClass(), detalle_factura.getId());
                factura.setDetalle_factura(detalle_factura);
            }
            Venta venta = factura.getVenta();
            if (venta != null) {
                venta = em.getReference(venta.getClass(), venta.getId());
                factura.setVenta(venta);
            }
            Cliente cliente = factura.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getId());
                factura.setCliente(cliente);
            }
            em.persist(factura);
            if (detalle_factura != null) {
                Factura oldFacturaOfDetalle_factura = detalle_factura.getFactura();
                if (oldFacturaOfDetalle_factura != null) {
                    oldFacturaOfDetalle_factura.setDetalle_factura(null);
                    oldFacturaOfDetalle_factura = em.merge(oldFacturaOfDetalle_factura);
                }
                detalle_factura.setFactura(factura);
                detalle_factura = em.merge(detalle_factura);
            }
            if (venta != null) {
                Factura oldFacturaOfVenta = venta.getFactura();
                if (oldFacturaOfVenta != null) {
                    oldFacturaOfVenta.setVenta(null);
                    oldFacturaOfVenta = em.merge(oldFacturaOfVenta);
                }
                venta.setFactura(factura);
                venta = em.merge(venta);
            }
            if (cliente != null) {
                cliente.getFactura().add(factura);
                cliente = em.merge(cliente);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Factura factura) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Factura persistentFactura = em.find(Factura.class, factura.getId());
            DetalleFactura detalle_facturaOld = persistentFactura.getDetalle_factura();
            DetalleFactura detalle_facturaNew = factura.getDetalle_factura();
            Venta ventaOld = persistentFactura.getVenta();
            Venta ventaNew = factura.getVenta();
            Cliente clienteOld = persistentFactura.getCliente();
            Cliente clienteNew = factura.getCliente();
            if (detalle_facturaNew != null) {
                detalle_facturaNew = em.getReference(detalle_facturaNew.getClass(), detalle_facturaNew.getId());
                factura.setDetalle_factura(detalle_facturaNew);
            }
            if (ventaNew != null) {
                ventaNew = em.getReference(ventaNew.getClass(), ventaNew.getId());
                factura.setVenta(ventaNew);
            }
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getId());
                factura.setCliente(clienteNew);
            }
            factura = em.merge(factura);
            if (detalle_facturaOld != null && !detalle_facturaOld.equals(detalle_facturaNew)) {
                detalle_facturaOld.setFactura(null);
                detalle_facturaOld = em.merge(detalle_facturaOld);
            }
            if (detalle_facturaNew != null && !detalle_facturaNew.equals(detalle_facturaOld)) {
                Factura oldFacturaOfDetalle_factura = detalle_facturaNew.getFactura();
                if (oldFacturaOfDetalle_factura != null) {
                    oldFacturaOfDetalle_factura.setDetalle_factura(null);
                    oldFacturaOfDetalle_factura = em.merge(oldFacturaOfDetalle_factura);
                }
                detalle_facturaNew.setFactura(factura);
                detalle_facturaNew = em.merge(detalle_facturaNew);
            }
            if (ventaOld != null && !ventaOld.equals(ventaNew)) {
                ventaOld.setFactura(null);
                ventaOld = em.merge(ventaOld);
            }
            if (ventaNew != null && !ventaNew.equals(ventaOld)) {
                Factura oldFacturaOfVenta = ventaNew.getFactura();
                if (oldFacturaOfVenta != null) {
                    oldFacturaOfVenta.setVenta(null);
                    oldFacturaOfVenta = em.merge(oldFacturaOfVenta);
                }
                ventaNew.setFactura(factura);
                ventaNew = em.merge(ventaNew);
            }
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getFactura().remove(factura);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getFactura().add(factura);
                clienteNew = em.merge(clienteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = factura.getId();
                if (findFactura(id) == null) {
                    throw new NonexistentEntityException("The factura with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Factura factura;
            try {
                factura = em.getReference(Factura.class, id);
                factura.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The factura with id " + id + " no longer exists.", enfe);
            }
            DetalleFactura detalle_factura = factura.getDetalle_factura();
            if (detalle_factura != null) {
                detalle_factura.setFactura(null);
                detalle_factura = em.merge(detalle_factura);
            }
            Venta venta = factura.getVenta();
            if (venta != null) {
                venta.setFactura(null);
                venta = em.merge(venta);
            }
            Cliente cliente = factura.getCliente();
            if (cliente != null) {
                cliente.getFactura().remove(factura);
                cliente = em.merge(cliente);
            }
            em.remove(factura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Factura> findFacturaEntities() {
        return findFacturaEntities(true, -1, -1);
    }

    public List<Factura> findFacturaEntities(int maxResults, int firstResult) {
        return findFacturaEntities(false, maxResults, firstResult);
    }

    private List<Factura> findFacturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Factura.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Factura findFactura(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Factura.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Factura> rt = cq.from(Factura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
