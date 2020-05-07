<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    import="java.util.*,java.lang.*" pageEncoding="ISO-8859-1"%>
     <%@ page import="java.util.ResourceBundle"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign Up</title>
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

.td{
	width:300px;
}
</style>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script> 
<script src="//geodata.solutions/includes/countrystatecity.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<%  ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages",request.getLocale());   %>

<%
	String url = "http://localhost:8080/SmartWeatherApplication/";
	String home = url +"home.jsp";
	String login = url +"login.jsp";
	String[] cuisines = {"", "indian", "italian", "japanese"};
%>

<div align="center">

<div  style="position: absolute; right: 0; top: 0; text-align: right">
	<input type="button" class="button" class="fa fa-home" value="<%=resourceBundle.getString("home") %>" onClick="JavaScript:window.location='<%= home %>';">
	<input type="button" class="button" value="<%=resourceBundle.getString("login") %>" onClick="JavaScript:window.location='<%= login %>';">
</div>
<h1> <%=resourceBundle.getString("signUpTitle") %></h1>
<form action="SignUpServlet" method="post"> <!-- Add action -->
			<table style="with: 50%">
				<tr>
					<td><%=resourceBundle.getString("FirstName") %></td>
					<td><input type="text" name="first_name" /></td>
				</tr>
				<tr>
					<td><%=resourceBundle.getString("LastName") %></td>
					<td><input type="text" name="last_name" /></td>
				</tr>
				<tr>
					<td><%=resourceBundle.getString("emailID") %></td>
					<td><input type="text" name="email" /></td>
				</tr>
				<tr>
					<td><%=resourceBundle.getString("contact") %> No</td>
					<td><input type="text" name="contact" /></td>
				</tr>
				<tr>
					<td><%=resourceBundle.getString("Gender") %></td>
					<td><input type="radio" id="male" value="male" name="gender"> <%=resourceBundle.getString("Male") %>
					<input type="radio" id="female" value="female" name="gender" /> <%=resourceBundle.getString("Female") %>
					<input type="radio" id="other" value="other" name="gender" /> <%=resourceBundle.getString("Other") %></td>
				</tr>
				<tr>
					<td><%=resourceBundle.getString("Birthdate") %></td>
					<td><input type="date" name="birthdate" /></td>
				</tr>
					<tr>
					<td><%=resourceBundle.getString("password") %></td>
					<td><input type="password" name="password" /></td>
				</tr>
				<tr>
					<td><%=resourceBundle.getString("Confirmpassword") %></td>
					<td><input type="password" name="password" /></td>
				</tr>
				<tr>
					<td><%=resourceBundle.getString("address") %></td>
					<td><input type="text" name="address" /></td>
				</tr>
				<tr>
					<td> 
					</td> 
					<td>
						<select name="country" class="countries" id="countryId">
							<option value=""><%=resourceBundle.getString("country") %></option>
						</select>
					</td>
				</tr>
				<tr>
					<td> 
					</td> 
					<td>			
						<select name="state" class="states" id="stateId">
   							<option value=""><%=resourceBundle.getString("state") %></option>
   						</select>
					</td> 
				</tr>
				<tr>
					<td> 
					</td> 
					<td>			
						<select name="city" class="cities" id="cityId">
    						<option value=""><%=resourceBundle.getString("city") %></option>
						</select>
					</td>
				</tr>
				<tr>
					<td><%=resourceBundle.getString("zipcode") %></td>
					<td><input type="text" name="zipcode" /></td>
				</tr>
				<tr>
					<td>	<%=resourceBundle.getString("Selectcuisine") %> </td>
					<td>		
				<select name="cuisine_1">
				<% for(int i = 0; i < cuisines.length; i+=1) { %>
					<option value=<%= cuisines[i]%>><%= cuisines[i]%></option>
				<% } %>
			</select> </td>
			<tr>
					<td>	 </td>
					<td>		
				<select name="cuisine_2">
				<% for(int i = 0; i < cuisines.length; i+=1) { %>
					<option value=<%= cuisines[i]%>><%= cuisines[i]%></option>
				<% } %>
			</select> </td>
			<tr>
					<td>	</td>
					<td>		
				<select name="cuisine_3">
						<% for(int i = 0; i < cuisines.length; i+=1) { %>
					<option value=<%= cuisines[i]%>><%= cuisines[i]%></option>
				<% } %>
			</select> </td>
			
		</form>
				</tr>
				</table>
			<input class="button" type="submit" value="<%=resourceBundle.getString("Submit") %>" /></form>
			</div>
			
			
    

</body>
</html>