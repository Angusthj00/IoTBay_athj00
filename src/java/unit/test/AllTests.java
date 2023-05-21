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
import java.util.ArrayList;


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
    public void testGetId() {
        int res = item.getId();
        assertEquals(20, res);
    }
    
    @Test
    public void testGetName() {
        String res = item.getName();
        assertEquals("Samsung Note 10", res);
    }
    
    @Test 
    public void testGetCategory() {
        String res = item.getCategory();
        assertEquals("Smartphone", res);
    }
    
    @Test 
    public void testGetImage() {
        String res = item.getImage();
        assertEquals("samsung.jpg", res);
    }
    
    @Test
    public void testGetDescription() {
        String res = item.getDescription();
        assertEquals("This is samsung phone.", res);
    }
    
    @Test
    public void testGetPrice() {
        double res = item.getPrice();
        assertEquals(1299.99, res, 0.001);
    }
    
    @Test
    public void testGetQuantity() {
        int res = item.getQuantity();
        assertEquals(10, res, 0.001);
    }
    
    
    @Test
    public void testAddItem() throws SQLException {
        conn = db.openConnection(); //set up connection
        
        try {
            itemManager = new DBItemManager(conn); //create new DBManager instance
        
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
        } finally {
            db.closeConnection(); //close connection!!!!
        }
    }
    

    
    @Test
    public void testUpdateItem() throws SQLException {
        conn = db.openConnection(); // set up connection

        try {
            itemManager = new DBItemManager(conn); // create new DBManager instance

            // Provide sample data for testing
            String originalName = "Samsung A9";
            String newName = "Samsung A9 Plus";
            String newCategory = "Smartphone";
            String newImage = "samsungA9_plus.jpg";
            String newDescription = "This is an updated Samsung A9 Plus phone.";
            double newCost = 1199.99;
            int newQuantity = 8;

            // Call the updateItem method
            itemManager.updateItem(originalName, newName, newCategory, newImage, newDescription, newCost, newQuantity);

            // Retrieve the updated item from the database
            Item updatedItem = itemManager.fetchItemByName(newName); // Assuming there's a method to retrieve an item by name
            assertNotNull(updatedItem); // Assert that the updated item is not null
            assertEquals(newName, updatedItem.getName());
            assertEquals(newCategory, updatedItem.getCategory());
            assertEquals(newImage, updatedItem.getImage());
            assertEquals(newDescription, updatedItem.getDescription());
            assertEquals(newCost, updatedItem.getPrice(), 0.001); // Assuming a tolerance of 0.001 for floating-point comparison
            assertEquals(newQuantity, updatedItem.getQuantity());

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            db.closeConnection();
        }
    }
    
    @Test
    public void testDeleteItem() throws SQLException {
        conn = db.openConnection(); // set up connection

        try {
            itemManager = new DBItemManager(conn); // create new DBManager instance

            // Provide sample data for testing
            String itemName = "Samsung A9 Plus";

            // Call the deleteItem method
            itemManager.deleteItem(itemName);

            // Attempt to retrieve the deleted item from the database
            Item deletedItem = itemManager.fetchItemByName(itemName); // Assuming there's a method to retrieve an item by name

            assertNull(deletedItem); // Assert that the deleted item is null (indicating it was successfully deleted)

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            db.closeConnection();
        }
    }
    
    @Test
    public void testFetchItemsByCategory() throws SQLException {
        conn = db.openConnection(); // set up connection

        try {
            itemManager = new DBItemManager(conn); // create new DBManager instance

            // Test case 1: Searching for items with a specific name and category
            String name1 = "Samsung";
            String category1 = "Smartphone";
            ArrayList<Item> items1 = itemManager.fetchItemsByCategory(name1, category1);
            assertNotNull(items1);
            assertTrue(items1.size() > 0);

            // Test case 2: Searching for items with a specific name and "All" categories
            String name2 = "Apple";
            String category2 = "All";
            ArrayList<Item> items2 = itemManager.fetchItemsByCategory(name2, category2);
            assertNotNull(items2);
            assertTrue(items2.size() > 0);

            // Test case 3: Searching for items with a specific category regardless of the name
            String name3 = "";
            String category3 = "Laptop";
            ArrayList<Item> items3 = itemManager.fetchItemsByCategory(name3, category3);
            assertNotNull(items3);
            assertTrue(items3.size() > 0);

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            db.closeConnection();
        }
    }



    
    
}
    

