<%@ page import="java.util.*" %>
<%@ page import="java.util.ResourceBundle" %>	
<%@ page import="com.application.weather.Cuisine" %>
<%@ page import="com.application.weather.Clothing" %>
<%@ page import="com.application.weather.Validate" %>
<%@ page import="com.application.weather.Activity" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Smart Weather Application</title>
<style>
.button {
	background-color: #008CBA;;
	border: none;
	color: white;
	padding: 10px 25px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	margin: 4px 2px;
	cursor: pointer;
}
.searchbutton{
	background-color: #008CBA;;
	border: none;
	color: white;
	padding: 4px 4px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 12px;
	margin: 4px 2px;
	cursor: pointer;
}
.main-wrap{
  display: flex;
  flex-direction: row;
}
.main-wrap > div{
  flex: 1;
}
.container img {
  width: 100%;
}
table, th, td {
  border: 1px solid black;
  padding: 10px;
}

</style>
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="//geodata.solutions/includes/countrystatecity.js"></script>
<link href='http://fonts.googleapis.com/css?family=Oleo+Script' rel='stylesheet' type='text/css'>


</head>
<body>
<%  ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages",request.getLocale());   %>

<% 
	String url = "http://localhost:8081/SmartWeatherApplication/";
	String signUp = url +"signUp.jsp";
	String login = url +"login.jsp";
	String logout = url +"home.jsp";
	Validate val = new Validate();
	boolean loggedIn = val.getSt();
	if (session.getAttribute("username") == "") {
		loggedIn = false; // set this back to false when the user has logged out
	}
	String username = (String)session.getAttribute("username");
	Clothing clothing = new Clothing();
	Cuisine cuisine = new Cuisine();
	
	
	String clothingOpt = "";
	String cuisineOpt = "";
	
	
	if (username != "") {
		clothingOpt = clothing.getClothing(username);
		cuisineOpt = cuisine.getCuisine(username);
		
}

%>
	<h1 align="center"><%=resourceBundle.getString("title") %></h1>
	<br>
	<br>
	<div style="position: absolute; right: 0; top: 0; text-align: right">
		
		<%
			if(loggedIn == false){
		%>
		
		<input type="button" class="button" value="<%=resourceBundle.getString("SignUp") %>"
			onClick="JavaScript:window.location='<%= signUp %>';"> 
		<input type="button" class="button" value="<%=resourceBundle.getString("login") %>"
			onClick="JavaScript:window.location='<%= login %>';">
			
		<% } else { %>	
		<input type="button" class="button" value="<%=resourceBundle.getString("logout") %>"
			onClick="JavaScript:window.location='<%= logout %>';">
		<% session.setAttribute("username","");} %>
	</div>
	
	<form id="cityForecast" onsubmit="cityWiseForecast()">
		<div align="center">
			<select name="country" class="countries" id="countryId">
				<option value=""><%=resourceBundle.getString("country") %></option>
			</select> <select name="state" class="states" id="stateId">
				<option value=""><%=resourceBundle.getString("state") %></option>
			</select> <select name="city" class="cities" id="cityId">
				<option value=""><%=resourceBundle.getString("city") %></option>
			</select>
			<input class="searchbutton" type="submit" value="<%=resourceBundle.getString("Search") %>" />
			</select> 
			
			<select id="unit" onchange = "unitModified()">
				<option value="C"><%=resourceBundle.getString("Celcius") %></option>
				<option value="F">Farheneit</option>
				<option value="K">Kelvin</option>
			</select>
		</div>
	</form>
	<div align="center">
		<a href="javascript:void(0);" onclick="geoFindMe();">Locate Me</a>
	</div>
	<br>
	<br>
	<%
	if(loggedIn == false)
	{
%>

	<div align="center" style="background-color: #33E3FF; color: black; padding: 20px;;"><%=resourceBundle.getString("TemperatureD") %>
		<div align="left">	
			<p><%=resourceBundle.getString("Temperature")%>: <span id = "temp"></span></p>
			<p><%=resourceBundle.getString("FeelsLike")%>: <span id = "feels_like"></span> </p>
			<p><%=resourceBundle.getString("TemperatureMin")%>: <span id = "temp_min"></span> </p>
			<p><%=resourceBundle.getString("TemperatureMax")%>: <span id = "temp_max"></span> </p>
			<p><%=resourceBundle.getString("Humidity")%>: <span id = "hum"></span> </p>
		</div>
		
		<div>		
			<button onclick="hourlyForecast()"> <%=resourceBundle.getString("Forecast")%> </button>
		</div>
		<br>
		<div>
			<table id = "hourly_forecast">
				<tr>
					<th><%=resourceBundle.getString("Time")%></th>
					<th><%=resourceBundle.getString("Temperature")%></th>
					<th><%=resourceBundle.getString("FeelsLike")%></th>
				</tr>
				
			
			</table>
		</div>		
	</div>

	<% 
} else{
	

%>

<div class="main-wrap">
  <div class="container" align="center" style="background-color: #33E3FF; color: black; padding: 20px;"><%=resourceBundle.getString("TemperatureD") %>
  	<div align="left">	
		<p>Temperature: <span id = "temp"></span></p>
		<p>Feels Like: <span id = "feels_like"></span> </p>
		<p>Temperature Min: <span id = "temp_min"></span> </p>
		<p>Temperature Max: <span id = "temp_max"></span> </p>
		<p>Humidity: <span id = "hum"></span> </p>
	</div>
	
	<div>		
			<button onclick="hourlyForecast()"> See 24 Hour Forecast in your Location </button>
	</div>
	<br>
	<div>
		<table id = "hourly_forecast">
			<tr>
				<th><%=resourceBundle.getString("Time")%></th>
				<th><%=resourceBundle.getString("Temperature")%></th>
				<th><%=resourceBundle.getString("FeelsLike")%></th>
			</tr>
			
		
		</table>
	</div>	
		
  </div>
  <div align="center">
    <table>
  		<tr>
					<button onclick="clothingRecommendation()"><%=resourceBundle.getString("ClothingRecommendation") %></button>
		</tr>
		<tr>			<p id="clothing"></p> </tr>
		<tr>
					<button onclick="foodRecommendation()"><%=resourceBundle.getString("FoodRecommendation") %></button>
		</tr>
		<tr>			<p id="food"></p> </tr>
		<tr>
					<button onclick="activityRecommendation()"><%=resourceBundle.getString("ActivityRecommendation") %></button>
		</tr>
		<tr>			<p id="activity"></p> </tr>

					<script>
						function clothingRecommendation() {
							document.getElementById("clothing").innerHTML = '<%= clothingOpt %>';
						}
						function foodRecommendation() {
							document.getElementById("food").innerHTML = '<%= cuisineOpt %>';
						}
						function activityRecommendation() {
							<% ArrayList<String> activitiesOpt = new ArrayList<String>();
							Activity activity = new Activity();
							if (username != null) {
								activitiesOpt = activity.getActivityRecommendation(username);
							}
							%>
							document.getElementById("activity").innerHTML = "<%= activitiesOpt%>";
						}
					</script>
					
  		
  	</table>
  </div>

</div>

	
	<%
}
%>

<script type="text/javascript">

	var userLatitude;
	var userLongitude;

	window.onload = function() {
		geoFindMe();
	};
	
	$(document).ready(function() {
	    $("#hourly_forecast").hide();
	});
	
	var form = document.getElementById("cityForecast");
	
	function handleForm(event) { 
		event.preventDefault(); 
	} 
	
	form.addEventListener('submit', handleForm);
	
	function geoFindMe() {
	  
	  function success(position) {
	    const latitude  = position.coords.latitude;
	    const longitude = position.coords.longitude;
	    
	    userLatitude = latitude;
	    userLongitude = longitude;
	    
	    sendLocationData(latitude, longitude);
	  }

	  function error() {
	    alert('Unable to retrieve your location. Please insert it manually.');
	  }

	  if (!navigator.geolocation) {
	    alert('Geolocation is not supported by your browser. Please insert it manually.');
	  } else {
	    navigator.geolocation.getCurrentPosition(success, error);
	  }

	}
	
    function sendLocationData(lat, lon) { 
    	var unit = unitConversion();
        var latitude = lat, longitude = lon;
        
        $.ajax({
          url: 'WeatherServlet',
          type: 'GET',
          dataType: "json",
          data: {
         	 	"latitude" : latitude,
         	 	"longitude" : longitude, 
         	 	"unit" : unit
          },
          success: function(data) {
	          	document.getElementById('temp').innerHTML = data.temperature;
	          	document.getElementById('feels_like').innerHTML = data.feels_like;
	          	document.getElementById('temp_min').innerHTML = data.temp_min;
	          	document.getElementById('temp_max').innerHTML = data.temp_max;
	          	document.getElementById('hum').innerHTML = data.humidity;
          }
        });
    }
	
    function hourlyForecast() {
    	var unit = unitConversion();
    	$.ajax({
            url: 'HourlyForecastServlet',
            type: 'GET',
            dataType: "json",
            data: {
           	 	"latitude" : userLatitude,
           	 	"longitude" : userLongitude,
           	 	"unit" : unit
            },
            success: function(data) {
	            		            	
            	var date = new Date();
            	var currentHour = date.getHours();
            	
            	var table = document.getElementById("hourly_forecast");
            	var sortedbyKeyJSONArray = sortByKey(data);
            	var hour = 1;
            	
            	for(var hourlyDetails in sortedbyKeyJSONArray) {
            		var row = table.insertRow(hour);
            		
            		var hour_cell = row.insertCell(0);
            		var temperature_cell = row.insertCell(1);
            		var feels_like_cell = row.insertCell(2);

            		hour_cell.innerHTML = "" + (currentHour + hour) % 24 + ":00";
            		temperature_cell.innerHTML = sortedbyKeyJSONArray[hourlyDetails][1].temp;
            		feels_like_cell.innerHTML = sortedbyKeyJSONArray[hourlyDetails][1].temp;
            		
            		hour++;
           		}             	
            }
          });
    	$("#hourly_forecast").show();
    }
    
    function sortByKey(jsObj){
        var sortedArray = [];

        for(var key in jsObj) {
        	var stringHour = key.split("hour");
			var hour = parseInt(stringHour[1]);
            sortedArray.push([key, jsObj[key]]);
        }

        sortedArray.sort(function(a, b){
		    return a[0].split("hour")[1] - b[0].split("hour")[1];
		  });
        
        return sortedArray;
    }
    
    function cityWiseForecast() { 
    	var cityBox = document.getElementById("cityId");
    	var city = cityBox.options[cityBox.selectedIndex].text;
    	var stateBox = document.getElementById("stateId");
    	var state = stateBox.options[stateBox.selectedIndex].text;
    	var countryBox = document.getElementById("countryId");
    	var country = countryBox.options[countryBox.selectedIndex].text;
    	var unit = unitConversion();
    	
        $.ajax({
          url: 'CityWiseForecast',
          type: 'GET',
          dataType: "json",
          data: {
         	 	"city" : city,
         	 	"state" : state,
         	 	"country" : country,
         	 	"unit" : unit
          		},
          success: function(data) {
          	console.log(data.temperature);
          	document.getElementById('temp').innerHTML = data.temperature;
          	document.getElementById('feels_like').innerHTML = data.feels_like;
          	document.getElementById('temp_min').innerHTML = data.temp_min;
          	document.getElementById('temp_max').innerHTML = data.temp_max;
          	document.getElementById('hum').innerHTML = data.humidity;
          	
          	}
        });
 	}
    
    function unitConversion() {
    	var unit = document.getElementById("unit");
    	var temperature_unit = unit.options[unit.selectedIndex].text;
    	
    	if(temperature_unit == "Farheneit") {
    		return "imperial";
    	} else if(temperature_unit == "Kelvin") {
    		return "default";
    	} else {
    		return "metric";
    	}
    	
    }
    
	window.setInterval(function() {
		  geoFindMe();
		}, 120000);

	
</script>
</body>
</html>