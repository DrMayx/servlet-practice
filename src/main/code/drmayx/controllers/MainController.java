package code.drmayx.controllers;

import code.drmayx.models.Person;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

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

        try {
            factory = new Configuration().configure().buildSessionFactory();
        }catch (Throwable ex){
            System.err.println("Failed to create sessionFactory object.\n" + ex);
            throw new ExceptionInInitializerError(ex);
        }


        Integer person1 = instance.addPerson("Zara Ali", "Earns 1000$ monthly");
        Integer person2 = instance.addPerson("Daisy Das", "Earns 5000$ monthly");
        Integer person3 = instance.addPerson("John Paul", "Earns 10000$ monthly");

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
}
