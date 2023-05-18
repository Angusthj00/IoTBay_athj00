/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.DAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import models.Shipment;

/* 
* DBManager is the primary DAO class to interact with the database. 
* Complete the existing methods of this classes to perform CRUD operations with the db.
*/

public class DBShipmentManager {

    private Statement st;

    public DBShipmentManager (Connection conn) throws SQLException {       
       st = conn.createStatement();   
    }

    //Find shipment by id - read one row in the database   
    public Shipment findShipment(int shipmentId) throws SQLException {       
       //setup the select sql query string 
       String fetch = "SELECT * FROM SHIPMENT WHERE SHIPMENTID=" + shipmentId;

       //execute this query using the statement field
       ResultSet rs = st.executeQuery(fetch);

       //add the results to a ResultSet
       Shipment shipment = null;
       if (rs.next()) {
           //search the ResultSet for a shipment using the parameters
           int id = rs.getInt("SHIPMENTID");
           String method = rs.getString("SHIPMENTMETHOD");
           LocalDate date = LocalDate.parse(rs.getString("SHIPMENTDATE"));
           String address = rs.getString("SHIPMENTADDRESS");
           shipment = new Shipment(id, method, date, address);
       }
       return shipment;   
    }


    //Add a user-data into the database   
    public void addShipment(int id, String method, LocalDate date, String address) throws SQLException {                   
        //code for add-operation       
        // setup the insert sql query string with parameters
        String insert = "INSERT INTO SHIPMENT (SHIPMENTID, SHIPMENTMETHOD, SHIPMENTDATE, SHIPMENTADDRESS) VALUES (?, ?, ?, ?)";

        // prepare the statement with the sql query string
        PreparedStatement ps = st.getConnection().prepareStatement(insert);

        // set the values of the parameters in the prepared statement
        ps.setInt(1, id);
        ps.setString(2, method);
        ps.setString(3, date.toString());
        ps.setString(4, address);

        // execute the prepared statement to insert the shipment into the database
        ps.executeUpdate();

        // close the prepared statement
        ps.close();
    }

    //update a shipment details in the database   
    public void editShipment(int id, String method, LocalDate date, String address) throws SQLException {       
        // setup the update sql query string with parameters
        String update = "UPDATE SHIPMENT SET SHIPMENTMETHOD=?, SHIPMENTDATE=?, SHIPMENTADDRESS=? WHERE SHIPMENTID=?";

        // prepare the statement with the sql query string
        PreparedStatement ps = st.getConnection().prepareStatement(update);

        // set the values of the parameters in the prepared statement
        ps.setString(1, method);
        ps.setString(2, date.toString());
        ps.setString(3, address);
        ps.setInt(4, id);

        // execute the prepared statement to update the shipment in the database
        ps.executeUpdate();

        // close the prepared statement
        ps.close();
    }

    //delete a shipment from the database   
    public void deleteShipment(int id) throws SQLException{       
        // setup the delete sql query string with parameters
        String delete = "DELETE FROM SHIPMENT WHERE SHIPMENTID=?";

        // prepare the statement with the sql query string
        PreparedStatement ps = st.getConnection().prepareStatement(delete);

        // set the value of the parameter in the prepared statement
        ps.setInt(1, id);

        // execute the prepared statement to delete the shipment from the database
        ps.executeUpdate();

        // close the prepared statement
        ps.close();
    }
    
    public ArrayList<Shipment> fetchShipment() throws SQLException {
        String fetch = "SELECT * FROM SHIPMENT";
        ResultSet rs = st.executeQuery(fetch);
        ArrayList<Shipment> shipments = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("SHIPMENTID");
            String method = rs.getString("SHIPMENTMETHOD");
            LocalDate date = LocalDate.parse(rs.getString("SHIPMENTDATE"));
            String address = rs.getString("SHIPMENTADDRESS");
            Shipment shipment = new Shipment(id, method, date, address);
            shipments.add(shipment);
        }

        return shipments;
    }

    public boolean checkShipment(int id) throws SQLException {
        // Setup the select SQL query string
        String query = "SELECT COUNT(*) FROM SHIPMENT WHERE SHIPMENTID = ?";

        // Prepare the statement with the SQL query string
        PreparedStatement ps = st.getConnection().prepareStatement(query);

        // Set the value of the parameter in the prepared statement
        ps.setInt(1, id);

        // Execute the prepared statement and retrieve the result
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        // Close the prepared statement and result set
        rs.close();
        ps.close();

        // Return true if shipment ID exists in the database, false otherwise
        return count > 0;
    }
}
