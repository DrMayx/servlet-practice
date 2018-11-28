package test;

import code.drmayx.dao.PostgresDao;
import code.drmayx.servlets.DatabaseServlet;
import code.drmayx.servlets.RootServlet;
import code.drmayx.servlets.SimpleServlet;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestMain {
    public static void main(String[] args) {

        testServlets();
        testDABInitializesAndSelects();
    }

    public static void testServlets(){
        SimpleServlet svl = new SimpleServlet();
        Class test = svl.getClass();
        System.out.println(test.getCanonicalName());
        RootServlet root = new RootServlet();
        Class roottest = root.getClass();
        System.out.println(roottest.getCanonicalName());
        DatabaseServlet db = new DatabaseServlet();
        Class dbtest = db.getClass();
        System.out.println(dbtest.getCanonicalName());
    }

    public static void testDABInitializesAndSelects(){
        // test dao
        PostgresDao pgdao = new PostgresDao();
        ResultSet results = null;
        try {
            results = pgdao.GetDataFromQuery("SELECT name FROM testdata");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(results != null){
            try {
                results.next();
                System.out.println(results.getString("name"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
