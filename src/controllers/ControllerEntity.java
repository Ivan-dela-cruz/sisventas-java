/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

/**
 *
 * @author ivan
 */
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import models.*;

public class ControllerEntity {

    private static EntityManager manager;
    public static EntityManagerFactory emf;

    public boolean MigrateTables() {
        boolean ex = false;
        try {
            emf = Persistence.createEntityManagerFactory("sisventasPU");
            ex = true;
           
        } catch (Exception e) {
            ex = false;
            JOptionPane.showMessageDialog(null, e);
        }
        return ex;
    }
}
