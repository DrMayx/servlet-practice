package code.drmayx.dao;

import java.sql.*;

public class PostgresDao {

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
            this.connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "programtestowy", "testAppPostgre5q1");
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        isConnectionLoaded = true;
        System.out.println("Database opened succesfully!");
    }

    public ResultSet GetDataFromQuery(String query) throws SQLException {
        if(!isConnectionLoaded){
            throw new SQLException("Connection with database is not initialized!");
        }
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }
}
