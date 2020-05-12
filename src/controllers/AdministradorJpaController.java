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
import models.Local;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import models.Administrador;

/**
 *
 * @author ivan
 */
public class AdministradorJpaController implements Serializable {

    public AdministradorJpaController() {
        this.emf = Persistence.createEntityManagerFactory("sisventasPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Administrador administrador) {
        if (administrador.getLocal() == null) {
            administrador.setLocal(new ArrayList<Local>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario user = administrador.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getId());
                administrador.setUser(user);
            }
            List<Local> attachedLocal = new ArrayList<Local>();
            for (Local localLocalToAttach : administrador.getLocal()) {
                localLocalToAttach = em.getReference(localLocalToAttach.getClass(), localLocalToAttach.getId());
                attachedLocal.add(localLocalToAttach);
            }
            administrador.setLocal(attachedLocal);
            em.persist(administrador);
            if (user != null) {
                Administrador oldAdminOfUser = user.getAdmin();
                if (oldAdminOfUser != null) {
                    oldAdminOfUser.setUser(null);
                    oldAdminOfUser = em.merge(oldAdminOfUser);
                }
                user.setAdmin(administrador);
                user = em.merge(user);
            }
            for (Local localLocal : administrador.getLocal()) {
                Administrador oldAdminOfLocalLocal = localLocal.getAdmin();
                localLocal.setAdmin(administrador);
                localLocal = em.merge(localLocal);
                if (oldAdminOfLocalLocal != null) {
                    oldAdminOfLocalLocal.getLocal().remove(localLocal);
                    oldAdminOfLocalLocal = em.merge(oldAdminOfLocalLocal);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Administrador administrador) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Administrador persistentAdministrador = em.find(Administrador.class, administrador.getId());
            Usuario userOld = persistentAdministrador.getUser();
            Usuario userNew = administrador.getUser();
            List<Local> localOld = persistentAdministrador.getLocal();
            List<Local> localNew = administrador.getLocal();
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getId());
                administrador.setUser(userNew);
            }
            List<Local> attachedLocalNew = new ArrayList<Local>();
            for (Local localNewLocalToAttach : localNew) {
                localNewLocalToAttach = em.getReference(localNewLocalToAttach.getClass(), localNewLocalToAttach.getId());
                attachedLocalNew.add(localNewLocalToAttach);
            }
            localNew = attachedLocalNew;
            administrador.setLocal(localNew);
            administrador = em.merge(administrador);
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.setAdmin(null);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                Administrador oldAdminOfUser = userNew.getAdmin();
                if (oldAdminOfUser != null) {
                    oldAdminOfUser.setUser(null);
                    oldAdminOfUser = em.merge(oldAdminOfUser);
                }
                userNew.setAdmin(administrador);
                userNew = em.merge(userNew);
            }
            for (Local localOldLocal : localOld) {
                if (!localNew.contains(localOldLocal)) {
                    localOldLocal.setAdmin(null);
                    localOldLocal = em.merge(localOldLocal);
                }
            }
            for (Local localNewLocal : localNew) {
                if (!localOld.contains(localNewLocal)) {
                    Administrador oldAdminOfLocalNewLocal = localNewLocal.getAdmin();
                    localNewLocal.setAdmin(administrador);
                    localNewLocal = em.merge(localNewLocal);
                    if (oldAdminOfLocalNewLocal != null && !oldAdminOfLocalNewLocal.equals(administrador)) {
                        oldAdminOfLocalNewLocal.getLocal().remove(localNewLocal);
                        oldAdminOfLocalNewLocal = em.merge(oldAdminOfLocalNewLocal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = administrador.getId();
                if (findAdministrador(id) == null) {
                    throw new NonexistentEntityException("The administrador with id " + id + " no longer exists.");
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
            Administrador administrador;
            try {
                administrador = em.getReference(Administrador.class, id);
                administrador.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The administrador with id " + id + " no longer exists.", enfe);
            }
            Usuario user = administrador.getUser();
            if (user != null) {
                user.setAdmin(null);
                user = em.merge(user);
            }
            List<Local> local = administrador.getLocal();
            for (Local localLocal : local) {
                localLocal.setAdmin(null);
                localLocal = em.merge(localLocal);
            }
            em.remove(administrador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Administrador> findAdministradorEntities() {
        return findAdministradorEntities(true, -1, -1);
    }

    public List<Administrador> findAdministradorEntities(int maxResults, int firstResult) {
        return findAdministradorEntities(false, maxResults, firstResult);
    }

    private List<Administrador> findAdministradorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Administrador.class));
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

    public Administrador findAdministrador(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Administrador.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdministradorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Administrador> rt = cq.from(Administrador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
