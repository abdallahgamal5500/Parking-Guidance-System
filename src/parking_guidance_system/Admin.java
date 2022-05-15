package parking_guidance_system;

import Help.*;
import java.sql.SQLException;

public class Admin {
    
    private static Admin admin;
    
    public static Admin getInstance() {
        if (admin==null) 
            admin = new Admin();
        return admin;
    }
    
    public boolean addSpot() throws SQLException {
        return Spot.getInstance().addSpot();
    }
    
    public String[][] getAllSpots2D() throws SQLException {
        return Help.convertArrayListTo2dArray(Spot.getInstance().getAllSpots(), 2);
    }
    
    public boolean changeParkingPricePerHour(int newPrice) throws SQLException {
        return Parking.getInstance().changeParkingPricePerHour(newPrice);
    }
    
    public String[][] getExistTicketsInfo2D() throws SQLException {
        return Help.convertArrayListTo2dArray(Ticket.getInstance().getExistCarsInfo(), 3);
    }
    
    public boolean isUserExist(String email) throws SQLException {
        return User.getInstance().isUserExist(email);
    }
    
    public boolean createNewUser(String fullName, String password, String roll) throws SQLException {
        return User.getInstance().createNewUser(fullName, password, roll);
    }
    
    public boolean logIn(String email, String password) throws SQLException {
        if (User.getInstance().logIn(email, password)) {
            Help.currentUserRoll = User.getInstance().getCurrentUserRoleFromDatabase(email);
            return true;
        }
        return false;
    }
    
    public String[][] getAllUsers2D() throws SQLException {
        return Help.convertArrayListTo2dArray(User.getInstance().getAllUsers(), 3);
    }
    
    public boolean deleteUserByEmail(String email) throws SQLException {
        return User.getInstance().deleteUserByEmail(email);
    }
    
    public String[][] getTotalPayments2D() throws SQLException {
        return Help.convertArrayListTo2dArray(Ticket.getInstance().getTotalPayments(), 3);
    }
}