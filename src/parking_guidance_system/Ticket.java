package parking_guidance_system;

import Database.Database;
import Help.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Ticket {
    
    private int id,spotId,plateNumber;
    private float totalHours,totalPayment;
    private String entryDate ,exitDate;
    private String query = "";
    private ResultSet resultSet;
    private static Ticket ticket;
    
    public static Ticket getInstance() {
        if (ticket==null) 
            ticket = new Ticket();
        return ticket;
    }

    public int getId() {
        return id;
    }

    public int getSpotId() {
        return spotId;
    }

    public int getPlateNumber() {
        return plateNumber;
    }

    public float getTotalHours() {
        return totalHours;
    }

    public float getTotalPayment() {
        return totalPayment;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public String getExitDate() {
        return exitDate;
    }

    public void setTicketData(int plateNumber, int spotId) {
        this.plateNumber = plateNumber;
        this.spotId = spotId;
        this.entryDate = Help.getCurrentDateAndTime();
    }
    
    public boolean saveEntryTicket() throws SQLException {
        query = "insert into TICKET(SPOT_ID,PLATE_NUMBER,ENTRY_DATE) values("+spotId+","+plateNumber+",'"+getEntryDate()+"')";
        if (Database.getInstance().update(query)==1) {
            initTicketId();
            return true;
        }
        return false;
    }

    private void initTicketId() throws SQLException {
        query = "SELECT id FROM TICKET WHERE PLATE_NUMBER = " + plateNumber + "";
        resultSet = Database.getInstance().getRecords(query);
        resultSet.next();
        this.id = resultSet.getInt("ID");
    }

    public boolean isTicketExist(int ticketId) throws SQLException {
        this.id = ticketId;
        query = "select count(1) from TICKET where ID= "+ id +"";
        resultSet = Database.getInstance().getRecords(query);
        int counter = 0;
        while (resultSet.next()) {
            counter = resultSet.getInt("1");
        }
        return (counter == 1);
    }
    
    public void initEntryTiketFromDatabase() throws SQLException {
        query = "SELECT * from ticket where id = "+id+"";
        resultSet = Database.getInstance().getRecords(query);
        resultSet.next();
        this.spotId = resultSet.getInt("spot_id");
        this.plateNumber = resultSet.getInt("plate_number");
        this.entryDate = resultSet.getString("entry_date");
    }
    public void initExitStation() throws SQLException {
        this.exitDate = Help.getCurrentDateAndTime();
        this.totalHours = getTotalParkingHours();
        this.totalPayment = Float.parseFloat(String.format("%.2f",Parking.getInstance().getParkingPricePerHour()*this.totalHours));
    } 
    
    private float getTotalParkingHours() {
        return Float.parseFloat(String.format("%.2f", Help.getTotalHours(entryDate,exitDate)));
    }
    
    public boolean saveExitTicket() throws SQLException {
        query = "UPDATE TICKET SET EXIT_DATE = '"+exitDate+"', TOTAL_HOURS = "+totalHours+", TOTAL_PAYMENTS = "+totalPayment+" WHERE ID = "+id+"";
        return Database.getInstance().update(query)==1;
    }
    
    public ArrayList<String> getTotalPayments() throws SQLException {
        ArrayList<String> arrayList = new ArrayList<>();
        double totalPaymentsFirstShift = 0, totalPaymentsSecondShift = 0, totalPaymentsThirdShift = 0;
        resultSet = getExitDateAndTotalPaymentsFromDatabase();
        while (resultSet.next()) {
            if (Help.ifCurrentDay(resultSet.getString("EXIT_DATE"))) {
                int hour = Help.getHourFromDateTime(resultSet.getString("EXIT_DATE"));
                if (hour >= 0 && hour <= 7) {
                    totalPaymentsFirstShift += resultSet.getDouble("TOTAL_PAYMENTS");
                } else if (hour >= 8 && hour <= 15) {
                    totalPaymentsSecondShift += resultSet.getDouble("TOTAL_PAYMENTS");
                } else {
                    totalPaymentsThirdShift += resultSet.getDouble("TOTAL_PAYMENTS");
                }
            }
        }
        arrayList.add(totalPaymentsFirstShift + " Dollar");
        arrayList.add(totalPaymentsSecondShift + " Dollar");
        arrayList.add(totalPaymentsThirdShift + " Dollar");
        return arrayList;
    }
    
    private ResultSet getExitDateAndTotalPaymentsFromDatabase() throws SQLException {
        query = "SELECT EXIT_DATE,TOTAL_PAYMENTS FROM TICKET WHERE EXIT_DATE IS NOT NULL";
        resultSet = Database.getInstance().getRecords(query);
        return resultSet;
    }
    
    public ArrayList<String> getExistCarsInfo() throws SQLException {
        ArrayList<String> arrayList = new ArrayList<>();
        query = "SELECT SPOT_ID,PLATE_NUMBER,ENTRY_DATE FROM TICKET WHERE exit_date IS NULL";
        resultSet = Database.getInstance().getRecords(query);
        while (resultSet.next()) {
            arrayList.add("" + resultSet.getInt("SPOT_ID"));
            arrayList.add("" + resultSet.getInt("PLATE_NUMBER"));
            arrayList.add(resultSet.getString("ENTRY_DATE"));
        }
        return arrayList;
    }
}