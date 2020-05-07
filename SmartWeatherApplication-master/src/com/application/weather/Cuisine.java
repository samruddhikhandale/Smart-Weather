package com.application.weather;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sun.javafx.collections.MappingChange.Map;

public class Cuisine {
	public static String temperature = null;
	public static String humidity = null;
	public static String pressure = null;
	public static Object rainPred = null;
	public static Object snowPred = null;
	public static String unit = "";
	
	public String getCuisine(String username) 
    {
	String rain = ""; //calculate on basis of humidity and pressure
	String city = "";
	String snow = ""; //calculate on basis of humidity and pressure
	double temp = 0.0;
	if (temperature != null) {
		 temp = Double.parseDouble(temperature);
	}
	
	if (rainPred != null){
		rain = "yes";
	}else{
		rain = "no";
	}
	
	if(snowPred != null){
		snow = "yes";
	}else {
		snow = "no";
	}
	
	if(unit.equals("imperial")){
		temp  = ((temp - 32)*5)/9;
		System.out.println("Temp after converting to C");
	}else if ( unit.equals("default")) {
		temp = temp - 273.15;
		System.out.println("Temp after converting to C");
	}
	
	System.out.println("Temp API" + temperature);
	int age = -1;
	String cuisine_type = "";
	String fav_cuisine_1 = "";
	String fav_cuisine_2 = "";
	String fav_cuisine_3 = "";
	Date birthdate = null;
	 try {
     	Class.forName("com.mysql.jdbc.Driver");  
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smartWeatherdb","root","1234567");  
		PreparedStatement ps = con.prepareStatement("select birth_date, city, fav_cuisine_1, fav_cuisine_2, fav_cuisine_3 from users where email_id=?");
        ps.setString(1, username);
        ResultSet rs =ps.executeQuery();
        if (rs.next()) {
        	birthdate = rs.getDate(1);
            city= rs.getString(2);
            fav_cuisine_1 = rs.getString(3);
            fav_cuisine_2 = rs.getString(4);
            fav_cuisine_3 = rs.getString(5);
        }
        if (birthdate != null) {
            String year = String.valueOf(birthdate).split("-")[0];
            age = 2020 - Integer.valueOf(year);
        }
        

        ps = con.prepareStatement("select cuisine_type, restaurants from cuisine where min_age <= ? and max_age >= ? and min_temp <= ? and max_temp >= ? and rain = ? and snow = ? and city = ? and (cuisine_type = ? or  cuisine_type = ? or cuisine_type = ?)");
        ps.setInt(1, age);
        ps.setInt(2, age);
        ps.setDouble(3, temp);
        ps.setDouble(4, temp);
        ps.setString(5, rain);
        ps.setString(6, snow);
        ps.setString(7, city);
        ps.setString(8, fav_cuisine_1);
        ps.setString(9, fav_cuisine_2);
        ps.setString(10, fav_cuisine_3);
        rs = ps.executeQuery();
        while (rs.next()) {
        	cuisine_type = cuisine_type + "Cuisine_type: " + rs.getString(1) + " and " + "Restaurants: " + rs.getString(2) + " "; // have to make changes in jsp for adjusting the td and tr
        }
     }
     catch(Exception e) {
         e.printStackTrace();
     }
	 return cuisine_type;
    }
}
