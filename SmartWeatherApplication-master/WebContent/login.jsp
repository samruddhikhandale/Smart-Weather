<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.ResourceBundle"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
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
</style>
</head>
<body>

<%
	String url = "http://localhost:8080/SmartWeatherApplication/";
	String home = url +"home.jsp";
	String signUp = url +"signUp.jsp";
%>
<%  ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.messages",request.getLocale());   %>

<h1 align="center"><%=resourceBundle.getString("loginTitle") %></h1>

<div  style="position: absolute; right: 0; top: 0; text-align: right">
	<input type="button" class="button" value="<%=resourceBundle.getString("home") %>" onClick="JavaScript:window.location='<%= home %>';">
	<input type="button" class="button" value="<%=resourceBundle.getString("login") %>" onClick="JavaScript:window.location='<%= signUp %>';">
</div>
<div align="center">
<form action="LoginServlet" method="post"> <!-- Action -->
		<table style="with: 50%">

			<tr>
				<td><%=resourceBundle.getString("emailID") %></td>
				<td><input type="text" name="username" /></td>
			</tr>
				<tr>
				<td><%=resourceBundle.getString("password") %></td>
				<td><input type="password" name="password" /></td>
			</tr>
		</table>
		<input class="button" type="submit" value="<%=resourceBundle.getString("login") %>" /></form>
</div> -->
</body>

</body>
</html>