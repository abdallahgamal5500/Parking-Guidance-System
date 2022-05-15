package parking_guidance_system;

import Help.Help;
import java.sql.SQLException;

public class Operator{
    
    private static Operator operator;
    
    public static Operator getInstance() {
        if (operator==null) 
            operator = new Operator();
        return operator;
    }
    
    public String[][] getAllFreeSpots() throws SQLException {
        return Help.convertArrayListTo2dArray(Spot.getInstance().getAllFreeSpots(), 2);
    }    
    
    public int getFreeSpotId() throws SQLException {
        return Spot.getInstance().getFreeSpotId();
    }
    
    public boolean entryStation(int plateNumber, int spotId) throws SQLException {
        Ticket.getInstance().setTicketData(plateNumber, spotId);
        return (Ticket.getInstance().saveEntryTicket() && Spot.getInstance().changeState(spotId, "full"));
    }
    
    public String[][] getEntryTicketData2D() {
        String data[][] = {{"" + Ticket.getInstance().getId(), "" + Ticket.getInstance().getSpotId(), "" + Ticket.getInstance().getPlateNumber(), Ticket.getInstance().getEntryDate()}};
        return data;
    }
    
    public boolean isTicketExist(int ticketId) throws SQLException {
        return Ticket.getInstance().isTicketExist(ticketId);
    }
    
    public boolean exitStation() throws SQLException {
        Ticket.getInstance().initEntryTiketFromDatabase();
        Ticket.getInstance().initExitStation();
        return (Ticket.getInstance().saveExitTicket() && Spot.getInstance().changeState(Ticket.getInstance().getSpotId(), "empty"));
    }
    
    public String[][] getExitTicketData2D() {
        String data[][] = {{Ticket.getInstance().getEntryDate(),Ticket.getInstance().getExitDate(),""+Ticket.getInstance().getTotalHours()+" Hour",Ticket.getInstance().getTotalPayment()+" Dollar"}};
        return data;
    }
    
    public float getTotalParkingHours() throws SQLException {
        Ticket.getInstance().initEntryTiketFromDatabase();
        Ticket.getInstance().initExitStation();
        return Ticket.getInstance().getTotalHours();
    }
}