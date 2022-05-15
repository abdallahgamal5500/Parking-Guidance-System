package Database;

import java.sql.*;

public class Database {
    
    private static final String URL = "jdbc:derby://localhost:1527/parking_database";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "123456";
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    private static Database database;
    
    //singleton pattern
    public static Database getInstance() throws SQLException {
        if (database==null) {
            database = new Database();
            connection = DriverManager.getConnection(URL,USER_NAME,PASSWORD);
            statement = connection.createStatement();
        }
        return database;
    }
    
    public int update(String query) throws SQLException {
        return statement.executeUpdate(query);
    }
    
    public ResultSet getRecords(String query) throws SQLException {
        resultSet = statement.executeQuery(query);
        return resultSet;
    }
}