package com.application.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class HourlyForecastServlet
 */
@WebServlet("/HourlyForecastServlet")
public class HourlyForecastServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HourlyForecastServlet() {
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
		String urlString = "https://api.openweathermap.org/data/2.5/onecall?lat=" + LATITUDE + "&lon=" + 
				LONGITUDE + "&appid=" + API_KEY + "&units=" + UNIT;
		
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
			List<LinkedTreeMap<String, Object>> list = new ArrayList<>();
			list = (List<LinkedTreeMap<String, Object>>) respMap.get("hourly");
			int counter = 0;
			
			LinkedTreeMap<String, Object> serviceMap = new LinkedTreeMap<>();
			
			for(LinkedTreeMap<String, Object> forecast : list) {
				LinkedTreeMap<String, Object> thisHour = new LinkedTreeMap<String, Object>();
				thisHour.put("temp", forecast.get("temp"));
				thisHour.put("feels_like",forecast.get("feels_like"));
				counter++;
				serviceMap.put("hour" + counter, thisHour);
				if(counter == 24) {
					break;
				}
			}
			
			Activity activity = new Activity();
			activity.unit = UNIT;
			
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
