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
import models.Caja;
import models.Factura;
import models.Venta;

/**
 *
 * @author USUARIO
 */
public class VentaJpaController implements Serializable {

    public VentaJpaController() {
       this.emf = Persistence.createEntityManagerFactory("sisventasPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Venta venta) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Caja caja = venta.getCaja();
            if (caja != null) {
                caja = em.getReference(caja.getClass(), caja.getId());
                venta.setCaja(caja);
            }
            Factura factura = venta.getFactura();
            if (factura != null) {
                factura = em.getReference(factura.getClass(), factura.getId());
                venta.setFactura(factura);
            }
            em.persist(venta);
            if (caja != null) {
                caja.getVenta().add(venta);
                caja = em.merge(caja);
            }
            if (factura != null) {
                Venta oldVentaOfFactura = factura.getVenta();
                if (oldVentaOfFactura != null) {
                    oldVentaOfFactura.setFactura(null);
                    oldVentaOfFactura = em.merge(oldVentaOfFactura);
                }
                factura.setVenta(venta);
                factura = em.merge(factura);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Venta venta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venta persistentVenta = em.find(Venta.class, venta.getId());
            Caja cajaOld = persistentVenta.getCaja();
            Caja cajaNew = venta.getCaja();
            Factura facturaOld = persistentVenta.getFactura();
            Factura facturaNew = venta.getFactura();
            if (cajaNew != null) {
                cajaNew = em.getReference(cajaNew.getClass(), cajaNew.getId());
                venta.setCaja(cajaNew);
            }
            if (facturaNew != null) {
                facturaNew = em.getReference(facturaNew.getClass(), facturaNew.getId());
                venta.setFactura(facturaNew);
            }
            venta = em.merge(venta);
            if (cajaOld != null && !cajaOld.equals(cajaNew)) {
                cajaOld.getVenta().remove(venta);
                cajaOld = em.merge(cajaOld);
            }
            if (cajaNew != null && !cajaNew.equals(cajaOld)) {
                cajaNew.getVenta().add(venta);
                cajaNew = em.merge(cajaNew);
            }
            if (facturaOld != null && !facturaOld.equals(facturaNew)) {
                facturaOld.setVenta(null);
                facturaOld = em.merge(facturaOld);
            }
            if (facturaNew != null && !facturaNew.equals(facturaOld)) {
                Venta oldVentaOfFactura = facturaNew.getVenta();
                if (oldVentaOfFactura != null) {
                    oldVentaOfFactura.setFactura(null);
                    oldVentaOfFactura = em.merge(oldVentaOfFactura);
                }
                facturaNew.setVenta(venta);
                facturaNew = em.merge(facturaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = venta.getId();
                if (findVenta(id) == null) {
                    throw new NonexistentEntityException("The venta with id " + id + " no longer exists.");
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
            Venta venta;
            try {
                venta = em.getReference(Venta.class, id);
                venta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The venta with id " + id + " no longer exists.", enfe);
            }
            Caja caja = venta.getCaja();
            if (caja != null) {
                caja.getVenta().remove(venta);
                caja = em.merge(caja);
            }
            Factura factura = venta.getFactura();
            if (factura != null) {
                factura.setVenta(null);
                factura = em.merge(factura);
            }
            em.remove(venta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Venta> findVentaEntities() {
        return findVentaEntities(true, -1, -1);
    }

    public List<Venta> findVentaEntities(int maxResults, int firstResult) {
        return findVentaEntities(false, maxResults, firstResult);
    }

    private List<Venta> findVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Venta.class));
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

    public Venta findVenta(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Venta.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Venta> rt = cq.from(Venta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
