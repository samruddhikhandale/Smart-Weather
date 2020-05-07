package com.application.weather;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
	
		
		String name=request.getParameter("username");
		String password=request.getParameter("password");
		
		if(Validate.checkUser(name, password)){
			out.print("Welcome user, "+name);
			HttpSession session=request.getSession();
			session.setAttribute("username",name);
			request.getRequestDispatcher("home.jsp").include(request, response);

		} else {
			out.print("Incorrect username or password!");
			request.getRequestDispatcher("login.jsp").include(request, response);
		}
		out.close();
	}

}
