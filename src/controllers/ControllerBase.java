/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.ControllerEntity.emf;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;
import models.Administrador;
import models.Local;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author ivan
 */
public class ControllerBase {

    private EntityManager manager;

    public ControllerBase() {
    }

    public void insert(Object objeto) {
//        manager = emf.createEntityManager();
//        manager.getTransaction().begin();
//        manager.persist(objeto);
//        manager.getTransaction().commit();
//        manager.close();

        SessionFactory misesion = NewHibernateUtil.getSessionFactory();
        Session session;
        session = misesion.openSession();
        Transaction tx = session.beginTransaction();
        session.save(objeto);
        tx.commit();
        session.close();
        JOptionPane.showMessageDialog(null, "Guardado exitosamente");
    }

    public void update(Object objeto) {
        manager = emf.createEntityManager();
        manager.getTransaction().begin();
        manager.persist(objeto);
        manager.getTransaction().commit();
        manager.close();
    }

    public void delete(Object objeto) {
        manager = emf.createEntityManager();
        manager.getTransaction().begin();
        manager.persist(objeto);
        manager.getTransaction().commit();
        manager.close();
    }

    public void show(Object objeto) {
        manager = emf.createEntityManager();
        manager.getTransaction().begin();
        manager.persist(objeto);
        manager.getTransaction().commit();
        manager.close();
    }

}
