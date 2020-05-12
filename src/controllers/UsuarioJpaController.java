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
import models.Administrador;
import models.Empleado;
import models.Caja;
import models.Usuario;

/**
 *
 * @author ivan
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController() {
        this.emf = Persistence.createEntityManagerFactory("sisventasPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Administrador admin = usuario.getAdmin();
            if (admin != null) {
                admin = em.getReference(admin.getClass(), admin.getId());
                usuario.setAdmin(admin);
            }
            Empleado empleado = usuario.getEmpleado();
            if (empleado != null) {
                empleado = em.getReference(empleado.getClass(), empleado.getId());
                usuario.setEmpleado(empleado);
            }
            Caja caja = usuario.getCaja();
            if (caja != null) {
                caja = em.getReference(caja.getClass(), caja.getId());
                usuario.setCaja(caja);
            }
            em.persist(usuario);
            if (admin != null) {
                Usuario oldUserOfAdmin = admin.getUser();
                if (oldUserOfAdmin != null) {
                    oldUserOfAdmin.setAdmin(null);
                    oldUserOfAdmin = em.merge(oldUserOfAdmin);
                }
                admin.setUser(usuario);
                admin = em.merge(admin);
            }
            if (empleado != null) {
                Usuario oldUserOfEmpleado = empleado.getUser();
                if (oldUserOfEmpleado != null) {
                    oldUserOfEmpleado.setEmpleado(null);
                    oldUserOfEmpleado = em.merge(oldUserOfEmpleado);
                }
                empleado.setUser(usuario);
                empleado = em.merge(empleado);
            }
            if (caja != null) {
                Usuario oldUserOfCaja = caja.getUser();
                if (oldUserOfCaja != null) {
                    oldUserOfCaja.setCaja(null);
                    oldUserOfCaja = em.merge(oldUserOfCaja);
                }
                caja.setUser(usuario);
                caja = em.merge(caja);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getId());
            Administrador adminOld = persistentUsuario.getAdmin();
            Administrador adminNew = usuario.getAdmin();
            Empleado empleadoOld = persistentUsuario.getEmpleado();
            Empleado empleadoNew = usuario.getEmpleado();
            Caja cajaOld = persistentUsuario.getCaja();
            Caja cajaNew = usuario.getCaja();
            if (adminNew != null) {
                adminNew = em.getReference(adminNew.getClass(), adminNew.getId());
                usuario.setAdmin(adminNew);
            }
            if (empleadoNew != null) {
                empleadoNew = em.getReference(empleadoNew.getClass(), empleadoNew.getId());
                usuario.setEmpleado(empleadoNew);
            }
            if (cajaNew != null) {
                cajaNew = em.getReference(cajaNew.getClass(), cajaNew.getId());
                usuario.setCaja(cajaNew);
            }
            usuario = em.merge(usuario);
            if (adminOld != null && !adminOld.equals(adminNew)) {
                adminOld.setUser(null);
                adminOld = em.merge(adminOld);
            }
            if (adminNew != null && !adminNew.equals(adminOld)) {
                Usuario oldUserOfAdmin = adminNew.getUser();
                if (oldUserOfAdmin != null) {
                    oldUserOfAdmin.setAdmin(null);
                    oldUserOfAdmin = em.merge(oldUserOfAdmin);
                }
                adminNew.setUser(usuario);
                adminNew = em.merge(adminNew);
            }
            if (empleadoOld != null && !empleadoOld.equals(empleadoNew)) {
                empleadoOld.setUser(null);
                empleadoOld = em.merge(empleadoOld);
            }
            if (empleadoNew != null && !empleadoNew.equals(empleadoOld)) {
                Usuario oldUserOfEmpleado = empleadoNew.getUser();
                if (oldUserOfEmpleado != null) {
                    oldUserOfEmpleado.setEmpleado(null);
                    oldUserOfEmpleado = em.merge(oldUserOfEmpleado);
                }
                empleadoNew.setUser(usuario);
                empleadoNew = em.merge(empleadoNew);
            }
            if (cajaOld != null && !cajaOld.equals(cajaNew)) {
                cajaOld.setUser(null);
                cajaOld = em.merge(cajaOld);
            }
            if (cajaNew != null && !cajaNew.equals(cajaOld)) {
                Usuario oldUserOfCaja = cajaNew.getUser();
                if (oldUserOfCaja != null) {
                    oldUserOfCaja.setCaja(null);
                    oldUserOfCaja = em.merge(oldUserOfCaja);
                }
                cajaNew.setUser(usuario);
                cajaNew = em.merge(cajaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = usuario.getId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            Administrador admin = usuario.getAdmin();
            if (admin != null) {
                admin.setUser(null);
                admin = em.merge(admin);
            }
            Empleado empleado = usuario.getEmpleado();
            if (empleado != null) {
                empleado.setUser(null);
                empleado = em.merge(empleado);
            }
            Caja caja = usuario.getCaja();
            if (caja != null) {
                caja.setUser(null);
                caja = em.merge(caja);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
