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
import models.Producto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import models.Proveedor;

/**
 *
 * @author ivan
 */
public class ProveedorJpaController implements Serializable {

    public ProveedorJpaController() {
        this.emf = Persistence.createEntityManagerFactory("sisventasPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proveedor proveedor) {
        if (proveedor.getProducto() == null) {
            proveedor.setProducto(new ArrayList<Producto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Producto> attachedProducto = new ArrayList<Producto>();
            for (Producto productoProductoToAttach : proveedor.getProducto()) {
                productoProductoToAttach = em.getReference(productoProductoToAttach.getClass(), productoProductoToAttach.getId());
                attachedProducto.add(productoProductoToAttach);
            }
            proveedor.setProducto(attachedProducto);
            em.persist(proveedor);
            for (Producto productoProducto : proveedor.getProducto()) {
                Proveedor oldProveedorOfProductoProducto = productoProducto.getProveedor();
                productoProducto.setProveedor(proveedor);
                productoProducto = em.merge(productoProducto);
                if (oldProveedorOfProductoProducto != null) {
                    oldProveedorOfProductoProducto.getProducto().remove(productoProducto);
                    oldProveedorOfProductoProducto = em.merge(oldProveedorOfProductoProducto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proveedor proveedor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor persistentProveedor = em.find(Proveedor.class, proveedor.getId());
            List<Producto> productoOld = persistentProveedor.getProducto();
            List<Producto> productoNew = proveedor.getProducto();
            List<Producto> attachedProductoNew = new ArrayList<Producto>();
            for (Producto productoNewProductoToAttach : productoNew) {
                productoNewProductoToAttach = em.getReference(productoNewProductoToAttach.getClass(), productoNewProductoToAttach.getId());
                attachedProductoNew.add(productoNewProductoToAttach);
            }
            productoNew = attachedProductoNew;
            proveedor.setProducto(productoNew);
            proveedor = em.merge(proveedor);
            for (Producto productoOldProducto : productoOld) {
                if (!productoNew.contains(productoOldProducto)) {
                    productoOldProducto.setProveedor(null);
                    productoOldProducto = em.merge(productoOldProducto);
                }
            }
            for (Producto productoNewProducto : productoNew) {
                if (!productoOld.contains(productoNewProducto)) {
                    Proveedor oldProveedorOfProductoNewProducto = productoNewProducto.getProveedor();
                    productoNewProducto.setProveedor(proveedor);
                    productoNewProducto = em.merge(productoNewProducto);
                    if (oldProveedorOfProductoNewProducto != null && !oldProveedorOfProductoNewProducto.equals(proveedor)) {
                        oldProveedorOfProductoNewProducto.getProducto().remove(productoNewProducto);
                        oldProveedorOfProductoNewProducto = em.merge(oldProveedorOfProductoNewProducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = proveedor.getId();
                if (findProveedor(id) == null) {
                    throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.");
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
            Proveedor proveedor;
            try {
                proveedor = em.getReference(Proveedor.class, id);
                proveedor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.", enfe);
            }
            List<Producto> producto = proveedor.getProducto();
            for (Producto productoProducto : producto) {
                productoProducto.setProveedor(null);
                productoProducto = em.merge(productoProducto);
            }
            em.remove(proveedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proveedor> findProveedorEntities() {
        return findProveedorEntities(true, -1, -1);
    }

    public List<Proveedor> findProveedorEntities(int maxResults, int firstResult) {
        return findProveedorEntities(false, maxResults, firstResult);
    }

    private List<Proveedor> findProveedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proveedor.class));
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

    public Proveedor findProveedor(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proveedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getProveedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proveedor> rt = cq.from(Proveedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
