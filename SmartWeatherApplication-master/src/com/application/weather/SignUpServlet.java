package com.application.weather;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
	


		String startDateStr = request.getParameter("birthdate");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//surround below line with try catch block as below code throws checked exception
		Date birthdate = null;
		java.sql.Date sDate = null;
		try {
			birthdate = (Date) sdf.parse(startDateStr);
		    sDate = new java.sql.Date(birthdate.getTime());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String first_name=request.getParameter("first_name");
		String last_name=request.getParameter("last_name");
		String email=request.getParameter("email");
		String contact=request.getParameter("contact");
		String password=request.getParameter("password");
		String gender = request.getParameter("gender");
		String address = request.getParameter("address");
		String country = request.getParameter("country");
		String state = request.getParameter("state");
		String city = request.getParameter("city");
		String cuisine_1 = request.getParameter("cuisine_1");
		String cuisine_2 = request.getParameter("cuisine_2");
		String cuisine_3 = request.getParameter("cuisine_3");
		String zipcode = request.getParameter("zipcode");
		
	  
        try {
        	Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smartWeatherdb","root","1234567");  
			PreparedStatement ps = con.prepareStatement("INSERT INTO users (gender, first_name, last_name, email_id, phone_no, address, country, state, city, birth_date, fav_cuisine_1, fav_cuisine_2, fav_cuisine_3, postal_code, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, AES_ENCRYPT( ? , ? ));");
			ps.setString(1, gender);
			ps.setString(2, first_name);
            ps.setString(3, last_name);
            ps.setString(4, email);
            ps.setString(5, contact);
            ps.setString(6, address);
            ps.setString(7, country);
            ps.setString(8, state);
            ps.setString(9, city);
            ps.setDate(10, sDate);
            ps.setString(11, cuisine_1);
            ps.setString(12, cuisine_2);
            ps.setString(13, cuisine_3);
            ps.setString(14, zipcode);
            ps.setString(15, password);
            ps.setString(16, "secret123");
            

            ps.execute();
            con.close();
            request.getRequestDispatcher("login.jsp").include(request, response);

        }
        catch(Exception e) {
            e.printStackTrace();
        }
		out.close();
	}

}
