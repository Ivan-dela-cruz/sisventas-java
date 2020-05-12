/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.Cliente;

/**
 *
 * @author ivan
 */
public class ControllerCliente extends ControllerBase {

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
    private Cliente cliente;
    private String id;
    private String ruc;
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private String email;
    private float descuento;

    public ControllerCliente() {
        super();
        cliente = new Cliente();
    }

    public void  LoadControllerCliente(String id, String ruc, String nombre, String apellido, String direccion, String telefono, String email, float descuento) {

        cliente.setCiCliente(id);

        cliente.setRucCliente(ruc);

        cliente.setNomCliente(nombre);

        cliente.setApeCliente(apellido);

        cliente.setDirCliente(direccion);

        cliente.setTelfCliente(telefono);

        cliente.setEmailCliente(email);

        cliente.setDescuentoCliente(descuento);

    }

    public void executeInsert() {
        this.insert(cliente);
    }
}
