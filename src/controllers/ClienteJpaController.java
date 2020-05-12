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
import models.Factura;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import models.Cliente;

/**
 *
 * @author ivan
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController() {
        this.emf = Persistence.createEntityManagerFactory("sisventasPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        if (cliente.getFactura() == null) {
            cliente.setFactura(new ArrayList<Factura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Factura> attachedFactura = new ArrayList<Factura>();
            for (Factura facturaFacturaToAttach : cliente.getFactura()) {
                facturaFacturaToAttach = em.getReference(facturaFacturaToAttach.getClass(), facturaFacturaToAttach.getId());
                attachedFactura.add(facturaFacturaToAttach);
            }
            cliente.setFactura(attachedFactura);
            em.persist(cliente);
            for (Factura facturaFactura : cliente.getFactura()) {
                Cliente oldClienteOfFacturaFactura = facturaFactura.getCliente();
                facturaFactura.setCliente(cliente);
                facturaFactura = em.merge(facturaFactura);
                if (oldClienteOfFacturaFactura != null) {
                    oldClienteOfFacturaFactura.getFactura().remove(facturaFactura);
                    oldClienteOfFacturaFactura = em.merge(oldClienteOfFacturaFactura);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getId());
            List<Factura> facturaOld = persistentCliente.getFactura();
            List<Factura> facturaNew = cliente.getFactura();
            List<Factura> attachedFacturaNew = new ArrayList<Factura>();
            for (Factura facturaNewFacturaToAttach : facturaNew) {
                facturaNewFacturaToAttach = em.getReference(facturaNewFacturaToAttach.getClass(), facturaNewFacturaToAttach.getId());
                attachedFacturaNew.add(facturaNewFacturaToAttach);
            }
            facturaNew = attachedFacturaNew;
            cliente.setFactura(facturaNew);
            cliente = em.merge(cliente);
            for (Factura facturaOldFactura : facturaOld) {
                if (!facturaNew.contains(facturaOldFactura)) {
                    facturaOldFactura.setCliente(null);
                    facturaOldFactura = em.merge(facturaOldFactura);
                }
            }
            for (Factura facturaNewFactura : facturaNew) {
                if (!facturaOld.contains(facturaNewFactura)) {
                    Cliente oldClienteOfFacturaNewFactura = facturaNewFactura.getCliente();
                    facturaNewFactura.setCliente(cliente);
                    facturaNewFactura = em.merge(facturaNewFactura);
                    if (oldClienteOfFacturaNewFactura != null && !oldClienteOfFacturaNewFactura.equals(cliente)) {
                        oldClienteOfFacturaNewFactura.getFactura().remove(facturaNewFactura);
                        oldClienteOfFacturaNewFactura = em.merge(oldClienteOfFacturaNewFactura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = cliente.getId();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<Factura> factura = cliente.getFactura();
            for (Factura facturaFactura : factura) {
                facturaFactura.setCliente(null);
                facturaFactura = em.merge(facturaFactura);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
