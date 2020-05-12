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
import models.Proveedor;
import models.DetalleFactura;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import models.Producto;

/**
 *
 * @author ivan
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("sisventasPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        if (producto.getDtfactura() == null) {
            producto.setDtfactura(new ArrayList<DetalleFactura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor proveedor = producto.getProveedor();
            if (proveedor != null) {
                proveedor = em.getReference(proveedor.getClass(), proveedor.getId());
                producto.setProveedor(proveedor);
            }
            List<DetalleFactura> attachedDtfactura = new ArrayList<DetalleFactura>();
            for (DetalleFactura dtfacturaDetalleFacturaToAttach : producto.getDtfactura()) {
                dtfacturaDetalleFacturaToAttach = em.getReference(dtfacturaDetalleFacturaToAttach.getClass(), dtfacturaDetalleFacturaToAttach.getId());
                attachedDtfactura.add(dtfacturaDetalleFacturaToAttach);
            }
            producto.setDtfactura(attachedDtfactura);
            em.persist(producto);
            if (proveedor != null) {
                proveedor.getProducto().add(producto);
                proveedor = em.merge(proveedor);
            }
            for (DetalleFactura dtfacturaDetalleFactura : producto.getDtfactura()) {
                Producto oldProductoOfDtfacturaDetalleFactura = dtfacturaDetalleFactura.getProducto();
                dtfacturaDetalleFactura.setProducto(producto);
                dtfacturaDetalleFactura = em.merge(dtfacturaDetalleFactura);
                if (oldProductoOfDtfacturaDetalleFactura != null) {
                    oldProductoOfDtfacturaDetalleFactura.getDtfactura().remove(dtfacturaDetalleFactura);
                    oldProductoOfDtfacturaDetalleFactura = em.merge(oldProductoOfDtfacturaDetalleFactura);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getId());
            Proveedor proveedorOld = persistentProducto.getProveedor();
            Proveedor proveedorNew = producto.getProveedor();
            List<DetalleFactura> dtfacturaOld = persistentProducto.getDtfactura();
            List<DetalleFactura> dtfacturaNew = producto.getDtfactura();
            if (proveedorNew != null) {
                proveedorNew = em.getReference(proveedorNew.getClass(), proveedorNew.getId());
                producto.setProveedor(proveedorNew);
            }
            List<DetalleFactura> attachedDtfacturaNew = new ArrayList<DetalleFactura>();
            for (DetalleFactura dtfacturaNewDetalleFacturaToAttach : dtfacturaNew) {
                dtfacturaNewDetalleFacturaToAttach = em.getReference(dtfacturaNewDetalleFacturaToAttach.getClass(), dtfacturaNewDetalleFacturaToAttach.getId());
                attachedDtfacturaNew.add(dtfacturaNewDetalleFacturaToAttach);
            }
            dtfacturaNew = attachedDtfacturaNew;
            producto.setDtfactura(dtfacturaNew);
            producto = em.merge(producto);
            if (proveedorOld != null && !proveedorOld.equals(proveedorNew)) {
                proveedorOld.getProducto().remove(producto);
                proveedorOld = em.merge(proveedorOld);
            }
            if (proveedorNew != null && !proveedorNew.equals(proveedorOld)) {
                proveedorNew.getProducto().add(producto);
                proveedorNew = em.merge(proveedorNew);
            }
            for (DetalleFactura dtfacturaOldDetalleFactura : dtfacturaOld) {
                if (!dtfacturaNew.contains(dtfacturaOldDetalleFactura)) {
                    dtfacturaOldDetalleFactura.setProducto(null);
                    dtfacturaOldDetalleFactura = em.merge(dtfacturaOldDetalleFactura);
                }
            }
            for (DetalleFactura dtfacturaNewDetalleFactura : dtfacturaNew) {
                if (!dtfacturaOld.contains(dtfacturaNewDetalleFactura)) {
                    Producto oldProductoOfDtfacturaNewDetalleFactura = dtfacturaNewDetalleFactura.getProducto();
                    dtfacturaNewDetalleFactura.setProducto(producto);
                    dtfacturaNewDetalleFactura = em.merge(dtfacturaNewDetalleFactura);
                    if (oldProductoOfDtfacturaNewDetalleFactura != null && !oldProductoOfDtfacturaNewDetalleFactura.equals(producto)) {
                        oldProductoOfDtfacturaNewDetalleFactura.getDtfactura().remove(dtfacturaNewDetalleFactura);
                        oldProductoOfDtfacturaNewDetalleFactura = em.merge(oldProductoOfDtfacturaNewDetalleFactura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = producto.getId();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
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
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            Proveedor proveedor = producto.getProveedor();
            if (proveedor != null) {
                proveedor.getProducto().remove(producto);
                proveedor = em.merge(proveedor);
            }
            List<DetalleFactura> dtfactura = producto.getDtfactura();
            for (DetalleFactura dtfacturaDetalleFactura : dtfactura) {
                dtfacturaDetalleFactura.setProducto(null);
                dtfacturaDetalleFactura = em.merge(dtfacturaDetalleFactura);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
