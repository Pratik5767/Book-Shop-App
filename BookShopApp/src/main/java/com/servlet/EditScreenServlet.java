package com.servlet;

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
					pw.println("<table align='center'>");
					pw.println("<tr>");
					pw.println("<td>Book Name</td>");
					pw.println("<td><input type='text' name='bookName' value='" + resultSet.getString(1) + "'></td>");
					pw.println("</tr>");
					
					pw.println("<tr>");
					pw.println("<td>Book Edition</td>");
					pw.println("<td><input type='text' name='bookEdition' value='" + resultSet.getString(2) + "'></td>");
					pw.println("</tr>");
					
					pw.println("<tr>");
					pw.println("<td>Book Price</td>");
					pw.println("<td><input type='text' name='bookPrice' value='" + resultSet.getFloat(3) + "'></td>");
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
			pw.println("<h2>" + e.getMessage() + "</h2>");
		} catch (Exception e) {
			e.printStackTrace();
			pw.println("<h1>" + e.getMessage() + "</h1>");
		} finally {
			try {
				JdbcUtils.clearConnections(connection, preparedStatement, resultSet);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		pw.println("<a href='home.html'>Home</a>");
		pw.println("<br/>");
		pw.println("<a href='booklist'>Book List</a>");
		pw.close();
	}
}
