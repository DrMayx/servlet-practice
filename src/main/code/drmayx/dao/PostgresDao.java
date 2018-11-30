package code.drmayx.dao;

import java.sql.*;
import java.util.ArrayList;

public class PostgresDao {

    private static ArrayList<Connection> connections = new ArrayList<>();
    private Connection connection;
    private boolean isConnectionLoaded = false;

    public PostgresDao(){
        try{
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        try{
            this.connection = DriverManager.getConnection("jdbc:postgresql://192.168.1.102:5432/postgres", "programtestowy", "testAppPostgre5q1");
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        isConnectionLoaded = true;
        System.out.println("Database opened succesfully!");
        connections.add(connection);
    }

    public ResultSet GetDataFromQuery(String query) throws SQLException {
        if(!isConnectionLoaded){
            throw new SQLException("Connection with database is not initialized!");
        }
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public static void stop(){
        for(Connection c : connections){
            try {
                c.close();
            } catch (SQLException e) {
                System.out.println("Could not close connection!");
                e.printStackTrace();
            }
        }
    }
}
