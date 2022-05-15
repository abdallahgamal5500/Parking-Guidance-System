package parking_guidance_system;

import Database.Database;
import Help.Help;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Spot {
    
    private String query;
    private ResultSet resultSet;
    private static Spot spot;
    
    //singleton pattern
    public static Spot getInstance() {
        if (spot==null) 
            spot = new Spot();
        return spot;
    }
    
    public boolean changeState(int id,String state) throws SQLException {
        query = "UPDATE SPOT SET STATE = '"+state+"' WHERE ID = "+id+"";
        return Database.getInstance().update(query)==1;
    } 
    
    public boolean addSpot() throws SQLException {
        query = "insert INTO spot (\"STATE\") VALUES ('empty')";
        return (Database.getInstance().update(query)==1);
    }
    
    public int getFreeSpotId() throws SQLException {
        int id = -1;
        query = "SELECT ID from SPOT where state='empty'";
        resultSet = Database.getInstance().getRecords(query);
        while(resultSet.next()) {
            id = resultSet.getInt("ID");
            break;
        }
        return id;
    }
    
    public ArrayList<String> getAllFreeSpots() throws SQLException {
        ArrayList<String> arrayList = new ArrayList<>();
        query = "SELECT * from spot where state ='empty'";
        resultSet = Database.getInstance().getRecords(query);
        while (resultSet.next()) {
            arrayList.add("" + resultSet.getInt("id"));
            arrayList.add(resultSet.getString("state"));
        }
        return arrayList;
    }
    
    public ArrayList<String> getAllSpots() throws SQLException {
        ArrayList<String> arrayList = new ArrayList<>();
        query = "SELECT * FROM SPOT";
        resultSet = Database.getInstance().getRecords(query);
        while (resultSet.next()) {
            arrayList.add("" + resultSet.getInt("id"));
            arrayList.add(resultSet.getString("state"));
        }
        return arrayList;
    } 
}
