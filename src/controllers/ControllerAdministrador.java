/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.swing.JOptionPane;
import models.Administrador;
import models.Cliente;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


/**
 *
 * @author ivan
 */
public class ControllerAdministrador extends ControllerBase {

//<editor-fold defaultstate="collapsed" desc="CODIGO CLIENTE">
//    public void saveClient(Cliente cliente) {
//        SessionFactory misesion = NewHibernateUtil.getSessionFactory();
//        Session session;
//        session = misesion.openSession();
//        Transaction tx = session.beginTransaction();
//        session.save(cliente);
//        tx.commit();
//        session.close();
//        JOptionPane.showMessageDialog(null, "Guardado exitosamente");
//    }
//
//    public void deleteClient(Cliente cliente) {
//        SessionFactory misesion = NewHibernateUtil.getSessionFactory();
//        Session session;
//        session = misesion.openSession();
//        Transaction tx = session.beginTransaction();
//        session.delete(cliente);
//        tx.commit();
//        session.close();
//        JOptionPane.showMessageDialog(null, "Eliminado exitosamente");
//    }
//
//    public Cliente searchCliente(String cliente) {
//        System.out.println("Cliente es : "+cliente);
//        
//        SessionFactory misesion = NewHibernateUtil.getSessionFactory();
//        Session session;
//        session = misesion.openSession();
//        Transaction tx = session.beginTransaction();
//        //cli = (Clientes) session.get(Clientes.class, cliente);
//        Cliente cli = (Cliente) session.createCriteria(Cliente.class)
//                .add(Restrictions.eq("nom_cliente", cliente)).uniqueResult();
//        
////        System.out.println("clie es : "+cli.getIdTab());
//
//        tx.commit();
//        session.close();
//        return cli;
//    }
//
//    public void saveReportClient(ReportesCliente reportes) {
//        SessionFactory misesion = NewHibernateUtil.getSessionFactory();
//        Session session;
//        session = misesion.openSession();
//        Transaction tx = session.beginTransaction();
//        session.save(reportes);
//        tx.commit();
//        session.close();
//        JOptionPane.showMessageDialog(null, "Reporte creado exitosamente");
//    }
//
////    public ArrayList<Clientes> listar() {
////        ArrayList<Clientes> datos = new ArrayList<Clientes>();
////        SessionFactory misesion = NewHibernateUtil.getSessionFactory();
////        Session session;
////        session = misesion.openSession();
////        Transaction tx = session.beginTransaction();
////        datos = (ArrayList<Clientes>) session.createCriteria(Clientes.class).list();
////        tx.commit();
////        session.close();
////
////        return datos;
////    }
//    public ArrayList<Cliente> listClient() {
//        ArrayList<Cliente> datos = new ArrayList<Cliente>();
//        SessionFactory misesion = NewHibernateUtil.getSessionFactory();
//        Session session;
//        session = misesion.openSession();
//        Transaction tx = session.beginTransaction();
//        datos = (ArrayList<Cliente>) session.createCriteria(Cliente.class).list();
//        tx.commit();
//        session.close();
//
//        return datos;
//    }
    //</editor-fold>
    private Administrador admin;
    private String id;
    private String ruc;
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private String email;
    private float descuento;

    public ControllerAdministrador() {
        super();
        admin = new Administrador();
    }

    public void LoadControllerAdministrador(String id, String nombre, String apellido, String direccion, String telefono) {

        admin.setCi(id);

        admin.setNombres(nombre);

        admin.setApellidos(apellido);

        admin.setDireccion(direccion);

        admin.setTelefono(telefono);

    }

    public void executeInsert() {
        this.insert(admin);

//        Session sesion = NewHibernateUtil.getCurrentSession();
//        sesion.beginTransaction();
//        sesion.save(admin);
//        sesion.getTransaction().commit();
//        sesion.close();
    }

    public void executeShow() {
        this.insert(admin);
    }
}
