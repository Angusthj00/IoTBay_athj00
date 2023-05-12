/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author angus
 */
public class DBConnector extends DB {
    //build a connection instance with the database using JDBC driver
    public DBConnector() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        conn = DriverManager.getConnection(url, username, password); 
    }
    
    //return the conn instance to be used in DBManager (to execute SQL queries)
    public Connection openConnection() {
        System.out.println("Connection has been established");
        return this.conn;
    }
    
    //important*** close the conn with the database after executing the queries
    public void closeConnection() throws SQLException {
        this.conn.close();
    }
}
