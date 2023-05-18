/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import jakarta.servlet.http.Part;

/**
 *
 * @author angus
 */
public class Validator implements Serializable {
    private String itemNamePattern = "^[A-Za-z0-9\\s]+$";
    private String itemCategoryPattern = "^[A-Za-z\\s]+$";
//    private String itemImagePattern = "^.+\\.(jpg|jpeg|png)$";
    private String itemDescriptionPattern = "^[A-Za-z0-9\\s.,\\-!@#$%&*'\":?<>()]*";
    private String itemCostPattern = "^[1-9][0-9]*\\.[0-9]{2}$";
    private String itemQuantityPattern = "^[1-9][0-9]*$"; //eg. 0, 1, 2, 10, 12345, ....
    
    
    public Validator() { 
    }
    
    public boolean validate(String pattern, String input) {
        Pattern regEx = Pattern.compile(pattern);
        Matcher match = regEx.matcher(input);
        return match.matches();
    }
    
    public boolean checkEmpty(String name, String category, String cost, String quantity) {
        return name.isEmpty() || category.isEmpty() || cost.isEmpty() || quantity.isEmpty();
    }
    
    public boolean validateItemName(String name) {
        return validate(itemNamePattern, name);
    }
    
    public boolean validateItemCategory(String category) {
        return validate(itemCategoryPattern, category);
    }
    
//    public boolean validateItemImage(String image) {
//        return validate(itemImagePattern, image);
//    }
    
    public boolean validateItemDescription(String description) {
        return validate(itemDescriptionPattern, description);
    }
    
    public boolean validateItemCost(String cost) {
        return validate(itemCostPattern, cost);
    }
    
    public boolean validateItemQuantity(String quantity) {
        return validate(itemQuantityPattern, quantity);
    }
    
//    public void clear(HttpSession session) {
//        //session.removeAttribute("addItemErr");
//    }
    
    
    
//    Charlie Huang's
    
    private static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";
    private static final String ADDRESS_PATTERN = ".+";

    public boolean validateShipmentDate(String date) {
        Pattern pattern = Pattern.compile(DATE_PATTERN);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    public boolean validateShipmentAddress(String address) {
        Pattern pattern = Pattern.compile(ADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(address);
        return matcher.matches();
    }

    public boolean checkShipmentEmpty(String id, String method, String date, String address) {
        return id.isEmpty() || method.isEmpty() || date.isEmpty() || address.isEmpty();
    }

}
