/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package face_rec;

import java.sql.*;

/**
 *
 * @author Dansugan
 */
public class DB {
    
    String db = "face_rec";
    String username = "root";
    String password = "";
    Connection con = null;
    Statement stmt;
    ResultSet rs = null;
    PreparedStatement pst=null;
   
    
    public Connection getConnection(){

        try {
            String url = "jdbc:mysql://localhost/" + db;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, username, password);
        } 
        catch (SQLException ex) {
            System.out.println("Error "+ex.getMessage());
        }
        
         catch (ClassNotFoundException ex) {
            System.out.println("Error "+ex.getMessage());
        }
        finally{
        return con;
        }
    }
    
       public ResultSet getResultSet(String query) {
        try {
            getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } 
        
        catch (SQLException ex) {
            System.out.println("Error "+ex.getMessage());
        }
        return rs;
    }

    public int getUpdate(String query) {
        int i = 0;
        try {
            stmt = con.createStatement();
            i = stmt.executeUpdate(query);
        } catch (Exception e) {
        }
        return i;
    }
    
    public PreparedStatement getpst(String sql)
    {
        try {
            getConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            
        } 
        
        catch (SQLException ex) {
            System.out.println("Error "+ex.getMessage());
        }
        return pst; 
    }
    
    public void close()
    {
         try {
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }
}
