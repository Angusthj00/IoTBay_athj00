/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package unit.test;

import java.sql.Connection;
import junit.framework.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import models.Item;
import model.DAO.*;
import java.sql.SQLException;


/**
 *
 * @author angus
 */

public class AllTests {
    private Item item;
    private DBConnector db;
    private DBItemManager itemManager;
    private Connection conn;
    
    public AllTests() {
        item = new Item(20, "Samsung Note 10", "Smartphone", "samsung.jpg", "This is samsung phone.", 1299.99, 10);
        
        //initialise db connection here
        try {
            db = new DBConnector();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    @Test
    public void getId() {
        int res = item.getId();
        assertEquals(20, res);
    }
    
    @Test
    public void getName() {
        String res = item.getName();
        assertEquals("Samsung Note 10", res);
    }
    
    @Test 
    public void getCategory() {
        String res = item.getCategory();
        assertEquals("Smartphone", res);
    }
    
    @Test 
    public void getImage() {
        String res = item.getImage();
        assertEquals("samsung.jpg", res);
    }
    
    @Test
    public void getDescription() {
        String res = item.getDescription();
        assertEquals("This is samsung phone.", res);
    }
    
    @Test
    public void getPrice() {
        double res = item.getPrice();
        assertEquals(1299.99, res, 0.001);
    }
    
    @Test
    public void getQuantity() {
        int res = item.getQuantity();
        assertEquals(10, res, 0.001);
    }
    
    
    @Test
    public void testAddItem() throws SQLException {
        conn = db.openConnection(); //set up connection
        
        try {
            itemManager = new DBItemManager(conn);
        
            // Provide sample data for testing
            String name = "Samsung A9";
            String category = "Smartphone";
            String image = "samsungA9.jpg";
            String description = "This is a samsung A9 phone.";
            double cost = 999.99;
            int quantity = 5;

            // Call the addItem method
            itemManager.addItem(name, category, image, description, cost, quantity);

            // Validate the test results
            // Retrieve the inserted item from the database and compare its properties with the expected values
            Item insertedItem = itemManager.fetchItemByName(name); // Assuming there's a method to retrieve an item by name
            assertNotNull(insertedItem); // Assert that the inserted item is not null
            assertEquals(name, insertedItem.getName());
            assertEquals(category, insertedItem.getCategory());
            assertEquals(image, insertedItem.getImage());
            assertEquals(description, insertedItem.getDescription());
            assertEquals(cost, insertedItem.getPrice(), 0.001); // Assuming a tolerance of 0.001 for floating-point comparison
            assertEquals(quantity, insertedItem.getQuantity());

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    
    
    
    
    
}
    
    
    
    

