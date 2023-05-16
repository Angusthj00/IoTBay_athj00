/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.DAO;

import models.Item;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author angus
 */
public class DBItemManager {
    private Statement st;
    
    public DBItemManager(Connection conn) throws SQLException {
        st = conn.createStatement();
    }
    
    //pick particular item by id
    public Item fetchItemById(int id) throws SQLException {
        String fetch = "SELECT * FROM ITEM WHERE itemID = '" + id + "'";
        ResultSet rs = st.executeQuery(fetch);
        
        int itemID = rs.getInt(1);
        String itemName = rs.getString(2);
        String itemCategory = rs.getString(3);
        String itemImage = rs.getString(4);
        String itemDescription = rs.getString(5);
        double itemCost = rs.getDouble(6);
        int itemQuantity = rs.getInt(7);
        
        Item item = new Item(itemID, itemName, itemCategory, itemImage, itemDescription, itemCost, itemQuantity);
        return item;
    }
    
    //find item by name by category in db
    public ArrayList<Item> fetchItemsByCategory(String name, String category) throws SQLException {
        ArrayList<Item> temp = new ArrayList<>();
        ResultSet rs;
        String fetch;
        
        if (category.equals("All")) {
            fetch =  "SELECT * FROM ITEM WHERE LOWER(itemName) LIKE '%" + name + "%'";
        } else {
            fetch = "SELECT * FROM ITEM WHERE LOWER(itemName) LIKE '%" + name + "%' AND itemCategory = '" + category + "';";
        }
        
        rs = st.executeQuery(fetch);

        while (rs.next()) {
            int itemID = rs.getInt(1);
            String itemName = rs.getString(2);
            String itemCategory = rs.getString(3);
            String itemImage = rs.getString(4);
            String itemDescription = rs.getString(5);
            double itemCost = rs.getDouble(6);
            int itemQuantity = rs.getInt(7);
            temp.add(new Item(itemID, itemName, itemCategory, itemImage, itemDescription, itemCost, itemQuantity));
        }
        return temp;
    }
    
    
    
    //fetch all categories and count in the db
    public ArrayList<ArrayList<String>> fetchCategories() throws SQLException {
//        String fetch = "SELECT DISTINCT itemCategory FROM ITEM";
        
        //add an "All" category which technically wasnt stored in db
        String fetchAll = "SELECT COUNT(*) FROM ITEM";
        ResultSet rsCount = st.executeQuery(fetchAll);
        int allCount = 0;
        if (rsCount.next()) {
            allCount = rsCount.getInt(1);
        }
        
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        ArrayList<String> all = new ArrayList<>();
        
        all.add("All");
        all.add(Integer.toString(allCount));
        result.add(all);

        //fetch stored categories and counts
        String fetch = "SELECT itemCategory, COUNT(*) FROM ITEM GROUP BY itemCategory";
        ResultSet rs = st.executeQuery(fetch);
        
        while (rs.next()) {
            String itemCategory = rs.getString(1);
            int count = rs.getInt(2);
            ArrayList<String> temp = new ArrayList<>(); 
            
            temp.add(itemCategory);
            temp.add(Integer.toString(count));
            result.add(temp);
        }
        return result;
    }  
    
    //add an item to db
    public void addItem(String name, String category, String image, String description, double cost, int quantity) throws SQLException {
        st.executeUpdate("INSERT INTO ITEM(itemName, itemCategory, itemImage, itemDescription, itemCost, itemQuantity) " + "VALUES ('" + name + "', '" + category + "', '" + image + "', '" + description + "', '" + cost + "', '" + quantity + "')");
    }
    
    //update an item (all records) in the db
    public void updateItem(String originalName, String name, String category, String image, String description, double cost, int quantity) throws SQLException {
        st.executeUpdate("UPDATE ITEM SET itemName = '" + name + "', itemCategory = '" + category + "', itemImage = '" + image + "', itemDescription = '" + description + "', itemCost = '" + cost + "', itemQuantity = '" + quantity + "' WHERE itemName = '" + originalName + "'");
    }
        
    //delete an item from db
    public void deleteItem(String name) throws SQLException {
        st.executeUpdate("DELETE FROM ITEM WHERE itemName = '" + name + "'");
    }
    
    //fetch all items in db
    public ArrayList<Item> sortItems(String sort, String category) throws SQLException {
        String fetch;

        switch (sort) {
            case "Price low to high":
                if (category.equals("All")) {
                    fetch = "SELECT * FROM ITEM ORDER BY itemCost ASC";
                } else {
                    fetch = "SELECT * FROM ITEM WHERE itemcategory = '" + category + "' ORDER BY itemCost ASC";
                }
                break;
            case "Price high to low":
                if (category.equals("All")) {
                    fetch = "SELECT * FROM ITEM ORDER BY itemCost DESC";
                } else {
                    fetch = "SELECT * FROM ITEM WHERE itemcategory = '" + category + "' ORDER BY itemCost DESC";                    
                }
                break;
            case "In stock low to high":
                if (category.equals("All")) {
                    fetch = "SELECT * FROM ITEM ORDER BY itemQuantity ASC";
                } else {
                    fetch = "SELECT * FROM ITEM WHERE itemcategory = '" + category + "' ORDER BY itemQuantity ASC";
                }
                break;
            case "In stock high to low":
                if (category.equals("All")) {
                    fetch = "SELECT * FROM ITEM ORDER BY itemQuantity DESC";
                } else {
                    fetch = "SELECT * FROM ITEM WHERE itemcategory = '" + category + "' ORDER BY itemQuantity DESC";
                }
                break;
            default:
                if (category.equals("All")) {
                    fetch = "SELECT * FROM ITEM ORDER BY itemCategory ASC";
                } else {
                    fetch = "SELECT * FROM ITEM WHERE itemcategory = '" + category + "' ORDER BY itemCategory ASC";
                }
                break;
        }
            
        ResultSet rs = st.executeQuery(fetch);
        ArrayList<Item> temp = new ArrayList<>();
        
        while (rs.next()) {
            int itemID = rs.getInt(1);
            String itemName = rs.getString(2);
            String itemCategory = rs.getString(3);
            String itemImage = rs.getString(4);
            String itemDescription = rs.getString(5);
            double itemCost = rs.getDouble(6);
            int itemQuantity = rs.getInt(7);
            temp.add(new Item(itemID, itemName, itemCategory, itemImage, itemDescription, itemCost, itemQuantity));
        }
        return temp;
    }
        

}
