package com.application.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
 * Servlet implementation class CityWiseForecast
 */
@WebServlet("/CityWiseForecast")
public class CityWiseForecast extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, String> countries;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CityWiseForecast() {
        super();
        // TODO Auto-generated constructor stub
        countries = new HashMap<String, String>();
		String[] countryCodes = Locale.getISOCountries();

		for (String cc : countryCodes) {
		    // country name , country code map
		    countries.put(new Locale("", cc).getDisplayCountry(), cc.toUpperCase());
//		    System.out.println("Country: " + new Locale("", cc).getDisplayCountry() + "\nCode: " + cc.toUpperCase());
		}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String CITY = request.getParameter("city");
		String STATE = request.getParameter("state");
		String COUNTRY = request.getParameter("country");
		String COUNTRY_CODE = countries.get(COUNTRY);
		String UNIT = request.getParameter("unit");
		String API_KEY = "f8586bffbcc473437b19f7371094fe18";
		String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + CITY + "," + STATE + "," + COUNTRY_CODE + "&appid="
							+ API_KEY + "&units=" + UNIT;
		
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
			Map<String, Object> mainMap = (Map<String, Object>) respMap.get("main");
			Map<String, Object> serviceMap = new HashMap<>();
			
			serviceMap.put("temperature", mainMap.get("temp"));
			serviceMap.put("feels_like", mainMap.get("feels_like"));
			serviceMap.put("temp_min", mainMap.get("temp_min"));
			serviceMap.put("temp_max", mainMap.get("temp_max"));
			serviceMap.put("humidity", mainMap.get("humidity"));
			serviceMap.put("pressure", mainMap.get("pressure"));
			
			JSONObject jsonMap = new JSONObject(serviceMap);
			out.print(jsonMap);
			
		} catch(IOException e) {
			
			System.out.print("HERE" + e.getMessage());
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
