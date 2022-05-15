package parking_guidance_system;

import Database.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User { 
    
    private String query;
    private ResultSet resultSet;
    private static User user;
    private String email;
    
    public static User getInstance() {
        if (user==null) 
            user = new User();
        return user;
    }
    
    public boolean isUserExist(String email) throws SQLException {
        this.email = email;
        query = "select count(1) from USERS where email= '"+ email +"'";
        resultSet = Database.getInstance().getRecords(query);
        int counter = 0;
        while (resultSet.next()) {
            counter = resultSet.getInt("1");
        }
        return (counter == 1);
    }
    
    public boolean createNewUser(String fullName, String password, String roll) throws SQLException {
        query = "INSERT INTO USERS(FULL_NAME,EMAIL,PASSWORD,ROLE) values('" + fullName + "','" + email + "','" + password + "','" + roll + "')";
        return (Database.getInstance().update(query) == 1);
    }

    public boolean logIn(String email, String password) throws SQLException {
        query = "select count(1) from USERS where EMAIL='" + email + "' and PASSWORD='" + password + "'";
        resultSet = Database.getInstance().getRecords(query);
        int counter = 0;
        while (resultSet.next()) {
            counter = resultSet.getInt("1");
        }
        return (counter == 1);
    }

    public String getCurrentUserRoleFromDatabase(String email) throws SQLException {
        query = "select * from USERS where EMAIL='"+email+"'";
        resultSet = Database.getInstance().getRecords(query);
        resultSet.next();
        return resultSet.getString("ROLE"); 
    }
    
    public ArrayList<String> getAllUsers() throws SQLException {
        ArrayList<String> arrayList = new ArrayList<>();
        query = "SELECT * FROM USERS";
        resultSet = Database.getInstance().getRecords(query);
        while (resultSet.next()) {
            arrayList.add(resultSet.getString("FULL_NAME"));
            arrayList.add(resultSet.getString("EMAIL"));
            arrayList.add(resultSet.getString("ROLE"));
        }
        return arrayList;
    }

    public boolean deleteUserByEmail(String email) throws SQLException {
        query = "DELETE FROM USERS WHERE EMAIL =  '"+ email + "'";
        return (Database.getInstance().update(query) == 1);
    }
}
