/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.Serializable;
import java.util.List;
import static java.util.concurrent.ThreadLocalRandom.current;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import models.Administrador;
import models.Local;

/**
 *
 * @author ivan
 */
public class Controlador implements Serializable {

    private EntityManagerFactory emf;

    public Controlador() {
        this.emf = Persistence.createEntityManagerFactory("sisventasPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void insert(Object o) {
        EntityManager em = null;
        em = getEntityManager();
        em.getTransaction().begin();
        
        em.persist(o);
        em.getTransaction().commit();
        em.close();
    }

    public void update(Object o) {
        EntityManager em = null;
        em = getEntityManager();
        em.getTransaction().begin();
        em.merge(o);
        em.getTransaction().commit();
        em.close();
    }

    public void delete(Object o) {
        EntityManager em = null;
        em = getEntityManager();
        em.getTransaction().begin();
        if (!em.contains(o)) {
            o = em.merge(o);
        }
        em.remove(o);
        em.getTransaction().commit();
        em.close();
    }

    public Local buscarLocal(int id) {
        EntityManager em = getEntityManager();

        try {
            return em.find(Local.class, id);
        } finally {
            em.close();
        }

    }

    public Administrador buscarAdmin(int id) {
        EntityManager em = null;
        em = getEntityManager();
        return em.find(Administrador.class, id);
    }

    public List<Local> getAllLocales() {
        EntityManager em = null;
        em = getEntityManager();
        String SQL = "Select p from Local p";
        return em.createQuery(SQL).getResultList();
    }

}
