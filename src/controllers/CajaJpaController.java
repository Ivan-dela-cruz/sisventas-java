/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import models.Usuario;
import models.Venta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import models.Caja;

/**
 *
 * @author USUARIO
 */
public class CajaJpaController implements Serializable {

    public CajaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("sisventasPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Caja caja) {
        if (caja.getVenta() == null) {
            caja.setVenta(new ArrayList<Venta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario user = caja.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getId());
                caja.setUser(user);
            }
            List<Venta> attachedVenta = new ArrayList<Venta>();
            for (Venta ventaVentaToAttach : caja.getVenta()) {
                ventaVentaToAttach = em.getReference(ventaVentaToAttach.getClass(), ventaVentaToAttach.getId());
                attachedVenta.add(ventaVentaToAttach);
            }
            caja.setVenta(attachedVenta);
            em.persist(caja);
            if (user != null) {
                Caja oldCajaOfUser = user.getCaja();
                if (oldCajaOfUser != null) {
                    oldCajaOfUser.setUser(null);
                    oldCajaOfUser = em.merge(oldCajaOfUser);
                }
                user.setCaja(caja);
                user = em.merge(user);
            }
            for (Venta ventaVenta : caja.getVenta()) {
                Caja oldCajaOfVentaVenta = ventaVenta.getCaja();
                ventaVenta.setCaja(caja);
                ventaVenta = em.merge(ventaVenta);
                if (oldCajaOfVentaVenta != null) {
                    oldCajaOfVentaVenta.getVenta().remove(ventaVenta);
                    oldCajaOfVentaVenta = em.merge(oldCajaOfVentaVenta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Caja caja) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Caja persistentCaja = em.find(Caja.class, caja.getId());
            Usuario userOld = persistentCaja.getUser();
            Usuario userNew = caja.getUser();
            List<Venta> ventaOld = persistentCaja.getVenta();
            List<Venta> ventaNew = caja.getVenta();
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getId());
                caja.setUser(userNew);
            }
            List<Venta> attachedVentaNew = new ArrayList<Venta>();
            for (Venta ventaNewVentaToAttach : ventaNew) {
                ventaNewVentaToAttach = em.getReference(ventaNewVentaToAttach.getClass(), ventaNewVentaToAttach.getId());
                attachedVentaNew.add(ventaNewVentaToAttach);
            }
            ventaNew = attachedVentaNew;
            caja.setVenta(ventaNew);
            caja = em.merge(caja);
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.setCaja(null);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                Caja oldCajaOfUser = userNew.getCaja();
                if (oldCajaOfUser != null) {
                    oldCajaOfUser.setUser(null);
                    oldCajaOfUser = em.merge(oldCajaOfUser);
                }
                userNew.setCaja(caja);
                userNew = em.merge(userNew);
            }
            for (Venta ventaOldVenta : ventaOld) {
                if (!ventaNew.contains(ventaOldVenta)) {
                    ventaOldVenta.setCaja(null);
                    ventaOldVenta = em.merge(ventaOldVenta);
                }
            }
            for (Venta ventaNewVenta : ventaNew) {
                if (!ventaOld.contains(ventaNewVenta)) {
                    Caja oldCajaOfVentaNewVenta = ventaNewVenta.getCaja();
                    ventaNewVenta.setCaja(caja);
                    ventaNewVenta = em.merge(ventaNewVenta);
                    if (oldCajaOfVentaNewVenta != null && !oldCajaOfVentaNewVenta.equals(caja)) {
                        oldCajaOfVentaNewVenta.getVenta().remove(ventaNewVenta);
                        oldCajaOfVentaNewVenta = em.merge(oldCajaOfVentaNewVenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = caja.getId();
                if (findCaja(id) == null) {
                    throw new NonexistentEntityException("The caja with id " + id + " no longer exists.");
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
            Caja caja;
            try {
                caja = em.getReference(Caja.class, id);
                caja.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The caja with id " + id + " no longer exists.", enfe);
            }
            Usuario user = caja.getUser();
            if (user != null) {
                user.setCaja(null);
                user = em.merge(user);
            }
            List<Venta> venta = caja.getVenta();
            for (Venta ventaVenta : venta) {
                ventaVenta.setCaja(null);
                ventaVenta = em.merge(ventaVenta);
            }
            em.remove(caja);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Caja> findCajaEntities() {
        return findCajaEntities(true, -1, -1);
    }

    public List<Caja> findCajaEntities(int maxResults, int firstResult) {
        return findCajaEntities(false, maxResults, firstResult);
    }

    private List<Caja> findCajaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Caja.class));
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

    public Caja findCaja(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Caja.class, id);
        } finally {
            em.close();
        }
    }

    public int getCajaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Caja> rt = cq.from(Caja.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
