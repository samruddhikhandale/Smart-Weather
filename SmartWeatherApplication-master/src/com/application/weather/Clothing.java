package com.application.weather;


import java.sql.*;

public class Clothing {
	public static String temperature = null;
	public static String humidity = null;
	public static String pressure = null;
	public static Object rainPred = null;
	public static Object snowPred = null;
	public static String unit = "";

	public String getClothing(String username) 
    {
	String rain = "";
	String snow = "";
	double temp = 0.0;
	if (temperature != null) {
		 temp = Double.parseDouble(temperature); //retrieve from api
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
	System.out.println("unit" + unit);
	if(unit.equals("imperial")){
		temp  = ((temp - 32)*5)/9;
		System.out.println("Temp after converting to C");
	}else if ( unit.equals("default")) {
		temp = temp - 273.15;
		System.out.println("Temp after converting to C");
	}
	
	System.out.println("Temp API" + temperature);
	String clothing = "";
	String gender = "";
	 try {
     	Class.forName("com.mysql.jdbc.Driver");  
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smartWeatherdb","root","1234567");  
		PreparedStatement ps = con.prepareStatement("select * from users where email_id=?");
        ps.setString(1, username);
        ResultSet rs =ps.executeQuery();
        if (rs.next()) {
            gender= rs.getString(1);
        }


        ps = con.prepareStatement("select clothing_type, items_to_carry from clothing where gender=? and min_temp <= ? and max_temp >= ? and rain = ? and snow = ?");
        ps.setString(1, gender);
        ps.setDouble(2, temp);
        ps.setDouble(3, temp);
        ps.setString(4, rain);
        ps.setString(5, snow);
        rs = ps.executeQuery();
        while (rs.next()) {
        	clothing = clothing + "Clothing: " + rs.getString(1) + " and " + "Items to Carry: " + rs.getString(2) + " "; // have to make changes in jsp for adjusting the td and tr
        }
     }
     catch(Exception e) {
         e.printStackTrace();
     }
	 return clothing;
    }
}
