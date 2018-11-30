package code.drmayx.controllers;

import code.drmayx.models.*;
import code.drmayx.services.*;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class MainController {

    private static MainController instance = null;
    private static Scanner scanner = null;
    private static SessionFactory factory;


    private MainController(){
        if(instance == null || instance != this){
            instance = this;
            if(scanner == null){
                scanner = new Scanner(System.in);
            }
        }
    }
    public static void Start(){
        new MainController();

        instance.useHibernate();

        String input = "";
        while(!input.equalsIgnoreCase("exit")){
            input = scanner.nextLine();
            System.out.println("You entered: " + input);
        }
        System.out.println("Closing console control...");
    }

    private Integer addPerson(String name, String info){
        Transaction tx = null;
        Integer id = null;
        try(Session session = factory.openSession()){
            tx = session.beginTransaction();
            Person person = new Person(name,info);
            id = (Integer) session.save(person);
            tx.commit();
        }catch (HibernateException e){
            if(tx!=null)tx.rollback();
            e.printStackTrace();
        }

        return id;
    }

    private void useHibernate(){

        Address address = new Address();
        address.setCity("Dhaka").setCountry("Bangladesh").setPostcode("1000").setStreet("Poribagh").setProvince("-");

        EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
        em.getTransaction().begin();
        em.persist(address);
        em.getTransaction().commit();
        em.close();
        PersistenceManager.INSTANCE.close();


//        try {
//            Configuration configuration = new Configuration().configure("META-INF/hibernate.cfg.xml");
//            factory = configuration.buildSessionFactory();
//        }catch (Throwable ex){
//            System.err.println("Failed to create sessionFactory object.\n" + ex);
//            throw new ExceptionInInitializerError(ex);
//        }
//
//
//        Integer person1 = instance.addPerson("Zara Ali", "Earns 1000$ monthly");
//        Integer person2 = instance.addPerson("Daisy Das", "Earns 5000$ monthly");
//        Integer person3 = instance.addPerson("John Paul", "Earns 10000$ monthly");
//
//        instance.listPeople();
//
//        instance.updatePerson(person1, 7000);
//
//        instance.deletePerson(person2);
//
//        instance.listPeople();
    }

    private void listPeople(){
        Transaction tx = null;
        try(Session session = factory.openSession()){
            tx = session.beginTransaction();
            List people = session.createQuery("FROM testdata").list();
            for(Iterator iterator = people.iterator(); iterator.hasNext();){
                Person person = (Person) iterator.next();
                System.out.println(person);
            }
            tx.commit();
        }catch (HibernateException e){
            if(tx!=null)tx.rollback();
            e.printStackTrace();
        }
    }

    public void updatePerson(Integer EmployeeID, int salary ){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Person employee = (Person)session.get(Person.class, EmployeeID);
            employee.setInfo("Earns " + salary + "$ monthly");
            session.update(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void deletePerson(Integer EmployeeID){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Person employee = (Person)session.get(Person.class, EmployeeID);
            session.delete(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
