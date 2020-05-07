package com.application.weather;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class Activity {

	public static String temperature = null;
	public static String humidity = null;
	public static String pressure = null;
	public static Object rainPred = null;
	public static Object snowPred = null;
	public static String unit = "";
	
	
	public ArrayList<String> getActivityRecommendation(String username){
		
		//System.out.print("****Rain");
		if(username != null && !username.equals("")){
			System.out.print("Username" +username);
			ArrayList<String> activity = new ArrayList<>();
			
			String gender = "";
			int age = 0;
			String birthdate = "";
			double temp = 12; 
			String rain = "";		
			String snow = "";
			String city = "";
			String activties = "";
			
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
			System.out.print("Unit" + unit);
			if(unit.equals("imperial")){
				temp  = ((temp - 32)*5)/9;
				System.out.println("Temp after converting to C");
			}else if ( unit.equals("default")) {
				temp = temp - 273.15;
				System.out.println("Temp after converting to C");
			}
			
			System.out.print("Temp API" + temperature);
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smartWeatherdb","root","1234567");  
				PreparedStatement ps = con.prepareStatement("select gender,city,birth_date from users where email_id=?");
				ps.setString(1, username);
		        ResultSet rs =ps.executeQuery(); 
		       
		        if (rs.next()) {
		           gender = rs.getString(1); ;
		           city = rs.getString(2);
		           birthdate = rs.getString(3);
		           if (birthdate != null) {
		               String year = String.valueOf(birthdate).split("-")[0];
		               age = 2020 - Integer.valueOf(year);
		           }
		        }
		        
		        ps =  con.prepareStatement("select activities from activities where gender=? and min_age <= ? and max_age >= ? and min_temp <= ? and max_temp >= ? and rain = ? and snow = ? and city = ?;");
		        ps.setString(1, gender);
		        ps.setInt(2, age);
		        ps.setInt(3, age);
		        ps.setInt(4,  (int) temp);
		        ps.setInt(5,  (int) temp);
		        ps.setString(6, rain);
		        ps.setString(7, snow);
		        ps.setString(8, city);
		        
		        rs = ps.executeQuery();
		        
		        while(rs.next()){
		        	String activityTemp = rs.getString(1);
		        	activity.add(activityTemp);
		        }
		        
		        return activity;
		      
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			
			
			return activity;
		}
		
		return null;
	}
	
}
