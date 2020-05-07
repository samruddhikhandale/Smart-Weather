package com.application.weather;

import java.sql.*;


public class Validate {
	static boolean st =false;
    public static boolean checkUser(String email,String pass) 
    {
        
        try {
        	Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smartWeatherdb","root","1234567");  
			PreparedStatement ps = con.prepareStatement("select * from users where email_id=? and password=AES_ENCRYPT(?, ?)");
            ps.setString(1, email);
            ps.setString(2, pass);
            ps.setString(3, "secret123");
            ResultSet rs =ps.executeQuery();
            st = rs.next();

        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return st;                 
    } 
    public boolean getSt() {
    	return st;
    }
    

}