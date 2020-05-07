package com.application.weather;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//ADDED
import java.io.*;
import java.net.*;
import java.util.*;
import com.google.gson.*;
import com.google.gson.reflect.*;
/**
 * Servlet implementation class WeatherServlet
 */
@WebServlet("/WeatherServlet")
public class WeatherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeatherServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();
		String LATITUDE = request.getParameter("latitude");
		String LONGITUDE = request.getParameter("longitude");
		String UNIT = request.getParameter("unit");
		String API_KEY = "f8586bffbcc473437b19f7371094fe18";
		String urlString = "http://api.openweathermap.org/data/2.5/find?lat=" + LATITUDE + "&lon=" + 
				LONGITUDE + "&appid=" + API_KEY + "&cnt=1&units=" + UNIT;
		
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
			System.out.println("URL is: " + url);
			
			Map<String, Object> respMap = jsonToMap(result.toString());
			List<Map<String, Object>> list = new ArrayList<>();
			list = (List<Map<String, Object>>) respMap.get("list");
			Map<String, Object> listMap = list.get(0);
			Map<String, Object> mainMap = (Map<String, Object>) listMap.get("main");
			Map<String, Object> serviceMap = new HashMap<>();
			
			serviceMap.put("temperature", mainMap.get("temp"));
			serviceMap.put("feels_like", mainMap.get("feels_like"));
			serviceMap.put("temp_min", mainMap.get("temp_min"));
			serviceMap.put("temp_max", mainMap.get("temp_max"));
			serviceMap.put("humidity", mainMap.get("humidity"));
			serviceMap.put("pressure", mainMap.get("pressure"));
			
			Cuisine cuisine = new Cuisine();
			cuisine.temperature =  mainMap.get("temp").toString();
			cuisine.humidity =  mainMap.get("humidity").toString();
			cuisine.pressure =  mainMap.get("pressure").toString();
			cuisine.snowPred = mainMap.get("snow");
			cuisine.rainPred = mainMap.get("rain");
			cuisine.unit = UNIT;
			
			Clothing clothing = new Clothing();
			clothing.temperature =  mainMap.get("temp").toString();
			clothing.humidity =  mainMap.get("humidity").toString();
			clothing.pressure =  mainMap.get("pressure").toString();
			clothing.snowPred = mainMap.get("snow");
			clothing.rainPred = mainMap.get("rain");
			clothing.unit = UNIT;
			
			Activity activity = new Activity();
			activity.temperature = mainMap.get("temp").toString();
			activity.humidity =  mainMap.get("humidity").toString();
			activity.pressure =  mainMap.get("pressure").toString();
			activity.snowPred = mainMap.get("snow");
			activity.rainPred = mainMap.get("rain");
			activity.unit = UNIT;
			
			//System.out.print("----------- " +unit);
			JSONObject jsonMap=new JSONObject(serviceMap);
			out.print(jsonMap);
			
		} catch(IOException e) {
			System.out.print(e.getMessage());
		} 
	}

	public static Map<String, Object> jsonToMap(String str) {                                     
		Map<String, Object> map = new Gson().fromJson(
				str, new TypeToken<HashMap<String, Object>>() {}.getType()
			);
		return map;
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        doGet(request, response);
	}
}
