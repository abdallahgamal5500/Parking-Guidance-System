package parking_guidance_system;
    
import Database.Database;
import java.sql.*;

public class Parking {
    
    private static String query;
    private static ResultSet resultSet;
    private static Parking parking;
    
    public static Parking getInstance() {
        if (parking==null) 
            parking = new Parking();
        return parking;
    }
    
    public int getParkingPricePerHour() throws SQLException {
        query = "select PARKING_PRICE_PER_HOUR from constant";
        resultSet = Database.getInstance().getRecords(query);
        resultSet.next();
        return resultSet.getInt("PARKING_PRICE_PER_HOUR");
    }
    
    public boolean changeParkingPricePerHour(int newPrice) throws SQLException {
        query = "update constant set PARKING_PRICE_PER_HOUR = " + newPrice + "";
        return (Database.getInstance().update(query) == 1);
    }
}
