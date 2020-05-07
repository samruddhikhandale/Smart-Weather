package com.application.weather;

import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;

import javax.mail.*;
import javax.mail.Authenticator;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Document;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.mail.PasswordAuthentication;
import java.util.*;
import com.google.gson.*;
import com.google.gson.reflect.*;
import com.sun.org.apache.bcel.internal.generic.RETURN;

public class WeatherAlert extends java.lang.Object {
	
	public ArrayList<ArrayList<String>> coordinates = new ArrayList<>();
	
	public String alertMessage = "";
	public void checkTemperature() {
		
		ArrayList<String> allCities = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smartWeatherdb","root","root");  
			
			PreparedStatement ps = con.prepareStatement("select distinct city from users;");
	        ResultSet rs =ps.executeQuery();

	        while (rs.next()) {
	           String city = rs.getString(1); ;
	           allCities.add(city);
	           System.out.print("Users" + allCities);
	        }
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			//latLongs = getLatLongPositions("27606,North Carolina");
			//System.out.println("Latitude: "+latLongs[0]+" and Longitude: "+latLongs[1]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    
		setCoordinates();
		System.out.print("Coord" +coordinates);
		for(String city: allCities){
			String LATITUDE = "" ;
			String LONGITUDE = "";

			for(int i=0 ; i<coordinates.size() ; i++){
				ArrayList<String> temp = new ArrayList<>();
				temp = coordinates.get(i);
				
				if(temp.get(0).equals(city)){
					LATITUDE = temp.get(1);
					LONGITUDE = temp.get(2);
			
				}
			}
			
			String API_KEY = "f8586bffbcc473437b19f7371094fe18";
			
			String urlString = "http://api.openweathermap.org/data/2.5/find?lat=" + LATITUDE + "&lon=" + 
					LONGITUDE + "&appid=" + API_KEY + "&cnt=1&units=metric";
			try {
				StringBuilder result = new StringBuilder();
				URL url = new URL(urlString);
				URLConnection conn = url.openConnection();
				BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line;
				while((line = rd.readLine()) != null) {
					result.append(line);
				}
				rd.close();			
				System.out.println(url);
				Map<String, Object> respMap = jsonToMap(result.toString());
				List<Map<String, Object>> list = new ArrayList<>();
				list = (List<Map<String, Object>>) respMap.get("list");
				Map<String, Object> listMap = list.get(0);
				Map<String, Object> mainMap = (Map<String, Object>) listMap.get("main");
				
				System.out.println("Temp " +city+ mainMap.get("temp")) ;
				double temperature = (Double) mainMap.get("temp");
				
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				
				Main obj = new Main();
				
				alertMessage = "Alert! Alert! Alert! \n";
				if (temperature > 10){
					alertMessage += "Too sunny! at " +city; 
					alertMessage += "\nReported at " + dtf.format(now);
					Main.timerSeconds = 9999999;
					sendAlert(city);
				}
				
				if (temperature < 0){
					alertMessage += "Too cold! at "+city;  
					alertMessage += "\nReported at " + dtf.format(now);
					Main.timerSeconds = 9999999;
					sendAlert(city);
				}
				
			} catch(IOException e) {
				System.out.print(e.getMessage());
			} 
			
		}
		
	}
	
	private void setCoordinates() {
		ArrayList<String> temp = new ArrayList<>();
		temp.add("Raleigh");
		temp.add("35.779591");
		temp.add("-78.638176");
		coordinates.add(temp);
		ArrayList<String> temp1 = new ArrayList<>();
		temp1.add("Cary");
		temp1.add("35.791470");
		temp1.add("-78.781143");
		coordinates.add(temp1);
		
		ArrayList<String> temp2 = new ArrayList<>();
		temp2.add("Durham");
		temp2.add("35.996948");
		temp2.add("-78.899017");
		coordinates.add(temp2);
		
	}

	public void sendAlert(String city){
		
		Properties properties = new Properties();
		
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		String account = "seproject.orangeteam@gmail.com";
		String password = "seproject123";
		
		 Session session = Session.getDefaultInstance(properties,  
				    new javax.mail.Authenticator() {  
				      protected PasswordAuthentication getPasswordAuthentication() {  
				    return new PasswordAuthentication(account, password); 
				      }  
				    });  
		
		ArrayList<String> users = new ArrayList<String>();
		users = getUsersList(city); 
		System.out.print("Users" +users);
		
	
		try {
			for(String email_id : users){
				Message message = prepareMessage(session,account,email_id);
				Transport.send(message);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private ArrayList<String> getUsersList(String city) {
		ArrayList<String> users = new ArrayList<String>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smartWeatherdb","root","root");  
			PreparedStatement ps = con.prepareStatement("select email_id from users where city=?");
			ps.setString(1, city);
	        ResultSet rs =ps.executeQuery();
	       
	      //  System.out.println(rs.getString(1));
	        while (rs.next()) {
	           String email = rs.getString(1); ;
	           users.add(email);
	           System.out.print("Users" +users);
	        }
	        return users;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		return null;
	}

	public Message prepareMessage(Session session, String account, String recipient){
		
		
		try {
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(account));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			message.setSubject("Weather Alert Message");
			message.setText(alertMessage);
			return message;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
	}
	
	public static Map<String, Object> jsonToMap(String str) { 
		Map<String, Object> map = new Gson().fromJson(
				str, new TypeToken<HashMap<String, Object>>() {}.getType()
			);
		return map;
	}
	 public static String[] getLatLongPositions(String address) throws Exception
	  {
		String apiKey = "AIzaSyAIiNqmFtIRyTP1Q901wiE9456ebFaS2tg"; 
	    int responseCode = 0;
	    String api = "http://maps.googleapis.com/maps/api/geocode/xml?address=" + URLEncoder.encode(address, "UTF-8") + "&sensor=true"+ "key="+apiKey;
	    URL url = new URL(api);
	    HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
	    httpConnection.connect();
	    responseCode = httpConnection.getResponseCode();
	    if(responseCode == 200)
	    {
	      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();;
	      Document document = builder.parse(httpConnection.getInputStream());
	      XPathFactory xPathfactory = XPathFactory.newInstance();
	      XPath xpath = xPathfactory.newXPath();
	      XPathExpression expr = xpath.compile("/GeocodeResponse/status");
	      String status = (String)expr.evaluate(document, XPathConstants.STRING);
	      if(status.equals("OK"))
	      {
	         expr = xpath.compile("//geometry/location/lat");
	         String latitude = (String)expr.evaluate(document, XPathConstants.STRING);
	         expr = xpath.compile("//geometry/location/lng");
	         String longitude = (String)expr.evaluate(document, XPathConstants.STRING);
	         return new String[] {latitude, longitude};
	      }
	      else
	      {
	         throw new Exception("Error from the API - response status: "+status);
	      }
	    }
	    return null;
	  }
}
	
	