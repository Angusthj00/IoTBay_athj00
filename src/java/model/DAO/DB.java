/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.DAO;

import java.sql.Connection;
/**
 *
 * @author angus
 */
public abstract class DB {
    protected String url = "jdbc:sqlite:C:/Users/angus/sqliteDB.db";
    protected String driver = "org.sqlite.JDBC";
    protected String username = "username";
    protected String password = "password";
    protected Connection conn;
}
