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
import models.Producto;
import models.Factura;

/**
 *
 * @author ivan
 */
public class DetalleFacturaJpaController implements Serializable {

    public DetalleFacturaJpaController() {
       this.emf = Persistence.createEntityManagerFactory("sisventasPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleFactura detalleFactura) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto producto = detalleFactura.getProducto();
            if (producto != null) {
                producto = em.getReference(producto.getClass(), producto.getId());
                detalleFactura.setProducto(producto);
            }
            Factura factura = detalleFactura.getFactura();
            if (factura != null) {
                factura = em.getReference(factura.getClass(), factura.getId());
                detalleFactura.setFactura(factura);
            }
            em.persist(detalleFactura);
            if (producto != null) {
                producto.getDtfactura().add(detalleFactura);
                producto = em.merge(producto);
            }
            if (factura != null) {
                DetalleFactura oldDetalle_facturaOfFactura = factura.getDetalle_factura();
                if (oldDetalle_facturaOfFactura != null) {
                    oldDetalle_facturaOfFactura.setFactura(null);
                    oldDetalle_facturaOfFactura = em.merge(oldDetalle_facturaOfFactura);
                }
                factura.setDetalle_factura(detalleFactura);
                factura = em.merge(factura);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleFactura detalleFactura) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleFactura persistentDetalleFactura = em.find(DetalleFactura.class, detalleFactura.getId());
            Producto productoOld = persistentDetalleFactura.getProducto();
            Producto productoNew = detalleFactura.getProducto();
            Factura facturaOld = persistentDetalleFactura.getFactura();
            Factura facturaNew = detalleFactura.getFactura();
            if (productoNew != null) {
                productoNew = em.getReference(productoNew.getClass(), productoNew.getId());
                detalleFactura.setProducto(productoNew);
            }
            if (facturaNew != null) {
                facturaNew = em.getReference(facturaNew.getClass(), facturaNew.getId());
                detalleFactura.setFactura(facturaNew);
            }
            detalleFactura = em.merge(detalleFactura);
            if (productoOld != null && !productoOld.equals(productoNew)) {
                productoOld.getDtfactura().remove(detalleFactura);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getDtfactura().add(detalleFactura);
                productoNew = em.merge(productoNew);
            }
            if (facturaOld != null && !facturaOld.equals(facturaNew)) {
                facturaOld.setDetalle_factura(null);
                facturaOld = em.merge(facturaOld);
            }
            if (facturaNew != null && !facturaNew.equals(facturaOld)) {
                DetalleFactura oldDetalle_facturaOfFactura = facturaNew.getDetalle_factura();
                if (oldDetalle_facturaOfFactura != null) {
                    oldDetalle_facturaOfFactura.setFactura(null);
                    oldDetalle_facturaOfFactura = em.merge(oldDetalle_facturaOfFactura);
                }
                facturaNew.setDetalle_factura(detalleFactura);
                facturaNew = em.merge(facturaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = detalleFactura.getId();
                if (findDetalleFactura(id) == null) {
                    throw new NonexistentEntityException("The detalleFactura with id " + id + " no longer exists.");
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
            DetalleFactura detalleFactura;
            try {
                detalleFactura = em.getReference(DetalleFactura.class, id);
                detalleFactura.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleFactura with id " + id + " no longer exists.", enfe);
            }
            Producto producto = detalleFactura.getProducto();
            if (producto != null) {
                producto.getDtfactura().remove(detalleFactura);
                producto = em.merge(producto);
            }
            Factura factura = detalleFactura.getFactura();
            if (factura != null) {
                factura.setDetalle_factura(null);
                factura = em.merge(factura);
            }
            em.remove(detalleFactura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleFactura> findDetalleFacturaEntities() {
        return findDetalleFacturaEntities(true, -1, -1);
    }

    public List<DetalleFactura> findDetalleFacturaEntities(int maxResults, int firstResult) {
        return findDetalleFacturaEntities(false, maxResults, firstResult);
    }

    private List<DetalleFactura> findDetalleFacturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleFactura.class));
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

    public DetalleFactura findDetalleFactura(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleFactura.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleFacturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleFactura> rt = cq.from(DetalleFactura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
