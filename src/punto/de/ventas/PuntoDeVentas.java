/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package punto.de.ventas;


import controllers.ControllerEntity;
import static java.awt.Frame.MAXIMIZED_BOTH;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import View.Principal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author AlexJPZ
 */
public class PuntoDeVentas {

    /**
     * @param args the command line arguments
     */
    private static EntityManager manager;
    public static EntityManagerFactory emf;
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            // select Look and Feel
//            UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
//            // start application
////            new MinFrame();
//        }
//        catch (Exception ex) {
//            ex.printStackTrace();
//        }
       
//        emf = Persistence.createEntityManagerFactory("sisventasPU");

        System.out.println("*****   pasooo**  ");
        Principal main = new Principal();
        main.setExtendedState(MAXIMIZED_BOTH);
        main.setVisible(true);

    }

}
