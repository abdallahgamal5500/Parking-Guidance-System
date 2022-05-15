/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking_guidance_system;

import Database.Database;
import GUI.LogIn;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.SQLException;

/**
 *
 * @author abdal
 */
public class Main {

    public static void main(String[] args) throws SQLException {
        LogIn logIn = new LogIn();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        logIn.setLocation(dimension.width/2-logIn.getSize().width/2, dimension.height/2-logIn.getSize().height/2);
        logIn.setVisible(true);
    }
}
