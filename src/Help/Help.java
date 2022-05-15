package Help;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Help {
    
    public static final String ERROR_EMPTY_FIELD = "Empty field";
    public static final String ERROR_UPDATING_DATABASE = "Error during updating the database";
    public static String currentUserRoll = "";
    
    public static String getCurrentDateAndTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        return dateTimeFormatter.format(localDateTime);
    }
    
    public static float getTotalHours(String startDate,String endDate) {
        int totalYear = Integer.parseInt(endDate.substring(0, 4))-Integer.parseInt(startDate.substring(0, 4));
        int totalMonth = Integer.parseInt(endDate.substring(5, 7))-Integer.parseInt(startDate.substring(5, 7));
        int totalDay = Integer.parseInt(endDate.substring(8, 10))-Integer.parseInt(startDate.substring(8, 10));
        int totalHour = Integer.parseInt(endDate.substring(11,13))-Integer.parseInt(startDate.substring(11,13));
        int totalMinute = Integer.parseInt(endDate.substring(14,16))-Integer.parseInt(startDate.substring(14,16));
        return (float) ((365*totalYear*24) + (totalMonth*365*24/12) + (totalDay*24) + totalHour + (totalMinute/60.0));
    }
    
    private static String[] spliteDateTime(String dateAndTime) {
        String[] dateAndTimeArray = {dateAndTime.substring(0, dateAndTime.indexOf(" ")), dateAndTime.substring(dateAndTime.indexOf(" ") + 1)};
        return dateAndTimeArray;
    }
    
    public static boolean ifCurrentDay(String dateAndTime) {
        return spliteDateTime(dateAndTime)[0].equals(spliteDateTime(getCurrentDateAndTime())[0]);
    }
    
    public static int getHourFromDateTime(String dateAndTime) {
        return Integer.parseInt(spliteDateTime(dateAndTime)[1].substring(0, spliteDateTime(dateAndTime)[1].indexOf(":")));
    }
    
    public static String[][] convertArrayListTo2dArray(ArrayList<String> arrayList, int coulmnsNumber) {
        int counter = 0;
        String[][] data = new String[(arrayList.size()) / coulmnsNumber][coulmnsNumber];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < coulmnsNumber; j++) {
                data[i][j] = arrayList.get(counter);
                counter++;
            }
        }
        return data;
    }
    
    public static void setCenterScreen(JFrame frame1,JFrame frame2) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame2.setLocation(dimension.width/2-frame2.getSize().width/2, dimension.height/2-frame2.getSize().height/2);
        frame1.dispose();
        frame2.setVisible(true);
    }
    
    public static void showErrorDialog(String errorMessage) {
        JOptionPane.showMessageDialog(null,errorMessage,"Error",JOptionPane.ERROR_MESSAGE);
    }
    
    public static void showInfoDialog(String infoMessage) {
        JOptionPane.showMessageDialog(null,infoMessage,"Information",JOptionPane.INFORMATION_MESSAGE);
    }    
}