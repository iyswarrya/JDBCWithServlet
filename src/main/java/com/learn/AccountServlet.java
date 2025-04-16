package com.learn;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AccountServlet
 */
@WebServlet("/accounts")
public class AccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection connection = null;

    @Override
    public void init(ServletConfig config) throws ServletException{
    	
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String connectionURL = config.getServletContext().getInitParameter("connectionURL");
			String username = config.getServletContext().getInitParameter("username");
			String password = config.getServletContext().getInitParameter("password");
			
			connection = DriverManager.getConnection(connectionURL, username, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		
		try(PreparedStatement ps = connection.prepareStatement("select * from accounts")){
			ResultSet resultSet = ps.executeQuery();
			
			while(resultSet.next()) {
				int id = resultSet.getInt(1);
				String firstName = resultSet.getString(2);
				String lastName = resultSet.getString(3);
				int balance = resultSet.getInt(4);
				
				writer.append("<h3> ID : "+id + " first name :" + firstName + " last name: "+ lastName + " has balance: "+balance+"</h3>");
			}
			writer.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
