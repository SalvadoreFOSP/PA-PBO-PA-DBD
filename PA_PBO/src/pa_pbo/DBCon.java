/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pa_pbo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBCon {
    
    private final String DB_URL = "jdbc:mysql://localhost:3306/lurah";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "";
            
    public Connection create_connection() throws SQLException {
        
        Connection conn = null;
        
        try{
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
        
        return conn;
    }  
    
    
    }