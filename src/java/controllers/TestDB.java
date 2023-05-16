/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.sql.*;
import java.util.*;
import java.util.logging.*;
import models.Item;
import model.DAO.*;

/**
 *
 * @author angus
 */

public class TestDB {
    private static Scanner in = new Scanner(System.in);
    private DBConnector connector;
    private Connection conn;
    private DBItemManager db;
    
    public static void main(String[] args) throws SQLException {
        (new TestDB()).selectItem();
//        Validator validator = new Validator();       
//        if (!validator.validateItemCategory("ABC")) {
//            System.out.println("Invalid category");
//        } else {
//            System.out.println("Valid category");
//        }    
    }
    
    public TestDB() {
        try {
            connector = new DBConnector();
            conn = connector.openConnection();
            db = new DBItemManager(conn);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(TestDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private char readChoice() {
        System.out.println("Operation CRUDS or * to exit: ");
        return in.nextLine().charAt(0);
    }
    
    private void runQueries() throws SQLException {
        char c;
        while ((c = readChoice()) != '*') {
            switch (c) {
                case 'C':
                    testAdd();
                    break;
                case 'R':
                    testRead();
                    break;
                case 'U':
                    //testUpdate();
                    break;
                case 'D':
                    //testDelete();
                    break;
                case 'S':
                    //showAll(); //deleted**
                    break;
                default:
                    System.out.println("Unknown command");
                    break;
            }
        }
    }
    
    private void testAdd() {
        System.out.println("Item name: ");
        String name = in.nextLine();
        System.out.println("Category: ");
        String category = in.nextLine();
        System.out.println("Image: ");
        String image = in.nextLine();
        System.out.println("Description: ");
        String description = in.nextLine();
        System.out.println("Cost: ");
        double cost = in.nextDouble();
        in.nextLine(); //consume new line
        System.out.println("Quantity: ");
        int quantity = in.nextInt();
        in.nextLine(); //consume new line
        try {
            db.addItem(name, category, image, description, cost, quantity);
        } catch (SQLException ex) {
            Logger.getLogger(TestDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Item is added to the database");
    }
    
    private void testRead() throws SQLException {
        System.out.println("Item name: ");
        String name = in.nextLine();
        System.out.println("Item category: ");
        String category = in.nextLine();
        
        ArrayList<Item> items = db.fetchItemsByCategory(name, category);
        
        if (items != null) {
            System.out.println("ITEMS TABLE: ");
            items.stream().forEach((item) -> {
                System.out.printf("%-20s %-20s %-20s %-100s %-20s %-20s \n", item.getName(), item.getCategory(), item.getImage(), item.getDescription(), item.getPrice(), item.getQuantity());
            });
            System.out.println();
        } else {
            System.out.println("Item does not exist.");
        }
    }
    
//    private void testUpdate() throws SQLException {
//        System.out.println("Item name: ");
//        String nametoedit = in.nextLine();
//        
//        Item item = db.findItem(name);
//        
//        try {
//            if (item != null) {
//                System.out.println("Item name: ");
//                name = in.nextLine();
//                System.out.println("Category: ");
//                String category = in.nextLine();
//                System.out.println("Image: ");
//                String image = in.nextLine();
//                System.out.println("Description: ");
//                String description = in.nextLine();
//                System.out.println("Cost: ");
//                double cost = in.nextDouble();
//                in.nextLine(); //consume new line
//                System.out.println("Quantity: ");
//                int quantity = in.nextInt();
//                in.nextLine(); //comsume new line
//                
//                db.updateItem(nametoedit, name, category, image, description, cost, quantity);
//            } else {
//                System.out.println("Item does not exist");
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(TestDB.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
//    private void testDelete() throws SQLException {
//        System.out.println("Item name: ");
//        String name = in.nextLine();
//        
//        Item item = db.findItem(name);
//        
//        try {
//            if (item != null) {
//                db.deleteItem(name);
//            } else {
//                System.out.println("Item does not exist.");
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(TestDB.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    
    private void showCategories() {
        try {
            ArrayList<ArrayList<String>> categories = db.fetchCategories();
            System.out.println("Categories and count: ");
            categories.stream().forEach((category) -> {
                System.out.println(category);
            });
            System.out.println();
        } catch (SQLException ex) {
            Logger.getLogger(TestDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void sortItems() {
        try {
            ArrayList<Item> items = db.sortItems("In stock low to high", "Laptop");
            System.out.println("ITEMS TABLE: ");
            items.stream().forEach((item) -> {
                System.out.printf("%-20s %-20s %-20s %-100s %-20s %-20s \n", item.getName(), item.getCategory(), item.getImage(), item.getDescription(), item.getPrice(), item.getQuantity());
            });
            System.out.println();
        } catch (SQLException ex) {
            Logger.getLogger(TestDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void selectItem() {
        try {
            Item item = db.fetchItemById(116);
            System.out.println("ITEM TABLE: ");
            System.out.printf("%-10s %-20s %-20s %-20s %-100s %-20s %-20s \n", item.getId(), item.getName(), item.getCategory(), item.getImage(), item.getDescription(), item.getPrice(), item.getQuantity());
            System.out.println();
        } catch (SQLException ex) {
            Logger.getLogger(TestDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    
}
