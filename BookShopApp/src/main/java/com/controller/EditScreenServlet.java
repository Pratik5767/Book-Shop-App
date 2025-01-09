package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DB.JdbcUtils;

@WebServlet("/editScreen")
public class EditScreenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String sqlQuery = "SELECT `bookName`,`bookEdition`,`bookPrice` FROM book WHERE id = ?";

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doProcess(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doProcess(req, res);
	}

	public void doProcess(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// Get PrintWriter
		PrintWriter pw = res.getWriter();

		// set content Type
		res.setContentType("text/html");
		
		pw.println("<head>");
		pw.println("<link rel='stylesheet' href='css/bootstrap.css'>");
        pw.println("<style>");
        pw.println("body { font-family: Arial, sans-serif; margin: 2rem; }");
        pw.println("table { border: 1px solid #ccc; padding: 1rem; margin: 2rem auto; background-color: #f9f9f9; }");
        pw.println("td { padding: 10px; text-align: left; }");
        pw.println("input[type='text'] { width: 100%; padding: 8px; margin: 5px 0; box-sizing: border-box; }");
        pw.println("input[type='submit'], input[type='reset'] { padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; }");
        pw.println("input[type='submit'] { background-color: #28a745; color: white; }");
        pw.println("input[type='submit']:hover { background-color: #218838; }");
        pw.println("input[type='reset'] { background-color: #ffc107; color: black; }");
        pw.println("input[type='reset']:hover { background-color: #e0a800; }");
        pw.println("a { text-decoration: none; margin-top: 1rem; display: block; text-align: center; font-weight: bold; }");
        pw.println("a.btn { padding: 10px 20px; border-radius: 5px; margin: 10px auto; display: inline-block; }");
        pw.println("a.btn-primary { background-color: #007bff; color: white; }");
        pw.println("a.btn-primary:hover { background-color: #0056b3; }");
        pw.println("a.btn-success { background-color: #28a745; color: white; }");
        pw.println("a.btn-success:hover { background-color: #218838; }");
        pw.println("</style>");
        pw.println("</head>");
        pw.println("<body>");

		// get the id of record
		int id = Integer.parseInt(req.getParameter("id"));

		// Load JDBC Driver
		try {
			connection = JdbcUtils.getConnection();

			if (connection != null) {
				preparedStatement = connection.prepareStatement(sqlQuery);
			}

			if (preparedStatement != null) {
				preparedStatement.setInt(1, id);
				resultSet = preparedStatement.executeQuery();
			}

			if (resultSet != null) {
				if (resultSet.next()) {
					pw.println("<form action='editurl?id=" + id + "' method='post'>");
					pw.println("<table>");
					pw.println("<tr>");
					pw.println("<td><label for='bookName'>Book Name</label></td>");
					pw.println("<td><input type='text' id='bookName' name='bookName' value='" + resultSet.getString(1) + "'></td>");
					pw.println("</tr>");
					
					pw.println("<tr>");
					pw.println("<td><label for='bookName'>Book Edition</label></td>");
					pw.println("<td><input type='text' id='bookEdition' name='bookEdition' value='" + resultSet.getString(2) + "'></td>");
					pw.println("</tr>");
					
					pw.println("<tr>");
                    pw.println("<td><label for='bookPrice'>Book Price</label></td>");
                    pw.println("<td><input type='text' id='bookPrice' name='bookPrice' value='" + resultSet.getFloat(3) + "'></td>");
                    pw.println("</tr>");
					
					pw.println("<tr>");
					pw.println("<td><input type='submit' value='Edit'</td>");
					pw.println("<td><input type='reset' value='Cancel'</td>");
					pw.println("</tr>");
					pw.println("</table>");
					pw.println("</form>");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			pw.println("<h2 class='text-danger'>" + e.getMessage() + "</h2>");
		} catch (Exception e) {
			e.printStackTrace();
			pw.println("<h1 class='text-danger'>" + e.getMessage() + "</h1>");
		} finally {
			try {
				JdbcUtils.clearConnections(connection, preparedStatement, resultSet);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		pw.println("<div class='text-center mt-4'>");
		pw.println("<a href='home.html' class='btn btn-success'>Home</a>");
        pw.println("<a href='booklist' class='btn btn-primary'>View Book List</a>");
        pw.println("</div>");
        pw.println("</body>");
		pw.close();
	}
}
