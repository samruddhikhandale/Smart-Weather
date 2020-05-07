<%@ page language="java" contentType="text/html; charset=ISO-8859-1" import="org.json.*"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.ResourceBundle" %>	
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<meta charset="ISO-8859-1">
<title>Smart Weather Application</title>
</head>
<%  ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages",request.getLocale());   %>
<body>
		<p><%=resourceBundle.getString("Temperature")%>: <span id = "temp"></span></p>
		<p><%=resourceBundle.getString("FeelsLike")%>: <span id = "feels_like"></span> </p>
		<p><%=resourceBundle.getString("TemperatureMin")%>: <span id = "temp_min"></span> </p>
		<p><%=resourceBundle.getString("TemperatureMax")%>: <span id = "temp_max"></span> </p>
		<p><%=resourceBundle.getString("Humidity")%>: <span id = "hum"></span> </p>

</body>

<script type="text/javascript">

	window.onload = function() {
		geoFindMe();
	};
	
	function geoFindMe() {
	  
	  function success(position) {
	    const latitude  = position.coords.latitude;
	    const longitude = position.coords.longitude;
	    
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
           var latitude = lat, longitude = lon;
           $.ajax({
             url: 'WeatherServlet',
             type: 'GET',
             dataType: "json",
             data: {
            	 	"latitude" : latitude,
            	 	"longitude" : longitude 
             		},
             success: function(data) {
             	alert("Retrieved latest weather data");
             	document.getElementById('temp').innerHTML = data.temperature;
             	document.getElementById('feels_like').innerHTML = data.feels_like;
             	document.getElementById('temp_min').innerHTML = data.temp_min;
             	document.getElementById('temp_max').innerHTML = data.temp_max;
             	document.getElementById('hum').innerHTML = data.humidity;
             	
             	}
           });
    }
	
	window.setInterval(function(){
		  geoFindMe();
		}, 60000);

	
</script>
</html>